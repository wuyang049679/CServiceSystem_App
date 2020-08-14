package com.hc_android.hc_css.model;

import com.hc_android.hc_css.api.ApiManager;
import com.hc_android.hc_css.base.BaseApplication;
import com.hc_android.hc_css.base.BaseEntity;
import com.hc_android.hc_css.contract.ColleagueActivityContract;
import com.hc_android.hc_css.entity.ReceptionEntity;
import com.hc_android.hc_css.entity.TeamEntity;
import com.hc_android.hc_css.utils.Constant;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ColleagueActivityModel implements ColleagueActivityContract.Model {
    @Override
    public Observable<BaseEntity<TeamEntity.DataBean>> getTeamList() {
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put(Constant.KEY_HASH, BaseApplication.getUserEntity().getHash());
        hashMap.put(Constant.REQUEST_TYPE,Constant.STANDARD);

        return ApiManager.getApistore().getTeamList(hashMap).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<BaseEntity<ReceptionEntity.DataBean>> endDialog(String idList, String offEnd, String autoEnd) {
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put(Constant.KEY_HASH, BaseApplication.getUserEntity().getHash());
        hashMap.put(Constant.REQUEST_TYPE,Constant.STANDARD);
        hashMap.put("idList",idList);
        if (offEnd!=null)hashMap.put("offEnd",offEnd);
        if (autoEnd!=null)hashMap.put("autoEnd",autoEnd);
        return ApiManager.getApistore().endDialog(hashMap).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
