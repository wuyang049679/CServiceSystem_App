package com.hc_android.hc_css.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.azhon.appupdate.config.UpdateConfiguration;
import com.azhon.appupdate.listener.OnDownloadListener;

import com.hc_android.hc_css.R;
import com.hc_android.hc_css.base.BaseActivity;
import com.hc_android.hc_css.base.BaseApplication;
import com.hc_android.hc_css.contract.MainActivityContract;
import com.hc_android.hc_css.entity.AppUpdateEntity;
import com.hc_android.hc_css.entity.LoginEntity;
import com.hc_android.hc_css.entity.MessageEntity;
import com.hc_android.hc_css.entity.TagEntity;

import com.hc_android.hc_css.presenter.MainActivityPresenter;
import com.hc_android.hc_css.service.PushSendService;
import com.hc_android.hc_css.ui.fragment.ChatListFragment;
import com.hc_android.hc_css.ui.fragment.MineFragment;
import com.hc_android.hc_css.ui.fragment.VisitorListFragment;
import com.hc_android.hc_css.utils.Constant;
import com.hc_android.hc_css.utils.DateUtils;
import com.hc_android.hc_css.utils.NullUtils;
import com.hc_android.hc_css.utils.android.AppUtils;
import com.hc_android.hc_css.utils.android.SharedPreferencesUtils;
import com.hc_android.hc_css.utils.android.app.CacheData;
import com.hc_android.hc_css.utils.socket.MessageEvent;

import com.hc_android.hc_css.wight.ChoiceDialog;
import com.hc_android.hc_css.wight.LocalDataSource;
import com.next.easynavigation.view.EasyNavigationBar;
import com.tencent.bugly.crashreport.CrashReport;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.provider.ContactsContract.Intents.Insert.ACTION;
import static com.hc_android.hc_css.utils.socket.MessageEventType.EventMessage;

/**
 * @author wuyang
 */
public class MainActivity extends BaseActivity<MainActivityPresenter, LoginEntity.DataBean> implements MainActivityContract.View , OnDownloadListener {


    @BindView(R.id.easyNavigationBar)
    EasyNavigationBar easyNavigationBar;


    private String[] tabText = {"对话", "访客", "我的"};
    //未选中icon
    private int[] normalIcon = {R.mipmap.chat_h, R.mipmap.fk_h, R.mipmap.user_h};
    //选中时icon
    private int[] selectIcon = {R.mipmap.chat, R.mipmap.fk, R.mipmap.user};

    private List<Fragment> fragments = new ArrayList<>();
    private ProgressDialog pd1;



    @Override
    public MainActivityPresenter getPresenter() {
        return new MainActivityPresenter();
    }

    @Override
    protected void reloadActivity() {

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String action = intent.getStringExtra("_ACTION");
        if (intent!=null&&action!=null&&action.equals("FRESH")) {
            if (fragments.get(2)!=null)((MineFragment) fragments.get(2)).setRealName();
        }
    }

    @Override
    protected View injectTarget() {
        return findViewById(R.id.easyNavigationBar);
    }

    @Override
    protected void initData() {
        //进行登录验证
        String hash = (String) SharedPreferencesUtils.getParam(Constant.HASH, "");
//         mPresenter.pCheckLogin(hash);
         mPresenter.pGetTagList();//获取标签列表
         mPresenter.pAppUpdate();
         showFragment();
    }

    @Override
    protected void initView() {

        CrashReport.putUserData(this,"_serviceId",BaseApplication.getUserBean().getId());
        CacheData.getAllCache();


    }

    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }


    public void showFragment() {

//        UserEntity userEntity = new UserEntity();
//        userEntity.setHash(datas.getHash());
//        userEntity.setUserbean(datas.getInfo());
//        BaseApplication.setUserEntity(userEntity);


        fragments.add(ChatListFragment.newInstance());
        fragments.add(VisitorListFragment.newInstance());
        fragments.add(MineFragment.newInstance());

        easyNavigationBar.titleItems(tabText)
                .normalIconItems(normalIcon)
                .selectIconItems(selectIcon)
                .fragmentList(fragments)
                .fragmentManager(getSupportFragmentManager())
                .addLayoutRule(EasyNavigationBar.RULE_BOTTOM)
                .iconSize(20)
                .tabTextSize(10)
                .normalTextColor(getResources().getColor(R.color.black_999))   //Tab未选中时字体颜色
                .selectTextColor(getResources().getColor(R.color.theme_app))
                .navigationHeight(52)
                .msgPointTop(-11)
                .msgPointSize(17)
                .smoothScroll(false)  //点击Tab  Viewpager切换是否有动画
                .canScroll(false)
                .msgPointTextSize(11)
                //分割线高度  默认1px
                .lineColor(getResources().getColor(R.color.black_line))
                .mode(EasyNavigationBar.MODE_NORMAL)
                .hasPadding(true)//fragment列表底部重叠，部分看不到
                .build();

       PushSendService.initPush(this);//注册推送服务



    }

    @Override
    public void showDataError(String errorMessage) {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
//        overridePendingTransition(R.anim.activity_open,R.anim.activity_close);
        finish();
    }

    @Override
    public void showDataSuccess(LoginEntity.DataBean datas) {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
//        PushManager.getInstance().setPushCallback(new OPPOPushReceiver());


    }
    public EasyNavigationBar getNavigationBar() {
        return easyNavigationBar;
    }

    @Override
    public void showTagList(TagEntity.DataBean dataBean) {

        if (!NullUtils.isEmptyList(dataBean.getList())){
            LocalDataSource.setTAGLIST(dataBean.getList());
        }
    }

    @Override
    public void showAppUpdate(AppUpdateEntity.DataBean dataBean) {

        int versionCode = AppUtils.getVersionCode(this);
        if (dataBean.getVersion()>versionCode){
            startUpdate(dataBean.getDownloadAddress());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("tag","关闭了");

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    private void startUpdate(String url) {
        UpdateConfiguration configuration = new UpdateConfiguration()
                //输出错误日志
                .setEnableLog(true)
                //设置自定义的下载
                //.setHttpManager()
                //下载完成自动跳动安装页面
                .setJumpInstallPage(true)
                //设置对话框背景图片 (图片规范参照demo中的示例图)
                //.setDialogImage(R.drawable.ic_dialog)
                //设置按钮的颜色
                //.setDialogButtonColor(Color.parseColor("#E743DA"))
                //设置对话框强制更新时进度条和文字的颜色
                //.setDialogProgressBarColor(Color.parseColor("#E743DA"))
                //设置按钮的文字颜色
                .setDialogButtonTextColor(Color.WHITE)
                //设置是否显示通知栏进度
                .setShowNotification(true)
                //设置是否提示后台下载toast
                .setShowBgdToast(false)
                //设置强制更新
                .setForcedUpgrade(true)
                //设置对话框按钮的点击监听
//                .setButtonClickListener(this)
                //设置下载过程的监听
                .setOnDownloadListener(this);
        ChoiceDialog.updateApkDialog(this,url,configuration);

    }

    @Override
    public void start() {
        pd1 = new ProgressDialog(MainActivity.this);
        pd1.setCancelable(false);
        pd1.setMessage("正在下载更新...");
        pd1.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd1.setIndeterminate(false);
        pd1.show();
    }

    @Override
    public void downloading(int max, int progress) {
        float i = (float) progress /(float) max;
        if (pd1!=null)pd1.setProgress((int) (i*100));

    }

    @Override
    public void done(File apk) {

        if (pd1!=null)pd1.dismiss();
    }

    @Override
    public void cancel() {
        if (pd1!=null)pd1.dismiss();
    }

    @Override
    public void error(Exception e) {
        if (pd1!=null)pd1.dismiss();
    }


    @Override
    public void onSocketEvent(MessageEvent msg) {
        super.onSocketEvent(msg);
        MessageEntity message = (MessageEntity) msg.getMsg();
        if (msg.getType() == EventMessage) ;
        switch (message.getAct()) {
            //连接成功
//            case MESSAGE_LOGINSUC:
//
//                BaseApplication.getUserEntity().setSocketId(message.getSocketId());
//                BaseConfig.setStateChange(BaseApplication.getUserBean().getState(),false);
//                break;
//            case MESSAGE_STATEUPATE://收到客服在线状态
//                if (message.getServiceId() != null && message.getServiceId().equals(BaseApplication.getUserBean().getId())) {
//                    String hash = (String) SharedPreferencesUtils.getParam(Constant.HASH, "");
//                    if (!message.getState().equals(BaseApplication.getUserBean().getState())&&message.getHash()!=null&&!message.getHash().equals(hash)){
//
////                        if (message.getState().equals("break")){
////                            EventServiceImpl.getInstance().disconnect();
////                        }
////                        BaseConfig.setStateChange(message.getState(),false);
//                    }
//                }
//                break;
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        Log.i(TAG, "ACK: onSaveInstanceState" + "保存数据");
    }


    public void  showIndex(int index){
            easyNavigationBar.selectTab(index);
    }
}
