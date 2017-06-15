package com.zk.walkwayapp.view.interfaces;

import com.zk.library.common.mvp.IBaseView;
import com.zk.walkwayapp.model.bean.Sport;

import java.util.List;

/**
 * Created by dzs on 2017/6/14.
 */

public interface IMainView extends IBaseView {
    void showSport(Sport sport);

    void showDetail(List<Sport> sportList);
}
