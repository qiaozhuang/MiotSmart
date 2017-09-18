package com.miot.android.robot.host.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.miot.android.robot.host.service.RobotHostService;
import com.miot.android.robot.host.service.NetworkService;

/**
 * Created by xdf on 2016/7/27.
 */
public class  BootBrcastReceiver extends BroadcastReceiver {

    String action = "android.intent.action.BOOT_COMPLETED";


    @Override
    public void onReceive(Context context, Intent intent) {
        //接收广播：系统启动完成后运行程序
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED))
        {
            context.startService(new Intent(context, NetworkService.class));
            context.startService(new Intent(context, RobotHostService.class));
        }
    }


}
