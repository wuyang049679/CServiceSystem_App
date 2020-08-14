package com.hc_android.hc_css.model;

import com.hc_android.hc_css.api.ApiManager;
import com.hc_android.hc_css.base.BaseApplication;
import com.hc_android.hc_css.base.BaseEntity;
import com.hc_android.hc_css.contract.VisitorListFragmentContract;
import com.hc_android.hc_css.entity.CustomPathEntity;
import com.hc_android.hc_css.entity.IneValuateEntity;
import com.hc_android.hc_css.entity.VisitorEntity;
import com.hc_android.hc_css.utils.Constant;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class VisitorListFragmentModel implements VisitorListFragmentContract.Model {
    @Override
    public Observable<BaseEntity<VisitorEntity.DataBean>> getVisitorList() {
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put(Constant.KEY_HASH, BaseApplication.getUserEntity().getHash());
        hashMap.put(Constant.REQUEST_TYPE,Constant.STANDARD);

        return ApiManager.getApistore().getVisitorList(hashMap).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    @Override
    public Observable<BaseEntity<CustomPathEntity.DataBean>> getRoutes(String realtimeId, String customerId, String visitorId) {
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put(Constant.KEY_HASH, BaseApplication.getUserEntity().getHash());
        hashMap.put(Constant.REQUEST_TYPE,Constant.STANDARD);
        if (realtimeId!=null) {
            hashMap.put("realtimeId",realtimeId);
        }
        if (visitorId!=null){
            hashMap.put("visitorId",visitorId);
        }else {
            if (customerId != null) {
                hashMap.put("customerId", customerId);
            }
        }

        return ApiManager.getApistore().getRoutes(hashMap).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<BaseEntity<IneValuateEntity.DataBean>> visitorInvitation(String realtimeId) {
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put(Constant.KEY_HASH, BaseApplication.getUserEntity().getHash());
        hashMap.put(Constant.REQUEST_TYPE,Constant.STANDARD);
        hashMap.put(Constant.SERVICEID,BaseApplication.getUserBean().getId());
        if (realtimeId!=null) {
            hashMap.put("realtimeId",realtimeId);
        }

        return ApiManager.getApistore().visitorInvitation(hashMap).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<BaseEntity<IneValuateEntity.DataBean>> realtimeActive(String realtimeId) {
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put(Constant.KEY_HASH, BaseApplication.getUserEntity().getHash());
        hashMap.put(Constant.REQUEST_TYPE,Constant.STANDARD);
        hashMap.put(Constant.SERVICEID,BaseApplication.getUserBean().getId());
        if (realtimeId!=null) {
            hashMap.put("realtimeId",realtimeId);
        }

        return ApiManager.getApistore().realtimeActive(hashMap).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
