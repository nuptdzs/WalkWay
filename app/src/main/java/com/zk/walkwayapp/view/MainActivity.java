package com.zk.walkwayapp.view;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.ArraySet;
import android.support.v4.view.ViewPager;
import android.text.format.DateUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.bumptech.glide.Glide;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.Holder;
import com.orhanobut.dialogplus.ViewHolder;
import com.zk.api.weather.Result;
import com.zk.api.weather.WeatherInfo;
import com.zk.api.weather.WeatherResult;
import com.zk.library.common.mvp.BaseActivity;
import com.zk.library.common.mvp.BasePresenter;
import com.zk.library.common.mvp.ContentView;
import com.zk.walkwayapp.R;
import com.zk.walkwayapp.view.activity.LoginActivity;
import com.zk.walkwayapp.view.fragment.GMapFragment;
import com.zk.walkwayapp.view.fragment.MainFragment;
import com.zk.walkwayapp.view.fragment.MeFragment;
import com.zk.walkwayapp.view.fragment.MsgFragment;
import com.zk.walkwayapp.view.fragment.RecordFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.ll_weather)
    RelativeLayout llWeather;
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    @Bind(R.id.rgBottom)
    RadioGroup rgBottom;
    @Bind(R.id.tv_weatherinfo)
    TextView tvWeatherInfo;
    private ImageView iv_weather;
    private TextView tv_weather,tv_jsl, tv_aqi, tv_sd, tv_wind_direction, tv_wind_power, tv_temperature_time,
            tv_temperature;

    private LinearLayout ll_dialog_holder;

    WeatherResult weatherResult;
    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    private List<Fragment> fragments = new ArrayList<>();
    private ArraySet<Integer> rgBtnMap = new ArraySet<>();
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Result<WeatherResult> weatherResultResult){
        if (tvWeatherInfo==null)return;
        weatherResult = weatherResultResult.getHeWeather5().get(0);
        tvWeatherInfo.setText(weatherResult.getNow().getCond().getTxt()+" "+weatherResult.getNow().getTmp()+"℃");
        tvWeatherInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNowWeatherDialog();
            }
        });
    }

    @Override
    protected void addEvent() {
        if (AVUser.getCurrentUser()==null){
            nextActivity(LoginActivity.class);
            finish();
        }
        EventBus.getDefault().register(this);
        rgBtnMap.add(R.id.rb_0);
        rgBtnMap.add(R.id.rb_1);
        rgBtnMap.add(R.id.rb_2);
        rgBtnMap.add(R.id.rb_3);
        rgBtnMap.add(R.id.rb_4);
        fragments.add(new MeFragment());
        fragments.add(new RecordFragment());
        fragments.add(new MainFragment());
        fragments.add(new GMapFragment());
        fragments.add(new MsgFragment());
        viewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
        rgBottom.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                viewpager.setCurrentItem(rgBtnMap.indexOf(checkedId));
            }
        });
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                rgBottom.check(rgBtnMap.valueAt(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    /**
     * 显示当前天气弹窗
     */
    public void showNowWeatherDialog() {
        WeatherInfo weatherInfo = weatherResult.getNow();
        ll_dialog_holder = (LinearLayout)getLayoutInflater().inflate(R.layout.dialog_weather, null);
        Holder holder = new ViewHolder(ll_dialog_holder);
        findHolderChildView(holder);
        Glide.with(this).load(String.format("https://cdn.heweather.com/cond_icon/%s.png",weatherInfo.getCond().getCode())).into(iv_weather);
        tv_weather.setText(weatherInfo.getCond().getTxt());
        tv_temperature.setText(weatherInfo.getTmp()+ "℃");
        tv_temperature_time.setText(weatherResult.getBasic().getUpdate().getLoc());
        tv_aqi.setText(String.format(getResources().getString(R.string.weather_dialog_aqi),
                weatherInfo.getVis()));
        tv_sd.setText(String.format(getResources().getString(R.string.weather_dialog_sd),
                weatherInfo.getHum()));
        tv_wind_direction.setText(String.format(getResources().getString(R.string.weather_dialog_wind_direction),
                weatherInfo.getWind().getDir()));
        tv_wind_power.setText(String.format(getResources().getString(R.string.weather_dialog_wind_power),
                weatherInfo.getWind().getSc()));
        tv_jsl.setText(String.format(getResources().getString(R.string.weather_dialog_jsl),weatherInfo.getPcpn()));
        showOnlyContentDialog(holder, Gravity.BOTTOM, false);
    }


    /**
     * 查找弹窗的holder的子控件
     *
     * @param holder
     */
    private void findHolderChildView(Holder holder) {
        tv_jsl = (TextView) holder.getInflatedView().findViewById(R.id.tv_jsl);
        iv_weather = (ImageView) holder.getInflatedView().findViewById(R.id.iv_weather);
        tv_weather = (TextView) holder.getInflatedView().findViewById(R.id.tv_weather);
        tv_temperature = (TextView) holder.getInflatedView().findViewById(R.id.tv_temperature);
        tv_temperature_time = (TextView) holder.getInflatedView().findViewById(R.id.tv_temperature_time);
        tv_aqi = (TextView) holder.getInflatedView().findViewById(R.id.tv_aqi);
        tv_sd = (TextView) holder.getInflatedView().findViewById(R.id.tv_sd);
        tv_wind_direction = (TextView) holder.getInflatedView().findViewById(R.id.tv_wind_direction);
        tv_wind_power = (TextView) holder.getInflatedView().findViewById(R.id.tv_wind_power);
    }
    /**
     * 仅显示内容的dialog
     *
     * @param holder
     * @param gravity         显示位置（居中，底部，顶部）
     * @param expanded        是否支持展开（有列表时适用）
     */
    private void showOnlyContentDialog(Holder holder, int gravity,
                                       boolean expanded) {
        final DialogPlus dialog = DialogPlus.newDialog(this)
                .setContentHolder(holder)
                .setGravity(gravity)
                .setExpanded(expanded)
                .setCancelable(true)
                .create();
        dialog.show();
    }
    @Override
    protected void loadData() {
        viewpager.setCurrentItem(2);
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        tvTitle.setText(DateUtils.formatDateTime(this,date.getTime(),DateUtils.FORMAT_ABBREV_WEEKDAY));
    }

}
