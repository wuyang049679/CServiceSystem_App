package com.hc_android.hc_css.presenter;

import com.hc_android.hc_css.base.BasePresenterIm;
import com.hc_android.hc_css.base.RxSubscribe;
import com.hc_android.hc_css.contract.LocalDataContract;
import com.hc_android.hc_css.entity.TagEntity;
import com.hc_android.hc_css.model.LocalDataModel;

import io.reactivex.disposables.Disposable;

public class LocalDataPresenter extends BasePresenterIm<LocalDataContract.View> implements LocalDataContract.Presenter {


    LocalDataModel localDataModel;
    public LocalDataPresenter() {
        localDataModel=new LocalDataModel();
    }

    @Override
    public void pGetTagList() {
        localDataModel.getTagList().subscribe(new RxSubscribe<TagEntity.DataBean>() {
            @Override
            protected void onSuccess(TagEntity.DataBean listBean) {
                mView.show(listBean);
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
