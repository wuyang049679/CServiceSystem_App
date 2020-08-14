package com.hc_android.hc_css.presenter;

import com.hc_android.hc_css.base.BasePresenterIm;
import com.hc_android.hc_css.base.RxSubscribe;
import com.hc_android.hc_css.contract.NotReceivedActivityContract;
import com.hc_android.hc_css.entity.ReceptionEntity;
import com.hc_android.hc_css.model.NotReceivedActivityModel;
import com.hc_android.hc_css.utils.android.ToastUtils;

import io.reactivex.disposables.Disposable;

public class NotReceivedActivityPresenter extends BasePresenterIm<NotReceivedActivityContract.View>implements NotReceivedActivityContract.Presenter {

    NotReceivedActivityModel receivedActivityModel;


    public NotReceivedActivityPresenter() {
        receivedActivityModel=new NotReceivedActivityModel();
    }

    @Override
    public void pReceptionDialog(String dialogId) {
        receivedActivityModel.receptionDialog(dialogId).subscribe(new RxSubscribe<ReceptionEntity.DataBean>() {
            @Override
            protected void onSuccess(ReceptionEntity.DataBean dataBean) {
                mView.showDataSuccess(dataBean);
            }

            @Override
            protected void onFailed(int code, String msg) {
                ToastUtils.showShort(msg);
            }

            @Override
            public void onSubscribe(Disposable d) {
                addSubscription(d);
            }
        });
    }
    @Override
    public void pEndDialog(String idList, String offEnd, String autoEnd) {
        receivedActivityModel.endDialog(idList, offEnd, autoEnd).subscribe(new RxSubscribe<ReceptionEntity.DataBean>() {
            @Override
            protected void onSuccess(ReceptionEntity.DataBean dataBean) {
                mView.showEndDialog(dataBean);
            }

            @Override
            protected void onFailed(int code, String msg) {
                ToastUtils.showShort(msg);
            }

            @Override
            public void onSubscribe(Disposable d) {
                addSubscription(d);
            }
        });
    }
}
