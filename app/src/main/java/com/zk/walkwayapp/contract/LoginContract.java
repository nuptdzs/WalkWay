package com.zk.walkwayapp.contract;

import com.avos.avoscloud.AVUser;
import com.zk.library.common.mvp.IBaseView;
import com.zk.library.common.mvp.IMvpPresenter;

public interface LoginContract {
    interface ILoginPresenter extends IMvpPresenter<ILoginActivity>{
        void login();
    }

    interface ILoginActivity extends IBaseView {
        String getUserName();
        String getUserPwd();
        void login();
        void regist();
        void findPwd();
        void loginSuccess(AVUser avUser);
        void loginFail(String err_msg);
    }
}
