package com.example.eagleweb.shttplib.http;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by 菜鹰帅帅 on 2016/9/21.
 * POST请求框架的Service
 */

public interface HttpPostUploadFileService {

    // 单个文件上传
    @Multipart
    @POST
    Observable<ResponseBody> uploadFile(
            @Url String url
            //            , @Part("description") RequestBody description
            , @Part MultipartBody.Part file
            , @PartMap Map<String, RequestBody> map
            //            , @FieldMap Map<String, String> map
    );


    // 多个文件上传
    @Multipart
    @POST
    Observable<ResponseBody> uploadFiles(
            @Url String url
            , @PartMap Map<String, RequestBody> map1
            , @PartMap Map<String, MultipartBody.Part> map2
    );


}
