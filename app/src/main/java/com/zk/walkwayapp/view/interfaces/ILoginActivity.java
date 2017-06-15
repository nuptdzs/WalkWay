package com.zk.walkwayapp.view.interfaces;


import com.avos.avoscloud.AVUser;
import com.zk.library.common.mvp.IBaseView;
public interface ILoginActivity extends IBaseView {
    String getUserName();
    String getUserPwd();
    void login();
    void regist();
    void findPwd();
    void loginSuccess(AVUser avUser);
    void loginFail(String err_msg);
}
