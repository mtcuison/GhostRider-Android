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
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.g3appdriver.GRider.Etc.TransparentToolbar;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.utils.ServiceScheduler;
import org.rmj.guanzongroup.authlibrary.Activity.Activity_Authenticate;
import org.rmj.guanzongroup.ghostrider.epacss.R;
import org.rmj.guanzongroup.ghostrider.epacss.Service.DataImportService;
import org.rmj.guanzongroup.ghostrider.epacss.ViewModel.VMSplashScreen;

import java.util.List;
import java.util.Map;

public class Activity_SplashScreen extends AppCompatActivity {
    public static final String TAG = Activity_SplashScreen.class.getSimpleName();

    private ProgressBar prgrssBar;
    private TextView lblVrsion;
    private VMSplashScreen mViewModel;

    private MessageBox poDialog;

    private AppConfigPreference poConfigx;

    private List<String> psPermissions;

    private final ActivityResultLauncher<String[]> poRequest = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), result -> {
        mViewModel.CheckPermissions();
    });

    private final ActivityResultLauncher<Intent> poLogin = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == RESULT_OK) {
            if(!ServiceScheduler.isJobRunning(Activity_SplashScreen.this, AppConstants.DataServiceID)) {
                if(poConfigx.getLastSyncDate().equalsIgnoreCase("") ||
                        !poConfigx.getLastSyncDate().equalsIgnoreCase(new AppConstants().CURRENT_DATE)) {
                    ServiceScheduler.scheduleJob(Activity_SplashScreen.this, DataImportService.class, EIGHT_HOUR_PERIODIC, AppConstants.DataServiceID);
                }
            }

            startActivity(new Intent(Activity_SplashScreen.this, Activity_Main.class));
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
        poConfigx = AppConfigPreference.getInstance(Activity_SplashScreen.this);
        new TransparentToolbar(Activity_SplashScreen.this).SetupActionbar();
        prgrssBar = findViewById(R.id.progress_splashscreen);
        lblVrsion = findViewById(R.id.lbl_versionInfo);

        mViewModel.CheckPermissions();
        mViewModel.GetPermissions().observe(Activity_SplashScreen.this, strings -> {
            try{
                if(strings.size() > 0){
                    poRequest.launch(strings.toArray(new String[0]));
                } else {
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
                        public void OnSessionExpired() {
                            poDialog.initDialog();
                            poDialog.setTitle("GhostRider");
                            poDialog.setMessage("Your last account session has expired. Please login again.");
                            poDialog.setPositiveButton("Login", (view, dialog) -> {
                                dialog.dismiss();
                            });
                            poDialog.setNegativeButton("Exit", new MessageBox.DialogButton() {
                                @Override
                                public void OnButtonClick(View view, AlertDialog dialog) {
                                    dialog.dismiss();
                                    finish();
                                }
                            });
                            poDialog.show();
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
            } catch (Exception e){
                e.printStackTrace();
            }
        });
    }
}