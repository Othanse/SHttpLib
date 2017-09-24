package com.example.eagleweb.kotlinonedemo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.eagleweb.kotlinonedemo.bean.DayEnglishBean
import com.example.eagleweb.kotlinonedemo.util.Constant
import com.example.eagleweb.shttplib.http.ErrorMessage
import com.example.eagleweb.shttplib.http.HttpClient
import com.example.eagleweb.shttplib.http.HttpDefaultCallback


class OneDayEnglishActivity : AppCompatActivity() {

    lateinit var tv_english: TextView
    lateinit var tv_chinese: TextView
    lateinit var iv_img: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_day_english)
        tv_english = findViewById(R.id.tv_english) as TextView
        tv_chinese = findViewById(R.id.tv_chinese) as TextView
        iv_img = findViewById(R.id.iv_img) as ImageView

        initData()
    }

    /**
     * 初始化数据，获取金山每日一句信息
     */
    private fun initData() {
        HttpClient.getInstance().get(Constant.ONE_DAY_ENGLISH_URL, DayEnglishBean::class.java, object : HttpDefaultCallback<DayEnglishBean>() {
            override fun success(bean: DayEnglishBean) {
                // 每日一句 英文
                tv_english.text = bean.content
                // 每日一句 中文
                tv_chinese.text = bean.note

                // 每日一句 合成图片
                Glide.with(this@OneDayEnglishActivity)
                        .load(bean.fenxiang_img)
                        .crossFade()
                        .into(iv_img)

                // 合成图片的点击事件 跳转到系统浏览器
                iv_img.setOnClickListener {
                    val intent = Intent()
                    intent.action = Intent.ACTION_VIEW
                    val content_url = Uri.parse(Constant.ONE_DAY_EBGLISH_WEB_URL + bean.sid)
                    intent.data = content_url
                    startActivity(intent)
                }
            }

            override fun failed(errorMessage: ErrorMessage) {
            }

        })
    }
}
