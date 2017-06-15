package com.zk.api.weather;

import com.zk.library.common.network.BaseUrl;

import retrofit2.http.Field;
import retrofit2.http.GET;
import rx.Observable;

/**
 * 天气API
 */
@BaseUrl(path = "https://api.seniverse.com/v3/")
public interface WeatherServiceApi {
    @GET("/weather/now.jsn")
    Observable<Result<WeatherResult>> getWeatherInfo(@Field("key")String key,
                                 @Field("location")String location,
                                 @Field("language")String language);

}
