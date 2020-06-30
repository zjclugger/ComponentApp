package com.zjclugger.lib.utils;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import androidx.annotation.ArrayRes;
import androidx.annotation.StringRes;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 共用工具类<br>
 * Created by King.Zi on 2016/10/8.<br>
 */
public class CommonUtils {
    private static final String TAG = "LibUtils";
    public static final SimpleDateFormat DATETIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd " +
            "HH:mm:ss");
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private CommonUtils() {
    }

    /**
     * 返回字符串的整数类型,非整数或为空则返回最小值
     *
     * @param value
     * @return
     */
    public static int getIntValue(String value) {
        if (TextUtils.isEmpty(value)) {
            return invalidIntValue();
        }
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return invalidIntValue();
        }
    }

    public static String getString(String value, String defaultText) {
        return !TextUtils.isEmpty(value) ? value : defaultText;
    }

    public static String getString(Context context, @StringRes int resId, Object... value) {
        return String.format(context.getString(resId), value);
    }

    public static String formatNumber(double value) {
        return String.format("%.2f", value);
    }
    /**
     * 截取字符串
     *
     * @param text      字符串
     * @param length    长度
     * @param encode    GBK
     * @param fromRight 是否从右边开始
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String subString(String text, int length, String encode, boolean fromRight)
            throws UnsupportedEncodingException {
        if (TextUtils.isEmpty(text)) {
            return "";
        }
        String value = "";
        int currentLength = 0;
        char[] textArray = text.toCharArray();
        if (fromRight) {
            for (int i = textArray.length - 1; i >= 0; i--) {
                currentLength += String.valueOf(textArray[i]).getBytes(encode).length;
                if (currentLength <= length) {
                    value = textArray[i] + value;
                } else {
                    break;
                }
            }
        } else {
            for (char c : textArray) {
                currentLength += String.valueOf(c).getBytes(encode).length;
                if (currentLength <= length) {
                    value = value + c;
                } else {
                    break;
                }
            }
        }
        Log.d("KING", "value=" + value);
        return value;
    }

    /**
     * 获取年月日
     *
     * @param date
     * @return
     */
    public static String getDate(String date) {
        if (!TextUtils.isEmpty(date) && date.length() > 10) {
            return date.substring(0, 10);
        }
        return "";
    }

    public static String getDate(String date, String defaultNullString) {
        if (!TextUtils.isEmpty(date) && date.length() > 9) {
            return date.substring(0, 10);
        }
        return defaultNullString;
    }

    /**
     * 获取日期时间
     *
     * @param date
     * @param format 如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getDateTime(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    public static String getDouble(double value) {
        return String.valueOf(value);
    }

    public static String getDouble(double value, String pattern) {
        //"##0.0"
        if (TextUtils.isEmpty(pattern)) {
            pattern = "##0.0";
        }

        DecimalFormat df = new DecimalFormat(pattern);
        return df.format(value);
    }

    public static Double getDoubleValue(String value) {
        if (TextUtils.isEmpty(value)) {
            return 0d;
        }
        try {
            return Double.parseDouble(value);
        } catch (Exception e) {
            return 0d;
        }
    }

    public static Float getFloatValue(String value) {
        if (TextUtils.isEmpty(value)) {
            return 0f;
        }
        try {
            return Float.parseFloat(value);
        } catch (Exception e) {
            return 0f;
        }
    }

    public static String getFloat(float value, String pattern, String suffix) {
        //"##0.0"
        if (TextUtils.isEmpty(pattern)) {
            pattern = "##0.00";
        }

        if (TextUtils.isEmpty(suffix)) {
            suffix = "";
        }

        DecimalFormat df = new DecimalFormat(pattern);
        return df.format(value) + suffix;
    }

    public static String getPercentage(double value, String pattern) {
        //"##0.0"
        if (TextUtils.isEmpty(pattern)) {
            pattern = "##0.00";
        }

        DecimalFormat df = new DecimalFormat(pattern);
        return df.format(value * 100) + "%";
    }


    public static int invalidIntValue() {
        return Integer.MIN_VALUE;
    }

    public static boolean isIP(String str) {
        Pattern pattern = Pattern.compile("^((\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]|[*])\\.){3}" +
                "(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]|[*])$");
        return pattern.matcher(str).matches();
    }

    public static boolean isPort(int port) {
        return 0 < port && 65536 > port;
    }

    public static boolean isMask(String mask) {
        Pattern pattern = Pattern.compile("(^((\\d|[01]?\\d\\d|2[0-4]\\d|25[0-5])\\.){3}" +
                "(\\d|[01]?\\d\\d|2[0-4]\\d|25[0-5])$)|^(\\d|[1-2]\\d|3[0-2])$");
        return pattern.matcher(mask).matches();
    }

    public static JSONObject parseJson(String jsonString) {
        if (null == jsonString) {
            return null;
        }
        try {
            return new JSONObject(jsonString);
        } catch (JSONException e) {
            return null;
        }
    }

    public static <T> T json2Object(String input, Class<T> clazz) {
        try {
            return new Gson().fromJson(input, clazz);
        } catch (JsonSyntaxException e) {
            Log.d(TAG, "exception is " + e.getMessage());
        }

        return null;
    }

    public static String object2Json(Object instance) {
        if (null != instance) {
            return new Gson().toJson(instance);
        }

        return "";
    }

    public static <T> T parseObject(String input, Type clazz) {
        return new Gson().fromJson(input, clazz);
    }

    /**
     * 复制对象数据
     *
     * @param source
     * @param <T>
     * @return
     */
    public static <T> T copyObject(Object source) {
        if (null != source) {
            return parseObject(object2Json(source), source.getClass());
        }
        return null;
    }

    public static boolean compare(Object obj1, Object obj2) {
        return object2Json(obj1).equalsIgnoreCase(object2Json(obj2));
    }

    /**
     * 检测Intent跳转目标是否存在
     *
     * @param context
     * @param implicitIntent Intent跳转目标
     * @return 不存在返回NULL
     */
    public static Intent getServiceIntent(Context context, Intent implicitIntent) {
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfo = pm.queryIntentServices(implicitIntent, 0);
        if (resolveInfo == null || resolveInfo.size() != 1) {
            return null;
        }
        ResolveInfo serviceInfo = resolveInfo.get(0);
        String packageName = serviceInfo.serviceInfo.packageName;
        String className = serviceInfo.serviceInfo.name;
        ComponentName component = new ComponentName(packageName, className);
        Intent explicitIntent = new Intent(implicitIntent);
        explicitIntent.setComponent(component);
        return explicitIntent;
    }

    /**
     * 检测Intent跳转目标是否存在
     *
     * @param context
     * @param action  action
     * @return 不存在返回NULL
     */
    public static Intent getActivityIntent(Context context, String action) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = new Intent(action);
        List<ResolveInfo> resolveInfo = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        if (resolveInfo.size() > 0) {
            return intent;
        }
        return null;
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static int getVersionCode(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (Exception e) {
            return 0;
        }
    }

    public static String getVersionName(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);
            return info.versionName;
        } catch (Exception e) {
            return "";
        }
    }

    public static String getString(String str) {
        return TextUtils.isEmpty(str) || str.equalsIgnoreCase("null") ? "" : str;
    }

    /**
     * @param prefix 前缀
     * @param str
     * @param suffix 后缀
     * @return
     */
    public static String getString(String str, String prefix, String suffix, String defaultStr) {
        String value;
        if (TextUtils.isEmpty(str) || str.equalsIgnoreCase("null")) {
            value = defaultStr;
        } else {
            value = str;
        }

        return TextUtils.isEmpty(value) ? "" : prefix + value + suffix;
    }


    /**
     * 升级APP
     *
     * @param context
     */
    public static boolean installAPk(Context context, String packageName) {
     /*   String apkPath = getApkPath(context, packageName);
        if (!TextUtils.isEmpty(apkPath)) {
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(new File(apkPath)), "application/vnd.android" +
                    ".package-archive");
            context.startActivity(intent);
            return true;
        }*/
        return false;
    }
/*
    public static String getApkPath(Context context, String packageName) {
        String apkPath = null;
        File file = new File(Constants.FileSavePaths.ZIPPED_APP);
        if (file.isDirectory() && file.listFiles().length > 0) {
            PackageManager pm = context.getPackageManager();
            PackageInfo info;
            for (File tmpFile : file.listFiles()) {
                info = pm.getPackageArchiveInfo(tmpFile.getAbsolutePath(),
                        PackageManager.GET_ACTIVITIES);
                if (info != null && info.packageName.equalsIgnoreCase(packageName)) {
                    int currentVersion = getVersionCode(context);
                    if (info.versionCode > currentVersion) {
                        //新版本，待升级
                        apkPath = tmpFile.getAbsolutePath();
                    } else {
                        //not new app so delete
                        //TODO:删除
                        boolean result = FileUtils.deleteFile(tmpFile);
                        Log.d(TAG,
                                "apk version is " + info.versionCode + ", current version is " +
                                currentVersion +
                                        ",delete " + tmpFile + " is " + result);
                    }
                    break;
                }
            }
        }
        Log.d(TAG, "Apk path of " + packageName + " is " + apkPath);
        return apkPath;
    }*/


    /**
     * 下载App
     *
     * @param context
     * @param //appUrl
     * @param //md5
     */
/*
    public static void downloadApps(Context context, String appUrl, int newVersion, String md5) {
       */
/* Intent intent = new Intent(Constants.Action.APP_DOWNLOAD);
        intent.putExtra(Constants.Keywords.DOWNLOAD_URL, appUrl);
        intent.putExtra(Constants.Keywords.NEW_VERSION, newVersion);
        intent.putExtra(Constants.Keywords.INTERFACE_OR_NOT, false);
        intent.putExtra(Constants.Keywords.MD5, md5);
        intent.putExtra(Constants.Keywords.UNZIP_OR_NOT, false);
        //下载完成后会把文件存储到约定好的位置，以方便重启升级,此处可无须指定地址
        intent.putExtra(Constants.Keywords.SAVE_PATH, "");
        context.sendBroadcast(intent);*//*

    }
*/
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public static Point getScreenSize(Context context) {
        WindowManager windowManager =
                (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR2) {
            return new Point(display.getWidth(), display.getHeight());
        } else {
            Point point = new Point();
            display.getSize(point);
            return point;
        }
    }

    /**
     * 输出的信息过长时,使用分段打印
     *
     * @param tag
     * @param message
     */
    public static void logcat(String tag, String message) {
        //  把4*1024的MAX字节打印长度改为2001字符数
        int max_str_length = 2001 - tag.length();
        //大于4000时
        while (message.length() > max_str_length) {
            Log.d(tag, message.substring(0, max_str_length));
            message = message.substring(max_str_length);
        }
        Log.d(tag, message);
    }

 /*   public static List filterList(List dataList,String field,String fieldValue) {
        return dataList.stream().filter(user -> user.getId() > 5 && "1组".equals(user.group))
        .collect(Collectors.toList());
    }*/

   /* Java 7 中引入了 try-with-resources 语句，该语句能保证将相关资源关闭，优于原来的 try-catch-finally 语句，并且使程序代码更安全更简洁
   private void handle(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {

            }
        } catch (Exception e) {

        }
    }*/

    public static boolean isEmpty(List list) {
        return null == list || list.isEmpty();
    }

    public static boolean isEmail(String email) {
        if (null == email || "".equals(email)) return false;
        //"^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        //String strPattern = "^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\
        // .-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
        Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
        Matcher m = p.matcher(email);
        return m.matches();
    }

    public static List<Integer> getResourceList(Context context, @ArrayRes int arrayResId) {
        TypedArray typedArray = context.getResources().obtainTypedArray(arrayResId);
        List<Integer> resourceList = new ArrayList<>();
        for (int i = 0; i < typedArray.length(); i++) {
            resourceList.add(typedArray.getIndex(i));
        }
        return resourceList;
    }
}