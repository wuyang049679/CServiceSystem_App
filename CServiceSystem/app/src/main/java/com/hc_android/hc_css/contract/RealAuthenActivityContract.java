package com.hc_android.hc_css.contract;

import com.hc_android.hc_css.base.BaseEntity;
import com.hc_android.hc_css.base.BaseView;
import com.hc_android.hc_css.entity.IneValuateEntity;
import com.hc_android.hc_css.entity.VerityEntity;
import com.hc_android.hc_css.entity.VerityResultEntity;

import io.reactivex.Observable;

public interface RealAuthenActivityContract {
    interface Model {
        Observable<BaseEntity<VerityEntity.DataBean>> verityToken(String type,String name,String idCard);//实名认证获取token
        Observable<BaseEntity<VerityResultEntity.DataBean>> verityResult(String realtype,String type,String bizId);//实名认证获取result
        Observable<BaseEntity<IneValuateEntity.DataBean>> accountModify(String info);//修改客服资料
    }

    interface View extends BaseView<VerityEntity.DataBean> {

        void showResult(VerityResultEntity.DataBean dataBean);

    }

    interface Presenter {
        void pVerityToken(String type,String name,String idCard);
        void pVerityResult(String realtype,String type,String bizId);
        void pAccountModify(String info);
    }
}
