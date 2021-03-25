package org.rmj.guanzongroup.ghostrider.settings.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import androidx.core.content.FileProvider;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.rmj.g3appdriver.GRider.Constants.AppConstants.APP_PUBLIC_FOLDER;
import static org.rmj.g3appdriver.GRider.Constants.AppConstants.SUB_FOLDER_EXPORTS;

public class DatabaseExport {
    public Context context;
    private String FILE_FOLDER;
    private String dataName;
    private  String folder_name = "/Exported File/";
    public DatabaseExport(Context context, String usage, String dataName) {
        this.context = context;
        this.FILE_FOLDER = usage;
        this.dataName = dataName;
    }
    public String export(){
        String root = Environment.getExternalStorageDirectory().toString();
        File sd = new File(root + APP_PUBLIC_FOLDER + "/" + SUB_FOLDER_EXPORTS + "/");
        if (!sd.exists()) {
            sd.mkdirs();
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
//            Toast.makeText(context, "DB Exported!", Toast.LENGTH_LONG).show();

        return "Database successfully exported";
        } catch(SecurityException e) {
            e.printStackTrace();
            return "Exporting failed!";
        }catch(IOException e) {
            e.printStackTrace();
            return "Exporting failed!";
        }

    }

}
