package com.hecong.cssystem.contract;

import com.hecong.cssystem.base.BaseEntity;
import com.hecong.cssystem.base.BaseView;
import com.hecong.cssystem.entity.ReceptionEntity;

import io.reactivex.Observable;

public interface NotReceivedActivityContract {
    interface Model {

        Observable<BaseEntity<ReceptionEntity.DataBean>> receptionDialog(String dialogId);
        Observable<BaseEntity<ReceptionEntity.DataBean>> endDialog(String idList,String offEnd,String autoEnd);
    }

    interface View extends BaseView<ReceptionEntity.DataBean> {
        void  showEndDialog(ReceptionEntity.DataBean messageEntity);
    }

    interface Presenter {
        void pReceptionDialog(String dialogId);
        void pEndDialog(String idList,String offEnd,String autoEnd);
    }
}
