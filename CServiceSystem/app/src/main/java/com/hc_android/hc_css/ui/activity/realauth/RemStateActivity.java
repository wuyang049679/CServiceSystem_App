package com.hc_android.hc_css.ui.activity.realauth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.hc_android.hc_css.R;
import com.hc_android.hc_css.base.BaseActivity;
import com.hc_android.hc_css.base.BaseApplication;
import com.hc_android.hc_css.contract.RemStateActivityContract;
import com.hc_android.hc_css.entity.LoginEntity;
import com.hc_android.hc_css.entity.UserEntity;
import com.hc_android.hc_css.entity.VerityResultEntity;
import com.hc_android.hc_css.presenter.RemStateActivityPresenter;
import com.hc_android.hc_css.ui.activity.MainActivity;
import com.hc_android.hc_css.utils.android.ToastUtils;
import com.hc_android.hc_css.wight.AddAndSubEditText;
import com.hc_android.hc_css.wight.ChoiceDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RemStateActivity extends BaseActivity<RemStateActivityPresenter, VerityResultEntity.DataBean> implements RemStateActivityContract.View {


    @BindView(R.id.backImg)
    ImageView backImg;
    @BindView(R.id.msg_count_tv)
    TextView msgCountTv;
    @BindView(R.id.title_real_authen)
    TextView titleRealAuthen;
    @BindView(R.id.act_real_authen)
    ConstraintLayout actRealAuthen;
    @BindView(R.id.click_ok)
    TextView clickOk;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.phone_et)
    AddAndSubEditText phoneEt;
    @BindView(R.id.yuan)
    TextView yuan;
    @BindView(R.id.next_b)
    TextView nextB;
    @BindView(R.id.dhz_lin)
    LinearLayout dhzLin;
    @BindView(R.id.qr_lin)
    LinearLayout qrLin;
    private LoginEntity.DataBean.InfoBean userBean;


    @Override
    public RemStateActivityPresenter getPresenter() {
        return new RemStateActivityPresenter();
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

        userBean = BaseApplication.getUserBean();
        if (userBean.getCompany()!=null && userBean.getCompany().getRealNameAuth()!=null) {
            LoginEntity.DataBean.InfoBean.CompanyBean.RealNameAuthBean realNameAuth = userBean.getCompany().getRealNameAuth();
            if (realNameAuth.getComplestatus() != null && realNameAuth.getComplestatus().equals("loading") ) {
                dhzLin.setVisibility(View.VISIBLE);
                qrLin.setVisibility(View.GONE);
                if (realNameAuth.getRemittance()!=null &&realNameAuth.getRemittance().getState()!=null && realNameAuth.getRemittance().getState().equals("remited")){
                    dhzLin.setVisibility(View.GONE);
                    qrLin.setVisibility(View.VISIBLE);
                }
            }

        }

    }

    @Override
    public int getContentView() {
        return R.layout.activity_rem_state;
    }


    @Override
    public void showDataSuccess(VerityResultEntity.DataBean datas) {
        if (datas.getResult()!=null&&datas.getResult().equals("success")){
            LoginEntity.DataBean.InfoBean userBean = BaseApplication.getUserBean();
            if (userBean.getCompany().getRealNameAuth()!=null){
                userBean.getCompany().getRealNameAuth().setComplestatus("complete");
                if (userBean.getCompany().getRealNameAuth().getRemittance()!=null){
                    userBean.getCompany().getRealNameAuth().getRemittance().setState("checked");
                }else {
                    userBean.getCompany().getRealNameAuth().setRemittance(new LoginEntity.DataBean.InfoBean.CompanyBean.RealNameAuthBean.RemittanceBean("checked"));
                }
                userBean.getCompany().getRealNameAuth().setType("corporatebank");
            }
            UserEntity userEntity=BaseApplication.getUserEntity();
            userEntity.setUserbean(userBean);
            BaseApplication.setUserEntity(userEntity);

            ChoiceDialog choiceDialog = new ChoiceDialog(this, "您已完成企业对公打款认证", 1);
            choiceDialog.setCancelCallBack(new ChoiceDialog.ChoiceCancelCallBack() {
                @Override
                public void cancelBack() {

                }

                @Override
                public void okBack(String s) {
                    Intent intent = new Intent(RemStateActivity.this, MainActivity.class);
                    intent.putExtra("_ACTION","FRESH");
                    startActivity(intent);
                    finish();
                }
            });

        }else if (datas.getResult()!=null&&datas.getResult().equals("limittimes")){
            LoginEntity.DataBean.InfoBean userBean = BaseApplication.getUserBean();
            if (userBean.getCompany().getRealNameAuth()!=null){
                userBean.getCompany().getRealNameAuth().setComplestatus("nostart");
                if (userBean.getCompany().getRealNameAuth().getRemittance()!=null){
                    userBean.getCompany().getRealNameAuth().getRemittance().setState("noremit");
                }else {
                    userBean.getCompany().getRealNameAuth().setRemittance(new LoginEntity.DataBean.InfoBean.CompanyBean.RealNameAuthBean.RemittanceBean("noremit"));
                }
                userBean.getCompany().getRealNameAuth().setType("personal");
            }
            UserEntity userEntity=BaseApplication.getUserEntity();
            userEntity.setUserbean(userBean);
            BaseApplication.setUserEntity(userEntity);
             ChoiceDialog dialog= new ChoiceDialog(this, "您输入的汇款金额错误次数过多，请重新进行实名认证!", 1);
            dialog.setCancelCallBack(new ChoiceDialog.ChoiceCancelCallBack() {
                @Override
                public void cancelBack() {

                }

                @Override
                public void okBack(String s) {
                    Intent intent = new Intent(RemStateActivity.this, MainActivity.class);
                    intent.putExtra("_ACTION","FRESH");
                    startActivity(intent);
                    finish();
                }
            });
        }else {
          new ChoiceDialog(this, "您输入的汇款金额不正确，请核实后再输入", 1);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.click_ok, R.id.next_b, R.id.backImg})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backImg:
            case R.id.click_ok:
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("_ACTION","FRESH");
                startActivity(intent);
                finish();
                break;
            case R.id.next_b:
                String s = phoneEt.getText().toString();
                mPresenter.pRealConfirm(s);
                break;
        }
    }


}
