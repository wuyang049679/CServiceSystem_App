package com.hc_android.hc_css.contract;

import com.hc_android.hc_css.base.BaseEntity;
import com.hc_android.hc_css.base.BaseView;
import com.hc_android.hc_css.entity.CardEntity;
import com.hc_android.hc_css.entity.IneValuateEntity;
import com.hc_android.hc_css.entity.ReceptionEntity;

import io.reactivex.Observable;

public interface ChatSetActivityContract {
    interface Model {

        Observable<BaseEntity<CardEntity.DataBean>> getCardList();
        Observable<BaseEntity<IneValuateEntity.DataBean>> dialogTransfer(String serviceId,String dialogId);
        Observable<BaseEntity<ReceptionEntity.DataBean>> endDialog(String idList, String offEnd, String autoEnd);
        Observable<BaseEntity<IneValuateEntity.DataBean>> blackAdd(String dialogId, String item);
        Observable<BaseEntity<IneValuateEntity.DataBean>> blackDel(String dialogId, String customerId);
        Observable<BaseEntity<IneValuateEntity.DataBean>> disturb(String dialogId, String disturb);
    }

    interface View extends BaseView<CardEntity.DataBean> {
        void showTransferSuccess(IneValuateEntity.DataBean dataBean);
        void  showEndDialog(ReceptionEntity.DataBean messageEntity);
        void blackAddSuccess(IneValuateEntity.DataBean dataBean);
        void blackDelSuccess(IneValuateEntity.DataBean dataBean);
        void disturbSuccess(IneValuateEntity.DataBean dataBean);
    }

    interface Presenter {
        void pGetCardList();
        void pDialogTransfer(String serviceId,String dialogId);
        void pEndDialog(String idList,String offEnd,String autoEnd);
        void pBlackAdd(String dialogId, String item);
        void pBlackDel(String dialogId, String customerId);
        void pDisturb(String dialogId, String disturb);
    }
}
