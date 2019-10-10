package com.hecong.cssystem.api;


import com.hecong.cssystem.base.BaseEntity;
import com.hecong.cssystem.entity.MineEntity;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * The interface Apistore.
 *
 * @author wuayng
 */
public interface Apistore {


    /**
     * 示例接口
     * @param body
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("app/goods/list")
    Observable<BaseEntity<MineEntity>> example(@Body RequestBody body);


}