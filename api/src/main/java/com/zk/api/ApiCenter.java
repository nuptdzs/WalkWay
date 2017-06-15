package com.zk.api;

import com.zk.api.weather.Result;
import com.zk.api.weather.WeatherResult;
import com.zk.api.weather.WeatherServiceApi;
import com.zk.library.common.network.HttpReuslObs;
import com.zk.library.common.network.ServiceFactory;
import com.zk.library.common.network.TransformUtils;


public class ApiCenter {
    /**
     * the apikey
     */
    public static final String WEATHERAPI_KEY = "hoew23rno7wwjibj";

    public static void getWeatherInfo(HttpReuslObs<Result<WeatherResult>> httpReuslObs){
        ServiceFactory.createService(WeatherServiceApi.class)
                .getWeatherInfo(WEATHERAPI_KEY,"ip","en").compose(TransformUtils.<Result<WeatherResult>>UISchedulers())
        .subscribe(httpReuslObs);
    }
}
