package com.hc_android.hc_css.contract;

import com.hc_android.hc_css.base.BaseEntity;
import com.hc_android.hc_css.base.BaseView;
import com.hc_android.hc_css.entity.LoginEntity;

import io.reactivex.Observable;

public interface LoginActivityContract {
    interface Model {
        Observable<BaseEntity<LoginEntity.DataBean>> login(String s, String username, String password);
        Observable<BaseEntity<LoginEntity.DataBean>> wXlogin(String code);
        Observable<BaseEntity<LoginEntity.DataBean>> checkLogin(String hash);
    }

    interface View extends BaseView<LoginEntity.DataBean> {

        void showWeChatLogin(LoginEntity.DataBean dataBean);
    }

    interface Presenter {
        void pLogin(String username, String password, String imei);
        void pWxLogin(String code);
        void pCheckLogin(String hash);
    }
}
