package com.hc_android.hc_css.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gyf.immersionbar.ImmersionBar;
import com.hc_android.hc_css.R;
import com.hc_android.hc_css.adapter.DialogListAdapter;
import com.hc_android.hc_css.base.BaseActivity;
import com.hc_android.hc_css.base.BaseApplication;
import com.hc_android.hc_css.contract.NotReceivedActivityContract;
import com.hc_android.hc_css.entity.MessageDialogEntity;
import com.hc_android.hc_css.entity.MessageEntity;
import com.hc_android.hc_css.entity.ReceptionEntity;
import com.hc_android.hc_css.presenter.NotReceivedActivityPresenter;
import com.hc_android.hc_css.utils.Constant;
import com.hc_android.hc_css.utils.DateUtils;
import com.hc_android.hc_css.utils.JsonParseUtils;
import com.hc_android.hc_css.utils.android.ToastUtils;
import com.hc_android.hc_css.utils.socket.MessageEvent;
import com.hc_android.hc_css.wight.LocalDataSource;
import com.hc_android.hc_css.wight.RecyclerViewNoBugLinearLayoutManager;

import java.util.Collections;
import java.util.Comparator;
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

public class NotReceivedActivity extends BaseActivity<NotReceivedActivityPresenter, ReceptionEntity.DataBean> implements NotReceivedActivityContract.View {

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
        return findViewById(R.id.inject_target);
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
        notListBean = LocalDataSource.getNOTLIST();
        mCountAll = getIntent().getIntExtra(Constant.HAVERECEIVED_NUM, 0);
        for (MessageDialogEntity.DataBean.ListBean listBean : notListBean) {
            listBean.setItemtype(Constant.NOTRECEIVED_ACT);
        }
        listSort(notListBean);
        if (notListBean != null) {
            conmonTitleTextView.setText(getResources().getString(R.string.dialog_no_received));
            RecyclerViewNoBugLinearLayoutManager layoutManager = new RecyclerViewNoBugLinearLayoutManager(this);
            actNotReceivedRecycler.setLayoutManager(layoutManager);
            listAdapter = new DialogListAdapter(notListBean);
            actNotReceivedRecycler.setAdapter(listAdapter);
        }
        if (notListBean.size()==0){
            showEmptyView(getString(R.string.dialog_empty));
        }
        listAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (adapter.getData()!=null&&adapter.getData().size()>0) {
                MessageDialogEntity.DataBean.ListBean item = (MessageDialogEntity.DataBean.ListBean) adapter.getData().get(position);
                POSITION = position;
                switch (view.getId()) {
                    case R.id.close_btn:
                        String idList = item.getId();
                        mPresenter.pEndDialog(idList, null, null);
                        break;
                    case R.id.btn_jd:
                        if (mCountAll < BaseApplication.getUserBean().getMaxChat()) {
                            mPresenter.pReceptionDialog(notListBean.get(position).getId());
                        } else {
                            ToastUtils.showShort("同时对话已经数达到最大值，请先处理当前对话");
                        }
                        break;
                    case R.id.lin_constrain:
                        LocalDataSource.setITEMBEAN(item);
                        if (hasPermission()) {
                            Bundle bundle = new Bundle();
                            bundle.putString(Constant.INTENT_TYPE, Constant.NOTRECEIVEDACT_);
                            startActivity(ChatActivity.class, bundle);
                        }
                        break;
                }
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
        if (suc == 0) {
            ToastUtils.showShort("对话已被接待");
        }
        if (suc == 1) {
//            notListBean.remove(POSITION);
//            listAdapter.notifyDataSetChanged();
            conmonTitleTextView.setText(getResources().getString(R.string.dialog_no_received));
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
        switch (v.getId()) {
            case R.id.backImg:
                finish();

                break;
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void showEndDialog(ReceptionEntity.DataBean messageEntity) {
//        notListBean.remove(POSITION);
//        listAdapter.notifyItemRemoved(POSITION);
        conmonTitleTextView.setText(getResources().getString(R.string.dialog_no_received));
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
                    if (notListBean.size()==0){
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
                    for (MessageDialogEntity.DataBean.ListBean listBean : notListBean) {
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
                    listAdapter.notifyDataSetChanged();
                    break;
                //接收顾客输入的文字
                case MESSAGE_INPUTING:
                    break;
                //顾客上线
                case MESSAGE_ONLINE:
                    updateData();
                    for (int i = 0; i < notListBean.size(); i++) {
                        if (notListBean.get(i).getCustomer().getId().equals(message.getCustomerId())) {
                            notListBean.get(i).setCustomerOffTime(null);
                            listAdapter.notifyItemChanged(i);
                        }
                    }
                    break;
                //顾客离线
                case MESSAGE_OFFLINE:
                   updateData();
                    for (int i = 0; i < notListBean.size(); i++) {
                        if (notListBean.get(i).getCustomer().getId().equals(message.getCustomerId())) {
                            notListBean.get(i).setCustomerOffTime(DateUtils.getDateFormat(DateUtils.getCurrentTimeInLong()));
                            listAdapter.notifyItemChanged(i);
                        }
                    }

                    break;
                //对话被接待
                case MESSAGE_RECEPTION:
                    updateData();
                    listAdapter.notifyDataSetChanged();
                    if (notListBean.size()==0){
                        showEmptyView(getString(R.string.dialog_empty));
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
                    updateData();
                    for (MessageDialogEntity.DataBean.ListBean listBean : notListBean) {
                        MessageDialogEntity.DataBean.ListBean item = JsonParseUtils.parseToObject(message.getItem().toString(),MessageDialogEntity.DataBean.ListBean.class);
                        if (listBean.getId().equals(item.getId())) {
                            listAdapter.notifyDataSetChanged();
                        }
                    }

                    break;
                //收到客服汇报的在线状态
                case MESSAGE_REPORT_STATE:
                    break;

            }
        }
    }

    /**
     * 更新未接待数据
     */
    public synchronized void updateData(){

        notListBean.clear();
        listAdapter.notifyItemRangeChanged(0,notListBean.size());//防止java.lang.IndexOutOfBoundsException: Inconsistency detected.
        notListBean.addAll(LocalDataSource.getNOTLIST());
        for (MessageDialogEntity.DataBean.ListBean listBean : notListBean) {
            listBean.setItemtype(Constant.NOTRECEIVED_ACT);
        }
        listSort(notListBean);
    }


    /**
     * 将有未读消息数的排前面
     *
     * @param listBeans
     */
    private synchronized void listSort(List<MessageDialogEntity.DataBean.ListBean> listBeans) {

        Collections.sort(listBeans, new Comparator<MessageDialogEntity.DataBean.ListBean>() {
            @Override
            public int compare(MessageDialogEntity.DataBean.ListBean o1, MessageDialogEntity.DataBean.ListBean o2) {
                try {

                    long dt1 = o1.getLastMsg().getTime();
                    long dt2 =o2.getLastMsg().getTime();

                    //先置顶排序后时间倒序排序
                    if (o1.getUnreadNum()!=0 && o2.getUnreadNum()!=0) {  //判断是否有消息未读数
                        if (dt1 < dt2) {
                            return 1;
                        } else if (dt1 > dt2) {
                            return -1;
                        } else {
                            return 0;
                        }
                    } else if (o1.getUnreadNum()!=0) {
                        return -1;
                    } else if (o2.getUnreadNum()!=0) {
                        return 1;
                    } else {
                        //时间排序
                        if (dt1 < dt2) {
                            return 1;
                        } else if (dt1 > dt2) {
                            return -1;
                        } else {
                            return 0;
                        }
                    }
                } catch (Exception e) {

                    e.printStackTrace();
                }
                return 0;
            }
        });

    }

}
