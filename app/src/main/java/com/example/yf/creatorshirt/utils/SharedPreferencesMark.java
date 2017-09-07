package com.example.yf.creatorshirt.utils;

/**
 * SharedPreference存储 记录只出现一次的组件是否已经出现过
 * Created by chinaso on 2016/7/25.
 */
public final class SharedPreferencesMark {


    public static Boolean getHasShowCamera() {
        return SharedPreferencesUtil.getBoolean(PrefKey.APP_SETTING, PrefKey.APP_CAMERA_PERMISSION, false);
    }

    public static void setHasShowCamera(Boolean hasShowCamera) {
        SharedPreferencesUtil.setBoolean(PrefKey.APP_SETTING, PrefKey.APP_CAMERA_PERMISSION, hasShowCamera);
    }

//    public static Boolean getHasShowLocation() {
//        return SharedPreferencesUtil.getBoolean(PrefKey.APP_SETTING, PrefKey.APP_LOCATION_PERMISSION, false);
//    }
//
//    public static void setHasShowLocation(Boolean hasShowLocation) {
//        SharedPreferencesUtil.setBoolean(PrefKey.APP_SETTING, PrefKey.APP_LOCATION_PERMISSION, hasShowLocation);
//    }
//
//    public static Boolean getHasShowRecordAudio() {
//        return SharedPreferencesUtil.getBoolean(PrefKey.APP_SETTING, PrefKey.APP_RECORD_PERMISSION, false);
//    }
//
//    public static void setHasShowRecordAudio(Boolean hasShowRecordAudio) {
//        SharedPreferencesUtil.setBoolean(PrefKey.APP_SETTING, PrefKey.APP_RECORD_PERMISSION, hasShowRecordAudio);
//    }
//
//    public static Boolean getHasShowExtralStoreage() {
//        return SharedPreferencesUtil.getBoolean(PrefKey.APP_SETTING, PrefKey.APP_STORAGE_PERMISSION, false);
//    }
//
//    public static void setHasShowExtralStoreage(Boolean hasShowExtralStoreage) {
//        SharedPreferencesUtil.setBoolean(PrefKey.APP_SETTING, PrefKey.APP_STORAGE_PERMISSION, hasShowExtralStoreage);
//    }
//
//    public static Boolean getHasShowContact() {
//        return SharedPreferencesUtil.getBoolean(PrefKey.APP_SETTING, PrefKey.APP_STORAGE_PERMISSION, false);
//
//    }
//
//    public static void setHasShowContact(Boolean hasShowContact) {
//        SharedPreferencesUtil.setBoolean(PrefKey.APP_SETTING, PrefKey.APP_CONTACT_PERMISSION, hasShowContact);
//    }
}
