package com.example.eagleweb.shttplib.http;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

/**
 * Created by 菜鹰帅帅 on 2016/8/31.
 * 这是~
 */
public class HttpClient {
    private static HttpClient mHttpClient = new HttpClient();
    private List<Interceptor> interceptors;
    private String baseUrl        = "http://github.eagleweb.xyz";
    private String cacheUrl       = "";
    private String user           = "";
    private int    connectTimeout = 10;
    private int    readTimeout    = 10;
    private int    writeTimeout   = 10;

    private HttpClient() {
    }

    public static HttpClient getInstance() {
        return mHttpClient;
    }

    /**
     * 设置拦截器
     *
     * @param ins 拦截器集合，可以设置多个拦截器，网络请求拦截器，日志打印拦截器等。
     */
    public HttpClient setInterceptors(List<Interceptor> ins) {
        interceptors = ins;
        return this;
    }

    public List<Interceptor> getInterceptors() {
        return interceptors;
    }

    public HttpClient addInterceptor(Interceptor interceptor) {
        if (interceptors != null) {
            interceptors.add(interceptor);
        } else {
            interceptors = new ArrayList<>();
            interceptors.add(interceptor);
        }
        return this;
    }

    /**
     * 设置访问根路径，可以不设置，每次请求都传入完成访问路径即可
     *
     * @param baseUrl
     * @return
     */
    public HttpClient setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    /**
     * 设置连接超时时间
     *
     * @param connectTimeout 超时时间 单位：秒
     * @return
     */
    public HttpClient setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    /**
     * 设置读取超时时间
     *
     * @param readTimeout 超时时间 单位：秒
     * @return
     */
    public HttpClient setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
        return this;
    }

    public int getWriteTimeout() {
        return writeTimeout;
    }

    /**
     * 设置写入超时时间
     *
     * @param writeTimeout 超时时间 单位：秒
     * @return
     */
    public HttpClient setWriteTimeout(int writeTimeout) {
        this.writeTimeout = writeTimeout;
        return this;
    }

    public String getCacheUrl() {
        return cacheUrl;
    }

    /**
     * 设置缓存路径
     *
     * @param cacheUrl 缓存路径，只需要文件夹根路径就好，后面会自动添加cache文件夹左右缓存目录
     * @return
     */
    public HttpClient setCachePath(String cacheUrl) {
        this.cacheUrl = cacheUrl;
        HttpCache.getInstance().setCachePath(cacheUrl);
        return this;
    }

    public String getUser() {
        return user;
    }

    /**
     * 设置用户唯一标识，如果想要对不同的用户做不同的缓存，可以在更换用户的地方设置新用户的唯一标识，当用户切换回之前用户时，会自动读取之前用户的缓存
     *
     * @param user 用户唯一标识
     * @return
     */
    public HttpClient setUser(String user) {
        this.user = user;
        HttpCache.getInstance().setUser(user);
        return this;
    }

    /**
     * post访问一个URL
     * (不需要请求参数，也不关心请求结果的)
     *
     * @param url 要访问的URL
     * @param <T>
     */
    public <T> void post(String url) {
        HttpDefaultRepository<T> httpDefaultRepository = new HttpDefaultRepository<>();
        httpDefaultRepository.post(url, null, EmptyBean.class, false, null);
    }

    /**
     * post访问一个URL
     * （自己处理返回结果的，传入HttpDefaultCallback时，将泛型设置为String就好，例如：HttpDefaultCallback<String>(){}）
     *
     * @param url      要请求的URL
     * @param callback 回调
     * @param <T>
     */
    public <T> void post(String url, HttpDefaultCallback<T> callback) {
        HttpDefaultRepository<T> httpDefaultRepository = new HttpDefaultRepository<>();
        httpDefaultRepository.post(url, null, null, false, callback);
    }

    /**
     * post访问一个URL
     * （需要携带请求参数，但是不需要关心结果的）
     *
     * @param url 要请求的URL
     * @param map 请求参数的键值对map
     * @param <T>
     */
    public <T> void post(String url, Map<String, String> map) {
        HttpDefaultRepository<T> httpDefaultRepository = new HttpDefaultRepository<>();
        httpDefaultRepository.post(url, map, null, false, null);
    }

    /**
     * post访问一个URL
     * （不需要携带参数）
     *
     * @param url      要请求的URL
     * @param clazz    请求结果返回类型的class
     * @param callback 请求结果回调
     * @param <T>
     */
    public <T> void post(String url, Class clazz, HttpDefaultCallback<T> callback) {
        HttpDefaultRepository<T> httpDefaultRepository = new HttpDefaultRepository<>();
        httpDefaultRepository.post(url, null, clazz, false, callback);
    }

    /**
     * post访问一个URL
     *
     * @param url      要请求的URL
     * @param clazz    请求结果返回类型的class
     * @param isCache  是否缓存（如果设置为ture，可以复写HttpDefaultCallback的cache方法和noCache方法，在有缓存的时候，会将缓存通过cache方法返回，不会在success方法返回。如果没有缓存，将会回调noCache方法）
     * @param callback 请求结果回调
     * @param <T>
     */
    public <T> void post(String url, Class clazz, boolean isCache, HttpDefaultCallback<T> callback) {
        HttpDefaultRepository<T> httpDefaultRepository = new HttpDefaultRepository<>();
        httpDefaultRepository.post(url, null, clazz, isCache, callback);
    }

    /**
     * post访问一个URL
     *
     * @param url      要请求的URL
     * @param map      请求参数的键值对map
     * @param clazz    请求结果返回类型的class
     * @param callback 请求结果回调
     * @param <T>
     */
    public <T> void post(String url, Map<String, String> map, Class clazz, HttpDefaultCallback<T> callback) {
        HttpDefaultRepository<T> httpDefaultRepository = new HttpDefaultRepository<>();
        httpDefaultRepository.post(url, map, clazz, false, callback);
    }

    /**
     * post访问一个URL
     *
     * @param url      要请求的URL
     * @param map      请求参数的键值对map
     * @param clazz    请求结果返回类型的class
     * @param isCache  是否缓存（如果设置为ture，可以复写HttpDefaultCallback的cache方法和noCache方法，在有缓存的时候，会将缓存通过cache方法返回，不会在success方法返回。如果没有缓存，将会回调noCache方法）
     * @param callback 请求结果回调
     * @param <T>
     */
    public <T> void post(String url, Map<String, String> map, Class clazz, boolean isCache, HttpDefaultCallback<T> callback) {
        HttpDefaultRepository<T> httpDefaultRepository = new HttpDefaultRepository<>();
        httpDefaultRepository.post(url, map, clazz, isCache, callback);
    }


    //    ====================================================================================================

    /**
     * get访问一个URL
     * (不需要请求参数，也不关心请求结果的)
     *
     * @param url 要访问的URL
     * @param <T>
     */
    public <T> void get(String url) {
        HttpDefaultRepository<T> httpDefaultRepository = new HttpDefaultRepository<>();
        httpDefaultRepository.get(url, null, EmptyBean.class, false, null);
    }

    /**
     * get访问一个URL
     * （自己处理返回结果的，传入HttpDefaultCallback时，将泛型设置为String就好，例如：HttpDefaultCallback<String>(){}）
     *
     * @param url      要请求的URL
     * @param callback 回调
     * @param <T>
     */
    public <T> void get(String url, HttpDefaultCallback<T> callback) {
        HttpDefaultRepository<T> httpDefaultRepository = new HttpDefaultRepository<>();
        httpDefaultRepository.get(url, null, null, false, callback);
    }

    /**
     * get访问一个URL
     * （需要携带请求参数，但是不需要关心结果的）
     *
     * @param url 要请求的URL
     * @param map 请求参数的键值对map
     * @param <T>
     */
    public <T> void get(String url, Map<String, String> map) {
        HttpDefaultRepository<T> httpDefaultRepository = new HttpDefaultRepository<>();
        httpDefaultRepository.post(url, map, null, false, null);
    }

    /**
     * get访问一个URL
     * （不需要携带参数）
     *
     * @param url      要请求的URL
     * @param clazz    请求结果返回类型的class
     * @param callback 请求结果回调
     * @param <T>
     */
    public <T> void get(String url, Class clazz, HttpDefaultCallback<T> callback) {
        HttpDefaultRepository<T> httpDefaultRepository = new HttpDefaultRepository<>();
        httpDefaultRepository.get(url, null, clazz, false, callback);
    }

    /**
     * get访问一个URL
     *
     * @param url      要请求的URL
     * @param clazz    请求结果返回类型的class
     * @param isCache  是否缓存（如果设置为ture，可以复写HttpDefaultCallback的cache方法和noCache方法，在有缓存的时候，会将缓存通过cache方法返回，不会在success方法返回。如果没有缓存，将会回调noCache方法）
     * @param callback 请求结果回调
     * @param <T>
     */
    public <T> void get(String url, Class clazz, boolean isCache, HttpDefaultCallback<T> callback) {
        HttpDefaultRepository<T> httpDefaultRepository = new HttpDefaultRepository<>();
        httpDefaultRepository.get(url, null, clazz, isCache, callback);
    }

    /**
     * get访问一个URL
     *
     * @param url      要请求的URL
     * @param map      请求参数的键值对map
     * @param clazz    请求结果返回类型的class
     * @param callback 请求结果回调
     * @param <T>
     */
    public <T> void get(String url, Map<String, String> map, Class clazz, HttpDefaultCallback<T> callback) {
        HttpDefaultRepository<T> httpDefaultRepository = new HttpDefaultRepository<>();
        httpDefaultRepository.get(url, map, clazz, false, callback);
    }

    /**
     * get访问一个URL
     *
     * @param url      要请求的URL
     * @param map      请求参数的键值对map
     * @param clazz    请求结果返回类型的class
     * @param isCache  是否缓存（如果设置为ture，可以复写HttpDefaultCallback的cache方法和noCache方法，在有缓存的时候，会将缓存通过cache方法返回，不会在success方法返回。如果没有缓存，将会回调noCache方法）
     * @param callback 请求结果回调
     * @param <T>
     */
    public <T> void get(String url, Map<String, String> map, Class clazz, boolean isCache, HttpDefaultCallback<T> callback) {
        HttpDefaultRepository<T> httpDefaultRepository = new HttpDefaultRepository<>();
        httpDefaultRepository.get(url, map, clazz, isCache, callback);
    }


    // ==============================================================

    /**
     * 获取OkHttp网络请求对象,一般使用者不需要关心此方法
     *
     * @return
     */
    OkHttpClient defaultOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                .writeTimeout(writeTimeout, TimeUnit.SECONDS)
                .readTimeout(readTimeout, TimeUnit.SECONDS);
        if (interceptors != null) {
            for (Interceptor interceptor1 : interceptors) {
                builder.addInterceptor(interceptor1);
            }
        }
        return builder.build();
    }


    //    private final static String[] keys     = {"token", "imei", "uid", "packagename", "appname", "versioncode", "versionname", "accounttype", "rand_code", "app_sig_md5", "umeng_channel", "devicetype", "phone_brand", "phone_model", "android_level", "android_version", "mac"};
    //    //    private final static String[] keys = {"abaaa", "abbs", "abc", "abdefghijka", "qwertyu", "zxcvbnmasdf", "asdffghjkla", "poiuytreeew", "lkjhgggff", "mnvbcxcvxcs"};
    //    private static final char[]   HEX_CHAR = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    //
    //    public static String getSignature() {
    //        Context context = MyApplication.application;
    //        String pkName = context.getPackageName();
    //        PackageManager manager = context.getPackageManager();
    //        if (TextUtils.isEmpty(pkName)) {
    //            return "";
    //        } else {
    //            try {
    //                PackageInfo packageInfo = manager.getPackageInfo(pkName, PackageManager.GET_SIGNATURES);
    //                String result = getSignatureDigest(packageInfo);
    //                return result.toLowerCase();
    //            } catch (PackageManager.NameNotFoundException var4) {
    //                var4.printStackTrace();
    //            }
    //        }
    //        return "";
    //    }
    //
    //    public static String getSignatureDigest(PackageInfo pkgInfo) {
    //        int length = pkgInfo.signatures.length;
    //        if (length <= 0) {
    //            return "";
    //        } else {
    //            Signature signature = pkgInfo.signatures[0];
    //            MessageDigest md5 = null;
    //
    //            try {
    //                md5 = MessageDigest.getInstance("MD5");
    //            } catch (NoSuchAlgorithmException var5) {
    //                var5.printStackTrace();
    //            }
    //            byte[] digest = md5.digest(signature.toByteArray());
    //            return toHexString(digest);
    //        }
    //    }
    //
    //    @NonNull
    //    private static List<String> getValues() {
    //        List<String> values = new ArrayList<>();
    //        MyApplication application = MyApplication.application;
    //        Random random = new Random();
    //        int randomInt = random.nextInt(100000);
    //        String imei = DeviceInfoUtil.getImei();
    //        String uid = DeviceInfoUtil.getUid();
    //        if (TextUtils.isEmpty(uid)) {
    //            uid = "0";
    //        }
    //        String token = MD5Utils.encode(MD5Utils.encode(imei + randomInt + "facing") + uid + "facing");  // md5(md5(imei+rand_code)+imei);
    //        String packageName = "";
    //        String appName = "";
    //        String versionCode = "";
    //        String versionName = "";
    //        try {
    //            packageName = application.getPackageName();
    //            PackageInfo packageInfo = application.getPackageManager().getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
    //            appName = application.getResources().getString(R.string.app_name);
    //            versionCode = packageInfo.versionCode + "";
    //            versionName = packageInfo.versionName + "";
    //            //
    //        } catch (Exception e) {
    //            e.printStackTrace();
    //        }
    //        String accessToken = SharePrefUtil.getString(application, SharePrefUtil.ACCESS_TOKEN, "");
    //        if (!TextUtils.isEmpty(accessToken) && accessToken.length() > 5) {
    //            accessToken = accessToken.substring(5, accessToken.length());   // token验证，需要将前五位去掉，然后在给服务端
    //        }
    //        //                if (LogUtil.isLog()) LogUtil.s("底层传输数据的集合的长度1：" + values.size() + " data:" + values);
    //        values.add("eagle");//用来占位
    //        values.set(0, accessToken);//token
    //        values.add("eagle");//用来占位
    //        values.set(1, imei);//imei
    //        values.add("eagle");//用来占位
    //        values.set(2, uid);//uid
    //        values.add("eagle");//用来占位
    //        values.set(3, packageName);//包名
    //        values.add("eagle");//用来占位
    //        values.set(4, appName);//appName
    //        values.add("eagle");//用来占位
    //        values.set(5, versionCode);// 版本号
    //        values.add("eagle");//用来占位
    //        values.set(6, versionName);//版本名称
    //        values.add("eagle");//用来占位
    //        values.set(7, versionName);//账户类型（没有规定，可询问后台字段用处）
    //        values.add("eagle");//用来占位
    //        values.set(8, randomInt + "");// 随机数
    //        values.add("eagle");//用来占位
    //        values.set(9, getSignature());// apk签名
    //        values.add("eagle");//用来占位
    //        values.set(10, PushAgent.getInstance(MyApplication.application).getMessageChannel());//友盟渠道
    //        values.add("eagle");//用来占位
    //        values.set(11, "0");//设备类型 0是android 2是ios
    //        values.add("eagle");//用来占位
    //        values.set(12, DeviceInfoUtil.getBRAND());//手机品牌
    //        values.add("eagle");//用来占位
    //        values.set(13, DeviceInfoUtil.getPhoneModel());//手机型号
    //        values.add("eagle");//用来占位
    //        values.set(14, DeviceInfoUtil.getBuildLevel() + "");//系统api
    //        values.add("eagle");//用来占位
    //        values.set(15, DeviceInfoUtil.getOsVersion());//系统版本
    //        values.add("eagle");//用来占位
    //        values.set(16, DeviceInfoUtil.getWlanMacAddress());//mac地址
    //        return values;
    //    }
    //
    //    public static String getPostParams() {
    //        List<String> values = getValues();
    //        StringBuilder stringBuilder = new StringBuilder();
    //        for (int i = 0; i < keys.length; i++) {
    //            stringBuilder.append(keys[i]).append("=").append(values.get(i))
    //                    .append("&");
    //        }
    //        if (stringBuilder.length() > 0) {
    //            stringBuilder.deleteCharAt(stringBuilder.lastIndexOf("&"));
    //        }
    //        if (LogUtil.isLog())
    //            LogUtil.s("base params==" + stringBuilder.toString());
    //        return stringBuilder.toString();
    //    }
    //
    //    private static String toHexString(byte[] rawByteArray) {
    //        char[] chars = new char[rawByteArray.length * 2];
    //        for (int i = 0; i < rawByteArray.length; ++i) {
    //            byte b = rawByteArray[i];
    //            chars[i * 2] = HEX_CHAR[b >>> 4 & 15];
    //            chars[i * 2 + 1] = HEX_CHAR[b & 15];
    //        }
    //        return new String(chars);
    //    }
    //
    //    /**
    //     * 这个是假的，千万不要当做正常的来使用，用来做安全混淆的
    //     *
    //     * @param rawByteArray
    //     * @return
    //     */
    //    private static String toHexString2(byte[] rawByteArray) {
    //        char[] chars = new char[rawByteArray.length * 2];
    //        for (int i = 0; i < rawByteArray.length; ++i) {
    //            byte b = rawByteArray[i];
    //            chars[i * 2] = HEX_CHAR[b >> 4 & 15];
    //            chars[i * 2 + 1] = HEX_CHAR[b & 15];
    //        }
    //        return new String(chars);
    //    }
    //
    //    /**
    //     * 这个是假的，千万不要当做正常的来使用，用来做安全混淆的
    //     *
    //     * @param rawByteArray
    //     * @return
    //     */
    //    private static String toHexString3(byte[] rawByteArray) {
    //        char[] chars = new char[rawByteArray.length * 2];
    //        for (int i = 0; i < rawByteArray.length; ++i) {
    //            byte b = rawByteArray[i];
    //            chars[i * 2] = HEX_CHAR[b & 15];
    //            chars[i * 2 + 1] = HEX_CHAR[b >> 4 & 15];
    //        }
    //        return new String(chars);
    //    }
    //
    //    /**
    //     * 这个是假的，千万不要当做正常的来使用，用来做安全混淆的
    //     *
    //     * @param rawByteArray
    //     * @return
    //     */
    //    private static String toHexString4(byte[] rawByteArray) {
    //        char[] chars = new char[rawByteArray.length * 2];
    //        for (int i = 0; i < rawByteArray.length; ++i) {
    //            byte b = rawByteArray[i];
    //            chars[i * 2] = HEX_CHAR[b & 15];
    //            chars[i * 2 + 1] = HEX_CHAR[b >>> 4 & 15];
    //        }
    //        return new String(chars);
    //    }


    // 这个拦截器给每个请求都添加POST参数mobileinfo
    //        Interceptor interceptors = new Interceptor() {
    //            @Override
    //            public Response intercept(Chain chain) throws IOException {
    //                Request request = chain.request();
    //                RequestBody body1 = request.body();
    //                Request.Builder builder = request.newBuilder();
    //                List<String> values = getValues();
    //                //                if (LogUtil.isLog()) LogUtil.s("底层传输数据的集合的长度 6：" + values.size() + " data:" + values);
    //                {
    //                    MediaType mediaType = body1.contentType();
    //                    if (LogUtil.isLog())
    //                        LogUtil.s("底层数据。请求的类型：" + mediaType);
    //                    if (mediaType.toString().contains("multipart/form-data")) {
    //                        if (LogUtil.isLog())
    //                            LogUtil.s("是文件上传类型的 咋添加参数啊~");
    //                        MultipartBody body = (MultipartBody) body1;
    //                        int size = body.size();
    //                        MultipartBody.Builder builder1 = new MultipartBody.Builder();
    //                        builder1.setType(MediaType.parse("multipart/form-data"));
    //                        for (int i = 0; i < size; i++) {
    //                            builder1.addPart(body.part(i));
    //                        }
    //                        for (int i = 0; i < keys.length; i++) {
    //                            builder1.addFormDataPart(keys[i], values.get(i));
    //                        }
    //                        builder.method(request.method(), builder1.build());
    //                    } else {
    //                        FormBody.Builder newBody = new FormBody.Builder();
    //                        // 要在所有URL添加的POST参数
    //                        if (body1 instanceof FormBody) {
    //                            FormBody body = (FormBody) body1;
    //                            for (int i = 0; i < body.size(); i++) {
    //                                newBody.add(body.name(i), body.value(i));
    //                            }
    //                        } else if (body1 == null || body1.contentLength() == 0) {
    //                            newBody = new FormBody.Builder();
    //                        } else {
    //                            if (LogUtil.isLog())
    //                                LogUtil.s(" 不是FormBody 也不是没有Body~");
    //                        }
    //                        for (int i = 0; i < keys.length; i++) {
    //                            newBody.add(keys[i], values.get(i));
    //                        }
    //                        builder.method(request.method(), newBody.build());
    //                    }
    //                }
    //                return chain.proceed(builder.build());
    //            }
    //        };
    // log打印拦截器
    //        MyHttpLoggingInterceptor loggingInterceptor = new MyHttpLoggingInterceptor(new MyHttpLoggingInterceptor.Logger() {
    //            @Override
    //            public void log(String message) {
    //                if (LogUtil.isLog())
    //                    LogUtil.s("  OkHttp网络请求日志：" + message);
    //            }
    //        }).setLevel(MyHttpLoggingInterceptor.Level.BODY);
}
