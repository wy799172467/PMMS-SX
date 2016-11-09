package com.geno.pm.pmms_sx.activity;


import com.geno.pm.pmms_sx.Bean.Login;

public interface ILoginView {

    //登录
    String getUsername();

    String getPassword();

    void showWaiting();

    void hideWaiting();

    void toToast(int rsID); //检查填写内容

    void loginSuccess(Login login);

    void loginFailed(String message);

    boolean isRememberMe();

    boolean isAutoLogin();

    //记住我
    void setUsername(String username);

    void setPassword(String password);

    void setRememberMe(boolean flag);

    void setAutoLogin(boolean flag);

}
