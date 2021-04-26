/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.app
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */

package org.rmj.guanzongroup.ghostrider.epacss.themeController;

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
