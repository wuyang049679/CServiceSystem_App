package com.hc_android.hc_css.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;


import com.hc_android.hc_css.base.BaseApplication;
import com.hc_android.hc_css.base.BaseConfig;
import com.hc_android.hc_css.entity.MessageEntity;
import com.hc_android.hc_css.utils.socket.MessageEvent;
import com.hc_android.hc_css.utils.socket.MessageEventType;
import com.shuyu.gsyvideoplayer.utils.NetworkUtils;

import org.greenrobot.eventbus.EventBus;

import static com.hc_android.hc_css.utils.Constant.EVENTBUS_NETWORK_STATE;
import static com.hc_android.hc_css.utils.Constant.EVENTBUS_NOTIFICATION_STATE;


/**
 * Created by dingmouren on 2018/8/1.
 * 监听网络状态变更的广播接收器
 */

public class NetworkConnectChangedReceiver extends BroadcastReceiver {

    private static final String TAG = "NetworkChangedReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction() == ConnectivityManager.CONNECTIVITY_ACTION) {
            /*判断当前网络时候可用以及网络类型*/
            boolean isConnected = NetworkUtils.isConnected(BaseApplication.getBaseApplication().getApplicationContext());
            int netWorkType = NetworkUtils.getNetWorkType(BaseApplication.getBaseApplication().getApplicationContext());
            MessageEntity message = new MessageEntity();
            message.setAct(EVENTBUS_NETWORK_STATE);//在线状态
            message.setConnected(isConnected);
            message.setNetWorkType(netWorkType);
            MessageEvent event = new MessageEvent(MessageEventType.EventMessage, message);
            EventBus.getDefault().postSticky(event);
        }
    }
}