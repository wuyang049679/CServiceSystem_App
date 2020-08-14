package com.hc_android.hc_css.presenter;

import com.hc_android.hc_css.base.BasePresenterIm;
import com.hc_android.hc_css.base.RxSubscribe;
import com.hc_android.hc_css.contract.RemStateActivityContract;
import com.hc_android.hc_css.entity.VerityResultEntity;
import com.hc_android.hc_css.model.RemStateActivityModel;
import com.hc_android.hc_css.utils.android.ToastUtils;

import io.reactivex.disposables.Disposable;

public class RemStateActivityPresenter extends BasePresenterIm<RemStateActivityContract.View> implements RemStateActivityContract.Presenter {

    RemStateActivityModel remStateActivityModel;

    public RemStateActivityPresenter() {
        remStateActivityModel = new RemStateActivityModel();
    }

    @Override
    public void pRealConfirm(String num) {
        remStateActivityModel.realConfirm(num).subscribe(new RxSubscribe<VerityResultEntity.DataBean>() {
            @Override
            protected void onSuccess(VerityResultEntity.DataBean dataBean) {
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
