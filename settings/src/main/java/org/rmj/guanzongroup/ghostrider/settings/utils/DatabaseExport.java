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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.util.Calendar;
import java.util.TimeZone;

import static org.rmj.g3appdriver.GRider.Constants.AppConstants.SUB_FOLDER_EXPORTS;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.rmj.g3appdriver.GRider.Etc.SessionManager;

public class DatabaseExport {
    private static final String TAG = DatabaseExport.class.getSimpleName();
    public Context context;
    private final SessionManager poSession;
    private final FirebaseStorage storage = FirebaseStorage.getInstance();
    private final StorageReference poRefrnce = storage.getReference().child("database");
    private final String FILE_FOLDER;
    private String dataName;

    public DatabaseExport(Context context, String usage, String dataName) {
        Log.e(TAG, "Initialized.");
        this.context = context;
        this.poSession = new SessionManager(context);
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
//            ** UPLOAD TO EXTERNAL STORAGE **
            source = new FileInputStream(currentDB).getChannel();
            destination = new FileOutputStream(backupDB).getChannel();
            destination.transferFrom(source, 0, source.size());
            source.close();
            destination.close();

//            ** USING PUTBYTES() **
//            byte[] poByte = new byte[(int) currentDB.length()];
//            uploadToFirebase(poByte);

            uploadToFirebase(backupDB.getPath());
            return "Database successfully exported";

        } catch(SecurityException e) {
            e.printStackTrace();
            return "Exporting failed!, " + e.getMessage();
        } catch (Exception e){
            e.printStackTrace();
            return "Exporting failed!, " + e.getMessage();
        }
    }

    // Using putStream()
    private void uploadToFirebase(String fsPath) {
        try {
            File loFile = new File(fsPath);
            InputStream stream = new FileInputStream(loFile);
            UploadTask uploadTask = getReference().putStream(stream);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.e("FirebaseUpload", "ERROR | " + exception.getMessage());
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Log.e("FirebaseUpload", "SUCCESS");
                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private StorageReference getReference() {
        try {
            Calendar loCalendr = Calendar.getInstance(TimeZone.getTimeZone("Asia/Manila"));
            String lsBranchN = poSession.getBranchName();
            int lnMonthxx = loCalendr.get(Calendar.MONTH) + 1;
            String lsMonthxx = (lnMonthxx < 10) ? "0" + lnMonthxx : String.valueOf(lnMonthxx);
            String lsMnthDay = (loCalendr.get(Calendar.DAY_OF_MONTH) < 10) ?
                    "0" + loCalendr.get(Calendar.DAY_OF_MONTH)
                    : String.valueOf(loCalendr.get(Calendar.DAY_OF_MONTH));
            String lsYearxxx = String.valueOf(loCalendr.get(Calendar.YEAR)).substring(2, 4);
            String lsFileNme = lsBranchN + " - " + lsMonthxx + lsMnthDay + lsYearxxx + ".db";
            Log.e("Sampal Date", lsFileNme);

            return poRefrnce.child(lsFileNme);

        } catch (NullPointerException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

//    ** PUTBYTE METHOD() **
//    private void uploadToFirebase(byte[] foByte) {
//        UploadTask uploadTask = poRefrnce.putBytes(foByte);
//        uploadTask.addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                Log.e("FirebaseUpload", "ERROR | " + exception.getMessage());
//            }
//        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                Log.e("FirebaseUpload", "SUCCESS");
//            }
//        });
//    }

}
