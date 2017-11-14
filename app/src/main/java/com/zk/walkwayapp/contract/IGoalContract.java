package com.zk.walkwayapp.contract;

import com.zk.library.common.mvp.IBaseView;
import com.zk.library.common.mvp.IMvpPresenter;

/**
 * Created by dzs on 2017/7/2.
 */

public interface IGoalContract {

    interface IGoalView extends IBaseView{
        void setGoal(int stepGoal,int weightGoal);
    }

    interface IGoalPresenter extends IMvpPresenter<IGoalView>{
        void loadGoal();
        void saveGoal(int stepGoal,int weightGoal);
    }
}
