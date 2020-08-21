package com.hc_android.hc_css.contract;

import com.hc_android.hc_css.base.BaseEntity;
import com.hc_android.hc_css.base.BaseView;
import com.hc_android.hc_css.entity.BusinessEntity;
import com.hc_android.hc_css.entity.IneValuateEntity;
import com.hc_android.hc_css.entity.VerityResultEntity;

import io.reactivex.Observable;

public interface BindActivityContract {
    interface Model {
        Observable<BaseEntity<IneValuateEntity.DataBean>> verification(String mobile, String email);
        Observable<BaseEntity<IneValuateEntity.DataBean>> vercode(String mobile, String email);
    }

    interface View extends BaseView<IneValuateEntity.DataBean> {

    }

    interface Presenter {
        void pVerification(String mobile, String email);
        void pVercode(String mobile, String email);
    }
}
