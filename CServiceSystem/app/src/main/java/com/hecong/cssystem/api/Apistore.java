package com.hecong.cssystem.api;


import com.hecong.cssystem.base.BaseEntity;
import com.hecong.cssystem.entity.LoginEntity;
import com.hecong.cssystem.entity.MessageDialogEntity;
import com.hecong.cssystem.entity.ReceptionEntity;
import com.hecong.cssystem.entity.TeamEntity;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * The interface Apistore.
 *
 * @author wuayng
 */
public interface Apistore {


    /**
     * 登录操作
     * @param map
     * @return
     */
    @GET("account/login")
    Observable<BaseEntity<LoginEntity.DataBean>> toLogin(@QueryMap HashMap<String, String> map);

    /**
     * 获取对话列表
     * @param map
     * @return
     */
    @GET("dialog/get")
    Observable<BaseEntity<MessageDialogEntity.DataBean>> showMessageDialog(@QueryMap HashMap<String, String> map);

    /**
     * 获取同事列表
     * @param map
     * @return
     */
    @GET("company/control")
    Observable<BaseEntity<TeamEntity.DataBean>> getTeamList(@QueryMap HashMap<String, String> map);

    /**
     * 获取新对话列表信息
     * @param map
     * @return
     */
    @GET("dialog/getone")
    Observable<BaseEntity<MessageDialogEntity.DataBean>> getNewDialog(@QueryMap HashMap<String, String> map);


   /**
     * 对话被接待
     * @param map
     * @return
     */
    @GET("dialog/reception")
    Observable<BaseEntity<ReceptionEntity.DataBean>> receptionDialog(@QueryMap HashMap<String, String> map);

    /**
     * 对话结束
     * @param
     * @return
     */
    @FormUrlEncoded
    @POST("dialog/autoend")
    Observable<BaseEntity<ReceptionEntity.DataBean>> endDialog(@FieldMap Map<String, String> map);
}