package com.hc_android.hc_css.model;


import com.hc_android.hc_css.api.ApiManager;
import com.hc_android.hc_css.base.BaseApplication;
import com.hc_android.hc_css.base.BaseEntity;
import com.hc_android.hc_css.contract.MainActivityContract;
import com.hc_android.hc_css.entity.AppUpdateEntity;
import com.hc_android.hc_css.entity.LoginEntity;
import com.hc_android.hc_css.entity.TagEntity;
import com.hc_android.hc_css.utils.Constant;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Desccribe:
 *
 * @author Created by wuyang on 2019/9/2
 */
public class MainActivityModel implements MainActivityContract.Model {

    @Override
    public Observable<BaseEntity<LoginEntity.DataBean>> checkLogin(String hash) {
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put("mode","app");
        hashMap.put(Constant.KEY_HASH, hash);
        hashMap.put(Constant.REQUEST_TYPE,Constant.STANDARD);
        return ApiManager.getApistore().toLogin(hashMap).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<BaseEntity<TagEntity.DataBean>> getTagList() {
        HashMap<String,String> hashMap=new HashMap<>();

        hashMap.put(Constant.KEY_HASH, BaseApplication.getUserEntity().getHash());
        hashMap.put(Constant.REQUEST_TYPE,Constant.STANDARD);
        return ApiManager.getApistore().getTag(hashMap).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<BaseEntity<AppUpdateEntity.DataBean>> appUpdate() {
        HashMap<String,String> hashMap=new HashMap<>();

        hashMap.put(Constant.KEY_HASH, BaseApplication.getUserEntity().getHash());
        hashMap.put(Constant.REQUEST_TYPE,Constant.STANDARD);
        return ApiManager.getApistore().appUpdate(hashMap).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
