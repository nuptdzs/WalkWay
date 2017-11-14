package com.zk.walkwayapp.contract;

import com.zk.library.common.mvp.IBaseView;
import com.zk.library.common.mvp.IMvpPresenter;
import com.zk.walkwayapp.model.bean.PersonInfo;

/**
 * Created by dzs on 2017/6/20.
 */

public interface PersonInfoContract {
    interface IPersonInfoView extends IBaseView{

        void setPersonInfo(PersonInfo personInfo);
        PersonInfo getPersonInfo();
    }
    interface IPersonInfoPresenter extends IMvpPresenter<IPersonInfoView>{
        void save();
        void load();
    }
}
