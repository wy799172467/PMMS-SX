package com.geno.pm.pmms_sx.util;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.geno.pm.pmms_sx.http.ApiService;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *
 */
public final class Util {

    private static ApiService sInstance = null;

    private Util() {

    }

    public static ApiService getInstance() {
        if (sInstance == null) {
            Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiService.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                    .client(sOkHttpClient) // open this if using HTTPS with self-signed certificate
                    .build();

            sInstance = retrofit.create(ApiService.class);
        }
        return sInstance;
    }

    /*设置Toolbar透明*/
    public static void setToolBarClear(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0 全透明实现
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        // 方法2：此方法只能检测是否连接网络不能确定网络是否可用
        if (cm != null) {
            NetworkInfo info = cm.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void insertData(SQLiteDatabase db, String extras) {
        JSONObject project;
        try {
            project = new JSONObject(extras);
            String projectNo = project.getString("ProjectNo");
            String projectName = project.getString("ProjectName");
            //                String projectDetail = project.getString("ProjectDetail");

            ContentValues cv = new ContentValues();//实例化一个ContentValues用来装载待插入的数据
            cv.put("ProjectNo", projectNo); //添加项目ID
            cv.put("ProjectName", projectName); //添加项目名称
//            cv.put("ProjectDetail",projectDetail); //添加项目细节
            cv.put("ProjectDetail", "细节"); //添加项目细节
            db.insert("information", null, cv);//执行插入操作
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static void deleteFirstLine(SQLiteDatabase db, int count, int firstID) {
        /*String whereClause = "username=?";//删除的条件
        String[] whereArgs = {"Jack Johnson"};//删除的条件参数
        db.delete("user",whereClause,whereArgs);//执行删除*/
        for (int i = firstID; i <= firstID + count - 10; i++) {
            String sql = "delete from information where ID=" + i;//删除操作的SQL语句
            db.execSQL(sql);//执行删除操作
        }
    }
}
