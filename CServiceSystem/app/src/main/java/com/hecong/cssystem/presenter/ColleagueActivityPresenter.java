package com.hecong.cssystem.presenter;

import com.hecong.cssystem.base.BasePresenterIm;
import com.hecong.cssystem.base.RxSubscribe;
import com.hecong.cssystem.contract.ColleagueActivityContract;
import com.hecong.cssystem.entity.TeamEntity;
import com.hecong.cssystem.model.ColleagueActivityModel;
import com.hecong.cssystem.utils.android.ToastUtils;

import io.reactivex.disposables.Disposable;

public class ColleagueActivityPresenter extends BasePresenterIm<ColleagueActivityContract.View> implements ColleagueActivityContract.Presenter {

    ColleagueActivityModel colleagueActivityModel;

    public ColleagueActivityPresenter() {
        this.colleagueActivityModel = new ColleagueActivityModel();
    }

    @Override
    public void pGetTeamList() {
        colleagueActivityModel.getTeamList().subscribe(new RxSubscribe<TeamEntity.DataBean>() {
            @Override
            protected void onSuccess(TeamEntity.DataBean dataBean) {
                mView.showDataSuccess(dataBean);
            }

            @Override
            protected void onFailed(int code, String msg) {
                ToastUtils.showShort(msg);
            }

            @Override
            public void onSubscribe(Disposable d) {
                addSubscription(d);
            }
        });
    }
}
