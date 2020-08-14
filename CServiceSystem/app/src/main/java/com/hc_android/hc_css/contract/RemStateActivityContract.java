package com.hc_android.hc_css.contract;

import com.hc_android.hc_css.base.BaseEntity;
import com.hc_android.hc_css.base.BaseView;
import com.hc_android.hc_css.entity.VerityEntity;
import com.hc_android.hc_css.entity.VerityResultEntity;

import io.reactivex.Observable;

public interface RemStateActivityContract {
    interface Model {
        Observable<BaseEntity<VerityResultEntity.DataBean>> realConfirm(String num);//实名认证获取token
    }

    interface View extends BaseView<VerityResultEntity.DataBean> {
    }

    interface Presenter {
        void pRealConfirm(String num);
    }
}
