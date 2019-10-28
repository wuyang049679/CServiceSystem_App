package com.hecong.cssystem.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gyf.barlibrary.ImmersionBar;
import com.hecong.cssystem.R;
import com.hecong.cssystem.adapter.DialogListAdapter;
import com.hecong.cssystem.base.BaseActivity;
import com.hecong.cssystem.entity.MessageDialogEntity;
import com.hecong.cssystem.utils.Constant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotReceivedActivity extends BaseActivity {

    @BindView(R.id.backImg)
    ImageView backImg;
    @BindView(R.id.conmonTitleTextView)
    TextView conmonTitleTextView;
    @BindView(R.id.act_not_received_recycler)
    RecyclerView actNotReceivedRecycler;
    private List<MessageDialogEntity.DataBean.ListBean> notListBean;
    private DialogListAdapter listAdapter;

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
        ImmersionBar.with(this)
                .statusBarColor(R.color.theme_app)
                .titleBar(R.id.not_received_include)
                .init();
        backImg.setImageResource(R.mipmap.back);
        conmonTitleTextView.setText(getResources().getString(R.string.dialog_no_received));
        notListBean= (ArrayList<MessageDialogEntity.DataBean.ListBean>) getIntent().getSerializableExtra(Constant.NOTRECEIVED_LIST);
        for (MessageDialogEntity.DataBean.ListBean listBean : notListBean) {
            listBean.setItemtype(Constant.NOTRECEIVED_ACT);
        }
        if (notListBean!=null) {
            conmonTitleTextView.setText(getResources().getString(R.string.dialog_no_received)+"("+notListBean.size()+")");
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            actNotReceivedRecycler.setLayoutManager(layoutManager);
            listAdapter = new DialogListAdapter(notListBean);
            actNotReceivedRecycler.setAdapter(listAdapter);
        }
    }

    @Override
    public int getContentView() {
        return R.layout.activity_not_received;
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

    @OnClick(R.id.backImg)
    public void onViewClicked(View v) {
        switch (v.getId()){
            case R.id.backImg:
                finish();
                break;
        }
    }
}
