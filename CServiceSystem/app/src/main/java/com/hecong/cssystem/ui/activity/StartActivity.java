package com.hecong.cssystem.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.gyf.barlibrary.ImmersionBar;
import com.hecong.cssystem.R;
import com.hecong.cssystem.base.BaseActivity;
import com.hecong.cssystem.contract.StartActivityContract;
import com.hecong.cssystem.entity.LoginEntity;
import com.hecong.cssystem.presenter.StartActivityPresenter;
import com.hecong.cssystem.utils.Constant;
import com.hecong.cssystem.utils.android.SharedPreferencesUtils;

public class StartActivity extends BaseActivity<StartActivityPresenter, LoginEntity> implements StartActivityContract.View {


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

       hash= (String) SharedPreferencesUtils.getParam(Constant.HASH,"");
        Log.i("TAG","首次：" +hash);
       if (hash!=null){
           mPresenter.pCheckLogin(hash);
       }else {
           showDataError("请登录");
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
    public void showDataSuccess(LoginEntity datas) {
        @SuppressLint("HandlerLeak") Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        };
        handler.sendEmptyMessageDelayed(1,500);//延时发送，第二个参数跟的毫秒
    }

    @Override
    public void showDataError(String errorMessage) {
        Intent intent = new Intent(StartActivity.this, LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.activity_open,R.anim.activity_close);
        finish();
    }
}
