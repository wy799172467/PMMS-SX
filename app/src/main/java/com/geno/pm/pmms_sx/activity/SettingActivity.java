package com.geno.pm.pmms_sx.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.geno.pm.pmms_sx.R;
import com.geno.pm.pmms_sx.util.Util;

public class SettingActivity extends AppCompatActivity {

    private LayoutInflater inflater;
    private static final String NAME="姓名";
    private static final String USER_ACCOUNT="账号";
    private static final String DEPARTMENT="部门";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_layout);

        inflater = LayoutInflater.from(SettingActivity.this);

        //获取上个activity的数据
        SharedPreferences userData = getSharedPreferences("UserData", Context.MODE_PRIVATE);

        initToolbar();//设置导航栏
        initUserInfo(userData);//初始化用户信息
        initCheck();//初始化待审核标签
        initQuit();//设置系统退出登录
    }

    private void initUserInfo(SharedPreferences userData) {
        /*ListView listView = (ListView) findViewById(R.id.setting_listView);
        SettingListViewAdapter settingListViewAdapter = new SettingListViewAdapter(SettingActivity.this, data);
        listView.setAdapter(settingListViewAdapter);*/
        @SuppressLint("InflateParams")
        View view1 = inflater.inflate(R.layout.setting_listview_item, null);
        TextView key1 = (TextView) view1.findViewById(R.id.setting_listView_key);
        TextView value1 = (TextView) view1.findViewById(R.id.setting_listView_value);
        key1.setText(USER_ACCOUNT);
        value1.setText(userData.getString("userAccount",""));
        LinearLayout linear1= (LinearLayout) findViewById(R.id.userAccount);
        linear1.addView(view1);
        @SuppressLint("InflateParams")
        View view2 = inflater.inflate(R.layout.setting_listview_item, null);
        TextView key2 = (TextView) view2.findViewById(R.id.setting_listView_key);
        TextView value2 = (TextView) view2.findViewById(R.id.setting_listView_value);
        key2.setText(NAME);
        value2.setText(userData.getString("name",""));
        LinearLayout linear2= (LinearLayout) findViewById(R.id.name);
        linear2.addView(view2);
        @SuppressLint("InflateParams")
        View view3 = inflater.inflate(R.layout.setting_listview_item, null);
        TextView key3 = (TextView) view3.findViewById(R.id.setting_listView_key);
        TextView value3 = (TextView) view3.findViewById(R.id.setting_listView_value);
        key3.setText(DEPARTMENT);
        value3.setText(userData.getString("Department",""));
        LinearLayout linear3= (LinearLayout) findViewById(R.id.Department);
        linear3.addView(view3);
    }

    //设置系统退出登录
    private void initQuit() {
        TextView textView = (TextView) findViewById(R.id.setting_quit_textView);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(SettingActivity.this, LoginActivity.class);
                login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                login.addCategory(Intent.CATEGORY_HOME);
                startActivity(login);
                finish();
            }
        });
    }

    //初始化待审核标签
    private void initCheck() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.setting_project_linearLayout);
        @SuppressLint("InflateParams")
        View view = inflater.inflate(R.layout.setting_layout_linearlayout, null);
        linearLayout.addView(view);
    }

    //设置导航栏
    private void initToolbar() {
        //设置状态栏透明
        Util.setToolBarClear(this);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.specifics_toolbar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        TextView textview = (TextView) findViewById(R.id.title_text);
        textview.setText("我的设置");
        mToolbar.setNavigationIcon(R.drawable.icon_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingActivity.this.finish();
            }
        });
    }
}
