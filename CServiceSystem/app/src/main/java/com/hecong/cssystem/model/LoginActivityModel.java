package com.hecong.cssystem.model;

import com.hecong.cssystem.api.ApiManager;
import com.hecong.cssystem.base.BaseEntity;
import com.hecong.cssystem.contract.LoginActivityContract;
import com.hecong.cssystem.entity.LoginEntity;
import com.hecong.cssystem.utils.RequestBodyBuilder;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

public class LoginActivityModel implements LoginActivityContract.Model {
    @Override
    public Observable<BaseEntity<LoginEntity>> login(String username, String password) {
        RequestBodyBuilder.Builder builder = new RequestBodyBuilder.Builder();
        RequestBody requestBody = builder
                .params("account",username)
                .params("password",password)
                .build();
        return ApiManager.getApistore().toLogin(requestBody).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
