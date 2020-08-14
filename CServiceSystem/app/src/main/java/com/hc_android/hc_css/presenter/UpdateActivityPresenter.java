package com.hc_android.hc_css.presenter;

import com.hc_android.hc_css.base.BasePresenterIm;
import com.hc_android.hc_css.base.RxSubscribe;
import com.hc_android.hc_css.contract.UpdateActivityContract;
import com.hc_android.hc_css.entity.IneValuateEntity;
import com.hc_android.hc_css.model.UpdateActivityModel;
import com.hc_android.hc_css.utils.android.ToastUtils;

import io.reactivex.disposables.Disposable;

public class UpdateActivityPresenter extends BasePresenterIm<UpdateActivityContract.View> implements UpdateActivityContract.Presenter {


    UpdateActivityModel updateActivityModel;
    public UpdateActivityPresenter() {
        updateActivityModel=new UpdateActivityModel();
    }

    @Override
    public void pUpdateCards(String dialogId, String item) {
        updateActivityModel.updateCards(dialogId,item).subscribe(new RxSubscribe<IneValuateEntity.DataBean>() {
            @Override
            protected void onSuccess(IneValuateEntity.DataBean dataBean) {
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
    public void pUpdateRemarks(String dialogId, String remarks) {
        updateActivityModel.updateRemarks(dialogId,remarks).subscribe(new RxSubscribe<IneValuateEntity.DataBean>() {
            @Override
            protected void onSuccess(IneValuateEntity.DataBean dataBean) {
                mView.updateRemarksSuccess(dataBean);
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
    public void pTagUsege(String mids) {
        updateActivityModel.tagUsege(mids).subscribe(new RxSubscribe<IneValuateEntity.DataBean>() {
            @Override
            protected void onSuccess(IneValuateEntity.DataBean dataBean) {

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
    public void pAccountModify(String info) {
        updateActivityModel.accountModify(info).subscribe(new RxSubscribe<IneValuateEntity.DataBean>() {
            @Override
            protected void onSuccess(IneValuateEntity.DataBean dataBean) {
                mView.accountModifySuccess(dataBean);
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
