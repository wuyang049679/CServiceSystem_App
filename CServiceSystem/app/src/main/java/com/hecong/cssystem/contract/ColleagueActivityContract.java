package com.hecong.cssystem.contract;

import com.hecong.cssystem.base.BaseEntity;
import com.hecong.cssystem.base.BaseView;
import com.hecong.cssystem.entity.TeamEntity;

import io.reactivex.Observable;

public interface ColleagueActivityContract {
    interface Model {
        Observable<BaseEntity<TeamEntity.DataBean>> getTeamList();
    }

    interface View extends BaseView<TeamEntity.DataBean> {
    }

    interface Presenter {
        void pGetTeamList();
    }
}
