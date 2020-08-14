package com.hc_android.hc_css.base;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gyf.immersionbar.ImmersionBar;
import com.hc_android.hc_css.R;
import com.hc_android.hc_css.service.NetworkConnectChangedReceiver;
import com.hc_android.hc_css.ui.activity.FileBrowsActivity;
import com.hc_android.hc_css.ui.activity.StartActivity;
import com.hc_android.hc_css.utils.Constant;
import com.hc_android.hc_css.utils.android.EasyPermissionUtils;
import com.hc_android.hc_css.utils.android.RomUtil;
import com.hc_android.hc_css.utils.android.SharedPreferencesUtils;
import com.hc_android.hc_css.utils.android.ToastUtils;
import com.hc_android.hc_css.utils.android.app.StatusHolder;
import com.hc_android.hc_css.utils.socket.EventServiceImpl;
import com.hc_android.hc_css.utils.socket.MessageEvent;
import com.hc_android.hc_css.utils.socket.NotificationUtils;
import com.hc_android.hc_css.wight.CustomDialog;
import com.hc_android.hc_css.wight.LoadingDialog;
import com.hc_android.hc_css.wight.stateview.StateView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.net.URISyntaxException;

import butterknife.ButterKnife;
import me.jessyan.autosize.utils.LogUtils;


public abstract class BaseActivity<T extends BasePresenterIm, V> extends AppCompatActivity implements BaseView<V> {
    public String TAG = "wy_activity";
    public T mPresenter;
    private StateView mStateView;
    protected Activity mActivity;
    public int mUserId = -1;
    private ImmersionBar mImmersionBar;
    private CustomDialog dialog;
    private NetworkConnectChangedReceiver mNetWorkChangReceiver;
    public static int activityActive; //全局变量,表示当前在前台的activity数量
    private boolean isBackground;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        setContentView(getContentView());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initStateLayout();
        ButterKnife.bind(this);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        if (isImmersionBarEnabled()){
            initImmersionBar();
        }
        if (null != getPresenter()) {
            mPresenter = getPresenter();
        }
//        CustomDialog.Builder builder=new CustomDialog.Builder(BaseActivity.this);
//        dialog=  builder.view(R.layout.loading_dialog)
//                .style(R.style.Dialog)
//                .heightDimenRes(R.dimen.progress_witch)
//                .widthDimenRes(R.dimen.progress_witch)
//                .build();
        initView();
        
        addOclick();
        if (null != getPresenter()) {
            mPresenter = getPresenter();
            mPresenter.attachView(this, this);
        }
        initData();


//        首先判断是不是初始化的页面 是的话就设置是否被杀为false，如果不是初始化页面就判断是否被杀，被杀就重启
        if (mActivity instanceof StartActivity) {
            StatusHolder.getInstance().setKill(false);
        }else {
            if(StatusHolder.getInstance().isKill()) {
                Log.i(TAG,"app was kill");
                Intent intent = new Intent(this, StartActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                android.os.Process.killProcess(android.os.Process.myPid());
            }else {
                Log.i(TAG,"app was normal");
            }
        }

        //注册网络状态监听广播
        mNetWorkChangReceiver = new NetworkConnectChangedReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mNetWorkChangReceiver, filter);
    }

    protected  void addOclick(){};


    protected void initImmersionBar() {
        //在BaseActivity里初始化
        mImmersionBar = ImmersionBar.with(this);
    }
    /**
     * 是否可以使用沉浸式
     * Is immersion bar enabled boolean.
     *
     * @return the boolean
     */
    protected boolean isImmersionBarEnabled() {
        return true;
    }
    /**
     * 注入statelayout
     */
    private void initStateLayout() {
        View view = injectTarget();
        if (view == null) {
            return;
        }
        mStateView = StateView.inject(injectTarget());
        mStateView.setOnRetryClickListener(new StateView.OnRetryClickListener() {
            @Override
            public void onRetryClick() {
                reloadActivity();
            }
        });
    }

    /**
     * 点击重试要做的操作
     */
    protected abstract void reloadActivity();

    /**
     * 注入要替换的View
     */
    protected abstract View injectTarget();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 初始化View
     */
    protected abstract void initView();

    /**
     * 页面布局
     */
    public abstract int getContentView();

    @Override
    public void showDataError(String errorMessage) {
        if (mStateView!=null)mStateView.showRetry();

    }

    @Override
    public abstract void showDataSuccess(V datas);


    @Override
    public void showLoadingView(String msg) {
        if(loadingDialog==null){
            loadingDialog=new LoadingDialog();
        }
        loadingDialog.show(mActivity.getFragmentManager(), msg);
//        loadingDialog.show();
    }

    @Override
    public void showLoadingView() {

        if (mStateView!=null) mStateView.showLoading();
    }

    @Override
    public void showNetErrorView() {
        mStateView.showRetry();
        if(loadingDialog!=null){
            loadingDialog.dismiss();
        }
    }

    @Override
    public void showEmptyView(String msg) {
        if (mStateView!=null)mStateView.showEmpty(msg);
        if(loadingDialog!=null){
            loadingDialog.dismiss();
        }
    }

    @Override
    public void showContentView() {
        if (mStateView!=null)mStateView.showContent();
        if(loadingDialog!=null){
            loadingDialog.dismiss();
        }
    }

    @Override
    public void showRetryView() {
        if (mStateView!=null)mStateView.showRetry();
        if(loadingDialog!=null){
            loadingDialog.dismiss();
        }
    }

    public T getPresenter() {
        return mPresenter;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.unsubcrible();

        }
        unRegisterSomething();
        ImmersionBar.destroy(this,null);
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
            EventBus.getDefault().removeAllStickyEvents();
        }
        //如果DToast.make(mContext)使用的是ActivityContext,则在退出Activity时需要调用
//        DToast.cancelActivityToast(this);
    }

    /**
     * 解除注册一些东西
     */
    public void unRegisterSomething() {
        unregisterReceiver(mNetWorkChangReceiver);
    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);
    }


    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
        //定义转场动画
//        overridePendingTransition(R.anim.activity_start_enter, R.anim.activity_finish_exit);
    }


    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 是否有读写权限
     */

    public boolean hasPermission(){
        String[] perms = {
                // 把你想要申请的权限放进这里就行，注意用逗号隔开
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
        };
        boolean flag = EasyPermissionUtils.checkPermission(BaseActivity.this, perms);
        if (!flag) {
            EasyPermissionUtils.requireSomePermission(BaseActivity.this,"获取手机读写权限",1,perms);
        }
        return flag;
    }
    /**
     * 短暂显示Toast提示(来自String)
     **/
    public void showShortToast(String text) {
                ToastUtils.showShort( text);
    }

    /**
     * 短暂显示Toast提示(id)
     **/
    public void showShortToast(int resId) {
//        ToastUtils.show(this.getResources().getString(resId));
    }
    /**
     * 短暂显示显示在中间Toast提示(id)
     **/
    public void showCenterToast(int resId) {
//        ToastUtils.showAtCenter(this.getResources().getString(resId));
    }
    @Override
    public void showCommentEmptyView() {

    }

    @Override
    public void showDiyView(int imgRes, String msg) {

    }

    @Override
    public void logCat(String m) {


        Log.e("erroe",m);
    }

    @Override
    public void startToLogIn() {
//        startActivity(LogInActivity.class);
    }

    @Override
    public void hideLoading() {
//        loadingDialog.dismiss();
        dialog.dismiss();

    }

    @Override
    protected void onResume() {
        super.onResume();
//        mUserId = ShareUtil.getInt("userId");
    }

//    @Override
//    public void showPages(PageEntity pageEntity) {
//        this.mPageEntity = pageEntity;
//    }

    /**
     * 获取xml中的string
     */
    public String getXmlString(int resId){

        return getResources().getString(resId);
    }
    /**
     * 设置手机号分割间隔
     */
//    public static void setPNSpace(final AddAndSubEditText editText) {
//        editText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (s == null || s.length() == 0) return;
//                StringBuilder sb = new StringBuilder();
//                for (int i = 0; i < s.length(); i++) {
//                    if (i != 3 && i != 8 && s.charAt(i) == ' ') {
//                        continue;
//                    } else {
//                        sb.append(s.charAt(i));
//                        if ((sb.length() == 4 || sb.length() == 9) && sb.charAt(sb.length() - 1) != ' ') {
//                            sb.insert(sb.length() - 1, ' ');
//                        }
//                    }
//                }
//                if (!sb.toString().equals(s.toString())) {
//                    int index = start + 1;
//                    if (sb.charAt(start) == ' ') {
//                        if (before == 0) {
//                            index++;
//                        } else {
//                            index--;
//                        }
//                    } else {
//                        if (before == 1) {
//                            index--;
//                        }
//                    }
//                    editText.setText(sb.toString());
//                    editText.setSelection(index);
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSocketEvent(MessageEvent message) {

    }

//    private void isKeyboardShown(Context context) {
//        View decorView = ((Activity)context).getWindow().getDecorView();
//        //这个可以获取键盘的高度
//        decorView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                Rect rect = new Rect();
//                decorView.getWindowVisibleDisplayFrame(rect);
//                //计算出可见屏幕的高度
//                int displayHight = rect.bottom - rect.top;
//                //获取屏幕整体高度
//                int height = decorView.getHeight();
//                //判断键盘是否显示和消失
//                boolean visible = (double) displayHight / height < 0.8;
//                int statusBarHeight = 0;
//                //获取键盘的输入法工具条的高度，否则设置的lyoContent的高度将会多出一点
//                try {
//                    Class<?> c = Class.forName("com.android.internal.R$dimen");
//                    Object obj = c.newInstance();
//                    Field field = c.getField("status_bar_height");
//                    int x = Integer.parseInt(field.get(obj).toString());
//                    statusBarHeight = context.getResources().getDimensionPixelSize(x);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                int keyboardHeight = height - displayHight - statusBarHeight;
//                onSoftKeyBoardVisible(visible, keyboardHeight);
//            }
//        });
//        //获取键盘的高度
//
//    }
//    private void onSoftKeyBoardVisible(boolean visible, int keyboardHeight) {
//        //如果键盘显示那么获取到他的高度设置lyoContent的marginBottom 这里lyoContent就是包裹EditText的那个view
//        if (visible) {
//            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//                    LinearLayout.LayoutParams.WRAP_CONTENT);
//            lp.setMargins(0, 0, 0, keyboardHeight);
//            lyoContent.setLayoutParams(lp);
//        } else {
//            //消失直接设置lyoContent的marginBottom 为0让其恢复到原来的布局
//            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//                    LinearLayout.LayoutParams.WRAP_CONTENT);
//            lp.setMargins(0, 0, 0, 0);
//            lyoContent.setLayoutParams(lp);
//        }
//    }


    @Override
    protected void onStart() {
        //app 从后台唤醒，进入前台
        if (activityActive==0){
            Log.i(TAG, "程序从后台唤醒");
            NotificationUtils notificationUtils=new NotificationUtils(this);
            notificationUtils.clearNotice();//清除消息通知栏
            if (BaseApplication.getUserBean()!=null) {
                String state = BaseApplication.getUserBean().getState();
                if (state==null||!state.equals("break")) {
                    EventServiceImpl.getInstance().keepLink();//发送一次心跳
                    EventServiceImpl.getInstance().onTyping();
                }
            }

        }
        activityActive++;
        isBackground = false;

        super.onStart();
    }

    @Override
    protected void onStop() {
        activityActive--;
        if (activityActive == 0) {
            //app 进入后台
            isBackground = true;//记录当前已经进入后台
            Log.i(TAG, "程序进入后台");
            if (RomUtil.isMiui()) {//小米推送,断开连接,后台无推送的消息
                EventServiceImpl.getInstance().disconnect();
            }
        }
        super.onStop();
    }



}
