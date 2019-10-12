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

    protected abstract void onSuccess(T t,String hash);


    protected abstract void onFailed(int code, String msg);


    @Override
    public abstract void onSubscribe(@NonNull Disposable d);

    @Override
    public void onComplete() {
        // 比如隐藏加载中对话框
    }

    @Override
    public void onNext(BaseEntity<T> baseModel) {
        if (baseModel.get_err()!=null){
      //  判断返回数据是为_err如果为_err直接返回异常
            onFailed(baseModel.get_err(),"网络服务异常");
        }else {
        if (baseModel.get_suc()!=null&&baseModel.getHash()!=null){
       // 如果为suc正常返回数据
            onSuccess(baseModel.info,baseModel.getHash());

        }else {
            onFailed(baseModel.get_suc(),baseModel.getText());
        }
        }
    }

    @Override
    public void onError(Throwable t) {
        onFailed(1000,"网络错误");
        Log.i("onError",t.toString());
//        if (t instanceof ConnectException) {
//            //网络连接失败
//            onFailed(403, t.getMessage());
//        } else if (t instanceof HttpException) {
//            HttpException ex = (HttpException) t;
//            onFailed(ex.code(), ex.message());
//        } else {
//            onFailed(405, t.getMessage());
//        }
    }
}
