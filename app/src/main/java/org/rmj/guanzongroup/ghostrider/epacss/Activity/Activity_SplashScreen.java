/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.app
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */

package org.rmj.guanzongroup.ghostrider.epacss.Activity;

import static org.rmj.g3appdriver.utils.ServiceScheduler.EIGHT_HOUR_PERIODIC;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.lights.LightState;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.messaging.FirebaseMessaging;

import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.g3appdriver.GRider.Etc.TransparentToolbar;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.utils.AppDirectoryCreator;
import org.rmj.g3appdriver.utils.ServiceScheduler;
import org.rmj.guanzongroup.authlibrary.Activity.Activity_Authenticate;
import org.rmj.guanzongroup.ghostrider.epacss.R;
import org.rmj.guanzongroup.ghostrider.epacss.Service.DataImportService;
import org.rmj.guanzongroup.ghostrider.epacss.Service.GMessagingService;
import org.rmj.guanzongroup.ghostrider.epacss.ViewModel.VMSplashScreen;

import java.util.ArrayList;
import java.util.List;

public class Activity_SplashScreen extends AppCompatActivity {
    public static final String TAG = Activity_SplashScreen.class.getSimpleName();

    private ProgressBar prgrssBar;
    private TextView lblVrsion;
    private VMSplashScreen mViewModel;

    private MessageBox poDialog;

    private final ActivityResultLauncher<String[]> poRequest = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), result -> {
        InitializeAppData();
    });

    private final ActivityResultLauncher<Intent> poLogin = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == RESULT_OK) {
            startActivity(new Intent(Activity_SplashScreen.this, Activity_Main.class));
            ServiceScheduler.scheduleJob(Activity_SplashScreen.this, DataImportService.class, EIGHT_HOUR_PERIODIC, AppConstants.DataServiceID);
            finish();
        } else if (result.getResultCode() == RESULT_CANCELED) {
            finish();
        }
    });

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VMSplashScreen.class);
        setContentView(R.layout.activity_splash_screen);
        poDialog = new MessageBox(Activity_SplashScreen.this);
        new TransparentToolbar(Activity_SplashScreen.this).SetupActionbar();
        prgrssBar = findViewById(R.id.progress_splashscreen);
        lblVrsion = findViewById(R.id.lbl_versionInfo);

        CheckPermissions();
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                        return;
                    }

                    // Get new FCM registration token
                    String token = task.getResult();

                    AppConfigPreference.getInstance(Activity_SplashScreen.this).setAppToken(token);
                });
        startService(new Intent(Activity_SplashScreen.this, GMessagingService.class));

        AppDirectoryCreator loCreator = new AppDirectoryCreator();
        if(loCreator.createAppDirectory(Activity_SplashScreen.this)){
            Log.e(TAG, loCreator.getMessage());
        } else {
            Log.e(TAG, loCreator.getMessage());
        }
    }

    private void CheckPermissions(){
        List<String> lsPermissions = new ArrayList<>();
        if(ActivityCompat.checkSelfPermission(Activity_SplashScreen.this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED){
            lsPermissions.add(Manifest.permission.INTERNET);
        }
        if(ActivityCompat.checkSelfPermission(Activity_SplashScreen.this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED){
            lsPermissions.add(Manifest.permission.ACCESS_NETWORK_STATE);
        }
        if(ActivityCompat.checkSelfPermission(Activity_SplashScreen.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            lsPermissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if(ActivityCompat.checkSelfPermission(Activity_SplashScreen.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED){
            lsPermissions.add(Manifest.permission.READ_PHONE_STATE);
        }
        if(ActivityCompat.checkSelfPermission(Activity_SplashScreen.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            lsPermissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if(ActivityCompat.checkSelfPermission(Activity_SplashScreen.this, Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED){
            lsPermissions.add(Manifest.permission.GET_ACCOUNTS);
        }
        if(ActivityCompat.checkSelfPermission(Activity_SplashScreen.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            lsPermissions.add(Manifest.permission.CAMERA);
        }
        if(ActivityCompat.checkSelfPermission(Activity_SplashScreen.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            lsPermissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if(ActivityCompat.checkSelfPermission(Activity_SplashScreen.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            lsPermissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        poRequest.launch(lsPermissions.toArray(new String[0]));
    }

    private void InitializeAppData(){
        mViewModel.InitializeData(new VMSplashScreen.OnInitializecallback() {
            @Override
            public void OnProgress(String args, int progress) {
                prgrssBar.setProgress(progress);
            }

            @Override
            public void OnSuccess() {
                startActivity(new Intent(Activity_SplashScreen.this, Activity_Main.class));
                finish();
            }

            @Override
            public void OnNoSession() {
                poLogin.launch(new Intent(Activity_SplashScreen.this, Activity_Authenticate.class));
            }

            @Override
            public void OnFailed(String message) {
                poDialog.initDialog();
                poDialog.setTitle("GhostRider Android");
                poDialog.setMessage(message);
                poDialog.setPositiveButton("Okay", (view, dialog) -> {
                    dialog.dismiss();
                    finish();
                });
                poDialog.show();
            }
        });
    }
}