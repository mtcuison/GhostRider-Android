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

import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
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
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.rmj.g3appdriver.GRider.Etc.LoadDialog;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.g3appdriver.GRider.Etc.SessionManager;
import org.rmj.guanzongroup.ghostrider.notifications.Function.GRiderErrorReport;

public class DatabaseExport {
    private static final String TAG = DatabaseExport.class.getSimpleName();
    private final Context context;
    private final SessionManager poSession;
    private final FirebaseStorage storage = FirebaseStorage.getInstance();
    private final StorageReference poRefrnce = storage.getReference().child("database");
    private final GRiderErrorReport poReportx;
    private final LoadDialog poDiaLoad;
    private final MessageBox poMessage;
    private final String FILE_FOLDER;
    private String dataName;

    public DatabaseExport(Context context, String usage, String dataName) {
        Log.e(TAG, "Initialized.");
        this.context = context;
        this.poReportx = new GRiderErrorReport((Application) context.getApplicationContext());
        this.poSession = new SessionManager(context);
        this.poDiaLoad = new LoadDialog(context);
        this.poMessage = new MessageBox(context);
        this.FILE_FOLDER = usage;
        this.dataName = dataName;
    }

    public void export(){
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
            // UPLOAD TO EXTERNAL STORAGE
            source = new FileInputStream(currentDB).getChannel();
            destination = new FileOutputStream(backupDB).getChannel();
            destination.transferFrom(source, 0, source.size());
            source.close();
            destination.close();

            // UPLOAD TO FIREBASE
            uploadToFirebase(backupDB.getPath());

        } catch(SecurityException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    // Using putStream()
    private void uploadToFirebase(String fsPath) {
        try {
            File loFile = new File(fsPath);
            InputStream stream = new FileInputStream(loFile);
//            StorageMetadata metadata = new StorageMetadata.Builder()
//                    .setContentType("sqlite/database-file")
//                    .build();

            UploadTask uploadTask = getReference().putStream(stream);
            poDiaLoad.initDialog("Export Database","Exporting database. Please wait...", false);

            uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    poDiaLoad.show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    poDiaLoad.dismiss();
                    showExportDialog(false, exception.getMessage());
                    Log.e("FirebaseUpload", "ERROR -> " + exception.getMessage());
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    getReference().getDownloadUrl().addOnSuccessListener( uri -> {
                       Log.e("downloadLink", uri.toString());
                        poDiaLoad.dismiss();
                        showExportDialog(true, "Database Exported Successfully.");
                        Log.e("FirebaseUpload", "SUCCESS");
//                        sendDownloadLink(uri.toString(), isSent -> {
//                            if(isSent) {
//                                poDiaLoad.dismiss();
//                                showExportDialog(true, "Database Exported Successfully.");
//                                Log.e("FirebaseUpload", "SUCCESS");
//                            } else {
//                                poDiaLoad.dismiss();
//                                showExportDialog(true, "Database Exported Successfully.");
//                                Log.e("FirebaseUpload", "ERROR -> GRiderErrorReport");
//                            }
//                        });
                    });
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

    private void sendDownloadLink(String fsUrl, DownloadLinkListener foCallBck) {
        try {
            new SendDownloadLinkAsync(poReportx, foCallBck).execute(fsUrl);
        } catch(NullPointerException e) {
            e.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void showExportDialog(boolean isSuccess, String message){
        poMessage.initDialog();
        poMessage.setNegativeButton("Okay", (view, dialog) -> dialog.dismiss());
        poMessage.setTitle("Export Database");

        if(isSuccess == false){
            poMessage.setPositiveButton("Retry", (view, dialog) -> export());
            poMessage.setNegativeButton("Cancel", (view, dialog) -> dialog.dismiss());
            poMessage.setMessage(message);
            poMessage.show();
        }else {
            poMessage.setMessage(message);
            poMessage.show();
        }
    }

    private static class SendDownloadLinkAsync extends AsyncTask<String, Void, Boolean> {

        private final GRiderErrorReport poReportx;
        private final DownloadLinkListener callBack;

        SendDownloadLinkAsync(GRiderErrorReport foReportx, DownloadLinkListener fsCallBck) {
            this.poReportx = foReportx;
            this.callBack = fsCallBck;
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            return poReportx.SendErrorReport("Database Export", "Database download link: \n \n" + "[" + strings[0] + "]");
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            callBack.onSent(aBoolean);
        }

    }

    private interface DownloadLinkListener {
        void onSent(boolean isSent);
    }

}
