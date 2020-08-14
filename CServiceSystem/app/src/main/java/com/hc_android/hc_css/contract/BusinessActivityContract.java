package com.hc_android.hc_css.contract;

import com.hc_android.hc_css.base.BaseEntity;
import com.hc_android.hc_css.base.BaseView;
import com.hc_android.hc_css.entity.BusinessEntity;
import com.hc_android.hc_css.entity.IneValuateEntity;
import com.hc_android.hc_css.entity.TokenEntity;

import io.reactivex.Observable;

public interface BusinessActivityContract {
    interface Model {
        Observable<BaseEntity<BusinessEntity.DataBean>> verityBusiness(String image);//企业营业执照验证
        Observable<BaseEntity<TokenEntity.DataBean>> getToken(String type, String fileType);//获取七牛token
    }

    interface View extends BaseView<BusinessEntity.DataBean> {
        void showToken(TokenEntity.DataBean dataBean);
    }

    interface Presenter {
       void pVerityBusiness(String image);
        void pGetToken(String type,String fileType);
    }
}
