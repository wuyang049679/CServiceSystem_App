package com.hc_android.hc_css.presenter;

import com.hc_android.hc_css.base.BasePresenterIm;
import com.hc_android.hc_css.base.RxSubscribe;
import com.hc_android.hc_css.contract.VisitorListFragmentContract;
import com.hc_android.hc_css.entity.CustomPathEntity;
import com.hc_android.hc_css.entity.IneValuateEntity;
import com.hc_android.hc_css.entity.VisitorEntity;
import com.hc_android.hc_css.model.VisitorListFragmentModel;
import com.hc_android.hc_css.utils.android.ToastUtils;

import io.reactivex.disposables.Disposable;

public class VisitorListFragmentPresenter extends BasePresenterIm<VisitorListFragmentContract.View>implements VisitorListFragmentContract.Presenter {

    VisitorListFragmentModel visitorListFragmentModel;


    public VisitorListFragmentPresenter() {
        visitorListFragmentModel=new VisitorListFragmentModel();
    }

    /**
     * 访客列表
     */
    @Override
    public void pVisitorList() {
        visitorListFragmentModel.getVisitorList().subscribe(new RxSubscribe<VisitorEntity.DataBean>() {
            @Override
            protected void onSuccess(VisitorEntity.DataBean dataBean) {
                mView.showDataSuccess(dataBean );
            }

            @Override
            protected void onFailed(int code, String msg) {

            }

            @Override
            public void onSubscribe(Disposable d) {
                addSubscription(d);
            }
        });
    }

    /**
     * 访客轨迹
     * @param realtimeId
     * @param customId
     * @param visitorId
     */
    @Override
    public void pGetroutes(String realtimeId, String customId,String visitorId) {
        visitorListFragmentModel.getRoutes(realtimeId,customId,visitorId).subscribe(new RxSubscribe<CustomPathEntity.DataBean>() {
            @Override
            protected void onSuccess(CustomPathEntity.DataBean dataBean) {
                mView.showPathList(dataBean);
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

    /**
     * 邀请对话
     * @param realtimeId
     */
    @Override
    public void pVisitorInvitation(String realtimeId) {
        visitorListFragmentModel.visitorInvitation(realtimeId).subscribe(new RxSubscribe<IneValuateEntity.DataBean>() {
            @Override
            protected void onSuccess(IneValuateEntity.DataBean dataBean) {
                mView.showInvitation(dataBean);
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

    /**
     * 主动对话
     * @param realtimeId
     */
    @Override
    public void pRealtimeActive(String realtimeId) {
        visitorListFragmentModel.realtimeActive(realtimeId).subscribe(new RxSubscribe<IneValuateEntity.DataBean>() {
            @Override
            protected void onSuccess(IneValuateEntity.DataBean dataBean) {
                mView.showActive(dataBean);
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
