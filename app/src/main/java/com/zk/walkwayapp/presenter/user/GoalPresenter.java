package com.zk.walkwayapp.presenter.user;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.zk.library.common.mvp.BasePresenter;
import com.zk.walkwayapp.contract.IGoalContract;
import com.zk.walkwayapp.model.bean.UserTag;

/**
 * Created by dzs on 2017/7/2.
 */

public class GoalPresenter extends BasePresenter<IGoalContract.IGoalView> implements IGoalContract.IGoalPresenter{
    /**
     * 创建presenter的时候加载view
     *
     * @param view
     */
    public GoalPresenter(IGoalContract.IGoalView view) {
        super(view);
    }

    @Override
    public void loadGoal() {
        int stepgoal = AVUser.getCurrentUser().getInt(UserTag.STEP_GOAL);
        int weightGoal = AVUser.getCurrentUser().getInt(UserTag.WEIGHT_GOAL);
        getView().setGoal(stepgoal,weightGoal);
    }

    @Override
    public void saveGoal(int stepGoal, int weightGoal) {
        AVUser.getCurrentUser().put(UserTag.STEP_GOAL,stepGoal);
        AVUser.getCurrentUser().put(UserTag.WEIGHT_GOAL,weightGoal);
        AVUser.getCurrentUser().saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if(e == null){
                    getView().showToast("save successfully");
                }else {
                    getView().showToast(e.getMessage());
                }
            }
        });
    }
}
