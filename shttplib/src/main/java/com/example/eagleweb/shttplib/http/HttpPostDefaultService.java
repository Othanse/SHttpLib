package com.example.eagleweb.shttplib.http;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by 菜鹰帅帅 on 2016/9/21.
 * POST请求框架的Service
 */

public interface HttpPostDefaultService {
    @FormUrlEncoded
    @POST
    Observable<ResponseBody> request(
            @Url String url
            , @FieldMap Map<String, String> map);


}
