package com.hc_android.hc_css.presenter;

import com.hc_android.hc_css.base.BasePresenterIm;
import com.hc_android.hc_css.base.RxSubscribe;
import com.hc_android.hc_css.contract.HistoryActivityContract;
import com.hc_android.hc_css.entity.MessageDialogEntity;
import com.hc_android.hc_css.model.HistoryActivityModel;

import io.reactivex.disposables.Disposable;

public class HistoryActivityPresenter extends BasePresenterIm<HistoryActivityContract.View> implements HistoryActivityContract.Presenter {


    HistoryActivityModel historyActivityModel;
    public HistoryActivityPresenter() {
        historyActivityModel=new HistoryActivityModel();

    }

    @Override
    public void pGetHistoryList(String condition, int limit, int skip) {

        historyActivityModel.getHistoryList(condition,limit,skip).subscribe(new RxSubscribe<MessageDialogEntity.DataBean>() {
            @Override
            protected void onSuccess(MessageDialogEntity.DataBean dataBean) {
                mView.showDataSuccess(dataBean);
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
