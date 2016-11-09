package com.geno.pm.pmms_sx.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
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
import com.geno.pm.pmms_sx.presenter.ILoginPresenter;
import com.geno.pm.pmms_sx.presenter.LoginPresenter;
import com.geno.pm.pmms_sx.util.Util;

import java.util.HashSet;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class LoginActivity extends AppCompatActivity implements ILoginView {

    @Bind(R.id.pb_loading)
    ProgressBar mProgressBar;
    @Bind(R.id.ckBox_remember)
    CheckBox mRememberMe;
    @Bind(R.id.btnLogin)
    Button mLoginButton;
    @Bind(R.id.ckBox_auto)
    CheckBox mAutoLogin;
    @Bind(R.id.etPassword)
    EditText mPasswordEditText;
    @Bind(R.id.etUser)
    EditText mUsernameEditText;

    private ILoginPresenter mLoginPresenter;
    /**
     * 模拟登陆标志
     */
    public static boolean isLogin = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("ldz", "------------------------------------------------------");
        Log.d("ldz", "-------------- LoginActivity onCreate ---------------");
        Log.d("ldz", "------------------------------------------------------");

        setContentView(R.layout.login);
        ButterKnife.bind(this);

        setHintText(mUsernameEditText, R.string.username); //设置hint
        setHintText(mPasswordEditText, R.string.password); //设置hint

        //设置布局文件上移
//        mUsernameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean hasFocus) {
//                RelativeLayout login_layout= (RelativeLayout) findViewById(R.id.login_layout);
//                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) login_layout.getLayoutParams();
//
//                if(hasFocus){
//                    lp.topMargin=-30;
//                    login_layout.requestLayout();
//                }else {
//                    lp.topMargin=0;
//                    login_layout.requestLayout();
//                }
//            }
//        });
//        mPasswordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean hasFocus) {
//                RelativeLayout login_layout= (RelativeLayout) findViewById(R.id.login_layout);
//                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) login_layout.getLayoutParams();
//                if(hasFocus){
//                    lp.topMargin=-30;
//                    login_layout.requestLayout();
//                }else {
//                    lp.topMargin=0;
//                    login_layout.requestLayout();
//                }
//            }
//        });
        mLoginPresenter = LoginPresenter.getInstance();
        mLoginPresenter.init(this);
        mLoginPresenter.initUsernameAndPassword();

        mLoginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mLoginPresenter.login();
            }
        });
        mAutoLogin.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    setRememberMe(true);
                }
            }
        });

        Util.setToolBarClear(LoginActivity.this);


    }

    //设置hint
    private void setHintText(EditText editText, int resources) {
        SpannableString ss = new SpannableString(getResources().getString(resources)); //定义hint的值
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(14, true); //设置字体大小 true表示单位是sp
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //设置字体大小
        //noinspection deprecation
        ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.hint_bg)), 0, ss.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //设置字体颜色
        editText.setHint(new SpannedString(ss));
    }

    @Override
    public String getUsername() {
        return mUsernameEditText.getText().toString();
    }

    @Override
    public String getPassword() {
        return mPasswordEditText.getText().toString();
    }

    @Override
    public void showWaiting() {
        mProgressBar.setVisibility(View.VISIBLE);
        mLoginButton.setText(R.string.doing_login);
        mLoginButton.setClickable(false);
    }

    @Override
    public void hideWaiting() {
        mProgressBar.setVisibility(View.GONE);
        mLoginButton.setText(R.string.login);
        mLoginButton.setClickable(true);
    }

    @Override
    public void toToast(int rsID) {
        Toast.makeText(LoginActivity.this, rsID, Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void loginSuccess(Login login) {

        isLogin = true; //监听登陆状态
        setPush(); //设置推送
        setAliasAndTags(login); //设置推送标签

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void loginFailed(String message) {
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setUsername(String username) {
        mUsernameEditText.setText(username);
    }

    @Override
    public void setPassword(String password) {
        mPasswordEditText.setText(password);
    }

    @Override
    public void setRememberMe(boolean flag) {
        mRememberMe.setChecked(flag);
    }

    @Override
    public void setAutoLogin(boolean flag) {
        mAutoLogin.setChecked(flag);
    }

    @Override
    public boolean isRememberMe() {
        return mRememberMe.isChecked();
    }

    @Override
    public boolean isAutoLogin() {
        return mAutoLogin.isChecked();
    }

    //设置推送
    private void setPush() {
        JPushInterface.setDebugMode(true);
        JPushInterface.init(LoginActivity.this);
        if (JPushInterface.isPushStopped(LoginActivity.this)) {
            JPushInterface.resumePush(LoginActivity.this);
        }
    }

    //设置标签别名
    private void setAliasAndTags(Login login) {
        Set<String> label = new HashSet<>(); //添加标签
        label.add(login.getData().getDepartment());
        JPushInterface.setAliasAndTags(LoginActivity.this, null, label, new TagAliasCallback() {
            @Override
            public void gotResult(int arg0, String s, Set<String> set) {
                Log.i("JPush", "Jpush status: " + arg0); //状态  为 0 时标示成功
                if (arg0 == 0) {
                    Toast.makeText(LoginActivity.this, "推送身份验证成功", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(LoginActivity.this, "推送身份验证失败", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
