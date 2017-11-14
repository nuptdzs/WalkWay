package com.zk.walkwayapp.contract;

import com.zk.library.common.mvp.IBaseView;
import com.zk.library.common.mvp.IMvpPresenter;
import com.zk.walkwayapp.model.bean.UserInfo;

/**
 * Created by dzs on 2017/6/20.
 */

public interface IUserInfoContract {
    interface IUserInfoView extends IBaseView{
        UserInfo getUserInfo();
        void setUserInfo(UserInfo userInfo);
    }
    interface IUserInfoPresenter extends IMvpPresenter<IUserInfoView>{
        void saveUserInfo();
        void loadUserInfo();
    }
}
