package com.zk.walkwayapp.view.fragment;


import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zk.library.common.mvp.BaseFragment;
import com.zk.library.common.mvp.ContentView;
import com.zk.library.common.ui.BarChartViewSport;
import com.zk.library.common.ui.module.holographlibrary.Bar;
import com.zk.library.common.utils.DateUtils;
import com.zk.walkwayapp.R;
import com.zk.walkwayapp.contract.IDaySportRecordContract;
import com.zk.walkwayapp.model.bean.Sport;
import com.zk.walkwayapp.model.dao.SportDbUtils;
import com.zk.walkwayapp.presenter.sport.SportDayPresenter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

import static com.zk.library.common.utils.DateUtils.DATE_FORMAT;

/**
 * A simple {@link Fragment} subclass.
 */
@ContentView(R.layout.fragment_record)
public class RecordFragment extends BaseFragment<IDaySportRecordContract.IDaySportRecordPresenter>
        implements IDaySportRecordContract.IDaySportRecordView {


    String currentDate;
    @Bind(R.id.tvDate)
    TextView tvDate;
    @Bind(R.id.sport_total)
    TextView sportTotal;
    @Bind(R.id.chartView)
    BarChartViewSport chartView;
    @Bind(R.id.barchart_layout)
    LinearLayout barchartLayout;
    @Bind(R.id.bar_layout)
    FrameLayout barLayout;
    @Bind(R.id.first_title)
    TextView firstTitle;
    @Bind(R.id.tvStepCount)
    TextView tvStepCount;
    @Bind(R.id.tvDistance)
    TextView tvDistance;
    @Bind(R.id.third_title)
    TextView thirdTitle;
    @Bind(R.id.tvCalorie)
    TextView tvCalorie;
    @Bind(R.id.tvTimeHour)
    TextView tvTimeHour;
    @Bind(R.id.tvTimeMin)
    TextView tvTimeMin;
    @Bind(R.id.bottom_layout)
    LinearLayout bottomLayout;
    @Bind(R.id.id_home_layout)
    LinearLayout idHomeLayout;
    @Bind(R.id.tap_pre)
    ImageView tapPre;
    @Bind(R.id.tap_next)
    ImageView tapNext;
    private int preCount;
    private int itemType;
    private String date0;

    public RecordFragment() {
        // Required empty public constructor
    }

    @Override
    protected void load() {
        if (currentDate == null) {
            currentDate = DateUtils.formatDate(new Date());
        }
        mPresenter.querySports(currentDate);
    }

    @Override
    protected IDaySportRecordContract.IDaySportRecordPresenter getPresenter() {
        return new SportDayPresenter(this);
    }

    @Override
    public void showSportsDetail(List<Sport> sports) {
        if (preCount <= 0) {
            tapNext.setVisibility(View.INVISIBLE);
        } else {
            tapNext.setVisibility(View.VISIBLE);
        }
        if (isPreEnd("", itemType)) {
            tapPre.setVisibility(View.INVISIBLE);
        } else {
            tapPre.setVisibility(View.VISIBLE);
        }
        updateBarGraph(chartView, sports);
    }

    private ArrayList<Bar> bars = new ArrayList<>();

    /**
     * 刷新柱状view数据
     *
     * @param barGraph
     * @param datas    数据
     */
    private void updateBarGraph(BarChartViewSport barGraph, List<Sport> datas) {
        Log.e("sport", datas.toString());
        if (datas == null || datas.size() <= 0) {
            //如果没连设备或获取设备为空给一个初始值，使其ui显示。
            datas = new ArrayList<>();
            for (int i = 0; i < 25; i++) {
                Sport sport = new Sport();
                datas.add(sport);
            }
        }
        bars.clear();
        for (int i = 0; i < 24; i++) {
            Bar bar = new Bar();
            bar.setColor(getResources().getColor(R.color.white));
            if (i == 0 || i == 23) {
                bar.setShowBottomText(true);
                bar.setBottomText("00:00");
            }
            if (i == 6 || i == 12 || i == 18) {
                bar.setShowBottomText(true);
                bar.setBottomText(i + ":00");
            }

            bar.setShowTopText(true);
            int value = 0;
            Sport sport = new Sport();
            for (Sport sport1 : datas) {
                if (sport1.getTime() == i) {
                    sport = sport1;
                }
            }
            value = sport.getWalkCount();
            bar.setValue(value);

            int current = i;
            if (itemType == 1) {
                current = i - 4;
            }
            if (preCount == 0 && current == getCurrentHour()) {//当前时间
                bar.setStartColor(getResources().getColor(R.color.color_orange_f77e30));
                bar.setEndColor(getResources().getColor(R.color.color_orange_f77e30));

            } else if (bar.getValue() > 0) {//数值大于0
                bar.setStartColor(getResources().getColor(R.color.color_4fc7fb));
                bar.setEndColor(getResources().getColor(R.color.color_6c65fa));
            } else {//数值等于0
                bar.setStartColor(getResources().getColor(R.color.themeColorLight));
                bar.setEndColor(getResources().getColor(R.color.themeColorLight));
            }
            bars.add(bar);
        }
        int temp = 0;
        for (int i = 0; i < bars.size(); i++) {
            if (bars.get(temp).getValue() < bars.get(i).getValue()) {
                temp = i;
            }
        }
        if (bars.get(temp).getValue() > 0) {
            bars.get(temp).setTopText((int) bars.get(temp).getValue() + "");
            bars.get(temp).setShowTopText(true);
        }
        barGraph.setBarWidth(8);
        barGraph.setBarInitHeight(8);
        barGraph.setlTextSize(12);
        barGraph.setPageCount(23);
        barGraph.setBars(bars);

        date0 = DateUtils.nextDay(DateUtils.formatDate(new Date(), DATE_FORMAT), -preCount, DATE_FORMAT);
        if (date0 != null && date0.length() > 5) date0 = date0.substring(5, date0.length());
        if (preCount == 0) date0 = "today";
        tvDate.setText(date0);
    }

    @Override
    public void showSportsSum(Sport sport) {
        int stepTime = sport.getWalkTime();
        double stepDistance = sport.getWalkDistance();
        int stepCount = sport.getWalkCount();
        double currentCalorie = sport.getCalorie();
        tvStepCount.setText(stepCount + "");
        tvDistance.setText(getFormatData(stepDistance / 1000) + "");
        tvCalorie.setText(currentCalorie / 1000 + "");
        setTimeDescription(tvTimeHour, tvTimeMin, stepTime / 1000 / 60);
    }

    /***
     * 由总分钟数解析为 小时分钟字符描述
     *
     * @param minute
     * @return
     */
    public void setTimeDescription(TextView tvHour, TextView tvMin, int minute) {
        int hour0 = minute / 60;
        int minute0 = minute % 60;
        if (hour0 > 0) {
            tvHour.setText(hour0 + "");
        } else {
            tvHour.setText(0 + "");
        }
        if (minute0 > 0) {
            tvMin.setText(minute0 + "");
        } else {
            tvMin.setText(0 + "");
        }
        if (minute == 0) {
            tvHour.setText(0 + "");
            tvMin.setText(0 + "");
        }
    }

    /**
     * 获取当前小时
     *
     * @return
     */
    private int getCurrentHour() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取当前分钟
     *
     * @return
     */
    private int getCurrentMinutes() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        return calendar.get(Calendar.MINUTE);
    }

    /**
     * 保留两位小数点
     *
     * @param data
     * @return
     */
    private double getFormatData(double data) {
        BigDecimal bg = new BigDecimal(data);
        double result = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return result;
    }

    @OnClick({R.id.sport_total, R.id.tap_pre, R.id.tap_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tap_pre:
                preCount++;
                mPresenter.querySports(DateUtils.nextDay(DateUtils.formatDate(new Date(), DATE_FORMAT), -preCount, DATE_FORMAT));
                break;
            case R.id.tap_next:
                preCount--;
                mPresenter.querySports(DateUtils.nextDay(DateUtils.formatDate(new Date(), DATE_FORMAT), -preCount, DATE_FORMAT));
                break;
        }
    }

    /**
     * 检查当前选择日期是否比记录最早日期早
     *
     * @param userId
     * @param itemType
     * @return
     */
    private boolean isPreEnd(String userId, int itemType) {

        date0 = DateUtils.nextDay(DateUtils.formatDate(new Date(), DATE_FORMAT), -preCount, DATE_FORMAT);
        String markDate;
        markDate = SportDbUtils.getInstance().queryMinDate(userId);
        if (markDate == null) return false;
        long offsetDay = DateUtils.dateOffset(date0, markDate, DATE_FORMAT);
        if (offsetDay > 0) {
            return false;
        }
        return true;
    }

}
