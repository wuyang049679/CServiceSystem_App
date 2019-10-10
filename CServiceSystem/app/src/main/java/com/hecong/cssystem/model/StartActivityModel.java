package com.hecong.cssystem.model;

import com.hecong.cssystem.api.ApiManager;
import com.hecong.cssystem.base.BaseEntity;
import com.hecong.cssystem.contract.StartActivityContract;
import com.hecong.cssystem.entity.LoginEntity;
import com.hecong.cssystem.utils.RequestBodyBuilder;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

public class StartActivityModel implements StartActivityContract.Model {
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
