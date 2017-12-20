package com.example.eagleweb.kotlinonedemo.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.eagleweb.kotlinonedemo.R
import com.example.eagleweb.kotlinonedemo.bean.TranslateBeanBaesInfoSymbolsParts

/**
 * @创建者 帅子
 * @创建时间  17/9/23.
 * @描述
 */
class QueryResultAdapter(var context: Context, var list: List<TranslateBeanBaesInfoSymbolsParts>) : RecyclerView.Adapter<QueryResultAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val stringBuilder = StringBuilder()
        stringBuilder.append(list[position].part)
        stringBuilder.append("   ")
        for (mean in list[position].means) {
            stringBuilder.append(mean).append("，")
        }
        holder.tv_content.text = stringBuilder.toString()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = View.inflate(context, R.layout.translate_result_item, null)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        if (list == null) {
            return 0
        } else {
            return list.size
        }
    }

    class ViewHolder : RecyclerView.ViewHolder {
        lateinit var tv_content: TextView

        constructor(itemView: View?) : super(itemView) {
            initView()
        }

        fun initView() {
            tv_content = itemView.findViewById<TextView>(R.id.tv_content) as TextView
        }
    }
}