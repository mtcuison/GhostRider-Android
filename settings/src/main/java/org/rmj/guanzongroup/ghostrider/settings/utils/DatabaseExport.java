/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.settings
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */

package org.rmj.guanzongroup.ghostrider.settings.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import static org.rmj.g3appdriver.GRider.Constants.AppConstants.SUB_FOLDER_EXPORTS;

public class DatabaseExport {
    private final Context context;
    private final String FILE_FOLDER;
    private final String dataName;

    public DatabaseExport(Context context, String usage, String dataName) {
        this.context = context;
        this.FILE_FOLDER = usage;
        this.dataName = dataName;
    }

    public String export(){
        String root = String.valueOf(context.getExternalFilesDir(null));
        File sd = new File(root + "/" + SUB_FOLDER_EXPORTS + "/");

        if (!sd.exists()) {
            if(sd.mkdirs()){
                Log.e("Database Export", "Directory has been created.");
            } else {
                Log.e("Database Export", "Failed to create directory.");
            }
        }

        File data = Environment.getDataDirectory();
        FileChannel source;
        FileChannel destination;
        String currentDBPath = "/data/"+ context.getPackageName() + "/databases/" +  dataName;
        File currentDB = new File(data, currentDBPath);
        File backupDB = new File(sd, dataName);

        try {
            source = new FileInputStream(currentDB).getChannel();
            destination = new FileOutputStream(backupDB).getChannel();
            destination.transferFrom(source, 0, source.size());
            source.close();
            destination.close();

        return "Database successfully exported";

        } catch(SecurityException e) {
            e.printStackTrace();
            return "Exporting failed!, " + e.getMessage();
        } catch(IOException e) {
            e.printStackTrace();
            return "Exporting failed!, " + e.getMessage();
        } catch (Exception e){
            e.printStackTrace();
            return "Exporting failed!, " + e.getMessage();
        }
    }
}
