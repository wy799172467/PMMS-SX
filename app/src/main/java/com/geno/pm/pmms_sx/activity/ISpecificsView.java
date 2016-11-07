package com.geno.pm.pmms_sx.activity;

import com.geno.pm.pmms_sx.Bean.Project_Detail;

public interface ISpecificsView {

    //网络
    void showWaiting();
    void hideWaiting();

    //toolbar
    void initToolbar(String title);

    //TabLayout
    void initTabLayout(String[] tabTitle);

    //List
    void initProjectDetailList(Project_Detail mProjectDetail);

    //view2
    void initProjectStatusWebView(String url);
}
