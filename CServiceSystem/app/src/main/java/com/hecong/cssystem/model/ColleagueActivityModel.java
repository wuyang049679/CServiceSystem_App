package com.hecong.cssystem.model;

import com.hecong.cssystem.api.ApiManager;
import com.hecong.cssystem.base.BaseApplication;
import com.hecong.cssystem.base.BaseEntity;
import com.hecong.cssystem.contract.ColleagueActivityContract;
import com.hecong.cssystem.entity.TeamEntity;
import com.hecong.cssystem.utils.Constant;

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
}
