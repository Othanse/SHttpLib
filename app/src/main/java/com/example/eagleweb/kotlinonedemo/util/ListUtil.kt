package com.example.eagleweb.kotlinonedemo.util

import java.util.*

/**
 * @创建者 帅子
 * @创建时间  17/9/23.
 * @描述
 */
object ListUtil {
    @SafeVarargs
    fun <T> asList(vararg a: T): List<T> {
        val ts = ArrayList<T>()
        if (a != null && a.size > 0) {
            Collections.addAll(ts, *a)
        }
        return ts
    }

    fun isEmpty(list: Collection<*>?): Boolean {
        return list == null || list.isEmpty()
    }
}