package com.hecong.cssystem.ui.fragment;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.hecong.cssystem.R;
import com.hecong.cssystem.base.BaseFragment;
import com.hecong.cssystem.utils.Constant;
import com.hecong.cssystem.utils.android.SharedPreferencesUtils;
import com.hecong.cssystem.utils.socket.ServerConnection;

import butterknife.BindView;
import butterknife.OnClick;

public class ChatListFragment extends BaseFragment implements ServerConnection.ServerListener {

    private final String SERVER_URL = "http://192.168.0.105:17106/?type=service";
    @BindView(R.id.backLayout)
    LinearLayout backLayout;
    @BindView(R.id.conmonTitleTextView)
    TextView conmonTitleTextView;
    @BindView(R.id.message_from_server)
    TextView messageFromServer;
    @BindView(R.id.server_connection_status)
    TextView serverConnectionStatus;
    @BindView(R.id.send_message_button)
    Button sendMessageButton;
    private ServerConnection mServerConnection;
    int mCounter = 0;
    public static ChatListFragment newInstance() {

        Bundle args = new Bundle();

        ChatListFragment fragment = new ChatListFragment();
        fragment.setArguments(args);
        return fragment;

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
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        String hash= (String) SharedPreferencesUtils.getParam(Constant.HASH,"");
        mServerConnection = new ServerConnection(SERVER_URL+"&hash="+hash);
        sendMessageButton.setEnabled(false);
        mServerConnection.connect(this);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

    }


    @Override
    public void showDataError(String errorMessage) {

    }

    @Override
    public void showDataSuccess(Object datas) {

    }

    @Override
    public void onNewMessage(String message) {
        messageFromServer.setText(message);
    }

    @Override
    public void onStatusChange(ServerConnection.ConnectionStatus status) {
        String statusMsg = (status == ServerConnection.ConnectionStatus.CONNECTED ?
               "已连接" : "未连接");
        serverConnectionStatus.setText(statusMsg);
        sendMessageButton.setEnabled(status == ServerConnection.ConnectionStatus.CONNECTED);
    }

    @OnClick(R.id.send_message_button)
    public void onViewClicked() {
        mServerConnection.sendMessage(""+mCounter++);
    }
}
