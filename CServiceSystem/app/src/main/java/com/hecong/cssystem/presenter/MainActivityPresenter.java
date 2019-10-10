package com.hecong.cssystem.presenter;

import com.hecong.cssystem.base.BasePresenterIm;
import com.hecong.cssystem.base.RxSubscribe;
import com.hecong.cssystem.contract.MainActivityContract;
import com.hecong.cssystem.entity.MineEntity;
import com.hecong.cssystem.model.MainActivityModel;

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
    public void pexample(String version) {

        mainActivityModel.forexample(version).subscribe(new RxSubscribe<MineEntity>() {
            @Override
            protected void onSuccess(MineEntity mineEntity) {
                mView.showContentView();
                mView.showSuccess(mineEntity);
            }

            @Override
            protected void onFailed(int code, String msg) {
                mView.showDataError(msg);
            }

            @Override
            public void onSubscribe(Disposable d) {
                mView.showLoadingView();
                addSubscription(d);
            }
        });
    }
}
