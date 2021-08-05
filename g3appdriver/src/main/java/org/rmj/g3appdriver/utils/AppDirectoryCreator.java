/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */

package org.rmj.g3appdriver.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import org.rmj.g3appdriver.GRider.Constants.AppConstants;

import java.io.File;

public class AppDirectoryCreator {
    private static final String TAG = AppDirectoryCreator.class.getSimpleName();
    private static final String psExtDir = Environment.getExternalStorageDirectory().toString();
    private static final String psAppDir = AppConstants.APP_PUBLIC_FOLDER;
    private static final String psExptDir = AppConstants.SUB_FOLDER_EXPORTS;
    private static final File poExport = new File(psExtDir + psAppDir + psExptDir + "/");

    public static boolean createAppDirectory() {
        Log.e(TAG, poExport.toString());
        if(!poExport.exists()) {
            Log.e(TAG, "poExport");
            return poExport.mkdirs();
        }
        return false;
    }
}
