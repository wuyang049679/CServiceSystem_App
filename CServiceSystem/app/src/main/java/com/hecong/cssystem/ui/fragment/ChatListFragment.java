package com.hecong.cssystem.ui.fragment;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gyf.barlibrary.ImmersionBar;
import com.hecong.cssystem.R;
import com.hecong.cssystem.adapter.DialogListAdapter;
import com.hecong.cssystem.api.Address;
import com.hecong.cssystem.base.BaseFragment;
import com.hecong.cssystem.contract.ChatListFragmentContract;
import com.hecong.cssystem.entity.MessageDialogEntity;
import com.hecong.cssystem.presenter.ChatListFragmentPresenter;
import com.hecong.cssystem.utils.Constant;
import com.hecong.cssystem.utils.android.SharedPreferencesUtils;
import com.hecong.cssystem.utils.socket.ServerConnection;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ChatListFragment extends BaseFragment<ChatListFragmentPresenter, MessageDialogEntity.DataBean> implements ChatListFragmentContract.View, ServerConnection.ServerListener {

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
        dialogListAdapter = new DialogListAdapter(listBeans);
        LinearLayoutManager layoutParams = new LinearLayoutManager(getActivity());
        chatRecycler.setLayoutManager(layoutParams);
        chatRecycler.setAdapter(dialogListAdapter);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        String hash = (String) SharedPreferencesUtils.getParam(Constant.HASH, "");
        mServerConnection = new ServerConnection(SERVER_URL + hash);
        mServerConnection.connect(this);
        mPresenter.pShowMessageDialog(limit, skip);
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
    public void onNewMessage(String message) {

    }

    @Override
    public void onStatusChange(ServerConnection.ConnectionStatus status) {
        String statusMsg = (status == ServerConnection.ConnectionStatus.CONNECTED ?
                "已连接" : "未连接");

    }


    @Override
    public void showDialogList(MessageDialogEntity.DataBean messageEntity) {

        if (messageEntity.getList()!=null&&messageEntity.getList().size()!=0){
            List<MessageDialogEntity.DataBean.ListBean> list = messageEntity.getList();
            listBeans.addAll(list);
            skip=listBeans.size();
            mCounter=listBeans.size();
            conmonTitleTextView.setText(getResources().getString(R.string.dialog_list)+"("+listBeans.size()+")");
            dialogListAdapter.notifyDataSetChanged();

            if (list.size()==limit){
                //第二次请求将限制条数扩大到150
                limit=150;
                mPresenter.pShowMessageDialog(limit,skip);
            }

        }

    }
}
