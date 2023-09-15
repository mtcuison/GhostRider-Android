/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.griderScanner
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.griderscanner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import org.rmj.g3appdriver.dev.Database.GCircle.DataAccessObject.DCreditApplicationDocuments;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.ECreditApplicationDocuments;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.EImageInfo;
import org.rmj.g3appdriver.etc.LoadDialog;
import org.rmj.g3appdriver.etc.MessageBox;
import org.rmj.g3appdriver.etc.WebFileServer;
import org.rmj.guanzongroup.ghostrider.griderscanner.adapter.FileCodeAdapter;
import org.rmj.guanzongroup.ghostrider.griderscanner.dialog.DialogImagePreview;
import org.rmj.guanzongroup.ghostrider.griderscanner.helpers.ScannerConstants;
import org.rmj.guanzongroup.ghostrider.griderscanner.model.CreditAppDocumentModel;
import org.rmj.guanzongroup.ghostrider.griderscanner.viewModel.VMClientInfo;
import org.rmj.guanzongroup.ghostrider.griderscanner.viewModel.VMMainScanner;
import org.rmj.guanzongroup.ghostrider.griderscanner.viewModel.ViewModelCallBack;
import org.rmj.guanzongroup.ghostrider.imgcapture.ImageFileCreator;
import org.rmj.guanzongroup.ghostrider.notifications.Notifications.GNotifBuilder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.rmj.guanzongroup.ghostrider.notifications.Notifications.GNotifBuilder.APP_SYNC_DATA;

public class ClientInfo extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private VMClientInfo mViewModel;
    private VMMainScanner sViewModel;

    TextView lblTransNoxxx;
    TextView lblClientName;
    TextView lblAppltnDate;
    TextView lblModelName;
    TextView lblAccntTern;
    TextView lblMobileNo;
    TextView lblStatus;

    private LoadDialog poDialogx;
    private MessageBox poMessage;

    private List<CreditAppDocumentModel> docInfo;
    public static CreditAppDocumentModel infoModel;
//    Grider initialization

    public ImageView imgBitmap;
    public static String mCurrentPhotoPath;
    public static ImageFileCreator poFilexx;
    private String subFolder = "";
    public static ContentResolver contentResolver;
    public static int  CROP_REQUEST_CODE = 1234;

    private EImageInfo poImageInfo;
    private ECreditApplicationDocuments poDocumentsInfo;
    String TransNox;
    FileCodeAdapter loAdapter;

    private List<DCreditApplicationDocuments.ApplicationDocument> documentInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_info);
        Toolbar toolbar = findViewById(R.id.toolbar_client);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        mViewModel = new ViewModelProvider(this).get(VMClientInfo.class);
        sViewModel = new ViewModelProvider(this).get(VMMainScanner.class);
        docInfo =  new ArrayList<>();
        initWidgets();
        contentResolver = this.getContentResolver();
        setData();

        ScannerConstants.TransNox = TransNox;
        mViewModel.initAppDocs(TransNox);
        mViewModel.setsTransNox(TransNox);
        mViewModel.CheckFile( TransNox, new ViewModelCallBack() {
            @Override
            public void OnStartSaving() {
                poDialogx.initDialog("Credit Online \nApplication Documents", "Checking document file from server. Please wait...", false);
                poDialogx.show();
            }

            @Override
            public void onSaveSuccessResult(String args) {

            }

            @Override
            public void onFailedResult(String message) {

            }

            @Override
            public void OnSuccessResult(String[] args) {
                poDialogx.dismiss();
                GNotifBuilder.createNotification(ClientInfo.this, "Document Scanner", args[0],APP_SYNC_DATA).show();

            }

            @Override
            public void OnFailedResult(String message) {
                poDialogx.dismiss();
                GNotifBuilder.createNotification(ClientInfo.this, "Document Scanner", message,APP_SYNC_DATA).show();

            }
        });
        mViewModel.getDocumentInfos(TransNox).observe(ClientInfo.this, fileCodeDetails -> {

            loAdapter = new FileCodeAdapter(ClientInfo.this, fileCodeDetails, new FileCodeAdapter.OnItemClickListener() {
                @Override
                public void OnClick(int position) {
                    ScannerConstants.FileDesc = fileCodeDetails.get(position).sBriefDsc;
                    if (fileCodeDetails.get(position).sSendStat == null && fileCodeDetails.get(position).sImageNme == null){
                        poImageInfo = new EImageInfo();
                        poDocumentsInfo = new ECreditApplicationDocuments();
                        poFilexx = new ImageFileCreator(ClientInfo.this , ScannerConstants.SubFolder, fileCodeDetails.get(position).sFileCode,fileCodeDetails.get(position).nEntryNox, TransNox);
                        // poFilexx = new ImageFileCreator(ClientInfo.this , AppConstants.APP_PUBLIC_FOLDER, AppConstants.SUB_FOLDER_CREDIT_APP, fileCodeDetails.get(position).sFileCode,fileCodeDetails.get(position).nEntryNox, TransNox);
                        poFilexx.CreateScanFile((openCamera, camUsage, photPath, FileName) -> {
                            mCurrentPhotoPath = photPath;
                            ScannerConstants.Usage =camUsage;
                            ScannerConstants.FileCode = fileCodeDetails.get(position).sFileCode;
                            ScannerConstants.PhotoPath = photPath;
                            ScannerConstants.EntryNox = (position + 1);
                            ScannerConstants.FileName = FileName;
                            ScannerConstants.FileDesc = fileCodeDetails.get(position).sBriefDsc;
//                            ScannerConstants.Latt = latitude;
//                            ScannerConstants.Longi = longitude;
                            startActivityForResult(openCamera, ImageFileCreator.GCAMERA);
                        });
                    }else{
                        mViewModel.DownloadDocumentFile(fileCodeDetails.get(position), TransNox, new ViewModelCallBack() {
                            @Override
                            public void OnStartSaving() {
                                poDialogx.initDialog("Credit Online \nApplication Documents", "Downloading document file from server. Please wait...", false);
                                poDialogx.show();
                            }

                            @Override
                            public void onSaveSuccessResult(String args) {

                            }

                            @Override
                            public void onFailedResult(String message) {

                            }

                            @Override
                            public void OnSuccessResult(String[] strings) {
                                poDialogx.dismiss();
                                Bitmap bitmap = null;
                                try {
                                    bitmap = MediaStore.Images.Media.getBitmap(
                                            contentResolver, Uri.fromFile(new File(ScannerConstants.PhotoPath)));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                DialogImagePreview loDialog = new DialogImagePreview(ClientInfo.this, bitmap, fileCodeDetails.get(position).sBriefDsc);
                                loDialog.initDialog(new DialogImagePreview.OnDialogButtonClickListener() {
                                    @Override
                                    public void OnCancel(Dialog Dialog) {
                                        Dialog.dismiss();
                                    }
                                });
                                loDialog.show();
                                GNotifBuilder.createNotification(ClientInfo.this, "Document Scanner", strings[0],APP_SYNC_DATA).show();

                            }

                            @Override
                            public void OnFailedResult(String message) {
                                poDialogx.dismiss();
                                GNotifBuilder.createNotification(ClientInfo.this, "Document Scanner", message,APP_SYNC_DATA).show();

                            }
                        });
                    }


//
                }

                @Override
                public void OnActionButtonClick() {

                }
            });
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(loAdapter);
            recyclerView.getRecycledViewPool().clear();
            loAdapter.notifyDataSetChanged();
        });


    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public void initWidgets(){
        poDialogx = new LoadDialog(ClientInfo.this);
        poMessage = new MessageBox(ClientInfo.this);
        recyclerView = findViewById(R.id.recyclerview_fileCodeInfo);
        layoutManager = new LinearLayoutManager(ClientInfo.this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);

        lblTransNoxxx = findViewById(R.id.lbl_list_transNox);
        lblClientName = findViewById(R.id.lbl_list_applicantName);
        lblAppltnDate = findViewById(R.id.lbl_list_applicationDate);
//        lblStatus = findViewById(R.id.lbl_list_applicationWithCI);
        lblModelName = findViewById(R.id.lbl_modelName);
        lblAccntTern = findViewById(R.id.lbl_accntTerm);
        lblMobileNo = findViewById(R.id.lbl_mobileNo);

    }
    public void setData(){
        TransNox = getIntent().getStringExtra("TransNox");
        lblTransNoxxx.setText("Trans. No: " + TransNox);
        lblClientName.setText(getIntent().getStringExtra("ClientNm"));
        lblAppltnDate.setText(getIntent().getStringExtra("dTransact"));
//        lblStatus.setText(getIntent().getStringExtra("Status"));
        lblModelName.setText(getIntent().getStringExtra("ModelName"));
        lblAccntTern.setText(getIntent().getStringExtra("AccntTerm"));
        lblMobileNo.setText(getIntent().getStringExtra("MobileNo"));
        ScannerConstants.SubFolder = getIntent().getStringExtra("SubFolder");
//        lblSentStatus.setVisibility(poLoan.getSendStatus());
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ImageFileCreator.GCAMERA){
            if(resultCode == RESULT_OK) {
                cameraCapture(mCurrentPhotoPath);
                startActivityForResult(new Intent(this,ImageCrop.class), CROP_REQUEST_CODE);

            }
        }
        if (requestCode == CROP_REQUEST_CODE ) {
            if(resultCode == RESULT_OK) {
                try {
                    poImageInfo = (EImageInfo) data.getSerializableExtra("poImage");
                    poDocumentsInfo = (ECreditApplicationDocuments) data.getSerializableExtra("poDocumentsInfo");
                    poImageInfo.setMD5Hashx(WebFileServer.createMD5Hash(poImageInfo.getFileLoct()));
                    mViewModel.saveImageInfo(poImageInfo);
                    mViewModel.saveDocumentInfoFromCamera(TransNox, poImageInfo.getFileCode());
                    mViewModel.PostDocumentScanDetail(poDocumentsInfo, new ViewModelCallBack() {
                        @Override
                        public void OnStartSaving() {
                        }

                        @Override
                        public void onSaveSuccessResult(String args) {

                        }

                        @Override
                        public void onFailedResult(String message) {

                        }

                        @Override
                        public void OnSuccessResult(String[] args) {
                            GNotifBuilder.createNotification(ClientInfo.this, "Document Scanner", args[0],APP_SYNC_DATA).show();

                        }

                        @Override
                        public void OnFailedResult(String message) {
                            GNotifBuilder.createNotification(ClientInfo.this, "Document Scanner", message,APP_SYNC_DATA).show();
                        }
                    });
                    loAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Calling this will delete the images from cache directory
     * useful to clear some memory
     */

    public boolean cameraCapture (String photoPath) {
        try {
            ScannerConstants.selectedImageBitmap = MediaStore.Images.Media.getBitmap(
                    contentResolver,Uri.fromFile(new File(photoPath)));
        } catch (IOException e) {
            return false;
        }

        return ScannerConstants.selectedImageBitmap != null;
    }



}