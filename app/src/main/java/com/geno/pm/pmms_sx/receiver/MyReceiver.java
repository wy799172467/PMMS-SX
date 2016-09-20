package com.geno.pm.pmms_sx.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.geno.pm.pmms_sx.activity.LoginActivity;
import com.geno.pm.pmms_sx.activity.MainActivity;

import cn.jpush.android.api.JPushInterface;

public class MyReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        if(JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())){
            if(LoginActivity.IsLogin){
                Intent i=new Intent(context, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }else {
                Intent i=new Intent(context, LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        }
    }
}
