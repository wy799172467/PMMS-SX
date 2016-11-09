package com.geno.pm.pmms_sx.presenter;

import android.app.Activity;
import android.content.Context;

import com.geno.pm.pmms_sx.Bean.Project;
import com.geno.pm.pmms_sx.activity.IMainView;
import com.geno.pm.pmms_sx.model.IMainModel;
import com.geno.pm.pmms_sx.model.MainModel;

import java.util.List;

public class MainPresenter implements IMainPresenter, IMainModel.ProjectResult {

    private static MainPresenter mInstance = new MainPresenter();
    private IMainView mIMainView;
    private IMainModel mIMainModel;

    private MainPresenter() {
    }

    public static MainPresenter getInstance() {
        return mInstance;
    }

    @Override
    public void init(IMainView iMainView) {
        mIMainView = iMainView;
        mIMainModel = MainModel.getInstance();
        mIMainModel.init();
    }

    @Override
    public void showProjectChangeInformation() {
        mIMainView.showInformationPopupWindow();
        mIMainView.setBackWindowAction();
        mIMainView.showInformationData(mIMainModel.getInformation((Activity) mIMainView));
        mIMainView.setInformationPopupWindowCloseListen();
    }

    @Override
    public void initFilter() {
        mIMainView.initFilterTitle(mIMainModel.getFilterTitle((Context) mIMainView));
        mIMainView.initFilterPopupWindow();
        mIMainView.setLinkListen(); //设置下拉框的联动监听
        mIMainView.setFilterItem();
        mIMainView.setFilterPopupWindowDismiss();
    }

    @Override
    public void setFilterItem(String filter) {

        //noinspection ConstantConditions,deprecation
        if (mIMainView.isFilterImage1Up()) {
            if (filter.equals("全部类型")) {
                mIMainModel.setFilterType("all");
                mIMainView.setFilterTextView1(mIMainModel.getFilterTitle((Context) mIMainView)[0]);
            } else {
                mIMainModel.setFilterType(mIMainModel.getFilterTypeSharedPreferences((Context) mIMainView, filter));
                mIMainView.setFilterTextView1(filter);
            }
            mIMainView.setFilterImage1Down();
            mIMainView.showWaiting();
            mIMainModel.getFilterProjects(mIMainModel.getFilterType(),
                    mIMainModel.getFilterYear(),
                    mIMainModel.getFilterStatus(),
                    this);
        } else //noinspection ConstantConditions,deprecation
            if (mIMainView.isFilterImage2Up()) {
                if (filter.equals("全部年度")) {
                    mIMainModel.setFilterYear("all");
                    mIMainView.setFilterTextView2(mIMainModel.getFilterTitle((Context) mIMainView)[1]);
                } else {
                    mIMainModel.setFilterYear(mIMainModel.getFilterYearSharedPreferences((Context) mIMainView, filter));
                    mIMainView.setFilterTextView2(filter);
                }
                mIMainView.setFilterImage2Down();
                mIMainView.showWaiting();
                mIMainModel.getFilterProjects(mIMainModel.getFilterType(),
                        mIMainModel.getFilterYear(),
                        mIMainModel.getFilterStatus(),
                        this);
            } else { //noinspection ConstantConditions,deprecation
                if (filter.equals("全部状态")) {
                    mIMainModel.setFilterStatus("all");
                    mIMainView.setFilterTextView3(mIMainModel.getFilterTitle((Context) mIMainView)[2]);
                } else {
                    mIMainModel.setFilterStatus(mIMainModel.getFilterStatusSharedPreferences((Context) mIMainView, filter));
                    mIMainView.setFilterTextView3(filter);
                }
                mIMainView.setFilterImage3Down();
                mIMainView.showWaiting();
                mIMainModel.getFilterProjects(mIMainModel.getFilterType(),
                        mIMainModel.getFilterYear(),
                        mIMainModel.getFilterStatus(),
                        this);
            }
        mIMainView.filterDismiss();
    }

    @Override
    public void setFilter1LinkListen() {
        if (mIMainView.isFilterPopupWindowShowing()) {
            //noinspection ConstantConditions
            if (mIMainView.isFilterImage1Down()) {
                mIMainView.setPopData(mIMainModel.getAllFilterType());
                mIMainView.setFilterImage1Up();
                //noinspection ConstantConditions
                if (mIMainView.isFilterImage2Up()) {
                    mIMainView.setFilterImage2Down();
                } else {
                    mIMainView.setFilterImage3Down();
                }
            } else {
                mIMainView.filterDismiss();
                mIMainView.setFilterImage1Down();
            }
        } else {
            mIMainView.setPopData(mIMainModel.getAllFilterType());
            mIMainView.showFilterPopWindow();
            mIMainView.setBackViewAction();
            mIMainView.setFilterImage1Up();
            //方法二
                /*WindowManager.LayoutParams params=getWindow().getAttributes();
                params.alpha=0.5f;
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                getWindow().setAttributes(params);*/
            //方法三
                /*ListView listView = (ListView) findViewById(R.id.listView);
                listView.getBackground().setAlpha(255);
                listView.setBackgroundColor(getResources().getColor(R.color.main_listView_item_project_status));*/
        }
    }

    @Override
    public void setFilter2LinkListen() {
        if (mIMainView.isFilterPopupWindowShowing()) {
            //noinspection ConstantConditions,ConstantConditions
            if (mIMainView.isFilterImage2Down()) {
                mIMainView.setPopData(mIMainModel.getAllFilterYear());
                mIMainView.setFilterImage2Up();
                //noinspection ConstantConditions
                if (mIMainView.isFilterImage1Up()) {
                    mIMainView.setFilterImage1Down();
                } else {
                    mIMainView.setFilterImage3Down();
                }
            } else {
                mIMainView.filterDismiss();
                mIMainView.setFilterImage2Down();
            }
        } else {
            mIMainView.setPopData(mIMainModel.getAllFilterYear());
            mIMainView.showFilterPopWindow();
            mIMainView.setBackViewAction();
            mIMainView.setFilterImage2Up();
        }
    }

    @Override
    public void setFilter3LinkListen() {
        if (mIMainView.isFilterPopupWindowShowing()) {
            //noinspection ConstantConditions
            if (mIMainView.isFilterImage3Down()) {
                mIMainView.setPopData(mIMainModel.getAllFilterStatus());
                mIMainView.setFilterImage3Up();
                //noinspection ConstantConditions
                if (mIMainView.isFilterImage1Up()) {
                    mIMainView.setFilterImage1Down();
                } else {
                    mIMainView.setFilterImage2Down();
                }
            } else {
                mIMainView.filterDismiss();
                mIMainView.setFilterImage3Down();
            }
        } else {
            mIMainView.setPopData(mIMainModel.getAllFilterStatus());
            mIMainView.showFilterPopWindow();
            mIMainView.setBackViewAction();
            mIMainView.setFilterImage3Up();
        }
    }

    @Override
    public void initAllProject() {
        mIMainView.showWaiting();
        mIMainModel.getAllProject(this);
    }

    @Override
    public void onProjectSuccess(List<Project> projects) {
        mIMainView.hideWaiting();
        if (projects.size() == 0) {
            mIMainView.projectCallToast("暂无数据");
        }
        mIMainView.initListView(projects);
    }

    @Override
    public void onProjectFailed(String message) {
        mIMainView.hideWaiting();
        mIMainView.projectCallToast(message);
    }
}
