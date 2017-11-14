package com.zk.walkwayapp.contract;

import com.zk.library.common.mvp.IBaseView;
import com.zk.library.common.mvp.IMvpPresenter;
import com.zk.walkwayapp.model.bean.Sport;

import java.util.List;

/**
 * Created by dzs on 2017/7/3.
 */

public interface IDaySportRecordContract {

    interface IDaySportRecordView extends IBaseView{
        void showSportsDetail(List<Sport> sports);
        void showSportsSum(Sport sport);
    }

    interface IDaySportRecordPresenter extends IMvpPresenter<IDaySportRecordView>{
        void querySports(String date);
    }
}
