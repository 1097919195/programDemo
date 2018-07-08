package example.com.aaaa.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import example.com.aaaa.broadcast.ShowNotificationReceiver;

/**
 * Created by asus-pc on 2017/12/7.
 * 在新进程中启动的Service，负责监听服务器，收到服务器的信息后将消息广播出去，在本demo中，为了简化，只是简单的广播一个消息
 */

public class PushService extends Service{
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("PushService", "PushService onCreate");
        //用AlarmManager定时发送广播
        AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(this, ShowNotificationReceiver.class);

        //主要的区别在于Intent的执行立刻的，而pendingIntent的执行不是立刻的,它执行的操作实质上是参数传进来的Intent的操作
        PendingIntent pendingIntent =
                PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+2000, pendingIntent);
        //停止服务
//        stopSelf();

    }

    // TODO 写这个里面试试
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
}
