package com.hc_android.hc_css.presenter;

import com.hc_android.hc_css.base.BasePresenterIm;
import com.hc_android.hc_css.base.RxSubscribe;
import com.hc_android.hc_css.contract.PhoneVerificationContract;
import com.hc_android.hc_css.entity.IneValuateEntity;
import com.hc_android.hc_css.entity.VerityEntity;
import com.hc_android.hc_css.model.PhoneVerificationModel;
import com.hc_android.hc_css.utils.android.ToastUtils;

import io.reactivex.disposables.Disposable;

public class PhoneVerificationPresenter extends BasePresenterIm<PhoneVerificationContract.View> implements PhoneVerificationContract.Presenter {

    PhoneVerificationModel phoneVerificationModel;

    public PhoneVerificationPresenter() {
        phoneVerificationModel=new PhoneVerificationModel();

    }

    @Override
    public void pVerityPhone(String token, String tel) {
        phoneVerificationModel.verityPhone(token,tel).subscribe(new RxSubscribe<VerityEntity.DataBean>() {
            @Override
            protected void onSuccess(VerityEntity.DataBean dataBean) {
                if (dataBean.isPass()){
                    mView.showDataSuccess(dataBean);
                }else {
                    ToastUtils.showShort("号码验证失败,请检查是否是本机号码");
                }
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
        phoneVerificationModel.accountModify(info).subscribe(new RxSubscribe<IneValuateEntity.DataBean>() {
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
