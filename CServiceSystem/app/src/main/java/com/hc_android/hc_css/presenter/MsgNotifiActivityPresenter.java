package com.hc_android.hc_css.presenter;

import com.hc_android.hc_css.base.BasePresenterIm;
import com.hc_android.hc_css.base.RxSubscribe;
import com.hc_android.hc_css.contract.MsgNotifiActivityContract;
import com.hc_android.hc_css.entity.IneValuateEntity;
import com.hc_android.hc_css.model.MsgNotifiActivityModel;

import io.reactivex.disposables.Disposable;

public class MsgNotifiActivityPresenter extends BasePresenterIm<MsgNotifiActivityContract.View>implements MsgNotifiActivityContract.Presenter {

    MsgNotifiActivityModel msgNotifiActivityModel;

    public MsgNotifiActivityPresenter() {
        msgNotifiActivityModel=new MsgNotifiActivityModel();
    }

    @Override
    public void pAccountModify(String info) {
        msgNotifiActivityModel.accountModify(info).subscribe(new RxSubscribe<IneValuateEntity.DataBean>() {
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

}
