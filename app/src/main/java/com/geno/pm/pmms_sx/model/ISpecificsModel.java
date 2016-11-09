package com.geno.pm.pmms_sx.model;

import com.geno.pm.pmms_sx.Bean.Project_Detail;

public interface ISpecificsModel {

    //
    void init();

    //toolbar

    //TabLayout
    String[] getTabTitle();

    //view1
    void getProjectDetail(ProjectDetailResult callback);
    interface ProjectDetailResult {
        void onProjectDetailSuccess(Project_Detail projectDetail);

        void onProjectDetailFailed();
    }

    //view2
    String getUrl();

}
