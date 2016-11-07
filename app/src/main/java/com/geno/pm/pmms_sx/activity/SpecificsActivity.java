package com.geno.pm.pmms_sx.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.geno.pm.pmms_sx.Bean.Project_Detail;
import com.geno.pm.pmms_sx.R;
import com.geno.pm.pmms_sx.adapter.MyPagerAdapter;
import com.geno.pm.pmms_sx.adapter.MyRecyclerViewAdapter;
import com.geno.pm.pmms_sx.presenter.ISpecificsPresenter;
import com.geno.pm.pmms_sx.presenter.SpecificsPresenter;
import com.geno.pm.pmms_sx.util.Util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SpecificsActivity extends AppCompatActivity implements ISpecificsView {

    private View mView1, mView2;
    private List<View> mViewList = new ArrayList<>();//页卡视图集合
    private List<String> mTitleList = new ArrayList<>();//页卡标题集合

    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private WebView mWebView;
    private ProgressDialog dialog;

    @SuppressLint("InflateParams")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.specifics);

        LayoutInflater mInflater = LayoutInflater.from(SpecificsActivity.this);
        mView1 = mInflater.inflate(R.layout.specifics_tablayout_recycler, null);
        mRecyclerView = (RecyclerView) mView1.findViewById(R.id.specifics_recycler);
        mProgressBar = (ProgressBar) mView1.findViewById(R.id.specifics_load_progressbar);
        mView2 = mInflater.inflate(R.layout.specifics_tablayout_webview, null);
        mWebView = (WebView) mView2.findViewById(R.id.specifics_webView);

        ISpecificsPresenter mISpecificsPresenter = SpecificsPresenter.getInstance();
        mISpecificsPresenter.init(this);

        mISpecificsPresenter.setToolbar();//初始化导航栏
        mISpecificsPresenter.setTabLayout();//初始化页卡
        mISpecificsPresenter.setView1();//初始化List
        mISpecificsPresenter.setView2();//初始化WebView
    }

    @Override
    public void showWaiting() {
        mProgressBar.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void hideWaiting() {
        mProgressBar.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    //初始化View1
    @Override
    public void initProjectDetailList(Project_Detail mProjectDetail) {
        //获取屏幕的宽
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;

        mRecyclerView.setLayoutManager(new LinearLayoutManager(SpecificsActivity.this));//设置线性布局
        MyRecyclerViewAdapter myAdapter = new MyRecyclerViewAdapter(mProjectDetail, width);
        mRecyclerView.setAdapter(myAdapter);
    }

    //初始化View2
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void initProjectStatusWebView(String url) {
        if (mWebView != null) {
            mWebView.getSettings().setJavaScriptEnabled(true);
            mWebView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    dialog.dismiss();
                }
            });
            mWebView.loadUrl(url);
            dialog = ProgressDialog.show(this, null, "页面加载中，请稍后..");
            mWebView.reload();
        } else {
            Toast.makeText(SpecificsActivity.this, "页面加载失败", Toast.LENGTH_LONG).show();
        }
    }

    @SuppressLint("InflateParams")
    @Override
    public void initTabLayout(String[] tabTitle) {
        TabLayout mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        ViewPager mViewPager = (ViewPager) findViewById(R.id.view_pager);
//        mView1 =  mInflater.inflate(R.layout.specifics_tablayout_recycler, null);
//        mView2 =  mInflater.inflate(R.layout.specifics_tablayout_webview, null);
        //添加页卡视图
        mViewList.add(mView1);
        mViewList.add(mView2);
        //添加页卡标题
        Collections.addAll(mTitleList, tabTitle);
        /*mTitleList.add(mTabTitle[0]);
        mTitleList.add(mTabTitle[1]);*/
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);//设置tab模式，当前为系统默认模式
//        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(0)));//添加tab选项卡
//        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(1)));
        MyPagerAdapter mAdapter = new MyPagerAdapter(mViewList, mTitleList);
        mViewPager.setAdapter(mAdapter);//给ViewPager设置适配器
        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来。
        //noinspection deprecation
        mTabLayout.setTabsFromPagerAdapter(mAdapter);//给Tabs设置适配器
        setIndicator(mTabLayout);//设置tabLayout的indicator的宽度
    }

    //设置tabLayout的indicator的宽度
    private void setIndicator(TabLayout mTabLayout) {
        Class<?> tabLayout = mTabLayout.getClass();
        try {
            Field tabStrip = tabLayout.getDeclaredField("mTabStrip");
            tabStrip.setAccessible(true);
            LinearLayout ll_tab = (LinearLayout) tabStrip.get(mTabLayout);
            for (int i = 0; i < ll_tab.getChildCount(); i++) {
                View child = ll_tab.getChildAt(i);
                child.setPadding(0, 0, 0, 0);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
                params.setMarginStart(140);
                params.setMarginEnd(140);
//                params.width=70;
                params.gravity = Gravity.CENTER_HORIZONTAL;
                child.setLayoutParams(params);
                child.invalidate();
            }

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    //设置导航栏
    @Override
    public void initToolbar(String title) {
        //设置状态栏透明
        Util.setToolBarClear(this);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.specifics_toolbar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        TextView titleText = (TextView) findViewById(R.id.specifics_title_text);
        titleText.setText(title);
        mToolbar.setNavigationIcon(R.drawable.icon_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SpecificsActivity.this.finish();
            }
        });
    }
}
