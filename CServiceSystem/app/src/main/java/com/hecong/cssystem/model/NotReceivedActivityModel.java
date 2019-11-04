package com.hecong.cssystem.model;

import com.hecong.cssystem.api.ApiManager;
import com.hecong.cssystem.base.BaseApplication;
import com.hecong.cssystem.base.BaseEntity;
import com.hecong.cssystem.contract.NotReceivedActivityContract;
import com.hecong.cssystem.entity.ReceptionEntity;
import com.hecong.cssystem.utils.Constant;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NotReceivedActivityModel implements NotReceivedActivityContract.Model {
    @Override
    public Observable<BaseEntity<ReceptionEntity.DataBean>> receptionDialog(String dialogId) {
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put(Constant.KEY_HASH, BaseApplication.getUserEntity().getHash());
        hashMap.put(Constant.REQUEST_TYPE,Constant.STANDARD);
        hashMap.put("dialogId",dialogId);

        return ApiManager.getApistore().receptionDialog(hashMap).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
