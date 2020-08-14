package com.hc_android.hc_css.presenter;

import com.hc_android.hc_css.base.BasePresenterIm;
import com.hc_android.hc_css.base.RxSubscribe;
import com.hc_android.hc_css.contract.LoginActivityContract;
import com.hc_android.hc_css.entity.LoginEntity;
import com.hc_android.hc_css.model.LoginActivityModel;

import io.reactivex.disposables.Disposable;

public class LoginActivityPresenter extends BasePresenterIm<LoginActivityContract.View> implements LoginActivityContract.Presenter {

    LoginActivityModel activityModel;

    public LoginActivityPresenter() {
        this.activityModel = new LoginActivityModel();
    }

    @Override
    public void pLogin(String username, String password, String imei) {

        activityModel.login(username,password,imei).subscribe(new RxSubscribe<LoginEntity.DataBean>() {
            @Override
            protected void onSuccess(LoginEntity.DataBean loginEntity) {
                if (loginEntity.get_suc()!=0) {
                mView.showDataSuccess(loginEntity);
                }else {
                onFailed(0,loginEntity.getText());
                }
            }

            @Override
            protected void onFailed(int code, String msg) {
                mView.showDataError(msg);
            }

            @Override
            public void onSubscribe(Disposable d) {
                addSubscription(d);
            }
        });
    }

    @Override
    public void pWxLogin(String code) {
        activityModel.wXlogin(code).subscribe(new RxSubscribe<LoginEntity.DataBean>() {
            @Override
            protected void onSuccess(LoginEntity.DataBean loginEntity) {
                if (loginEntity.get_suc()!=0) {
                    mView.showWeChatLogin(loginEntity);
                }else {
                    onFailed(0,"微信未绑定账号");
                }
            }

            @Override
            protected void onFailed(int code, String msg) {
                mView.showDataError(msg);
            }

            @Override
            public void onSubscribe(Disposable d) {
                addSubscription(d);
            }
        });
    }
    @Override
    public void pCheckLogin(String hash) {
        activityModel.checkLogin(hash).subscribe(new RxSubscribe<LoginEntity.DataBean>() {
            @Override
            protected void onSuccess(LoginEntity.DataBean loginEntity) {
                mView.showDataSuccess(loginEntity);
            }

            @Override
            protected void onFailed(int code, String msg) {
                mView.showDataError(msg);
            }

            @Override
            public void onSubscribe(Disposable d) {
                addSubscription(d);
            }
        });
    }
}
