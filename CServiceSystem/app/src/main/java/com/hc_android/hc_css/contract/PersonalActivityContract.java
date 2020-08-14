package com.hc_android.hc_css.contract;

import com.hc_android.hc_css.base.BaseEntity;
import com.hc_android.hc_css.base.BaseView;
import com.hc_android.hc_css.entity.IneValuateEntity;
import com.hc_android.hc_css.entity.TokenEntity;

import io.reactivex.Observable;

public interface PersonalActivityContract {
    interface Model {
        Observable<BaseEntity<TokenEntity.DataBean>> getToken(String type,String fileType);//获取七牛token
        Observable<BaseEntity<IneValuateEntity.DataBean>> accountModify(String info);//修改客服资料
    }

    interface View extends BaseView<TokenEntity.DataBean>  {

        void showToken(TokenEntity.DataBean dataBean);
        void accountModifySuccess(IneValuateEntity.DataBean dataBean);
    }

    interface Presenter {
        void pGetToken(String type,String fileType);
        void pAccountModify(String info);
    }
}
