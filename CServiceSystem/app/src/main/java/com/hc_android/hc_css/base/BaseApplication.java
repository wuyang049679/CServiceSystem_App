package com.hc_android.hc_css.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Environment;
import android.os.Handler;
import android.os.Process;
import android.util.Log;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.alibaba.security.cloud.CloudRealIdentityTrigger;
import com.facebook.drawee.backends.pipeline.Fresco;

import com.google.gson.Gson;
import com.hc_android.hc_css.R;
import com.hc_android.hc_css.entity.LoginEntity;
import com.hc_android.hc_css.entity.UserEntity;

import com.hc_android.hc_css.greendao.gen.DaoManager;
import com.hc_android.hc_css.service.NetworkConnectChangedReceiver;
import com.hc_android.hc_css.service.OPPOPushReceiver;
import com.hc_android.hc_css.utils.FileUtils;
import com.hc_android.hc_css.utils.JsonParseUtils;
import com.hc_android.hc_css.utils.MoonEmoji.LQREmotionKit;
import com.hc_android.hc_css.utils.android.SharedPreferencesUtils;
import com.hc_android.hc_css.utils.android.app.AppParam;
import com.hc_android.hc_css.wight.CustomFooter;
import com.hc_android.hc_css.wight.CustomHeader;
import com.heytap.mcssdk.PushManager;
import com.huawei.hmf.tasks.OnCompleteListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.push.HmsMessaging;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.smtt.sdk.QbSdk;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.io.File;
import java.util.List;
import java.util.Set;


/**
 * Desccribe:
 *
 * @author Created by wuyang on 2019/8/30
 */
public class BaseApplication extends MultiDexApplication {
    public static IWXAPI mWXapi;
    private static BaseApplication application = null;
    private static Context mContext;
    private Set<Activity> allActivities;
    public static UserEntity userEntity;
    private static long mMainThreadId;//主线程id
    private static Handler mHandler;//主线程Handler
    private NetworkConnectChangedReceiver mNetWorkChangReceiver;

    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> {
            layout.setPrimaryColorsId(R.color.background, R.color.background);//全局设置主题颜色
            return new CustomHeader(context);//
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) -> {
            return new CustomFooter(context);
        });
    }


    public static BaseApplication getInstance() {
        return application;
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        application = this;
        mMainThreadId = android.os.Process.myTid();
        mHandler = new Handler();
        init();
        clearFile();

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
        //bugly初始化
        CrashReport.initCrashReport(getApplicationContext(), AppParam.BuglyID, false);
        //用户信息
        userEntity=new UserEntity();
        //Fecbook初始化
        Fresco.initialize(this);
        //Dao数据库初始化
        initGreenDao();
        //微信Sdk
        registerToWX();
        //Gson初始化
        LQREmotionKit.init(this);

        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
            @Override
            public void onViewInitFinished(boolean arg0) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("app", " onViewInitFinished is " + arg0);
            }
            @Override
            public void onCoreInitFinished() {
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(),  cb);

        // 注册小米push服务，注册成功后会向DemoMessageReceiver发送广播
        // 可以从DemoMessageReceiver的onCommandResult方法中MiPushCommandMessage对象参数中获取注册信息
        if (shouldInit()) {
            MiPushClient.registerPush(this, AppParam.APPID_MI, AppParam.APPKEY_MI);

        }
        //oppo推送注册
        if(PushManager.isSupportPush(this)){
            PushManager.getInstance().register(this,AppParam.APPKEY_OPPO,AppParam.APPSECRET_OPPO,new OPPOPushReceiver());
        }


        //注册网络状态监听广播
        mNetWorkChangReceiver = new NetworkConnectChangedReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mNetWorkChangReceiver, filter);

    }

    /**
     * 解除注册一些东西
     */
    public void unRegisterSomething() {
        unregisterReceiver(mNetWorkChangReceiver);
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
     * 清理视频和图片缓存文件
     */
    private void clearFile() {
        //
        File downloadFile = new File(Environment.getExternalStorageDirectory(), "hecong/chatfile");
        FileUtils.deleteAllFile(downloadFile.getPath());//删除目录下的文件
    }

    /**
     * 用户信息保存
     * @return
     */
    public static UserEntity getUserEntity() {

        if (userEntity == null || userEntity.getUserbean() == null){
            userEntity = new UserEntity();
            LoginEntity.DataBean.InfoBean infoBean = JsonParseUtils.parseToObject((String) SharedPreferencesUtils.getParam("_UserEntity", ""), LoginEntity.DataBean.InfoBean.class);
            userEntity.setUserbean(infoBean == null ? new LoginEntity.DataBean.InfoBean() : infoBean);
        }
       return userEntity;
    }

    public static void setUserEntity(UserEntity userEntity) {
        if (userEntity.getUserbean() != null) {
            String s = JsonParseUtils.parseToJson(userEntity.getUserbean());
            if (userEntity.getUserbean() != null) SharedPreferencesUtils.setParam("_UserEntity", s);
        }
        BaseApplication.userEntity = userEntity;
    }

    /**
     * 用户信息详情
     * @return
     */
    public static LoginEntity.DataBean.InfoBean getUserBean() {

        if (userEntity==null || userEntity.getUserbean() ==null){
            userEntity = new UserEntity();
            LoginEntity.DataBean.InfoBean infoBean = JsonParseUtils.parseToObject((String) SharedPreferencesUtils.getParam("_UserEntity", ""), LoginEntity.DataBean.InfoBean.class);
            userEntity.setUserbean(infoBean ==null ? new LoginEntity.DataBean.InfoBean() : infoBean);
        }
        return userEntity.getUserbean();
    }


    /**
     * 因为推送服务XMPushService在AndroidManifest.xml中设置为运行在另外一个进程，这导致本Application会被实例化两次，所以我们需要让应用的主进程初始化。
     * @return
     */
    private boolean shouldInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 初始化GreenDao,直接在Application中进行初始化操作
     */
    private void initGreenDao() {
        DaoManager mManager = DaoManager.getInstance();
        mManager.init(this);
    }

    /**
     * 微信sdk注册
     */
    private void registerToWX() {
        mWXapi = WXAPIFactory.createWXAPI(application, AppParam.APPID_WECHAT, false);
        mWXapi.registerApp( AppParam.APPID_WECHAT);
    }

    public static long getMainThreadId() {
        return mMainThreadId;
    }

    public static Handler getMainHandler() {
        return mHandler;
    }



}
