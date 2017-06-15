package com.zk.library.common.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by dzs on 2017/6/12.
 */
public class PerferenceUtils {
    private static final String NAME = "MY_PERFERENCE";
    private static SharedPreferences sharedPreference;
    private static PerferenceUtils perferenceUtils;
    public static PerferenceUtils getInstance(Context appContext){
        if(perferenceUtils == null){
            perferenceUtils = new PerferenceUtils();
            sharedPreference = appContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        }
        return perferenceUtils;
    }

    public String getValue(String key){
        return sharedPreference.getString(key,"");
    }

    public void setValue(String key,String value){
        sharedPreference.edit().putString(key,value).apply();
    }

    public int getIntValue(String key){
        return sharedPreference.getInt(key,0);
    }

    public void setIntValue(String key,int value){
        sharedPreference.edit().putInt(key,value).apply();
    }

    public long getLongValue(String key){
        return sharedPreference.getLong(key,0);
    }

    public void setLongValue(String key,long value){
        sharedPreference.edit().putLong(key,value).apply();
    }
}
