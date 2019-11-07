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
import com.hecong.cssystem.base.BaseApplication;
import com.hecong.cssystem.contract.NotReceivedActivityContract;
import com.hecong.cssystem.entity.MessageDialogEntity;
import com.hecong.cssystem.entity.ReceptionEntity;
import com.hecong.cssystem.presenter.NotReceivedActivityPresenter;
import com.hecong.cssystem.utils.Constant;
import com.hecong.cssystem.utils.android.ToastUtils;
import com.hecong.cssystem.wight.LocalDataSource;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotReceivedActivity extends BaseActivity<NotReceivedActivityPresenter, ReceptionEntity.DataBean>implements NotReceivedActivityContract.View {

    @BindView(R.id.backImg)
    ImageView backImg;
    @BindView(R.id.conmonTitleTextView)
    TextView conmonTitleTextView;
    @BindView(R.id.act_not_received_recycler)
    RecyclerView actNotReceivedRecycler;
    private List<MessageDialogEntity.DataBean.ListBean> notListBean;
    private DialogListAdapter listAdapter;
    private int POSITION;
    private int mCountAll;

    @Override
    public NotReceivedActivityPresenter getPresenter() {
        return new NotReceivedActivityPresenter();
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
        ImmersionBar.with(this)
                .statusBarColor(R.color.theme_app)
                .titleBar(R.id.not_received_include)
                .init();
        backImg.setImageResource(R.mipmap.back);
        conmonTitleTextView.setText(getResources().getString(R.string.dialog_no_received));
        notListBean= LocalDataSource.getNOTLIST();
        mCountAll=getIntent().getIntExtra(Constant.HAVERECEIVED_NUM,0);
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
        listAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            MessageDialogEntity.DataBean.ListBean item = (MessageDialogEntity.DataBean.ListBean) adapter.getData().get(position);
            POSITION=position;
            switch (view.getId()){
                case R.id.close_btn:
                    String idList=item.getId();
                    mPresenter.pEndDialog(idList,null,null);
                    break;
                case R.id.btn_jd:
                    if (mCountAll< BaseApplication.getUserEntity().getMaxChat()) {
                        mPresenter.pReceptionDialog(notListBean.get(position).getId());
                    }else {
                        ToastUtils.showShort("同时对话已经数达到最大值，请先处理当前对话");
                    }
                    break;
            }
        });
    }

    @Override
    public int getContentView() {
        return R.layout.activity_not_received;
    }

    @Override
    public void showDataSuccess(ReceptionEntity.DataBean datas) {
        int suc = datas.get_suc();
        if (suc==0){
            ToastUtils.showShort("对话已被接待");
        }
        if (suc==1){
            notListBean.remove(POSITION);
            listAdapter.notifyDataSetChanged();
            conmonTitleTextView.setText(getResources().getString(R.string.dialog_no_received)+"("+notListBean.size()+")");
        }
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

    @Override
    public void showEndDialog(ReceptionEntity.DataBean messageEntity) {
        notListBean.remove(POSITION);
        listAdapter.notifyItemRemoved(POSITION);
    }
}
