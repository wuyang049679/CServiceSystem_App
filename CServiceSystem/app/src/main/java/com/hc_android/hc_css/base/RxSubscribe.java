package com.hc_android.hc_css.base;

import android.util.Log;

import com.hc_android.hc_css.entity.MessageEntity;
import com.hc_android.hc_css.utils.socket.MessageEvent;
import com.hc_android.hc_css.utils.socket.MessageEventType;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

import static com.hc_android.hc_css.utils.Constant.EVENTBUS_HASH_STATE;
import static com.hc_android.hc_css.utils.Constant.UI_FRESH;

/**
 * 封装Subscribe订阅对象 统一处理回调码
 */

public abstract class RxSubscribe<T> implements Observer<BaseEntity<T>> {


    public RxSubscribe() {
    }

    protected abstract void onSuccess(T t);


    protected abstract void onFailed(int code, String msg);


    @Override
    public abstract void onSubscribe(@NonNull Disposable d);

    @Override
    public void onComplete() {
        // 比如隐藏加载中对话框
    }

    @Override
    public void onNext(BaseEntity<T> baseModel) {

        //code等于200为请求成功操作
        if (baseModel.code == 200) {
            onSuccess(baseModel.data);
        } else if (baseModel.code == 10003) {
            onFailed(baseModel.code,baseModel.msg);
            //通知hash过期重新登录
            MessageEntity message = new MessageEntity();
            message.setAct(EVENTBUS_HASH_STATE);
            MessageEvent event = new MessageEvent(MessageEventType.EventMessage, message);
            EventBus.getDefault().postSticky(event);
          //登录权限过期或失效
        }else {
            //请求失败
            onFailed(baseModel.code,"网络错误");

        }

    }

    @Override
    public void onError(Throwable t) {

        Log.i("onError", t.toString());
        if (t instanceof IllegalStateException) {
            //类型转换异常
            onFailed(1, t.getMessage());
        }else {
            onFailed(0, "网络错误:");
        }
//        else if (t instanceof HttpException) {
//            HttpException ex = (HttpException) t;
//            onFailed(ex.code(), ex.message());
//        } else {
//            onFailed(405, t.getMessage());
//        }
    }
}
