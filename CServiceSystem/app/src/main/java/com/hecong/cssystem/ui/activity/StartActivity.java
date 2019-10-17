package com.hecong.cssystem.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.gyf.barlibrary.ImmersionBar;
import com.hecong.cssystem.R;
import com.hecong.cssystem.base.BaseActivity;
import com.hecong.cssystem.base.BaseApplication;
import com.hecong.cssystem.contract.StartActivityContract;
import com.hecong.cssystem.entity.LoginEntity;
import com.hecong.cssystem.presenter.StartActivityPresenter;
import com.hecong.cssystem.utils.Constant;
import com.hecong.cssystem.utils.android.SharedPreferencesUtils;

public class StartActivity extends BaseActivity<StartActivityPresenter, LoginEntity.DataBean> implements StartActivityContract.View {


    private String hash;

    @Override
    public StartActivityPresenter getPresenter() {
        return new StartActivityPresenter();
    }

    @Override
    protected void reloadActivity() {

    }

    @Override
    protected View injectTarget() {
        return findViewById(R.id.act_start_lin);
    }

    @Override
    protected void initData() {
        //判断本地是否有hash来验证是否登录过
        hash = (String) SharedPreferencesUtils.getParam(Constant.HASH, "");
        if (TextUtils.isEmpty(hash)) {
            showDataError("请登录");
        } else {
            BaseApplication.setHash(hash);
            startActivity(MainActivity.class);
            finish();
        }

    }

    @Override
    protected void initView() {
        //沉浸式状态栏
        ImmersionBar.with(this)
                .statusBarColor(R.color.white)
                .init();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_start;

    }


    @Override
    public void showDataError(String errorMessage) {
        Intent intent = new Intent(StartActivity.this, LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.activity_open, R.anim.activity_close);
        finish();
    }



    @Override
    public void showDataSuccess(LoginEntity.DataBean datas) {

    }
}
