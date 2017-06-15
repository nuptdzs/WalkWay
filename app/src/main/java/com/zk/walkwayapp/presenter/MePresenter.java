package com.zk.walkwayapp.presenter;

import com.zk.library.common.mvp.BasePresenter;
import com.zk.walkwayapp.view.interfaces.IMeView;

/**
 * Created by dzs on 2017/6/14.
 */

public class MePresenter extends BasePresenter<IMeView> {
    /**
     * 创建presenter的时候加载view
     *
     * @param view
     */
    public MePresenter(IMeView view) {
        super(view);
    }
}
