package com.hecong.cssystem.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.hecong.cssystem.R;
import com.hecong.cssystem.base.BaseActivity;
import com.hecong.cssystem.base.BaseApplication;
import com.hecong.cssystem.contract.LoginActivityContract;
import com.hecong.cssystem.entity.LoginEntity;
import com.hecong.cssystem.presenter.LoginActivityPresenter;
import com.hecong.cssystem.utils.Constant;
import com.hecong.cssystem.utils.ValidateUtils;
import com.hecong.cssystem.utils.android.SharedPreferencesUtils;
import com.hecong.cssystem.utils.android.ToastUtils;
import com.hecong.cssystem.wight.AddAndSubEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity<LoginActivityPresenter, LoginEntity.DataBean> implements LoginActivityContract.View {


    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_chat_login)
    Button btnChatLogin;

    @BindView(R.id.progress_login)
    ProgressBar progressLogin;

    private String usernames;
    private String passwords;
    @Override
    public LoginActivityPresenter getPresenter() {
        return new LoginActivityPresenter();
    }

    @Override
    protected void reloadActivity() {

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
        if (!TextUtils.isEmpty(user)){
            username.setText(user);
        }
        if (!TextUtils.isEmpty(pass)){
            password.setText(pass);
        }
    }

    @Override
    protected void initView() {

        ImmersionBar.with(this)
                .statusBarColor(R.color.white)
                .init();
        AddAndSubEditText.TextChangeListener textChangeListener = new AddAndSubEditText.TextChangeListener(btnLogin);
//        监听输入不为空
        textChangeListener.addAllEditText(username, password);

        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i== EditorInfo.IME_ACTION_GO){
                    checkInput();
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
        BaseApplication.setHash(datas.getHash());
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void showDataError(String errorMessage) {
        super.showDataError(errorMessage);
        isLoading(false);
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
    @OnClick({R.id.btn_login, R.id.btn_chat_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                checkInput();
                break;
            case R.id.btn_chat_login:
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
            isLoading(true);
            mPresenter.pLogin(usernames, passwords);
        } else {
            ToastUtils.showShort("请输入正确的邮箱或手机");
        }
    }

    /**
     * 是否是加载中状态
     * @param isLoading
     */
    private void isLoading(boolean isLoading) {

        if (isLoading){
            btnLogin.setText("");
            progressLogin.setVisibility(View.VISIBLE);
            AddAndSubEditText.isLoading=true;
            btnLogin.setEnabled(false);
            btnChatLogin.setEnabled(false);
        }else {
            btnLogin.setText(R.string.act_login_dl);
            progressLogin.setVisibility(View.GONE);
            AddAndSubEditText.isLoading=false;
            btnLogin.setEnabled(true);
            btnChatLogin.setEnabled(true);
        }
    }


}
