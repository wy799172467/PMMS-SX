package com.geno.pm.pmms_sx.activity;

import android.annotation.SuppressLint;
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

import com.geno.pm.pmms_sx.Bean.Login;
import com.geno.pm.pmms_sx.R;
import com.geno.pm.pmms_sx.http.HttpQuery;

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

        HttpQuery.doLogin(username, password, new HttpQuery.LoginCallback() {

            @SuppressLint("WorldReadableFiles")
            @Override
            public void onLoginSuccess(Login login) {
                hideWaiting();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("Data", login.getData());
                startActivity(intent);
            }

            @Override
            public void onLoginFail(String message) {
                hideWaiting();
                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
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
}
