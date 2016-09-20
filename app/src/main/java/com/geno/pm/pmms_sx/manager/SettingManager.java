package com.geno.pm.pmms_sx.manager;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingManager {

    private Context mContext;

    private SharedPreferences userData;


    private static SettingManager sInstance = new SettingManager();

    private SettingManager(){
    }

    public static SettingManager getInstance() {
        return sInstance;
    }

    public void init(Context context){
        mContext=context;
        userData=mContext.getSharedPreferences("UserData", Context.MODE_PRIVATE);
    }

    public SharedPreferences getUserData(){
        return userData;
    }

}
