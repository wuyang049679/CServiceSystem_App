package com.hc_android.hc_css.presenter;

import com.hc_android.hc_css.base.BasePresenterIm;
import com.hc_android.hc_css.base.RxSubscribe;
import com.hc_android.hc_css.contract.MainActivityContract;
import com.hc_android.hc_css.entity.AppUpdateEntity;
import com.hc_android.hc_css.entity.LoginEntity;
import com.hc_android.hc_css.entity.TagEntity;
import com.hc_android.hc_css.model.MainActivityModel;
import com.hc_android.hc_css.utils.thread.RetryWithDelay;

import io.reactivex.disposables.Disposable;

/**
 * Desccribe:
 *
 * @author Created by wuyang on 2019/9/2
 */
public class MainActivityPresenter extends BasePresenterIm<MainActivityContract.View>implements MainActivityContract.Presenter {

    MainActivityModel mainActivityModel;

    public MainActivityPresenter() {
        this.mainActivityModel=new MainActivityModel();
    }


    @Override
    public void pCheckLogin(String hash) {
        mainActivityModel.checkLogin(hash).retryWhen(new RetryWithDelay(1,1000)).subscribe(new RxSubscribe<LoginEntity.DataBean>() {
            @Override
            protected void onSuccess(LoginEntity.DataBean loginEntity) {
                mView.showDataSuccess(loginEntity);
            }

            @Override
            protected void onFailed(int code, String msg) {
                mView.showDataError(msg);
            }

            @Override
            public void onSubscribe(Disposable d) {
                addSubscription(d);
            }
        });
    }

    @Override
    public void pGetTagList() {
        mainActivityModel.getTagList().retryWhen(new RetryWithDelay(3,2000)).subscribe(new RxSubscribe<TagEntity.DataBean>() {
            @Override
            protected void onSuccess(TagEntity.DataBean loginEntity) {
                mView.showTagList(loginEntity);
            }

            @Override
            protected void onFailed(int code, String msg) {
            }

            @Override
            public void onSubscribe(Disposable d) {
                addSubscription(d);
            }
        });
    }

    @Override
    public void pAppUpdate() {
        mainActivityModel.appUpdate().retryWhen(new RetryWithDelay(2,2000)).subscribe(new RxSubscribe<AppUpdateEntity.DataBean>() {
            @Override
            protected void onSuccess(AppUpdateEntity.DataBean dataBean) {
                mView.showAppUpdate(dataBean);
            }

            @Override
            protected void onFailed(int code, String msg) {

            }

            @Override
            public void onSubscribe(Disposable d) {
                addSubscription(d);
            }
        });
    }
}
