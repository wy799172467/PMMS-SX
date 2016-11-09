package com.geno.pm.pmms_sx.presenter;

import com.geno.pm.pmms_sx.AllData.MainToSpecifics;
import com.geno.pm.pmms_sx.Bean.Project_Detail;
import com.geno.pm.pmms_sx.activity.ISpecificsView;
import com.geno.pm.pmms_sx.model.ISpecificsModel;
import com.geno.pm.pmms_sx.model.SpecificsModel;

public class SpecificsPresenter implements ISpecificsPresenter {

    private ISpecificsView mISpecificsView;
    private ISpecificsModel mISpecificsModel;

    private static SpecificsPresenter mInstance = new SpecificsPresenter();

    private SpecificsPresenter() {
    }

    public static SpecificsPresenter getInstance() {
        return mInstance;
    }

    @Override
    public void init(ISpecificsView iSpecificsView) {
        mISpecificsView = iSpecificsView;
        mISpecificsModel = SpecificsModel.getInstance();
        mISpecificsModel.init();
    }

    @Override
    public void setToolbar() {
        mISpecificsView.initToolbar(MainToSpecifics.mProjectName);
    }

    @Override
    public void setTabLayout() {
        mISpecificsView.initTabLayout(mISpecificsModel.getTabTitle());
    }

    @Override
    public void setView1() {
        mISpecificsView.showWaiting();
        mISpecificsModel.getProjectDetail(new ISpecificsModel.ProjectDetailResult() {
            @Override
            public void onProjectDetailSuccess(Project_Detail projectDetail) {
                mISpecificsView.hideWaiting();
                mISpecificsView.initProjectDetailList(projectDetail);
            }

            @Override
            public void onProjectDetailFailed() {
                mISpecificsView.hideWaiting();
            }
        });
    }

    @Override
    public void setView2() {
        mISpecificsView.showWaiting();
        mISpecificsView.initProjectStatusWebView(mISpecificsModel.getUrl());
        mISpecificsView.hideWaiting();
    }
}
