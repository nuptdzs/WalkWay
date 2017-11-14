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
    public static final String WEATHERAPI_KEY = "d253a2bd0c504b37a858a48e5fad24fc";

    public static void getWeatherInfo(HttpReuslObs<Result<WeatherResult>> httpReuslObs,String location){
        ServiceFactory.createService(WeatherServiceApi.class)
                .getWeatherInfo(location).compose(TransformUtils.<Result<WeatherResult>>UISchedulers())
        .subscribe(httpReuslObs);
    }
}
