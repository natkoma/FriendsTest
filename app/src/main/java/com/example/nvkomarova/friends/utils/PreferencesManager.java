package com.example.nvkomarova.friends.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Настройки приложения
 */
public class PreferencesManager {
    private static final String APP_PREFERENCES 			= "app_settings";      	// Имя файла настроек
    private static final String VK_TOKEN 			        = "vk_token";
    private static final String VK_UID   			        = "vk_uid";
    private static SharedPreferences settings;

    /*
    * Необходимо вызвать на начальной активити.
    */
    public static void init(Context context) {
        if (settings == null) settings = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
    }

    public static String getVKToken() {
        return settings.getString(VK_TOKEN, "");
    }

    public static void setVKToken(String vkToken) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(VK_TOKEN, vkToken);
        editor.apply();
    }

    public static String getVKUid() {
        return settings.getString(VK_UID, "");
    }

    public static void setVkUid(String vkUid) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(VK_UID, vkUid);
        editor.apply();
    }

    public static String getVKApiVersion(){return "5.69";}

}
