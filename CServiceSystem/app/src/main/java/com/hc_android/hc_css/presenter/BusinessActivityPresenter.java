package com.hc_android.hc_css.presenter;

import com.hc_android.hc_css.base.BasePresenterIm;
import com.hc_android.hc_css.base.RxSubscribe;
import com.hc_android.hc_css.contract.BusinessActivityContract;
import com.hc_android.hc_css.entity.BusinessEntity;
import com.hc_android.hc_css.entity.TokenEntity;
import com.hc_android.hc_css.entity.VerityEntity;
import com.hc_android.hc_css.model.BusinessActivityModel;
import com.hc_android.hc_css.utils.android.ToastUtils;

import io.reactivex.disposables.Disposable;

public class BusinessActivityPresenter extends BasePresenterIm<BusinessActivityContract.View> implements BusinessActivityContract.Presenter {

    BusinessActivityModel businessActivityModel;

    public BusinessActivityPresenter() {
        businessActivityModel = new BusinessActivityModel();
    }

    @Override
    public void pGetToken(String type, String fileType) {

        businessActivityModel.getToken(type,fileType).subscribe(new RxSubscribe<TokenEntity.DataBean>() {
            @Override
            protected void onSuccess(TokenEntity.DataBean dataBean) {
                if (mView!=null)mView.showToken(dataBean);
            }

            @Override
            protected void onFailed(int code, String msg) {
                mView.showContentView();
                ToastUtils.showShort(msg);
            }

            @Override
            public void onSubscribe(Disposable d) {
                addSubscription(d);
            }
        });
    }
    @Override
    public void pVerityBusiness(String image) {
        businessActivityModel.verityBusiness(image).subscribe(new RxSubscribe<BusinessEntity.DataBean>() {
            @Override
            protected void onSuccess(BusinessEntity.DataBean s) {
                mView.showDataSuccess(s);
            }

            @Override
            protected void onFailed(int code, String msg) {
                mView.showContentView();
//                ToastUtils.showShort(msg);
            }

            @Override
            public void onSubscribe(Disposable d) {
                addSubscription(d);
            }

           });
    }
}
