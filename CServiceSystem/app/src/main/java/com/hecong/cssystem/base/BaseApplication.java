package com.hecong.cssystem.base;

import android.app.Activity;
import android.content.Context;

import androidx.multidex.MultiDexApplication;

import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.Set;

/**
 * Desccribe:
 *
 * @author Created by wuyang on 2019/8/30
 */
public class BaseApplication extends MultiDexApplication {
    private static BaseApplication application = null;
    private static Context mContext;
    //用户id，用户唯一标识
    private static String uid ="10001";
    private Set<Activity> allActivities;

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
        //Fecbook初始化
        Fresco.initialize(this);
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
     * 得到登录用户id
     * @return
     */
    public static String getUID() {
        return uid;
    }

    /**
     * 设置用户id
     * @param userID
     */
    public static void setUID(String userID) {
        uid = userID;
    }
}
