package com.zk.walkwayapp.contract;

import com.zk.library.common.mvp.IBaseView;
import com.zk.library.common.mvp.IMvpPresenter;

public interface IMeContract {
    interface IMePresenter extends IMvpPresenter<IMeView>{

        void logout();
    }

    interface IMeView extends IBaseView{

    }
}
