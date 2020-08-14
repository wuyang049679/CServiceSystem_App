package com.hc_android.hc_css.ui.activity.realauth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.hc_android.hc_css.R;
import com.hc_android.hc_css.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CompanyRealActivity extends BaseActivity {


    @BindView(R.id.backImg)
    ImageView backImg;
    @BindView(R.id.msg_count_tv)
    TextView msgCountTv;
    @BindView(R.id.title_real_authen)
    TextView titleRealAuthen;
    @BindView(R.id.act_real_authen)
    ConstraintLayout actRealAuthen;
    @BindView(R.id.face)
    TextView face;
    @BindView(R.id.tv_face)
    TextView tvFace;
    @BindView(R.id.yj)
    ImageView yj;
    @BindView(R.id.lin_face)
    RelativeLayout linFace;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.yj_phone)
    ImageView yjPhone;
    @BindView(R.id.lin_phone)
    RelativeLayout linPhone;

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

    }

    @Override
    public int getContentView() {
        return R.layout.activity_company_real;
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

    @OnClick({R.id.lin_face, R.id.lin_phone, R.id.backImg})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backImg:
                finish();
                break;
            case R.id.lin_phone:
                Intent intent = new Intent(CompanyRealActivity.this, BusinessActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                intent.putExtra("REAL_TYPE",phone.getText().toString());
                startActivity(intent);
                break;
            case R.id.lin_face:
                Intent intent2 = new Intent(CompanyRealActivity.this, BusinessActivity.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                intent2.putExtra("REAL_TYPE",face.getText().toString());
                startActivity(intent2);

                break;
        }
    }
}
