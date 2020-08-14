package com.hc_android.hc_css.presenter;

import com.hc_android.hc_css.base.BasePresenterIm;
import com.hc_android.hc_css.base.RxSubscribe;
import com.hc_android.hc_css.contract.StartActivityContract;
import com.hc_android.hc_css.entity.LoginEntity;
import com.hc_android.hc_css.model.StartActivityModel;

import io.reactivex.disposables.Disposable;

public class StartActivityPresenter extends BasePresenterIm<StartActivityContract.View>implements StartActivityContract.Presenter {

    StartActivityModel startActivityModel;

    public StartActivityPresenter() {
        startActivityModel=new StartActivityModel();
    }

    @Override
    public void pCheckLogin(String hash) {
        startActivityModel.checkLogin(hash).subscribe(new RxSubscribe<LoginEntity.DataBean>() {
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
}
