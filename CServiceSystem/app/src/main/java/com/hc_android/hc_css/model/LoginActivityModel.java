package com.hc_android.hc_css.model;

import com.hc_android.hc_css.api.ApiManager;
import com.hc_android.hc_css.base.BaseEntity;
import com.hc_android.hc_css.contract.LoginActivityContract;
import com.hc_android.hc_css.entity.LoginEntity;
import com.hc_android.hc_css.utils.Constant;
import com.hc_android.hc_css.utils.NullUtils;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginActivityModel implements LoginActivityContract.Model {
    @Override
    public Observable<BaseEntity<LoginEntity.DataBean>> login( String username, String password,String imei) {
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put("account", username);
        hashMap.put("password",password);
        hashMap.put("mode","app");
        if (!NullUtils.isNull(imei)){
            hashMap.put("macAddress",imei);
        }
        hashMap.put(Constant.REQUEST_TYPE,Constant.STANDARD);
        return ApiManager.getApistore().toLogin(hashMap).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<BaseEntity<LoginEntity.DataBean>> wXlogin(String code) {
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put("mode","app");
        hashMap.put("code",code);
        hashMap.put(Constant.REQUEST_TYPE,Constant.STANDARD);
        return ApiManager.getApistore().toWeChatLogin(hashMap).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    @Override
    public Observable<BaseEntity<LoginEntity.DataBean>> checkLogin(String hash) {
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put("mode","app");
        hashMap.put(Constant.KEY_HASH, hash);
        hashMap.put(Constant.REQUEST_TYPE,Constant.STANDARD);
        return ApiManager.getApistore().toLogin(hashMap).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
