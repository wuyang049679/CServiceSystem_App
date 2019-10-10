package com.hecong.cssystem.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gyf.barlibrary.ImmersionBar;
import com.hecong.cssystem.R;
import com.hecong.cssystem.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity {


    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_chat_login)
    Button btnChatLogin;

    @Override
    protected void reloadActivity() {

    }

    @Override
    protected View injectTarget() {
        return null;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

        ImmersionBar.with(this)
                .statusBarColor(R.color.white)
                .init();
        initListener();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    public void showDataSuccess(Object datas) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    private void initListener() {
        TextChange textChange = new TextChange();
        username.addTextChangedListener(textChange);
        password.addTextChangedListener(textChange);
    }

    private class TextChange implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (username.getText().toString().length() > 0 && password.getText().toString().length() > 0) {
                btnLogin.setBackground(getResources().getDrawable(R.drawable.login_btn_t));
                btnLogin.setEnabled(true);
            } else {
                btnLogin.setBackground(getResources().getDrawable(R.drawable.login_btn_f));
                btnLogin.setEnabled(false);
            }
        }
    }

}
