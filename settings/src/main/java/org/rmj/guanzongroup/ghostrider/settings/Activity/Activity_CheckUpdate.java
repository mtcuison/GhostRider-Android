/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.settings
 * Electronic Personnel Access Control Security System
 * project file created : 6/28/21 11:11 AM
 * project file last modified : 6/28/21 11:11 AM
 */

package org.rmj.guanzongroup.ghostrider.settings.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.ybq.android.spinkit.BuildConfig;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;
import com.github.ybq.android.spinkit.style.FadingCircle;
import com.google.android.material.button.MaterialButton;

import org.rmj.g3appdriver.GRider.Etc.GToast;
import org.rmj.g3appdriver.GRider.Etc.LoadDialog;
import org.rmj.guanzongroup.ghostrider.settings.R;
import org.rmj.guanzongroup.ghostrider.settings.ViewModel.VMCheckUpdate;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.Objects;

public class Activity_CheckUpdate extends AppCompatActivity {

    private Toolbar toolbar;
    private LinearLayout newUpdate;
    private RelativeLayout lnProgress;
    private TextView lblCurrent, lblnewVrsn, lblProgress;
    private ProgressBar prgUpdate, prgDownload;
    private MaterialButton btnUpdate;
    private VMCheckUpdate mViewModel;

    private LoadDialog poDialog;

    private boolean isDownloading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_update);
        mViewModel = new ViewModelProvider(this).get(VMCheckUpdate.class);

        toolbar = findViewById(R.id.toolbar_checkUpdate);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        newUpdate = findViewById(R.id.linear_newVersion);
        lnProgress = findViewById(R.id.linear_progress);
        lblCurrent = findViewById(R.id.lbl_currentVersion);
        lblProgress = findViewById(R.id.lbl_progressUpdate);
        prgUpdate = findViewById(R.id.progress_update);
        prgDownload = findViewById(R.id.progress_download);
        lblnewVrsn = findViewById(R.id.lbl_newVersion);
        btnUpdate = findViewById(R.id.btn_Update);

        poDialog = new LoadDialog(Activity_CheckUpdate.this);

        Sprite loDrawable = new FadingCircle();
        loDrawable.setColor(getResources().getColor(R.color.guanzon_digital_orange));
        prgDownload.setIndeterminateDrawable(loDrawable);

        mViewModel.getVersionInfo().observe(this, s -> lblCurrent.setText(s));

        btnUpdate.setOnClickListener(v -> mViewModel.DownloadUpdate(new VMCheckUpdate.SystemUpateCallback() {
            @Override
            public void OnDownloadUpdate(String title, String message) {
                lnProgress.setVisibility(View.VISIBLE);
                btnUpdate.setText("Downloading Updates...");
                btnUpdate.setEnabled(false);
                isDownloading = true;
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void OnProgressUpdate(int progress) {
                prgUpdate.setIndeterminate(false);
                prgUpdate.setMax(100);
                prgUpdate.setProgress(progress);
                lblProgress.setText(progress+"%");
            }

            @Override
            public void OnFinishDownload(Intent intent) {
                startActivity(intent);
                lnProgress.setVisibility(View.GONE);
                btnUpdate.setText("Updated");
                btnUpdate.setEnabled(true);
                isDownloading = false;
            }

            @Override
            public void OnFailedDownload(String message) {
                lnProgress.setVisibility(View.GONE);
                btnUpdate.setText("Download Update");
                btnUpdate.setEnabled(true);
                isDownloading = false;
            }
        }));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            if(!isDownloading) {
                finish();
            } else {
                GToast.CreateMessage(Activity_CheckUpdate.this, "Unable to exit while downloading updates...", GToast.WARNING).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(!isDownloading) {
            finish();
        } else {
            GToast.CreateMessage(Activity_CheckUpdate.this, "Unable to exit while downloading updates...", GToast.WARNING).show();
        }
    }
}