package com.zk.walkwayapp.presenter.login;


import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVMobilePhoneVerifyCallback;
import com.avos.avoscloud.AVSMS;
import com.avos.avoscloud.AVSMSOption;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.RequestMobileCodeCallback;
import com.avos.avoscloud.SignUpCallback;
import com.zk.library.common.mvp.BasePresenter;
import com.zk.walkwayapp.contract.RegisterContract;

public class RegisterPresenter extends BasePresenter<RegisterContract.IRegisterView> implements RegisterContract.IRegisterPresenter{

    /**
     * 创建presenter的时候加载view
     *
     * @param view
     */
    public RegisterPresenter(RegisterContract.IRegisterView view) {
        super(view);
    }

    //发送短信验证码
    public void sendSms(){
        if(getView().getPhone().equals("")||getView().getPwd().equals("")){
            getView().showToast("手机号和密码不能为空");
        }else {
            AVSMS.requestSMSCodeInBackground(getView().getPhone(), new AVSMSOption(), new RequestMobileCodeCallback() {
                @Override
                public void done(AVException e) {
                    if(e == null){
                        getView().sendSmsSuccess();
                    }else {
                        getView().showToast(e.getMessage());
                    }
                }
            });
        }

    }
    //验证短信验证码
    public void verfySmsCode(){
        if(getView().getSmsCode().equals("")){
            getView().showToast("请输入验证码");
        }else {
            getView().showLoading("正在验证");
            AVSMS.verifySMSCodeInBackground( getView().getPhone(), getView().getSmsCode(), new AVMobilePhoneVerifyCallback() {
                @Override
                public void done(AVException e) {
                    if(e==null){
                        register();
                    }else {
                        getView().hideLoading();
                        getView().showToast("短信验证码错误");
                    }
                }
            });
        }
    }
    //注册
    public void register(){
        AVUser avUser = new AVUser();
        avUser.setUsername(getView().getPhone());
        avUser.setMobilePhoneNumber(getView().getPhone());
        avUser.setPassword(getView().getPwd());
        avUser.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(AVException e) {
                    if(e==null){
                        getView().hideLoading();
                        getView().registerSuccess();
                    }else {
                        getView().hideLoading();
                        getView().showToast(e.getMessage());
                    }
            }
        });
    }
}
