package com.geno.pm.pmms_sx.receiver;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import com.geno.pm.pmms_sx.activity.LoginActivity;
import com.geno.pm.pmms_sx.activity.MainActivity;
import com.geno.pm.pmms_sx.util.DatabaseHelper;
import com.geno.pm.pmms_sx.util.Util;

import cn.jpush.android.api.JPushInterface;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            if (LoginActivity.IsLogin) {
                Intent i = new Intent(context, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            } else {
                Intent i = new Intent(context, LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        }

        if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Bundle bundle = intent.getExtras();
            String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
            Log.i("JPush--------title", title);
            String message = bundle.getString(JPushInterface.EXTRA_ALERT);
            Log.i("JPush--------message", message);
            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);

            DatabaseHelper database = new DatabaseHelper(context);//这段代码放到Activity类中才用this
            SQLiteDatabase db = database.getWritableDatabase();
            @SuppressLint("Recycle")
            Cursor cursor = db.query("information", null, null, null, null, null, null);//查询并获得游标
            int count = cursor.getCount();
            cursor.moveToNext();
            int firstID = cursor.getInt(cursor.getColumnIndex("ID"));
            if (count < 10) {
                Util.insertData(db, extras);
            } else {
                Util.deleteFirstLine(db, count, firstID);
                Util.insertData(db, extras);
            }

            MainActivity.mInstance.setInformationIcon();
        }
    }


    public interface InformationIcon {
        void setInformationIcon();
    }
}


