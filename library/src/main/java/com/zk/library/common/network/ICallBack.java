package com.zk.library.common.network;

public interface ICallBack<T> {
    void onSuccess(T t);
    void onFailure(int code, String err_msg);
}
