package com.zk.api.weather;

import com.zk.library.common.network.BaseUrl;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 天气API
 */
@BaseUrl(path = "http://apis.baidu.com/heweather/")
public interface WeatherServiceApi {
    @GET("pro/weather")
    Observable<Result<WeatherResult>> getWeatherInfo(
                                 @Query("city")String location);

}
