package com.hc_android.hc_css.model;

import com.hc_android.hc_css.api.ApiManager;
import com.hc_android.hc_css.base.BaseApplication;
import com.hc_android.hc_css.base.BaseEntity;
import com.hc_android.hc_css.contract.UpdateActivityContract;
import com.hc_android.hc_css.entity.IneValuateEntity;
import com.hc_android.hc_css.utils.Constant;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class UpdateActivityModel implements UpdateActivityContract.Model {
    @Override
    public Observable<BaseEntity<IneValuateEntity.DataBean>> updateCards(String dialogId, String item) {
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put(Constant.REQUEST_TYPE,Constant.STANDARD);
        hashMap.put(Constant.DIALOGID,dialogId);
        hashMap.put(Constant.KEY_HASH, BaseApplication.getUserEntity().getHash());
        hashMap.put("item",item);

        return ApiManager.getApistore().updateCard(hashMap).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<BaseEntity<IneValuateEntity.DataBean>> updateRemarks(String dialogId, String remarks) {
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put(Constant.REQUEST_TYPE,Constant.STANDARD);
        hashMap.put(Constant.DIALOGID,dialogId);
        hashMap.put(Constant.KEY_HASH, BaseApplication.getUserEntity().getHash());
        hashMap.put("remarks",remarks);

        return ApiManager.getApistore().updateRemarks(hashMap).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<BaseEntity<IneValuateEntity.DataBean>> tagUsege(String mids) {
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put(Constant.REQUEST_TYPE,Constant.STANDARD);
        hashMap.put(Constant.KEY_HASH, BaseApplication.getUserEntity().getHash());
        hashMap.put("mids",mids);

        return ApiManager.getApistore().tagUsage(hashMap).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<BaseEntity<IneValuateEntity.DataBean>> accountModify(String info) {
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put(Constant.REQUEST_TYPE,Constant.STANDARD);
        hashMap.put(Constant.KEY_HASH, BaseApplication.getUserEntity().getHash());
        hashMap.put("info",info);

        return ApiManager.getApistore().accountModify(hashMap).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


}
