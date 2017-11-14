package com.zk.walkwayapp.presenter.user;

import com.avos.avoscloud.AVUser;
import com.zk.library.common.mvp.BasePresenter;
import com.zk.walkwayapp.contract.IMeContract;

/**
 * Created by dzs on 2017/6/14.
 */

public class MePresenter extends BasePresenter<IMeContract.IMeView> implements IMeContract.IMePresenter{
    /**
     * 创建presenter的时候加载view
     *
     * @param view
     */
    public MePresenter(IMeContract.IMeView view) {
        super(view);
    }

    @Override
    public void logout() {
        AVUser.logOut();
    }
}
