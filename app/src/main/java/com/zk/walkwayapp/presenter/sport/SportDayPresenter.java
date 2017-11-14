package com.zk.walkwayapp.presenter.sport;

import com.avos.avoscloud.AVUser;
import com.zk.library.common.mvp.BasePresenter;
import com.zk.walkwayapp.contract.IDaySportRecordContract;
import com.zk.walkwayapp.model.bean.Sport;
import com.zk.walkwayapp.model.dao.SportDbUtils;

import java.util.List;

/**
 * Created by dzs on 2017/7/3.
 */

public class SportDayPresenter extends BasePresenter<IDaySportRecordContract.IDaySportRecordView>
        implements IDaySportRecordContract.IDaySportRecordPresenter{
    /**
     * 创建presenter的时候加载view
     *
     * @param view
     */
    public SportDayPresenter(IDaySportRecordContract.IDaySportRecordView view) {
        super(view);
    }

    /**
     * 从本地数据库中查询指定日期的运动记录
     * @param date
     */
    @Override
    public void querySports(String date) {
        String userId = AVUser.getCurrentUser().getObjectId();
        List<Sport> sports = SportDbUtils.getInstance().querySportDetailsByDay(userId,date);
        getView().showSportsDetail(sports);
        List<Sport> sportList = SportDbUtils.getInstance().querySportData(userId,date,-1);
        if(sportList == null||sportList.size() == 0){
            getView().showSportsSum(new Sport());
        }else {
            getView().showSportsSum(sportList.get(0));
        }
    }
}
