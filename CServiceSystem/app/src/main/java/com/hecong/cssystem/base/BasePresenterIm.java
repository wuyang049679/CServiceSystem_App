package com.hecong.cssystem.base;

import android.content.Context;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


/**
 * Created by wuyang on 2019/8/1.
 */

public abstract class BasePresenterIm<V extends BaseView> implements BasePresenter {

    public V mView;
    public Context mContext;

    private CompositeDisposable mCompositeSubscription;

    void attachView(V view, Context context) {
        this.mView = view;
        this.mContext = context;
    }

    @Override
    public Boolean checkNetWork(Context context) {
//        if (!NerWorkUtilss.isNetWorkAvailable(context)) {
//             //ToastUtils.show(context, "没有网络了");
//        }
        return null;
    }


    /**
     * 事件订阅
     */
    public void addSubscription(Disposable s) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeDisposable();
        }
        this.mCompositeSubscription.add(s);
    }


    @Override
    public void unsubcrible() {
        if (this.mCompositeSubscription != null) {
            this.mCompositeSubscription.clear();
        }
        mView = null;
        mContext = null;
        this.mCompositeSubscription = null;
    }

}
