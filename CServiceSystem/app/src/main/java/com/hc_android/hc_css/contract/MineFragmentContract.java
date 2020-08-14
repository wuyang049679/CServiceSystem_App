package com.hc_android.hc_css.contract;

import com.hc_android.hc_css.base.BaseEntity;
import com.hc_android.hc_css.base.BaseView;
import com.hc_android.hc_css.entity.IneValuateEntity;

import io.reactivex.Observable;

public interface MineFragmentContract {

    interface Model {

        Observable<BaseEntity<IneValuateEntity.DataBean>> logout();//退出登录
        Observable<BaseEntity<IneValuateEntity.DataBean>> accountState(String state);//在线状态
    }

    interface View extends BaseView<IneValuateEntity.DataBean> {
        void showState(IneValuateEntity.DataBean dataBean);
    }

    interface Presenter {
        void pLogout();
        void pAccountState(String state);
    }
}
