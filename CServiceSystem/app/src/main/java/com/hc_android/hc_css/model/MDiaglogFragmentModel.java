package com.hc_android.hc_css.model;

import com.hc_android.hc_css.api.ApiManager;
import com.hc_android.hc_css.base.BaseApplication;
import com.hc_android.hc_css.base.BaseEntity;
import com.hc_android.hc_css.contract.MDiaglogFragmentContract;
import com.hc_android.hc_css.entity.IneValuateEntity;
import com.hc_android.hc_css.entity.QuickEntity;
import com.hc_android.hc_css.entity.QuickGroupEntity;
import com.hc_android.hc_css.utils.Constant;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MDiaglogFragmentModel implements MDiaglogFragmentContract.Model {

    @Override
    public Observable<BaseEntity<QuickEntity.DataBean>> getQuickList(String serviceId, boolean team) {
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put(Constant.REQUEST_TYPE,Constant.STANDARD);
        hashMap.put(Constant.KEY_HASH, BaseApplication.getUserEntity().getHash());
        if (serviceId!=null) {
            hashMap.put(Constant.SERVICEID, serviceId);
        }
        if (team){
            hashMap.put("team","true");
        }

        return ApiManager.getApistore().getQuickList(hashMap).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<BaseEntity<QuickGroupEntity.DataBean>> getQuickGroup(String serviceId, String type) {
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put(Constant.REQUEST_TYPE,Constant.STANDARD);
        hashMap.put(Constant.KEY_HASH, BaseApplication.getUserEntity().getHash());
        if (serviceId!=null) {
            hashMap.put(Constant.SERVICEID, serviceId);
        }
        if (type!=null){
            hashMap.put("type",type);
        }

        return ApiManager.getApistore().getQuickGroup(hashMap).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<BaseEntity<IneValuateEntity.DataBean>> quickUse(String id) {
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put(Constant.REQUEST_TYPE,Constant.STANDARD);
        hashMap.put(Constant.KEY_HASH, BaseApplication.getUserEntity().getHash());
        hashMap.put("id",id);
        return ApiManager.getApistore().quickUsage(hashMap).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
