package com.hc_android.hc_css.contract;

import com.hc_android.hc_css.base.BaseEntity;
import com.hc_android.hc_css.base.BaseView;
import com.hc_android.hc_css.entity.IneValuateEntity;
import com.hc_android.hc_css.entity.VerityEntity;

import io.reactivex.Observable;

public interface PhoneVerificationContract {

    interface Model {
        Observable<BaseEntity<VerityEntity.DataBean>> verityPhone(String token,String tel);//号码认证
        Observable<BaseEntity<IneValuateEntity.DataBean>> accountModify(String info);//修改客服资料
    }

    interface View extends BaseView<VerityEntity.DataBean> {
    }

    interface Presenter {
        void pVerityPhone(String token,String tel);
        void pAccountModify(String info);
    }
}
