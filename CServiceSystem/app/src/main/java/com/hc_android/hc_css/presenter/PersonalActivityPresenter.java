package com.hc_android.hc_css.presenter;

import com.hc_android.hc_css.base.BasePresenterIm;
import com.hc_android.hc_css.base.RxSubscribe;
import com.hc_android.hc_css.contract.PersonalActivityContract;
import com.hc_android.hc_css.entity.IneValuateEntity;
import com.hc_android.hc_css.entity.LoginEntity;
import com.hc_android.hc_css.entity.TokenEntity;
import com.hc_android.hc_css.model.PersonalActivityModel;
import com.hc_android.hc_css.utils.android.ToastUtils;

import io.reactivex.disposables.Disposable;

public class PersonalActivityPresenter extends BasePresenterIm<PersonalActivityContract.View>implements PersonalActivityContract.Presenter {

    PersonalActivityModel personalActivityModel;

    public PersonalActivityPresenter() {
        personalActivityModel=new PersonalActivityModel();
    }

    @Override
    public void pGetToken(String type, String fileType) {

        personalActivityModel.getToken(type,fileType).subscribe(new RxSubscribe<TokenEntity.DataBean>() {
            @Override
            protected void onSuccess(TokenEntity.DataBean dataBean) {
                if (mView!=null)mView.showToken(dataBean);
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
        personalActivityModel.accountModify(info).subscribe(new RxSubscribe<IneValuateEntity.DataBean>() {
            @Override
            protected void onSuccess(IneValuateEntity.DataBean dataBean) {
                if (mView!=null)mView.accountModifySuccess(dataBean);
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
    public void pRelievebind(String type) {

        personalActivityModel.relievebind(type).subscribe(new RxSubscribe<IneValuateEntity.DataBean>() {
            @Override
            protected void onSuccess(IneValuateEntity.DataBean dataBean) {
                if (mView!=null)mView.showRelievebindSuccess(dataBean);
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
    public void pCheckLogin(String code) {
        personalActivityModel.wXlogin(code).subscribe(new RxSubscribe<LoginEntity.DataBean>() {
            @Override
            protected void onSuccess(LoginEntity.DataBean loginEntity) {
               mView.showWeChatLogin(loginEntity);

            }

            @Override
            protected void onFailed(int code, String msg) {
                mView.showWeChatLogin(null);
            }

            @Override
            public void onSubscribe(Disposable d) {
                addSubscription(d);
            }
        });
    }
}
