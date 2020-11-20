package com.hc_android.hc_css.model;

import com.hc_android.hc_css.api.ApiManager;
import com.hc_android.hc_css.base.BaseApplication;
import com.hc_android.hc_css.base.BaseEntity;
import com.hc_android.hc_css.contract.ChatSetActivityContract;
import com.hc_android.hc_css.entity.CardEntity;
import com.hc_android.hc_css.entity.IneValuateEntity;
import com.hc_android.hc_css.entity.ReceptionEntity;
import com.hc_android.hc_css.utils.Constant;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ChatSetActivityModel implements ChatSetActivityContract.Model {
    @Override
    public Observable<BaseEntity<CardEntity.DataBean>> getCardList() {

            HashMap<String,String> hashMap=new HashMap<>();
            hashMap.put(Constant.REQUEST_TYPE,Constant.STANDARD);
            hashMap.put(Constant.KEY_HASH, BaseApplication.getUserEntity().getHash());
            return ApiManager.getApistore().getCard(hashMap).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());

    }

    @Override
    public Observable<BaseEntity<IneValuateEntity.DataBean>> dialogTransfer(String serviceId,String dialogId) {
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put(Constant.REQUEST_TYPE,Constant.STANDARD);
        hashMap.put(Constant.SERVICEID,serviceId);
        hashMap.put(Constant.DIALOGID,dialogId);
        hashMap.put(Constant.KEY_HASH, BaseApplication.getUserEntity().getHash());

        return ApiManager.getApistore().dialogTransfer(hashMap).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<BaseEntity<ReceptionEntity.DataBean>> endDialog(String idList, String offEnd, String autoEnd) {
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put(Constant.KEY_HASH, BaseApplication.getUserEntity().getHash());
        hashMap.put(Constant.REQUEST_TYPE,Constant.STANDARD);
        hashMap.put("idList",idList);
        if (offEnd!=null)hashMap.put("offEnd",offEnd);
        if (autoEnd!=null)hashMap.put("autoEnd",autoEnd);
        return ApiManager.getApistore().endDialog(hashMap).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<BaseEntity<IneValuateEntity.DataBean>> blackAdd(String dialogId, String item) {
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put(Constant.KEY_HASH, BaseApplication.getUserEntity().getHash());
        hashMap.put(Constant.REQUEST_TYPE,Constant.STANDARD);
        hashMap.put(Constant.DIALOGID,dialogId);
        hashMap.put("item",item);

        return ApiManager.getApistore().blackAdd(hashMap).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<BaseEntity<IneValuateEntity.DataBean>> blackDel(String dialogId, String customerId) {
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put(Constant.KEY_HASH, BaseApplication.getUserEntity().getHash());
        hashMap.put(Constant.REQUEST_TYPE,Constant.STANDARD);
        hashMap.put(Constant.DIALOGID,dialogId);
        hashMap.put(Constant.CUSTOMERID,customerId);

        return ApiManager.getApistore().blackDel(hashMap).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<BaseEntity<IneValuateEntity.DataBean>> disturb(String dialogId, String disturb) {
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put(Constant.KEY_HASH, BaseApplication.getUserEntity().getHash());
        hashMap.put(Constant.REQUEST_TYPE,Constant.STANDARD);
        hashMap.put(Constant.DIALOGID,dialogId);
        hashMap.put("disturb",disturb);

        return ApiManager.getApistore().disturb(hashMap).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<BaseEntity<IneValuateEntity.DataBean>> top(String dialogId, boolean top) {
        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put(Constant.KEY_HASH, BaseApplication.getUserEntity().getHash());
        hashMap.put(Constant.REQUEST_TYPE,Constant.STANDARD);
        hashMap.put(Constant.DIALOGID,dialogId);
        hashMap.put("top",top);

        return ApiManager.getApistore().top(hashMap).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


}
