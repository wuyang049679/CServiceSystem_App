package com.hc_android.hc_css.model;

import com.hc_android.hc_css.api.ApiManager;
import com.hc_android.hc_css.base.BaseApplication;
import com.hc_android.hc_css.base.BaseEntity;
import com.hc_android.hc_css.contract.ChatActivityContract;
import com.hc_android.hc_css.entity.ChatEntity;
import com.hc_android.hc_css.entity.CustomPathEntity;
import com.hc_android.hc_css.entity.IneValuateEntity;
import com.hc_android.hc_css.entity.QuickEntity;
import com.hc_android.hc_css.entity.QuickGroupEntity;
import com.hc_android.hc_css.entity.SendEntity;
import com.hc_android.hc_css.entity.TokenEntity;
import com.hc_android.hc_css.utils.Constant;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ChatActivityModel implements ChatActivityContract.Model {
    @Override
    public Observable<BaseEntity<CustomPathEntity.DataBean>> getRoutes(String realtimeId, String customerId,String visitorId) {
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put(Constant.KEY_HASH, BaseApplication.getUserEntity().getHash());
        hashMap.put(Constant.REQUEST_TYPE,Constant.STANDARD);
        if (realtimeId!=null) {
            hashMap.put("realtimeId",realtimeId);
        }
        if (visitorId!=null){
            hashMap.put("visitorId",visitorId);
        }else {
            if (customerId != null) {
                hashMap.put("customerId", customerId);
            }
        }

        return ApiManager.getApistore().getRoutes(hashMap).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<BaseEntity<ChatEntity.DataBean>> getChatList(String dialogId, String serviceId, int entId, int limit, int skip) {
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put("serviceHash", BaseApplication.getUserEntity().getHash());
        hashMap.put(Constant.REQUEST_TYPE,Constant.STANDARD);
        hashMap.put(Constant.SERVICEID,serviceId);
        hashMap.put(Constant.DIALOGID,dialogId);
        hashMap.put(Constant.ENTID,entId+"");
        hashMap.put("limit",limit+"");
        if (skip!=0) {
            hashMap.put("skip", skip + "");
        }
        return ApiManager.getApistore().getChatList(hashMap).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<BaseEntity<SendEntity.DataBean>> sendMsg(String dialogId, String serviceId, String customerId, String socketId, String type, String contents, String key, int entId) {
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put(Constant.REQUEST_TYPE,Constant.STANDARD);
        hashMap.put(Constant.SERVICEID,serviceId);
        hashMap.put(Constant.DIALOGID,dialogId);
        hashMap.put(Constant.CUSTOMERID,customerId);
        hashMap.put(Constant.SOCKETID,socketId);
        hashMap.put(Constant.ENTID,entId+"");
        hashMap.put("sendType","service");
        hashMap.put("contents",contents);
        hashMap.put("type",type);
        hashMap.put("key",key);
        return ApiManager.getApistore().sendMsg(hashMap).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<BaseEntity<TokenEntity.DataBean>> getToken(String type) {
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put(Constant.REQUEST_TYPE,Constant.STANDARD);
        hashMap.put(Constant.KEY_HASH,BaseApplication.getUserEntity().getHash());
        hashMap.put("type",type);
        return ApiManager.getApistore().getToken(hashMap).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<BaseEntity<TokenEntity.DataBean>> sendRead(String dialogId, String customerId, String mids, int entId) {
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put(Constant.REQUEST_TYPE,Constant.STANDARD);
        hashMap.put(Constant.KEY_HASH,BaseApplication.getUserEntity().getHash());
        hashMap.put(Constant.DIALOGID,dialogId);
        hashMap.put(Constant.CUSTOMERID,customerId);
        hashMap.put(Constant.ENTID,entId+"");
        hashMap.put("mids",mids);
        return ApiManager.getApistore().sendRead(hashMap).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<BaseEntity<IneValuateEntity.DataBean>> sendIneValuate(String dialogId) {
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put(Constant.REQUEST_TYPE,Constant.STANDARD);
        hashMap.put(Constant.KEY_HASH,BaseApplication.getUserEntity().getHash());
        hashMap.put(Constant.DIALOGID,dialogId);
        return ApiManager.getApistore().sendInevaluate(hashMap).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<BaseEntity<IneValuateEntity.DataBean>> msgUndo(String msgId,String serviceId,int entId) {
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put(Constant.REQUEST_TYPE,Constant.STANDARD);
        hashMap.put(Constant.KEY_HASH,BaseApplication.getUserEntity().getHash());
        hashMap.put(Constant.ENTID,entId+"");
        hashMap.put(Constant.SERVICEID,serviceId);
        hashMap.put("msgId",msgId);
        return ApiManager.getApistore().msgUndo(hashMap).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<BaseEntity<QuickEntity.DataBean>> getQuickList(String serviceId, boolean team) {
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put(Constant.REQUEST_TYPE,Constant.STANDARD);
        hashMap.put(Constant.KEY_HASH, BaseApplication.getUserEntity().getHash());
        if (serviceId!=null) {
            hashMap.put(Constant.SERVICEID, serviceId);
        }
        if (team){
            hashMap.put("team","true");
        }

        return ApiManager.getApistore().getQuickList(hashMap).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<BaseEntity<IneValuateEntity.DataBean>> quickUse(String id) {
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put(Constant.REQUEST_TYPE,Constant.STANDARD);
        hashMap.put(Constant.KEY_HASH, BaseApplication.getUserEntity().getHash());
        hashMap.put("id",id);
        return ApiManager.getApistore().quickUsage(hashMap).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<BaseEntity<IneValuateEntity.DataBean>> reopen(String key,String customerId, String historyId, String source, String message) {
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put(Constant.REQUEST_TYPE,Constant.STANDARD);
        hashMap.put(Constant.KEY_HASH, BaseApplication.getUserEntity().getHash());
        hashMap.put("customerId",customerId);
        hashMap.put("historyId",historyId);
        hashMap.put("source",source);
        hashMap.put("message",message);
        return ApiManager.getApistore().reopen(hashMap).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<BaseEntity<IneValuateEntity.DataBean>> realtimeActive(String realtimeId) {
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put(Constant.KEY_HASH, BaseApplication.getUserEntity().getHash());
        hashMap.put(Constant.REQUEST_TYPE,Constant.STANDARD);
        hashMap.put(Constant.SERVICEID,BaseApplication.getUserBean().getId());
        if (realtimeId!=null) {
            hashMap.put("realtimeId",realtimeId);
        }

        return ApiManager.getApistore().realtimeActive(hashMap).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<BaseEntity<IneValuateEntity.DataBean>> pushwechat(String key,String customerId, String historyId, String source, String message,String type, String contents, String entId) {
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put(Constant.REQUEST_TYPE,Constant.STANDARD);
        hashMap.put(Constant.KEY_HASH, BaseApplication.getUserEntity().getHash());
        hashMap.put("customerId",customerId);
        hashMap.put("type",type);
        hashMap.put("contents",contents);
        hashMap.put("entId",entId);
        return ApiManager.getApistore().pushwechat(hashMap).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

}
