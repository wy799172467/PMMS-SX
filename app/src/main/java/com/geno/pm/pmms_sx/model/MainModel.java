package com.geno.pm.pmms_sx.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.geno.pm.pmms_sx.AllData.LoginToMain;
import com.geno.pm.pmms_sx.Bean.Information;
import com.geno.pm.pmms_sx.Bean.Project;
import com.geno.pm.pmms_sx.R;
import com.geno.pm.pmms_sx.util.DatabaseHelper;
import com.geno.pm.pmms_sx.util.Util;

import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainModel implements IMainModel{

    private String YEAR = "all";
    private String STATUS = "all";
    private String TYPE = "all";

    private static MainModel mInstance=new MainModel();
    private MainModel(){}
    public static MainModel getInstance(){
        return mInstance;
    }


    @Override
    public void init() {

    }

    @Override
    public List<Information> getInformation(Context context) {
        DatabaseHelper database = new DatabaseHelper(context);//这段代码放到Activity类中才用this
        SQLiteDatabase db = database.getWritableDatabase();
        @SuppressLint("Recycle")
        Cursor mCursor = db.query("information", null, null, null, null, null, null);//查询并获得游标
        List<Information> informations = new ArrayList<>();
        while (mCursor.moveToNext()) {
            Information information = new Information();
            information.setProjectName(mCursor.getString(mCursor.getColumnIndex("ProjectName")));
            information.setProjectNo(mCursor.getString(mCursor.getColumnIndex("ProjectNo")));
            information.setProjectDetail(mCursor.getString(mCursor.getColumnIndex("ProjectDetail")));
            informations.add(information);
        }
        return informations;
    }

    @Override
    public String[] getFilterTitle(Context context) {
        return context.getResources().getStringArray(R.array.filter);
    }

    @Override
    public void setFilterYear(String year) {
        YEAR=year;
    }

    @Override
    public String getFilterYear() {
        return YEAR;
    }

    @Override
    public void setFilterStatus(String status) {
        STATUS=status;
    }

    @Override
    public String getFilterStatus() {
        return STATUS;
    }

    @Override
    public void setFilterType(String type) {
        TYPE=type;
    }

    @Override
    public String getFilterType() {
        return TYPE;
    }

    @Override
    public String getFilterTypeSharedPreferences(Context context, String filter) {
        return context.getSharedPreferences("filter_type", Context.MODE_PRIVATE).getString(filter, "");
    }

    @Override
    public String getFilterYearSharedPreferences(Context context, String filter) {
        return context.getSharedPreferences("filter_year", Context.MODE_PRIVATE).getString(filter, "");
    }

    @Override
    public String getFilterStatusSharedPreferences(Context context, String filter) {
        return context.getSharedPreferences("filter_status", Context.MODE_PRIVATE).getString(filter, "");
    }

    @Override
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
                        callback.onProjectFailed(e.getMessage());
                    }

                    @Override
                    public void onNext(List<Project> projects) {
                        callback.onProjectSuccess(projects);
                    }
                });
    }

    @Override
    public String[] getAllFilterYear() {
        return ArrayUtils.add(LoginToMain.mYears, "全部年度");
    }

    @Override
    public String[] getAllFilterStatus() {
        return ArrayUtils.add(LoginToMain.mStatus, "全部状态");
    }

    @Override
    public String[] getAllFilterType() {
        return ArrayUtils.add(LoginToMain.mProjects, "全部类型");
    }

    @Override
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
                        callback.onProjectFailed(e.getMessage());
                    }

                    @Override
                    public void onNext(List<Project> projects) {
                        callback.onProjectSuccess(projects);
                    }
                });
    }


}
