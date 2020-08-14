package com.hc_android.hc_css.contract;

import com.hc_android.hc_css.base.BaseEntity;
import com.hc_android.hc_css.base.BaseView;
import com.hc_android.hc_css.entity.IneValuateEntity;

import io.reactivex.Observable;

public interface UpdateActivityContract {

    interface Model {
        Observable<BaseEntity<IneValuateEntity.DataBean>> updateCards(String dialogId,String item);//修改名片
        Observable<BaseEntity<IneValuateEntity.DataBean>> updateRemarks(String dialogId,String remarks);//修改备注
        Observable<BaseEntity<IneValuateEntity.DataBean>> tagUsege(String mids);//修改标签
        Observable<BaseEntity<IneValuateEntity.DataBean>> accountModify(String info);//修改客服资料

    }

    interface View extends BaseView<IneValuateEntity.DataBean> {

        void updateRemarksSuccess(IneValuateEntity.DataBean dataBean);
        void accountModifySuccess(IneValuateEntity.DataBean dataBean);
    }

    interface Presenter {
        void pUpdateCards(String dialogId,String item);
        void pUpdateRemarks(String dialogId,String remarks);
        void pTagUsege(String mids);
        void pAccountModify(String info);

    }
}
