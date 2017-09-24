package com.example.eagleweb.shttplib.http;


import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 菜鹰帅帅 on 2016/9/5.
 * 这是~
 */
public class InterfaceRetrofit {
    private static Retrofit retrofit;

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            synchronized (InterfaceRetrofit.class) {
                if (retrofit == null) {
                    retrofit = new Retrofit.Builder()
                            .baseUrl(HttpClient.getInstance().getBaseUrl())
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .client(HttpClient.getInstance().defaultOkHttpClient())
                            .build();
                }
            }
        }
        return retrofit;
    }
}
