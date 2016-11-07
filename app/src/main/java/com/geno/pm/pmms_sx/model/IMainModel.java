package com.geno.pm.pmms_sx.model;

import android.content.Context;

import com.geno.pm.pmms_sx.Bean.Information;
import com.geno.pm.pmms_sx.Bean.Project;

import java.util.List;

public interface IMainModel {

    void init();
    //toolbar
    List<Information> getInformation(Context context);

    //initFilter
    String[] getFilterTitle(Context context);

    //FilterItem    获取过滤信息
    void setFilterYear(String year);
    String getFilterYear();
    void setFilterStatus(String status);
    String getFilterStatus();
    void setFilterType(String type);
    String getFilterType();
    String getFilterTypeSharedPreferences(Context context,String filter);
    String getFilterYearSharedPreferences(Context context,String filter);
    String getFilterStatusSharedPreferences(Context context,String filter);
    void getFilterProjects(String TYPE, String YEAR, String STATUS, ProjectResult callback);
    interface ProjectResult {
        void onProjectSuccess(List<Project> projects);

        void onProjectFailed(String message);
    }

    //设置联动
    String[] getAllFilterYear();
    String[] getAllFilterStatus();
    String[] getAllFilterType();

    //获取全部工程
    void getAllProject(ProjectResult callback);
}
