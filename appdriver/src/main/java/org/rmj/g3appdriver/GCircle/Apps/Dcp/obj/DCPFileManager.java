package org.rmj.g3appdriver.GCircle.Apps.Dcp.obj;

import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;
import android.net.Uri;
import android.os.ParcelFileDescriptor;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class DCPFileManager {
    private static final String TAG = DCPFileManager.class.getSimpleName();

    private final Application instance;

    private String message;

    public DCPFileManager(Application instance) {
        this.instance = instance;
    }

    public String getMessage() {
        return message;
    }

    public String ReadToJson(Uri uri){
        try{
            StringBuffer builder = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(instance.getContentResolver().openInputStream(uri)));

            String line = "";
            while ((line = reader.readLine()) != null)
            {
                builder.append(line);
            }
            reader.close();

            String lsResult = builder.toString();

            JSONObject loJson = new JSONObject(lsResult);
            return loJson.toString();
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return null;
        }
    }

    public boolean ExportToFile(Uri uri, String fsVal){
        try{
            ParcelFileDescriptor pfd = instance.getContentResolver().
                    openFileDescriptor(uri, "w");
            FileOutputStream fileOutputStream =
                    new FileOutputStream(pfd.getFileDescriptor());
            fileOutputStream.write(fsVal.getBytes());
            // Let the document provider know you're done by closing the stream.
            fileOutputStream.close();
            pfd.close();
            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }
}
