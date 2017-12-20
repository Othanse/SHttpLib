package com.example.eagleweb.shttplib.http;

import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.util.Map;
import java.util.Set;

/**
 * Created by 菜鹰帅帅 on 2017/2/6.
 * 给网络请求做缓存的专用类
 */

public class HttpCache {

    private static ACache httpCache1;
    private static String cacheId;
    private static String uid = "eagle";
    private String mPath = Environment.getExternalStorageDirectory() + File.separator + "com.eagleweb.http";

    private HttpCache() {

    }

    private static final HttpCache httpCache = new HttpCache();

    /**
     * 设置用户
     *
     * @param user
     */
    public void setUser(String user) {
        if (TextUtils.isEmpty(user)) {
            uid = "eagle";
        } else {
            uid = user;
        }
    }

    /**
     * 设置缓存路径
     *
     * @param path
     */
    public void setCachePath(String path) {
        if (TextUtils.isEmpty(path)) {
            mPath = Environment.getExternalStorageDirectory() + "com.eagleweb";
        } else {
            mPath = path;
        }
    }

    /**
     * 获取缓存对象
     *
     * @return 缓存对象
     */
    public static HttpCache getInstance() {
        return httpCache;
    }

    public String getCache(String url, Map<String, String> map) {
        // 获取缓存
        if (cacheId != null) {
            if (!cacheId.equals(uid)) {
                httpCache1 = null;
            }
        }
        String asString = "";
        try {
            String prama = "";
            if (map != null) {
                try {
                    Set<Map.Entry<String, String>> entries = map.entrySet();
                    for (Map.Entry<String, String> entry : entries) {
                        String key = entry.getKey();
                        String value = entry.getValue();
                        prama += key + "=" + value + "&";
                    }
                    prama = prama.substring(0, prama.length() - 1);
                } catch (Exception e) {
                    //                    e.printStackTrace();
                }
            }
            if (httpCache1 == null) {
                if (!TextUtils.isEmpty(uid)) {
                    httpCache1 = ACache.get(new File(mPath + File.separator + "cache" + File.separator + uid));
                    cacheId = uid;
                }
            }
            if (httpCache1 != null) {
                asString = httpCache1.getAsString(url + "?" + prama);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return asString;
    }

    public void saveData(String url, Map<String, String> map, String string) {
        // 根据url和map 将string存储起来
        if (cacheId != null) {
            if (!cacheId.equals(uid)) {
                httpCache1 = null;
            }
        }
        try {
            String prama = "";
            if (map != null) {
                try {
                    Set<Map.Entry<String, String>> entries = map.entrySet();
                    for (Map.Entry<String, String> entry : entries) {
                        String key = entry.getKey();
                        String value = entry.getValue();
                        prama += key + "=" + value + "&";
                    }
                    prama = prama.substring(0, prama.length() - 1);
                } catch (Exception e) {
                    //                    e.printStackTrace();
                }
            }
            if (httpCache1 == null) {
                if (!TextUtils.isEmpty(uid)) {
                    httpCache1 = ACache.get(new File(mPath + File.separator + "cache" + File.separator + uid));
                    cacheId = uid;
                }
            }
            if (httpCache1 != null) {
                httpCache1.put(url + "?" + prama, string);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cleanAll() {
        if (httpCache1 != null) {
            httpCache1.clear();
        }
    }
}
