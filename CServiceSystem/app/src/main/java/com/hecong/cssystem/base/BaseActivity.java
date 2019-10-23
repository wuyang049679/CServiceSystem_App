package com.hecong.cssystem.base;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.FragmentActivity;

import com.gyf.barlibrary.ImmersionBar;
import com.hecong.cssystem.utils.android.ToastUtils;
import com.hecong.cssystem.wight.CustomDialog;
import com.hecong.cssystem.wight.stateview.StateView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;


public abstract class BaseActivity<T extends BasePresenterIm, V> extends FragmentActivity implements BaseView<V> {
    public String TAG = "xy_activity";
    public T mPresenter;
    private StateView mStateView;
    protected Activity mActivity;
    public int mUserId = -1;

    private ImmersionBar mImmersionBar;
    private CustomDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        setContentView(getContentView());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initStateLayout();
        ButterKnife.bind(this);

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
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
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
        mStateView.showRetry();

    }

    @Override
    public abstract void showDataSuccess(V datas);


    @Override
    public void showLoadingView(String msg) {
//        if(loadingDialog==null){
//            loadingDialog=new LoadingDialog();
//        }
//        loadingDialog.show(mActivity.getFragmentManager(), msg);
       dialog.show();
    }

    @Override
    public void showLoadingView() {

        mStateView.showLoading();
    }

    @Override
    public void showNetErrorView() {
        mStateView.showNetError();
    }

    @Override
    public void showEmptyView(String msg) {
        mStateView.showEmpty(msg);

    }

    @Override
    public void showContentView() {
        mStateView.showContent();
    }

    @Override
    public void showRetryView() {
        mStateView.showRetry();
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
    public void refreshView(Message message) {
        int what = message.what;
//        if (what == Constant.MESSAGEEVENT) {
//            mUserId = message.arg1;
//        }
    }


}
