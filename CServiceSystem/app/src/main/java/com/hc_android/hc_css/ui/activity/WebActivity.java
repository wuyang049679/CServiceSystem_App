package com.hc_android.hc_css.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.gyf.immersionbar.ImmersionBar;
import com.hc_android.hc_css.R;
import com.hc_android.hc_css.base.BaseActivity;
import com.hc_android.hc_css.entity.FileEntity;
import com.just.agentweb.AgentWeb;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebActivity extends BaseActivity {


    @BindView(R.id.web_lin)
    LinearLayout webLin;

    @Override
    protected void reloadActivity() {

    }

    @Override
    protected View injectTarget() {
        return null;
    }

    @Override
    protected void initData() {

        Intent intent = this.getIntent();
        FileEntity fileEntity = (FileEntity) intent.getSerializableExtra("_fileEntity");
        FileEntity.ListBean listBean = fileEntity.getList().get(0);
        AgentWeb.with(this)
                .setAgentWebParent(webLin, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .createAgentWeb()
                .ready()
                .go(listBean.getUrl());
    }

    @Override
    protected void initView() {
        ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .keyboardEnable(true)
                .statusBarColor(R.color.theme_app)
                .titleBar(R.id.web_lin)
                .init();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_web;
    }

    @Override
    public void showDataSuccess(Object datas) {

    }

    public static void show(Context context, FileEntity fileEntity) {
        Intent intent = new Intent(context, WebActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("_fileEntity", fileEntity);
        intent.putExtras(bundle);
        context.startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
