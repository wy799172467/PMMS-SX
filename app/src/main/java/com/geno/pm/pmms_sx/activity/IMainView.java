package com.geno.pm.pmms_sx.activity;


import com.geno.pm.pmms_sx.Bean.Information;
import com.geno.pm.pmms_sx.Bean.Project;

import java.util.List;

public interface IMainView {

    //网络访问
    void showWaiting();
    void hideWaiting();

    //初始化filter过滤器
    void initFilterTitle(String[] filter);
    void setLinkListen();
    void setFilterItem();
    void initFilterPopupWindow();
    void setFilterPopupWindowDismiss();

    //setFilterItem
    boolean isFilterImage1Up();
    boolean isFilterImage2Up();
    void setFilterTextView1(String filter);
    void setFilterTextView2(String filter);
    void setFilterTextView3(String filter);
    void setFilterImage1Up();
    void setFilterImage2Up();
    void setFilterImage3Up();
    void filterDismiss();
    void projectCallToast(String message);
    void initListView(final List<Project> projects);

    //设置联动
    boolean isFilterPopupWindowShowing();
    boolean isFilterImage1Down();
    boolean isFilterImage2Down();
    boolean isFilterImage3Down();
    void setFilterImage1Down();
    void setFilterImage2Down();
    void setFilterImage3Down();
    void showFilterPopWindow();
    void setPopData(String[] data);

    //显示通知信息
    void showInformationPopupWindow();
    void showInformationData(List<Information> information);
    void setInformationPopupWindowCloseListen();


    void setBackViewAction();
}
