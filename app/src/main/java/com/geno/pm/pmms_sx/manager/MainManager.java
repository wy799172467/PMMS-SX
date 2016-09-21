package com.geno.pm.pmms_sx.manager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.geno.pm.pmms_sx.Bean.Project;
import com.geno.pm.pmms_sx.util.Util;

import org.apache.commons.lang3.ArrayUtils;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainManager {

    private Context mContext;

    private static final String[] FILTER = new String[]{"全部类型", "年度计划", "项目状态"};

    private String[] mFILTER_YEAR;
    private String[] mFILTER_STATUS;
    private String[] mFILTER_TYPE;

    private SharedPreferences mFilterYear;
    private SharedPreferences mFilterStatus;
    private SharedPreferences mFilterType;

    @SuppressLint("StaticFieldLeak")
    private static MainManager sInstance = new MainManager();

    private MainManager() {
    }

    public static MainManager getInstance() {
        return sInstance;
    }

    public void init(Context context) {
        mContext = context;
        initSharePreferences();
        initFilterArray();
    }

    private void initFilterArray() {
        if(mFILTER_YEAR==null){
            Intent intent = ((Activity) mContext).getIntent();
            mFILTER_YEAR = ArrayUtils.add(intent.getStringArrayExtra("YEAR"), "全部年度");
            mFILTER_STATUS = ArrayUtils.add(intent.getStringArrayExtra("STATUS"), "全部状态");
            mFILTER_TYPE = ArrayUtils.add(intent.getStringArrayExtra("TYPE"), "全部类型");
        }
    }

    public String[] getFILTER_YEAR(){
        return mFILTER_YEAR;
    }

    public String[] getFILTER_STATUS(){
        return mFILTER_STATUS;
    }

    public String[] getFILTER_TYPE(){
        return mFILTER_TYPE;
    }


    private void initSharePreferences() {
        mFilterYear = mContext.getSharedPreferences("filter_year", Context.MODE_PRIVATE);
        mFilterStatus = mContext.getSharedPreferences("filter_status", Context.MODE_PRIVATE);
        mFilterType = mContext.getSharedPreferences("filter_type", Context.MODE_PRIVATE);
    }

    public SharedPreferences getFilterTypeSharePreferences() {
        return mFilterType;
    }

    public SharedPreferences getFilterYearSharePreferences() {
        return mFilterYear;
    }

    public SharedPreferences getFilterStatusSharePreferences() {
        return mFilterStatus;
    }

    public String[] getFilter(){
        return FILTER;
    }

    //获取全部数据
    public void getAllProject(final ProjectResult callback) {
        Observable<List<Project>> allProject = Util.getInstance().getAllProject();
        allProject.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Project>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
//                        String message = e.getMessage();
//                        Log.i("error---------",message);
                        callback.onProjectFailed();
                    }

                    @Override
                    public void onNext(List<Project> projects) {
                        callback.onProjectSuccess(projects);
                    }
                });
    }

    //得到筛选的工程项目
    public void getFilterProjects(String TYPE, String YEAR, String STATUS,
                                   final ProjectResult callback) {
        Observable<List<Project>> filterProject = Util.getInstance().
                getFilterProject(TYPE, YEAR, STATUS);
        filterProject.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Project>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onProjectFailed();
                    }

                    @Override
                    public void onNext(List<Project> projects) {
                        callback.onProjectSuccess(projects);
                    }
                });
    }

    public interface ProjectResult{
        void onProjectSuccess(List<Project> projects);
        void onProjectFailed();
    }
}
