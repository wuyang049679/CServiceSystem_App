package com.hecong.cssystem.presenter;

import com.hecong.cssystem.base.BasePresenterIm;
import com.hecong.cssystem.base.RxSubscribe;
import com.hecong.cssystem.contract.NotReceivedActivityContract;
import com.hecong.cssystem.entity.ReceptionEntity;
import com.hecong.cssystem.model.NotReceivedActivityModel;
import com.hecong.cssystem.utils.android.ToastUtils;

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
