package com.zk.walkwayapp.view.fragment;


import com.zk.library.common.mvp.BaseFragment;
import com.zk.library.common.mvp.ContentView;
import com.zk.walkwayapp.R;
import com.zk.walkwayapp.presenter.MePresenter;
import com.zk.walkwayapp.view.interfaces.IMeView;

@ContentView(R.layout.fragment_me)
public class MeFragment extends BaseFragment<MePresenter> implements IMeView{


    public MeFragment() {
        // Required empty public constructor
    }

    @Override
    protected void load() {

    }

    @Override
    protected MePresenter getPresenter() {
        return new MePresenter(this);
    }

}
