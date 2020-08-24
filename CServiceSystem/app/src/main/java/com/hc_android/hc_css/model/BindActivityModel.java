package com.hc_android.hc_css.model;

import com.hc_android.hc_css.api.ApiManager;
import com.hc_android.hc_css.base.BaseApplication;
import com.hc_android.hc_css.base.BaseEntity;
import com.hc_android.hc_css.contract.BindActivityContract;
import com.hc_android.hc_css.entity.BusinessEntity;
import com.hc_android.hc_css.entity.IneValuateEntity;
import com.hc_android.hc_css.entity.VerityResultEntity;
import com.hc_android.hc_css.utils.Constant;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class BindActivityModel implements BindActivityContract.Model {

    @Override
    public Observable<BaseEntity<IneValuateEntity.DataBean>> verification(String mobile, String email) {
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put(Constant.REQUEST_TYPE,Constant.STANDARD);
//        hashMap.put(Constant.KEY_HASH, BaseApplication.getUserEntity().getHash());
        if (mobile != null)hashMap.put("tel",mobile);
        if (email != null)hashMap.put("email",email);
        return ApiManager.getApistore().verification(hashMap).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<BaseEntity<IneValuateEntity.DataBean>> vercode(String mobile, String email, String type, String antiBrush) {
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put(Constant.REQUEST_TYPE,Constant.STANDARD);
        if (BaseApplication.getUserEntity().getHash()!=null)hashMap.put(Constant.KEY_HASH, BaseApplication.getUserEntity().getHash());
        if (mobile != null)hashMap.put("tel",mobile);
        if (email != null)hashMap.put("email",email);
        if (antiBrush !=null)hashMap.put("antiBrush",antiBrush);
        hashMap.put("type",type);
        hashMap.put("mode","app");
        return ApiManager.getApistore().vercode(hashMap).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<BaseEntity<IneValuateEntity.DataBean>> relievebind(String type) {
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put(Constant.REQUEST_TYPE,Constant.STANDARD);
        hashMap.put(Constant.KEY_HASH, BaseApplication.getUserEntity().getHash());
        hashMap.put("type",type);
        return ApiManager.getApistore().relievebind(hashMap).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<BaseEntity<IneValuateEntity.DataBean>> bind(String mobile, String email, String code) {
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put(Constant.REQUEST_TYPE,Constant.STANDARD);
        hashMap.put(Constant.KEY_HASH, BaseApplication.getUserEntity().getHash());
        if (mobile != null)hashMap.put("tel",mobile);
        if (email != null)hashMap.put("email",email);
        if (code != null)hashMap.put("code",code);
        return ApiManager.getApistore().bind(hashMap).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<BaseEntity<IneValuateEntity.DataBean>> regisiter(String fields) {
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put(Constant.REQUEST_TYPE,Constant.STANDARD);
        if (fields != null)hashMap.put("fields",fields);
        return ApiManager.getApistore().regisiter(hashMap).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
