package org.rmj.guanzongroup.ghostrider.epacss.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Etc.TransparentToolbar;
import org.rmj.guanzongroup.authlibrary.Activity.Activity_Authenticate;
import org.rmj.guanzongroup.ghostrider.epacss.R;
import org.rmj.guanzongroup.ghostrider.epacss.Service.DataImportService;
import org.rmj.guanzongroup.ghostrider.epacss.ViewModel.VMSplashScreen;

public class SplashScreenActivity extends AppCompatActivity {
    public static final String TAG = SplashScreenActivity.class.getSimpleName();

    private ProgressBar prgrssBar;
    private TextView lblVrsion;
    private VMSplashScreen mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new TransparentToolbar(SplashScreenActivity.this).SetupActionbar();
        prgrssBar = findViewById(R.id.progress_splashscreen);
        mViewModel = new ViewModelProvider(this).get(VMSplashScreen.class);
        mViewModel.setupTokenInfo("sample token info");
        mViewModel.isPermissionsGranted().observe(this, isGranted -> {
            if(!isGranted){
                mViewModel.getPermisions().observe(this, strings -> ActivityCompat.requestPermissions(SplashScreenActivity.this, strings, AppConstants.PERMISION_REQUEST_CODE));
            } else {
                mViewModel.isLoggedIn().observe(this, isValid -> {
                    if (isValid) {
                        mViewModel.getSessionTime().observe(this, session -> {
                            try {
                                mViewModel.setSessionTime(session.Session);
                            } catch (Exception e){
                                e.printStackTrace();
                            }
                        });
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
                                startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                                finish();
                            } else {
                                startActivityForResult(new Intent(SplashScreenActivity.this, Activity_Authenticate.class), AppConstants.LOGIN_ACTIVITY_REQUEST_CODE);
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
                        startActivityForResult(new Intent(SplashScreenActivity.this, Activity_Authenticate.class), AppConstants.LOGIN_ACTIVITY_REQUEST_CODE);
                    }
                });
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if(!isJobRunning()) {
                scheduleJob();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == AppConstants.PERMISION_REQUEST_CODE){
            boolean lbIsGrnt = true;
            for(int x = 0 ; x < grantResults.length; x++){
                if(ContextCompat.checkSelfPermission(SplashScreenActivity.this, permissions[x]) != grantResults[x]){
                    lbIsGrnt = false;
                    break;
                }
            }
            if(lbIsGrnt){
                mViewModel.setPermissionsGranted(true);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
            finish();
        } else if(resultCode == RESULT_CANCELED){
            finish();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private boolean isJobRunning(){
        JobScheduler scheduler = (JobScheduler) this.getSystemService( Context.JOB_SCHEDULER_SERVICE ) ;

        boolean hasBeenScheduled = false ;

        for ( JobInfo jobInfo : scheduler.getAllPendingJobs() ) {
            if ( jobInfo.getId() == AppConstants.JOB_ID ) {
                hasBeenScheduled = true ;
                break ;
            }
        }

        return hasBeenScheduled;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void scheduleJob(){
        ComponentName loComp = new ComponentName(this, DataImportService.class);
        @SuppressLint("MissingPermission")
        JobInfo loJob = new JobInfo.Builder(AppConstants.JOB_ID, loComp)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPersisted(true)
                .setPeriodic(900000)
                .build();
        JobScheduler loScheduler = (JobScheduler)getSystemService(JOB_SCHEDULER_SERVICE);
        int liResult = loScheduler.schedule(loJob);
        if(liResult == JobScheduler.RESULT_SUCCESS){
            Log.e(TAG, "Job Scheduled");
        } else {
            Log.e(TAG, "Job Schedule Failed.");
        }
    }
}