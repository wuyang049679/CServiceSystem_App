package com.hc_android.hc_css.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.immersionbar.ImmersionBar;
import com.hc_android.hc_css.R;
import com.hc_android.hc_css.adapter.HistoryAdapter;
import com.hc_android.hc_css.base.BaseActivity;
import com.hc_android.hc_css.base.BaseApplication;
import com.hc_android.hc_css.contract.HistoryActivityContract;
import com.hc_android.hc_css.entity.ConditionEntity;
import com.hc_android.hc_css.entity.LoginEntity;
import com.hc_android.hc_css.entity.MessageDialogEntity;
import com.hc_android.hc_css.entity.MessageEntity;
import com.hc_android.hc_css.entity.ScreenSaveEntity;
import com.hc_android.hc_css.presenter.HistoryActivityPresenter;
import com.hc_android.hc_css.utils.Constant;
import com.hc_android.hc_css.utils.DateUtils;
import com.hc_android.hc_css.utils.JsonParseUtils;
import com.hc_android.hc_css.utils.NullUtils;
import com.hc_android.hc_css.utils.android.app.AppConfig;
import com.hc_android.hc_css.utils.android.app.DataProce;
import com.hc_android.hc_css.utils.socket.MessageEvent;
import com.hc_android.hc_css.wight.LocalDataSource;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.hc_android.hc_css.utils.Constant.UI_FRESH;

public class HistoryActivity extends BaseActivity<HistoryActivityPresenter, MessageDialogEntity.DataBean> implements HistoryActivityContract.View {


    @BindView(R.id.backImg)
    ImageView backImg;
    @BindView(R.id.act_history)
    ConstraintLayout actHistory;
    @BindView(R.id.history_recycler)
    RecyclerView historyRecycler;
    @BindView(R.id.history_smart)
    SmartRefreshLayout historySmart;
    @BindView(R.id.history_lin)
    LinearLayout historyLin;
    @BindView(R.id.tv_screen)
    TextView tvScreen;
    @BindView(R.id.msg_count_tv)
    TextView msgCountTv;
    private HistoryAdapter historyAdapter;
    private List<MessageDialogEntity.DataBean.ListBean> listBeanList;

    private int skip = 0;
    private final static int HISTORY_ACT = 10;
    private ScreenSaveEntity screen_save;
    private String condition;

    @Override
    protected void reloadActivity() {

    }

    @Override
    public HistoryActivityPresenter getPresenter() {
        return new HistoryActivityPresenter();
    }

    @Override
    protected View injectTarget() {
        return findViewById(R.id.history_lin);
    }

    @Override
    protected void initData() {
        showLoadingView();
        ScreenSaveEntity screen_save = new ScreenSaveEntity();
        screen_save.setServicename("全部成员");
        ConditionEntity conditionEntity = DataModify(screen_save);
        condition = JsonParseUtils.parseToJson(conditionEntity);
        mPresenter.pGetHistoryList(condition, 30, skip);
        LocalDataSource.setScreenList(null);
    }

    @Override
    protected void initView() {
        ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .keyboardEnable(true)
                .statusBarColor(R.color.theme_app)
                .titleBar(R.id.act_history)
                .init();
        listBeanList = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        historyRecycler.setLayoutManager(layoutManager);
        historyAdapter = new HistoryAdapter(listBeanList);
        historyRecycler.setAdapter(historyAdapter);
        historySmart.setEnableRefresh(false);
        historySmart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.pGetHistoryList(condition, 30, skip);
            }
        });
        historyAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                MessageDialogEntity.DataBean.ListBean item = (MessageDialogEntity.DataBean.ListBean) adapter.getData().get(position);
                LocalDataSource.setITEMBEAN(item);
                Bundle bundle = new Bundle();
                bundle.putString(Constant.INTENT_TYPE, Constant.HISTORYACT_);
                if (hasPermission()) {
                    startActivity(ChatActivity.class, bundle);
                }
            }
        });

        setMsgCount();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_history;
    }

    @Override
    public void showDataSuccess(MessageDialogEntity.DataBean datas) {
        showContentView();
        if (!NullUtils.isEmptyList(datas.getList())) {
            skip = skip + datas.getList().size();
            historyAdapter.addData(datas.getList());
            historySmart.finishLoadMore();
        } else {
            if (skip == 0) {
                showEmptyView("当前筛选没有对话");
            } else {
                historySmart.setNoMoreData(true);

            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.backImg, R.id.tv_screen})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backImg:
                finish();
                break;
            case R.id.tv_screen:
                startActivityForResult(ScreenActivity.class, HISTORY_ACT);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == HISTORY_ACT && data != null) {
            ScreenSaveEntity screen_save = (ScreenSaveEntity) data.getSerializableExtra("SCREEN_SAVE");
            ConditionEntity conditionEntity = DataModify(screen_save);
            condition = JsonParseUtils.parseToJson(conditionEntity);
            listBeanList.clear();
            skip = 0;
            historySmart.setNoMoreData(false);//重置加载完毕
            mPresenter.pGetHistoryList(condition, 30, skip);
        }
    }

    /**
     * 数据上传前修改
     *
     * @return
     */
    private ConditionEntity DataModify(ScreenSaveEntity saveEntity) {

        ConditionEntity conditionEntity = new ConditionEntity();
        ConditionEntity.ServiceBean serviceBean = new ConditionEntity.ServiceBean();
        serviceBean.setId(BaseApplication.getUserBean().getId());
        serviceBean.setName(BaseApplication.getUserBean().getName());
        conditionEntity.setService(serviceBean);
        long l = DateUtils.dateToUnixTimestamp(DateUtils.getCurrentDateBefore30Day(), "yyyy-MM-dd");
        conditionEntity.setStartTime(l + "");


        if (saveEntity != null) {

            //客服id和name
            if (!NullUtils.isNull(saveEntity.getId())) {
                serviceBean.setId(saveEntity.getId());
            }
            if (!NullUtils.isNull(saveEntity.getServicename())) {
                serviceBean.setName(saveEntity.getServicename());
                if (saveEntity.getServicename().equals("全部成员")){
                    serviceBean.setId(null);
                    LoginEntity.DataBean.InfoBean userBean = BaseApplication.getUserBean();
                    //判断是否开启对话协助权限
                    if (userBean != null && userBean.getAuthority() !=null && !userBean.getAuthority().isAssist()){
                    serviceBean.setId(userBean.getId());
                    }
                }
            }
            conditionEntity.setService(serviceBean);//设置客服信息

            //直接赋值不需要转换
            if (!NullUtils.isNull(saveEntity.getKeyWord()))
                conditionEntity.setKeyWord(saveEntity.getKeyWord());
            if (!NullUtils.isNull(saveEntity.getRemarks()))
                conditionEntity.setRemarks(saveEntity.getRemarks());
            if (!NullUtils.isNull(saveEntity.getIp())) conditionEntity.setIp(saveEntity.getIp());
            if (!NullUtils.isNull(saveEntity.getMsg())) conditionEntity.setMsg(saveEntity.getMsg());
            if (!NullUtils.isNull(saveEntity.getVague()))
                conditionEntity.setVague(saveEntity.getVague());
            if (!NullUtils.isNull(saveEntity.getMiss()))
                conditionEntity.setMiss(DataProce.getStringParams(saveEntity.getMiss(), Constant.MISS_PARAMS));
            if (!NullUtils.isNull(saveEntity.getInvalid()))
                conditionEntity.setInvalid(DataProce.getStringParams(saveEntity.getInvalid(), Constant.INVALID_PARAMS));

            //数组转换
            if (!NullUtils.isNull(saveEntity.getTag())) { //标签
                List<String> tagList = new ArrayList<>();
                String[] split = saveEntity.getTag().split("-");
                if (split != null) {
                    for (int i = 0; i < split.length; i++) {
                        tagList.add(split[i]);
                    }
                } else {
                    tagList.add(saveEntity.getTag());
                }
                conditionEntity.setTag(tagList);
            }
            if (!NullUtils.isNull(saveEntity.getEvaluate())) { //评价
                List<String> strList = new ArrayList<>();
                String[] split = saveEntity.getEvaluate().split("-");
                if (split != null) {
                    for (int i = 0; i < split.length; i++) {
                        strList.add(DataProce.getStringParams(split[i], Constant.EVALUATE_PARAMS));
                    }
                } else {
                    strList.add(saveEntity.getEvaluate());
                }
                conditionEntity.setEvaluate(strList);
            }
            if (!NullUtils.isNull(saveEntity.getSource())) { //接入方式
                List<String> strList = new ArrayList<>();
                String[] split = saveEntity.getSource().split("-");
                if (split != null) {
                    for (int i = 0; i < split.length; i++) {
                        strList.add(DataProce.getStringParams(split[i], Constant.SOURCE_PARAMS));
                    }
                } else {
                    strList.add(saveEntity.getSource());
                }
                conditionEntity.setSource(strList);
            }

            //设置起止时间
            if (!NullUtils.isNull(saveEntity.getStartDate()) && !NullUtils.isNull(saveEntity.getStartTime())) {
                //将上下午时间转成时间戳
                long stringDate = DataProce.getStringDate(saveEntity.getStartDate(), saveEntity.getStartTime());
                if (stringDate != 0) conditionEntity.setStartTime(stringDate + "");
            }

            if (!NullUtils.isNull(saveEntity.getEndDate()) && !NullUtils.isNull(saveEntity.getEndTime())) {
                //将上下午时间转成时间戳
                long stringDate = DataProce.getStringDate(saveEntity.getEndDate(), saveEntity.getEndTime());
                if (stringDate != 0) conditionEntity.setEndTime(stringDate + "");
            }
            if (!NullUtils.isNull(saveEntity.getEndAdate()) && !NullUtils.isNull(saveEntity.getEndAtime())) {
                //将上下午时间转成时间戳
                long stringDate = DataProce.getStringDate(saveEntity.getEndAdate(), saveEntity.getEndAtime());
                if (stringDate != 0) conditionEntity.setEndAtime(stringDate + "");
            }
            if (!NullUtils.isNull(saveEntity.getEndBdate()) && !NullUtils.isNull(saveEntity.getEndBtime())) {
                //将上下午时间转成时间戳
                long stringDate = DataProce.getStringDate(saveEntity.getEndBdate(), saveEntity.getEndBtime());
                if (stringDate != 0) conditionEntity.setEndBtime(stringDate + "");
            }
        }
        return conditionEntity;
    }

    @Override
    public void onSocketEvent(MessageEvent message) {
        super.onSocketEvent(message);
        MessageEntity messageEntity= (MessageEntity) message.getMsg();
        switch (messageEntity.getAct()){

            case UI_FRESH:
                setMsgCount();
                break;
        }
    }

    /**
     * 设置未读数
     */
    private void setMsgCount() {
        int unReadCount = AppConfig.unReadCount;
        if (unReadCount==0){
            msgCountTv.setVisibility(View.GONE);
        }else {
            msgCountTv.setVisibility(View.VISIBLE);
            msgCountTv.setText(unReadCount+"");
        }
    }
}
