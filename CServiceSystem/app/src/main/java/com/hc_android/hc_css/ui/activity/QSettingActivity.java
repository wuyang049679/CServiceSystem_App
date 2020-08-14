package com.hc_android.hc_css.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;

import com.gyf.immersionbar.ImmersionBar;
import com.hc_android.hc_css.R;
import com.hc_android.hc_css.base.BaseActivity;
import com.hc_android.hc_css.entity.QConfigEntity;
import com.hc_android.hc_css.utils.android.app.AppConfig;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QSettingActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {


    @BindView(R.id.qs_cancel)
    TextView qsCancel;
    @BindView(R.id.qs_save)
    TextView qsSave;
    @BindView(R.id.input_switch)
    SwitchCompat inputSwitch;
    @BindView(R.id.expand_switch)
    SwitchCompat expandSwitch;
    @BindView(R.id.count_switch)
    SwitchCompat countSwitch;
    @BindView(R.id.type_switch)
    SwitchCompat typeSwitch;
    @BindView(R.id.picker_type)
    LinearLayout pickerType;
    @BindView(R.id.visible_switch)
    SwitchCompat visibleSwitch;

    @Override
    protected void reloadActivity() {

    }

    @Override
    protected View injectTarget() {
        return null;
    }

    @Override
    protected void initData() {
        QConfigEntity configEntity = AppConfig.getConfigEntity();
        inputSwitch.setChecked(configEntity.isAddInput());
        expandSwitch.setChecked(configEntity.isFoldType());
        countSwitch.setChecked(configEntity.isUseCounts());
        typeSwitch.setChecked(configEntity.isOpenTypePick());
        visibleSwitch.setChecked(configEntity.isDisplayCounts());
    }

    @Override
    protected void initView() {
        ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .keyboardEnable(true)
                .statusBarColor(R.color.white)
                .init();

        countSwitch.setOnCheckedChangeListener(this);
        inputSwitch.setOnCheckedChangeListener(this);
        expandSwitch.setOnCheckedChangeListener(this);
        typeSwitch.setOnCheckedChangeListener(this);
        visibleSwitch.setOnCheckedChangeListener(this);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_qsetting;
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

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        switch (buttonView.getId()) {

            case R.id.input_switch:
                break;
            case R.id.expand_switch:
                if (isChecked){
                    pickerType.setVisibility(View.GONE);
                }else {
                    pickerType.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.count_switch:
                break;
            case R.id.type_switch:
                break;
            case R.id.visible_switch:
                break;
        }
    }

    @OnClick({R.id.qs_cancel, R.id.qs_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.qs_cancel:
                finish();
                break;
            case R.id.qs_save:
                QConfigEntity qConfigEntity = AppConfig.getConfigEntity();
                qConfigEntity.setAddInput(inputSwitch.isChecked());
                qConfigEntity.setFoldType(expandSwitch.isChecked());
                qConfigEntity.setUseCounts(countSwitch.isChecked());
                qConfigEntity.setOpenTypePick(typeSwitch.isChecked());
                qConfigEntity.setDisplayCounts(visibleSwitch.isChecked());
                AppConfig.setConfigEntity(qConfigEntity);
                finish();
                break;
        }
    }
}
