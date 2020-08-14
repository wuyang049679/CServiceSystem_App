package com.hc_android.hc_css.base;

import android.util.Log;

import com.hc_android.hc_css.api.ApiManager;
import com.hc_android.hc_css.entity.IneValuateEntity;
import com.hc_android.hc_css.entity.MessageEntity;
import com.hc_android.hc_css.entity.UpdateUserEntity;
import com.hc_android.hc_css.utils.Constant;
import com.hc_android.hc_css.utils.JsonParseUtils;
import com.hc_android.hc_css.utils.android.SharedPreferencesUtils;
import com.hc_android.hc_css.utils.android.ToastUtils;
import com.hc_android.hc_css.utils.socket.EventServiceImpl;
import com.hc_android.hc_css.utils.socket.MessageEvent;
import com.hc_android.hc_css.utils.thread.RetryWithDelay;

import org.greenrobot.eventbus.EventBus;

import java.net.URISyntaxException;
import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.hc_android.hc_css.utils.Constant.EVENTBUS_NOTIFICATION_STATE;
import static com.hc_android.hc_css.utils.socket.MessageEventType.EventMessage;

/**
 * 全局配置信息，状态信息
 */
public class BaseConfig {

    public final static int ONLINE_STATE=1001;//在线状态
    public final static int ONLINE_STATE_DEFULTE=1000;//在线状态_默认类型
    public final static int ONLINE_STATE_MAX=1002;//在线状态_坐席数已满
    public final static int ONLINE_STATE_REALAUTHEN=1004;//在线状态_要求实名认证

    public static String _ONLINE_STATE="";


    /**
     *
     * @param OnlineState
     * 是否请求api
     * @param toApi
     */
    public static void setStateChange(String OnlineState,boolean toApi,int type) {
//        if (_ONLINE_STATE.equals(OnlineState))return;//如果是一样的状态则不改变

        if (toApi) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put(Constant.REQUEST_TYPE, Constant.STANDARD);
            hashMap.put(Constant.KEY_HASH, BaseApplication.getUserEntity().getHash());
            hashMap.put("state", OnlineState);
            ApiManager.getApistore().accountState(hashMap).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(new RxSubscribe<IneValuateEntity.DataBean>() {
                @Override
                protected void onSuccess(IneValuateEntity.DataBean dataBean) {
                    if (dataBean.get_suc() == 1) {

                        _ONLINE_STATE = OnlineState;
                        switch (_ONLINE_STATE){
                            case "on":
                            case "off":
                                String hash = (String) SharedPreferencesUtils.getParam(Constant.HASH, "");
                                try {
                                    EventServiceImpl.getInstance().connect(hash);
                                } catch (URISyntaxException e) {
                                    e.printStackTrace();
                                }
                                break;
                            case "break":
                                _ONLINE_STATE=OnlineState;
                                BaseApplication.getUserBean().setState(OnlineState);
                                EventServiceImpl.getInstance().disconnect();
                                MessageEntity message = new MessageEntity();
                                message.setAct(EVENTBUS_NOTIFICATION_STATE);//通知离线
                                message.setState(OnlineState);
                                message.setConfigId(type);
                                MessageEvent event = new MessageEvent(EventMessage, message);
                                EventBus.getDefault().postSticky(event);
                                break;

                        }

                    }
                }

                @Override
                protected void onFailed(int code, String msg) {
                    ToastUtils.showShort("发送失败");
                }

                @Override
                public void onSubscribe(Disposable d) {

                }
            });
        }else {
            _ONLINE_STATE=OnlineState;
            BaseApplication.getUserBean().setState(OnlineState);
            MessageEntity message = new MessageEntity();
            message.setAct(EVENTBUS_NOTIFICATION_STATE);//通知离线
            message.setState(OnlineState);
            MessageEvent event = new MessageEvent(EventMessage, message);
            EventBus.getDefault().postSticky(event);
        }
    }
}
