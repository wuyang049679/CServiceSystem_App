package com.hecong.cssystem.model;

import com.hecong.cssystem.api.ApiManager;
import com.hecong.cssystem.base.BaseApplication;
import com.hecong.cssystem.base.BaseEntity;
import com.hecong.cssystem.contract.ChatListFragmentContract;
import com.hecong.cssystem.entity.MessageDialogEntity;
import com.hecong.cssystem.utils.Constant;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ChatListFragmentModel implements ChatListFragmentContract.Model {
    @Override
    public Observable<BaseEntity<MessageDialogEntity.DataBean>> showMessageDialog(int limit, int skip) {

        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put(Constant.KEY_HASH, BaseApplication.getHash());
        hashMap.put("limit",limit+"");
        hashMap.put("skip",skip+"");
        hashMap.put(Constant.REQUEST_TYPE,Constant.STANDARD);

        return ApiManager.getApistore().showMessageDialog(hashMap).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}