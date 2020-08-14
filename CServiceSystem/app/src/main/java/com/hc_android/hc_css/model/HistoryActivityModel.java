package com.hc_android.hc_css.model;

import com.hc_android.hc_css.api.ApiManager;
import com.hc_android.hc_css.base.BaseApplication;
import com.hc_android.hc_css.base.BaseEntity;
import com.hc_android.hc_css.contract.HistoryActivityContract;
import com.hc_android.hc_css.entity.MessageDialogEntity;
import com.hc_android.hc_css.utils.Constant;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HistoryActivityModel implements HistoryActivityContract.Model {
    @Override
    public Observable<BaseEntity<MessageDialogEntity.DataBean>> getHistoryList(String condition, int limit, int skip) {
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put(Constant.REQUEST_TYPE,Constant.STANDARD);
        hashMap.put(Constant.KEY_HASH, BaseApplication.getUserEntity().getHash());
        hashMap.put("limit",limit+"");
        hashMap.put("skip",skip+"");
        if (condition!=null) {
            hashMap.put("condition", condition);
        }

        return ApiManager.getApistore().getHistoryList(hashMap).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
