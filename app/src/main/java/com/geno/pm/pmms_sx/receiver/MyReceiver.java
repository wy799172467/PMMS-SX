package com.geno.pm.pmms_sx.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.geno.pm.pmms_sx.activity.LoginActivity;
import com.geno.pm.pmms_sx.activity.SpecificsActivity;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            if (LoginActivity.IsLogin) {
                JSONObject project;
                try {
                    Bundle bundle = intent.getExtras();
                    String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
                    Log.i("JPush--------title", title);
                    String message = bundle.getString(JPushInterface.EXTRA_ALERT);
                    Log.i("JPush--------message", message);
                    String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
                    project = new JSONObject(extras);
                    String projectNo = project.getString("ProjectNo");
                    String projectName = project.getString("ProjectName");
                    Intent i = new Intent(context, SpecificsActivity.class);
                    i.putExtra("ProjectNo", projectNo);
                    i.putExtra("ProjectName", projectName);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "推送数据有误", Toast.LENGTH_LONG).show();
                }
            } else {
                Intent i = new Intent(context, LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        }
    }
}
