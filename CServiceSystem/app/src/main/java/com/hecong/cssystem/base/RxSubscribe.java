package com.hecong.cssystem.base;

import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import java.net.ConnectException;

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
        Log.i("onNext",baseModel.toString());
        Integer resultCode = null;
         if (baseModel.code != null) {
            resultCode = baseModel.code;
            if (resultCode==200){
                onSuccess(baseModel.data);
            }else {
                onFailed(baseModel.code,baseModel.msg);
            }
        }else {
            onFailed(-1,"访问服务器异常");
        }

    }

    @Override
    public void onError(Throwable t) {
        Log.i("onError",t.toString());
        if (t instanceof ConnectException) {
            //网络连接失败
            onFailed(403, t.getMessage());
        } else if (t instanceof HttpException) {
            HttpException ex = (HttpException) t;
            onFailed(ex.code(), ex.message());
        } else {
            onFailed(405, t.getMessage());
        }
    }
}
