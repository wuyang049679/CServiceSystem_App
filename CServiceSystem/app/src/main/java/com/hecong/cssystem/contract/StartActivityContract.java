package com.hecong.cssystem.contract;

import com.hecong.cssystem.base.BaseEntity;
import com.hecong.cssystem.base.BaseView;
import com.hecong.cssystem.entity.LoginEntity;

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
