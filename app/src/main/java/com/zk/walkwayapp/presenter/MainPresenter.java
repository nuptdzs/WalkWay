package com.zk.walkwayapp.presenter;


import com.avos.avoscloud.AVUser;
import com.zk.library.common.mvp.BasePresenter;
import com.zk.walkwayapp.model.bean.Sport;
import com.zk.walkwayapp.model.dao.SportDbUtils;
import com.zk.walkwayapp.view.interfaces.IMainView;

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

public class MainPresenter extends BasePresenter<IMainView> {
    /**
     * 创建presenter的时候加载view
     *
     * @param view
     */
    public MainPresenter(IMainView view) {
        super(view);
    }

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
