package org.rmj.g3appdriver.lib.Firebase;

import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.rmj.g3appdriver.GCircle.Account.EmployeeSession;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class FireStorage {
    private static final String TAG = FireStorage.class.getSimpleName();

    private final Application instance;

    private String message;

    public FireStorage(Application instance) {
        this.instance = instance;
    }

    public String getMessage() {
        return message;
    }

    public boolean UploadData(String fsPath){
        try{
            File loFile = new File(fsPath);
            InputStream stream = new FileInputStream(loFile);

            EmployeeSession loSession = EmployeeSession.getInstance(instance);
            String lsBranchN = loSession.getBranchName();
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference loReference = storage.getReference().child("database/" + lsBranchN);
            loReference.child(loSession.getUserID() + ".db");

            UploadTask uploadTask = loReference.putStream(stream);
            uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    loReference.getDownloadUrl().addOnSuccessListener( uri -> {

                    });
                }
            });
            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }

    public boolean UploadDcpSelfie(String fsPath){
        try {

            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }
}
