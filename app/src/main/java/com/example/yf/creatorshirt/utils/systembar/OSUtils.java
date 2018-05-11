package com.example.yf.creatorshirt.utils.systembar;

import android.text.TextUtils;

import java.lang.reflect.Method;

/**
 * Created by yang on 07/06/2017.
 */

class OSUtils {
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_EMUI_VERSION_NAME = "ro.build.version.emui";
    private static final String KEY_DISPLAY = "ro.build.display.id";
    private static String flymeOSFlag;
    private static String MIUIVersion;

    /**
     * 判断是否为emui3.1版本
     * Is emui 3 1 boolean.
     *
     * @return the boolean
     */
    public static boolean isEMUI3_1() {
        String property = getEMUIVersion();
        return "EmotionUI 3".equals(property) || property.contains("EmotionUI_3.1");
    }

    /**
     * 得到emui的版本
     * Gets emui version.
     *
     * @return the emui version
     */
    public static String getEMUIVersion() {
        return isEMUI() ? getSystemProperty(KEY_EMUI_VERSION_NAME, "") : "";
    }


    /**
     * 判断是否为emui（小米）
     * Is emui boolean.
     *
     * @return the boolean
     */
    public static boolean isEMUI() {
        String property = getSystemProperty(KEY_EMUI_VERSION_NAME, "");
        return !TextUtils.isEmpty(property);
    }

    /**
     * 判断是否为flymeOS(魅族)
     * Is flyme os boolean.
     *
     * @return the boolean
     */
    public static boolean isFlymeOS() {
        return getFlymeOSFlag().toLowerCase().contains("flyme");
    }

    /**
     * 得到flymeOS的版本
     * Gets flyme os version.
     *
     * @return the flyme os version
     */
    public static String getFlymeOSVersion() {
        return isFlymeOS() ? getSystemProperty(KEY_DISPLAY, "") : "";
    }

    private static String getFlymeOSFlag() {
        return getSystemProperty(KEY_DISPLAY, "");
    }

    private static String getSystemProperty(String key, String defaultValue) {
        try {
            Class<?> clz = Class.forName("android.os.SystemProperties");
            Method get = clz.getMethod("get", String.class, String.class);
            return (String) get.invoke(clz, key, defaultValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

    /**
     * 判断miui版本是否大于等于6
     * Is miui 6 more boolean.
     *
     * @return the boolean
     */
    public static boolean isMIUI6More() {
        String version = getMIUIVersion();
        return (!version.isEmpty() && Integer.valueOf(version.substring(1)) >= 6);
    }

    /**
     * 获得miui的版本
     * Gets miui version.
     *
     * @return the miui version
     */
    public static String getMIUIVersion() {
        return isMIUI() ? getSystemProperty(KEY_MIUI_VERSION_NAME, "") : "";
    }

    /**
     * 判断是否为miui
     * Is miui boolean.
     *
     * @return the boolean
     */
    public static boolean isMIUI() {
        String property = getSystemProperty(KEY_MIUI_VERSION_NAME, "");
        return !TextUtils.isEmpty(property);
    }

    /**
     * 判断flymeOS的版本是否大于等于4
     * Is flyme os 4 more boolean.
     *
     * @return the boolean
     */
    public static boolean isFlymeOS4More() {
        String version = getFlymeOSVersion();
        int num;
        if (!version.isEmpty()) {
            if (version.toLowerCase().contains("os")) {
                num = Integer.valueOf(version.substring(9, 10));
            } else {
                num = Integer.valueOf(version.substring(6, 7));
            }
            return num >= 4;
        }
        return false;
    }
}
