package com.zk.walkwayapp.view;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.ArraySet;
import android.support.v4.view.ViewPager;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zk.library.common.mvp.BaseActivity;
import com.zk.library.common.mvp.BasePresenter;
import com.zk.library.common.mvp.ContentView;
import com.zk.walkwayapp.R;
import com.zk.walkwayapp.view.fragment.MainFragment;
import com.zk.walkwayapp.view.fragment.MapFragment;
import com.zk.walkwayapp.view.fragment.MeFragment;
import com.zk.walkwayapp.view.fragment.MsgFragment;
import com.zk.walkwayapp.view.fragment.RecordFragment;

import java.util.ArrayList;
import java.util.Calendar;
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

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    private List<Fragment> fragments = new ArrayList<>();
    private ArraySet<Integer> rgBtnMap = new ArraySet<>();
    @Override
    protected void addEvent() {
        rgBtnMap.add(R.id.rb_0);
        rgBtnMap.add(R.id.rb_1);
        rgBtnMap.add(R.id.rb_2);
        rgBtnMap.add(R.id.rb_3);
        rgBtnMap.add(R.id.rb_4);

        fragments.add(new MeFragment());
        fragments.add(new RecordFragment());
        fragments.add(new MainFragment());
        fragments.add(new MapFragment());
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

    @Override
    protected void loadData() {
        viewpager.setCurrentItem(2);
        Calendar calendar = Calendar.getInstance();
        tvTitle.setText(calendar.get(Calendar.MONTH)+"/"+calendar.get(Calendar.DAY_OF_MONTH));
    }

}
