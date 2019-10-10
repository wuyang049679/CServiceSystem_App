package com.hecong.cssystem.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.gyf.barlibrary.ImmersionBar;
import com.hecong.cssystem.R;
import com.hecong.cssystem.base.BaseActivity;

public class StartActivity extends BaseActivity {



    @Override
    protected void reloadActivity() {

    }

    @Override
    protected View injectTarget() {
        return null;
    }

    @Override
    protected void initData() {

        @SuppressLint("HandlerLeak") Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Intent intent = new Intent(StartActivity.this,LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.activity_open,R.anim.activity_close);
                finish();
            }
        };
        handler.sendEmptyMessageDelayed(1,2000);//延时发送，第二个参数跟的毫秒


    }

    @Override
    protected void initView() {

        ImmersionBar.with(this)
                .statusBarColor(R.color.white)
                .init();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_start;

    }

    @Override
    public void showDataSuccess(Object datas) {

    }
}
