package com.hc_android.hc_css.presenter;

import com.hc_android.hc_css.base.BasePresenterIm;
import com.hc_android.hc_css.base.RxSubscribe;
import com.hc_android.hc_css.contract.RealAuthenActivityContract;
import com.hc_android.hc_css.entity.IneValuateEntity;
import com.hc_android.hc_css.entity.VerityEntity;
import com.hc_android.hc_css.entity.VerityResultEntity;
import com.hc_android.hc_css.model.RealAuthenActivityModel;
import com.hc_android.hc_css.utils.android.ToastUtils;

import io.reactivex.disposables.Disposable;

public class RealAuthenActivityPresenter  extends BasePresenterIm<RealAuthenActivityContract.View> implements RealAuthenActivityContract.Presenter {


    RealAuthenActivityModel realAuthenActivityModel;

    public RealAuthenActivityPresenter() {
        realAuthenActivityModel=new RealAuthenActivityModel();
    }

    @Override
    public void pVerityToken(String type,String name,String idCard) {
        realAuthenActivityModel.verityToken(type,name,idCard).subscribe(new RxSubscribe<VerityEntity.DataBean>() {
            @Override
            protected void onSuccess(VerityEntity.DataBean dataBean) {

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
    public void pVerityResult(String realtype,String type,String bizId) {

        realAuthenActivityModel.verityResult(realtype,type,bizId).subscribe(new RxSubscribe<VerityResultEntity.DataBean>() {
            @Override
            protected void onSuccess(VerityResultEntity.DataBean dataBean) {
                mView.showResult(dataBean);
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
    public void pAccountModify(String info) {
        realAuthenActivityModel.accountModify(info).subscribe(new RxSubscribe<IneValuateEntity.DataBean>() {
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
}
