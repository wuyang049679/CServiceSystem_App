package com.hecong.cssystem.presenter;

import com.hecong.cssystem.base.BasePresenterIm;
import com.hecong.cssystem.base.RxSubscribe;
import com.hecong.cssystem.contract.LoginActivityContract;
import com.hecong.cssystem.entity.LoginEntity;
import com.hecong.cssystem.model.LoginActivityModel;
import com.hecong.cssystem.utils.android.ToastUtils;

import io.reactivex.disposables.Disposable;

public class LoginActivityPresenter extends BasePresenterIm<LoginActivityContract.View> implements LoginActivityContract.Presenter {

    LoginActivityModel activityModel;

    public LoginActivityPresenter() {
        this.activityModel = new LoginActivityModel();
    }

    @Override
    public void pLogin(String username, String password) {

        activityModel.login(username,password).subscribe(new RxSubscribe<LoginEntity>() {
            @Override
            protected void onSuccess(LoginEntity loginEntity,String hash) {
//                if (hash!=null) {
                    loginEntity.setHash(hash);
                    mView.showDataSuccess(loginEntity);
//                }
            }

            @Override
            protected void onFailed(int code, String msg) {
                mView.showDataError(msg);
                ToastUtils.showShort("用户名或密码错误");
            }

            @Override
            public void onSubscribe(Disposable d) {
                addSubscription(d);
            }
        });
    }
}
