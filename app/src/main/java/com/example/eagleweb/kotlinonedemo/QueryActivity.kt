package com.example.eagleweb.kotlinonedemo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.eagleweb.kotlinonedemo.adapter.QueryResultAdapter
import com.example.eagleweb.kotlinonedemo.bean.QueryBean
import com.example.eagleweb.kotlinonedemo.bean.TranslateBeanBaesInfoSymbolsParts
import com.example.eagleweb.kotlinonedemo.util.Constant
import com.example.eagleweb.kotlinonedemo.util.ListUtil
import com.example.eagleweb.shttplib.http.ErrorMessage
import com.example.eagleweb.shttplib.http.HttpClient
import com.example.eagleweb.shttplib.http.HttpDefaultCallback

class QueryActivity : Activity() {

    private lateinit var rv_result_list: RecyclerView
    private lateinit var edit_query: EditText
    private lateinit var btn_query: Button
    private lateinit var tv_result: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_translate)
        rv_result_list = findViewById<RecyclerView>(R.id.rv_result_list) as RecyclerView
        edit_query = findViewById<EditText>(R.id.edit_query) as EditText
        btn_query = findViewById<Button>(R.id.btn_query) as Button
        tv_result = findViewById<TextView>(R.id.tv_result) as TextView

    }

    fun oneday(v: View): Unit {
        startActivity(Intent(this, OneDayEnglishActivity::class.java))
    }

    fun translate(v: View): Unit {
        if (!TextUtils.isEmpty(edit_query.text)) {
            tv_result.visibility = View.VISIBLE
            rv_result_list.visibility = View.GONE


            val hashMap = HashMap<String, String>()
            hashMap.put("a", "fy")
            hashMap.put("f", "auto")
            hashMap.put("t", "auto")
            hashMap.put("w", edit_query.text.toString())
            HttpClient.getInstance().get(Constant.TRANSLATE_URL
                    , hashMap
                    , null
                    , object : HttpDefaultCallback<String>() {
                override fun success(bean: String) {
                    tv_result.text = bean
                }

                override fun failed(errorMessage: ErrorMessage) {
                    toast("不好意思，没有查询到哦：" + errorMessage.msg)
                    tv_result.text = ""
                }

            })

//            val hashMap = HashMap<String, String>()
//            hashMap.put("a", "fy")
//            hashMap.put("f", "auto")
//            hashMap.put("t", "auto")
//            hashMap.put("w", edit_query.text.toString())
//            HttpClient.getInstance().get(Constant.TRANSLATE_URL
//                    , hashMap
//                    , TranslateBean::class.java
//                    , object : HttpDefaultCallback<TranslateBean>() {
//                override fun success(bean: TranslateBean) {
//                    tv_result.text = bean.content.out
//                }
//
//                override fun failed(errorMessage: ErrorMessage) {
//                    toast("不好意思，没有查询到哦：" + errorMessage.msg)
//                    tv_result.text = ""
//                }
//
//            })
        }
    }

    fun query(v: View): Unit {
        if (!TextUtils.isEmpty(edit_query.text)) {

            tv_result.visibility = View.GONE
            rv_result_list.visibility = View.VISIBLE

            val hashMap = HashMap<String, String>()
            hashMap.put("a", "getWordMean")
            hashMap.put("c", "search")
            hashMap.put("list", "1")
            hashMap.put("word", edit_query.text.toString())
            HttpClient.getInstance().get(Constant.QUERY_URL
                    , hashMap
                    , QueryBean::class.java
                    , object : HttpDefaultCallback<QueryBean>() {
                override fun success(bean: QueryBean) {
                    for (symbol in bean.baesInfo.symbols) {
                        for (part in symbol.parts) {
                            for (mean in part.means) {
                                for (c in mean) {
                                    println("查询结果：" + c)
                                }
                            }
                        }
                    }
                    val adapter = QueryResultAdapter(this@QueryActivity, ListUtil.asList(*bean.baesInfo.symbols[0].parts))
                    rv_result_list.layoutManager = LinearLayoutManager(this@QueryActivity)
                    rv_result_list.adapter = adapter

//                    for (symbols in bean.baesInfo.symbols) {
//                        for (parts in symbols.parts) {
//                            for (s in parts.means) {
//                                println("查询完毕：" + s)
//                            }
//                        }
//                    }
                }

                override fun failed(errorMessage: ErrorMessage) {
                    toast("不好意思，没有查询到哦：" + errorMessage.msg)
                    val adapter = QueryResultAdapter(this@QueryActivity, ArrayList<TranslateBeanBaesInfoSymbolsParts>())
                    rv_result_list.adapter = adapter
                }

            })
        }
    }

    fun toast(s: String): Unit {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }

    fun toast(): Unit {
        Toast.makeText(this, "没有参数", Toast.LENGTH_SHORT).show()
    }

    override fun toString(): String {
        return super.toString()
    }
}
