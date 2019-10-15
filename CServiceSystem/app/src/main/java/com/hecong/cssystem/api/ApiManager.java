package com.hecong.cssystem.api;


import android.content.Context;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.hecong.cssystem.api.persistentcookie.ClearableCookieJar;
import com.hecong.cssystem.api.persistentcookie.PersistentCookieJar;
import com.hecong.cssystem.api.persistentcookie.cache.SetCookieCache;
import com.hecong.cssystem.api.persistentcookie.persistence.SharedPrefsCookiePersistor;
import com.hecong.cssystem.base.BaseApplication;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *
 * @author wuyang
 * @date 2018/8/1
 * https://blog.csdn.net/maosidiaoxian/article/details/78635550
 */

public class ApiManager {
    private static Apistore apistore;
    private static int TIME_OUT = 10;
    public static Context appContext = BaseApplication.getContext();
    public static ClearableCookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(appContext));


    public static Apistore getApistore() {
        synchronized (ApiManager.class) {
            if (apistore == null) {
                OkHttpClient okHttpClient = new OkHttpClient.Builder()
                        .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                        .readTimeout(TIME_OUT,TimeUnit.SECONDS)
                        .writeTimeout(TIME_OUT,TimeUnit.SECONDS)
                        .cookieJar(cookieJar)
//                        .addInterceptor(new VerifyLoginInterceptor())
//                        .addInterceptor(new LogInterceptor(false))
                        .build();
                //创建retrofit实例对象
                Retrofit retrofit = new Retrofit.Builder()
                        //设置服务器主机,以"/"结尾
                        .baseUrl(Address.BASEURL)
                        //配置gson解析器
                        .addConverterFactory(GsonConverterFactory.create())
                        //响应回调
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .client(okHttpClient)
                        .build();
                apistore = retrofit.create(Apistore.class);
            }
        }
        return apistore;
    }
}
