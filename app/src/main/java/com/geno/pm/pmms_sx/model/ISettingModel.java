package com.geno.pm.pmms_sx.model;

import android.content.Context;

public interface ISettingModel {

    void init(Context context);

    //UserInfo
    String getUserAccountLabel();
    String getUserAccountValue();
    String getUserNameLabel();
    String getUserNameValue();
    String getDepartmentLabel();
    String getDepartmentValue();
}
