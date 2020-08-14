package com.hc_android.hc_css.contract;

import com.hc_android.hc_css.base.BaseEntity;
import com.hc_android.hc_css.base.BaseView;
import com.hc_android.hc_css.entity.ReceptionEntity;
import com.hc_android.hc_css.entity.TeamEntity;

import io.reactivex.Observable;

public interface ColleagueActivityContract {
    interface Model {
        Observable<BaseEntity<TeamEntity.DataBean>> getTeamList();
        Observable<BaseEntity<ReceptionEntity.DataBean>> endDialog(String idList, String offEnd, String autoEnd);
    }

    interface View extends BaseView<TeamEntity.DataBean> {
        void  showEndDialog(ReceptionEntity.DataBean messageEntity);
    }

    interface Presenter {
        void pGetTeamList();
        void pEndDialog(String idList,String offEnd,String autoEnd);
    }
}
