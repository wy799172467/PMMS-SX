package com.geno.pm.pmms_sx.presenter;

import com.geno.pm.pmms_sx.activity.ISpecificsView;

public interface ISpecificsPresenter {

    void init(ISpecificsView iSpecificsView);

    //Toolbar
    void setToolbar();

    //TabLayout
    void setTabLayout();

    //View1
    void setView1();

    //View2
    void setView2();
}
