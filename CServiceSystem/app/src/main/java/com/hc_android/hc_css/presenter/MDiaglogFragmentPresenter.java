package com.hc_android.hc_css.presenter;

import com.hc_android.hc_css.base.BasePresenterIm;
import com.hc_android.hc_css.base.RxSubscribe;
import com.hc_android.hc_css.contract.MDiaglogFragmentContract;
import com.hc_android.hc_css.entity.IneValuateEntity;
import com.hc_android.hc_css.entity.QuickEntity;
import com.hc_android.hc_css.entity.QuickGroupEntity;
import com.hc_android.hc_css.model.MDiaglogFragmentModel;
import com.hc_android.hc_css.utils.android.ToastUtils;

import io.reactivex.disposables.Disposable;

public class MDiaglogFragmentPresenter extends BasePresenterIm<MDiaglogFragmentContract.View> implements MDiaglogFragmentContract.Presenter {


    MDiaglogFragmentModel mDiaglogFragmentModel;

    public MDiaglogFragmentPresenter() {
        mDiaglogFragmentModel=new MDiaglogFragmentModel();
    }

    @Override
    public void pGetQuickList(String serviceId, boolean team) {
        mDiaglogFragmentModel.getQuickList(serviceId,team).subscribe(new RxSubscribe<QuickEntity.DataBean>() {
            @Override
            protected void onSuccess(QuickEntity.DataBean dataBean) {

                mView.showQuickList(dataBean);
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
    public void pGetQuickGroup(String serviceId, String type) {
        mDiaglogFragmentModel.getQuickGroup(serviceId,type).subscribe(new RxSubscribe<QuickGroupEntity.DataBean>() {
            @Override
            protected void onSuccess(QuickGroupEntity.DataBean dataBean) {

                mView.showQuickGroup(dataBean);
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
    public void pQuickUse(String id) {
        mDiaglogFragmentModel.quickUse(id).subscribe(new RxSubscribe<IneValuateEntity.DataBean>() {
            @Override
            protected void onSuccess(IneValuateEntity.DataBean dataBean) {

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
