package com.hc_android.hc_css.service;

import android.util.Log;

import com.huawei.hms.push.HmsMessageService;
import com.huawei.hms.push.RemoteMessage;

public class HuaWeiMessageService extends HmsMessageService {

    private static final String TAG = "wy_activity";
    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        Log.i(TAG, "receive token:" + token);
        PushSendService.sendToken(token,"huawei");

    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.i(TAG, "receive RemoteMessage:" + remoteMessage);
    }

}
