package com.zk.walkwayapp.view.fragment;


import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.zk.api.weather.WeatherResult;
import com.zk.library.common.mvp.BaseFragment;
import com.zk.library.common.mvp.ContentView;
import com.zk.library.common.ui.ArcProgressBar;
import com.zk.walkwayapp.R;
import com.zk.walkwayapp.contract.IMainContract;
import com.zk.walkwayapp.model.bean.Sport;
import com.zk.walkwayapp.presenter.MainPresenter;

import java.math.BigDecimal;
import java.util.List;

import butterknife.Bind;


@ContentView(R.layout.fragment_main)
public class MainFragment extends BaseFragment<IMainContract.IMainPresenter> implements IMainContract.IMainView {
    @Bind(R.id.progress)
    ArcProgressBar progressBar;
    @Bind(R.id.tv_calorie)
    TextView tvCalorie;
    @Bind(R.id.tv_active_time)
    TextView tvActiveTime;
    @Bind(R.id.tv_distance)
    TextView tvDistance;
    @Bind(R.id.tv_goal)
    TextView tvGoal;
    @Bind(R.id.tv_level)
    TextView tvLevel;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    protected void load() {
        mPresenter.loadGoal();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.loadTodaySport();
    }

    @Override
    protected IMainContract.IMainPresenter getPresenter() {
        if (mPresenter != null) {
            return mPresenter;
        }
        return new MainPresenter(this);
    }

    private int goal = 10000;
    @Override
    public void showSport(Sport sport) {
        progressBar.setMaxProgress(goal);
        progressBar.setProgress(sport.getWalkCount());
        progressBar.setProgressDesc(sport.getWalkCount() + "");
        tvCalorie.setText(getFormatData(sport.getCalorie()/1000)+"");
        tvActiveTime.setText(getTimeDescription(sport.getWalkTime())+"");
        tvDistance.setText(getFormatData(sport.getWalkDistance()/1000)+"");
    }

    @Override
    public void showStepGoal(int goal) {
        if(goal != 0){
            this.goal = goal;
        }
        tvGoal.setText("Goal:"+this.goal);
    }

    /**
     * 保留两位小数点
     *
     * @param data
     * @return
     */
    private double getFormatData(double data) {
        BigDecimal bg = new BigDecimal(data);
        return bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    /***
     * 由总分钟数解析为 小时分钟字符描述
     * @return
     */
    public String getTimeDescription(int mins) {
        int seconds = mins/1000;
        int second0 = seconds%60;
        int minute = mins/1000/60;
        int hour0 = minute / 60;
        int minute0 = minute % 60;

        StringBuilder stringBuilder = new StringBuilder();
        if (hour0 > 0) {
            stringBuilder.append(hour0 + "h");
        }
        if (minute0 > 0) {
            stringBuilder.append(minute0 + "m");
        }
        if(second0>0){
            stringBuilder.append(second0+"s");
        }
        if (TextUtils.isEmpty(stringBuilder.toString())) {
            stringBuilder.append("0h 0m");
        }

        return stringBuilder.toString();
    }
    @Override
    public void showDetail(List<Sport> sportList) {
        Log.e("SPORT",sportList.toString());
    }

    @Override
    public void showWeatherInfo(WeatherResult weatherResult) {

    }
}
