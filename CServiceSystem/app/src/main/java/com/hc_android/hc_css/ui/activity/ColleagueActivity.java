package com.hc_android.hc_css.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.immersionbar.ImmersionBar;
import com.hc_android.hc_css.R;
import com.hc_android.hc_css.adapter.DialogListAdapter;
import com.hc_android.hc_css.adapter.PopWindowListAdapter;
import com.hc_android.hc_css.base.BaseActivity;
import com.hc_android.hc_css.base.BaseApplication;
import com.hc_android.hc_css.contract.ColleagueActivityContract;
import com.hc_android.hc_css.entity.MessageDialogEntity;
import com.hc_android.hc_css.entity.MessageEntity;
import com.hc_android.hc_css.entity.ReceptionEntity;
import com.hc_android.hc_css.entity.TeamEntity;
import com.hc_android.hc_css.presenter.ColleagueActivityPresenter;
import com.hc_android.hc_css.utils.Constant;
import com.hc_android.hc_css.utils.DateUtils;
import com.hc_android.hc_css.utils.FastUtils;
import com.hc_android.hc_css.utils.JsonParseUtils;
import com.hc_android.hc_css.utils.NullUtils;
import com.hc_android.hc_css.utils.android.app.AppConfig;
import com.hc_android.hc_css.utils.socket.MessageEvent;
import com.hc_android.hc_css.wight.CustomDialog;
import com.hc_android.hc_css.wight.LocalDataSource;
import com.hc_android.hc_css.wight.RecyclerViewNoBugLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.hc_android.hc_css.utils.Constant.EVENTBUS_NEWDIALOG;
import static com.hc_android.hc_css.utils.Constant.MESSAGE_INPUTING;
import static com.hc_android.hc_css.utils.Constant.MESSAGE_JOIN;
import static com.hc_android.hc_css.utils.Constant.MESSAGE_LEAVE;
import static com.hc_android.hc_css.utils.Constant.MESSAGE_LOGINSUC;
import static com.hc_android.hc_css.utils.Constant.MESSAGE_NEW;
import static com.hc_android.hc_css.utils.Constant.MESSAGE_NEWDIALOG;
import static com.hc_android.hc_css.utils.Constant.MESSAGE_OFFLINE;
import static com.hc_android.hc_css.utils.Constant.MESSAGE_ONLINE;
import static com.hc_android.hc_css.utils.Constant.MESSAGE_REALTIME_ADD;
import static com.hc_android.hc_css.utils.Constant.MESSAGE_REALTIME_DEL;
import static com.hc_android.hc_css.utils.Constant.MESSAGE_REALTIME_MODIFY;
import static com.hc_android.hc_css.utils.Constant.MESSAGE_RECEPTION;
import static com.hc_android.hc_css.utils.Constant.MESSAGE_REPORT_STATE;
import static com.hc_android.hc_css.utils.Constant.MESSAGE_SERVICELEAVE;
import static com.hc_android.hc_css.utils.Constant.MESSAGE_SERVICEONLY;
import static com.hc_android.hc_css.utils.Constant.MESSAGE_STATEUPATE;
import static com.hc_android.hc_css.utils.Constant.MESSAGE_UPATEDIALOG;
import static com.hc_android.hc_css.utils.Constant.UI_FRESH;
import static com.hc_android.hc_css.utils.socket.MessageEventType.EventMessage;

public class ColleagueActivity extends BaseActivity<ColleagueActivityPresenter, TeamEntity.DataBean> implements ColleagueActivityContract.View {


    @BindView(R.id.colleague_recycler)
    RecyclerView colleagueRecycler;
    @BindView(R.id.backImg)
    ImageView backImg;
    @BindView(R.id.title_colleague)
    TextView titleColleague;
    @BindView(R.id.msg_count_tv)
    TextView msgCountTv;
    @BindView(R.id.inject_target)
    LinearLayout injectTarget;
    private List<MessageDialogEntity.DataBean.ListBean> colleagueListBean;
    private List<MessageDialogEntity.DataBean.ListBean> colleagueListBeans;
    private DialogListAdapter listAdapter;
    private CustomDialog customDialog;
    private PopWindowListAdapter popWindowListAdapter;
    private int POSITION;
    private String serviceId;


    @Override
    public ColleagueActivityPresenter getPresenter() {
        return new ColleagueActivityPresenter();
    }

    @Override
    protected void reloadActivity() {

    }

    @Override
    protected View injectTarget() {
        return findViewById(R.id.inject_target);
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

        setMsgCount();
        colleagueListBean = LocalDataSource.getTEAMLIST();
        if (!NullUtils.isEmptyList(colleagueListBean)) {
            for (MessageDialogEntity.DataBean.ListBean listBean : colleagueListBean) {
                listBean.setItemtype(Constant.COLLEAGUE_ACT);
            }
        }
        colleagueListBeans = new ArrayList<>();
        colleagueListBeans.addAll(colleagueListBean);
        if (colleagueListBean != null) {
            RecyclerViewNoBugLinearLayoutManager layoutManager = new RecyclerViewNoBugLinearLayoutManager(this);
            colleagueRecycler.setLayoutManager(layoutManager);
            listAdapter = new DialogListAdapter(colleagueListBeans);
            colleagueRecycler.setAdapter(listAdapter);
            if (colleagueListBeans.size() == 0) {
                showEmptyView(getString(R.string.dialog_empty));
            }
        }

        listAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (adapter.getData()!=null&&adapter.getData().size()>position) {
                MessageDialogEntity.DataBean.ListBean bean = (MessageDialogEntity.DataBean.ListBean) adapter.getData().get(position);
                switch (view.getId()) {
                    case R.id.close_btn:
                        POSITION = position;
                        mPresenter.pEndDialog(bean.getId(), null, null);
                        break;
                    case R.id.lin_constrain:
                        LocalDataSource.setITEMBEAN(bean);
                        if (hasPermission()) {
                            startActivity(ChatActivity.class);
                        }
                        break;
                }
            }
        });
    }

    @Override
    public int getContentView() {
        return R.layout.activity_colleague;
    }

    @Override
    public void showDataSuccess(TeamEntity.DataBean datas) {
        createDiaLog(datas);
    }


    @OnClick({R.id.backImg, R.id.btn_choose})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.backImg:
                finish();
                break;
            case R.id.btn_choose:
                if (FastUtils.isFastClick()) {
                    mPresenter.pGetTeamList();
                }
                break;
        }
    }

    /**
     * 弹出式对话框
     *
     * @param datas
     */
    private void createDiaLog(TeamEntity.DataBean datas) {
        List<TeamEntity.DataBean.ListBean> list = datas.getList();
        for (TeamEntity.DataBean.ListBean bean : list) {
            bean.setShowNum(true);
            int count = 0;//统计每个同伴的对话数量比较serviceId
            String id = bean.get_id();
            for (MessageDialogEntity.DataBean.ListBean listBean : colleagueListBean) {
                if (id.equals(listBean.getServiceId())) {
                    count++;
                    bean.setDialogCount(count);
                }
            }
        }
        for (int i = 0; i < list.size(); i++) {//移除管理员
            if (BaseApplication.getUserBean().getId().equals(datas.getList().get(i).get_id())) {
                datas.getList().remove(i);
                break;
            }
        }

        popWindowListAdapter = new PopWindowListAdapter(list);
        CustomDialog.Builder builder = new CustomDialog.Builder(this);
        //点击全部的分类操作
        customDialog = builder.heightDimenRes(R.dimen.popWindow_height)
                .widthDimenRes(R.dimen.popWindow_witch)
                .view(R.layout.colleague_list_popwindow)
                .style(R.style.Dialog)
                .setRecyclerView(R.id.colleague_popwindow_recycler, popWindowListAdapter)
                .cancelTouchout(true)
                .addViewOnclick(R.id.all_team, view -> {  //点击全部的时候全部显示
                    colleagueListBeans.clear();
                    listAdapter.notifyItemRangeChanged(0,colleagueListBeans.size());//防止java.lang.IndexOutOfBoundsException: Inconsistency detected.
                    colleagueListBeans.addAll(colleagueListBean);
                    listAdapter.notifyDataSetChanged();
                    customDialog.dismiss();
                    titleColleague.setText(getResources().getString(R.string.dialog_all));
                    if (colleagueListBeans.size() == 0) {
                        showEmptyView(getString(R.string.dialog_empty));
                    } else {
                        showContentView();
                    }
                })
                .build();

        customDialog.show();

        popWindowListAdapter.setOnItemChildClickListener((adapter, view, position) -> {//点击同事的时候分类显示
            TeamEntity.DataBean.ListBean bean = (TeamEntity.DataBean.ListBean) adapter.getData().get(position);
            String name = null;
            if (bean.getName() != null) name = bean.getName();
            name = bean.getNickname();
            customDialog.dismiss();
            serviceId = bean.get_id();
            colleagueListBeans.clear();
            listAdapter.notifyItemRangeChanged(0,colleagueListBeans.size());//防止java.lang.IndexOutOfBoundsException: Inconsistency detected.
            for (MessageDialogEntity.DataBean.ListBean listBean : colleagueListBean) {
                if (listBean.getServiceId().equals(serviceId)) {
                    colleagueListBeans.add(listBean);
                }
            }
            titleColleague.setText(name);
            listAdapter.notifyDataSetChanged();
            if (colleagueListBeans.size() == 0) {
                showEmptyView(getString(R.string.dialog_empty));
            } else {
                showContentView();
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void showEndDialog(ReceptionEntity.DataBean messageEntity) {
        if (colleagueListBean.size() <= POSITION)return;
        colleagueListBeans.remove(POSITION);
        listAdapter.notifyItemRemoved(POSITION);
        if (colleagueListBeans.size() == 0) {
            showEmptyView(getString(R.string.dialog_empty));
        }
    }

    @Override
    public void onSocketEvent(MessageEvent msg) {
        super.onSocketEvent(msg);




        MessageEntity message = (MessageEntity) msg.getMsg();
        String dialogId = message.getDialogId();
        if (msg.getType() == EventMessage) {
            switch (message.getAct()) {
                //连接成功
                case MESSAGE_LOGINSUC:
                    //登录连接成功获取对话列表，并加入房间
                    break;
                //加入房间
                case MESSAGE_JOIN:
                    break;
                //结束对话后退出房间
                case MESSAGE_LEAVE:
                    updateData();
                    listAdapter.notifyDataSetChanged();
                    if (colleagueListBeans.size() == 0) {
                        showEmptyView(getString(R.string.dialog_empty));
                    }
                    break;
                //只有客服能收到的退出房间消息
                case MESSAGE_SERVICELEAVE:
                    break;
                //收到新消息
                case MESSAGE_NEW:
                    updateData();
                    if (message.getAutoMsgType() != null && message.getAutoMsgType().equals("end"))
                        return;
                    if (message.getMessage().getOneway() != null && message.getMessage().getOneway().equals("service"))
                        return;//如果是服务器推送则不更新消息
                    for (MessageDialogEntity.DataBean.ListBean listBean : colleagueListBeans) {
                        if (listBean.getId().equals(dialogId)) {
                            listAdapter.notifyDataSetChanged();
                        }
                    }
                    break;
                //新对话加入
                case MESSAGE_NEWDIALOG:
//                listAdapter.notifyDataSetChanged();
                    break;
                //新对话加入(EventBus通知更新)
                case EVENTBUS_NEWDIALOG:
                    updateData();
                    //给对话添加类型避免列表类型错误
                    for (MessageDialogEntity.DataBean.ListBean listBean : colleagueListBeans) {
                        listBean.setItemtype(Constant.COLLEAGUE_ACT);
                    }
                    listAdapter.notifyDataSetChanged();
                    break;
                //接收顾客输入的文字
                case MESSAGE_INPUTING:
                    break;
                //顾客上线
                case MESSAGE_ONLINE:
                    updateData();
                    for (int i = 0; i < colleagueListBeans.size(); i++) {
                        if (colleagueListBeans.get(i).getCustomer().getId().equals(message.getCustomerId())) {
                            colleagueListBeans.get(i).setCustomerOffTime(null);
                            listAdapter.notifyItemChanged(i);
                        }
                    }
                    break;
                //顾客离线
                case MESSAGE_OFFLINE:
                    updateData();
                    for (int i = 0; i < colleagueListBeans.size(); i++) {
                        if (colleagueListBeans.get(i).getCustomer().getId().equals(message.getCustomerId())) {
                            colleagueListBeans.get(i).setCustomerOffTime(DateUtils.getDateFormat(DateUtils.getCurrentTimeInLong()));
                            listAdapter.notifyItemChanged(i);
                        }
                    }

                    break;
                //对话被接待
                case MESSAGE_RECEPTION:
                    updateData();
                    for (MessageDialogEntity.DataBean.ListBean listBeans : colleagueListBeans) {
                        MessageDialogEntity.DataBean.ListBean item = JsonParseUtils.parseToObject(message.getItem().toString(), MessageDialogEntity.DataBean.ListBean.class);
                        if (listBeans.getId().equals(item.getId())) {
                            listAdapter.notifyDataSetChanged();
                        }
                    }
                    break;
                //客服账号登录唯一性验证//保存serviceId
                case MESSAGE_SERVICEONLY:

                    break;
                //实时访客增加
                case MESSAGE_REALTIME_ADD:
                    break;
                //实时访客修改
                case MESSAGE_REALTIME_MODIFY:
                    break;
                //实时访客删除
                case MESSAGE_REALTIME_DEL:
                    break;
                //客服在线状态变化
                case MESSAGE_STATEUPATE:
                    break;
                //更新对话（团队协作可能更新）
                case MESSAGE_UPATEDIALOG:
                    for (MessageDialogEntity.DataBean.ListBean listBean : colleagueListBeans) {
                        MessageDialogEntity.DataBean.ListBean item = JsonParseUtils.parseToObject(message.getItem().toString(), MessageDialogEntity.DataBean.ListBean.class);
                        if (listBean.getId().equals(item.getId())) {
                            listAdapter.notifyDataSetChanged();
                        }
                    }

                    break;
                //收到客服汇报的在线状态
                case MESSAGE_REPORT_STATE:

                    break;
                case UI_FRESH:
                    setMsgCount();
                    break;
            }
        }
    }

    /**
     * 更新数据
     */
    public synchronized void updateData(){

        colleagueListBeans.clear();
        listAdapter.notifyItemRangeChanged(0,colleagueListBeans.size());//防止java.lang.IndexOutOfBoundsException: Inconsistency detected.
            colleagueListBean = LocalDataSource.getTEAMLIST();
            for (MessageDialogEntity.DataBean.ListBean listBean : colleagueListBean) {
                listBean.setItemtype(Constant.COLLEAGUE_ACT);
                if (serviceId==null||(listBean.getServiceId() != null && listBean.getServiceId().equals(serviceId))) {
                    colleagueListBeans.add(listBean);
                }
            }

    }
    /**
     * 设置未读数
     */
    private void setMsgCount() {
        int unReadCount = AppConfig.unReadCount;
        if (unReadCount == 0) {
            msgCountTv.setVisibility(View.GONE);
        } else {
            msgCountTv.setVisibility(View.VISIBLE);
            msgCountTv.setText(unReadCount + "");
        }
    }
}
