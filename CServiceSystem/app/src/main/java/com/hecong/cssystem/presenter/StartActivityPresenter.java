package com.hecong.cssystem.presenter;

import com.hecong.cssystem.base.BasePresenterIm;
import com.hecong.cssystem.base.RxSubscribe;
import com.hecong.cssystem.contract.StartActivityContract;
import com.hecong.cssystem.entity.LoginEntity;
import com.hecong.cssystem.model.StartActivityModel;

import io.reactivex.disposables.Disposable;

public class StartActivityPresenter extends BasePresenterIm<StartActivityContract.View>implements StartActivityContract.Presenter {

    StartActivityModel startActivityModel;

    public StartActivityPresenter() {
        startActivityModel=new StartActivityModel();
    }

    @Override
    public void pCheckLogin(String hash) {
        startActivityModel.checkLogin(hash).subscribe(new RxSubscribe<LoginEntity>() {
            @Override
            protected void onSuccess(LoginEntity loginEntity, String hash) {
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
