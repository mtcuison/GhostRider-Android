package org.rmj.guanzongroup.ghostrider.epacss.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import org.rmj.g3appdriver.GRider.Etc.GToast;
import org.rmj.g3appdriver.dev.DataManager;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel.VMDataManager;
import org.rmj.guanzongroup.ghostrider.epacss.R;

import java.io.File;
import java.lang.reflect.Method;

public class Activity_DataManager extends AppCompatActivity {
    private VMDataManager mViewModel;

    private ProgressBar loProgress;
    private TextView lblStatus;
    private MaterialButton btnClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_manager);
        mViewModel = new ViewModelProvider(this).get(VMDataManager.class);

        loProgress = findViewById(R.id.progress_checkLocal);
        lblStatus = findViewById(R.id.lbl_checkStatus);
        btnClear = findViewById(R.id.btn_clearData);

        mViewModel.checkData(new VMDataManager.OnDataFetchListener() {
            @Override
            public void OnCheck() {
                lblStatus.setText("Checking...");
            }

            @Override
            public void OnCheckProgress(int table) {
                loProgress.setProgress(table);
            }

            @Override
            public void OnCheckLocalData(boolean hasPendingData) {
                loProgress.setVisibility(View.GONE);
                if(hasPendingData) {
                    lblStatus.setText("Unable to clear data. Some data are still unposted.");
                    btnClear.setVisibility(View.GONE);
                } else {
                    lblStatus.setText("No unposted data.");
                    btnClear.setVisibility(View.VISIBLE);
                }
            }
        });

        btnClear.setOnClickListener(v -> {
            try {
                DataManager.KillProcessesAround(Activity_DataManager.this);
                clearApplicationData();
                GToast.CreateMessage(Activity_DataManager.this, "Data Cleared", GToast.INFORMATION).show();
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    public void clearApplicationData() {
        File cache = getCacheDir();
        File appDir = new File(cache.getParent());
        if(appDir.exists()){
            String[] children = appDir.list();
            for(String s : children){
                if(!s.equals("lib")){
                    deleteDir(new File(appDir, s));
                    Log.i("TAG", "File /data/data/APP_PACKAGE/" + s +" DELETED");
                }
            }
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (String child : children) {
                boolean success = deleteDir(new File(dir, child));
                if (!success) {
                    return false;
                }
            }
        }

        return dir.delete();
    }
}