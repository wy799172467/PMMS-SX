package com.geno.pm.pmms_sx.presenter;

import com.geno.pm.pmms_sx.activity.IMainView;

public interface IMainPresenter {

    void init(IMainView iMainView);

    //Toolbar
    void showProjectChangeInformation();

    //初始筛选化标题
    void initFilter();

    //获取筛选Item
    void setFilterItem(String filter);

    //设置联动监听
    void setFilter1LinkListen();
    void setFilter2LinkListen();
    void setFilter3LinkListen();

    //获取全部项目信息
    void initAllProject();

}
