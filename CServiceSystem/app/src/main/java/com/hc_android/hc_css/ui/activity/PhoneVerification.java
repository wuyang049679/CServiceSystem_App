package com.hc_android.hc_css.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.alibaba.fastjson.JSON;
import com.gyf.immersionbar.ImmersionBar;
import com.hc_android.hc_css.R;
import com.hc_android.hc_css.base.BaseActivity;
import com.hc_android.hc_css.base.BaseApplication;
import com.hc_android.hc_css.contract.PhoneVerificationContract;
import com.hc_android.hc_css.entity.LoginEntity;
import com.hc_android.hc_css.entity.MessageEntity;
import com.hc_android.hc_css.entity.VerityEntity;
import com.hc_android.hc_css.presenter.PhoneVerificationPresenter;
import com.hc_android.hc_css.utils.ValidateUtils;
import com.hc_android.hc_css.utils.android.ToastUtils;
import com.hc_android.hc_css.utils.android.app.AppConfig;
import com.hc_android.hc_css.utils.socket.MessageEvent;
import com.mobile.auth.gatewayauth.PhoneNumberAuthHelper;
import com.mobile.auth.gatewayauth.TokenResultListener;
import com.mobile.auth.gatewayauth.model.TokenRet;

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

public class PhoneVerification extends BaseActivity<PhoneVerificationPresenter, VerityEntity.DataBean> implements PhoneVerificationContract.View {


    @BindView(R.id.backImg)
    ImageView backImg;
    @BindView(R.id.msg_count_tv)
    TextView msgCountTv;
    @BindView(R.id.title_phone_ver)
    TextView titlePhoneVer;
    @BindView(R.id.act_phone_ver)
    ConstraintLayout actPhoneVer;
    @BindView(R.id.phone_et)
    EditText phoneEt;
    @BindView(R.id.btn_ver)
    TextView btnVer;
    @BindView(R.id.btn_lin)
    LinearLayout btnLin;

    private PhoneNumberAuthHelper mAlicomAuthHelper;
    private TokenResultListener mTokenListener;
    private String token;
    private TimeCount timeCount;
    private static final int SERVICE_TYPE_AUTH = 1;//号码认证
    private String phone;


    @Override
    public PhoneVerificationPresenter getPresenter() {
        return new PhoneVerificationPresenter();
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
        LoginEntity.DataBean.InfoBean userBean = BaseApplication.getUserBean();
        if (userBean != null && userBean.getTel() != null) {
            phoneEt.setText(userBean.getTel());
        }
        setMsgCount();
    }

    @Override
    protected void initView() {
        ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .keyboardEnable(true)
                .statusBarColor(R.color.theme_app)
                .titleBar(R.id.act_phone_ver)
                .init();




        initVer();
    }

    private void initVer() {
        /*
         *   1.init get token callback Listener
         */
        mTokenListener = new TokenResultListener() {
            @Override
            public void onTokenSuccess(final String ret) {
                PhoneVerification.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Log.e("xxxxxx", "onTokenSuccess:" + ret);

                        /*
                         *   setText just show the result for get token。
                         *   use ret to verfiy number。
                         */

                        TokenRet tokenRet = null;
                        try {
                            tokenRet = JSON.parseObject(ret, TokenRet.class);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (tokenRet != null && ("600024").equals(tokenRet.getCode())) {
                            titlePhoneVer.setText("终端自检成功:\n" + ret);
                        }

                        if (tokenRet != null && ("600001").equals(tokenRet.getCode())) {
                            titlePhoneVer.setText("唤起授权页成功:\n" + ret);
                        }

                        if (tokenRet != null && ("600000").equals(tokenRet.getCode())) {
                            token = tokenRet.getToken();
//                            verifyMoblie(token);
                                mPresenter.pVerityPhone(phone,token);
                        }
                    }
                });
            }

            @Override
            public void onTokenFailed(final String ret) {
                Log.e("xxxxxx", "onTokenFailed:" + ret);
                PhoneVerification.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showShort(ret);
                    }
                });
            }
        };

        /*
         *   2.init AlicomAuthHelper with tokenListener
         */
        mAlicomAuthHelper = PhoneNumberAuthHelper.getInstance(this, mTokenListener);
        mAlicomAuthHelper.setAuthListener(mTokenListener);
        /*
         *   3.set debugMode when app is in debug mode, sdk will print log in debug mode
         */
        mAlicomAuthHelper.setLoggerEnable(true);
        mAlicomAuthHelper.setAuthSDKInfo("xXpi/ZOWRDqgr4ov2pfi1ecG8bwzTgkbK/zaL00Pij0FGOTUbHHUtTpON04RC3E2qQ8TSXVJk8mnwGRi12ko6KfdU2juAEBXAO+kGod1PLVH1Ro4YwdCBPPOq+G0UaVI+5wfhCO4WX0vbz1LHK4qHwBsREKmOdIgdJtOx3E1hSmpqvE3EoLtftpPfLcUhVEvLifJ5ceOOQz6js3+AakDuf9HzOF0+7FX36QEOyGwp9uITVgk9RFspLZPIpCX7YJaCg0cwSd6cl+eN1pYLuoE3DHKMMZOxBAE");
        /*
         *   使用一键登录传入 SERVICE_TYPE_LOGIN
         *   使用号码校验传入 SERVICE_TYPE_AUTH
         */
        mAlicomAuthHelper.checkEnvAvailable();

        timeCount = new TimeCount(30000, 1000);
        btnLin.setVisibility(View.VISIBLE);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_phone_verification;
    }

    @Override
    public void showDataSuccess(VerityEntity.DataBean datas) {
        Intent intent2 = new Intent();
        setResult(RESULT_OK, intent2);
        finish();
    }





    public void verifyMoblie(String token) {


        OkHttpClient okHttpClient = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://192.168.31.125:16998/app/verify?tel="+phone+"&token=" + token)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("xxxxxx", "onFailure:" + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Log.e("xxxxxx", "string:" + string);
                if (string!=null){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Intent intent2 = new Intent();
                            setResult(RESULT_OK, intent2);
                            finish();
                        }
                    });

                }
            }
        });
    }


    @OnClick({R.id.backImg, R.id.btn_ver})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backImg:
                finish();
                break;
            case R.id.btn_ver:

                phone = phoneEt.getText().toString();
                if (!ValidateUtils.isPhone(phone)){
                   ToastUtils.showShort("请输入正确的手机号码");
                    return ;
                }
                Log.e("xxxxxx", "点击:" + phone);
                timeCount.start();
                mAlicomAuthHelper.getVerifyToken(5000);

                break;

        }
    }




    public class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            btnVer.setBackground(getResources().getDrawable(R.drawable.login_btn_f));
            btnVer.setClickable(false);
            btnVer.setText("(" + millisUntilFinished / 1000 + ") 秒后可重新校验");
        }

        @Override
        public void onFinish() {
            btnVer.setText("点击校验");
            btnVer.setClickable(true);
            btnVer.setBackground(getResources().getDrawable(R.drawable.login_btn));
        }
    }

    @Override
    public void onSocketEvent(MessageEvent message) {
        super.onSocketEvent(message);
        MessageEntity messageEntity = (MessageEntity) message.getMsg();
        switch (messageEntity.getAct()) {

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
        if (unReadCount == 0) {
            msgCountTv.setVisibility(View.GONE);
        } else {
            msgCountTv.setVisibility(View.VISIBLE);
            msgCountTv.setText(unReadCount + "");
        }
    }


}
