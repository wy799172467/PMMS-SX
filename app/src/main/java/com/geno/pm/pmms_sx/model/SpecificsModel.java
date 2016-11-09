package com.geno.pm.pmms_sx.model;

import com.geno.pm.pmms_sx.AllData.MainToSpecifics;
import com.geno.pm.pmms_sx.Bean.Project_Detail;
import com.geno.pm.pmms_sx.http.ApiService;
import com.geno.pm.pmms_sx.util.Util;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SpecificsModel implements ISpecificsModel {

    private static final String[] mTabTitle = new String[]{"基本信息", "项目流程"};

    private static SpecificsModel mInstance = new SpecificsModel();

    private SpecificsModel() {

    }

    public static SpecificsModel getInstance() {
        return mInstance;
    }

    @Override
    public void init() {

    }

    @Override
    public String[] getTabTitle() {
        return mTabTitle;
    }

    @Override
    public void getProjectDetail(final ProjectDetailResult callback) {
        Observable<Project_Detail> projectDetail = Util.getInstance().getProjectDetail(MainToSpecifics.mProjectNo);
        projectDetail.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Subscriber<Project_Detail>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onProjectDetailFailed();
                    }

                    @Override
                    public void onNext(Project_Detail projectDetail) {
                        callback.onProjectDetailSuccess(projectDetail);

                    }
                });
    }

    @Override
    public String getUrl() {
        return ApiService.PROJECT_PROGRESS + MainToSpecifics.mProjectNo;
    }

}
