package com.hc_android.hc_css.presenter;

import com.hc_android.hc_css.base.BasePresenterIm;
import com.hc_android.hc_css.base.RxSubscribe;
import com.hc_android.hc_css.contract.RemittanceActivityContract;
import com.hc_android.hc_css.entity.IneValuateEntity;
import com.hc_android.hc_css.model.RemittanceActivityModel;
import com.hc_android.hc_css.utils.android.ToastUtils;

import io.reactivex.disposables.Disposable;

public class RemittanceActivityPresenter extends BasePresenterIm<RemittanceActivityContract.View>implements RemittanceActivityContract.Presenter {

    RemittanceActivityModel remittanceActivityModel;

    public RemittanceActivityPresenter() {
        remittanceActivityModel = new RemittanceActivityModel();
    }

    @Override
    public void pRealBankData(String name,String bankcardnum, String bankcardname, String bankphone) {
        remittanceActivityModel.realBankData(name,bankcardnum,bankcardname,bankphone).subscribe(new RxSubscribe<IneValuateEntity.DataBean>() {
            @Override
            protected void onSuccess(IneValuateEntity.DataBean dataBean) {
                mView.showDataSuccess(dataBean);
            }

            @Override
            protected void onFailed(int code, String msg) {
                mView.showContentView();
                ToastUtils.showShort(msg);
            }

            @Override
            public void onSubscribe(Disposable d) {
                addSubscription(d);
            }
        });
    }
}
