package example.com.aaaa.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;

import example.com.aaaa.R;
import example.com.aaaa.app.AppApplication;

/**
 * Created by asus-pc on 2017/12/8.
 * 前台服务（白色保活）
 */

public class ForegroundService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Notification notification = new NotificationCompat.Builder(AppApplication.getAppContext())
                .setContentText("这是一个前台服务")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setWhen(System.currentTimeMillis())
                .build();

        startForeground(1,notification);
    }
}
