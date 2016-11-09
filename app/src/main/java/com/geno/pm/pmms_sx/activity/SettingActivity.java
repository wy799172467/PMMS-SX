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

public class SettingActivity extends AppCompatActivity implements ISettingView {

    private LayoutInflater mInflater;
    private TextView mKey1;
    private TextView mKey2;
    private TextView mKey3;
    private TextView mValue1;
    private TextView mValue2;
    private TextView mValue3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_layout);

        mInflater = LayoutInflater.from(SettingActivity.this);

        ISettingPresenter mISettingPresenter = SettingPresenter.getInstance();
        mISettingPresenter.init(this);
        mISettingPresenter.setUserInfo(); //初始化用户信息
        initToolbar(); //设置导航栏
        initCheck(); //初始化待审核标签
        initQuit(); //设置系统退出登录
    }

    @Override
    public void initUserInfo() {
        /*ListView listView = (ListView) findViewById(R.id.setting_listView);
        SettingListViewAdapter settingListViewAdapter = new SettingListViewAdapter(SettingActivity.this, data);
        listView.setAdapter(settingListViewAdapter);*/
        @SuppressLint("InflateParams")
        View view1 = mInflater.inflate(R.layout.setting_person_list_item, null);
        mKey1 = (TextView) view1.findViewById(R.id.setting_person_list_key);
        mValue1 = (TextView) view1.findViewById(R.id.setting_person_list_value);
        LinearLayout linear1 = (LinearLayout) findViewById(R.id.userAccount);
        linear1.addView(view1);
        @SuppressLint("InflateParams")
        View view2 = mInflater.inflate(R.layout.setting_person_list_item, null);
        mKey2 = (TextView) view2.findViewById(R.id.setting_person_list_key);
        mValue2 = (TextView) view2.findViewById(R.id.setting_person_list_value);
        LinearLayout linear2 = (LinearLayout) findViewById(R.id.name);
        linear2.addView(view2);
        @SuppressLint("InflateParams")
        View view3 = mInflater.inflate(R.layout.setting_person_list_item, null);
        mKey3 = (TextView) view3.findViewById(R.id.setting_person_list_key);
        mValue3 = (TextView) view3.findViewById(R.id.setting_person_list_value);
        LinearLayout linear3 = (LinearLayout) findViewById(R.id.Department);
        linear3.addView(view3);
    }

    @Override
    public void setDepartmentValue(String departmentValue) {
        mValue3.setText(departmentValue);
    }

    @Override
    public void setDepartmentLabel(String departmentLabel) {
        mKey3.setText(departmentLabel);
    }

    @Override
    public void setUserNameValue(String userNameValue) {
        mValue2.setText(userNameValue);
    }

    @Override
    public void setUserNameLabel(String userNameLabel) {
        mKey2.setText(userNameLabel);
    }

    @Override
    public void setUserAccountValue(String userAccountValue) {
        mValue1.setText(userAccountValue);
    }

    @Override
    public void setUserAccountLabel(String userAccountLabel) {
        mKey1.setText(userAccountLabel);
    }

    //设置系统退出登录
    private void initQuit() {
        TextView textView = (TextView) findViewById(R.id.setting_quit_textView);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity.isLogin = false;
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
        View view = mInflater.inflate(R.layout.setting_layout_check, null);
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
