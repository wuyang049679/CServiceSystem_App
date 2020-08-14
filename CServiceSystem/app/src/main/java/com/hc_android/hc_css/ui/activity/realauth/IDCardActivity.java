package com.hc_android.hc_css.ui.activity.realauth;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.alibaba.security.realidentity.ALRealIdentityCallback;
import com.alibaba.security.realidentity.ALRealIdentityResult;
import com.alibaba.security.realidentity.RPEventListener;
import com.alibaba.security.realidentity.RPResult;
import com.alibaba.security.realidentity.RPVerify;
import com.hc_android.hc_css.R;
import com.hc_android.hc_css.base.BaseActivity;
import com.hc_android.hc_css.base.BaseApplication;
import com.hc_android.hc_css.contract.RealAuthenActivityContract;
import com.hc_android.hc_css.entity.LoginEntity;
import com.hc_android.hc_css.entity.UserEntity;
import com.hc_android.hc_css.entity.VerityEntity;
import com.hc_android.hc_css.entity.VerityResultEntity;
import com.hc_android.hc_css.presenter.RealAuthenActivityPresenter;
import com.hc_android.hc_css.ui.activity.MainActivity;
import com.hc_android.hc_css.utils.DateUtils;
import com.hc_android.hc_css.utils.NullUtils;
import com.hc_android.hc_css.utils.ValidateUtils;
import com.hc_android.hc_css.utils.android.ToastUtils;
import com.hc_android.hc_css.wight.AddAndSubEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IDCardActivity extends BaseActivity <RealAuthenActivityPresenter, VerityEntity.DataBean> implements RealAuthenActivityContract.View {


    @BindView(R.id.backImg)
    ImageView backImg;
    @BindView(R.id.msg_count_tv)
    TextView msgCountTv;
    @BindView(R.id.title_real_authen)
    TextView titleRealAuthen;
    @BindView(R.id.act_real_authen)
    ConstraintLayout actRealAuthen;
    @BindView(R.id.name_id)
    TextView nameId;
    @BindView(R.id.id_card_name)
    AddAndSubEditText idCardName;
    @BindView(R.id.card_id)
    TextView cardId;
    @BindView(R.id.id_card_num)
    AddAndSubEditText idCardNum;
    @BindView(R.id.next_b)
    TextView nextB;

    private static final int PHONE_VER=1;
    private static final int FACE_VER=2;
    private String bizId;
    String realtype = "personal";//默认为个人认证

    @Override
    public RealAuthenActivityPresenter getPresenter() {
        return new RealAuthenActivityPresenter();
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
        if (title!=null)titleRealAuthen.setText(title);
        String owner = getIntent().getStringExtra("_OWNER");
        if (owner !=null){
            realtype = "legalperson"; //企业法人认证
            idCardName.setText(owner);
            idCardName.setFocusableInTouchMode(false);
            idCardName.setKeyListener(null);
            idCardName.setClickable(false);
            idCardName.setEnabled(false);
            showInputTips(idCardNum);
        }else {
            showInputTips(idCardName);
        }
    }

    @Override
    protected void initView() {

        RPVerify.init(this);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_idcard;
    }

    @Override
    public void showDataSuccess(VerityEntity.DataBean datas) {
        bizId=datas.getBizId();
        Log.i(TAG,"VerityEntity:"+datas.getResult().getVerifyToken());
        if (datas.getResult().getVerifyToken()!=null) {
            try {

                RPVerify.start(IDCardActivity.this, datas.getResult().getVerifyToken(), new RPEventListener() {
                    @Override
                    public void onFinish(RPResult auditResult, String s, String s1) {
                        if (auditResult == RPResult.AUDIT_PASS) {
                            // 认证通过。建议接入方调用实名认证服务端接口DescribeVerifyResult来获取最终的认证状态，并以此为准进行业务上的判断和处理。
                            // do something
                            mPresenter.pVerityResult(realtype,"RPBioOnly",bizId);
                        } else if (auditResult == RPResult.AUDIT_FAIL) {
                            // 认证不通过。建议接入方调用实名认证服务端接口DescribeVerifyResult来获取最终的认证状态，并以此为准进行业务上的判断和处理。
                            // do something
                        } else if (auditResult == RPResult.AUDIT_NOT) {
                            // 未认证，具体原因可通过code来区分（code取值参见错误码说明），通常是用户主动退出或者姓名身份证号实名校验不匹配等原因，导致未完成认证流程。
                            // do something
                        }
                    }
                });
            }catch (Exception e){
                ToastUtils.showShort("实名认证失败，请重新认证"+e);
            }
        }else {
            ToastUtils.showShort("实名认证失败，请重新认证");
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
                String name = idCardName.getText().toString();
                String idcard = idCardNum.getText().toString();
                if (NullUtils.isNull(name)){ToastUtils.showShort("请输入姓名");return;}
                if (NullUtils.isNull(idcard)){ToastUtils.showShort("请输入身份证号码");return;}
                if (!ValidateUtils.isRealName(name)||name.length()>10){ToastUtils.showShort("请输入正确的姓名");return;}
                if (!ValidateUtils.isIDCard(idcard)){ToastUtils.showShort("请输入正确的身份证号码");return;}
                if (DateUtils.isChildUnderTargetAge(idcard,18,true)){ToastUtils.showShort("未满18周岁无法完成实名认证");return;}
                mPresenter.pVerityToken("RPBioOnly",name,idcard);

                break;
        }
    }

    /**
     * 基础回调的方式 TODO
     *
     * @return
     */
    private ALRealIdentityCallback getALRealIdentityCallback() {
        return new ALRealIdentityCallback() {
            @Override
            public void onAuditResult(ALRealIdentityResult alRealIdentityResult, String s) {
                //DO your things
                Log.d("RPSDK","ALRealIdentityResult:"+alRealIdentityResult.audit+"认证结果："+s+"bizId:"+bizId);
                if (alRealIdentityResult.audit==1){
//                    mPresenter.pVerityResult(bizId);
                }else {
                    ToastUtils.showShort("实名认证失败，请重新认证");
                }
            }
        };
    }

    @Override
    public void showResult(VerityResultEntity.DataBean dataBean) {
        if (dataBean.getResult()!=null&&dataBean.getResult().equals("success")){
            LoginEntity.DataBean.InfoBean userBean = BaseApplication.getUserBean();
            if (userBean.getCompany().getRealNameAuth()!=null){
            userBean.getCompany().getRealNameAuth().setComplestatus("complete");
            if (userBean.getCompany().getRealNameAuth().getRemittance()!=null){
                userBean.getCompany().getRealNameAuth().getRemittance().setState("checked");
            }else {
                userBean.getCompany().getRealNameAuth().setRemittance(new LoginEntity.DataBean.InfoBean.CompanyBean.RealNameAuthBean.RemittanceBean("checked"));
            }
            userBean.getCompany().getRealNameAuth().setType(realtype);
            }
            UserEntity userEntity=BaseApplication.getUserEntity();
            userEntity.setUserbean(userBean);
            BaseApplication.setUserEntity(userEntity);
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("_ACTION","FRESH");
            startActivity(intent);
            finish();
        }else {
            ToastUtils.showShort("实名认证失败，请重新认证");
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
