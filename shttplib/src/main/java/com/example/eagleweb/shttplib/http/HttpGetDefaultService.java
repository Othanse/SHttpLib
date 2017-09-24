package com.example.eagleweb.shttplib.http;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by 菜鹰帅帅 on 2016/9/21.
 * POST请求框架的Service
 */

public interface HttpGetDefaultService {
    @GET
    Observable<ResponseBody> request(
            @Url String url
            , @QueryMap Map<String, String> map);


}
