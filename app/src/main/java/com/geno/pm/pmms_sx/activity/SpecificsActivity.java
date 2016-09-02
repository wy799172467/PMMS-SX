package com.geno.pm.pmms_sx.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.geno.pm.pmms_sx.Bean.Project;
import com.geno.pm.pmms_sx.R;
import com.geno.pm.pmms_sx.adapter.MyPagerAdapter;
import com.geno.pm.pmms_sx.adapter.MyRecyclerViewAdapter;
import com.geno.pm.pmms_sx.http.ApiService;
import com.geno.pm.pmms_sx.util.Util;

import java.util.ArrayList;
import java.util.List;

public class SpecificsActivity extends AppCompatActivity {

    private Project project;
    private View view1, view2;
    private List<View> mViewList = new ArrayList<>();//页卡视图集合
    private List<String> mTitleList = new ArrayList<>();//页卡标题集合

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.specifics);

        //获取数据
        Intent intent = getIntent();
        project = intent.getParcelableExtra("Data");

        initToolbar();//初始化导航栏

        //初始化页卡
        initTabLayout();

        initView1();//初始化View1

        initView2();//初始化View2
    }

    //初始化View1
    private void initView1() {
        RecyclerView recyclerView = (RecyclerView) view1.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(SpecificsActivity.this));//设置线性布局
        MyRecyclerViewAdapter myAdapter = new MyRecyclerViewAdapter(project);
        recyclerView.setAdapter(myAdapter);
    }

    //初始化View2
    private void initView2() {
        WebView webView = (WebView) view2.findViewById(R.id.webView);
        webView.loadUrl(ApiService.PROJECT_STATUS);
    }

    private void initTabLayout() {
        TabLayout mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        ViewPager mViewPager = (ViewPager) findViewById(R.id.view_pager);
        LayoutInflater mInflater = LayoutInflater.from(SpecificsActivity.this);
        view1 = mInflater.inflate(R.layout.specifics_tablayout_view1, null);
        view2 = mInflater.inflate(R.layout.specifics_tablayout_view2, null);
        //添加页卡视图
        mViewList.add(view1);
        mViewList.add(view2);
        //添加页卡标题
        mTitleList.add("基本信息");
        mTitleList.add("项目流程");
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);//设置tab模式，当前为系统默认模式
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(0)));//添加tab选项卡
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(1)));
        MyPagerAdapter mAdapter = new MyPagerAdapter(mViewList, mTitleList);
        mViewPager.setAdapter(mAdapter);//给ViewPager设置适配器
        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来。
        mTabLayout.setTabsFromPagerAdapter(mAdapter);//给Tabs设置适配器
    }

    //设置导航栏
    private void initToolbar() {
        //设置状态栏透明
        Util.setToolBar(this);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.specifics_toolbar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        TextView textview = (TextView) findViewById(R.id.title_text);
        textview.setText(project.getProjectName());
        mToolbar.setNavigationIcon(R.drawable.icon_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SpecificsActivity.this.finish();
            }
        });
    }
}
