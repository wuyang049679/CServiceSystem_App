package com.hecong.cssystem.base;

import android.app.Activity;
import android.content.Context;

import androidx.multidex.MultiDexApplication;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.hecong.cssystem.entity.UserEntity;

import java.util.Set;

/**
 * Desccribe:
 *
 * @author Created by wuyang on 2019/8/30
 */
public class BaseApplication extends MultiDexApplication {
    private static BaseApplication application = null;
    private static Context mContext;
    private Set<Activity> allActivities;
    public static UserEntity userEntity;



    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        application = this;
        init();
    }

    public static BaseApplication getBaseApplication(){
        return application;
    }

    /**
     * 得到Application环境变量
     * @return
     */
    public static Context getContext() {
        return mContext;
    }

    /**
     * 初始化话app信息
     */
    private void init() {
        //用户信息
        userEntity=new UserEntity();
        //Fecbook初始化
        Fresco.initialize(this);
        //Gson初始化

    }

    /**
     * 退出app
     */
    public void exitApp() {
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    /**
     * 退出app
     */
    public void exitAppAll() {
        if (allActivities != null) {
            synchronized (allActivities) {
                for (Activity act : allActivities) {
                    act.finish();
                }
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }


    /**
     * 用户信息保存
     * @return
     */
    public static UserEntity getUserEntity() {
        return userEntity;
    }

    public static void setUserEntity(UserEntity userEntity) {
        BaseApplication.userEntity = userEntity;
    }
}
