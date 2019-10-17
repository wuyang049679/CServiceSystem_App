package com.hecong.cssystem.model;


import com.hecong.cssystem.api.ApiManager;
import com.hecong.cssystem.base.BaseEntity;
import com.hecong.cssystem.contract.MainActivityContract;
import com.hecong.cssystem.entity.LoginEntity;
import com.hecong.cssystem.utils.Constant;

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
}
