package com.hc_android.hc_css.ui.activity.realauth;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.hc_android.hc_css.R;
import com.hc_android.hc_css.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DisclaimerActivity extends BaseActivity {


    @BindView(R.id.backImg)
    ImageView backImg;
    @BindView(R.id.msg_count_tv)
    TextView msgCountTv;
    @BindView(R.id.title_real_authen)
    TextView titleRealAuthen;
    @BindView(R.id.act_real_authen)
    ConstraintLayout actRealAuthen;
    @BindView(R.id.agreement_tv)
    TextView agreementTv;
    @BindView(R.id.agree_btn)
    TextView agreeBtn;
    private String title;

    @Override
    protected void reloadActivity() {

    }

    @Override
    protected View injectTarget() {
        return null;
    }

    @Override
    protected void initData() {

           SpannableString ss = new SpannableString(getResources().getString(R.string.agreement1));

           ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {

                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri targetUrl = Uri.parse("https://aihecong.com/agreement");
                intent.setData(targetUrl);
                startActivity(intent);
            }

               @Override
               public void updateDrawState(@NonNull TextPaint ds) {
                   super.updateDrawState(ds);
                   ds.setUnderlineText(false);
               }
           };
        ss.setSpan(clickableSpan,159,167,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        agreementTv.setText(ss);
        agreementTv.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    protected void initView() {

        title = getIntent().getStringExtra("REAL_TYPE");
    }

    @Override
    public int getContentView() {
        return R.layout.activity_disclaimer;
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

    @OnClick({R.id.backImg, R.id.agree_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backImg:
                finish();
                break;
            case R.id.agree_btn:
                Intent intent = new Intent(DisclaimerActivity.this, RealAuthenActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                intent.putExtra("REAL_TYPE",title);
                startActivity(intent);
                break;
        }
    }
}
