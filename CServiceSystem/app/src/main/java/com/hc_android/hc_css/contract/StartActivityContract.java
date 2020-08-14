package com.hc_android.hc_css.contract;

import com.hc_android.hc_css.base.BaseEntity;
import com.hc_android.hc_css.base.BaseView;
import com.hc_android.hc_css.entity.LoginEntity;

import io.reactivex.Observable;

public interface StartActivityContract {
    interface Model {
        Observable<BaseEntity<LoginEntity.DataBean>> checkLogin(String hash);
    }

    interface View extends BaseView<LoginEntity.DataBean> {
    }

    interface Presenter {
        void pCheckLogin(String hash);
    }
}
