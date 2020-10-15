package com.hc_android.hc_css.service;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.hc_android.hc_css.api.ApiManager;
import com.hc_android.hc_css.base.BaseApplication;
import com.hc_android.hc_css.base.RxSubscribe;
import com.hc_android.hc_css.entity.IneValuateEntity;
import com.hc_android.hc_css.entity.UpdateUserEntity;
import com.hc_android.hc_css.ui.activity.MainActivity;
import com.hc_android.hc_css.utils.Constant;
import com.hc_android.hc_css.utils.JsonParseUtils;
import com.hc_android.hc_css.utils.android.RomUtil;
import com.hc_android.hc_css.utils.android.SharedPreferencesUtils;
import com.hc_android.hc_css.utils.android.ToastUtils;
import com.hc_android.hc_css.utils.thread.RetryWithDelay;
import com.huawei.agconnect.config.AGConnectServicesConfig;
import com.huawei.hms.aaid.HmsInstanceId;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 发送唯一id为服务端
 */
public class PushSendService {

    static String TAG="wy_activity";
    private static Context mContext;
    /**
     * 根据设备机型获取token对接厂商推送服务
     */

    public static void initPush(Context context){
        mContext=context;
        if (RomUtil.isMiui()){//小米推送
//            ToastUtils.showShort("小米");
            String regId = XMMessageReceiver.getRegId();
            if (regId!=null){
                sendToken(regId,"xiaomi");
            }
        }else if (RomUtil.isOppo()){//oppo推送
//            ToastUtils.showShort("OPPO");
            String regId = OPPOPushReceiver.getRegisterId();

            if (regId!=null){
                sendToken(regId,"oppo");
            }
        }else if (RomUtil.isEmui()){//是华为手机
            getToken();
        }

    }
    /**
     * 发送token给服务器
     */

    public static void sendToken(String token,String type){
        String hash = (String) SharedPreferencesUtils.getParam(Constant.HASH, "");
        UpdateUserEntity userEntity=new UpdateUserEntity();
        userEntity.setRegistrationId(token);
        userEntity.setRegistrationIdType(type);
        String info = JsonParseUtils.parseToJson(userEntity);
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put(Constant.REQUEST_TYPE,Constant.STANDARD);
        hashMap.put(Constant.KEY_HASH, hash);
        hashMap.put("info",info);
        ApiManager.getApistore().accountModify(hashMap).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new RxSubscribe<IneValuateEntity.DataBean>() {
            @Override
            protected void onSuccess(IneValuateEntity.DataBean dataBean) {

                Log.i(TAG,"发送成功");
            }

            @Override
            protected void onFailed(int code, String msg) {
                Log.i(TAG,"PUSH发送失败");
//                ToastUtils.showShort("PUSH发送失败");
            }

            @Override
            public void onSubscribe(Disposable d) {

            }
        });
    }


    /**
     * 获取token
     */
    public static void getToken() {
        Log.i(TAG, "get token: begin");

        // get token
        new Thread() {
            @Override
            public void run() {
                try {
                    String appId = AGConnectServicesConfig.fromContext(mContext).getString("client/app_id");
                    Log.i(TAG, "appId:" + appId);
                    String pushtoken = HmsInstanceId.getInstance(mContext).getToken(appId, "HCM");
                    if(!TextUtils.isEmpty(pushtoken)) {
                        Log.i(TAG, "get token:" + pushtoken);
                        PushSendService.sendToken(pushtoken,"huawei");
//                        showLog(pushtoken);
                    }
                } catch (Exception e) {
                    Log.i(TAG,"getToken failed, " + e);

                }
            }
        }.start();
    }

}
