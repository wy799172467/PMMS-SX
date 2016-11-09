package com.geno.pm.pmms_sx.model;

import android.content.Context;

import com.geno.pm.pmms_sx.Bean.Login;

public interface ILoginModel {

    void init(Context context);

    void loginAction(String username, String password, LoginCallBack callBack);


    String getUsername();
    String getPassword();
    boolean getAuto();
    void saveUsername(String username);
    void savePassword(String password);
    void saveAuto(boolean flag);
    void saveName(Login login);
    void saveUserAccount(Login login);
    void saveDepartment(Login login);
    void setFilterYear(Context context, Login login);
    void setFilterStatus(Context context, Login login);
    void setFilterType(Context context, Login login);
    String[] getFilterYear();
    String[] getFilterStatus();
    String[] getFilterProject();


    interface LoginCallBack {
        void onLoginSuccess(Login login);

        void onLoginFailed(String message);
    }

}
