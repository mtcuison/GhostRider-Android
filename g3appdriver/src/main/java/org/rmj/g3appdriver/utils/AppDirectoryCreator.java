package org.rmj.g3appdriver.utils;

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

    public static final boolean createAppDirectory() {
        Log.e(TAG, poExport.toString());
        if(!poExport.exists()) {
            Log.e(TAG, "No directory exist.");
            return poExport.mkdirs();
        }
        return false;
    }

}
