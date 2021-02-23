package org.rmj.guanzongroup.ghostrider.epacss.themeController;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;

public class ThemeHelper {
    public static Boolean DEFAULT = false;

//    public static final Boolean DARK_MODE = false;
    public static void applyTheme(@NonNull Boolean themePref) {
        if (themePref){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
//
    }
}
