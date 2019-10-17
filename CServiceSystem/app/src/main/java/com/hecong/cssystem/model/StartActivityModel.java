package com.hecong.cssystem.model;

import com.hecong.cssystem.api.ApiManager;
import com.hecong.cssystem.base.BaseEntity;
import com.hecong.cssystem.contract.StartActivityContract;
import com.hecong.cssystem.entity.LoginEntity;
import com.hecong.cssystem.utils.Constant;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class StartActivityModel implements StartActivityContract.Model {
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
