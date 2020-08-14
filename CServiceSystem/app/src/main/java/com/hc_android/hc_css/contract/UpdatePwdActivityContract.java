package com.hc_android.hc_css.contract;

import com.hc_android.hc_css.base.BaseEntity;
import com.hc_android.hc_css.base.BaseView;
import com.hc_android.hc_css.entity.IneValuateEntity;

import io.reactivex.Observable;

public interface UpdatePwdActivityContract {
    interface Model {
        Observable<BaseEntity<IneValuateEntity.DataBean>> accountModifyPwd(String password,String newpassword);//修改密码
        Observable<BaseEntity<IneValuateEntity.DataBean>> accountState(String state);//在线状态
    }

    interface View extends BaseView<IneValuateEntity.DataBean> {

        void showState(IneValuateEntity.DataBean dataBean);
    }

    interface Presenter {
        void pAccountModifyPwd(String password,String newpassword);
        void pAccountState(String state);
    }
}
