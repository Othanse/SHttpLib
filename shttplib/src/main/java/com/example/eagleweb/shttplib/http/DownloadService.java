package com.example.eagleweb.shttplib.http;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by 菜鹰帅帅 on 2016/11/16.
 * 下载文件的网络Service
 */

public interface DownloadService {
    @Streaming
    @POST
    Observable<ResponseBody> download(@Url String url
            , @FieldMap Map<String, String> map);
}
