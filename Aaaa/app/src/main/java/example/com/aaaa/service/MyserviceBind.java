package example.com.aaaa.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.jaydenxiao.common.commonutils.LogUtils;

/**
 * Created by asus-pc on 2017/11/29.
 */

public class MyserviceBind extends Service{
    private final IBinder mbinder = new MyBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mbinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.loge("====onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    public class MyBinder extends Binder {
        public MyserviceBind getService() {
            return MyserviceBind.this;
        }
    }

    public void excute() {
        LogUtils.loge("====通过Binder得到Service的引用来调用Service内部的方法");
    }


    //服务销毁的时候调用
    @Override
    public void onDestroy() {
        // 当调用者退出(即使没有调用unbindService)或者主动停止服务时会调用
        super.onDestroy();

    }

    //当调用者退出(即使没有调用unbindService)或者主动停止服务时会调用
    @Override
    public boolean onUnbind(Intent intent) {
        LogUtils.loge("====调用者退出了");
        return super.onUnbind(intent);
    }
}
