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

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import org.rmj.g3appdriver.etc.AppConstants;

import java.io.File;

public class AppDirectoryCreator {
    private static final String TAG = AppDirectoryCreator.class.getSimpleName();
    private static String psExtDir;
    private static final String psExptDir = AppConstants.SUB_FOLDER_EXPORTS;
    private static File poExport;
    private String message;

    @SuppressLint("NewApi")
    public boolean createAppDirectory(Context context) {
//        File loDocsFx = new File(String.valueOf(context.getFilesDir()) , Environment.DIRECTORY_DOCUMENTS + "/" + psExptDir + "/");
//        File loPicsFx = new File(String.valueOf(context.getFilesDir()) , Environment.DIRECTORY_PICTURES + "/" + psExptDir + "/");
//        Log.e(TAG, "Docs DIR: "+loDocsFx.toString());
//        Log.e(TAG, "Images DIR: "+loPicsFx.toString());
        psExtDir = String.valueOf(context.getExternalFilesDir(null));
        poExport = new File(psExtDir + psExptDir + "/");

        Log.e(TAG, "DIR: "+poExport.toString());
        if(!poExport.exists()) {
            Log.e(TAG, "poExport");
            if(poExport.mkdirs()){
                message = "App directory has been created.";
                return true;
            } else {
                message = "Failed to create app directory.";
                return false;
            }
        } else {
            message = "Directory already exists";
            return false;
        }
    }

    public String getMessage(){
        return message;
    }
}
