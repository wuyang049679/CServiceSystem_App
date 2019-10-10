package com.hecong.cssystem.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.hecong.cssystem.R;
import com.hecong.cssystem.base.BaseActivity;
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

public class LoginActivity extends BaseActivity<LoginActivityPresenter, LoginEntity> implements LoginActivityContract.View {


    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.btn_login)
    LinearLayout btnLogin;
    @BindView(R.id.btn_chat_login)
    Button btnChatLogin;
    @BindView(R.id.login_tv)
    TextView loginTv;
    @BindView(R.id.progress_login)
    ProgressBar progressLogin;

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

        String ss = "测试";
    }

    @Override
    protected void initView() {

        ImmersionBar.with(this)
                .statusBarColor(R.color.white)
                .init();
        AddAndSubEditText.TextChangeListener textChangeListener = new AddAndSubEditText.TextChangeListener(btnLogin);
        //监听输入不为空
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
    public void showDataSuccess(LoginEntity datas) {
        SharedPreferencesUtils.setParam(Constant.HASH, datas.getHash());
        ToastUtils.showShort("hash:" + datas.getHash());
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
        String usernames = username.getText().toString();
        String passwords = password.getText().toString();
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
            loginTv.setVisibility(View.GONE);
            progressLogin.setVisibility(View.VISIBLE);
            AddAndSubEditText.isLoading=true;
        }else {
            loginTv.setVisibility(View.VISIBLE);
            progressLogin.setVisibility(View.GONE);
            AddAndSubEditText.isLoading=false;
        }
    }


}
