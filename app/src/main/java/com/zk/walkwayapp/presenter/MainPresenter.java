package com.zk.walkwayapp.presenter;


import android.util.Log;

import com.avos.avoscloud.AVUser;
import com.zk.api.ApiCenter;
import com.zk.api.weather.Result;
import com.zk.api.weather.WeatherResult;
import com.zk.library.common.mvp.BasePresenter;
import com.zk.library.common.network.HttpReuslObs;
import com.zk.walkwayapp.WalkWayApplication;
import com.zk.walkwayapp.contract.IMainContract;
import com.zk.walkwayapp.model.bean.Sport;
import com.zk.walkwayapp.model.bean.UserTag;
import com.zk.walkwayapp.model.dao.SportDbUtils;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by dzs on 2017/6/14.
 */
public class MainPresenter extends BasePresenter<IMainContract.IMainView> implements IMainContract.IMainPresenter{
    /**
     * 创建presenter的时候加载view
     *
     * @param view
     */
    public MainPresenter(IMainContract.IMainView view) {
        super(view);
    }

    /**
     * 加载今日运动数据
     */
    public void loadTodaySport(){
        Observable.just(SportDbUtils.getInstance().querySportData(AVUser.getCurrentUser().getObjectId(),getTodayDate(),-1))
        .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<Sport>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<Sport> sports) {
                if(sports == null||sports.size()<1){
                    return;
                }
                getView().showSport(sports.get(0));
            }
        });
        List<Sport> sportList = SportDbUtils.getInstance().querySportDetailsByDay(AVUser.getCurrentUser().getObjectId(),getTodayDate());
        getView().showDetail(sportList);
        ApiCenter.getWeatherInfo(new HttpReuslObs<Result<WeatherResult>>() {
            @Override
            public void onFailure(String errorMsg, int errorCode) {
                Log.e("weather",errorMsg+errorCode);
            }

            @Override
            public void onSuccess(Result<WeatherResult> weatherResultResult) {
                WalkWayApplication.application.setWeatherInfo(weatherResultResult.getHeWeather5().get(0).getNow());
                getView().showWeatherInfo(weatherResultResult.getHeWeather5().get(0));
                EventBus.getDefault().post(weatherResultResult);
                Log.e("weather",weatherResultResult.toString());
            }
        },"Manchester");
    }

    @Override
    public void loadGoal() {

        int goal = AVUser.getCurrentUser().getInt(UserTag.STEP_GOAL);
        getView().showStepGoal(goal);
    }

    /**
     * 获取当前日期
     * @return
     */
    private String getTodayDate() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    /**
     * 获取当前小时
     * @return
     */
    public  int getTimeHour(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return calendar.get(Calendar.HOUR_OF_DAY);
    }
}
