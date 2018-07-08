package example.com.aaaa.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.jaydenxiao.common.commonutils.LogUtils;

/**
 * Created by asus-pc on 2017/11/27.
 */

public class Myservice extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //服务第一次创建的时候调用
    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.loge("onCreate");
    }

    //每次服务启动的时候调用
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.loge("onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    //服务销毁的时候调用
    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.loge("onDestroy");
    }
}
