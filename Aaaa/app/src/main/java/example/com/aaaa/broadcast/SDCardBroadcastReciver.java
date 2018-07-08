package example.com.aaaa.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.jaydenxiao.common.commonutils.ToastUtil;

/**
 * Created by asus-pc on 2018/1/23.
 */

public class SDCardBroadcastReciver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //[1]获取到当前广播的事件类型
        String action = intent.getAction();
        //[2]对action做一个判断
        if("android.intent.action.MEDIA_UNMOUNTED".equals(action)){
            ToastUtil.showShort("说明sd卡 卸载了");

        }else if ("android.intent.action.MEDIA_MOUNTED".equals(action)) {

            ToastUtil.showShort("说明sd卡挂载了");
        }else if ("android.intent.action.MEDIA_REMOVED".equals(action)) {

            ToastUtil.showShort("说明sd卡被移除了");
        }else{
            ToastUtil.showShort("4.4以上已经不能发送SDcard广播了！");
        }
    }
}
