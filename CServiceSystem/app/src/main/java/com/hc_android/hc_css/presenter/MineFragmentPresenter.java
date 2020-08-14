package com.hc_android.hc_css.presenter;

import com.hc_android.hc_css.base.BasePresenterIm;
import com.hc_android.hc_css.base.RxSubscribe;
import com.hc_android.hc_css.contract.MineFragmentContract;
import com.hc_android.hc_css.entity.IneValuateEntity;
import com.hc_android.hc_css.model.MineFragmentModel;
import com.hc_android.hc_css.utils.android.ToastUtils;

import io.reactivex.disposables.Disposable;

public class MineFragmentPresenter extends BasePresenterIm<MineFragmentContract.View>implements MineFragmentContract.Presenter {

    MineFragmentModel mineFragmentModel;

    public MineFragmentPresenter() {
        mineFragmentModel=new MineFragmentModel();
    }

    @Override
    public void pLogout() {
        mineFragmentModel.logout().subscribe(new RxSubscribe<IneValuateEntity.DataBean>() {
            @Override
            protected void onSuccess(IneValuateEntity.DataBean dataBean) {
                mView.showDataSuccess(dataBean);
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

    @Override
    public void pAccountState(String state) {
        mineFragmentModel.accountState(state).subscribe(new RxSubscribe<IneValuateEntity.DataBean>() {
            @Override
            protected void onSuccess(IneValuateEntity.DataBean dataBean) {
                mView.showState(dataBean);
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
