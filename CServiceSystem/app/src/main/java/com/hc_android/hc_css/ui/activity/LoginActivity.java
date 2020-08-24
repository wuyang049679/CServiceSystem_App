package com.hc_android.hc_css.ui.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.azhon.appupdate.config.UpdateConfiguration;
import com.azhon.appupdate.listener.OnDownloadListener;
import com.azhon.appupdate.manager.DownloadManager;
import com.gyf.immersionbar.ImmersionBar;
import com.hc_android.hc_css.R;
import com.hc_android.hc_css.base.BaseActivity;
import com.hc_android.hc_css.base.BaseApplication;
import com.hc_android.hc_css.contract.LoginActivityContract;
import com.hc_android.hc_css.entity.LoginEntity;
import com.hc_android.hc_css.entity.UserEntity;
import com.hc_android.hc_css.presenter.LoginActivityPresenter;
import com.hc_android.hc_css.utils.Constant;
import com.hc_android.hc_css.utils.FileUtils;
import com.hc_android.hc_css.utils.ValidateUtils;
import com.hc_android.hc_css.utils.android.EasyPermissionUtils;
import com.hc_android.hc_css.utils.android.SharedPreferencesUtils;
import com.hc_android.hc_css.utils.android.ToastUtils;
import com.hc_android.hc_css.utils.apkupdate.ApkDownloadManager;
import com.hc_android.hc_css.utils.apkupdate.ApkUpdateUtils;
import com.hc_android.hc_css.wight.AddAndSubEditText;
import com.huawei.updatesdk.service.otaupdate.AppUpdateActivity;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity<LoginActivityPresenter, LoginEntity.DataBean> implements LoginActivityContract.View  {


    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.btn_login)
    TextView btnLogin;
    @BindView(R.id.btn_chat_login)
    TextView btnChatLogin;

    @BindView(R.id.progress_login)
    ProgressBar progressLogin;
    @BindView(R.id.progress_wxlogin)
    ProgressBar progressWxlogin;
    @BindView(R.id.act_lin)
    ConstraintLayout actLin;

    private String usernames;
    private String passwords;

    @Override
    public LoginActivityPresenter getPresenter() {
        return new LoginActivityPresenter();
    }

    @Override
    protected void reloadActivity() {

    }

    public static LoginActivity getInstance() {
        return new LoginActivity();
    }

    @Override
    protected View injectTarget() {
        return findViewById(R.id.act_lin);
    }

    @Override
    protected void initData() {
        //自动登录密码自动填充
        String user = (String) SharedPreferencesUtils.getParam(Constant.USERNAME, "");
        String pass = (String) SharedPreferencesUtils.getParam(Constant.PASSWORD, "");
        if (!TextUtils.isEmpty(user)) {
            username.setText(user);
        }
        if (!TextUtils.isEmpty(pass)) {
            password.setText(pass);
        }
        if (!TextUtils.isEmpty(user) && !TextUtils.isEmpty(pass)) {
            btnLogin.setEnabled(true);
            btnLogin.setBackgroundResource(R.drawable.login_btn);
        }

    }

    @Override
    protected void initView() {

        AddAndSubEditText.TextChangeListener textChangeListener = new AddAndSubEditText.TextChangeListener(btnLogin);
//        监听输入不为空
        textChangeListener.addAllEditText(username, password);

        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_GO) {
                    String[] perms = {
                            // 把你想要申请的权限放进这里就行，注意用逗号隔开
                            Manifest.permission.READ_PHONE_STATE,
                    };
                    boolean flag = EasyPermissionUtils.checkPermission(LoginActivity.this, perms);
                    if (!flag) {
                        //动态申请唯一识别码
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                                requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
                            }
                        }
                    } else {
                        checkInput();
                    }
                }
                return false;
            }
        });
    }

    @Override
    public int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    public void showDataSuccess(LoginEntity.DataBean datas) {

        SharedPreferencesUtils.setParam(Constant.HASH, datas.getHash());
        SharedPreferencesUtils.setParam(Constant.USERNAME, usernames);
        SharedPreferencesUtils.setParam(Constant.PASSWORD, passwords);
        UserEntity userEntity = new UserEntity();
        userEntity.setHash(datas.getHash());
        userEntity.setUserbean(datas.getInfo());
        BaseApplication.setUserEntity(userEntity);
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
//        AddAndSubEditText.hideSoftKeyboard(this,password);
        overridePendingTransition(R.anim.activity_open, R.anim.activity_close);
        finish();
    }

    @Override
    public void showDataError(String errorMessage) {
        super.showDataError(errorMessage);
        showShortToast(errorMessage);
        isLoading(false,false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);


    }



    /**
     * 登录点击事件
     *
     * @param view
     */
    @OnClick({R.id.btn_login, R.id.btn_chat_login, R.id.tv_register })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                String[] perms = {
                        // 把你想要申请的权限放进这里就行，注意用逗号隔开
                        Manifest.permission.READ_PHONE_STATE,
                };
                boolean flag = EasyPermissionUtils.checkPermission(this, perms);

                if (!flag) {
                    //动态申请唯一识别码
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                            requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
                        }
                    }
                } else {
                    checkInput();
                }
                break;
            case R.id.btn_chat_login:
                wxLogin();
                break;
            case R.id.tv_register:
                startActivity(RegisterActivity.class);
                break;
        }
    }

    /**
     * 用户输入校验
     */
    private void checkInput() {
        usernames = username.getText().toString().trim();
        passwords = password.getText().toString();
        if (ValidateUtils.isPhone(usernames) || ValidateUtils.isEmail(usernames)) {
            isLoading(true,false);
            String[] perms = {
                    // 把你想要申请的权限放进这里就行，注意用逗号隔开
                    Manifest.permission.READ_PHONE_STATE,
            };
            String imei = null;
            boolean flag = EasyPermissionUtils.checkPermission(this, perms);
            if (flag) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
//                    使用AndroidId代替，缺点是应用签署密钥或用户（如系统恢复出产设置）不同返回的Id不同。与实际测试结果相符。
//经实际测试：相同签名密钥的不同应用androidId相同，不同签名的应用androidId不同。恢复出产设置或升级系统没测。
                    imei = Settings.System.getString(this.getContentResolver(),
                            Settings.Secure.ANDROID_ID);
                }else {
                    TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(this.TELEPHONY_SERVICE);
                    imei = telephonyManager.getDeviceId();
                }

            }
            mPresenter.pLogin(usernames, passwords, imei);
        } else {
            ToastUtils.showShort("请输入正确的邮箱或手机");
        }
    }

    /**
     * 是否是加载中状态
     *
     * @param isLoading
     */
    private void isLoading(boolean isLoading,boolean isWeChat) {

        if (isLoading) {
            if (isWeChat){//如果是微信登录
                btnChatLogin.setText("");
                progressWxlogin.setVisibility(View.VISIBLE);
            }else  {
                btnLogin.setText("");
                progressLogin.setVisibility(View.VISIBLE);
            }
            AddAndSubEditText.isLoading = true;
            btnLogin.setEnabled(false);
            btnLogin.setBackgroundResource(R.drawable.login_btn_f);
            btnChatLogin.setEnabled(false);
            btnChatLogin.setBackgroundResource(R.drawable.login_btn_f);
        } else {

            btnChatLogin.setText(R.string.login_chat_btn);
            progressWxlogin.setVisibility(View.GONE);
            btnLogin.setText(R.string.act_login_dl);
            progressLogin.setVisibility(View.GONE);
            AddAndSubEditText.isLoading = false;
            btnLogin.setEnabled(true);
            btnChatLogin.setEnabled(true);
            btnLogin.setBackgroundResource(R.drawable.login_btn);
            btnChatLogin.setBackgroundResource(R.drawable.login_chat_btn);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        checkInput();
    }


    //微信登录页
    private void wxLogin() {
        if (!BaseApplication.mWXapi.isWXAppInstalled()) {
            showShortToast("您还未安装微信客户端");
            return;
        }

        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "ufile_wx_login";//这个字段可以任意更改
        BaseApplication.mWXapi.sendReq(req);

    }

    @Override
    public void showWeChatLogin(LoginEntity.DataBean dataBean) {
        if (dataBean.getHash()!=null){
        mPresenter.pCheckLogin(dataBean.getHash());
        }
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String wxCode = intent.getStringExtra(Constant.CODE);
        if (wxCode != null) {
            isLoading(true,true);
            mPresenter.pWxLogin(wxCode);
        } else {
            if (intent.hasExtra(Constant.CODE)) {
                Toast.makeText(this, "微信登录授权失败", Toast.LENGTH_SHORT).show();
            }
        }

    }

}
