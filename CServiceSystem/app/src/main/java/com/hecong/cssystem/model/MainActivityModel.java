package com.hecong.cssystem.model;


import com.hecong.cssystem.api.ApiManager;
import com.hecong.cssystem.base.BaseEntity;
import com.hecong.cssystem.contract.MainActivityContract;
import com.hecong.cssystem.entity.LoginEntity;
import com.hecong.cssystem.entity.MineEntity;
import com.hecong.cssystem.utils.RequestBodyBuilder;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;


/**
 * Desccribe:
 *
 * @author Created by wuyang on 2019/9/2
 */
public class MainActivityModel implements MainActivityContract.Model {

    @Override
    public Observable<BaseEntity<LoginEntity>> checkLogin(String hash) {
        RequestBodyBuilder.Builder builder = new RequestBodyBuilder.Builder();
        RequestBody requestBody = builder
                .params("mode","app")
                .params("hash",hash)
                .build();
        return ApiManager.getApistore().toLogin(requestBody).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
