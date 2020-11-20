package com.hc_android.hc_css.utils.socket;


import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.hc_android.hc_css.R;
import com.hc_android.hc_css.base.BaseApplication;
import com.hc_android.hc_css.entity.MessageDialogEntity;
import com.hc_android.hc_css.entity.MessageEntity;
import com.hc_android.hc_css.ui.activity.MainActivity;
import com.hc_android.hc_css.utils.Constant;
import com.hc_android.hc_css.utils.android.ActivityUtils;
import com.hc_android.hc_css.utils.android.RomUtil;
import com.hc_android.hc_css.utils.android.app.AppConfig;
import com.hc_android.hc_css.utils.android.app.DataProce;
import com.xiaomi.mipush.sdk.MiPushClient;

import me.leolin.shortcutbadger.ShortcutBadger;

import static androidx.core.app.NotificationCompat.PRIORITY_DEFAULT;
import static androidx.core.app.NotificationCompat.VISIBILITY_SECRET;

public class NotificationUtils extends ContextWrapper {

    public static final String CHANNEL_ID = "com.hc_android.hc_css";
    private static final String CHANNEL_NAME = "合从客服";
    private static final String CHANNEL_DESCRIPTION = "this is default channel!";
    private static Ringtone mRingtone;
    private NotificationManager mManager;
    private int notificationId = 0;


    public NotificationUtils(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel();
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannel() {
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
        //是否绕过请勿打扰模式
        channel.canBypassDnd();
        //闪光灯
//        channel.enableLights(true);
        //锁屏显示通知
        channel.setLockscreenVisibility(VISIBILITY_SECRET);
        //闪关灯的灯光颜色
//        channel.setLightColor(Color.RED);
        //桌面launcher的消息角标
        channel.canShowBadge();
        //是否允许震动
        channel.enableVibration(true);
        //获取系统通知响铃声音的配置
//        channel.setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.hint_voice), null);

        channel.setImportance(NotificationManager.IMPORTANCE_HIGH);
//        channel.getAudioAttributes();
        //获取通知取到组
        channel.getGroup();
        //设置可绕过  请勿打扰模式
        channel.setBypassDnd(true);
        //设置震动模式
//        channel.setVibrationPattern(new long[]{100, 200, 100, 200});
        //是否会有灯光
        channel.shouldShowLights();
        getManager().createNotificationChannel(channel);
    }

    private NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }

    /**
     * 发送通知
     */
    public void sendNotification(Context contexts,int unReadCount, String title, String content) {
        if (isSendNotice()) {//是否开启通知
            playVibrate();//震动
            playSound(contexts);//提示
        }
        if (ActivityUtils.isAppIsInBackground(contexts)&&isSendNotice()) {
            String text = "";
            String tit = "";
            if (content != null) text = content;
            if (title != null) tit = title;
            notificationId++;
            NotificationCompat.Builder builder = getNotification(title,tit+": " + text);
//            builder.setOnlyAlertOnce(true);//设置通知声音只响一次
            Notification notification=builder.build();
            ShortcutBadger.applyNotification(getApplicationContext(), notification, unReadCount);
            getManager().notify(notificationId, notification);
        }
    }
    /**
     * 发送通知
     */

    public void sendNotification(Context contexts, int unReadCount, MessageDialogEntity.DataBean.ListBean listBean) {

        if (isSendNotice()) {//是否开启通知
            playVibrate();//震动
            playSound(contexts);//提示
        }
        if (ActivityUtils.isAppIsInBackground(contexts)&&isSendNotice()) {
            String text = "";
            String tit = "";
            String content = DataProce.getContent(listBean);
            String title = DataProce.getTitle(listBean);
            if (content != null) text = content;
            if (title != null) tit = title;
            notificationId++;
            NotificationCompat.Builder builder = getNotification(title, tit+": " +text);
//            builder.setOnlyAlertOnce(true);//设置通知声音只响一次
            Notification notification=builder.build();
            ShortcutBadger.applyNotification(getApplicationContext(), notification, unReadCount);
            getManager().notify(notificationId, notification);
        }
    }

    private NotificationCompat.Builder getNotification(String title, String content) {
        NotificationCompat.Builder builder = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
        } else {
            builder = new NotificationCompat.Builder(getApplicationContext());
            builder.setPriority(PRIORITY_DEFAULT);
        }
        Intent s = new Intent(this, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, s, 0);
        //标题
//        builder.setContentTitle(title);
        builder.setSound(null);
        //文本内容
        builder.setContentText(content);
        //小图标
        builder.setSmallIcon(R.mipmap.logo);
        //设置点击信息后自动清除通知
        builder.setAutoCancel(true);
        builder.setVibrate(new long[]{100, 200, 100, 200});
        builder.setContentIntent(pi);
        return builder;
    }

    /**
     * 发送通知
     */
    public void sendNotification(int notifyId, String title, String content) {
        NotificationCompat.Builder builder = getNotification(title, content);
        getManager().notify(notifyId, builder.build());
    }

    /**
     * 发送带有进度的通知
     */
    public void sendNotificationProgress(String title, String content, int progress, PendingIntent intent) {
        NotificationCompat.Builder builder = getNotificationProgress(title, content, progress, intent);
        getManager().notify(0, builder.build());
    }

    /**
     * 获取带有进度的Notification
     */
    private NotificationCompat.Builder getNotificationProgress(String title, String content,
                                                               int progress, PendingIntent intent) {
        NotificationCompat.Builder builder = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
        } else {
            builder = new NotificationCompat.Builder(getApplicationContext());
            builder.setPriority(PRIORITY_DEFAULT);
        }
        //标题
        builder.setContentTitle(title);
        //文本内容
        builder.setContentText(content);
        //小图标
        builder.setSmallIcon(R.mipmap.ic_launcher);
        //设置大图标，未设置时使用小图标代替，拉下通知栏显示的那个图标
        //设置大图片 BitmpFactory.decodeResource(Resource res,int id) 根据给定的资源Id解析成位图
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        if (progress > 0 && progress < 100) {
            //一种是有进度刻度的（false）,一种是循环流动的（true）
            //设置为false，表示刻度，设置为true，表示流动
            builder.setProgress(100, progress, false);
        } else {
            //0,0,false,可以将进度条隐藏
            builder.setProgress(0, 0, false);
            builder.setContentText("下载完成");
        }

        //设置点击信息后自动清除通知
        builder.setAutoCancel(true);
        //通知的时间
        builder.setWhen(System.currentTimeMillis());
        //设置点击信息后的跳转（意图）
        builder.setContentIntent(intent);
        return builder;
    }

    //播放自定义的声音
    public static synchronized void playSound(Context context) {
//        if (!allowMusic) {
//            return;
//        }
        if (!AppConfig.isIsOpenVoice())return;
        if (getRingerMode()==AudioManager.RINGER_MODE_NORMAL) {
            if (mRingtone == null) {
                String uri = "android.resource://" + context.getPackageName() + "/" + R.raw.hint_voice;
                Uri no = Uri.parse(uri);
                mRingtone = RingtoneManager.getRingtone(context.getApplicationContext(), no);
            }
            if (!mRingtone.isPlaying()) {
                mRingtone.play();
            }
        }

    }

    /**
     * 播放通知声音
     */
    public void playRingTone() {
        if (getRingerMode()==AudioManager.RINGER_MODE_NORMAL) {
            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone rt = RingtoneManager.getRingtone(BaseApplication.getInstance().getApplicationContext(), Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.hint_voice));
            rt.play();
        }
    }

    /**
     * 手机震动一下
     */
    public  static void playVibrate() {

        if (!AppConfig.isIsOpenVibrator())return;
        if (getRingerMode()!=AudioManager.RINGER_MODE_SILENT) {
            Vibrator vibrator = (Vibrator) BaseApplication.getInstance().getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
            long[] vibrationPattern = new long[]{400, 200};
            // 第一个参数为开关开关的时间，第二个参数是重复次数，振动需要添加权限
            vibrator.vibrate(vibrationPattern, -1);
        }
    }


    /**
     * 获取手机声音模式
     */
    public static int getRingerMode(){
        try {
        AudioManager audioManager = (AudioManager) BaseApplication.getInstance().getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        return audioManager.getRingerMode();
        }catch (Exception e){}
        return AudioManager.RINGER_MODE_NORMAL;
    }


    /**
     * 清空通知栏消息
     */
    public  void clearNotice(){

        if (mManager!=null) mManager.cancelAll();
        if (RomUtil.isMiui()) {
            MiPushClient.clearNotification(getApplicationContext());
        }
    }
    /**
     * 判断是否开启通知按钮
     */

    public static boolean isSendNotice(){
        if (!AppConfig.isIsOpenNotice())return false;//是否开启消息通知
        if (BaseApplication.getUserBean().getSocketId()!=null&&!AppConfig.isIsOpenMeanwhile())return false;//电脑端在线，然后不开启同时提醒

        return true;
    }


}
