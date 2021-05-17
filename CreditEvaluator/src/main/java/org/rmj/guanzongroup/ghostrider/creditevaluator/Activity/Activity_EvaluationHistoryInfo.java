/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.CreditEvaluator
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.creditevaluator.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.rmj.g3appdriver.GRider.Database.Entities.ECIEvaluation;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Adapter.CreditEvaluationHistoryInfoAdapter;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Dialog.DialogImagePreview;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Etc.CIConstants;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Model.EvaluationHistoryInfoModel;
import org.rmj.guanzongroup.ghostrider.creditevaluator.R;
import org.rmj.guanzongroup.ghostrider.creditevaluator.ViewModel.VMEvaluationHistory;
import org.rmj.guanzongroup.ghostrider.creditevaluator.ViewModel.VMEvaluationHistoryInfo;

import java.util.List;
import java.util.Objects;

public class Activity_EvaluationHistoryInfo extends AppCompatActivity {
    private static final String TAG = Activity_EvaluationHistoryInfo.class.getSimpleName();
    private VMEvaluationHistoryInfo mViewModel;
    private LinearLayoutManager poLayout;
    private CreditEvaluationHistoryInfoAdapter poAdapter;
    private RecyclerView recyclerView;
    private String psTransNo;
    private ImageView ivCustomr;
    private TextView lblTransN, lblCustNm, lblLnUnit, lblDownpx,
            lblTermxx, lblApprov, lblDisapv, lblReason;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation_history_info);

        Toolbar toolbar = findViewById(R.id.toolbar_evaluation_history_info);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(CIConstants.EVALUATION_HISTORY);

        initObjects();
        initWidgets();
        initIntentValues();
        setContent();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return true;
    }

    private void initObjects() {
        this.mViewModel = ViewModelProviders.of(this).get(VMEvaluationHistoryInfo.class);
        this.poLayout = new LinearLayoutManager(Activity_EvaluationHistoryInfo.this);
    }

    private void initWidgets() {
        // poAdapter = new EvaluationHistoryInfoAdapter();
        ivCustomr = findViewById(R.id.iv_customer);
        lblTransN = findViewById(R.id.lbl_transNo);
        lblCustNm = findViewById(R.id.lbl_customer_name);
        lblLnUnit = findViewById(R.id.lbl_loan_unit);
        lblDownpx = findViewById(R.id.lbl_downpayment);
        lblTermxx = findViewById(R.id.lbl_terms);
        lblReason = findViewById(R.id.lbl_approval_reason);
        lblApprov = findViewById(R.id.lbl_approved);
        lblDisapv = findViewById(R.id.lbl_disapproved);
        recyclerView = findViewById(R.id.recyclerView);
    }

    private void initIntentValues() {
        psTransNo = getIntent().getStringExtra("sTransNox");
    }

    private void setContent() {
        mViewModel.setTransNo(psTransNo);
        mViewModel.getCiDetail().observe(Activity_EvaluationHistoryInfo.this, ciDetail -> {
            try {
                initalizeImg(ciDetail.sFileLoct);
                lblTransN.setText(ciDetail.sTransNox);
                lblCustNm.setText(ciDetail.sCompnyNm);
                lblLnUnit.setText(ciDetail.sModelNme);
                lblDownpx.setText(parseAmtToString(ciDetail.nDownPaym));
                lblTermxx.setText(ciDetail.nAcctTerm + "Month/s");
                ivCustomr.setOnClickListener(v -> {
                    DialogImagePreview plDialogx = new DialogImagePreview(Activity_EvaluationHistoryInfo.this,
                            ciDetail.sFileLoct);
                    plDialogx.initDialog(dialog -> {
                        dialog.dismiss();
                    });
                    plDialogx.show();
                });
            } catch(Exception e) {
                e.printStackTrace();
            }
        });
        mViewModel.getAllDoneCiInfo().observe(Activity_EvaluationHistoryInfo.this, eciEvaluation -> {
            try {
                displayTranStat(eciEvaluation.getTranStat());
                displayReason(eciEvaluation.getRemarksx());
                mViewModel.onFetchCreditEvaluationDetail(eciEvaluation, evaluationDetl -> {
                    this.poAdapter = new CreditEvaluationHistoryInfoAdapter(evaluationDetl);
                    poLayout.setOrientation(RecyclerView.VERTICAL);
                    recyclerView.setLayoutManager(poLayout);
                    recyclerView.setAdapter(this.poAdapter);
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private String parseAmtToString(String fsAmount) {
        return "₱" + Double.parseDouble(fsAmount);
    }

    private void displayTranStat(String fsTrnStat) {
        if(fsTrnStat.equalsIgnoreCase("1")) {
            lblApprov.setVisibility(View.VISIBLE);
            lblDisapv.setVisibility(View.GONE);
        } else if(fsTrnStat.equalsIgnoreCase("3")) {
            lblDisapv.setVisibility(View.VISIBLE);
            lblApprov.setVisibility(View.GONE);
        }
    }

    private void displayReason(String fsReason) {
        String lsReason = "Reason: " + fsReason;
        lblReason.setText(lsReason);
    }

    private void initalizeImg(String fsImgPath) {
        // Get the dimensions of the View
        int targetW = ivCustomr.getWidth();
        int targetH = ivCustomr.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(fsImgPath, bmOptions);

        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.max(1, Math.min(photoW/targetW, photoH/targetH));

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(fsImgPath, bmOptions);

        Bitmap bOutput;
        float degrees = 90;//rotation degree
        Matrix matrix = new Matrix();
        matrix.setRotate(degrees);
        bOutput = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        ivCustomr.setImageBitmap(bOutput);
    }
}