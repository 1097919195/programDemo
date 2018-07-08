package example.com.aaaa.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.WorkerThread;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.jaydenxiao.common.base.BaseActivity;
import com.jaydenxiao.common.baseapp.AppManager;
import com.jaydenxiao.common.commonutils.LogUtils;
import com.jaydenxiao.common.commonutils.ToastUtil;
import com.jaydenxiao.common.commonwidget.LoadingDialog;
import com.jaydenxiao.common.commonwidget.LoadingTip;

import butterknife.Bind;
import example.com.aaaa.R;
import example.com.aaaa.app.AppApplication;
import example.com.aaaa.broadcast.SDCardBroadcastReciver;
import example.com.aaaa.service.ForegroundService;
import example.com.aaaa.service.Myservice;
import example.com.aaaa.service.MyserviceBind;
import example.com.aaaa.service.PushService;
import example.com.aaaa.utils.AudioUtils;
import example.com.aaaa.utils.SystemUtils;

public class MainActivity extends BaseActivity {

    private MyserviceBind myserviceBind;

    private WorkerThread mWorkerThread;
    private Handler mMainHandler;

    @Bind(R.id.mStatusLine)
    TextView mStatusLine;

    @Bind(R.id.swip)
    SwipeRefreshLayout swipe;

    AudioUtils audioUtils=null;

    @Bind(R.id.loadedTip)
    LoadingTip loadingTip;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        //全频显示
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);

        AppManager.getAppManager().addActivity(this);

        //界面跳转
        findViewById(R.id.tiaozhuan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, OtherActivity.class);
                startActivity(intent);

            }
        });

        //发送广播
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.example.broadcasttest.MY_BROADCAST");
                sendBroadcast(intent);
                Log.e("adfasdf","111");
                LogUtils.loge("adfasdf======"+222);
            }
        });

        //开始服务
        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent startIntent = new Intent(MainActivity.this, Myservice.class);
                startService(startIntent);
            }
        });

        //停止服务
        findViewById(R.id.btn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent stopIntent = new Intent(MainActivity.this, Myservice.class);
                stopService(stopIntent);

            }
        });

        //绑定服务
        findViewById(R.id.btn4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bindIntent = new Intent(MainActivity.this, MyserviceBind.class);
                bindService(bindIntent, connection, Context.BIND_AUTO_CREATE);

            }
        });

        //解绑服务
        findViewById(R.id.btn5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unbindService(connection);
            }
        });

        //退出应用程序
        findViewById(R.id.btn6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppManager.getAppManager().AppExit(AppApplication.getAppContext(),true);

            }
        });

        //后台媒体播放
        findViewById(R.id.media_player).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

        //消息队列
        findViewById(R.id.looper).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                loadingTip.setLoadingTip(LoadingTip.LoadStatus.loading);
                LoadingDialog.showDialogForLoading(MainActivity.this, "...", true);

            }
        });

        //发送通知
        findViewById(R.id.send_notification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showNotification(AppApplication.getAppContext());

            }
        });

        //发送后台通知
        findViewById(R.id.send_notification_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, PushService.class);
                startService(intent);

            }
        });

        //前台服务
        findViewById(R.id.foreground_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, ForegroundService.class);
                startService(intent);

            }
        });

        //SDCard系统广播
        findViewById(R.id.broadcast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, SDCardBroadcastReciver.class);
                sendBroadcast(intent);
                Log.e("adfasdf","333");
                LogUtils.loge("adfasdf======"+444);


                view.post(new Runnable() {
                    @Override
                    public void run() {
                        mStatusLine.setText("更新");
                        audioUtils.playTTS("hello");
                    }
                });

            }
        });

    }

    //activity与service绑定
    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myserviceBind = ((MyserviceBind.MyBinder) service).getService();
            LogUtils.loge("====Service连接成功");
            // 执行Service内部自己的方法
            myserviceBind.excute();
            Intent intent = new Intent();
            myserviceBind.onStartCommand(intent, 0, 0);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            myserviceBind = null;
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        audioUtils=new AudioUtils(MainActivity.this);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
    }

    public void refresh() {
        ToastUtil.showShort("刷新完成！");
        swipe.setRefreshing(false);
    }

    public static void showNotification(Context context) {
        //Notification：是具体的状态栏通知对象，可以设置icon、文字、提示声音、振动等等参数。
        Notification notification = new NotificationCompat.Builder(context)
                /**设置通知左边的大图标**/
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                /**设置通知右边的小图标**/
                .setSmallIcon(R.mipmap.ic_launcher)
                /**通知首次出现在通知栏，带上升动画效果的**/
                .setTicker("通知来了")
                /**设置通知的标题**/
                .setContentTitle("这是一个通知的标题")
                /**设置通知的内容**/
                .setContentText("这是一个通知的内容这是一个通知的内容")
                /**通知产生的时间，会在通知信息里显示**/
                .setWhen(System.currentTimeMillis())
                /**设置该通知优先级**/
                .setPriority(Notification.PRIORITY_DEFAULT)
                /**设置这个标志当用户单击面板就可以让通知将自动取消**/
                .setAutoCancel(true)
                /**设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)**/
                .setOngoing(false)
                /**向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合：**/
                .setDefaults(Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND)
                /**点击通知时跳转的界面 **/
                .setContentIntent(PendingIntent.getActivity(context, 1, new Intent(AppApplication.getAppContext(), MainActivity.class), PendingIntent.FLAG_CANCEL_CURRENT))
                .build();
        //NotificationManager ：  是状态栏通知的管理类，负责发通知、清楚通知等。
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

//        /** 点击通知时跳转的界面 **/
//        // 需要注意的是，作为选项，此处可以设置MainActivity的启动模式为singleTop，避免重复新建onCreate()。
//        Intent intent = new Intent(context, OtherActivity.class);
//        // 当用户点击通知栏的Notification时候，切换回TaskDefineActivity。
//        PendingIntent pi = PendingIntent.getActivity(context, 0,
//                intent,  PendingIntent.FLAG_UPDATE_CURRENT);//参数说明 http://blog.csdn.net/bdmh/article/details/41804695
//        notification.contentIntent = pi;

//        Intent intent = new Intent(AppApplication.getAppContext(), ShowNotificationReceiver.class);
//        PendingIntent pendingIntent =
//                PendingIntent.getBroadcast(AppApplication.getAppContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        notification.contentIntent = pendingIntent;

        /**发起通知**/
        notificationManager.notify(0, notification);
    }



    @Override
    protected void onDestroy() {
        //返回3表示进程完全退出
        LogUtils.loge("======="+ SystemUtils.getAppSatus(AppApplication.getAppContext(), "example.com.aaaa"));
        super.onDestroy();
    }
}
