package app.taolin.one.utils;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import app.taolin.one.App;

/**
 * @author taolin
 * @version v1.0
 * @date May 31, 2016.
 * @description
 */

public class SharedPreferenceUtil {

    public static SharedPreferences getSharedPreference() {
        return PreferenceManager.getDefaultSharedPreferences(App.getInstance());
    }

    public static void writeInt(String key, int value) {
        getSharedPreference().edit().putInt(key, value).apply();
    }

    public static int readInt(String key, int def) {
        return getSharedPreference().getInt(key, def);
    }

    public static int readInt(String key) {
        return getSharedPreference().getInt(key, -1);
    }

    public static void writeBoolean(String key, boolean value) {
        getSharedPreference().edit().putBoolean(key, value).apply();
    }

    public static boolean readBoolean(String key) {
        return getSharedPreference().getBoolean(key, false);
    }

    public static void writeString(String key, String value) {
        getSharedPreference().edit().putString(key, value).apply();
    }

    public static String readString(String key) {
        return getSharedPreference().getString(key, "");
    }
}
