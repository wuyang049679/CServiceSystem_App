package com.hecong.cssystem.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.barlibrary.ImmersionBar;
import com.hecong.cssystem.R;
import com.hecong.cssystem.adapter.DialogListAdapter;
import com.hecong.cssystem.adapter.PopWindowListAdapter;
import com.hecong.cssystem.base.BaseActivity;
import com.hecong.cssystem.contract.ColleagueActivityContract;
import com.hecong.cssystem.entity.MessageDialogEntity;
import com.hecong.cssystem.entity.TeamEntity;
import com.hecong.cssystem.presenter.ColleagueActivityPresenter;
import com.hecong.cssystem.utils.Constant;
import com.hecong.cssystem.wight.CustomDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ColleagueActivity extends BaseActivity<ColleagueActivityPresenter,TeamEntity.DataBean> implements ColleagueActivityContract.View {


    @BindView(R.id.colleague_recycler)
    RecyclerView colleagueRecycler;
    @BindView(R.id.backImg)
    ImageView backImg;
    @BindView(R.id.btn_choose)
    TextView btnChoose;
    private List<MessageDialogEntity.DataBean.ListBean> colleagueListBean;
    private List<MessageDialogEntity.DataBean.ListBean> colleagueListBeans;
    private DialogListAdapter listAdapter;
    private CustomDialog customDialog;
    private PopWindowListAdapter popWindowListAdapter;


    @Override
    public ColleagueActivityPresenter getPresenter() {
        return new ColleagueActivityPresenter();
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
                .titleBar(R.id.colleague_include)
                .init();
        backImg.setImageResource(R.mipmap.back);

        colleagueListBean = (ArrayList<MessageDialogEntity.DataBean.ListBean>) getIntent().getSerializableExtra(Constant.COLLEAGUE_LIST);
        for (MessageDialogEntity.DataBean.ListBean listBean : colleagueListBean) {
            listBean.setItemtype(Constant.COLLEAGUE_ACT);
        }
        colleagueListBeans=new ArrayList<>();
        colleagueListBeans.addAll(colleagueListBean);
        if (colleagueListBean != null) {
//            conmonTitleTextView.setText(getResources().getString(R.string.dialog_team)+"("+colleagueListBean.size()+")");
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            colleagueRecycler.setLayoutManager(layoutManager);
            listAdapter = new DialogListAdapter(colleagueListBeans);
            colleagueRecycler.setAdapter(listAdapter);
        }
    }

    @Override
    public int getContentView() {
        return R.layout.activity_colleague;
    }

    @Override
    public void showDataSuccess(TeamEntity.DataBean datas) {
        createDiaLog(datas);
    }


    @OnClick({R.id.backImg,R.id.btn_choose})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.backImg:
                finish();
                break;
            case R.id.btn_choose:
                mPresenter.pGetTeamList();
                break;
        }
    }

    /**
     * 弹出式对话框
     * @param datas
     */
    private void createDiaLog(TeamEntity.DataBean datas) {
        popWindowListAdapter=new PopWindowListAdapter(datas.getList());
        CustomDialog.Builder builder=new CustomDialog.Builder(this);
        customDialog=builder.heightDimenRes(R.dimen.popWindow_height)
                .widthDimenRes(R.dimen.popWindow_witch)
                .view(R.layout.colleague_list_popwindow)
                .style(R.style.Dialog)
                .setRecyclerView(R.id.colleague_popwindow_recycler,popWindowListAdapter)
                .cancelTouchout(true)
                .addViewOnclick(R.id.all_team, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        colleagueListBeans.clear();
                        colleagueListBeans.addAll(colleagueListBean);
                        listAdapter.notifyDataSetChanged();
                        customDialog.dismiss();
                    }
                })
                .build();
            customDialog.show();

        popWindowListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                TeamEntity.DataBean.ListBean bean = (TeamEntity.DataBean.ListBean) adapter.getData().get(position);
                btnChoose.setText(bean.getNickname());
                customDialog.dismiss();
                String serviceId = bean.get_id();
                colleagueListBeans.clear();
                for (MessageDialogEntity.DataBean.ListBean listBean : colleagueListBean) {
                    if (listBean.getServiceId().equals(serviceId)){
                        colleagueListBeans.add(listBean);
                    }
                }
                listAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
