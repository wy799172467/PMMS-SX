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
import com.geno.pm.pmms_sx.manager.SettingManager;
import com.geno.pm.pmms_sx.util.Util;

public class SettingActivity extends AppCompatActivity {

    private LayoutInflater inflater;
    private static final String NAME="姓名";
    private static final String USER_ACCOUNT="账号";
    private static final String DEPARTMENT="部门";
    private SettingManager settingManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_layout);

        inflater = LayoutInflater.from(SettingActivity.this);

        //获取上个activity的数据
        settingManager=SettingManager.getInstance();
        settingManager.init(this);

        initToolbar();//设置导航栏
        initUserInfo();//初始化用户信息
        initCheck();//初始化待审核标签
        initQuit();//设置系统退出登录
    }

    private void initUserInfo() {
        /*ListView listView = (ListView) findViewById(R.id.setting_listView);
        SettingListViewAdapter settingListViewAdapter = new SettingListViewAdapter(SettingActivity.this, data);
        listView.setAdapter(settingListViewAdapter);*/
        @SuppressLint("InflateParams")
        View view1 = inflater.inflate(R.layout.setting_person_list_item, null);
        TextView key1 = (TextView) view1.findViewById(R.id.setting_person_list_key);
        TextView value1 = (TextView) view1.findViewById(R.id.setting_person_list_value);
        key1.setText(USER_ACCOUNT);
        value1.setText(settingManager.getUserAccount());
        LinearLayout linear1= (LinearLayout) findViewById(R.id.userAccount);
        linear1.addView(view1);
        @SuppressLint("InflateParams")
        View view2 = inflater.inflate(R.layout.setting_person_list_item, null);
        TextView key2 = (TextView) view2.findViewById(R.id.setting_person_list_key);
        TextView value2 = (TextView) view2.findViewById(R.id.setting_person_list_value);
        key2.setText(NAME);
        value2.setText(settingManager.getName());
        LinearLayout linear2= (LinearLayout) findViewById(R.id.name);
        linear2.addView(view2);
        @SuppressLint("InflateParams")
        View view3 = inflater.inflate(R.layout.setting_person_list_item, null);
        TextView key3 = (TextView) view3.findViewById(R.id.setting_person_list_key);
        TextView value3 = (TextView) view3.findViewById(R.id.setting_person_list_value);
        key3.setText(DEPARTMENT);
        value3.setText(settingManager.getDepartment());
        LinearLayout linear3= (LinearLayout) findViewById(R.id.Department);
        linear3.addView(view3);
    }

    //设置系统退出登录
    private void initQuit() {
        TextView textView = (TextView) findViewById(R.id.setting_quit_textView);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity.IsLogin=false;
                Intent login = new Intent(SettingActivity.this, LoginActivity.class);
                login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                login.addCategory(Intent.CATEGORY_HOME);
                startActivity(login);
                MainActivity.mInstance.finish();
                finish();
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
//        TextView textview = (TextView) findViewById(R.id.title_text);
//        textview.setText("我的设置");
//        mToolbar.setNavigationIcon(R.drawable.icon_back);
//        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                SettingActivity.this.finish();
//            }
//        });
        ImageView imageView= (ImageView) findViewById(R.id.setting_toolbar_image);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingActivity.this.finish();
            }
        });
    }
}
