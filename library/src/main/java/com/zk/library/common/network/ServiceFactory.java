package com.zk.library.common.network;

import android.annotation.TargetApi;
import android.os.Build;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
public class ServiceFactory {
    /**
     * 需要实现retrofit的接口  必须含有BASE_URL字段
     * @param serviceClass
     * @param <S>
     * @return
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static <S> S  createService(Class<S> serviceClass){
        String baseUrl ="";
        BaseUrl baseUrl1 = serviceClass.getAnnotation(BaseUrl.class);
        baseUrl = baseUrl1.path();
//            Field field = serviceClass.getField("BASE_URL");
//            baseUrl = (String) field.get(serviceClass);
        return build(baseUrl).build().create(serviceClass);
    }

    private static OkHttpClient client;

    private static OkHttpClient getClient() {
        if (client == null) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder builder =  new OkHttpClient.Builder();
            builder.readTimeout(8, TimeUnit.SECONDS);
            builder.writeTimeout(8, TimeUnit.SECONDS);
            builder.connectTimeout(5, TimeUnit.SECONDS);
            builder.addInterceptor(loggingInterceptor);
            builder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request()
                            .newBuilder()
                            .addHeader("apikey", "3ff9ce7447dbe0138c6c0f37bd0d614c")
                            .build();
                    return chain.proceed(request);
                }
            });
            client = builder.build();
        }
        return client;
    }
    private static Retrofit.Builder build(String baseurl) {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.addCallAdapterFactory(RxJavaCallAdapterFactory.create());
        builder.addConverterFactory(GsonConverterFactory.create());
        builder.baseUrl(baseurl);
        builder.client(getClient());
        return builder;
    }
}
