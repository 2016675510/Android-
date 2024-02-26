package com.example.administrator.test2.DBComponent;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;

import com.example.administrator.test2.R;


import android.support.v4.app.NotificationCompat;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimeService extends Service {
    private static Timer timer = null;
    private NotificationManager manager;
    private NotificationCompat.Builder builder;


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /** 清除通知     */
    private void cleanAllNotification() {
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancelAll();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        long period = 60 * 1000; //1分钟一个周期
        if (timer == null) {
            timer = new Timer();
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
               notifys( "系统当前时间为：" + formatter.format(date));
            }
        }, 0, period);

        return super.onStartCommand(intent, flags, startId);
    }

    public     void notifys( String text){
        NotificationManager   notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //处理版本兼容问题
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel manager = new NotificationChannel("campus_notice", "通知", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(manager);
        }
        // 获得RingtoneManager对象
        RingtoneManager ringtoneManager = new RingtoneManager(this);
        // 设置RingtoneManager对象的类型为TYPE_NOTIFICATION，这样只会获取到notification的对应内容
        ringtoneManager.setType(RingtoneManager.TYPE_NOTIFICATION);
        Cursor cursor = ringtoneManager.getCursor();
        Uri soundUri = ringtoneManager.getRingtoneUri(1);

        int notifyId = (int) System.currentTimeMillis();
        //实现通知栏的定制化
        Notification notification = new NotificationCompat.Builder(TimeService.this, "campus_notice")
                .setContentText(text)
                .setSmallIcon(R.drawable.add_notepad)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(Notification.DEFAULT_VIBRATE | Notification.DEFAULT_ALL | Notification.DEFAULT_SOUND )
                .build();
        notificationManager.notify(notifyId, notification);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cleanAllNotification();
        System.out.println("onDestory: cleanAllNotification ");
    }
}
