/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.creditApp
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */

package org.rmj.guanzongroup.onlinecreditapplication.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.GRider.Etc.GToast;
import org.rmj.g3appdriver.GRider.Etc.LoadDialog;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.g3appdriver.etc.WebFileServer;
import org.rmj.guanzongroup.ghostrider.griderscanner.dialog.DialogImagePreview;
import org.rmj.guanzongroup.ghostrider.imgcapture.ImageFileCreator;
import org.rmj.guanzongroup.ghostrider.notifications.Object.GNotifBuilder;
import org.rmj.guanzongroup.onlinecreditapplication.Adapter.LoanApplication;
import org.rmj.guanzongroup.onlinecreditapplication.Adapter.UserLoanApplicationsAdapter;
import org.rmj.guanzongroup.onlinecreditapplication.Model.DownloadImageCallBack;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;
import org.rmj.guanzongroup.onlinecreditapplication.R;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMApplicationHistory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.rmj.guanzongroup.ghostrider.notifications.Object.GNotifBuilder.APP_SYNC_DATA;

public class Activity_ApplicationHistory extends AppCompatActivity implements ViewModelCallBack, VMApplicationHistory.OnImportCallBack {
    private static final String TAG = Activity_ApplicationHistory.class.getSimpleName();

    private VMApplicationHistory mViewModel;

    private Toolbar toolbar;
    private TextInputEditText txtSearch;
    private RecyclerView recyclerView;
    private LinearLayout noRecord;

    private List<LoanApplication> loanList;
    private UserLoanApplicationsAdapter adapter;

    private ImageFileCreator poCamera;
    private EImageInfo poImage;

    private LoadDialog poDialogx;
    private MessageBox poMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_history);
        initWidgets();
        poImage = new EImageInfo();
        poDialogx = new LoadDialog(Activity_ApplicationHistory.this);
        poMessage = new MessageBox(Activity_ApplicationHistory.this);
        mViewModel = new ViewModelProvider(this).get(VMApplicationHistory.class);
        mViewModel.ImportLoanApplication(Activity_ApplicationHistory.this);
        mViewModel.getApplicationHistory().observe(Activity_ApplicationHistory.this, applicationLogs -> {
            if(applicationLogs.size()>0) {
                noRecord.setVisibility(View.GONE);
                loanList = new ArrayList<>();
                for (int x = 0; x < applicationLogs.size(); x++) {
                    LoanApplication loan = new LoanApplication();
                    loan.setGOCasNumber(applicationLogs.get(x).sGOCASNox);
                    loan.setTransNox(applicationLogs.get(x).sTransNox);
                    loan.setBranchCode(applicationLogs.get(x).sBranchNm);
                    loan.setDateTransact(applicationLogs.get(x).dCreatedx);
                    loan.setDetailInfo(applicationLogs.get(x).sDetlInfo);
                    loan.setClientName(applicationLogs.get(x).sClientNm);
                    loan.setLoanCIResult(applicationLogs.get(x).cWithCIxx);
                    loan.setSendStatus(applicationLogs.get(x).cSendStat);
                    loan.setTransactionStatus(applicationLogs.get(x).cTranStat);
                    loan.setDateSent(applicationLogs.get(x).dReceived);
                    loan.setcCaptured(applicationLogs.get(x).cCaptured);
                    loan.setDateApproved(applicationLogs.get(x).dVerified);
                    loan.setsFileLoct(applicationLogs.get(x).sFileLoct);
                    loanList.add(loan);
                }
                adapter = new UserLoanApplicationsAdapter(Activity_ApplicationHistory.this,loanList, new UserLoanApplicationsAdapter.LoanApplicantListActionListener() {
                    @Override
                    public void OnExport(String TransNox) {
                        mViewModel.ExportGOCasInfo(TransNox);
                    }

                    @Override
                    public void OnUpdate(String TransNox) {
                        mViewModel.UpdateGOCasInfo(TransNox);
                    }

                    @Override
                    public void OnDelete(String TransNox) {
                        mViewModel.DeleteGOCasInfo(TransNox);
                    }

                    @Override
                    public void OnPreview(int pos) {
                        if (Integer.parseInt(applicationLogs.get(pos).cCaptured) == 0 && applicationLogs.get(pos).sFileLoct == null) {
                            poCamera = new ImageFileCreator(Activity_ApplicationHistory.this,
                                    AppConstants.SUB_FOLDER_CREDIT_APP,
                                    "0029",
                                    0,
                                    applicationLogs.get(pos).sTransNox);
                            poCamera.CreateScanFile((openCamera, camUsage, photPath, FileName, latitude, longitude) -> {
                                poImage.setFileLoct(photPath);
                                poImage.setFileCode("0029");
                                poImage.setSourceCD("COAD");
                                poImage.setLatitude(String.valueOf(latitude));
                                poImage.setLongitud(String.valueOf(longitude));
                                poImage.setSourceNo(applicationLogs.get(pos).sTransNox);
                                poImage.setImageNme(FileName);
                                poImage.setCaptured(new AppConstants().DATE_MODIFIED);
                                startActivityForResult(openCamera, ImageFileCreator.GCAMERA);
                            });
                        }else{
                            mViewModel.DownloadDocumentFile(applicationLogs.get(pos).sTransNox, new DownloadImageCallBack() {
                                @Override
                                public void OnStartSaving() {
                                    poDialogx.initDialog("Credit Online \nApplication", "Downloading applicant photo from server. Please wait...", false);
                                    poDialogx.show();
                                }

                                @Override
                                public void onSaveSuccessResult(String args) {
                                    poDialogx.dismiss();
                                    onPreviewImage(args);
                                    GNotifBuilder.createNotification(Activity_ApplicationHistory.this, "Applicant Photo", "Applicant photo has been downloaded successfully.",APP_SYNC_DATA).show();

                                }

                                @Override
                                public void onFailedResult(String message) {
                                    poDialogx.dismiss();
                                    poMessage.initDialog();
                                    poMessage.setTitle("Loan Application");
                                    poMessage.setMessage(message);
                                    poMessage.setPositiveButton("Okay", (view, dialog) -> {
                                        dialog.dismiss();
                                    });
                                    poMessage.show();
                                    //GNotifBuilder.createNotification(Activity_ApplicationHistory.this, "Applicant Photo", message,APP_SYNC_DATA).show();

                                }
                            });
                        }

                    }

                    @Override
                    public void OnCamera(String TransNox) {
                        poCamera = new ImageFileCreator(Activity_ApplicationHistory.this,
                                AppConstants.SUB_FOLDER_CREDIT_APP,
                                "0029",
                                0,
                                TransNox);
                        poCamera.CreateScanFile((openCamera, camUsage, photPath, FileName, latitude, longitude) -> {
                            poImage.setFileLoct(photPath);
                            poImage.setFileCode("0029");
                            poImage.setSourceCD("COAD");
                            poImage.setLatitude(String.valueOf(latitude));
                            poImage.setLongitud(String.valueOf(longitude));
                            poImage.setSourceNo(TransNox);
                            poImage.setImageNme(FileName);
                            poImage.setCaptured(new AppConstants().DATE_MODIFIED);
                            startActivityForResult(openCamera, ImageFileCreator.GCAMERA);
                        });
                    }
                });
                LinearLayoutManager layoutManager = new LinearLayoutManager(Activity_ApplicationHistory.this);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(layoutManager);

                txtSearch.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        try {
                            adapter.getSearchFilter().filter(s.toString());
                            adapter.notifyDataSetChanged();
                            if (adapter.getItemCount() == 0){
                                noRecord.setVisibility(View.VISIBLE);
                            }else {
                                noRecord.setVisibility(View.GONE);
                            }
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }
            else
                noRecord.setVisibility(View.VISIBLE);

        });
    }

    private void initWidgets(){
        toolbar = findViewById(R.id.toolbar_applicationHistory);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        txtSearch = findViewById(R.id.txt_Search);
        recyclerView = findViewById(R.id.rectangles_applicationHistory);
        noRecord = findViewById(R.id.layout_application_history_noRecord);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.anim_intent_slide_in_left, R.anim.anim_intent_slide_out_right);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveSuccessResult(String args) {

    }

    @Override
    public void onFailedResult(String message) {
        GToast.CreateMessage(Activity_ApplicationHistory.this, message, GToast.WARNING).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(ImageFileCreator.GCAMERA == requestCode && resultCode == RESULT_OK){
            poImage.setMD5Hashx(WebFileServer.createMD5Hash(poImage.getFileLoct()));
            mViewModel.saveImageFile(poImage);
            mViewModel.uploadImage(poImage);
            mViewModel.saveApplicantImageFromCamera(poImage.getSourceNo());
        }
    }

    public void onPreviewImage(String FileLoct){
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(
                    getContentResolver(), Uri.fromFile(new File(FileLoct)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        DialogImagePreview loDialog = new DialogImagePreview(Activity_ApplicationHistory.this, bitmap, "Applicant Photo");
        loDialog.initDialog(new DialogImagePreview.OnDialogButtonClickListener() {
            @Override
            public void OnCancel(Dialog Dialog) {
                Dialog.dismiss();
            }
        });
        loDialog.show();

    }

    @Override
    public void onStartImport() {
        poDialogx.initDialog("Loan Application List", "Importing latest data. Please wait...", false);
        poDialogx.show();
    }

    @Override
    public void onSuccessImport() {
        poDialogx.dismiss();
    }

    @Override
    public void onImportFailed(String message) {
        poDialogx.dismiss();
        GNotifBuilder.createNotification(Activity_ApplicationHistory.this, "Loan Application List", message,APP_SYNC_DATA).show();

    }
}