package com.hc_android.hc_css.presenter;

import com.hc_android.hc_css.base.BasePresenterIm;
import com.hc_android.hc_css.base.RxSubscribe;
import com.hc_android.hc_css.contract.ChatSetActivityContract;
import com.hc_android.hc_css.entity.CardEntity;
import com.hc_android.hc_css.entity.IneValuateEntity;
import com.hc_android.hc_css.entity.ReceptionEntity;
import com.hc_android.hc_css.model.ChatSetActivityModel;
import com.hc_android.hc_css.utils.android.ToastUtils;

import io.reactivex.disposables.Disposable;

public class ChatSetActivityPresenter extends BasePresenterIm<ChatSetActivityContract.View>implements ChatSetActivityContract.Presenter {

    ChatSetActivityModel chatSetActivityModel;

    public ChatSetActivityPresenter() {
        chatSetActivityModel=new ChatSetActivityModel();
    }

    @Override
    public void pGetCardList() {
        chatSetActivityModel.getCardList().subscribe(new RxSubscribe<CardEntity.DataBean>() {
            @Override
            protected void onSuccess(CardEntity.DataBean dataBean) {
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
    @Override
    public void pDialogTransfer(String serviceId,String dialogId) {
        chatSetActivityModel.dialogTransfer(serviceId,dialogId).subscribe(new RxSubscribe<IneValuateEntity.DataBean>() {
            @Override
            protected void onSuccess(IneValuateEntity.DataBean dataBean) {
                mView.showTransferSuccess(dataBean);
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
        chatSetActivityModel.endDialog(idList, offEnd, autoEnd).subscribe(new RxSubscribe<ReceptionEntity.DataBean>() {
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

    @Override
    public void pBlackAdd(String dialogId, String item) {
        chatSetActivityModel.blackAdd(dialogId,item).subscribe(new RxSubscribe<IneValuateEntity.DataBean>() {
            @Override
            protected void onSuccess(IneValuateEntity.DataBean dataBean) {
                mView.blackAddSuccess(dataBean);
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
    public void pBlackDel(String dialogId, String customerId) {
        chatSetActivityModel.blackDel(dialogId,customerId).subscribe(new RxSubscribe<IneValuateEntity.DataBean>() {
            @Override
            protected void onSuccess(IneValuateEntity.DataBean dataBean) {
                mView.blackDelSuccess(dataBean);
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
