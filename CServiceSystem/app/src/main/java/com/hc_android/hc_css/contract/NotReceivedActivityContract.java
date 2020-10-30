package com.hc_android.hc_css.contract;

import com.hc_android.hc_css.base.BaseEntity;
import com.hc_android.hc_css.base.BaseView;
import com.hc_android.hc_css.entity.ReceptionEntity;

import io.reactivex.Observable;

public interface NotReceivedActivityContract {
    interface Model {

        Observable<BaseEntity<ReceptionEntity.DataBean>> receptionDialog(String dialogId, boolean upLimit);
        Observable<BaseEntity<ReceptionEntity.DataBean>> endDialog(String idList,String offEnd,String autoEnd);
    }

    interface View extends BaseView<ReceptionEntity.DataBean> {
        void  showEndDialog(ReceptionEntity.DataBean messageEntity);
    }

    interface Presenter {
        void pReceptionDialog(String dialogId, boolean upLimi);
        void pEndDialog(String idList,String offEnd,String autoEnd);
    }
}
