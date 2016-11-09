package com.geno.pm.pmms_sx.presenter;


import android.content.Context;

import com.geno.pm.pmms_sx.Bean.Login;
import com.geno.pm.pmms_sx.R;
import com.geno.pm.pmms_sx.model.ILoginModel;
import com.geno.pm.pmms_sx.model.LoginModel;
import com.geno.pm.pmms_sx.util.Util;
import com.geno.pm.pmms_sx.activity.ILoginView;
import com.geno.pm.pmms_sx.activity.LoginActivity;

public class LoginPresenter implements ILoginPresenter {

    private ILoginModel mILoginModel;
    private ILoginView mILoginView;

    private static LoginPresenter mInstance = new LoginPresenter();

    private LoginPresenter() {
    }

    public static LoginPresenter getInstance() {
        return mInstance;
    }

    @Override
    public void init(ILoginView iLoginView) {
        mILoginView = iLoginView;
        mILoginModel = LoginModel.getInstance();
        mILoginModel.init((LoginActivity) mILoginView);
    }


    @Override
    public void login() {

        if (checkFillContent()) {
            return;
        }

        saveLoginInformation();

        mILoginView.showWaiting();

        mILoginModel.loginAction(mILoginView.getUsername(), mILoginView.getPassword(), new ILoginModel.LoginCallBack() {
            @Override
            public void onLoginSuccess(Login login) {
                mILoginView.hideWaiting();
                mILoginModel.saveName(login);
                mILoginModel.saveUserAccount(login);
                mILoginModel.saveDepartment(login);
                mILoginModel.setFilterYear((Context) mILoginView, login);
                mILoginModel.setFilterStatus((Context) mILoginView, login);
                mILoginModel.setFilterType((Context) mILoginView, login);
                mILoginView.loginSuccess(login);
            }

            @Override
            public void onLoginFailed(String message) {
                mILoginView.hideWaiting();
                mILoginView.loginFailed(message);
            }
        });

    }

    private void saveLoginInformation() {
        if (mILoginView.isRememberMe()) {
            mILoginModel.saveUsername(mILoginView.getUsername());
            mILoginModel.savePassword(mILoginView.getPassword());
        } else {
            mILoginModel.savePassword("");
        }
        if (mILoginView.isAutoLogin()) {
            mILoginModel.saveAuto(true);
        } else {
            mILoginModel.saveAuto(false);
        }
    }

    private boolean checkFillContent() {
        if ("".equalsIgnoreCase(mILoginView.getUsername()) || "".equalsIgnoreCase(mILoginView.getPassword())) {
            mILoginView.toToast(R.string.account_null);
            return true;
        }
        if (!Util.isNetworkAvailable((LoginActivity) mILoginView)) {
            mILoginView.toToast(R.string.no_network);
            return true;
        }
        return false;
    }

    @Override
    public void initUsernameAndPassword() {
        if (!mILoginModel.getUsername().equals("")) {
            mILoginView.setUsername(mILoginModel.getUsername());
            mILoginView.setPassword(mILoginModel.getPassword());
            if (!mILoginModel.getPassword().equals("")) {
                mILoginView.setRememberMe(true);
            }

            if (mILoginModel.getAuto()) {
                mILoginView.setAutoLogin(true);
            }
        }
    }


}
