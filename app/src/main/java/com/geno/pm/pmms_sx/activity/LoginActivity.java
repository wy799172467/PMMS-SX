package com.geno.pm.pmms_sx.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.geno.pm.pmms_sx.Bean.Filter_Project;
import com.geno.pm.pmms_sx.Bean.Filter_Status;
import com.geno.pm.pmms_sx.Bean.Filter_Year;
import com.geno.pm.pmms_sx.Bean.LogStatus;
import com.geno.pm.pmms_sx.Bean.Login;
import com.geno.pm.pmms_sx.R;
import com.geno.pm.pmms_sx.util.MD5;
import com.geno.pm.pmms_sx.util.Util;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity implements OnClickListener,
        OnCheckedChangeListener {

    private Button mLoginButton;
    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private ProgressBar mProgressBar;
    private CheckBox mRememberMe;
    private CheckBox mAutoLogin;

    private String mUserId;
    private String mPassword;
    private String[] YEAR;
    private String[] STATUS;
    private String[] TYPE;

    private SharedPreferences mDefaultPrefs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("ldz", "------------------------------------------------------");
        Log.d("ldz", "-------------- LoginActivity onCreate ---------------");
        Log.d("ldz", "------------------------------------------------------");

        setContentView(R.layout.login);

        mUsernameEditText = (EditText) findViewById(R.id.etUser);
        mPasswordEditText = (EditText) findViewById(R.id.etPassword);
        mProgressBar = (ProgressBar) findViewById(R.id.pb_loading);
        mLoginButton = (Button) findViewById(R.id.btnLogin);
        mLoginButton.setOnClickListener(this);
        mRememberMe = (CheckBox) findViewById(R.id.ckBox_remember);
        mAutoLogin = (CheckBox) findViewById(R.id.ckBox_auto);
        mAutoLogin.setOnCheckedChangeListener(this);

        mDefaultPrefs = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
        mUserId = mDefaultPrefs.getString("user_id", "");

        if (!mUserId.equals("")) {
            mPassword = mDefaultPrefs.getString("password", "");

            mUsernameEditText.setText(mUserId);
            mPasswordEditText.setText(mPassword);
            mRememberMe.setChecked(true);

            if (mDefaultPrefs.getBoolean("auto", false)) {
                mAutoLogin.setChecked(true);
            }
        }

        Util.setToolBarClear(LoginActivity.this);
    }

    private void verifyUser(final String username, final String password) {

        if (mRememberMe.isChecked()) {
            mDefaultPrefs.edit().putString("user_id", username).putString("password", password).apply();
        } else {
            mDefaultPrefs.edit().putString("user_id", "").putString("password", "").apply();
        }
        if (mAutoLogin.isChecked()) {
            mDefaultPrefs.edit().putBoolean("auto", true).apply();
        } else {
            mDefaultPrefs.edit().putBoolean("auto", false).apply();
        }

        showWaiting();

        doLogin(username, password);
    }

    private void showWaiting() {
        mProgressBar.setVisibility(View.VISIBLE);
        mLoginButton.setText(R.string.doing_login);
    }

    private void hideWaiting() {
        mProgressBar.setVisibility(View.GONE);
        mLoginButton.setText(R.string.login);
    }

    @Override
    public void onClick(View v) {
        mUserId = mUsernameEditText.getText().toString();
        mPassword = mPasswordEditText.getText().toString();
        verifyUser(mUserId, mPassword);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            mRememberMe.setChecked(true);
        }
    }

    public void doLogin(final String username, final String password) {

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
                                hideWaiting();
                                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onNext(Login login) {
                        //获取登录成功时的返回信息
                        hideWaiting();

                        setData(login);

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                        intent.putExtra("Data", login.getData());
                        intent.putExtra("YEAR",YEAR);
                        intent.putExtra("STATUS",STATUS);
                        intent.putExtra("TYPE",TYPE);
                        startActivity(intent);
                    }
                });
    }

    //存储数据
    private void setData(Login login) {
        SharedPreferences userData = getSharedPreferences("UserData", Context.MODE_PRIVATE); //私有数据
        userData.edit().putString("name", login.getData().getName()).
                putString("userAccount", login.getData().getUserAccount()).
                putString("Department", login.getData().getDepartment()).apply();

        SharedPreferences filter_year = getSharedPreferences("filter_year", Context.MODE_PRIVATE); //私有数据
        List<Filter_Year> years = login.getFilter().getYear();
        String[] year=new String[years.size()];
        for (int i = 0; i < years.size(); i++) {
            filter_year.edit().putString(years.get(i).getKey(), years.get(i).getValue()).apply();
            year[i]=years.get(i).getKey();
        }

        SharedPreferences filter_status = getSharedPreferences("filter_status", Context.MODE_PRIVATE); //私有数据
        List<Filter_Status> status = login.getFilter().getStatus();
        String[] sta=new String[status.size()];
        for (int i = 0; i < status.size(); i++) {
            filter_status.edit().putString(status.get(i).getKey(), ""+status.get(i).getValue()).apply();
            sta[i]=status.get(i).getKey();
        }

        SharedPreferences filter_project = getSharedPreferences("filter_type", Context.MODE_PRIVATE); //私有数据
        List<Filter_Project> projects = login.getFilter().getProject_type();
        String[] project=new String[projects.size()];
        for (int i = 0; i < projects.size(); i++) {
            filter_project.edit().putString(projects.get(i).getKey(), projects.get(i).getValue()).apply();
            project[i]=projects.get(i).getKey();
        }

        YEAR=year;
        STATUS=sta;
        TYPE=project;
    }
}
