package com.hecong.cssystem.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.hecong.cssystem.R;
import com.hecong.cssystem.adapter.DialogListAdapter;
import com.hecong.cssystem.api.Address;
import com.hecong.cssystem.base.BaseFragment;
import com.hecong.cssystem.contract.ChatListFragmentContract;
import com.hecong.cssystem.entity.MessageDialogEntity;
import com.hecong.cssystem.entity.OffLineEntity;
import com.hecong.cssystem.presenter.ChatListFragmentPresenter;
import com.hecong.cssystem.utils.Constant;
import com.hecong.cssystem.utils.DateUtils;
import com.hecong.cssystem.utils.android.SharedPreferencesUtils;
import com.hecong.cssystem.utils.socket.EventListener;
import com.hecong.cssystem.utils.socket.EventServiceImpl;
import com.hecong.cssystem.utils.socket.ServerConnection;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ChatListFragment extends BaseFragment<ChatListFragmentPresenter, MessageDialogEntity.DataBean> implements ChatListFragmentContract.View, EventListener {

    private final String SERVER_URL = Address.SOCKET_URL + "?type=service&hash=";
    @BindView(R.id.backLayout)
    LinearLayout backLayout;
    @BindView(R.id.conmonTitleTextView)
    TextView conmonTitleTextView;
    @BindView(R.id.chat_recycler)
    RecyclerView chatRecycler;


    private DialogListAdapter dialogListAdapter;
    private ServerConnection mServerConnection;
    private List<MessageDialogEntity.DataBean.ListBean> listBeans;
    int mCounter = 0;//总的条数
    int limit=20;//限制每次访问多少条第一设置20条
    int skip=0;//跳过已获取的条数

    private String POSITION="item_position";
    private final int ONOFFLINE=100;
    private final int ONLINE=101;

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
        return 0;
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
        MessageDialogEntity.DataBean.ListBean listBean = new MessageDialogEntity.DataBean.ListBean();
        listBean.setItemtype(Constant.NOTRECEIVED);
        listBeans.add(listBean);
        dialogListAdapter = new DialogListAdapter(listBeans);
        LinearLayoutManager layoutParams = new LinearLayoutManager(getActivity());
        chatRecycler.setLayoutManager(layoutParams);
        chatRecycler.setAdapter(dialogListAdapter);
        dialogListAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId()==R.id.close_btn) {
                listBeans.remove(position);
                dialogListAdapter.notifyItemRemoved(position);
            }

        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        String hash = (String) SharedPreferencesUtils.getParam(Constant.HASH, "");
        try {
            EventServiceImpl.getInstance().connect(hash);
            EventServiceImpl.getInstance().setEventListener(this);
        } catch (URISyntaxException e) {
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





    @Override
    public void showDialogList(MessageDialogEntity.DataBean messageEntity) {

        if (messageEntity.getList()!=null&&messageEntity.getList().size()!=0){
            List<MessageDialogEntity.DataBean.ListBean> list = messageEntity.getList();
            for (MessageDialogEntity.DataBean.ListBean listBean : list) {
                listBean.setItemtype(Constant.HAVERECEIVED);
            }
            listBeans.addAll(list);
            skip=listBeans.size();
            mCounter=listBeans.size();
            conmonTitleTextView.setText(getResources().getString(R.string.dialog_list)+"("+listBeans.size()+")");


            if (list.size()==limit){
                if (limit==20){
                    //第一次请求20条刷新
                    dialogListAdapter.notifyDataSetChanged();
                }
                //第二次请求将限制条数扩大到150
                limit=150;
                mPresenter.pShowMessageDialog(limit,skip);
            }else {
                //最后所有数据请求完成之后刷新
                dialogListAdapter.notifyDataSetChanged();
            }

        }

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

        Gson gson=new Gson();
        OffLineEntity offLineEntity = gson.fromJson(args[0].toString(), OffLineEntity.class);
        if (offLineEntity!=null){
            String customId=offLineEntity.getCustomerId();
            for (int i = 0; i < listBeans.size(); i++) {
                if (listBeans.get(i).getCustomerId().equals(customId)) {
                    listBeans.get(i).setCustomerOffTime(DateUtils.getCurrentTimeInString());
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
        Gson gson=new Gson();
        OffLineEntity offLineEntity = gson.fromJson(args[0].toString(), OffLineEntity.class);
        if (offLineEntity!=null){
            String customId=offLineEntity.getCustomerId();
            for (int i = 0; i < listBeans.size(); i++) {
                if (listBeans.get(i).getCustomerId().equals(customId)) {
                    listBeans.get(i).setCustomerOffTime(null);
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
    private Handler mHandler = new Handler() {
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
            }
        }
    };
}
