package com.zk.walkwayapp.model.dao;

import com.zk.walkwayapp.model.bean.PushModel;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by dzs on 2017/8/13.
 */

public class PushMsgDbUtils {

    public static List<PushModel> queryMsgs(){
        Realm realm = Realm.getDefaultInstance();
        RealmResults<PushModel> pushModels;
        pushModels = realm.where(PushModel.class).
                findAll();
        return realm.copyFromRealm(pushModels);
    }

    public static void insert(PushModel pushModel){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.insert(pushModel);
        realm.commitTransaction();

    }

    public static PushModel findLast() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<PushModel> realmResults =  realm.where(PushModel.class).findAll();
        if (realmResults.size()>=1){
            return realmResults.get(realmResults.size()-1);
        }else {
            return new PushModel();
        }
    }
}
