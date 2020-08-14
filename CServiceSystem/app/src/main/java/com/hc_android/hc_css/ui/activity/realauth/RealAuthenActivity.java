package com.hc_android.hc_css.ui.activity.realauth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.alibaba.security.cloud.CloudRealIdentityTrigger;
import com.alibaba.security.realidentity.ALRealIdentityCallback;
import com.alibaba.security.realidentity.ALRealIdentityResult;
import com.hc_android.hc_css.R;
import com.hc_android.hc_css.base.BaseActivity;
import com.hc_android.hc_css.base.BaseApplication;
import com.hc_android.hc_css.contract.RealAuthenActivityContract;
import com.hc_android.hc_css.entity.LoginEntity;
import com.hc_android.hc_css.entity.MessageEntity;
import com.hc_android.hc_css.entity.UserEntity;
import com.hc_android.hc_css.entity.VerityEntity;
import com.hc_android.hc_css.entity.VerityResultEntity;
import com.hc_android.hc_css.presenter.RealAuthenActivityPresenter;
import com.hc_android.hc_css.utils.android.ToastUtils;
import com.hc_android.hc_css.utils.android.app.AppConfig;
import com.hc_android.hc_css.utils.socket.MessageEvent;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.hc_android.hc_css.utils.Constant.UI_FRESH;

/**
 * 实名认证
 */
public class RealAuthenActivity extends BaseActivity<RealAuthenActivityPresenter, VerityEntity.DataBean> implements RealAuthenActivityContract.View {

    @BindView(R.id.backImg)
    ImageView backImg;
    @BindView(R.id.msg_count_tv)
    TextView msgCountTv;
    @BindView(R.id.title_real_authen)
    TextView titleRealAuthen;
    @BindView(R.id.act_real_authen)
    ConstraintLayout actRealAuthen;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.yj_phone)
    ImageView yjPhone;
    @BindView(R.id.lin_phone)
    RelativeLayout linPhone;
    @BindView(R.id.face)
    TextView face;
    @BindView(R.id.tv_face)
    TextView tvFace;
    @BindView(R.id.yj)
    ImageView yj;
    @BindView(R.id.lin_face)
    RelativeLayout linFace;

    private static final int PHONE_VER=1;
    private static final int FACE_VER=2;
    private String bizId;

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

        setMsgCount();
    }

    @Override
    protected void initView() {

        // 初始化实名认证SDK。
        CloudRealIdentityTrigger.initialize(this);//CloudRealIdentityTrigger
        LoginEntity.DataBean.InfoBean userBean = BaseApplication.getUserBean();
        if (false){
            tvPhone.setVisibility(View.VISIBLE);
            yjPhone.setVisibility(View.GONE);
        }
        if (userBean.getCompany()!=null&&userBean.getCompany().getRealNameAuth()!=null){
            if (userBean.getCompany().getRealNameAuth().getAuthId()!=null) {
                tvFace.setVisibility(View.VISIBLE);
                yj.setVisibility(View.GONE);
                linFace.setClickable(false);
                linFace.setEnabled(false);
            }
        }
    }

    @Override
    public int getContentView() {
        return R.layout.activity_real_authen;
    }

    @Override
    public void showDataSuccess(VerityEntity.DataBean datas) {

        bizId=datas.getBizId();
       if (datas.getResult().getVerifyToken()!=null) {
           try {
           CloudRealIdentityTrigger.start(RealAuthenActivity.this, datas.getResult().getVerifyToken(), getALRealIdentityCallback());
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case PHONE_VER:
                if (resultCode==RESULT_OK) {
                    tvPhone.setVisibility(View.VISIBLE);
                    yjPhone.setVisibility(View.GONE);
                    linPhone.setClickable(false);
                }
                break;
            case FACE_VER:
                if (resultCode==RESULT_OK) {
                    tvFace.setVisibility(View.VISIBLE);
                    yj.setVisibility(View.GONE);
                    linFace.setClickable(false);
                    linFace.setEnabled(false);
                }
                break;
        }
    }

    @OnClick({R.id.backImg, R.id.lin_phone, R.id.lin_face})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backImg:
                finish();
                break;
            case R.id.lin_phone:
                Bundle bundle1 = new Bundle();
                bundle1.putString("REAL_TYPE",phone.getText().toString());
                startActivity(CompanyRealActivity.class,bundle1);
                break;
            case R.id.lin_face:
//                mPresenter.pVerityToken(null,null,null);`
                Bundle bundle2 = new Bundle();
                bundle2.putString("REAL_TYPE",face.getText().toString());
                startActivity(IDCardActivity.class,bundle2);
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
                    mPresenter.pVerityResult(null,null,bizId);
                }else {
                    ToastUtils.showShort("实名认证失败，请重新认证");
                }
            }
        };
    }



    @Override
    public void onSocketEvent(MessageEvent message) {
        super.onSocketEvent(message);
        MessageEntity messageEntity= (MessageEntity) message.getMsg();
        switch (messageEntity.getAct()){

            case UI_FRESH:
                setMsgCount();
                break;
        }
    }
    /**
     * 设置未读数
     */
    private void setMsgCount() {
        int unReadCount = AppConfig.unReadCount;
        if (unReadCount==0){
            msgCountTv.setVisibility(View.GONE);
        }else {
            msgCountTv.setVisibility(View.VISIBLE);
            msgCountTv.setText(unReadCount+"");
        }
    }

    @Override
    public void showResult(VerityResultEntity.DataBean dataBean) {
        if (dataBean.getResult()!=null&&dataBean.getResult().equals("success")){
            LoginEntity.DataBean.InfoBean userBean = BaseApplication.getUserBean();
            userBean.getCompany().getRealNameAuth().setAuthId(bizId);
            userBean.getCompany().getRealNameAuth().setState(false);
            UserEntity userEntity=BaseApplication.getUserEntity();
            userEntity.setUserbean(userBean);
            BaseApplication.setUserEntity(userEntity);
            tvFace.setVisibility(View.VISIBLE);
            yj.setVisibility(View.GONE);
            linFace.setClickable(false);
            linFace.setEnabled(false);
        }else {
            ToastUtils.showShort("实名认证失败，请重新认证");
        }
    }

    /**
     * 结果查询
     */
    private void verResult() {

        if (bizId!=null){
            OkHttpClient okHttpClient = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("http://192.168.31.125:16998/app/verify/result?bizId = "+"cf3e0e9c171b25d86843de9de7acb24b")
                    .build();

            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(() -> ToastUtils.showShort(e.toString()));
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String string = response.body().string();
                    Log.i(TAG,string);
                }
            });
        }
    }
}
