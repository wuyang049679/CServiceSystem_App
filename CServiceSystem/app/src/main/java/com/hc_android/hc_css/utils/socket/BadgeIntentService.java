package com.hc_android.hc_css.utils.socket;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.hc_android.hc_css.R;
import com.hc_android.hc_css.ui.activity.MainActivity;

import me.leolin.shortcutbadger.ShortcutBadger;

public class BadgeIntentService extends IntentService {

    private static final String NOTIFICATION_CHANNEL = "com.hc_android.hc_css";

    private int notificationId = 0;

    public BadgeIntentService() {
        super("BadgeIntentService");
    }

    private NotificationManager mNotificationManager;

    @Override
    public void onCreate() {
        super.onCreate();
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }
    
    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            int badgeCount = intent.getIntExtra("badgeCount", 0);
            String badgeContent = intent.getStringExtra("NOTIF_TXT");

            mNotificationManager.cancel(notificationId);
            notificationId++;
            Intent s = new Intent(this, MainActivity.class);
            PendingIntent pi = PendingIntent.getActivity(this, 0, s, 0);

            Notification.Builder builder = new Notification.Builder(getApplicationContext())
//                    .setContentTitle("合从客服")
                    .setContentText(badgeContent)
                    .setContentIntent(pi)
                    .setSound(null)
                    .setWhen(System.currentTimeMillis())//设置通知时间
                     .setSmallIcon(R.mipmap.logo);



            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                setupNotificationChannel();

                builder.setChannelId(NOTIFICATION_CHANNEL);
            }
            Notification notification = builder.build();
            notification.flags=Notification.FLAG_AUTO_CANCEL;
            ShortcutBadger.applyNotification(getApplicationContext(), notification, badgeCount);
            mNotificationManager.notify(notificationId, notification);
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void setupNotificationChannel() {
        NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL, "hecong",
                NotificationManager.IMPORTANCE_DEFAULT);

        mNotificationManager.createNotificationChannel(channel);
    }


}
