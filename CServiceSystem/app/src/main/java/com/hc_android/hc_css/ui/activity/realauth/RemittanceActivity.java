package com.hc_android.hc_css.ui.activity.realauth;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.hc_android.hc_css.R;
import com.hc_android.hc_css.base.BaseActivity;
import com.hc_android.hc_css.base.BaseApplication;
import com.hc_android.hc_css.contract.RemittanceActivityContract;
import com.hc_android.hc_css.entity.IneValuateEntity;
import com.hc_android.hc_css.entity.LoginEntity;
import com.hc_android.hc_css.entity.UserEntity;
import com.hc_android.hc_css.presenter.RemittanceActivityPresenter;
import com.hc_android.hc_css.utils.NullUtils;
import com.hc_android.hc_css.utils.ValidateUtils;
import com.hc_android.hc_css.utils.android.ToastUtils;
import com.hc_android.hc_css.wight.AddAndSubEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RemittanceActivity extends BaseActivity<RemittanceActivityPresenter, IneValuateEntity.DataBean> implements RemittanceActivityContract.View {


    @BindView(R.id.backImg)
    ImageView backImg;
    @BindView(R.id.msg_count_tv)
    TextView msgCountTv;
    @BindView(R.id.title_real_authen)
    TextView titleRealAuthen;
    @BindView(R.id.act_real_authen)
    ConstraintLayout actRealAuthen;
    @BindView(R.id.bank_num)
    TextView bankNum;
    @BindView(R.id.bank_num_et)
    AddAndSubEditText bankNumEt;
    @BindView(R.id.bank_name)
    TextView bankName;
    @BindView(R.id.bank_name_et)
    AddAndSubEditText bankNameEt;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.phone_et)
    AddAndSubEditText phoneEt;
    @BindView(R.id.next_b)
    TextView nextB;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.name_et)
    AddAndSubEditText nameEt;

    @Override
    public RemittanceActivityPresenter getPresenter() {
        return new RemittanceActivityPresenter();
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

        String title = getIntent().getStringExtra("REAL_TYPE");
        if (title != null) titleRealAuthen.setText(title);
        String name = getIntent().getStringExtra("_NAME");
        if (name != null) {
            nameEt.setText(name);
            nameEt.setFocusableInTouchMode(false);
            nameEt.setKeyListener(null);
            nameEt.setClickable(false);
            nameEt.setEnabled(false);
            showInputTips(bankNumEt);
        }
    }

    @Override
    protected void initView() {

    }

    @Override
    public int getContentView() {
        return R.layout.activity_remittance;
    }

    @Override
    public void showDataSuccess(IneValuateEntity.DataBean datas) {
        showContentView();
        if (datas.get_suc()==1){
            LoginEntity.DataBean.InfoBean userBean = BaseApplication.getUserBean();
            if (userBean.getCompany().getRealNameAuth()!=null){
                userBean.getCompany().getRealNameAuth().setComplestatus("loading");
                if (userBean.getCompany().getRealNameAuth().getRemittance()!=null){
                    userBean.getCompany().getRealNameAuth().getRemittance().setState("noremit");
                }else {
                    userBean.getCompany().getRealNameAuth().setRemittance(new LoginEntity.DataBean.InfoBean.CompanyBean.RealNameAuthBean.RemittanceBean("noremit"));
                }
            }
            UserEntity userEntity=BaseApplication.getUserEntity();
            userEntity.setUserbean(userBean);
            BaseApplication.setUserEntity(userEntity);
            startActivity(RemStateActivity.class);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.backImg, R.id.next_b})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backImg:
                finish();
                break;
            case R.id.next_b:
                String name = nameEt.getText().toString();
                String banknum = bankNumEt.getText().toString();
                String bankname =  bankNameEt.getText().toString();
                String phones = phoneEt.getText().toString();
                if (NullUtils.isNull(banknum)){
                    ToastUtils.showShort("请输入卡号");return;}
                if (NullUtils.isNull(bankname)){ToastUtils.showShort("请输入开户行名称");return;}
                if (!ValidateUtils.isBankNo(banknum)){ToastUtils.showShort("请输入正确的银行卡号");return;}
                if (!ValidateUtils.isPhone(phones)){ToastUtils.showShort("请输入正确的手机号");return;}
                mPresenter.pRealBankData(name,banknum,bankname, phones);
                showLoadingView("全局");
                break;
        }
    }

    private void showInputTips(EditText et_text) {
        et_text.setFocusable(true);
        et_text.setFocusableInTouchMode(true);
        et_text.requestFocus();
        InputMethodManager inputManager =
                (InputMethodManager) et_text.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(et_text, 0);
    }
}
