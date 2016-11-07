package com.geno.pm.pmms_sx.presenter;


import com.geno.pm.pmms_sx.activity.ILoginView;

public interface ILoginPresenter {

    void init(ILoginView iLoginView);

    void login();

    void initUsernameAndPassword();

}
