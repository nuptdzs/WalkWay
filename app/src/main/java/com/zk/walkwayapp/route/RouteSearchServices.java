package com.zk.walkwayapp.route;

import com.zk.library.common.network.BaseUrl;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/** * Created by dzs on 2017/8/6.
 */
@BaseUrl(path = "https://maps.googleapis.com/maps/api/directions/json/")
public interface RouteSearchServices {
    @GET()
    Observable<RouteJson> queryDirections(@Query("key") String key,
                                  @Query("origin")String origin,
                                  @Query("destination")String destination,
                                  @Query("mode")String mode);

}
