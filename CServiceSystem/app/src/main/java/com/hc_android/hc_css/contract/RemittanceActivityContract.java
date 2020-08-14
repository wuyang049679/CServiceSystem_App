package com.hc_android.hc_css.contract;

import com.hc_android.hc_css.base.BaseEntity;
import com.hc_android.hc_css.base.BaseView;
import com.hc_android.hc_css.entity.IneValuateEntity;
import com.hc_android.hc_css.entity.VerityEntity;

import io.reactivex.Observable;

public interface RemittanceActivityContract {
    interface Model {

        Observable<BaseEntity<IneValuateEntity.DataBean>> realBankData(String name, String bankcardnum, String bankcardname, String bankphone);//实名认证获取token
    }

    interface View extends BaseView<IneValuateEntity.DataBean> {
    }

    interface Presenter {
       void pRealBankData(String name, String bankcardnum, String bankcardname, String bankphone);
    }
}
