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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Etc.TransparentToolbar;
import org.rmj.g3appdriver.utils.AppDirectoryCreator;
import org.rmj.g3appdriver.utils.ServiceScheduler;
import org.rmj.guanzongroup.authlibrary.Activity.Activity_Authenticate;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Service.DCPLocatorService;
import org.rmj.guanzongroup.ghostrider.epacss.BuildConfig;
import org.rmj.guanzongroup.ghostrider.epacss.R;
import org.rmj.guanzongroup.ghostrider.epacss.Service.DataImportService;
import org.rmj.guanzongroup.ghostrider.epacss.Service.GMessagingService;
import org.rmj.guanzongroup.ghostrider.epacss.ViewModel.VMSplashScreen;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.rmj.g3appdriver.utils.ServiceScheduler.FIFTEEN_MINUTE_PERIODIC;
import static org.rmj.g3appdriver.utils.ServiceScheduler.TWO_HOUR_PERIODIC;

public class Activity_SplashScreen extends AppCompatActivity {
    public static final String TAG = Activity_SplashScreen.class.getSimpleName();

    private ProgressBar prgrssBar;
    private TextView lblVrsion;
    private VMSplashScreen mViewModel;

    private AppDirectoryCreator mMakeDir;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new TransparentToolbar(Activity_SplashScreen.this).SetupActionbar();
        mMakeDir = new AppDirectoryCreator();
        prgrssBar = findViewById(R.id.progress_splashscreen);
        lblVrsion = findViewById(R.id.lbl_versionInfo);
        lblVrsion.setText(BuildConfig.VERSION_NAME + "_" + BuildConfig.BUILD_TYPE.toUpperCase());
        if(mMakeDir.createAppDirectory()) {
            Log.e(TAG, "Export directory created.");
        } else {
            Log.e(TAG, "Export directory already exist.");
        }
        try {
            mViewModel = new ViewModelProvider(this).get(VMSplashScreen.class);
            startService(new Intent(Activity_SplashScreen.this, GMessagingService.class));

            if(!ServiceScheduler.isJobRunning(Activity_SplashScreen.this, AppConstants.DataServiceID)) {
                ServiceScheduler.scheduleJob(Activity_SplashScreen.this, DataImportService.class, TWO_HOUR_PERIODIC, AppConstants.DataServiceID);
            }

            if(!ServiceScheduler.isJobRunning(Activity_SplashScreen.this, AppConstants.GLocatorServiceID)) {
                ServiceScheduler.scheduleJob(Activity_SplashScreen.this, DCPLocatorService.class, FIFTEEN_MINUTE_PERIODIC, AppConstants.GLocatorServiceID);
            }

            mViewModel.isPermissionsGranted().observe(this, isGranted -> {
                if(!isGranted){
                    mViewModel.getPermisions().observe(this, strings -> ActivityCompat.requestPermissions(Activity_SplashScreen.this, strings, AppConstants.PERMISION_REQUEST_CODE));
                } else {
                    mViewModel.isLoggedIn().observe(this, isValid -> {
                        if (isValid) {
                            mViewModel.getSessionDate().observe(this, sessionDate -> {
                                try {
                                    @SuppressLint("SimpleDateFormat") SimpleDateFormat loFormater = new SimpleDateFormat("yyyy-MM-dd");
                                    Date loDate = new Date();
                                    String lsDateNow = loFormater.format(loDate);
                                    if (sessionDate.equalsIgnoreCase(lsDateNow)) {
                                        mViewModel.getSessionTime().observe(this, session -> {
                                            mViewModel.setSessionTime(session.Session);
                                            mViewModel.isSessionValid().observe(this, aBoolean -> {
                                                for(int x = 0; x < 3; x++){
                                                    int progress = (int) ((x / (float) x) * 100);
                                                    prgrssBar.setProgress(progress);
                                                    x++;
                                                    try {
                                                        Thread.sleep(1000);
                                                    } catch (InterruptedException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                                if (aBoolean) {
                                                    startActivity(new Intent(Activity_SplashScreen.this, Activity_Main.class));
                                                    finish();
                                                } else {
                                                    startActivityForResult(new Intent(Activity_SplashScreen.this, Activity_Authenticate.class), AppConstants.LOGIN_ACTIVITY_REQUEST_CODE);
                                                }
                                            });

                                        });

                                    } else {
                                        startActivityForResult(new Intent(Activity_SplashScreen.this, Activity_Authenticate.class), AppConstants.LOGIN_ACTIVITY_REQUEST_CODE);
                                    }
                                } catch (Exception e){
                                    e.printStackTrace();
                                }
                            });

                        } else {
                            for(int x = 0; x < 3; x++){
                                int progress = (int) ((x / (float) x) * 100);
                                prgrssBar.setProgress(progress);
                                x++;
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            startActivityForResult(new Intent(Activity_SplashScreen.this, Activity_Authenticate.class), AppConstants.LOGIN_ACTIVITY_REQUEST_CODE);
                        }
                    });
                }
            });
        }catch (NullPointerException e){
            e.printStackTrace();
        }catch (RuntimeException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == AppConstants.PERMISION_REQUEST_CODE) {
            boolean lbIsGrnt = true;
            for (int x = 0; x < grantResults.length; x++) {
                if (ContextCompat.checkSelfPermission(Activity_SplashScreen.this, permissions[x]) != grantResults[x]) {
                    lbIsGrnt = false;
                    break;
                }
                Log.e("Permission", permissions[x] + " Granted " + grantResults[x]);

            }
            if (lbIsGrnt) {
                mViewModel.setPermissionsGranted(true);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            startActivity(new Intent(Activity_SplashScreen.this, Activity_Main.class));
            finish();
        } else if(resultCode == RESULT_CANCELED){
            finish();
        }
    }
}