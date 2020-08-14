package com.hc_android.hc_css.contract;

import com.hc_android.hc_css.base.BaseEntity;
import com.hc_android.hc_css.base.BaseView;
import com.hc_android.hc_css.entity.MessageDialogEntity;
import com.hc_android.hc_css.entity.ReceptionEntity;
import com.hc_android.hc_css.entity.TeamEntity;

import io.reactivex.Observable;

public interface ChatListFragmentContract {
    interface Model {
        Observable<BaseEntity<MessageDialogEntity.DataBean>> showMessageDialog(int limit, int skip);
        Observable<BaseEntity<MessageDialogEntity.DataBean>> getDialog(String dialogId);
        Observable<BaseEntity<ReceptionEntity.DataBean>> endDialog(String idList,String offEnd,String autoEnd);
        Observable<BaseEntity<TeamEntity.DataBean>> getTeamList();

    }

    interface View extends BaseView<MessageDialogEntity.DataBean> {
        void  showDialogList(MessageDialogEntity.DataBean messageEntity);
        void  showNewDialog(MessageDialogEntity.DataBean messageEntity);
        void  showEndDialog(ReceptionEntity.DataBean messageEntity);
        void  showTeamList(TeamEntity.DataBean dataBean);
    }

    interface Presenter  {
        void pShowMessageDialog(int limit,int skip);
        void pGetDialog(String dialogId);
        void pEndDialog(String idList,String offEnd,String autoEnd);
        void pGetTeamList();
    }
}
