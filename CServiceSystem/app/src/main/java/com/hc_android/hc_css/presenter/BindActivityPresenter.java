package com.hc_android.hc_css.presenter;

import com.hc_android.hc_css.base.BasePresenterIm;
import com.hc_android.hc_css.base.RxSubscribe;
import com.hc_android.hc_css.contract.BindActivityContract;
import com.hc_android.hc_css.entity.IneValuateEntity;
import com.hc_android.hc_css.model.BindActivityModel;
import com.hc_android.hc_css.utils.android.ToastUtils;

import io.reactivex.disposables.Disposable;

public class BindActivityPresenter extends BasePresenterIm<BindActivityContract.View> implements BindActivityContract.Presenter {

    BindActivityModel bindActivityModel;

    public BindActivityPresenter() {
        bindActivityModel = new BindActivityModel();
    }

    @Override
    public void pVerification(String mobile, String email) {
        bindActivityModel.verification(mobile, email).subscribe(new RxSubscribe<IneValuateEntity.DataBean>() {
            @Override
            protected void onSuccess(IneValuateEntity.DataBean dataBean) {
                mView.showDataSuccess(dataBean);
            }

            @Override
            protected void onFailed(int code, String msg) {
                mView.showEroor(code,msg);
            }

            @Override
            public void onSubscribe(Disposable d) {
                    addSubscription(d);
            }
        });
    }

    @Override
    public void pVercode(String mobile, String email, String type, String antiBrush) {

        bindActivityModel.vercode(mobile, email, type,antiBrush).subscribe(new RxSubscribe<IneValuateEntity.DataBean>() {
            @Override
            protected void onSuccess(IneValuateEntity.DataBean dataBean) {
                mView.showVercode(dataBean);
            }

            @Override
            protected void onFailed(int code, String msg) {
                mView.showEroor(code,msg);
            }

            @Override
            public void onSubscribe(Disposable d) {
                addSubscription(d);
            }
        });
    }

    @Override
    public void pRelievebind(String type) {
        bindActivityModel.relievebind(type).subscribe(new RxSubscribe<IneValuateEntity.DataBean>() {
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

    @Override
    public void pBind(String mobile, String email, String code) {
        bindActivityModel.bind(mobile, email, code).subscribe(new RxSubscribe<IneValuateEntity.DataBean>() {
            @Override
            protected void onSuccess(IneValuateEntity.DataBean dataBean) {
                mView.showBindSuccess(dataBean);
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
    public void pRegisiter(String fields) {
        bindActivityModel.regisiter(fields).subscribe(new RxSubscribe<IneValuateEntity.DataBean>() {
            @Override
            protected void onSuccess(IneValuateEntity.DataBean dataBean) {
                mView.showRegisiterSuccess(dataBean);
            }

            @Override
            protected void onFailed(int code, String msg) {
                mView.showRegisiterEroor(msg);
            }

            @Override
            public void onSubscribe(Disposable d) {
                addSubscription(d);
            }
        });
    }
}
