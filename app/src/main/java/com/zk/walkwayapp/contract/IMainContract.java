package com.zk.walkwayapp.contract;

import com.zk.api.weather.WeatherResult;
import com.zk.library.common.mvp.IBaseView;
import com.zk.library.common.mvp.IMvpPresenter;
import com.zk.walkwayapp.model.bean.Sport;

import java.util.List;


public interface IMainContract {
    interface IMainView extends IBaseView{
        void showSport(Sport sport);

        void showStepGoal(int goal);
        void showDetail(List<Sport> sportList);

        void showWeatherInfo(WeatherResult weatherResult);
    }
    interface IMainPresenter extends IMvpPresenter<IMainView>{
        void loadTodaySport();

        void loadGoal();
    }
}
