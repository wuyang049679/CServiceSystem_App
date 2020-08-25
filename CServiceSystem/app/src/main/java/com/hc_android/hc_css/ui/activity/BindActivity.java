package com.hc_android.hc_css.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.hc_android.hc_css.R;
import com.hc_android.hc_css.base.BaseActivity;
import com.hc_android.hc_css.base.BaseApplication;
import com.hc_android.hc_css.contract.BindActivityContract;
import com.hc_android.hc_css.entity.IneValuateEntity;
import com.hc_android.hc_css.entity.LoginEntity;
import com.hc_android.hc_css.entity.UserEntity;
import com.hc_android.hc_css.entity.VerityResultEntity;
import com.hc_android.hc_css.presenter.BindActivityPresenter;
import com.hc_android.hc_css.utils.Constant;
import com.hc_android.hc_css.utils.android.ToastUtils;
import com.hc_android.hc_css.wight.ChoiceDialog;
import com.hc_android.hc_css.wight.botton.CountdownButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BindActivity extends BaseActivity<BindActivityPresenter, IneValuateEntity.DataBean> implements BindActivityContract.View {


    @BindView(R.id.cancel)
    TextView cancel;
    @BindView(R.id.title_act_update)
    TextView titleActUpdate;
    @BindView(R.id.update_save)
    TextView updateSave;
    @BindView(R.id.act_update)
    ConstraintLayout actUpdate;
    @BindView(R.id.update_et)
    EditText updateEt;
    @BindView(R.id.update_code)
    EditText updateCode;
    @BindView(R.id.cdb_register_timer)
    CountdownButton cdbRegisterTimer;
    @BindView(R.id.lin_bin)
    LinearLayout linBin;
    private String title;

    private final String WECHAt_TYPE = "wechat";
    private final String WECHAt_tel = "tel";
    private final String WECHAt_email = "email";
    @Override
    public BindActivityPresenter getPresenter() {
        return new BindActivityPresenter();
    }

    @Override
    protected void reloadActivity() {

    }

    @Override
    protected View injectTarget() {
        return findViewById(R.id.lin_bin);
    }

    @Override
    protected void initData() {

        cdbRegisterTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String et = updateEt.getText().toString();
                if (TextUtils.isEmpty(et)){
                    cdbRegisterTimer.stop();
                    ToastUtils.showShort("您的输入不能为空");
                    return;
                }
                if (title.equals("手机号")) {
                    mPresenter.pVerification(et, null);
                }else {
                    mPresenter.pVerification(null, et);
                }
            }
        });
    }

    @Override
    protected void initView() {
        Bundle extras = getIntent().getExtras();
        title = extras.getString(Constant._TITLE);
        titleActUpdate.setText("绑定" + title);
        updateEt.setHint("请输入" + title);

    }

    @Override
    public int getContentView() {
        return R.layout.activity_bind;
    }

    @Override
    public void showDataSuccess(IneValuateEntity.DataBean datas) {
        if (datas.isExistence()){
        cdbRegisterTimer.stop();
            new ChoiceDialog(this, "您填写的" + title +"已注册" , 1);
        }else {
            String et = updateEt.getText().toString();
            if (title.equals("手机号")) {
                mPresenter.pVercode(et, null,"bind",null);
            }else {
                mPresenter.pVercode(null, et,"bind",null);
            }
        }
    }


    @OnClick({R.id.cancel, R.id.update_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cancel:
                finish();
                break;
            case R.id.update_save:
                String tel = updateEt.getText().toString().trim();
                String code = updateCode.getText().toString().trim();
                if (TextUtils.isEmpty(tel)){
                    ToastUtils.showShort("请输入账号");
                    return;
                }
                if (TextUtils.isEmpty(code)){
                    ToastUtils.showShort("请输入验证码");
                    return;
                }
                if (title.equals("手机号")) {
                    mPresenter.pBind(tel,null,code);
                }else {
                    mPresenter.pBind(null,tel,code);

                }
                showLoadingView("全局");
                break;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void showRelievebindSuccess(IneValuateEntity.DataBean dataBean) {

    }

    @Override
    public void showEroor(int code, String msg) {
        cdbRegisterTimer.stop();
        if (code == 10005)msg = "请输入正确的手机号码或邮箱";
        ToastUtils.showShort(msg);
    }

    @Override
    public void showBindSuccess(IneValuateEntity.DataBean dataBean) {
        hideLoading();
        if (dataBean.get_suc() == 1) {
            String s = updateEt.getText().toString();
            LoginEntity.DataBean.InfoBean userBean = BaseApplication.getUserBean();
            if (title.equals("手机号")) {
                userBean.setTel(s);
            } else {
                userBean.setEmail(s);
            }
            UserEntity userEntity = BaseApplication.getUserEntity();
            userEntity.setUserbean(userBean);
            BaseApplication.setUserEntity(userEntity);

            ChoiceDialog choiceDialog = new ChoiceDialog(this, "绑定成功", 1);
            choiceDialog.setCancelCallBack(new ChoiceDialog.ChoiceCancelCallBack() {
                @Override
                public void cancelBack() {

                }

                @Override
                public void okBack(String msg) {
                    Intent intent = new Intent();
                    intent.putExtra("_CONTENT_TEXT", s);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });


        }else {
            if (dataBean.getText()!=null){
                new ChoiceDialog(this, dataBean.getText(), 1);
            }
        }
    }

    @Override
    public void showVercode(IneValuateEntity.DataBean dataBean) {

    }

    @Override
    public void showRegisiterSuccess(IneValuateEntity.DataBean dataBean) {

    }

    @Override
    public void showRegisiterEroor(String msg) {

    }


}