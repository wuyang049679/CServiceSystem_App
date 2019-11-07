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
import com.hecong.cssystem.entity.ReceptionEntity;
import com.hecong.cssystem.entity.UserEntity;
import com.hecong.cssystem.presenter.ChatListFragmentPresenter;
import com.hecong.cssystem.ui.activity.ColleagueActivity;
import com.hecong.cssystem.ui.activity.MainActivity;
import com.hecong.cssystem.ui.activity.NotReceivedActivity;
import com.hecong.cssystem.utils.Constant;
import com.hecong.cssystem.utils.DateUtils;
import com.hecong.cssystem.utils.JsonParseUtils;
import com.hecong.cssystem.utils.JsonUtils;
import com.hecong.cssystem.utils.ThreadUtils;
import com.hecong.cssystem.utils.android.SharedPreferencesUtils;
import com.hecong.cssystem.utils.socket.EventListener;
import com.hecong.cssystem.utils.socket.EventServiceImpl;
import com.hecong.cssystem.wight.LocalDataSource;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

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
    private List<MessageDialogEntity.DataBean.ListBean> haveListBeans;
    private List<MessageDialogEntity.DataBean.ListBean> notListBean;
    private List<MessageDialogEntity.DataBean.ListBean> colleagueListBean;
    private List<MessageDialogEntity.DataBean.ListBean> listUIBeans;//展示的listUI集合
    private List<MessageDialogEntity.DataBean.ListBean> listBeans;//请求总集合
    int mCounter = 0;//已接待对话总条数
    int unReadCount = 0;//总的未读条数
    int limit=20;//限制每次访问多少条第一设置20条
    int skip=0;//跳过已获取的条数
    int INDEXS=0;
    private String POSITION="item_position";
    private final int ONOFFLINE=100;
    private final int ONLINE=101;
    private final int NEW_MESSAGE=102;
    private final int UPDATE_MESSAGE=103;
    private final int LEAVE_DIALOG=104;
    private final int RECEPTION_DIALOG=105;

    ExecutorService singlePool;

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
            MessageDialogEntity.DataBean.ListBean item = (MessageDialogEntity.DataBean.ListBean) adapter.getData().get(position);
            switch (view.getId()){
                case R.id.dialog_no_received_lin://点击未接待跳转

                    LocalDataSource.setNOTLIST(notListBean);
                    Bundle bundle=new Bundle();
                    bundle.putInt(Constant.HAVERECEIVED_NUM,mCounter);
                    startActivity(NotReceivedActivity.class,bundle);
                    break;
                case R.id.dialog_list_colleague_lin://点击同事对话跳转
                    String json= JsonUtils.list2json(notListBean);
                    Bundle bundle2=new Bundle();
                    bundle2.putString(Constant.COLLEAGUE_LIST, json);
                    startActivity(ColleagueActivity.class,bundle2);
                    break;
                case R.id.close_btn://点击结束
                    INDEXS=position;
                    String idList=item.getId();
                    mPresenter.pEndDialog(idList,null,null);
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

       singlePool = ThreadUtils.getSinglePool();
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
        UserEntity userEntity=BaseApplication.getUserEntity();
        if (messageEntity!=null) {
            if (messageEntity.getAutoEnd()!=null)userEntity.setAutoEnd(messageEntity.getAutoEnd());
            if (messageEntity.getOffEnd()!=null)userEntity.setOffEnd(messageEntity.getOffEnd());
        }
        if (messageEntity.getList()!=null){
            List<MessageDialogEntity.DataBean.ListBean> list = messageEntity.getList();
            listBeans.addAll(list);
            skip=listBeans.size();

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
                //请求完成数据之后批量自动结束对话
                endDialogList(messageEntity);
                //最后所有数据请求完成之后刷新
                refreshUI(listBeans);
            }

        }

    }

    /**
     * 自动结束对话
     * @param messageEntity
     */
    private void endDialogList(MessageDialogEntity.DataBean messageEntity) {
        List<String> autoList=new ArrayList<>();
        List<String> offList=new ArrayList<>();
        if (messageEntity!=null){
        //超过设置时间自动过期结束
        if (BaseApplication.getUserEntity().getAutoEnd()!=null){
            if (BaseApplication.getUserEntity().getAutoEnd().isState()){

                long autoTime=(BaseApplication.getUserEntity().getAutoEnd().getTime())*60*1000;//分钟转毫秒
                for(Iterator<MessageDialogEntity.DataBean.ListBean> it = listBeans.iterator(); it.hasNext();){
                    MessageDialogEntity.DataBean.ListBean listBean = it.next();
                    String lastMsg=null;
                    if (listBean.getState().equals("active")){
                        if (listBean.getReceptionTime()!=null){
                            long receptionTime = DateUtils.getDate(listBean.getReceptionTime()).getTime();
                            //接待时间如果大于最后一条消息时间则以最后一条消息时间为最后时间
                            if (receptionTime>DateUtils.getDate(listBean.getLastMsg().getTime()).getTime()){
                                lastMsg=listBean.getLastMsg().getTime();
                            }
                        }
                        lastMsg=listBean.getLastMsg().getTime()!=null?listBean.getLastMsg().getTime():listBean.getAddtime();//是否存在lastmsg
                        long time=autoTime-(DateUtils.getCurrentTimeInLong()-DateUtils.getDate(lastMsg).getTime());//比较时间差
                        if (time<=0){
                            autoList.add(listBean.getId());
                            it.remove();
                        }
                    }
                }

                int counts= (int) Math.ceil((autoList.size())/200f);//计算需要上传次数(向上取整)
                if (counts>0) {
                    for (int i = 0; i < counts; i++) {
                        String idList=null;
                        int size=autoList.size()-i*200>200?(i+1)*200:autoList.size();//计算size(每次最多结束200)
                        for (int x = i*200; x< size; x++) {
                            if (idList==null){
                                idList=autoList.get(x);
                            }else {
                                idList+=","+autoList.get(x);
                            }
                        }

                        mPresenter.pEndDialog(idList,null,"true");
                    }
                }
            }
        //离线超过时间没有新消息
            if (BaseApplication.getUserEntity().getOffEnd()!=null&&BaseApplication.getUserEntity().getOffEnd().isState()){
                long offTime=(BaseApplication.getUserEntity().getOffEnd().getTime())*1000;
                for(Iterator<MessageDialogEntity.DataBean.ListBean> it = listBeans.iterator(); it.hasNext();){
                    MessageDialogEntity.DataBean.ListBean listBean = it.next();
                    if (listBean.getState().equals("active")&&listBean.getCustomerOffTime()!=null){
                        if (listBean.getSource().equals("web")||listBean.getSource().equals("link")){
                            if (listBean.getReceptionTime()!=null){
                                long time=0;
                                long receptionTime = DateUtils.getDate(listBean.getReceptionTime()).getTime();
                                //接待时间如果大于最后一条消息时间则以最后一条消息时间为最后时间
                                if (receptionTime>DateUtils.getDate(listBean.getCustomerOffTime()).getTime()){
                                    time=DateUtils.getDate(listBean.getCustomerOffTime()).getTime();
                                }
                            }
                            long time=offTime-(DateUtils.getCurrentTimeInLong()-DateUtils.getDate(listBean.getCustomerOffTime()).getTime());//比较时间差
                            if (time<=0){
                                offList.add(listBean.getId());
                                it.remove();
                            }
                        }
                    }
                }
                int counts= (int) Math.floor(offList.size()/200f);//计算需要上传次数
                if (counts>0) {
                    for (int i = 0; i < counts; i++) {
                        String idList=null;
                        int size=offList.size()-i*200>200?(i+1)*200:offList.size();
                        for (int x = i*200; i < size; x++) {
                            if (idList==null){
                                idList=offList.get(x);
                            }else {
                                idList+=","+offList.get(x);
                            }
                        }
                        mPresenter.pEndDialog(idList,"true",null);
                    }
                }
            }
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
     * 对话结束成功
     * @param messageEntity
     */
    @Override
    public void showEndDialog(ReceptionEntity.DataBean messageEntity) {

    }

    /**
     * 数据分类排序和刷新UI
     * @param list
     */

    @SuppressLint("NewApi")
    private  synchronized void refreshUI(List<MessageDialogEntity.DataBean.ListBean> list) {
        //加入房间操作

        for(Iterator<MessageDialogEntity.DataBean.ListBean> it = list.iterator(); it.hasNext();) {
            EventServiceImpl.getInstance().joinRoom(it.next().getCustomerId());
        }
        listUIBeans.clear();
        notListBean.clear();
        colleagueListBean.clear();
        haveListBeans.clear();
        //先置顶排序后按时间倒序排序
        listSort(list);
        //数据分类 为接待/已经待/同事的对话
        for(Iterator<MessageDialogEntity.DataBean.ListBean> it = list.iterator(); it.hasNext();) {
            MessageDialogEntity.DataBean.ListBean listBean=it.next();
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
        listBeans.stream().distinct().collect(Collectors.toList());
        for (int i = 0; i < listBeans.size(); i++) {
            for (int j = 0; j < listBeans.size(); j++) {
                if(i!=j&&listBeans.get(i).getId().equals(listBeans.get(j).getId())) {
                    listBeans.remove(listBeans.get(j));
                }
            }
        }
        dialogListAdapter.notifyDataSetChanged();
        checkDialogNum();
}

    /**
     * 检验对话数是否已满,和底部未读
     */
    @SuppressLint("SetTextI18n")
    private void checkDialogNum() {
        mCounter=0;
        unReadCount=0;
        for (MessageDialogEntity.DataBean.ListBean listBean : listBeans) {
            if (listBean.getState().equals("active")&&listBean.getServiceId().equals(BaseApplication.getUserEntity().getServiceId())){
                mCounter++;
            }

            //更新未读数
            if (listBean.getServiceId().equals(BaseApplication.getUserEntity().getServiceId())||listBean.getState().equals("unassigned")){
                if (!listBean.isAssignGrade()||listBean.getServiceId().equals(BaseApplication.getUserEntity().getServiceId())||BaseApplication.getUserEntity().isFounding()) {
                    if (listBean.getUnreadNum() != 0) {
                        unReadCount += listBean.getUnreadNum();
                    }
                }
            }
        }
        if (mCounter>=BaseApplication.getUserEntity().getMaxChat()) {
            conmonTitleTextView.setText(getResources().getString(R.string.dialog_list) + "(对话数已满)");
        }
        if (unReadCount!=0){
            ((MainActivity) getActivity()).getNavigationBar().setMsgPointCount(0,unReadCount);
        }else {
            ((MainActivity) getActivity()).getNavigationBar().clearAllMsgPoint();
        }
        }


    /**
     * 先置顶排序后按lastMsgTime排序
     * @param listBeans
     */
    private synchronized void listSort(List<MessageDialogEntity.DataBean.ListBean> listBeans) {

        Collections.sort(listBeans, new Comparator<MessageDialogEntity.DataBean.ListBean>() {
            @Override
            public int compare(MessageDialogEntity.DataBean.ListBean o1, MessageDialogEntity.DataBean.ListBean o2) {
                try {
                    Date dt1 = DateUtils.getDate(o1.getLastMsg().getTime());
                    Date dt2 = DateUtils.getDate(o2.getLastMsg().getTime());
                    //先置顶排序后时间倒序排序
                    if (o1.isTop() && o2.isTop()) {  //同时为top时根据时间倒序判断是否交换位置
                        if (dt1.getTime() < dt2.getTime()) {
                            return 1;
                        } else if (dt1.getTime() > dt2.getTime()) {
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
            }
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
        if (messageBean.getAutoMsgType()!=null&&messageBean.getAutoMsgType().equals("end"))return;
        if (messageBean.getMessage().getOneway()!=null&&messageBean.getMessage().getOneway().equals("service"))return;//如果是服务器推送则不更新消息
        if (messageBean!=null){
            Message message = mHandler.obtainMessage();
            message.what = NEW_MESSAGE;
            Bundle bundle = new Bundle();
            bundle.putString(Constant.DIALOGID, messageBean.getDialogId());
            message.setData(bundle);
            message.sendToTarget();
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

    /**
     * 更新对话
     * @param args
     */
    @Override
    public void onUpdateDialog(Object... args) {
        MessageEntity messageBean = JsonParseUtils.parseToObject(args[0].toString(), MessageEntity.class);

        if (messageBean!=null&& messageBean.getItem()!=null){
            MessageDialogEntity.DataBean.ListBean item = messageBean.getItem();
            for (MessageDialogEntity.DataBean.ListBean listUIBean : listBeans) {
                if (listUIBean.getId().equals(item.getId())){  //比较对话id替换对话原来对话的数据
                    MessageDialogEntity.DataBean.ListBean.CustomerBean _customer = listUIBean.getCustomer();
                    MessageDialogEntity.DataBean.ListBean.CustomerBean customer= item.getCustomer();
                    if (customer!=null){//更新顾客信息
                    if (customer.getName()!=null)_customer.setName(customer.getName());
                    if (customer.getHead()!=null)_customer.setHead(customer.getHead());
                    if (customer.getNumberId()!=0)_customer.setNumberId(customer.getNumberId());
                    if (customer.getCard()!=null)_customer.setCard(customer.getCard());
                    if (customer.getAttCard()!=null)_customer.setAttCard(customer.getAttCard());
                    if (customer.getAddress()!=null)_customer.setAddress(customer.getAddress());
                    if (customer.getAddtime()!=null)_customer.setAddtime(customer.getAddtime());
                    }
                    //更新未读消息
                    if (item.getRead()!=0)listUIBean.setUnreadNum(0);//清空未读消息
                    //置顶
                    if (item.isTop())listUIBean.setTop(true);
                }
            }
            Message message = mHandler.obtainMessage();
            message.what = UPDATE_MESSAGE;
            message.sendToTarget();
        }
    }

    /**
     * 结束对话
     * @param args
     */
    @Override
    public void onLeaveDialog(Object... args) {
        MessageEntity messageBean = JsonParseUtils.parseToObject(args[0].toString(), MessageEntity.class);
        if (messageBean!=null){
            Message message = mHandler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString(Constant.DIALOGID, messageBean.getDialogId());
            message.setData(bundle);
            message.what = LEAVE_DIALOG;
            message.sendToTarget();
        }
    }

    /**
     * 对话被接待
     * @param args
     */
    @Override
    public void onReception(Object... args) {
        MessageEntity messageBean = JsonParseUtils.parseToObject(args[0].toString(), MessageEntity.class);
        if (messageBean!=null){
            for (MessageDialogEntity.DataBean.ListBean listBean : listBeans) {
                if (listBean.getId().equals(messageBean.getDialogId())){
                    listBean.setServiceId(messageBean.getServiceId());
                    listBean.setState("active");
                    listBean.setReceptionTime(DateUtils.getDateFormat(messageBean.getReceptionTime()));
                }
            }
            Message message = mHandler.obtainMessage();
            message.what = RECEPTION_DIALOG;
            message.sendToTarget();
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
//        if(item.serviceId == myInfo.id || item.state == 'unassigned'){
//            if(!item.assignGrade || item.serviceId == myInfo.id || myInfo.founding)unread += item.unreadNum;
//        }
    }

    @Override
    public void onOffLine(Object... args) {

            singlePool.submit(new Runnable() {
                @Override
                public void run() {

                }
            });
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
                    String dialogIds = bundle.getString(Constant.DIALOGID);
                    for (int i = 0; i < listBeans.size(); i++) {
                        String id = listBeans.get(i).getId();
                        if (id!=null&&id.equals(dialogIds)){
                            listBeans.get(i).setUnreadNum(listBeans.get(i).getUnreadNum()+1);
                            MessageDialogEntity.DataBean.ListBean.LastMsgBean lastMsgBean=listBeans.get(i).getLastMsg();
                            lastMsgBean.setContents("新消息000000000000000000000000000000");
                            lastMsgBean.setType("text");
                            lastMsgBean.setTime(DateUtils.getDateFormat(DateUtils.getCurrentTimeInLong()));
                            listBeans.get(i).setLastMsg(lastMsgBean);

                        }
                    }
                    refreshUI(listBeans);
                    break;
                case UPDATE_MESSAGE://更新对话信息
//                    List<MessageDialogEntity.DataBean.ListBean>  lists2 = new ArrayList(Arrays.asList(new Object[listBeans.size()]));
//                    Collections.copy(lists2,listBeans);
//                    refreshUI(lists2);
                    refreshUI(listBeans);
                    break;
                case LEAVE_DIALOG://结束对话
                    String dialogId = bundle.getString(Constant.DIALOGID);
                    for(Iterator<MessageDialogEntity.DataBean.ListBean> it = listBeans.iterator(); it.hasNext();) {

                        if (it.next().getId().equals(dialogId)){
                            it.remove();
                            break;
                        }
                    }
                    refreshUI(listBeans);
                    break;
                case RECEPTION_DIALOG://对话被接

                    refreshUI(listBeans);
                    break;
            }
        }
    };
}
