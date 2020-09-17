package com.hc_android.hc_css.ui.fragment;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonObject;
import com.gyf.immersionbar.ImmersionBar;
import com.hc_android.hc_css.R;
import com.hc_android.hc_css.adapter.DialogListAdapter;
import com.hc_android.hc_css.api.Address;
import com.hc_android.hc_css.base.BaseApplication;
import com.hc_android.hc_css.base.BaseConfig;
import com.hc_android.hc_css.base.BaseFragment;
import com.hc_android.hc_css.contract.ChatListFragmentContract;
import com.hc_android.hc_css.entity.LoginEntity;
import com.hc_android.hc_css.entity.MessageDialogEntity;
import com.hc_android.hc_css.entity.MessageEntity;
import com.hc_android.hc_css.entity.ReceptionEntity;
import com.hc_android.hc_css.entity.TeamEntity;
import com.hc_android.hc_css.entity.UserEntity;
import com.hc_android.hc_css.presenter.ChatListFragmentPresenter;
import com.hc_android.hc_css.ui.activity.ChatActivity;
import com.hc_android.hc_css.ui.activity.ColleagueActivity;
import com.hc_android.hc_css.ui.activity.LoginActivity;
import com.hc_android.hc_css.ui.activity.MainActivity;
import com.hc_android.hc_css.ui.activity.NotReceivedActivity;
import com.hc_android.hc_css.ui.activity.realauth.DisclaimerActivity;
import com.hc_android.hc_css.ui.activity.realauth.RealAuthenActivity;
import com.hc_android.hc_css.utils.Constant;
import com.hc_android.hc_css.utils.DateUtils;
import com.hc_android.hc_css.utils.JsonParseUtils;
import com.hc_android.hc_css.utils.NullUtils;
import com.hc_android.hc_css.utils.android.SharedPreferencesUtils;
import com.hc_android.hc_css.utils.android.app.AppConfig;
import com.hc_android.hc_css.utils.android.app.CacheData;
import com.hc_android.hc_css.utils.socket.EventServiceImpl;
import com.hc_android.hc_css.utils.socket.MessageEvent;
import com.hc_android.hc_css.utils.socket.NotificationUtils;
import com.hc_android.hc_css.wight.ChoiceDialog;
import com.hc_android.hc_css.wight.LocalDataSource;
import com.hc_android.hc_css.wight.RecyclerViewNoBugLinearLayoutManager;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import io.socket.client.Ack;
import me.leolin.shortcutbadger.ShortcutBadger;

import static android.content.Context.NOTIFICATION_SERVICE;
import static com.hc_android.hc_css.utils.Constant.EVENTBUS_HASH_STATE;
import static com.hc_android.hc_css.utils.Constant.EVENTBUS_NETWORK_STATE;
import static com.hc_android.hc_css.utils.Constant.EVENTBUS_NEWDIALOG;
import static com.hc_android.hc_css.utils.Constant.EVENTBUS_NEWDIALOG_VISITOR;
import static com.hc_android.hc_css.utils.Constant.EVENTBUS_NOTIFICATION_STATE;
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
import static com.hc_android.hc_css.utils.Constant.MESSAGE_UNDO;
import static com.hc_android.hc_css.utils.Constant.MESSAGE_UPATEDIALOG;
import static com.hc_android.hc_css.utils.Constant.UI_FRESH;
import static com.hc_android.hc_css.utils.socket.MessageEventType.EventMessage;

public class ChatListFragment extends BaseFragment<ChatListFragmentPresenter, MessageDialogEntity.DataBean> implements ChatListFragmentContract.View {


    private static int notificationId=1;
    private final String SERVER_URL = Address.SOCKET_URL + "?type=service&hash=";
    @BindView(R.id.backLayout)
    LinearLayout backLayout;
    @BindView(R.id.conmonTitleTextView)
    TextView conmonTitleTextView;
    @BindView(R.id.chat_recycler)
    RecyclerView chatRecycler;
    @BindView(R.id.wifi_error)
    LinearLayout wifiError;


    private DialogListAdapter dialogListAdapter;
    private List<MessageDialogEntity.DataBean.ListBean> haveListBeans;
    private List<MessageDialogEntity.DataBean.ListBean> notListBean;
    private List<MessageDialogEntity.DataBean.ListBean> colleagueListBean;
    private List<MessageDialogEntity.DataBean.ListBean> listUIBeans;//展示的listUI集合
    private List<MessageDialogEntity.DataBean.ListBean> listBeans;//请求总集合
    int mCounter = 0;//已接待对话总条数
    int unReadCount = 0;//总的未读条数
    int limit = 20;//限制每次访问多少条第一设置20条
    int skip = 0;//跳过已获取的条数
    int INDEXS = 0;
    private String POSITION = "item_position";
    private NotificationManager mNotificationManager;
    private String NOTIFICATION_CHANNEL = "me.leolin.shortcutbadger.example";
    private String serviceId;
    private String realtimeId;
    private String needIntentId;//需要跳转的Id
    private static final int NOTIFI_ALL=1;//全局刷新
    private static final int NOTIFI_ITEM=2;//单个刷新
    private static final int NOTIFI_DEL=3;//单个删除
    private static final int NOTIFI_ADD=4;//单个添加
    private static final int NOTIFI_REM=5;//单个移除

    public ChatListFragment() {

    }

    public static ChatListFragment newInstance() {
        Bundle args = new Bundle();
        ChatListFragment fragment = new ChatListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public ChatListFragmentPresenter getPresenter() {
        return new ChatListFragmentPresenter();
    }

    @Override
    protected int injectTarget() {
        return R.id.inject_lin;
    }

    @Override
    protected void doRetry() {
        initload();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.chat_list_fragment;
    }

    @Override
    protected void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.theme_app)
                .titleBar(R.id.chat_list_include)
                .init();
        listBeans = new ArrayList<>();
        listUIBeans = new ArrayList<>();
        notListBean = new ArrayList<>();
        haveListBeans = new ArrayList<>();
        colleagueListBean = new ArrayList<>();
        dialogListAdapter = new DialogListAdapter(listUIBeans);
        RecyclerViewNoBugLinearLayoutManager layoutParams = new RecyclerViewNoBugLinearLayoutManager(getHcActivity());
        chatRecycler.setLayoutManager(layoutParams);
        chatRecycler.setAdapter(dialogListAdapter);
        dialogListAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            List data = adapter.getData();
            if (!NullUtils.isEmptyList(data)&&data.size()>position) {
                MessageDialogEntity.DataBean.ListBean item = (MessageDialogEntity.DataBean.ListBean) adapter.getData().get(position);
                switch (view.getId()) {
                    case R.id.dialog_no_received_lin://点击未接待跳转

                        LocalDataSource.setNOTLIST(notListBean);//保存未接待全局数据
                        Bundle bundle = new Bundle();
                        bundle.putInt(Constant.HAVERECEIVED_NUM, mCounter);
                        startActivity(NotReceivedActivity.class, bundle);
                        break;
                    case R.id.dialog_list_colleague_lin://点击同事对话跳转
                        LocalDataSource.setTEAMLIST(colleagueListBean);//保存同事对话全局数据
                        startActivity(ColleagueActivity.class);
                        break;
                    case R.id.close_btn://点击结束
                        INDEXS = position;
                        String idList = item.getId();
                        notifisychronze(NOTIFI_REM,position);
                        for (Iterator<MessageDialogEntity.DataBean.ListBean> it = listBeans.iterator(); it.hasNext(); ) {
                            if (it.next().getId().equals(idList)) {
                                it.remove();
                            }
                        }
                        mPresenter.pEndDialog(idList, null, null);
                        break;
                    case R.id.lin_constrain:
                        LocalDataSource.setITEMBEAN(item);
                        if (hasPermission()) {
                            startActivity(ChatActivity.class);
                        }
                        break;
                }

            }
        });

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

//        //判断在线状态
//        if (!BaseApplication.getUserBean().getState().equals("break")) {
//            String hash = (String) SharedPreferencesUtils.getParam(Constant.HASH, "");
//
//            try {
//                EventServiceImpl.getInstance().connect(hash);
//                showLoadingView();
//            } catch (URISyntaxException e) {
//                showContentView();
//                Toast.makeText(getHcActivity(), "Failed to connect to chat server.", Toast.LENGTH_LONG).show();
//                e.printStackTrace();
//            }
//        } else {
////            showEmptyView(getHcActivity().getResources().getString(R.string.break_online));
////            conmonTitleTextView.setText("对话（离线）");
//            BaseConfig.setStateChange("break",false);
//        }
//判断在线状态
        if (BaseApplication.getUserBean()!=null&&BaseApplication.getUserBean().getState()!=null) {
            String state = BaseApplication.getUserBean().getState();
            if (!state.equals("break")) {
                String hash = (String) SharedPreferencesUtils.getParam(Constant.HASH, "");

                try {
                    EventServiceImpl.getInstance().connect(hash);
                } catch (URISyntaxException e) {
                    showContentView();
                    Toast.makeText(getHcActivity(), "Failed to connect to chat server.", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            } else {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        BaseConfig.setStateChange("break", false,BaseConfig.ONLINE_STATE_DEFULTE);
                    }
                }, 500);

            }
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void showDataError(String errorMessage) {

    }

    @Override
    public void showDataSuccess(MessageDialogEntity.DataBean datas) {
//        for (MessageDialogEntity.DataBean.ListBean listBean : listBeans) {
//           if ( listBean.getId().equals(listUIBeans.get(INDEXS).getId())){
//                listBeans.remove(listBean);
//                break;
//            }
//        }
//        refreshUI(listBeans);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void showDialogList(MessageDialogEntity.DataBean messageEntity) {
        //设置自动结束状态和离线自动结束状态
        UserEntity userEntity = BaseApplication.getUserEntity();
        if (userEntity.getUserbean() != null) {
            serviceId = userEntity.getUserbean().getId();
            if (messageEntity != null) {
                if (messageEntity.getAutoEnd() != null)
                    userEntity.setAutoEnd(messageEntity.getAutoEnd());
                if (messageEntity.getOffEnd() != null)
                    userEntity.setOffEnd(messageEntity.getOffEnd());
            }
            if (messageEntity.getList() != null) {
                List<MessageDialogEntity.DataBean.ListBean> list = messageEntity.getList();
                listBeans.addAll(list);
                skip = listBeans.size();

                if (list.size() == limit) {
                    if (limit == 20) {
                        //第一次请求20条刷新
                        refreshUI(listBeans,null);
                        showContentView();
                    }
                    //第二次请求将限制条数扩大到150
                    limit = 150;
                    mPresenter.pShowMessageDialog(limit, skip);
                } else {
                    if (BaseConfig._ONLINE_STATE.equals("on")) {
                        conmonTitleTextView.setText(getResources().getString(R.string.dialog_list));
                    }
                    showContentView();

                    //请求完成数据之后批量自动结束对话
                    endDialogList(messageEntity);
                    //最后所有数据请求完成之后刷新
                    refreshUI(listBeans,null);
                    //获取同事成员列表
                    mPresenter.pGetTeamList();
                }
            }
        }

    }

    /**
     * 自动结束对话
     *
     * @param messageEntity
     */
    private void endDialogList(MessageDialogEntity.DataBean messageEntity) {
        List<String> autoList = new ArrayList<>();
        List<String> offList = new ArrayList<>();
        if (messageEntity != null) {
            //超过设置时间自动过期结束
            if (BaseApplication.getUserEntity().getAutoEnd() != null) {
                if (BaseApplication.getUserEntity().getAutoEnd().isState()) {

                    long autoTime = (BaseApplication.getUserEntity().getAutoEnd().getTime()) * 60 * 1000;//分钟转毫秒
                    for (Iterator<MessageDialogEntity.DataBean.ListBean> it = listBeans.iterator(); it.hasNext(); ) {
                        MessageDialogEntity.DataBean.ListBean listBean = it.next();
                        long lastMsg = 0;
                        if (listBean.getState().equals("active")) {
                            if (listBean.getReceptionTime() != null) {
                                long receptionTime = DateUtils.getDate(listBean.getReceptionTime()).getTime();
                                //接待时间如果大于最后一条消息时间则以最后一条消息时间为最后时间
                                if (receptionTime > listBean.getLastMsg().getTime()) {
                                    lastMsg = listBean.getLastMsg().getTime();
                                }
                            } else{
                                lastMsg = listBean.getLastMsg().getTime() != 0 ? listBean.getLastMsg().getTime() : DateUtils.getDate(listBean.getAddtime()).getTime();//是否存在lastmsg
                            }
                            long time = autoTime - (DateUtils.getCurrentTimeInLong() - lastMsg);//比较时间差
                            if (time <= 0) {
                                autoList.add(listBean.getId());
                                it.remove();
                            }
                        }
                    }

                    int counts = (int) Math.ceil((autoList.size()) / 200f);//计算需要上传次数(向上取整)
                    if (counts > 0) {
                        for (int i = 0; i < counts; i++) {
                            String idList = null;
                            int size = autoList.size() - i * 200 > 200 ? (i + 1) * 200 : autoList.size();//计算size(每次最多结束200)
                            for (int x = i * 200; x < size; x++) {
                                if (idList == null) {
                                    idList = autoList.get(x);
                                } else {
                                    idList += "," + autoList.get(x);
                                }
                            }

                            mPresenter.pEndDialog(idList, null, "true");
                        }
                    }
                }
                //离线超过时间没有新消息
                if (BaseApplication.getUserEntity().getOffEnd() != null && BaseApplication.getUserEntity().getOffEnd().isState()) {
                    long offTime = (BaseApplication.getUserEntity().getOffEnd().getTime()) * 1000;
                    for (Iterator<MessageDialogEntity.DataBean.ListBean> it = listBeans.iterator(); it.hasNext(); ) {
                        MessageDialogEntity.DataBean.ListBean listBean = it.next();
                        if (listBean.getState().equals("active") && listBean.getCustomerOffTime() != null) {
                            if (listBean.getSource().equals("web") || listBean.getSource().equals("link")) {
                                long time = 0;
                                if (listBean.getReceptionTime() != null) {
                                    long receptionTime = DateUtils.getDate(listBean.getReceptionTime()).getTime();
                                    //接待时间如果大于最后一条消息时间则以最后一条消息时间为最后时间
                                    if (receptionTime > DateUtils.getDate(listBean.getCustomerOffTime()).getTime()) {
                                        time = DateUtils.getDate(listBean.getCustomerOffTime()).getTime();
                                    }
                                } else{
                                    time = offTime - (DateUtils.getCurrentTimeInLong() - DateUtils.getDate(listBean.getCustomerOffTime()).getTime());//比较时间差
                                 }
                                if (time <= 0) {
                                    offList.add(listBean.getId());
                                    it.remove();
                                }
                            }
                        }
                    }
                    int counts = (int) Math.floor(offList.size() / 200f);//计算需要上传次数
                    if (counts > 0) {
                        for (int i = 0; i < counts; i++) {
                            String idList = null;
                            int size = offList.size() - i * 200 > 200 ? (i + 1) * 200 : offList.size();
                            for (int x = i * 200; x < size; x++) {
                                if (idList == null) {
                                    idList = offList.get(x);
                                } else {
                                    idList += "," + offList.get(x);
                                }
                            }
                            mPresenter.pEndDialog(idList, "true", null);
                        }
                    }
                }
            }
        }

    }

    /**
     * 新对话加入数据
     *
     * @param messageEntity
     */
    @Override
    public void showNewDialog(MessageDialogEntity.DataBean messageEntity) {
        if (messageEntity.getDialog() != null) {
            listBeans.add(messageEntity.getDialog());
            refreshUI(listBeans,null);
            //自己的对话时才通知
            if (messageEntity.getDialog().getServiceId()!=null&&messageEntity.getDialog().getServiceId().equals(BaseApplication.getUserBean().getId())) {
                if (AppConfig.isIsOpenNewDialog()&&messageEntity.getDialog().getState()!=null&&messageEntity.getDialog().getState().equals("active")) {
                    NotificationUtils notificationUtils = new NotificationUtils(getHcActivity());
                    notificationUtils.sendNotification(getHcActivity(), unReadCount, messageEntity.getDialog());
                }
            }
            //发送新对话请求完成通知，未接待页面更新UI
            MessageEntity message = new MessageEntity();
            message.setAct(EVENTBUS_NEWDIALOG);
            MessageEvent event = new MessageEvent(EventMessage, message);
            EventBus.getDefault().postSticky(event);
            //询问是否在线
            EventServiceImpl.getInstance().isOffLine(messageEntity.getDialog().getCustomerId(), messageEntity.getDialog().getServiceId());

            if (needIntentId != null && messageEntity.getDialog().getId()!=null&&messageEntity.getDialog().getId().equals(needIntentId)) {
                for (int i = 0; i < listUIBeans.size(); i++) {
                    if (listUIBeans.get(i).getId()!=null&&listUIBeans.get(i).getId().equals(needIntentId)) {
                        MessageDialogEntity.DataBean.ListBean item = listUIBeans.get(i);
                        LocalDataSource.setITEMBEAN(item);
                        if (hasPermission()) {
                            startActivity(ChatActivity.class);
                        }
                    }
                }

            }
        }
    }

    /**
     * 对话结束成功
     *
     * @param messageEntity
     */
    @Override
    public void showEndDialog(ReceptionEntity.DataBean messageEntity) {

    }

    /**
     * 同事成员列表
     *
     * @param dataBean
     */
    @Override
    public void showTeamList(TeamEntity.DataBean dataBean) {
        if (!NullUtils.isNull(dataBean.getList())) {
            LocalDataSource.setTEAMSERLIST(dataBean.getList());
        }
    }

    /**
     * 数据分类排序和刷新UI
     *
     * @param list
     */
    private synchronized void refreshUI(List<MessageDialogEntity.DataBean.ListBean> list,String dialogId) {
        //加入房间操作
        for (Iterator<MessageDialogEntity.DataBean.ListBean> it = list.iterator(); it.hasNext(); ) {
            EventServiceImpl.getInstance().joinRoom(it.next().getCustomerId());
        }
        listUIBeans.clear();
        notifisychronze(NOTIFI_ALL,0);
        notListBean.clear();
        colleagueListBean.clear();
        haveListBeans.clear();
        //先置顶排序后按时间倒序排序
        listSort(list);
        //数据分类 为接待/已经待/同事的对话
        for (Iterator<MessageDialogEntity.DataBean.ListBean> it = list.iterator(); it.hasNext(); ) {
            MessageDialogEntity.DataBean.ListBean listBean = it.next();
//            let unadok = true
//            if(item.assignGrade && item.serviceId != myInfo.id && !myInfo.authority.assist)unadok = false
//            if(myInfo.founding)unadok = true
            if (listBean.getState().equals("unassigned")) {
                boolean isAdd = true;//是否添加
                //是否是专属客服，如果是的，比较serviceId是否是本人serviceId,如果不是则看不到这条信息，管理员都可以看到(最后查看自己是否开启对话协助)
                if (listBean.isAssignGrade()&&!listBean.getServiceId().equals(BaseApplication.getUserBean().getId()) && !BaseApplication.getUserBean().getAuthority().isAssist()){
                    isAdd=false;
                }
                if (BaseApplication.getUserBean().isFounding()){//如果是管理员直接添加
                    isAdd=true;
                }
                if (isAdd) {
                    notListBean.add(listBean);
                }
            } else if (!listBean.getServiceId().equals(serviceId)) { //如果serviceId不相同则为同事对话
                colleagueListBean.add(listBean);
            } else {
                listBean.setItemtype(Constant.HAVERECEIVED);
                haveListBeans.add(listBean);
            }
        }


        //添加到UI list集合

        if (haveListBeans.size() != 0) { //添加已接待的集合
            listUIBeans.addAll(haveListBeans);
        }
        if (colleagueListBean.size() == 0) {  //添加同事 的对话
            MessageDialogEntity.DataBean.ListBean bean = new MessageDialogEntity.DataBean.ListBean();
            MessageDialogEntity.DataBean.ListBean.LastMsgBean lastMsgBean = new MessageDialogEntity.DataBean.ListBean.LastMsgBean();
            lastMsgBean.setTime(10000000);//如果没有同事的对话时，设置一个较小的时间戳，防止排序报错,将同事对话排在最后
            bean.setLastMsg(lastMsgBean);
            bean.setItemtype(Constant.COLLEAGUE);
            listUIBeans.add(bean);
        } else {
            //将同事 的最新一条数据添加到UI  list
            MessageDialogEntity.DataBean.ListBean bean = new MessageDialogEntity.DataBean.ListBean();
            try {
                bean = colleagueListBean.get(0).clone();//克隆避免原始数据被赋值
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            bean.setItemtype(Constant.COLLEAGUE);
            int unReadNum = 0;
//            for (MessageDialogEntity.DataBean.ListBean listBean : colleagueListBean) {
//                unReadNum+=listBean.getUnreadNum();
//            }
            bean.setUnCount(colleagueListBean.size());//设置同事对话数量
            bean.setUnreadNum(unReadNum);//设置同事未读数量设置为0
            bean.setTop(false);//不参与置顶排序
            listUIBeans.add(bean); //添加同事集合
        }
        //将添加了同事的item 再ui集合里面根据lastTime排序
        listSort(listUIBeans);
        //最后添加未接待item并插入第一条
        if (notListBean.size() != 0) {  //如果有未接待的先添加未接待集合
            //将未接待 的最新一条数据添加到UI  list
            MessageDialogEntity.DataBean.ListBean bean = new MessageDialogEntity.DataBean.ListBean();
            try {
                //将未接待的第一条克隆给UI列表
                bean = (MessageDialogEntity.DataBean.ListBean) (notListBean.get(0)).clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            //重新创建对象确保原始数据列表数据正常
            bean.setItemtype(Constant.NOTRECEIVED);
            //最后设置未读数量,避免未接待列表公用数据的不正确
            int unReadNum = 0;
            for (MessageDialogEntity.DataBean.ListBean listBean : notListBean) {
                unReadNum += listBean.getUnreadNum();
            }
            bean.setUnCount(notListBean.size());//设置未接待数量
            bean.setUnreadNum(unReadNum);//设置未读数量

            listUIBeans.add(0, bean); //添加未接待item到UI list的第一条
        }
        for (int i = 0; i < listBeans.size(); i++) {//去重元素
            for (int j = 0; j < listBeans.size(); j++) {
                if (i != j && listBeans.get(i).getId().equals(listBeans.get(j).getId())) {
                    listBeans.remove(listBeans.get(j));
                }
            }
        }

        notifisychronze(NOTIFI_ALL,0);
        checkDialogNum(dialogId);
        LocalDataSource.setNOTLIST(notListBean);
        LocalDataSource.setTEAMLIST(colleagueListBean);
    }

    /**
     * 检验对话数是否已满,和底部未读
     */
    @SuppressLint("SetTextI18n")
    private void checkDialogNum(String dialogId) {
        mCounter = 0;
        unReadCount = 0;
        for (MessageDialogEntity.DataBean.ListBean listBean : listBeans) {
            if (listBean.getState().equals("active") && listBean.getServiceId().equals(serviceId)) {
                mCounter++;
            }

            //更新未读数
            if (listBean.getServiceId().equals(serviceId) || listBean.getState().equals("unassigned")) {
                if (!listBean.isAssignGrade() || listBean.getServiceId().equals(serviceId) || BaseApplication.getUserBean().isFounding()) {
                    if (listBean.getUnreadNum() != 0 && !listBean.isDisturb()) {
                        unReadCount += listBean.getUnreadNum();
                    }
                }
            }
        }
        int maxChat = BaseApplication.getUserBean().getMaxChat();
        if (mCounter >= maxChat) {
            conmonTitleTextView.setText(getResources().getString(R.string.dialog_list) + "(对话数已满)");
        }else {
            if (!BaseApplication.getUserBean().getState().equals("off")) {
                conmonTitleTextView.setText(getResources().getString(R.string.dialog_list));
            }
        }

        if (unReadCount != 0) {
            ((MainActivity) getHcActivity()).getNavigationBar().setMsgPointCount(0, unReadCount);
            ShortcutBadger.applyCount(getHcActivity(), unReadCount);
        } else {
            ((MainActivity) getHcActivity()).getNavigationBar().clearAllMsgPoint();
            ShortcutBadger.applyCount(getHcActivity(), 0);
        }
            //设置全局未读数
        if (unReadCount<100) {
            AppConfig.setUnReadCount(unReadCount,dialogId);
        }
    }


    /**
     * 先置顶排序后按lastMsgTime排序
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
                    if (o1.isTop() && o2.isTop()) {  //同时为top时根据时间倒序判断是否交换位置
                        if (dt1 < dt2) {
                            return 1;
                        } else if (dt1 > dt2) {
                            return -1;
                        } else {
                            return 0;
                        }
                    } else if (o1.isTop()) {
                        return -1;
                    } else if (o2.isTop()) {
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

    @Override
    public void onSocketEvent(MessageEvent msg) {
        super.onSocketEvent(msg);
        MessageEntity message = (MessageEntity) msg.getMsg();
        if (msg.getType() == EventMessage) ;
        switch (message.getAct()) {
            //连接成功
            case MESSAGE_LOGINSUC:
                UserEntity userEntity = BaseApplication.getUserEntity();
                userEntity.setSocketId(message.getSocketId());
                LoginEntity.DataBean.InfoBean userbean = userEntity.getUserbean();
                userbean.setState(message.getState());
                userEntity.setUserbean(userbean);
                BaseApplication.setUserEntity(userEntity);
                EventServiceImpl.getInstance().keepLink();
                BaseConfig.setStateChange(BaseApplication.getUserBean().getState(), false,BaseConfig.ONLINE_STATE_DEFULTE);

//                wifiError.setVisibility(View.GONE);
//                haveListBeans.clear();
//                notListBean.clear();
//                colleagueListBean.clear();
//                listUIBeans.clear();//展示的listUI集合
//                listBeans.clear();//请求总集合
//                mCounter = 0;//已接待对话总条数
//                unReadCount = 0;//总的未读条数
//                limit = 20;//限制每次访问多少条第一设置20条
//                skip = 0;//跳过已获取的条数
//                INDEXS = 0;
//                //登录连接成功获取对话列表，并加入房间
//                mPresenter.pShowMessageDialog(limit, skip);
//                BaseApplication.getUserEntity().setSocketId(message.getSocketId());
//                BaseConfig.setStateChange(BaseApplication.getUserBean().getState(),false);
                break;
            //加入房间
            case MESSAGE_JOIN:
                break;
            //结束对话后退出房间
            case MESSAGE_LEAVE:
                EventServiceImpl.getInstance().leaveRoom(message.getRoomID());//离开房间操作
                updateData(message);
                break;
            //只有客服能收到的退出房间消息
            case MESSAGE_SERVICELEAVE:
                break;
            //收到新消息
            case MESSAGE_NEW:
                updateData(message);
                break;
            //新对话加入
            case MESSAGE_NEWDIALOG:
                updateData(message);
                break;
            //接收顾客输入的文字
            case MESSAGE_INPUTING:
                break;
            //顾客上线
            case MESSAGE_ONLINE:
                updateData(message);
                break;
            //顾客离线
            case MESSAGE_OFFLINE:
                updateData(message);
                break;
            //对话被接待
            case MESSAGE_RECEPTION:
                updateData(message);
                break;
            //客服账号登录唯一性验证//保存serviceId
            case MESSAGE_SERVICEONLY:
                //如果是app登录则验证socketId是否一样不一样则表其他设备登录
                if (message.getMode() != null && message.getMode().equals("app")) {
                    if (message.getServiceId() != null && message.getServiceId().equals(serviceId) && !message.getSocketId().equals(BaseApplication.getUserEntity().getSocketId())) {

                        EventServiceImpl.getInstance().disconnect();
                        Intent intent = new Intent(getHcActivity(), LoginActivity.class);
                        getHcActivity().finish();
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        showShortToast("您的账号已在其他设备登录");
                    }
                }
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
                if (message.getServiceId() != null && message.getServiceId().equals(BaseApplication.getUserBean().getId())) {
                    String hash = (String) SharedPreferencesUtils.getParam(Constant.HASH, "");
                    if (!message.getState().equals(BaseApplication.getUserBean().getState()) && message.getHash() != null && !message.getHash().equals(hash)) {

                        if (message.getState().equals("break")) {
                            EventServiceImpl.getInstance().disconnect();
                        }
                        BaseConfig.setStateChange(message.getState(), false,BaseConfig.ONLINE_STATE_DEFULTE);
                    }
                }


                break;
            //更新对话（团队协作可能更新）
            case MESSAGE_UPATEDIALOG:
                updateData(message);
                break;
            //收到客服汇报的在线状态
            case MESSAGE_REPORT_STATE:

                if (message.getItem() != null) {
                    try {
                        TeamEntity.DataBean.ListBean listBean = JsonParseUtils.parseToObject(message.getItem().toString(), TeamEntity.DataBean.ListBean.class);
                        List<TeamEntity.DataBean.ListBean> teamserlist = LocalDataSource.getTEAMSERLIST();
                        if (!NullUtils.isEmptyList(teamserlist) && !NullUtils.isNull(listBean)) {
                            for (int i = 0; i < teamserlist.size(); i++) {
                                if (teamserlist.get(i).get_id().equals(listBean.get_id())) {//设置其他成员的在线状态
                                    if (listBean.getState() != null)
                                        teamserlist.get(i).setState(listBean.getState());
                                }
                            }
                        }
                        //判断设置电脑端在线状态变化
                        if (message.getServiceId() != null && message.getServiceId().equals(BaseApplication.getUserBean().getId())) {//如果客服serviceId相等就判断比较socketId
                            if (!NullUtils.isNull(listBean)) {
                                if (listBean.getSocketId() == null) {
                                    BaseApplication.getUserBean().setSocketId(null);
                                } else {
                                    if (!listBean.getSocketId().equals(BaseApplication.getUserEntity().getSocketId())) {
                                        BaseApplication.getUserBean().setSocketId(listBean.getSocketId());
                                    }
                                }

                            }
                        }

                    } catch (Exception e) {
                    }
                }
                break;
            //UI刷新
            case UI_FRESH:

                String dialogId = message.getDialogId();
                if (dialogId != null) {
                    for (int i = 0; i < listUIBeans.size(); i++) {
                        if (listUIBeans.get(i)!=null&& listUIBeans.get(i).getId()!=null && listUIBeans.get(i).getId().equals(dialogId)) {
                           notifisychronze(NOTIFI_ITEM,i);
                        }
                    }
                }
                break;
            //访客页面主动对话推送
            case EVENTBUS_NEWDIALOG_VISITOR:
                realtimeId = message.getRealtimeId();
                break;

            //全局状态变化
            case EVENTBUS_NOTIFICATION_STATE:
                changedState(message);
                break;
            //网络状态监听
            case EVENTBUS_NETWORK_STATE:
                if (message.isConnected()) {
                    wifiError.setVisibility(View.GONE);
                } else {
                    wifiError.setVisibility(View.VISIBLE);
                    ((TextView) wifiError.getChildAt(1)).setText("当前网络不可用，请检查你的网络设置");
                    if (message.getNetWorkType() == 100) {//100等服务器连接错误
                        ((TextView) wifiError.getChildAt(1)).setText("无法链接到服务器");
                    }
                }

                break;
            //hash过期重新登录
            case EVENTBUS_HASH_STATE:
                EventServiceImpl.getInstance().disconnect();
                Intent intent = new Intent(getHcActivity(), LoginActivity.class);
                getHcActivity().finish();
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            //消息撤回
            case MESSAGE_UNDO:
                String dialogIds = message.getDialogId();
                //更新缓存
                if (dialogIds!=null) {
                    List<MessageEntity.MessageBean> cacheList = CacheData.getCacheList(dialogIds);
                    if (!NullUtils.isEmptyList(cacheList)) {
                        for (int i = cacheList.size() - 1; i >= 0; i--) {
                            if (cacheList.get(i).getId() != null && cacheList.get(i).getId().equals(message.getMsgId())) {
                                cacheList.get(i).setUndo(true);
                                cacheList.get(i).setItemType(Constant.CHAT_CENTER);
                                CacheData.updateCache(dialogIds, cacheList.get(i));
                            }
                        }
                    }
                }

             break;
        }
    }



    /**
     * 更新数据上同步锁
     *
     * @param message
     */
    private synchronized void updateData(MessageEntity message) {

        switch (message.getAct()) {
            //连接成功
            case MESSAGE_LOGINSUC:

                //登录连接成功获取对话列表，并加入房间
//                mPresenter.pShowMessageDialog(limit, skip);

                break;
            //加入房间
            case MESSAGE_JOIN:
                break;
            //结束对话后退出房间
            case MESSAGE_LEAVE:
                //移除数据
                boolean isOwn=true;//这里判断是否是自己手动结束，如果是自己手动结束就不做刷新处理
                for (Iterator<MessageDialogEntity.DataBean.ListBean> it = listBeans.iterator(); it.hasNext(); ) {
                    if (it.next().getId().equals(message.getDialogId())) {
                        it.remove();
                        isOwn=false;
                    }
                }
                //判断如果是自己已接待的列表直接移除单个
                for (int i = 0; i < listUIBeans.size(); i++) {
                    if (listUIBeans.get(i).getServiceId()!=null&&listUIBeans.get(i).getServiceId().equals(serviceId) &&listUIBeans.get(i).getState()!=null&& listUIBeans.get(i).getState().equals("active")) {
                        if (listUIBeans.get(i).getId().equals(message.getDialogId())) {
                            listUIBeans.remove(i);
                             notifisychronze(NOTIFI_DEL,i);
                            checkDialogNum(null);
                            return;
                        }
                    }

                }
                if (!isOwn) {
                    refreshUI(listBeans, null);
                }
                break;
            //只有客服能收到的退出房间消息
            case MESSAGE_SERVICELEAVE:
                break;
            //收到新消息
            case MESSAGE_NEW:
//                showNotifictionIcon(getHcActivity());

                if (message.getAutoMsgType() != null && message.getAutoMsgType().equals("end"))
                    return;
                if (message.getMessage().getOneway() != null && message.getMessage().getOneway().equals("customer")) {
                    CacheData.saveCache(message.getDialogId(), message.getMessage());//评价邀请也要保存,避免消息列表skip数据不对
                    return;//如果是服务器推送则不更新消息
                }
                boolean hasDialogid=false;//是否包含这个dialogid
                for (int i = 0; i < listBeans.size(); i++) {
                    String id = listBeans.get(i).getId();
                    if (id != null && id.equals(message.getDialogId())) {
                        hasDialogid=true;
                        if (message.getMessage().getSendType().equals("customer")) {
                            listBeans.get(i).setUnreadNum(listBeans.get(i).getUnreadNum() + 1);

                        }
                        //更新lastMsg
                        MessageDialogEntity.DataBean.ListBean.LastMsgBean lastMsgBean = listBeans.get(i).getLastMsg();
                        lastMsgBean.setContents(message.getMessage().getContents());
                        lastMsgBean.setType(message.getMessage().getType());
                        lastMsgBean.setTime(message.getMessage().getTime());
                        lastMsgBean.setId(message.getMessage().getId());
                        listBeans.get(i).setLastMsg(lastMsgBean);



                        if (message.getServiceId().equals(BaseApplication.getUserBean().getId())
                                && message.getMessage().getSendType().equals("customer")) {//serviceId是否为自己，是否是顾客发送
//                            未接待、同事的对话 都不提醒
                            if (listBeans.get(i).getItemType() == Constant.HAVERECEIVED && !listBeans.get(i).isDisturb()) {
                                //通知和铃声
                                if (AppConfig.isIsOpenNewMsg()) {//是否开启了新消息
                                    NotificationUtils notificationUtils = new NotificationUtils(getHcActivity());
                                    notificationUtils.sendNotification(getHcActivity(), unReadCount + 1, listBeans.get(i));
                                }
                            }
                        }
                        //消息缓存如果不是本人发送的
                        if (!message.getSocketId().equals(BaseApplication.getUserEntity().getSocketId())) {

                            CacheData.saveCache(message.getDialogId(), message.getMessage());
                        }
                    }
                }
                if (!hasDialogid){
                    CacheData.removeCache(message.getDialogId());
                }
                String dialogId=null;
                if (message.getDialogId()!=null)dialogId=message.getDialogId();
                refreshUI(listBeans,dialogId);
                //新消息数据全局缓存

                break;
            //新对话加入
            case MESSAGE_NEWDIALOG:
                //判断新对话是否是访客列表主动对话来的
                if (message.getRealtimeId() != null && message.getRealtimeId().equals(realtimeId)) {
                    needIntentId = message.getDialogId();
                }
                EventServiceImpl.getInstance().joinRoom(message.getCustomerId(), new Ack() {
                    @Override
                    public void call(Object... args) {//先加入房间等服务器回执再请求对话数据
                        mPresenter.pGetDialog(message.getDialogId());
                        for (int i = 0; i < args.length; i++) {
                            Log.i(TAG, "ACK: onNewDialog" + args[i]);
                        }
                    }
                });

                break;
            //接收顾客输入的文字
            case MESSAGE_INPUTING:
                break;
            //顾客上线
            case MESSAGE_ONLINE:
                //更新UI数据用于单个刷新
                String customId = message.getCustomerId();
                //保存顾客在线离线状态
                MessageDialogEntity.DataBean.ListBean.PathMsgBean pathMsgBean = JsonParseUtils.parseToObject(message.getCurrentUrl(), MessageDialogEntity.DataBean.ListBean.PathMsgBean.class);
                for (int i = 0; i < listUIBeans.size(); i++) {
                    String customerId = listUIBeans.get(i).getCustomerId();
                    if (customerId != null && customerId.equals(customId)) {
                        listUIBeans.get(i).setCustomerOffTime(null);
                        notifisychronze(NOTIFI_ITEM,i);
                         if (pathMsgBean!=null){
                             listUIBeans.get(i).setPathMsgBean(pathMsgBean);
                         }
                    }
                }
                //更新总体集合数据用于后续页面刷新
                for (int i = 0; i < listBeans.size(); i++) {
                    String customerId2 = listBeans.get(i).getCustomerId();
                    if (customerId2 != null && customerId2.equals(customId)) {
                        listBeans.get(i).setCustomerOffTime(null);
                        if (pathMsgBean!=null){
                            listBeans.get(i).setPathMsgBean(pathMsgBean);
                        }
                    }
                }
                break;
            //顾客离线
            case MESSAGE_OFFLINE:
                //更新UI数据用于单个刷新
                String customIds = message.getCustomerId();
                for (int i = 0; i < listUIBeans.size(); i++) {
                    String customerId = listUIBeans.get(i).getCustomerId();
                    if (customerId != null && customerId.equals(customIds)) {
                        listUIBeans.get(i).setCustomerOffTime(DateUtils.getDateFormat(DateUtils.getCurrentTimeInLong()));
                        notifisychronze(NOTIFI_ITEM,i);
                        listUIBeans.get(i).setPathMsgBean(new MessageDialogEntity.DataBean.ListBean.PathMsgBean());
                    }
                }
                //更新总体集合数据用于后续页面刷新
                for (int i = 0; i < listBeans.size(); i++) {
                    String customerId2 = listBeans.get(i).getCustomerId();
                    if (customerId2 != null && customerId2.equals(customIds)) {
                        listBeans.get(i).setCustomerOffTime(DateUtils.getDateFormat(DateUtils.getCurrentTimeInLong()));
//                        listBeans.get(i).setPathMsgBean(new MessageDialogEntity.DataBean.ListBean.PathMsgBean("break"));
                    }
                }
                break;
            //对话被接待
            case MESSAGE_RECEPTION:
                for (MessageDialogEntity.DataBean.ListBean listBean : listBeans) {
                    if (listBean.getId().equals(message.getDialogId())) {
                        listBean.setServiceId(message.getServiceId());
                        listBean.setState("active");
                        listBean.setReceptionTime(DateUtils.getDateFormat(message.getReceptionTime()));
                    }
                }
                refreshUI(listBeans,null);
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
                MessageDialogEntity.DataBean.ListBean item = JsonParseUtils.parseToObject(message.getItem().toString(), MessageDialogEntity.DataBean.ListBean.class);
                for (MessageDialogEntity.DataBean.ListBean listUIBean : listBeans) {
                    if (listUIBean.getId().equals(item.getId())) {  //比较对话id替换对话原来对话的数据
                        MessageDialogEntity.DataBean.ListBean.CustomerBean _customer = listUIBean.getCustomer();
                        MessageDialogEntity.DataBean.ListBean.CustomerBean customer = item.getCustomer();
                        if (customer != null) {//更新顾客信息
                            _customer.setName(customer.getName());
                            if (customer.getHead() != null) _customer.setHead(customer.getHead());
                            if (customer.getNumberId() != 0)
                                _customer.setNumberId(customer.getNumberId());
                            if (customer.getCard() != null) _customer.setCard(customer.getCard());
                            if (customer.getAttCard() != null)
                                _customer.setAttCard(customer.getAttCard());
                            if (customer.getAddress() != null)
                                _customer.setAddress(customer.getAddress());
                            if (customer.getAddtime() != null)
                                _customer.setAddtime(customer.getAddtime());
                            if (customer.getTag() != null) _customer.setTag(customer.getTag());
                        }
                        //更新未读消息
                        if (item.getRead() != 0) listUIBean.setUnreadNum(0);//清空未读消息
                        //置顶和免打扰
                        try {
                            JSONObject jsonObject = new JSONObject(message.getItem().toString());
                            if (jsonObject.has("top"))listUIBean.setTop(item.isTop());
                            if (jsonObject.has("disturb"))listUIBean.setDisturb(item.isDisturb());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (item.getTag() != null) listUIBean.setTag(item.getTag());

                        //备注
                        if (item.getRemarks()!=null)listUIBean.setRemarks(item.getRemarks());
                    }
                }
                String Id=null;
                if (message.getDialogId()!=null)Id=item.getId();
                refreshUI(listBeans,Id);
                break;
            //收到客服汇报的在线状态
            case MESSAGE_REPORT_STATE:
                break;

        }


    }


    public void showNotifictionIcon(Context context) {

        String id = "channel_001";
        String name = "name";
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        Notification notification = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_LOW);
            notificationManager.createNotificationChannel(mChannel);
            notification = new Notification.Builder(context)
                    .setChannelId(id)
                    .setContentTitle("活动")
                    .setContentText("您有一项新活动")
                    .setSmallIcon(R.mipmap.logo).build();
        } else {
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                    .setContentTitle("活动")
                    .setContentText("您有一项新活动")
                    .setSmallIcon(R.mipmap.logo)
                    .setOngoing(true)
                    .setChannelId(id);//无效
            notification = notificationBuilder.build();
        }
        notificationManager.notify(1, notification);


    }

    //全局状态变化
    private void changedState(MessageEntity message) {
        Log.i(TAG, "changedState:" +message.getState());
        switch (message.getState()){

            case "on":
            case "off":
                if (message.getState()!=null&&message.getState().equals("off")){
                    conmonTitleTextView.setText("对话（隐身）");
                }else {
                    conmonTitleTextView.setText("获取中...");
                    if (NullUtils.isEmptyList(listUIBeans)) {
                        showLoadingView();
                    }
                }
                wifiError.setVisibility(View.GONE);
                haveListBeans.clear();
                notListBean.clear();
                colleagueListBean.clear();
                listUIBeans.clear();//展示的listUI集合
                notifisychronze(NOTIFI_ALL,0);
                listBeans.clear();//请求总集合
                mCounter = 0;//已接待对话总条数
                unReadCount = 0;//总的未读条数
                limit = 20;//限制每次访问多少条第一设置20条
                skip = 0;//跳过已获取的条数
                INDEXS = 0;
                //登录连接成功获取对话列表，并加入房间
                mPresenter.pShowMessageDialog(limit, skip);

                break;
            case "break":
                ((MainActivity) getHcActivity()).getNavigationBar().clearAllMsgPoint();
                EventServiceImpl.getInstance().disconnect();
                showEmptyView(getHcActivity().getResources().getString(R.string.break_online));
                conmonTitleTextView.setText("对话（离线）");
                wifiError.setVisibility(View.GONE);
                if (message.getConfigId()==BaseConfig.ONLINE_STATE_MAX){
                    ChoiceDialog choiceDialog = new ChoiceDialog(getHcActivity(), "坐席数已满", 1);
                }
                if (message.getConfigId()==BaseConfig.ONLINE_STATE_REALAUTHEN){
                    ChoiceDialog choiceDialog = new ChoiceDialog(getHcActivity(), "根据网络安全法，请您先完成实名认证", 1);
                    choiceDialog.setCancelCallBack(new ChoiceDialog.ChoiceCancelCallBack() {
                        @Override
                        public void cancelBack() {
                            ((MainActivity)getActivity()).showIndex(2);
                        }

                        @Override
                        public void okBack(String s) {
                        }
                    });
                }
                break;
        }


    }

    public void initload() {
        haveListBeans.clear();
        notListBean.clear();
        colleagueListBean.clear();
        listUIBeans.clear();//展示的listUI集合
        notifisychronze(NOTIFI_ALL,0);
        listBeans.clear();//请求总集合
        mCounter = 0;//已接待对话总条数
        unReadCount = 0;//总的未读条数
        limit = 20;//限制每次访问多少条第一设置20条
        skip = 0;//跳过已获取的条数
        INDEXS = 0;
        String hash = (String) SharedPreferencesUtils.getParam(Constant.HASH, "");
        try {
            conmonTitleTextView.setText("获取中...");
            EventServiceImpl.getInstance().connect(hash);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

    /**
     * 同步刷新避免
     * java.lang.IndexOutOfBoundsException
     * Inconsistency detected. Invalid view holder adapter positionViewHolder
     */
    public synchronized void notifisychronze(int mode,int index){

        switch (mode){

            case NOTIFI_ALL:
                dialogListAdapter.notifyDataSetChanged();
                break;
            case NOTIFI_ITEM:
                dialogListAdapter.notifyItemChanged(index);
                break;
            case NOTIFI_DEL:
                dialogListAdapter.notifyDataSetChanged();
                break;
            case NOTIFI_REM:
                dialogListAdapter.remove(index);

                break;
        }

    }
}
