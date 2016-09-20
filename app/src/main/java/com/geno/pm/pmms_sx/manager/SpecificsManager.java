package com.geno.pm.pmms_sx.manager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.geno.pm.pmms_sx.Bean.Project_Detail;
import com.geno.pm.pmms_sx.util.Util;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SpecificsManager {

    private Context mContext;
    private static SpecificsManager sInstance=new SpecificsManager();

    private String mProjectNo;
    private String mProjectName;

    private SpecificsManager(){}


    public void init(Context context){
        mContext=context;
        Intent intent = ((Activity)mContext).getIntent();
        mProjectNo = intent.getStringExtra("ProjectNo");
        mProjectName = intent.getStringExtra("ProjectName");
    }

    public static SpecificsManager getInstance(){
        return sInstance;

    }

    public String getProjectName(){
        return mProjectName;
    }

    public String getProjectNo(){
        return mProjectNo;
    }

    //获取ProjectDetail信息
    public void getProjectDetail(final ProjectDetailResult callback) {
        Observable<Project_Detail> projectDetail = Util.getInstance().getProjectDetail(mProjectNo);
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
                    public void onNext(Project_Detail project_detail) {
                        callback.onProjectDetailSuccess(project_detail);

                    }
                });
    }

    public interface ProjectDetailResult{
        void onProjectDetailSuccess(Project_Detail project_detail);
        void onProjectDetailFailed();
    }
}
