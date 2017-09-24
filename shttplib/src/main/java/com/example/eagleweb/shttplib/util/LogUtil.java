package com.example.eagleweb.shttplib.util;

import android.util.Log;

/**
 * @author 小段果果
 * @time 2016/6/13  16:19
 * @E-mail duanyikang@mumayi.com
 * update By 菜鹰帅帅 QQ:1992149090
 */

public class LogUtil {
    //    private static boolean isLog = true;
    private static boolean isLog = false;// TODO 正式包打包需要改的

    public static boolean isLog() {
        return isLog;
    }

    public static void setLog(boolean log) {
        isLog = log;
    }

    /**
     * 将log信息写入文件中 xxxx7777-day.log（目前是传到服务器去，直接在服务器分析）
     *
     * @param msg
     */
    public static void writeToFile(final String type, String msg) {
        //        s(type + "." + msg);
        //        msg = msg + "\n";
        //        final String finalMsg = msg;
        //        AsyncTask.execute(new Runnable() {
        //            @Override
        //            public void run() {
        //                LogRecordUtil.record(type, finalMsg);
        //            }
        //        });
        //        //        AsyncTask.execute(new Runnable() {
        //        //            @Override
        //        //            public void run() {
        //        //                String day = DateUtil.getDay(System.currentTimeMillis());
        //        //                File file = new File(Constants.FILE_BASE + "/xxxx7777-" + day + ".log"); // 有些手机不能直接打开log，为了方便调试，后缀更改为txt
        //        //                FileUtil.initPath();
        //        //
        //        //                String log = DateUtil.currentDatetime() + "  " + System.currentTimeMillis() + " :   " + msg + "\n";//log日志内容，可以自行定制
        //        //
        //        //                //                if (!file.exists()) {
        //        //                //                    file.mkdirs();//创建父路径
        //        //                //                }
        //        //
        //        //                FileOutputStream fos = null;//FileOutputStream会自动调用底层的close()方法，不用关闭
        //        //                BufferedWriter bw = null;
        //        //                try {
        //        //
        //        //                    fos = new FileOutputStream(file, true);//这里的第二个参数代表追加还是覆盖，true为追加，flase为覆盖
        //        //                    bw = new BufferedWriter(new OutputStreamWriter(fos));
        //        //                    bw.write(log);
        //        //
        //        //                } catch (Exception e) {
        //        //                    e.printStackTrace();
        //        //                } finally {
        //        //                    try {
        //        //                        if (bw != null) {
        //        //                            bw.close();//关闭缓冲流
        //        //                        }
        //        //                    } catch (Exception e) {
        //        //                        e.printStackTrace();
        //        //                    }
        //        //                }
        //        //            }
        //        //        });
    }


    public static void s(String s) {
        s(" 映社：", s);
    }

    public static void s(String TAG, String s) {
        if (isLog) {
            System.out.println(TAG + " " + s);
        }
    }

    public static void e(String s) {
        if (isLog) {
            System.err.println("映社： " + s);
        }
    }

    public static void e(Exception e) {
        if (isLog) {
            if (e != null) {
                System.err.println("映社：  已捕获异常：" + e.getMessage() + "\n" + e.toString());
            }
        }
    }

    public static void d(String TAG, String debug) {
        if (isLog) {
            Log.d(TAG, debug);
        }
    }

    public static void i(String TAG, String info) {
        if (isLog) {
            Log.i(TAG, info);
        }
    }

    public static void w(String TAG, String warn) {
        if (isLog) {
            Log.w(TAG, warn);
        }
    }

    public static void v(String TAG, String verbose) {
        if (isLog) {
            Log.v(TAG, verbose);
        }
    }

    public static void E(String TAG, String clzName, Throwable throwable) {
        if (isLog) {
            Log.e(TAG, clzName + " / " + throwable.getMessage(), throwable);
        }
    }
}

