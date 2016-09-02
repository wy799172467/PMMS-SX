package com.geno.pm.pmms_sx.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.geno.pm.pmms_sx.Bean.Data;
import com.geno.pm.pmms_sx.R;
import com.geno.pm.pmms_sx.adapter.SettingListViewAdapter;
import com.geno.pm.pmms_sx.util.Util;

public class SettingActivity extends AppCompatActivity {

    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_layout);

        inflater = LayoutInflater.from(SettingActivity.this);

        //获取上个activity的数据
        Intent intent = getIntent();
        Data data = intent.getParcelableExtra("Data");

        initToolbar();//设置导航栏

        ListView listView = (ListView) findViewById(R.id.setting_listView);
        SettingListViewAdapter settingListViewAdapter = new SettingListViewAdapter(SettingActivity.this, data);
        listView.setAdapter(settingListViewAdapter);
        initCheck();//初始化待审核标签

        initQuit();//设置系统退出登录
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
        View view = inflater.inflate(R.layout.setting_layout_linearlayout, null);
        linearLayout.addView(view);
    }

    //设置导航栏
    private void initToolbar() {
        //设置状态栏透明
        Util.setToolBar(this);

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
