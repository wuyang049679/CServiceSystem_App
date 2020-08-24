package com.hc_android.hc_css.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.hc_android.hc_css.R;
import com.hc_android.hc_css.base.BaseActivity;
import com.hc_android.hc_css.contract.BindActivityContract;
import com.hc_android.hc_css.entity.FieldsEntity;
import com.hc_android.hc_css.entity.IneValuateEntity;
import com.hc_android.hc_css.presenter.BindActivityPresenter;
import com.hc_android.hc_css.utils.JsonParseUtils;
import com.hc_android.hc_css.utils.android.ToastUtils;
import com.hc_android.hc_css.utils.thread.ThreadUtils;
import com.hc_android.hc_css.wight.AddAndSubEditText;
import com.hc_android.hc_css.wight.ChoiceDialog;
import com.hc_android.hc_css.wight.botton.CountdownButton;
import com.hc_android.hc_css.wight.botton.LoadingButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity<BindActivityPresenter, IneValuateEntity.DataBean> implements BindActivityContract.View {


    @BindView(R.id.backImg)
    ImageView backImg;
    @BindView(R.id.title_act_chat_set)
    TextView titleActChatSet;
    @BindView(R.id.msg_count_tv)
    TextView msgCountTv;
    @BindView(R.id.act_chatSet)
    ConstraintLayout actChatSet;
    @BindView(R.id.tel)
    AddAndSubEditText tel;
    @BindView(R.id.update_code)
    EditText updateCode;
    @BindView(R.id.cdb_register_timer)
    CountdownButton cdbRegisterTimer;
    @BindView(R.id.username)
    AddAndSubEditText username;
    @BindView(R.id.password)
    AddAndSubEditText password;
    @BindView(R.id.tv_xy)
    TextView tvXy;
    @BindView(R.id.loadingBtn)
    LoadingButton loadingBtn;
    @BindView(R.id.act_lin)
    ConstraintLayout actLin;


    @Override
    public BindActivityPresenter getPresenter() {
        return new BindActivityPresenter();
    }

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

        loadingBtn.setText("立即注册");

    }

    @Override
    public int getContentView() {
        return R.layout.activity_register;
    }

    @Override
    public void showDataSuccess(IneValuateEntity.DataBean datas) {

        if (datas.isExistence()){
            cdbRegisterTimer.stop();
            new ChoiceDialog(this, "您填写的手机号已注册" , 1);
        }else {
            String et = tel.getText().toString();
             mPresenter.pVercode(et, null,"register","ecdfddd24937106ex");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.backImg, R.id.cdb_register_timer, R.id.tv_xy, R.id.loadingBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backImg:
                finish();
                break;
            case R.id.cdb_register_timer:
                String et = tel.getText().toString();
                if (TextUtils.isEmpty(et)){
                    ToastUtils.showShort("您输入的手机号不能为空");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                    cdbRegisterTimer.stop();
                        }
                    },800);
                    return;
                }
                mPresenter.pVerification(et, null);

                break;
            case R.id.tv_xy:
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri targetUrl = Uri.parse("https://aihecong.com/agreement");
                intent.setData(targetUrl);
                startActivity(intent);
                break;
            case R.id.loadingBtn:

                String hint = null;
                String pwd = password.getText().toString();
                String company = username.getText().toString();
                String code = updateCode.getText().toString();
                String tels = tel.getText().toString();
                if (TextUtils.isEmpty(pwd)) hint="请输入密码";
                if (TextUtils.isEmpty(company)) hint = "请输入公司名称";
                if (TextUtils.isEmpty(code)) hint = "请输入验证码";
                if (TextUtils.isEmpty(tels)) hint = "请输入手机号码";
                if (!TextUtils.isEmpty(pwd) && pwd.length() < 6 || pwd.length() > 18) hint = "请输入6至18位的字符密码";
                if (hint!=null){
                    ToastUtils.showShort(hint);
                    return;
                }
                FieldsEntity fieldsEntity = new FieldsEntity();
                fieldsEntity.setCode(code);
                fieldsEntity.setPassword(pwd);
                fieldsEntity.setCompany(company);
                fieldsEntity.setTel(tels);
                fieldsEntity.setType("register");
                fieldsEntity.setAntiBrush("ecdfddd24937106ex");
                String bean = JsonParseUtils.parseToJson(fieldsEntity);
                mPresenter.pRegisiter(bean);
                loadingBtn.start();
                break;
        }
    }

    @Override
    public void showRelievebindSuccess(IneValuateEntity.DataBean dataBean) {

    }

    @Override
    public void showEroor(String msg) {
        cdbRegisterTimer.stop();
        ToastUtils.showShort(msg);
    }

    @Override
    public void showBindSuccess(IneValuateEntity.DataBean dataBean) {

    }

    @Override
    public void showVercode(IneValuateEntity.DataBean dataBean) {
        if (dataBean.get_suc() == 0){
            if (dataBean.getText()!=null)ToastUtils.showShort(dataBean.getText());
            return;
        }
    }

    @Override
    public void showRegisiterSuccess(IneValuateEntity.DataBean dataBean) {
        loadingBtn.complete();
        if (dataBean.get_suc() == 0 && dataBean.getText()!=null){
            new ChoiceDialog(this, dataBean.getText() , 1);
            return;
        }
        if (dataBean.get_suc() == 1){
            new ChoiceDialog(this, "注册成功" , 1);
        }
    }

    @Override
    public void showRegisiterEroor(String msg) {
        loadingBtn.complete();
        ToastUtils.showShort(msg);
    }
}