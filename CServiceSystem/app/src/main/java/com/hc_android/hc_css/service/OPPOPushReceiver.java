package com.hc_android.hc_css.service;

import android.util.Log;

import com.heytap.mcssdk.callback.PushAdapter;
import com.heytap.mcssdk.mode.SubscribeResult;

import java.util.Arrays;
import java.util.List;

public class OPPOPushReceiver  extends PushAdapter {


    private static String registerId=null;
    @Override
    public void onRegister(int code, String s) {
        if (code == 0) {
            Log.i("注册成功", "registerId:" + s);
            registerId=s;
        } else {
            Log.i("注册失败", "code=" + code + ",msg=" + s);
        }
    }

    @Override
    public void onUnRegister(int code) {
        if (code == 0) {
            Log.i("注销成功", "code=" + code);
        } else {
            Log.i("注销失败", "code=" + code);
        }
    }

    @Override
    public void onGetAliases(int code, List<SubscribeResult> list) {
        if (code == 0) {
            Log.i("获取别名成功", "code=" + code + ",msg=" + Arrays.toString(list.toArray()));
        } else {
            Log.i("获取别名失败", "code=" + code);
        }
    }

    @Override
    public void onSetAliases(int code, List<SubscribeResult> list) {
        if (code == 0) {
            Log.i("设置别名成功", "code=" + code + ",msg=" + Arrays.toString(list.toArray()));
        } else {
            Log.i("设置别名失败", "code=" + code);
        }
    }

    @Override
    public void onUnsetAliases(int code, List<SubscribeResult> list) {
        if (code == 0) {
            Log.i("取消别名成功", "code=" + code + ",msg=" + Arrays.toString(list.toArray()));
        } else {
            Log.i("取消别名失败", "code=" + code);
        }
    }

    @Override
    public void onSetTags(int code, List<SubscribeResult> list) {
        if (code == 0) {
            Log.i("设置标签成功", "code=" + code + ",msg=" + Arrays.toString(list.toArray()));
        } else {
            Log.i("设置标签失败", "code=" + code);
        }
    }

    @Override
    public void onUnsetTags(int code, List<SubscribeResult> list) {
        if (code == 0) {
            Log.i("取消标签成功", "code=" + code + ",msg=" + Arrays.toString(list.toArray()));
        } else {
            Log.i("取消标签失败", "code=" + code);
        }
    }

    @Override
    public void onGetTags(int code, List<SubscribeResult> list) {
        if (code == 0) {
            Log.i("获取标签成功", "code=" + code + ",msg=" + Arrays.toString(list.toArray()));
        } else {
            Log.i("获取标签失败", "code=" + code);
        }
    }


    @Override
    public void onGetPushStatus(final int code, int status) {
        if (code == 0 && status == 0) {
            Log.i("Push状态正常", "code=" + code + ",status=" + status);
        } else {
            Log.i("Push状态错误", "code=" + code + ",status=" + status);
        }
    }

    @Override
    public void onGetNotificationStatus(final int code, final int status) {
        if (code == 0 && status == 0) {
            Log.i("通知状态正常", "code=" + code + ",status=" + status);
        } else {
            Log.i("通知状态错误", "code=" + code + ",status=" + status);
        }
    }

    @Override
    public void onSetPushTime(final int code, final String s) {
        Log.i("SetPushTime", "code=" + code + ",result:" + s);
    }

    public static String getRegisterId() {
        Log.i("wy_activity","_oppo_mRegId:"+registerId+"");
        return registerId;
    }


}
