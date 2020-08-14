package com.hc_android.hc_css.model;

import com.hc_android.hc_css.api.ApiManager;
import com.hc_android.hc_css.base.BaseApplication;
import com.hc_android.hc_css.base.BaseEntity;
import com.hc_android.hc_css.contract.RemittanceActivityContract;
import com.hc_android.hc_css.entity.IneValuateEntity;
import com.hc_android.hc_css.utils.Constant;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RemittanceActivityModel implements RemittanceActivityContract.Model {
    @Override
    public Observable<BaseEntity<IneValuateEntity.DataBean>> realBankData(String name,String bankcardnum, String bankcardname, String bankphone) {
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put(Constant.REQUEST_TYPE,Constant.STANDARD);
        hashMap.put(Constant.KEY_HASH, BaseApplication.getUserEntity().getHash());
        if (name!=null)hashMap.put("name",name);
        if (bankcardnum!=null)hashMap.put("bankcardnum",bankcardnum);
        if (bankcardname!=null)hashMap.put("bankcardname",bankcardname);
        if (bankphone!=null)hashMap.put("bankphone",bankphone);
        return ApiManager.getApistore().realBankData(hashMap).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
