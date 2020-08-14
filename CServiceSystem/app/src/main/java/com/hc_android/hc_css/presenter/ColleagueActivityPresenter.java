package com.hc_android.hc_css.presenter;

import com.hc_android.hc_css.base.BasePresenterIm;
import com.hc_android.hc_css.base.RxSubscribe;
import com.hc_android.hc_css.contract.ColleagueActivityContract;
import com.hc_android.hc_css.entity.ReceptionEntity;
import com.hc_android.hc_css.entity.TeamEntity;
import com.hc_android.hc_css.model.ColleagueActivityModel;
import com.hc_android.hc_css.utils.android.ToastUtils;

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
    @Override
    public void pEndDialog(String idList, String offEnd, String autoEnd) {
        colleagueActivityModel.endDialog(idList, offEnd, autoEnd).subscribe(new RxSubscribe<ReceptionEntity.DataBean>() {
            @Override
            protected void onSuccess(ReceptionEntity.DataBean dataBean) {
                mView.showEndDialog(dataBean);
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
