package org.rmj.g3appdriver.utils;

/*
TODO
    OldFileRemover.class:
    - Remove files from previous week based on the provided fsFolder(path)
       ~> fsFolder: Is the path of folder where the file resides.
*/

import android.util.Log;

import java.io.File;

public class FileRemover {
    private static final String TAG = FileRemover.class.getSimpleName();

    public static boolean execute(String fsFolder) {
        try {
            if(fsFolder != null) {
                Log.e(TAG, "Target Folder ~> " + fsFolder);
                File directory = new File(fsFolder);
                File[] files = directory.listFiles();

                if(files.length > 0) {
                    for (int i = 0; i < files.length; i++) {
                        File loFile = new File(files[i].getPath());
                        if (loFile.exists()) {
                            if (loFile.delete()) {
                                Log.e(TAG, files[i].getPath() + " delete success.");
                            } else {
                                Log.e(TAG, files[i].getPath() + " delete failed.");
                            }
                        }
                    }
                    return true;
                } else {
                    Log.e(TAG, "Nothing to delete here. Folder path is empty.");
                    return false;
                }

            } else {
                Log.e(TAG, "The argument provided for \"fsFolder\" is null. Please provide a folder path where old files reside.");
                return false;
            }
        } catch(NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }

}
