package com.example.eagleweb.shttplib.http;

import android.os.Environment;
import android.support.annotation.NonNull;

import com.example.eagleweb.shttplib.util.LogUtil;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 菜鹰帅帅 on 2016/9/21.
 * 关于网络的默认底层API
 */

public class HttpDefaultRepository<T> {

    private boolean            isRefreshToken; // 是否刷新过token了
    private boolean            isRefreshVoucher;   // 是否刷新过票据了
    private Subscription       subscription;  // 请求对象，可以取消请求
    private Call<ResponseBody> mExecutePost;

    /**
     * 取消请求 绑定了activity 来自动取消
     */
    public void cancelRequest() {
        if (subscription != null) {
            subscription.unsubscribe();
        }
        if (mExecutePost != null) {
            mExecutePost.cancel();
        }
    }

    /**
     * 是否取消了请求
     *
     * @return
     */
    @Deprecated
    public boolean isCancelRequest() {
        if (subscription != null) {
            return subscription.isUnsubscribed();
        }
        return false;
    }


    /**
     * 普通的POST请求（异步）
     * update:2017-02-16
     *
     * @param url      完整的url
     * @param map      携带的参数 key-value的格式
     * @param clazz    返回对象的JavaBean类型
     * @param isCache  是否缓存 如果为true 则会先去缓存里取数据，在cache回调里返回缓存的数据，网络请求完成后会再次调用success或者failed方法！ 如果为false，cache不会调用！
     * @param callback 回调
     */
    public Subscription post(@NonNull final String url, Map<String, String> map, final Class clazz, final boolean isCache, final HttpDefaultCallback<T> callback) {
        try {
            if (isCache) {
                try {
                    // 需要先从缓存拿数据
                    String str = HttpCache.getInstance().getCache(url, map);
                    Gson gson = new Gson();
                    T bean = (T) gson.fromJson(str, clazz);
                    if (bean != null && callback != null) {
                        if (LogUtil.isLog())
                            LogUtil.s("成功从缓存获取到数据 form：" + url + "\n" + bean);
                        callback.cache(bean);
                    } else if (bean == null && callback != null) {
                        callback.noCache();
                    }
                } catch (Exception x) {
                    if (callback != null) {
                        callback.noCache();
                    }
                    x.printStackTrace();
                }
            }
            if (map == null) {
                map = new HashMap<>();
            }
            final Map<String, String> finalMap = map;
            //            try {
            //                // 如果存在多个对象，先取消上一次请求
            //                if (subscription != null) {
            //                    subscription.unsubscribe();
            //                }
            //            } catch (Exception e) {
            //            }
            subscription = InterfaceRetrofit.getRetrofit()
                    .create(HttpPostDefaultService.class)
                    .request(url, map)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ResponseBody>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            if (LogUtil.isLog())
                                LogUtil.s("在Repository这里 请求Error了:" + e.getMessage() + "\n  请求失败的url:" + url + " map:" + finalMap.toString() + "  clazz:" + clazz.getName());
                            e.printStackTrace();
                            if (callback != null) {
                                callback.failed(ErrorMessage.createByReqError(e));
                            }
                        }

                        @Override
                        public void onNext(ResponseBody body) {
                            if (LogUtil.isLog())
                                LogUtil.s("数据请求到了onNext方法了" + "\n  请求到Next的url:" + url);
                            try {
                                String string = body.string();

                                if (clazz == null || clazz == String.class) {
                                    if (callback != null) {
                                        T bean = (T) string;
                                        // 正确获取到数据
                                        callback.success(bean);
                                    }
                                } else {
                                    Gson gson = new Gson();
                                    T bean = (T) gson.fromJson(string, clazz);
                                    if (callback != null) {
                                        // 正确获取到数据
                                        callback.success(bean);
                                    }
                                }
                                if (isCache) {
                                    // 需要缓存
                                    HttpCache.getInstance().saveData(url, finalMap, string);
                                }
                            } catch (Exception e) {
                                if (callback != null) {
                                    callback.failed(ErrorMessage.create(e.toString(), ErrorMessage.ERROR_PARSE));
                                }
                                if (LogUtil.isLog())
                                    LogUtil.e(e);
                                e.printStackTrace();
                            }
                        }
                    });
            return subscription;
        } catch (Exception e) {
            e.printStackTrace();
            if (callback != null) {
                callback.failed(ErrorMessage.create("网络不给力，请检查网络或稍后重试", ErrorMessage.ERROR_NETWORK));
            }
        }
        return null;
    }


    /**
     * 普通的POST请求（同步） 因为是耗时操作 所以需要在子线程操作
     * 使用注意：此方法在使用时，需要已经通过验签，否则会请求失败的
     * update:2017-02-16
     *
     * @param url   完整的url
     * @param map   携带的参数 key-value的格式
     * @param clazz 返回对象的JavaBean类型
     * @return 返回传入的JavaBean类型(如果请求失败, 为null)
     */
    public T postExecute(@NonNull final String url, Map<String, String> map, final Class clazz) {
        try {
            // 怎么搞成子线程 还是同步返回的。。。
            if (map == null) {
                map = new HashMap<>();
            }
            mExecutePost = InterfaceRetrofit.getRetrofit()
                    .create(HttpPostExecuteDefaultService.class)
                    .request(url, map);
            Response<ResponseBody> execute = mExecutePost.execute();
            String string = execute.body().string();
            Gson gson = new Gson();
            final T bean = (T) gson.fromJson(string, clazz);
            return bean;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 普通的get请求
     *
     * @param url      完整的url
     * @param map      携带的参数 key-value的格式
     * @param clazz    返回对象的JavaBean
     * @param callback 回调
     */
    public void get(@NonNull final String url, Map<String, String> map, final Class clazz, final boolean isCache, final HttpDefaultCallback<T> callback) {
        try {
            if (isCache) {
                try {
                    // 需要先从缓存拿数据
                    String str = HttpCache.getInstance().getCache(url, map);
                    Gson gson = new Gson();
                    T bean = (T) gson.fromJson(str, clazz);
                    if (bean != null && callback != null) {
                        if (LogUtil.isLog())
                            LogUtil.s("成功从缓存获取到数据 form：" + url + "\n" + bean);
                        callback.cache(bean);
                    }
                } catch (Exception x) {
                    x.printStackTrace();
                }
            }
            if (map == null) {
                map = new HashMap<>();
            }
            final Map<String, String> finalMap = map;
            subscription = InterfaceRetrofit.getRetrofit()
                    .create(HttpGetDefaultService.class)
                    .request(url, map)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ResponseBody>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            if (callback != null) {
                                callback.failed(ErrorMessage.createByReqError(e));
                            }
                        }

                        @Override
                        public void onNext(ResponseBody body) {
                            try {
                                String string = body.string();
                                if (isCache) {
                                    // 需要缓存
                                    HttpCache.getInstance().saveData(url, finalMap, string);
                                }
                                if (clazz == null || clazz == String.class) {
                                    if (callback != null) {
                                        T bean = (T) string;
                                        // 正确获取到数据
                                        callback.success(bean);
                                    }
                                } else {
                                    Gson gson = new Gson();
                                    T bean = (T) gson.fromJson(string, clazz);
                                    if (callback != null) {
                                        // 正确获取到数据
                                        callback.success(bean);
                                    }
                                }
                            } catch (Exception e) {
                                if (callback != null) {
                                    callback.failed(ErrorMessage.createByReqError(e));
                                }
                                e.printStackTrace();
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
            if (callback != null) {
                callback.failed(ErrorMessage.createByNotNetwork(e));
            }
        }

    }

    /**
     * 上传多个文件使用的接口 （注意 暂时未完善  待完善 暂不可使用）
     *
     * @param url      完整url
     * @param map1     普通的参数 key-value
     * @param fileMap  文件参数 key-filePath
     * @param clazz    返回的对象Bean的class
     * @param callback 回调
     */
    private void uploadFiles(String url, Map<String, String> map1, Map<String, String> fileMap, final Class clazz, final HttpDefaultCallback callback) {
        try {
            HashMap<String, RequestBody> requestBodyHashMap = new HashMap<>();
            HashMap<String, MultipartBody.Part> requestPartHashMap = new HashMap<>();
            if (map1 != null) {
                Set<String> strings = map1.keySet();
                for (String string : strings) {
                    RequestBody b = RequestBody.create(MediaType.parse("multipart/form-data"), map1.get(string));
                    requestBodyHashMap.put(string, b);
                }
            }
            if (fileMap != null) {
                Set<String> strings = fileMap.keySet();
                for (String string : strings) {
                    File file1 = new File(fileMap.get(string));
                    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file1);
                    MultipartBody.Part formData = MultipartBody.Part.createFormData(string, file1.getName(), requestFile);
                    requestPartHashMap.put(string, formData);
                }
            }
            subscription = InterfaceRetrofit.getRetrofit()
                    .create(HttpPostUploadFileService.class)
                    .uploadFiles(url, requestBodyHashMap, requestPartHashMap)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ResponseBody>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            if (callback != null) {
                                callback.failed(ErrorMessage.createByReqError(e));
                            }
                        }

                        @Override
                        public void onNext(ResponseBody body) {
                            try {
                                String string = body.string();

                                if (clazz == null || clazz == String.class) {
                                    if (callback != null) {
                                        T bean = (T) string;
                                        // 正确获取到数据
                                        callback.success(bean);
                                    }
                                } else {
                                    Gson gson = new Gson();
                                    T bean = (T) gson.fromJson(string, clazz);
                                    if (callback != null) {
                                        // 正确获取到数据
                                        callback.success(bean);
                                    }
                                }
                            } catch (Exception e) {
                                if (callback != null) {
                                    callback.failed(ErrorMessage.createByReqError(e));
                                }
                                e.printStackTrace();
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
            callback.failed(ErrorMessage.createByNotNetwork(e));
        }

    }

    /**
     * 上传文件使用的接口
     *
     * @param url      完整url
     * @param filePath 文件path
     * @param map1     携带的参数
     * @param clazz    返回的javabean
     * @param callback 回调
     */
    public void uploadFile(@NonNull String url, @NonNull String filePath, Map<String, String> map1, final Class clazz, final HttpDefaultCallback callback) {
        try {

            HashMap<String, RequestBody> requestBodyHashMap = new HashMap<>();
            HashMap<String, RequestBody> requestPartHashMap = new HashMap<>();
            if (map1 != null) {
                Set<String> strings = map1.keySet();
                for (String string : strings) {
                    RequestBody b = RequestBody.create(MediaType.parse("multipart/form-data"), map1.get(string));
                    requestBodyHashMap.put(string, b);
                }
            }

            //            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), "this is file description");

            File file1 = new File(filePath);
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file1);
            subscription = InterfaceRetrofit.getRetrofit()
                    .create(HttpPostUploadFileService.class)
                    .uploadFile(url, MultipartBody.Part.createFormData("file", file1.getName(), requestFile), requestBodyHashMap)
                    //                    .uploadFiles(url, requestBodyHashMap, )
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ResponseBody>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            if (callback != null) {
                                callback.failed(ErrorMessage.createByReqError(e));
                            }
                        }

                        @Override
                        public void onNext(ResponseBody body) {
                            try {
                                String string = body.string();
                                if (clazz == null || clazz == String.class) {
                                    if (callback != null) {
                                        T bean = (T) string;
                                        // 正确获取到数据
                                        callback.success(bean);
                                    }
                                } else {
                                    Gson gson = new Gson();
                                    T bean = (T) gson.fromJson(string, clazz);
                                    if (callback != null) {
                                        // 正确获取到数据
                                        callback.success(bean);
                                    }
                                }
                            } catch (Exception e) {
                                if (callback != null) {
                                    callback.failed(ErrorMessage.createByReqError(e));
                                }
                                e.printStackTrace();
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
            callback.failed(ErrorMessage.createByNotNetwork(e));
        }
    }

    public void download(@NonNull final String url, String outPath, Map<String, String> map, final DownloadListener listener) {
        try {
            InterfaceRetrofit.getRetrofit()
                    .create(DownloadService.class)
                    .download(url, map)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ResponseBody>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            if (LogUtil.isLog())
                                LogUtil.s("  下载文件  失败了~ 走了onError：" + e.getMessage() + "  url:" + url);
                            if (listener != null) {
                                listener.failed(ErrorMessage.createByReqError(e));
                            }
                        }

                        @Override
                        public void onNext(ResponseBody responseBody) {
                            //                            if (LogUtil.isLog()) LogUtil.s("  下载文件 成功！: url:" + url);
                            //                            if (responseBody != null) {
                            //                                InputStream inputStream = responseBody.byteStream();
                            //                            }else{
                            //                                if (listener != null) {
                            //                                    listener.failed(ErrorMessage.create("服务端返回为空！"));
                            //                                }
                            //                            }
                            InputStream is = null;
                            byte[] buf = new byte[2048];
                            int len = 0;
                            FileOutputStream fos = null;
                            String SDPath = Environment.getExternalStorageDirectory().getAbsolutePath();
                            try {
                                is = responseBody.byteStream();
                                long total = responseBody.contentLength();
                                File file = new File(SDPath, "test.log");
                                fos = new FileOutputStream(file);
                                long sum = 0;
                                while ((len = is.read(buf)) != -1) {
                                    fos.write(buf, 0, len);
                                    sum += len;
                                }
                                fos.flush();

                            } catch (Exception e) {

                            } finally {
                                try {
                                    if (is != null)
                                        is.close();
                                } catch (IOException e) {
                                }
                                try {
                                    if (fos != null)
                                        fos.close();
                                } catch (IOException e) {
                                }
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
            if (listener != null) {
                listener.failed(ErrorMessage.createByNotNetwork(e));
            }
        }
    }

    interface DownloadListener {
        void process(int process, long currentSize, long totalSize);

        void success();

        void failed(ErrorMessage errorMessage);
    }

}
