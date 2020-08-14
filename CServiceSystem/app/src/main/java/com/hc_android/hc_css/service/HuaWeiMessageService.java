package com.hc_android.hc_css.service;

import android.util.Log;

import com.huawei.hms.push.HmsMessageService;

public class HuaWeiMessageService extends HmsMessageService {

    private static final String TAG = "wy_activity";
    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        Log.i(TAG, "receive token:" + token);
        PushSendService.sendToken(token,"huawei");
    }

}
