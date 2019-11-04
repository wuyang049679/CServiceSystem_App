package com.hecong.cssystem.contract;

import com.hecong.cssystem.base.BaseEntity;
import com.hecong.cssystem.base.BaseView;
import com.hecong.cssystem.entity.ReceptionEntity;

import io.reactivex.Observable;

public interface NotReceivedActivityContract {
    interface Model {

        Observable<BaseEntity<ReceptionEntity.DataBean>> receptionDialog(String dialogId);
    }

    interface View extends BaseView<ReceptionEntity.DataBean> {
    }

    interface Presenter {
        void pReceptionDialog(String dialogId);
    }
}
