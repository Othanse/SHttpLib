package com.example.eagleweb.shttplib.http;

/**
 * Created by 菜鹰帅帅 on 2017/2/16.
 * Http请求的默认Callback<br/>
 * success(T bean) ： 请求成功回调<br/>
 * cache(T bean)   ： 如果有缓存 并且isCache为true(如果isCache为false，此方法不回调)，会先将缓存返回，网络请求完成后会再调用success或者failed方法！<br/>
 * noCache()       ： 设置了isCache缓存为TRUE，但是没有缓存时，则会回调此方法<br/>
 * failed(ErrorMessage errorMessage) ： 请求数据失败，可能是网络原因或者是服务器原因<br/>
 */

public abstract class HttpDefaultCallback<T> {
    protected abstract void success(T bean);

    protected void cache(T bean) {

    }

    protected void noCache() {

    }

    protected abstract void failed(ErrorMessage errorMessage);
}
