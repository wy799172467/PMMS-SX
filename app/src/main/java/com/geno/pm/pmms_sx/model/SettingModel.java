package com.geno.pm.pmms_sx.model;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingModel implements ISettingModel {

    private static final String NAME = "姓名";
    private static final String USER_ACCOUNT = "账号";
    private static final String DEPARTMENT = "部门";
    private SharedPreferences mUserData;

    private static SettingModel mInstance = new SettingModel();

    private SettingModel() {
    }

    public static SettingModel getInstance() {
        return mInstance;
    }

    @Override
    public void init(Context context) {
        mUserData = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
    }

    @Override
    public String getUserAccountLabel() {
        return USER_ACCOUNT;
    }

    @Override
    public String getUserAccountValue() {
        return mUserData.getString("userAccount", "");
    }

    @Override
    public String getUserNameLabel() {
        return NAME;
    }

    @Override
    public String getUserNameValue() {
        return mUserData.getString("name", "");
    }

    @Override
    public String getDepartmentLabel() {
        return DEPARTMENT;
    }

    @Override
    public String getDepartmentValue() {
        return mUserData.getString("Department", "");
    }
}
