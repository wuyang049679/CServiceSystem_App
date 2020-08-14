package com.hc_android.hc_css.contract;

import com.hc_android.hc_css.base.BaseEntity;
import com.hc_android.hc_css.base.BaseView;
import com.hc_android.hc_css.entity.IneValuateEntity;
import com.hc_android.hc_css.entity.QuickEntity;
import com.hc_android.hc_css.entity.QuickGroupEntity;

import io.reactivex.Observable;

public interface MDiaglogFragmentContract {
    interface Model {
        Observable<BaseEntity<QuickEntity.DataBean>> getQuickList(String serviceId, boolean team);
        Observable<BaseEntity<QuickGroupEntity.DataBean>> getQuickGroup(String serviceId, String type);
        Observable<BaseEntity<IneValuateEntity.DataBean>> quickUse(String id);

    }

    interface View extends BaseView<QuickEntity.DataBean> {
        void showQuickList(QuickEntity.DataBean dataBean);
        void showQuickGroup(QuickGroupEntity.DataBean dataBean);

    }

    interface Presenter {
        void pGetQuickList(String serviceId,boolean team);
        void pGetQuickGroup(String serviceId,String type);
        void pQuickUse(String id);
    }
}
