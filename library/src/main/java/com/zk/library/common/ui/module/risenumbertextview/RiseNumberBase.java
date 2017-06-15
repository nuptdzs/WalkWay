package com.zk.library.common.ui.module.risenumbertextview;

/**
 * Created by seazhang on 15/7/21.
 */
public interface RiseNumberBase {

    public void start();

    public RiseNumberTextView withNumber(float number);

    public RiseNumberTextView withNumber(int number);

    public RiseNumberTextView setDuration(long duration);

    public void setOnEnd(RiseNumberTextView.EndListener callback);

}
