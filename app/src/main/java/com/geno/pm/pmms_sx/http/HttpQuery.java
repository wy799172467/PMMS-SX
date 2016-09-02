package com.geno.pm.pmms_sx.http;

import com.geno.pm.pmms_sx.Bean.Login;
import com.geno.pm.pmms_sx.Bean.LogStatus;
import com.geno.pm.pmms_sx.Bean.Project;
import com.geno.pm.pmms_sx.util.MD5;
import com.geno.pm.pmms_sx.util.Util;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import retrofit.HttpException;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HttpQuery {

    //登录逻辑
    public static void doLogin(final String username, final String password,
                               final LoginCallback callback) {

        String md5Password = MD5.stringMD5(password).toLowerCase();
        rx.Observable<Login> observable = Util.getInstance().login(username, md5Password);
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
                                callback.onLoginFail(message);
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onNext(Login login) {
                        //获取登录成功时的返回信息
//                        LogStatus logStatus=login.getStatus();
//                        String code=logStatus.getCode();
//                        String message = logStatus.getMessage();
                        callback.onLoginSuccess(login);
                    }

                });

    }

    //登录反馈结果
    public interface LoginCallback {

        void onLoginSuccess(Login login);

        void onLoginFail(String message);
    }

    public static void getAllProject(final GetAllProjectCallback callback) {
        Observable<List<Project>> allProject = Util.getInstance().getAllProject();
        allProject.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Project>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        String message = e.getMessage();
//                        Log.i("ERORR---------",message);
                        callback.onGetAllProjectFail(message);
                    }

                    @Override
                    public void onNext(List<Project> projects) {
                        callback.onGetAllProjectSuccess(projects);
                    }
                });
    }

    //登录反馈结果
    public interface GetAllProjectCallback {

        void onGetAllProjectSuccess(List<Project> projects);

        void onGetAllProjectFail(String message);
    }


}
