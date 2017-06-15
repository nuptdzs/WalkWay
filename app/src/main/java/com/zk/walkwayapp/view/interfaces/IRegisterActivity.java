package com.zk.walkwayapp.view.interfaces;


import com.zk.library.common.mvp.IBaseView;

public interface IRegisterActivity extends IBaseView {
    String getPhone();
    String getPwd();
    String getSmsCode();
    void register();
    void sendSmsSuccess();
    void verfySmsSuccess();
    void registerSuccess();

}
