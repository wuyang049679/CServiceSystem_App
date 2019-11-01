package com.hecong.cssystem.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gyf.barlibrary.ImmersionBar;
import com.hecong.cssystem.R;
import com.hecong.cssystem.adapter.DialogListAdapter;
import com.hecong.cssystem.api.Address;
import com.hecong.cssystem.base.BaseApplication;
import com.hecong.cssystem.base.BaseFragment;
import com.hecong.cssystem.contract.ChatListFragmentContract;
import com.hecong.cssystem.entity.MessageDialogEntity;
import com.hecong.cssystem.entity.MessageEntity;
import com.hecong.cssystem.entity.OffLineEntity;
import com.hecong.cssystem.presenter.ChatListFragmentPresenter;
import com.hecong.cssystem.ui.activity.ColleagueActivity;
import com.hecong.cssystem.ui.activity.NotReceivedActivity;
import com.hecong.cssystem.utils.Constant;
import com.hecong.cssystem.utils.DateUtils;
import com.hecong.cssystem.utils.JsonParseUtils;
import com.hecong.cssystem.utils.android.SharedPreferencesUtils;
import com.hecong.cssystem.utils.socket.EventListener;
import com.hecong.cssystem.utils.socket.EventServiceImpl;

import java.io.Serializable;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import io.socket.client.Ack;

public class ChatListFragment extends BaseFragment<ChatListFragmentPresenter, MessageDialogEntity.DataBean> implements ChatListFragmentContract.View, EventListener {

    private final String SERVER_URL = Address.SOCKET_URL + "?type=service&hash=";
    @BindView(R.id.backLayout)
    LinearLayout backLayout;
    @BindView(R.id.conmonTitleTextView)
    TextView conmonTitleTextView;
    @BindView(R.id.chat_recycler)
    RecyclerView chatRecycler;


    private DialogListAdapter dialogListAdapter;
    private List<MessageDialogEntity.DataBean.ListBean> haveListBeans,notListBean,colleagueListBean;
    private List<MessageDialogEntity.DataBean.ListBean> listUIBeans;//展示的listUI集合
    private List<MessageDialogEntity.DataBean.ListBean> listBeans;//请求总集合
    int mCounter = 0;//总的条数
    int limit=20;//限制每次访问多少条第一设置20条
    int skip=0;//跳过已获取的条数

    private String POSITION="item_position";
    private final int ONOFFLINE=100;
    private final int ONLINE=101;
    private final int NEW_MESSAGE=102;

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
        conmonTitleTextView.setText(getResources().getString(R.string.dialog_list));
        listBeans = new ArrayList<>();
        listUIBeans = new ArrayList<>();
        notListBean=new ArrayList<>();
        haveListBeans=new ArrayList<>();
        colleagueListBean=new ArrayList<>();
        dialogListAdapter = new DialogListAdapter(listUIBeans);
        LinearLayoutManager layoutParams = new LinearLayoutManager(getActivity());
        chatRecycler.setLayoutManager(layoutParams);
        chatRecycler.setAdapter(dialogListAdapter);
        dialogListAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()){
                case R.id.dialog_no_received_lin://点击未接待跳转
                    Bundle bundle=new Bundle();
                    bundle.putSerializable(Constant.NOTRECEIVED_LIST, (Serializable) notListBean);
                    startActivity(NotReceivedActivity.class,bundle);
                    break;
                case R.id.dialog_list_colleague_lin://点击同事对话跳转
                    Bundle bundle2=new Bundle();
                    bundle2.putSerializable(Constant.COLLEAGUE_LIST, (Serializable) colleagueListBean);
                    startActivity(ColleagueActivity.class,bundle2);
                    break;
                case R.id.close_btn:
                    listUIBeans.remove(position);
                    dialogListAdapter.notifyItemRemoved(position);
                    break;
            }


        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        String hash = (String) SharedPreferencesUtils.getParam(Constant.HASH, "");
        try {
            EventServiceImpl.getInstance().connect(hash);
            EventServiceImpl.getInstance().setEventListener(this);
            showLoadingView();
        } catch (URISyntaxException e) {
            showContentView();
            Toast.makeText(getActivity(), "Failed to connect to chat server.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
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

    }





    @SuppressLint("SetTextI18n")
    @Override
    public void showDialogList(MessageDialogEntity.DataBean messageEntity) {
        if (messageEntity.getList()!=null){
            List<MessageDialogEntity.DataBean.ListBean> list = messageEntity.getList();
            listBeans.addAll(list);
            skip=listBeans.size();
            for (MessageDialogEntity.DataBean.ListBean listBean : list) {
                if (listBean.getState().equals("active")&&listBean.getServiceId().equals(BaseApplication.getUserEntity().getServiceId())){
                    mCounter++;
                }
                conmonTitleTextView.setText(getResources().getString(R.string.dialog_list) + "(" + mCounter + ")");
            }
            if (list.size()==limit){
                if (limit==20){
                 //第一次请求20条刷新
                refreshUI(listBeans);
                showContentView();
                }
                //第二次请求将限制条数扩大到150
                limit=150;
                mPresenter.pShowMessageDialog(limit,skip);
            }else {
                //最后所有数据请求完成之后刷新
                refreshUI(listBeans);
            }

        }

    }

    /**
     * 新对话加入数据
     * @param messageEntity
     */
    @Override
    public void showNewDialog(MessageDialogEntity.DataBean messageEntity) {
            if (messageEntity.getDialog()!=null){
                listBeans.add(messageEntity.getDialog());
                refreshUI(listBeans);
                EventServiceImpl.getInstance().isOffLine(messageEntity.getDialog().getCustomerId(),messageEntity.getDialog().getServiceId());
            }
    }

    /**
     * 数据分类排序和刷新UI
     * @param list
     */

    private void refreshUI(List<MessageDialogEntity.DataBean.ListBean> list) {
        //加入房间操作
        for (MessageDialogEntity.DataBean.ListBean listBean : list) {
            EventServiceImpl.getInstance().joinRoom(listBean.getCustomerId());
        }
        listUIBeans.clear();
        notListBean.clear();
        colleagueListBean.clear();
        haveListBeans.clear();
        //先置顶排序后按时间倒序排序
        listSort(list);
        //数据分类 为接待/已经待/同事的对话
        for (MessageDialogEntity.DataBean.ListBean listBean : list) {
            if (listBean.getState().equals("unassigned")){
                notListBean.add(listBean);
            }else if (!listBean.getServiceId().equals(BaseApplication.getUserEntity().getServiceId())){ //如果serviceId不相同则为同事对话
                colleagueListBean.add(listBean);
            }else {
                listBean.setItemtype(Constant.HAVERECEIVED);
                haveListBeans.add(listBean);
            }
        }

        //添加到UI list集合

        if (haveListBeans.size()!=0) { //添加已接待的集合
            listUIBeans.addAll(haveListBeans);
        }
        if (colleagueListBean.size()==0){  //添加同事 的对话
            MessageDialogEntity.DataBean.ListBean bean = new MessageDialogEntity.DataBean.ListBean();
            MessageDialogEntity.DataBean.ListBean.LastMsgBean lastMsgBean = new MessageDialogEntity.DataBean.ListBean.LastMsgBean();
            lastMsgBean.setTime(DateUtils.getDateFormat(10000000));//如果没有同事的对话时，设置一个较小的时间戳，防止排序报错,将同事对话排在最后
            bean.setLastMsg(lastMsgBean);
            bean.setItemtype(Constant.COLLEAGUE);
            listUIBeans.add(bean);
        }else {
            //将同事 的最新一条数据添加到UI  list
            MessageDialogEntity.DataBean.ListBean bean = new MessageDialogEntity.DataBean.ListBean();
            try {
                bean = colleagueListBean.get(0).clone();//克隆避免原始数据被赋值
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            bean.setItemtype(Constant.COLLEAGUE);
            int unReadNum=0;
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
        if (notListBean.size()!=0){  //如果有未接待的先添加未接待集合
            //将未接待 的最新一条数据添加到UI  list
            MessageDialogEntity.DataBean.ListBean bean = new MessageDialogEntity.DataBean.ListBean();
            try {
                //将未接待的第一条克隆给UI列表
                bean= (MessageDialogEntity.DataBean.ListBean) (notListBean.get(0)).clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            //重新创建对象确保原始数据列表数据正常
            bean.setItemtype(Constant.NOTRECEIVED);
            //最后设置未读数量,避免未接待列表公用数据的不正确
            int unReadNum=0;
            for (MessageDialogEntity.DataBean.ListBean listBean : notListBean) {
                    unReadNum += listBean.getUnreadNum();
            }
            bean.setUnCount(notListBean.size());//设置未接待数量
            bean.setUnreadNum(unReadNum);//设置未读数量

            listUIBeans.add(0,bean); //添加未接待item到UI list的第一条
        }

        dialogListAdapter.notifyDataSetChanged();
    }



    /**
     * 先置顶排序后按lastMsgTime排序
     * @param listBeans
     */
    private void listSort(List<MessageDialogEntity.DataBean.ListBean> listBeans) {

        Collections.sort(listBeans, (o1, o2) -> {
            try {
                Date dt1 = DateUtils.getDate(o1.getLastMsg().getTime());
                Date dt2 = DateUtils.getDate(o2.getLastMsg().getTime());
                //先置顶排序后时间倒序排序
                if (o1.isTop()&&o2.isTop()){  //同时为top时根据时间倒序判断是否交换位置
                    if (dt1.getTime() < dt2.getTime()) {
                        return 1;
                    } else if (dt1.getTime() > dt2.getTime()) {
                        return -1;
                    } else {
                        return 0;
                    }
                }else if (o1.isTop()){
                    return -1;
                }else if (o2.isTop()){
                    return 1;
                }else {
                    //时间排序
                    if (dt1.getTime() < dt2.getTime()) {
                        return 1;
                    } else if (dt1.getTime() > dt2.getTime()) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 0;
        });

    }

    @Override
    public void onConnect(Object... args) {

    }

    @Override
    public void onDisconnect(Object... args) {

    }

    @Override
    public void onConnectError(Object... args) {

    }

    @Override
    public void onConnectTimeout(Object... args) {

    }

    @Override
    public void onNewMessage(Object... args) {
        MessageEntity messageBean = JsonParseUtils.parseToObject(args[0].toString(), MessageEntity.class);
        if (messageBean!=null){
            for (int i = 0; i < listBeans.size(); i++) {
                String id = listBeans.get(i).getId();
                if (id!=null&&id.equals(messageBean.getDialogId())){
                    listBeans.get(i).setUnreadNum(listBeans.get(i).getUnreadNum()+1);
                    MessageDialogEntity.DataBean.ListBean.LastMsgBean lastMsgBean=listBeans.get(i).getLastMsg();
                    lastMsgBean.setContents(messageBean.getMessage().getContents());
                    lastMsgBean.setType(messageBean.getMessage().getType());
                    lastMsgBean.setTime(DateUtils.getDateFormat(messageBean.getMessage().getTime()));
                    listBeans.get(i).setLastMsg(lastMsgBean);
                    Message message = mHandler.obtainMessage();
                    message.what = NEW_MESSAGE;
                    Bundle bundle = new Bundle();
                    bundle.putInt(POSITION, i);
                    message.setData(bundle);
                    message.sendToTarget();
                }
            }

        }
    }

    /**
     * 新对话加入
     * @param args
     */
    @Override
    public void onNewDialog(Object... args) {
        MessageEntity messageBean = JsonParseUtils.parseToObject(args[0].toString(), MessageEntity.class);
        if (messageBean!=null){
            EventServiceImpl.getInstance().joinRoom(messageBean.getCustomerId(), new Ack() {
                @Override
                public void call(Object... args) {//先加入房间等服务器回执再请求对话数据
                    mPresenter.pGetDialog(messageBean.getDialogId());
                    for (int i = 0; i < args.length; i++) {
                        Log.i(TAG, "ACK: onNewDialog" + args[i]);
                    }
                }
            });
        }
    }

    @Override
    public void onUserJoined(Object... args) {

    }

    @Override
    public void onUserLeft(Object... args) {

    }

    @Override
    public void onTyping(Object... args) {

    }

    @Override
    public void onStopTyping(Object... args) {

    }

    @Override
    public void onUserState(Object... args) {

    }

    @Override
    public void onOffLine(Object... args) {


        OffLineEntity offLineEntity = JsonParseUtils.parseToObject(args[0].toString(), OffLineEntity.class);
        if (offLineEntity!=null){
            String customId=offLineEntity.getCustomerId();
            for (int i = 0; i < listUIBeans.size(); i++) {
                String customerId = listUIBeans.get(i).getCustomerId();
                if (customerId!=null&&customerId.equals(customId)) {
                    listUIBeans.get(i).setCustomerOffTime(DateUtils.getDateFormat(DateUtils.getCurrentTimeInLong()));
                    Message message = mHandler.obtainMessage();
                    message.what = ONOFFLINE;
                    Bundle bundle = new Bundle();
                    bundle.putInt(POSITION, i);
                    message.setData(bundle);
                    message.sendToTarget();
                }
            }

        }
    }

    @Override
    public void onLine(Object... args) {

        OffLineEntity offLineEntity = JsonParseUtils.parseToObject(args[0].toString(), OffLineEntity.class);
        if (offLineEntity!=null){
            String customId=offLineEntity.getCustomerId();
            for (int i = 0; i < listUIBeans.size(); i++) {
                String customerId = listUIBeans.get(i).getCustomerId();
                if (customerId!=null&&customerId.equals(customId)) {
                    listUIBeans.get(i).setCustomerOffTime(null);
                    Message message = mHandler.obtainMessage();
                    message.what = ONLINE;
                    Bundle bundle = new Bundle();
                    bundle.putInt(POSITION, i);
                    message.setData(bundle);
                    message.sendToTarget();
                }
            }
        }
    }

    /**
     * 登录连接成功获取对话列表，并加入房间
     * @param args
     */
    @Override
    public void onLogSuc(Object... args) {

        mPresenter.pShowMessageDialog(limit, skip);
    }

    @SuppressLint("HandlerLeak")
    private  Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            Bundle bundle = msg.getData();
            switch (msg.what){

                case ONOFFLINE://用户离线通知
                    int index = bundle.getInt(POSITION);
                    dialogListAdapter.notifyItemChanged(index);
                    break;
                case ONLINE://用户上线通知
                    int online_index = bundle.getInt(POSITION);
                    dialogListAdapter.notifyItemChanged(online_index);
                    break;
                case NEW_MESSAGE://新消息通知
//                    int message_index = bundle.getInt(POSITION);
//                    dialogListAdapter.notifyDataSetChanged();
                    refreshUI(listBeans);
                    break;
            }
        }
    };
}
