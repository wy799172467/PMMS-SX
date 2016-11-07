package com.geno.pm.pmms_sx.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.geno.pm.pmms_sx.R;
import com.geno.pm.pmms_sx.presenter.ISettingPresenter;
import com.geno.pm.pmms_sx.presenter.SettingPresenter;
import com.geno.pm.pmms_sx.util.Util;

import cn.jpush.android.api.JPushInterface;

public class SettingActivity extends AppCompatActivity implements ISettingView{

    private LayoutInflater inflater;
    private TextView key1;
    private TextView key2;
    private TextView key3;
    private TextView value1;
    private TextView value2;
    private TextView value3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_layout);

        inflater = LayoutInflater.from(SettingActivity.this);

        ISettingPresenter mISettingPresenter = SettingPresenter.getInstance();
        mISettingPresenter.init(this);
        mISettingPresenter.setUserInfo();//初始化用户信息
        initToolbar();//设置导航栏
        initCheck();//初始化待审核标签
        initQuit();//设置系统退出登录
    }

    @Override
    public void initUserInfo() {
        /*ListView listView = (ListView) findViewById(R.id.setting_listView);
        SettingListViewAdapter settingListViewAdapter = new SettingListViewAdapter(SettingActivity.this, data);
        listView.setAdapter(settingListViewAdapter);*/
        @SuppressLint("InflateParams")
        View view1 = inflater.inflate(R.layout.setting_person_list_item, null);
        key1 = (TextView) view1.findViewById(R.id.setting_person_list_key);
        value1 = (TextView) view1.findViewById(R.id.setting_person_list_value);
        LinearLayout linear1 = (LinearLayout) findViewById(R.id.userAccount);
        linear1.addView(view1);
        @SuppressLint("InflateParams")
        View view2 = inflater.inflate(R.layout.setting_person_list_item, null);
        key2 = (TextView) view2.findViewById(R.id.setting_person_list_key);
        value2 = (TextView) view2.findViewById(R.id.setting_person_list_value);
        LinearLayout linear2 = (LinearLayout) findViewById(R.id.name);
        linear2.addView(view2);
        @SuppressLint("InflateParams")
        View view3 = inflater.inflate(R.layout.setting_person_list_item, null);
        key3 = (TextView) view3.findViewById(R.id.setting_person_list_key);
        value3 = (TextView) view3.findViewById(R.id.setting_person_list_value);
        LinearLayout linear3 = (LinearLayout) findViewById(R.id.Department);
        linear3.addView(view3);
    }

    @Override
    public void setDepartmentValue(String departmentValue) {
        value3.setText(departmentValue);
    }

    @Override
    public void setDepartmentLabel(String departmentLabel) {
        key3.setText(departmentLabel);
    }

    @Override
    public void setUserNameValue(String userNameValue) {
        value2.setText(userNameValue);
    }

    @Override
    public void setUserNameLabel(String userNameLabel) {
        key2.setText(userNameLabel);
    }

    @Override
    public void setUserAccountValue(String userAccountValue) {
        value1.setText(userAccountValue);
    }

    @Override
    public void setUserAccountLabel(String userAccountLabel) {
        key1.setText(userAccountLabel);
    }

    //设置系统退出登录
    private void initQuit() {
        TextView textView = (TextView) findViewById(R.id.setting_quit_textView);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity.IsLogin = false;
                Intent login = new Intent(SettingActivity.this, LoginActivity.class);
                login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                login.addCategory(Intent.CATEGORY_HOME);
                startActivity(login);
                MainActivity.mInstance.finish();
                finish();
                JPushInterface.stopPush(SettingActivity.this);
            }
        });
    }

    //初始化待审核标签
    private void initCheck() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.setting_project_linearLayout);
        @SuppressLint("InflateParams")
        View view = inflater.inflate(R.layout.setting_layout_check, null);
        linearLayout.addView(view);
    }

    //设置导航栏
    private void initToolbar() {
        //设置状态栏透明
        Util.setToolBarClear(this);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.setting_toolbar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
//        TextView textView = (TextView) findViewById(R.id.title_text);
//        textView.setText("我的设置");
//        mToolbar.setNavigationIcon(R.drawable.icon_back);
//        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                SettingActivity.this.finish();
//            }
//        });
        ImageView imageView = (ImageView) findViewById(R.id.setting_toolbar_image);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingActivity.this.finish();
            }
        });
    }
}
