package com.hc_android.hc_css.presenter;

import com.hc_android.hc_css.base.BasePresenterIm;
import com.hc_android.hc_css.base.RxSubscribe;
import com.hc_android.hc_css.contract.UpdatePwdActivityContract;
import com.hc_android.hc_css.entity.IneValuateEntity;
import com.hc_android.hc_css.model.UpdatePwdActivityModel;
import com.hc_android.hc_css.utils.android.ToastUtils;

import io.reactivex.disposables.Disposable;

public class UpdatePwdActivityPresenter extends BasePresenterIm<UpdatePwdActivityContract.View>implements UpdatePwdActivityContract.Presenter {

    UpdatePwdActivityModel updatePwdActivityModel;

    public UpdatePwdActivityPresenter() {
        updatePwdActivityModel=new UpdatePwdActivityModel();
    }

    @Override
    public void pAccountModifyPwd(String password, String newpassword) {
        updatePwdActivityModel.accountModifyPwd(password,newpassword).subscribe(new RxSubscribe<IneValuateEntity.DataBean>() {
            @Override
            protected void onSuccess(IneValuateEntity.DataBean dataBean) {
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
    public void pAccountState(String state) {
        updatePwdActivityModel.accountState(state).subscribe(new RxSubscribe<IneValuateEntity.DataBean>() {
            @Override
            protected void onSuccess(IneValuateEntity.DataBean dataBean) {
                mView.showState(dataBean);
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
