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
        Observable<BaseEntity<IneValuateEntity.DataBean>> vercode(String mobile, String email, String type, String antiBrush);
        Observable<BaseEntity<IneValuateEntity.DataBean>> relievebind(String type);
        Observable<BaseEntity<IneValuateEntity.DataBean>> bind(String mobile, String email,String code);
        Observable<BaseEntity<IneValuateEntity.DataBean>> regisiter(String fields);
    }

    interface View extends BaseView<IneValuateEntity.DataBean> {
        void showRelievebindSuccess(IneValuateEntity.DataBean  dataBean);
        void showEroor(int code, String msg);
        void showBindSuccess(IneValuateEntity.DataBean  dataBean);
        void showVercode(IneValuateEntity.DataBean  dataBean);
        void showRegisiterSuccess(IneValuateEntity.DataBean  dataBean);
        void showRegisiterEroor(String msg);
    }

    interface Presenter {
        void pVerification(String mobile, String email);
        void pVercode(String mobile, String email, String type, String antiBrush);
        void pRelievebind(String type);
        void pBind(String mobile, String email,String code);
        void pRegisiter(String fields);
    }
}
