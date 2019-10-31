package com.hecong.cssystem.contract;

import com.hecong.cssystem.base.BaseEntity;
import com.hecong.cssystem.base.BaseView;
import com.hecong.cssystem.entity.MessageDialogEntity;
import com.hecong.cssystem.utils.socket.EventListener;

import io.reactivex.Observable;

public interface ChatListFragmentContract {
    interface Model {
        Observable<BaseEntity<MessageDialogEntity.DataBean>> showMessageDialog(int limit, int skip);
        Observable<BaseEntity<MessageDialogEntity.DataBean>> getDialog(String dialogId);
    }

    interface View extends BaseView<MessageDialogEntity.DataBean> {
        void  showDialogList(MessageDialogEntity.DataBean messageEntity);
        void  showNewDialog(MessageDialogEntity.DataBean messageEntity);
    }

    interface Presenter extends EventListener {
        void pShowMessageDialog(int limit,int skip);
        void pGetDialog(String dialogId);
    }
}
