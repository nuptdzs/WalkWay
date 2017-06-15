package com.zk.walkwayapp.presenter;


import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.zk.library.common.mvp.BasePresenter;
import com.zk.walkwayapp.view.interfaces.ILoginActivity;

public class LoginPresenter extends BasePresenter<ILoginActivity>{

    /**
     * 创建presenter的时候加载view
     *
     * @param view
     */
    public LoginPresenter(ILoginActivity view) {
        super(view);
    }


    public void login() {
        String name = getView().getUserName();
        String password = getView().getUserPwd();
        if (name.length() == 0 || password.length() == 0) {
            getView().showToast("用户名和密码不能为空！");
            return;
        }
        getView().showLoading("正在登陆");
        AVUser.loginByMobilePhoneNumberInBackground(name, password, new LogInCallback<AVUser>() {
            @Override
            public void done(AVUser avUser, AVException e) {
                getView().hideLoading();
                if(avUser!=null){
                    getView().loginSuccess(avUser);
                }else {
                    getView().loginFail(e.getMessage());
                }
            }
        });

    }

}
