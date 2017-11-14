package com.zk.walkwayapp.route;

/**
 * Created by dzs on 2017/8/6.
 */

public  class Fare {
    private String currency;//：ISO 4217 币种代码，表示票价所采用的币种
    private String value;//：总票价（以上面指定的币种为单位）
    private String text;//：总票价金额，设置为所请求语言的格式
}
