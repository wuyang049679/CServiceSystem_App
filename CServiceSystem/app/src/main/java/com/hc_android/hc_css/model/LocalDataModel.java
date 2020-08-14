package com.hc_android.hc_css.model;

import com.hc_android.hc_css.api.ApiManager;
import com.hc_android.hc_css.base.BaseApplication;
import com.hc_android.hc_css.base.BaseEntity;
import com.hc_android.hc_css.contract.LocalDataContract;
import com.hc_android.hc_css.entity.TagEntity;
import com.hc_android.hc_css.utils.Constant;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LocalDataModel implements LocalDataContract.Model {
    @Override
    public Observable<BaseEntity<TagEntity.DataBean>> getTagList() {
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put(Constant.REQUEST_TYPE,Constant.STANDARD);
        hashMap.put(Constant.KEY_HASH, BaseApplication.getUserEntity().getHash());
        return ApiManager.getApistore().getTag(hashMap).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
