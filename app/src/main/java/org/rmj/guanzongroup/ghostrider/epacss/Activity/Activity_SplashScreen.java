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
import org.rmj.g3appdriver.dev.DeptCode;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.utils.AppDirectoryCreator;
import org.rmj.g3appdriver.utils.ServiceScheduler;
import org.rmj.guanzongroup.authlibrary.Activity.Activity_Authenticate;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Service.GLocatorService;
import org.rmj.guanzongroup.ghostrider.epacss.R;
import org.rmj.guanzongroup.ghostrider.epacss.Service.DataImportService;
import org.rmj.guanzongroup.ghostrider.epacss.Service.GMessagingService;
import org.rmj.guanzongroup.ghostrider.epacss.Service.PerformanceImportService;
import org.rmj.guanzongroup.ghostrider.epacss.ViewModel.VMSplashScreen;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.rmj.g3appdriver.utils.ServiceScheduler.EIGHT_HOUR_PERIODIC;
import static org.rmj.g3appdriver.utils.ServiceScheduler.FIFTEEN_MINUTE_PERIODIC;

public class Activity_SplashScreen extends AppCompatActivity {
    public static final String TAG = Activity_SplashScreen.class.getSimpleName();

    private ProgressBar prgrssBar;
    private TextView lblVrsion;
    private VMSplashScreen mViewModel;

    private AppConfigPreference poConfigx;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        poConfigx = AppConfigPreference.getInstance(Activity_SplashScreen.this);
        new TransparentToolbar(Activity_SplashScreen.this).SetupActionbar();
        prgrssBar = findViewById(R.id.progress_splashscreen);
        lblVrsion = findViewById(R.id.lbl_versionInfo);
        try {
            mViewModel = new ViewModelProvider(this).get(VMSplashScreen.class);
            startService(new Intent(Activity_SplashScreen.this, GMessagingService.class));

            mViewModel.getVersionInfo().observe(Activity_SplashScreen.this, s -> lblVrsion.setText(s));

            mViewModel.isPermissionsGranted().observe(this, isGranted -> {
                if(!isGranted){
                    mViewModel.getPermisions().observe(this, strings -> ActivityCompat.requestPermissions(Activity_SplashScreen.this, strings, AppConstants.PERMISION_REQUEST_CODE));
                } else {
                    AppDirectoryCreator loCreator = new AppDirectoryCreator();
                    if(loCreator.createAppDirectory(Activity_SplashScreen.this)){
                        Log.e(TAG, loCreator.getMessage());
                    } else {
                        Log.e(TAG, loCreator.getMessage());
                    }
                    mViewModel.isLoggedIn().observe(this, isLogin -> {
                        if (isLogin) {
                            mViewModel.getSessionDate().observe(this, sessionDate -> {
                                try {
                                    @SuppressLint("SimpleDateFormat") SimpleDateFormat loFormater = new SimpleDateFormat("yyyy-MM-dd");
                                    Date loDate = new Date();
                                    String lsDateNow = loFormater.format(loDate);

                                    //null session date means no user info is save to local.
                                    if(sessionDate != null) {

                                        //Logout user if date of login is not equal to current date.
                                        if (sessionDate.equalsIgnoreCase(lsDateNow)) {

                                            mViewModel.getSessionTime().observe(this, session -> {
                                                mViewModel.setSessionTime(session.Session);
                                                mViewModel.isSessionValid().observe(this, sessionValid -> {
                                                    for (int x = 0; x < 3; x++) {
                                                        int progress = (int) ((x / (float) x) * 100);
                                                        prgrssBar.setProgress(progress);
                                                        x++;
                                                        try {
                                                             Thread.sleep(1000);
                                                        } catch (InterruptedException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                    if (sessionValid) {
                                                        mViewModel.getEmployeeLevel().observe(Activity_SplashScreen.this, empLevel ->{
                                                            try{
                                                                if (empLevel.isEmpty() || DeptCode.parseUserLevel(Integer.parseInt(empLevel)).equalsIgnoreCase("Area Manager")
                                                                        || DeptCode.parseUserLevel(Integer.parseInt(empLevel)).equalsIgnoreCase("General Manager")){
                                                                    Log.e(TAG, "emp level = "+ DeptCode.parseUserLevel(Integer.parseInt(empLevel)));
                                                                    ServiceScheduler.scheduleJob(Activity_SplashScreen.this, PerformanceImportService.class, FIFTEEN_MINUTE_PERIODIC, AppConstants.DataServiceID);
                                                                }
                                                            }catch (NumberFormatException e){
                                                                e.printStackTrace();
                                                            }
                                                        });
                                                        startActivity(new Intent(Activity_SplashScreen.this, Activity_Main.class));
                                                        finish();
//                                                        overridePendingTransition(R.anim.anim_intent_slide_up_out, R.anim.anim_intent_slide_up_in);
                                                    } else {
                                                        startActivityForResult(new Intent(Activity_SplashScreen.this, Activity_Authenticate.class), AppConstants.LOGIN_ACTIVITY_REQUEST_CODE);
//                                                        overridePendingTransition(R.anim.anim_intent_slide_up_out, R.anim.anim_intent_slide_up_in);
                                                    }
                                                });

                                            });

                                            //SessionDate
                                        } else {
                                            startActivityForResult(new Intent(Activity_SplashScreen.this, Activity_Authenticate.class), AppConstants.LOGIN_ACTIVITY_REQUEST_CODE);
//                                            overridePendingTransition(R.anim.anim_intent_slide_up_out, R.anim.anim_intent_slide_up_in);
                                        }
                                        //null session info
                                    } else {
                                        startActivityForResult(new Intent(Activity_SplashScreen.this, Activity_Authenticate.class), AppConstants.LOGIN_ACTIVITY_REQUEST_CODE);
//                                        overridePendingTransition(R.anim.anim_intent_slide_up_out, R.anim.anim_intent_slide_up_in);
                                    }
                                } catch (NullPointerException e){
                                    e.printStackTrace();
                                    startActivityForResult(new Intent(Activity_SplashScreen.this, Activity_Authenticate.class), AppConstants.LOGIN_ACTIVITY_REQUEST_CODE);
//                                    overridePendingTransition(R.anim.anim_intent_slide_up_out, R.anim.anim_intent_slide_up_in);
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
//                            overridePendingTransition(R.anim.anim_intent_slide_up_out, R.anim.anim_intent_slide_up_in);
                        }
                    });
                }
            });

            mViewModel.getLocatorDateTrigger().observe(Activity_SplashScreen.this, data -> {
                if(data.CollectionMaster == 1 && data.PostedCollection == 0){
                    startService(new Intent(Activity_SplashScreen.this, GLocatorService.class));
                } else if(data.Cash_On_Hand == null) {
                    Log.d(TAG, "No cash on hand");
                } else if(data.Cash_On_Hand != null){
                    if (!data.Cash_On_Hand.equalsIgnoreCase("0.0") ||
                            !data.Cash_On_Hand.equalsIgnoreCase("0")) {
                        startService(new Intent(Activity_SplashScreen.this, GLocatorService.class));
                    }
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
        if(requestCode == AppConstants.LOGIN_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                if(!ServiceScheduler.isJobRunning(Activity_SplashScreen.this, AppConstants.DataServiceID)) {
                    if(poConfigx.getLastSyncDate().equalsIgnoreCase("") ||
                            !poConfigx.getLastSyncDate().equalsIgnoreCase(new AppConstants().CURRENT_DATE)) {
                        ServiceScheduler.scheduleJob(Activity_SplashScreen.this, DataImportService.class, EIGHT_HOUR_PERIODIC, AppConstants.DataServiceID);
                    }
                }
                startActivity(new Intent(Activity_SplashScreen.this, Activity_Main.class));
                finish();
//                overridePendingTransition(R.anim.anim_intent_slide_up_out, R.anim.anim_intent_slide_up_in);
            } else if (resultCode == RESULT_CANCELED) {
                finish();
            }
        }
    }
}