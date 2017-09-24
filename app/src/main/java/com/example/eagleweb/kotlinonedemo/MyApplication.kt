package com.example.eagleweb.kotlinonedemo

import android.app.Application
import com.example.eagleweb.shttplib.http.HttpClient
import com.example.eagleweb.shttplib.http.MyHttpLoggingInterceptor
import com.example.eagleweb.shttplib.util.LogUtil

/**
 * @创建者 帅子
 * @创建时间  17/9/23.
 * @描述
 */
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        println("初始化应用程序咯")


        LogUtil.setLog(true)
        // log打印拦截器
        val loggingInterceptor = MyHttpLoggingInterceptor(MyHttpLoggingInterceptor.Logger { message ->
            if (LogUtil.isLog())
                LogUtil.s("  OkHttp网络请求日志：" + message)
        }).setLevel(MyHttpLoggingInterceptor.Level.BODY)
        HttpClient.getInstance().addInterceptor(loggingInterceptor)
    }
}