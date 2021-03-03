package org.rmj.guanzongroup.ghostrider.settings.themeController;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class DarkThemeApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//        SharedPreferences sharedPreferences =
//                PreferenceManager.getDefaultSharedPreferences(this);
//        Boolean themePref = sharedPreferences.getBoolean("themePref", ThemeHelper.DARK_MODE);
//        ThemeHelper.applyTheme(themePref);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean themePref = sharedPreferences.getBoolean("themePrefs", ThemeHelper.DEFAULT);
        ThemeHelper.applyTheme(themePref);
        Log.e("Theme Apply ", String.valueOf(themePref));
    }
}
