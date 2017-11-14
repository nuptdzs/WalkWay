package com.zk.library.common.network;

import java.util.HashSet;
import java.util.Set;

import rx.Observer;

public abstract class HttpReuslObs<T> implements Observer<T> {

    private static final int NETWORK_NOT_RESPONDING = 133;
    private static final int ERROR_RESULT_IS_NULL = 122;

    public HttpReuslObs(){
        for(String resultTag :resultTags){
            resultTagsSet.add(resultTag);
        }
        for (String resultValue :resultTagsSet){
            resultValuesSet.add(resultValue);
        }
    }
    @Override
    public void onCompleted() {

    }


    @Override
    public void onError(Throwable e) {
        onFailure("网络连接失败"+e.getMessage(),NETWORK_NOT_RESPONDING);
    }

    Set<String> resultTagsSet = new HashSet<>();
    Set<String> resultValuesSet = new HashSet<>();
    String[] resultTags = {};
    String[] resultValues = {};
    @Override
    public void onNext(T t){
        boolean isSuccess = true;
        if(t == null){
            onFailure("结果为空",ERROR_RESULT_IS_NULL);
            return;
        }
        if(isSuccess){
            onSuccess(t);
        }else {
            onFailure("操作失败",201);
        }
    }

    public abstract void onFailure(String errorMsg, int errorCode);
    public abstract void onSuccess(T t);
}
