package com.zk.walkwayapp.model.bean;

import com.zk.library.common.utils.DateUtils;

import java.util.Date;

import io.realm.RealmObject;

/**
 * Created by dzs on 2017/8/13.
 */
public class PushModel extends RealmObject {
    private String msg;
    private int type = -1;
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean equals(PushModel pushModel) {
        if (pushModel.getMsg().equals(msg)&& DateUtils.formatDate(pushModel.getDate()).equals(DateUtils.formatDate(date))){
            return true;
        }
        return super.equals(pushModel);
    }
}
