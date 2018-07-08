package example.com.aaaa.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.jaydenxiao.common.commonutils.LogUtils;

import java.util.List;

import example.com.aaaa.activity.MainActivity;
import example.com.aaaa.activity.OtherActivity;
import example.com.aaaa.app.AppApplication;

import static example.com.aaaa.utils.SystemUtils.getAppSatus;

/**
 * Created by asus-pc on 2017/12/7.
 *  在新进程中注册的BroadcastReceiver，用来设置点击通知栏通知的动作，打开app中的某个Activity
 */

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        /** 点击通知时跳转的界面 **/
        LogUtils.loge("======="+getAppSatus(AppApplication.getAppContext(), "example.com.aaaa"));
        //判断应用程序是否在进程中
        if (getAppSatus(AppApplication.getAppContext(), "example.com.aaaa")==1) {
            //app进程存活
            Intent mainIntent = new Intent(context, MainActivity.class);
            mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Intent detailIntent = new Intent(context, OtherActivity.class);
            Intent[] intents = {mainIntent, detailIntent};
            context.startActivities(intents);
        }else {
            Intent launchIntent = context.getPackageManager().
                    getLaunchIntentForPackage("example.com.aaaa");
            Intent detailIntent = new Intent(context, OtherActivity.class);
            Intent[] intents = {launchIntent, detailIntent};
            context.startActivities(intents);

            //开启QQ,注意先判断是否安装有该程序可用
//            PackageManager manager = context.getPackageManager();
//            Intent openQQ = manager.getLaunchIntentForPackage("com.tencent.mobileqq");
//            context.startActivity(openQQ);
        }
    }

    /**
     * 判断微信是否可用
     *
     * @param context
     * @return
     */
    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {//更换包名“com.tencent.mm”
                    return true;
                }
            }
        }
        return false;
    }
}
