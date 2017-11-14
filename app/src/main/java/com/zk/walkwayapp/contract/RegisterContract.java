package com.zk.walkwayapp.contract;

import com.zk.library.common.mvp.IBaseView;
import com.zk.library.common.mvp.IMvpPresenter;

public interface RegisterContract {

    interface IRegisterPresenter extends IMvpPresenter<IRegisterView>{
        void register();
        void verfySmsCode();
        void sendSms();
    }

    interface IRegisterView extends IBaseView {
        String getPhone();
        String getPwd();
        String getSmsCode();
        void register();
        void sendSmsSuccess();
        void verfySmsSuccess();
        void registerSuccess();
    }
}
