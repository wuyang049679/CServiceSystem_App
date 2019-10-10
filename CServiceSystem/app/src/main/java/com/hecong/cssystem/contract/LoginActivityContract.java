package com.hecong.cssystem.contract;

import com.hecong.cssystem.base.BaseEntity;
import com.hecong.cssystem.base.BaseView;
import com.hecong.cssystem.entity.LoginEntity;

import io.reactivex.Observable;

public interface LoginActivityContract {
    interface Model {
        Observable<BaseEntity<LoginEntity>> login(String username,String password);
    }

    interface View extends BaseView<LoginEntity> {

    }

    interface Presenter {
        void pLogin(String username,String password);
    }
}
