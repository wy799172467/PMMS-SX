package com.geno.pm.pmms_sx.model;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.geno.pm.pmms_sx.AllData.LoginToMain;
import com.geno.pm.pmms_sx.Bean.Filter_Project;
import com.geno.pm.pmms_sx.Bean.Filter_Status;
import com.geno.pm.pmms_sx.Bean.Filter_Year;
import com.geno.pm.pmms_sx.Bean.LogStatus;
import com.geno.pm.pmms_sx.Bean.Login;
import com.geno.pm.pmms_sx.util.MD5;
import com.geno.pm.pmms_sx.util.Util;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class LoginModel implements ILoginModel {

    private SharedPreferences mDefaultPrefs;
    private SharedPreferences mUserData;

    private static LoginModel mInstance = new LoginModel();

    private LoginModel() {

    }

    public static LoginModel getInstance() {
        return mInstance;
    }

    @Override
    public void init(Context context) {
        mDefaultPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        mUserData = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
    }

    @Override
    public void loginAction(String username, String password, final LoginCallBack callBack) {
        //noinspection ConstantConditions
        String md5Password = MD5.stringMD5(password).toLowerCase();
        Observable<Login> observable = Util.getInstance().login(username, md5Password);
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Login>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            String errorBody;
                            try {
                                errorBody = ((HttpException) e).response().errorBody().string();
                                Gson gson = new Gson();
                                Login login = gson.fromJson(errorBody, Login.class);
                                LogStatus logStatus = login.getStatus();
//                                String code=logStatus.getCode();
                                String message = logStatus.getMessage();

                                callBack.onLoginFailed(message);
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onNext(Login login) {

                        callBack.onLoginSuccess(login);
                    }
                });
    }

    @Override
    public String getUsername() {
        return mDefaultPrefs.getString("user_id", "");
    }

    @Override
    public String getPassword() {
        return mDefaultPrefs.getString("password", "");
    }

    @Override
    public boolean getAuto() {
        return mDefaultPrefs.getBoolean("auto", false);
    }

    @Override
    public void saveUsername(String username) {
        mDefaultPrefs.edit().putString("user_id", username).apply();
    }

    @Override
    public void savePassword(String password) {
        mDefaultPrefs.edit().putString("password", password).apply();
    }

    @Override
    public void saveAuto(boolean flag) {
        mDefaultPrefs.edit().putBoolean("auto", flag).apply();
    }

    @Override
    public void saveName(Login login) {
        mUserData.edit().putString("name", login.getData().getName()).apply();
    }

    @Override
    public void saveUserAccount(Login login) {
        mUserData.edit().putString("userAccount", login.getData().getUserAccount()).apply();
    }

    @Override
    public void saveDepartment(Login login) {
        mUserData.edit().putString("Department", login.getData().getDepartment()).apply();
    }

    @Override
    public void setFilterYear(Context context, Login login) {
        SharedPreferences filterYear = context.getSharedPreferences("filter_year", Context.MODE_PRIVATE); //私有数据
        List<Filter_Year> years = login.getFilter().getYear();
        String[] year = new String[years.size()];
        for (int i = 0; i < years.size(); i++) {
            filterYear.edit().putString(years.get(i).getKey(), years.get(i).getValue()).apply();
            year[i] = years.get(i).getKey();
        }
        LoginToMain.mYears = year;
    }

    @Override
    public void setFilterStatus(Context context, Login login) {
        SharedPreferences filterStatus = context.getSharedPreferences("filter_status", Context.MODE_PRIVATE);
        List<Filter_Status> status = login.getFilter().getStatus();
        String[] sta = new String[status.size()];
        for (int i = 0; i < status.size(); i++) {
            filterStatus.edit().putString(status.get(i).getKey(), "" + status.get(i).getValue()).apply();
            sta[i] = status.get(i).getKey();
        }
        LoginToMain.mStatus = sta;
    }

    @Override
    public void setFilterType(Context context, Login login) {
        SharedPreferences filterProject = context.getSharedPreferences("filter_type", Context.MODE_PRIVATE);
        List<Filter_Project> projects = login.getFilter().getProject_type();
        String[] project = new String[projects.size()];
        for (int i = 0; i < projects.size(); i++) {
            filterProject.edit().putString(projects.get(i).getKey(), projects.get(i).getValue()).apply();
            project[i] = projects.get(i).getKey();
        }
        LoginToMain.mProjects = project;
    }

    @Override
    public String[] getFilterYear() {
        return LoginToMain.mYears;
    }

    @Override
    public String[] getFilterStatus() {
        return LoginToMain.mStatus;
    }

    @Override
    public String[] getFilterProject() {
        return LoginToMain.mProjects;
    }


}
