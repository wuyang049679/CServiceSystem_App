package com.hecong.cssystem.base;

import android.util.Log;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

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
          //登录权限过期或失效
        }else {
            //请求失败
            onFailed(baseModel.code,"网络错误");
        }

    }

    @Override
    public void onError(Throwable t) {
        onFailed(0, "网络错误:0x1");
        Log.i("onError", t.toString());
        if (t instanceof IllegalStateException) {
            //类型转换异常
            onFailed(1, t.getMessage());
        }
//        else if (t instanceof HttpException) {
//            HttpException ex = (HttpException) t;
//            onFailed(ex.code(), ex.message());
//        } else {
//            onFailed(405, t.getMessage());
//        }
    }
}
