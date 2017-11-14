package com.zk.walkwayapp.presenter.user;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.zk.library.common.mvp.BasePresenter;
import com.zk.walkwayapp.contract.IUserInfoContract;
import com.zk.walkwayapp.model.bean.UserInfo;
import com.zk.walkwayapp.model.bean.UserTag;

/**
 * Created by dzs on 2017/6/20.
 */

public class UserInfoPresenter extends BasePresenter<IUserInfoContract.IUserInfoView>
        implements IUserInfoContract.IUserInfoPresenter{
    /**
     * 创建presenter的时候加载view
     *
     * @param view
     */
    public UserInfoPresenter(IUserInfoContract.IUserInfoView view) {
        super(view);
    }

    @Override
    public void saveUserInfo() {
        UserInfo userInfo = getView().getUserInfo();
        AVUser avUser = AVUser.getCurrentUser();
        avUser.put(UserTag.AVATOR,userInfo.getAvatorPath());
        avUser.put(UserTag.NICKNAME,userInfo.getNickName());
        avUser.put(UserTag.SEX,userInfo.getSex());
        avUser.put(UserTag.COMPANY_ADDRESS,userInfo.getCompanyAddress());
        avUser.put(UserTag.COMPANY_LAT,userInfo.getCompanyLat());
        avUser.put(UserTag.COMPANY_LNG,userInfo.getCompanyLng());
        avUser.put(UserTag.HOME_ADDRESS,userInfo.getHomeAddress());
        avUser.put(UserTag.HOME_LAT,userInfo.getHomeLat());
        avUser.put(UserTag.HOME_LNG,userInfo.getHomeLng());
        avUser.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                getView().showToast("save successfully");
            }
        });
    }

    @Override
    public void loadUserInfo() {
        AVUser avUser = AVUser.getCurrentUser();
        UserInfo userInfo = new UserInfo();
        userInfo.setAvatorPath(avUser.getString(UserTag.AVATOR));
        userInfo.setNickName(avUser.getString(UserTag.NICKNAME));
        userInfo.setSex(avUser.getInt(UserTag.SEX));
        userInfo.setHomeAddress(avUser.getString(UserTag.HOME_ADDRESS));
        userInfo.setHomeLat(avUser.getDouble(UserTag.HOME_LAT));
        userInfo.setHomeLng(avUser.getDouble(UserTag.HOME_LNG));

        userInfo.setCompanyAddress(avUser.getString(UserTag.COMPANY_ADDRESS));
        userInfo.setCompanyLat(avUser.getDouble(UserTag.COMPANY_LAT));
        userInfo.setCompanyLng(avUser.getDouble(UserTag.COMPANY_LNG));
        getView().setUserInfo(userInfo);
    }
}
