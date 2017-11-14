package com.zk.walkwayapp.view.fragment;


import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.bumptech.glide.Glide;
import com.zk.library.common.mvp.BaseFragment;
import com.zk.library.common.mvp.ContentView;
import com.zk.walkwayapp.R;
import com.zk.walkwayapp.contract.IMeContract;
import com.zk.walkwayapp.model.bean.UserTag;
import com.zk.walkwayapp.presenter.user.MePresenter;
import com.zk.walkwayapp.view.activity.AboutAppActivity;
import com.zk.walkwayapp.view.activity.FeedBackActivity;
import com.zk.walkwayapp.view.activity.GoalActivity;
import com.zk.walkwayapp.view.activity.LoginActivity;
import com.zk.walkwayapp.view.activity.PersonInfoActivity;
import com.zk.walkwayapp.view.activity.UserInfoActivity;

import butterknife.Bind;
import butterknife.OnClick;

@ContentView(R.layout.fragment_me)
public class MeFragment extends BaseFragment<IMeContract.IMePresenter> implements IMeContract.IMeView {


    @Bind(R.id.img_head)
    ImageView imgHead;
    @Bind(R.id.tv_nickname)
    TextView tvNickname;
    @Bind(R.id.tv_username)
    TextView tvUsername;
    @Bind(R.id.ll_userInfo)
    LinearLayout llUserInfo;
    @Bind(R.id.ll_msg)
    LinearLayout llMsg;
    @Bind(R.id.ll_goal)
    LinearLayout llGoal;
    @Bind(R.id.ll_personInfo)
    LinearLayout llPersonInfo;
    @Bind(R.id.ll_score)
    LinearLayout llScore;
    @Bind(R.id.ll_honors)
    LinearLayout llHonors;
    @Bind(R.id.ll_settings)
    LinearLayout llSettings;
    @Bind(R.id.ll_about)
    LinearLayout llAbout;
    @Bind(R.id.ll_feedback)
    LinearLayout llFeedback;
    @Bind(R.id.tv_logout)
    TextView tvLogout;

    public MeFragment() {
        // Required empty public constructor
    }

    @Override
    protected void load() {

    }

    /**
     * 界面重启 重新加载用户信息
     */
    @Override
    public void onResume() {
        super.onResume();
        AVUser avUser = AVUser.getCurrentUser();
        if (avUser !=null){
            String nickName = avUser.getString(UserTag.NICKNAME);
            tvUsername.setText(avUser.getMobilePhoneNumber());
            tvNickname.setText(TextUtils.isEmpty(nickName)?avUser.getMobilePhoneNumber():nickName);
            Glide.with(getContext()).load(avUser.getString(UserTag.AVATOR)).into(imgHead);
        }
    }

    @Override
    protected IMeContract.IMePresenter getPresenter() {
        return new MePresenter(this);
    }

    @OnClick({R.id.img_head, R.id.tv_nickname, R.id.tv_username, R.id.ll_userInfo, R.id.ll_msg, R.id.ll_goal, R.id.ll_personInfo, R.id.ll_score, R.id.ll_honors, R.id.ll_settings, R.id.ll_about, R.id.ll_feedback, R.id.tv_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_userInfo:
                startActivity(new Intent(getActivity(), UserInfoActivity.class));
                break;
            case R.id.ll_msg:
                break;
            case R.id.ll_goal:
                startActivity(new Intent(getActivity(), GoalActivity.class));
                break;
            case R.id.ll_personInfo:
                startActivity(new Intent(getActivity(), PersonInfoActivity.class));
                break;
            case R.id.ll_score:
                break;
            case R.id.ll_honors:
                break;
            case R.id.ll_settings:
                break;
            case R.id.ll_about:
                startActivity(new Intent(getActivity(), AboutAppActivity.class));
                break;
            case R.id.ll_feedback:
                startActivity(new Intent(getActivity(), FeedBackActivity.class));
                break;
            case R.id.tv_logout:
                mPresenter.logout();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                break;
        }
    }
}
