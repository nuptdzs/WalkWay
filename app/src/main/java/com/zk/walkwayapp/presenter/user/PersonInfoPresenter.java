package com.zk.walkwayapp.presenter.user;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.zk.library.common.mvp.BasePresenter;
import com.zk.walkwayapp.contract.PersonInfoContract;
import com.zk.walkwayapp.model.bean.PersonInfo;
import com.zk.walkwayapp.model.bean.UserTag;

import static com.avos.avoscloud.AVUser.getCurrentUser;

public class PersonInfoPresenter extends BasePresenter<PersonInfoContract.IPersonInfoView>
        implements PersonInfoContract.IPersonInfoPresenter {
    /**
     * 创建presenter的时候加载view
     *
     * @param view
     */
    public PersonInfoPresenter(PersonInfoContract.IPersonInfoView view) {
        super(view);
    }

    @Override
    public void save() {
        PersonInfo personInfo = getView().getPersonInfo();
        getCurrentUser().put(UserTag.HEIGHT,personInfo.getHeight());
        getCurrentUser().put(UserTag.WEIGHT,personInfo.getWeight());
        getCurrentUser().saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if(e == null){
                    getView().showToast("保存成功");
                }else {
                    getView().showToast(e.getMessage());
                }
            }
        });

    }

    @Override
    public void load() {
        PersonInfo personInfo = new PersonInfo();
        int height = AVUser.getCurrentUser().getInt(UserTag.HEIGHT);
        int weight = AVUser.getCurrentUser().getInt(UserTag.WEIGHT);
        personInfo.setHeight(height == 0?170:height);
        personInfo.setWeight(weight == 0?60:weight);
        getView().setPersonInfo(personInfo);
    }
}
