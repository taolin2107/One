package app.taolin.one.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;

import java.io.File;

import app.taolin.one.api.Api;

/**
 * @author taolin
 * @version v1.0
 * @date Jun 19, 2016.
 * @description
 */

public class Utils {

    // 简单的字符串加密,在每个字符上面加上p
    public static String encryto(String s, int p) {
        StringBuilder sb = new StringBuilder();
        int len = s.length();
        for (int i = 0; i < len; i++) {
            sb.append((char)(s.charAt(i) + p));
        }
        return sb.toString();
    }

    // 反转字符串
    public static String reverse(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = s.length() - 1; i >= 0; i--) {
            sb.append(s.charAt(i));
        }
        return sb.toString();
    }

    public static File getDiskCacheDir(Context context) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + Constants.CACHE_DIR);
    }

    public static int getAppVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    public static String getAppVersionName(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "1.0";
    }

    public static void main(String[] args) {
        System.out.println(Api.URL_BASE);
        String ss = encryto(reverse(Api.URL_BASE), -6);
        System.out.println(ss);
        System.out.println(encryto(reverse(ss), 6));


        System.out.println(Api.URL_MORE_BASE);
        String s = encryto(reverse(Api.URL_MORE_BASE), 9);
        System.out.println(s);
        System.out.println(encryto(reverse(s), -9));
    }
}
