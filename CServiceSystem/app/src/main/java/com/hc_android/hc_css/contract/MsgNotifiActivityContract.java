package com.hc_android.hc_css.contract;

import com.hc_android.hc_css.base.BaseEntity;
import com.hc_android.hc_css.base.BaseView;
import com.hc_android.hc_css.entity.IneValuateEntity;

import io.reactivex.Observable;

public interface MsgNotifiActivityContract {


    interface Model {

        Observable<BaseEntity<IneValuateEntity.DataBean>> accountModify(String info);//修改客服资料
    }

    interface View extends BaseView<IneValuateEntity.DataBean> {
    }

    interface Presenter {
        void pAccountModify(String info);
    }
}
