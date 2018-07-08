package example.com.aaaa.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.jaydenxiao.common.commonutils.ToastUtil;

/**
 * Created by asus-pc on 2017/11/27.
 */

public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ToastUtil.showShort("received in MyBroadcastReceiver");
    }
}
