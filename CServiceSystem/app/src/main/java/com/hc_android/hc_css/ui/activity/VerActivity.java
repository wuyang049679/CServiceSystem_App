package com.hc_android.hc_css.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.hc_android.hc_css.R;
import com.hc_android.hc_css.base.BaseActivity;
import com.hc_android.hc_css.contract.BindActivityContract;
import com.hc_android.hc_css.entity.IneValuateEntity;
import com.hc_android.hc_css.presenter.BindActivityPresenter;

public class VerActivity extends BaseActivity  {


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

    }

    @Override
    public int getContentView() {
        return R.layout.activity_ver;
    }

    @Override
    public void showDataSuccess(Object datas) {

    }


}