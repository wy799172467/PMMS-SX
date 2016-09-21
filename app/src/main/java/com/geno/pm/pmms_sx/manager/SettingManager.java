package com.geno.pm.pmms_sx.manager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class SettingManager {

    private SharedPreferences userData;


    @SuppressLint("StaticFieldLeak")
    private static SettingManager sInstance = new SettingManager();

    private SettingManager() {
    }

    public static SettingManager getInstance() {
        return sInstance;
    }

    public void init(Context context) {
        userData = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
    }

    /*public SharedPreferences getUserData(){
        return userData;
    }*/

    public String getUserAccount() {
        return userData.getString("userAccount", "");
    }

    public String getName() {
        return userData.getString("name", "");
    }

    public String getDepartment() {
        return userData.getString("Department", "");
    }
}
