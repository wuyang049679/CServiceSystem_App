package com.hecong.cssystem.model;

import com.hecong.cssystem.api.ApiManager;
import com.hecong.cssystem.base.BaseEntity;
import com.hecong.cssystem.contract.LoginActivityContract;
import com.hecong.cssystem.entity.LoginEntity;
import com.hecong.cssystem.utils.Constant;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginActivityModel implements LoginActivityContract.Model {
    @Override
    public Observable<BaseEntity<LoginEntity.DataBean>> login(String username, String password) {
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put("account", username);
        hashMap.put("password",password);
        hashMap.put("mode","app");
        hashMap.put(Constant.REQUEST_TYPE,Constant.STANDARD);
        return ApiManager.getApistore().toLogin(hashMap).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
