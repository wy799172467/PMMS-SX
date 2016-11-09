package com.geno.pm.pmms_sx.presenter;

import android.content.Context;

import com.geno.pm.pmms_sx.activity.ISettingView;
import com.geno.pm.pmms_sx.model.ISettingModel;
import com.geno.pm.pmms_sx.model.SettingModel;

public class SettingPresenter implements ISettingPresenter {

    private ISettingView mISettingView;
    private ISettingModel mISettingModel;

    private static SettingPresenter mInstance = new SettingPresenter();

    private SettingPresenter() {
    }

    public static SettingPresenter getInstance() {
        return mInstance;
    }

    @Override
    public void init(ISettingView iSettingView) {
        mISettingView = iSettingView;
        mISettingModel = SettingModel.getInstance();
        mISettingModel.init((Context) mISettingView);
    }

    @Override
    public void setUserInfo() {
        mISettingView.initUserInfo();
        mISettingView.setUserAccountLabel(mISettingModel.getUserAccountLabel());
        mISettingView.setUserAccountValue(mISettingModel.getUserAccountValue());
        mISettingView.setUserNameLabel(mISettingModel.getUserNameLabel());
        mISettingView.setUserNameValue(mISettingModel.getUserNameValue());
        mISettingView.setDepartmentLabel(mISettingModel.getDepartmentLabel());
        mISettingView.setDepartmentValue(mISettingModel.getDepartmentValue());
    }
}
