package org.rmj.guanzongroup.ghostrider.griderscanner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DCreditApplicationDocuments;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplicationDocuments;
import org.rmj.g3appdriver.GRider.Database.Entities.EFileCode;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.GRider.Etc.GToast;
import org.rmj.g3appdriver.GRider.Etc.LoadDialog;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.g3appdriver.etc.WebFileServer;
import org.rmj.guanzongroup.ghostrider.griderscanner.adapter.ClientFileCodeAdapter;
import org.rmj.guanzongroup.ghostrider.griderscanner.adapter.FileCodeAdapter;
import org.rmj.guanzongroup.ghostrider.griderscanner.adapter.LoanApplication;
import org.rmj.guanzongroup.ghostrider.griderscanner.dialog.DialogImagePreview;
import org.rmj.guanzongroup.ghostrider.griderscanner.helpers.ScannerConstants;
import org.rmj.guanzongroup.ghostrider.griderscanner.model.CreditAppDocumentModel;
import org.rmj.guanzongroup.ghostrider.griderscanner.viewModel.VMClientInfo;
import org.rmj.guanzongroup.ghostrider.griderscanner.viewModel.VMMainScanner;
import org.rmj.guanzongroup.ghostrider.griderscanner.viewModel.ViewModelCallBack;
import org.rmj.guanzongroup.ghostrider.imgcapture.ImageFileCreator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClientInfo extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private VMClientInfo mViewModel;
    private VMMainScanner sViewModel;
    TextView lblGoCasNoxxx;
    TextView lblTransNoxxx;
    TextView lblClientName;
    TextView lblAppltnDate;
    TextView lblApplResult;
    TextView lblDateApprov;
    TextView lblDateSentxx;
    TextView lblSentStatus;

    private LoadDialog poDialogx;
    private MessageBox poMessage;

    private List<CreditAppDocumentModel> docInfo;
    public static CreditAppDocumentModel infoModel;
//    Grider initialization

    public ImageView imgBitmap;
    public static String mCurrentPhotoPath;
    public static ImageFileCreator poFilexx;
    private final String FOLDER_NAME = "DocumentScan";
    public static ContentResolver contentResolver;
    public static int  CROP_REQUEST_CODE = 1234;

    public static EImageInfo poImageInfo;
    public static ECreditApplicationDocuments poDocumentsInfo;
    String TransNox, FileCode;
    FileCodeAdapter loAdapter;
    ClientFileCodeAdapter adapter;
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
        infoModel = new CreditAppDocumentModel();
        initWidgets();
        contentResolver = this.getContentResolver();
        setData();

        ScannerConstants.TransNox = lblTransNoxxx.getText().toString();
        mViewModel.getDocument(TransNox).observe(ClientInfo.this, data->{
            mViewModel.getFileCode().observe(ClientInfo.this, fileCodeDetails -> {
                Log.e("fileCode size", String.valueOf(fileCodeDetails.size()));
                for (int i = 0; i < fileCodeDetails.size(); i++){
                    poDocumentsInfo = new ECreditApplicationDocuments();
                    poDocumentsInfo.setEntryNox(fileCodeDetails.get(i).getEntryNox());
                    poDocumentsInfo.setTransNox(TransNox);
                    poDocumentsInfo.setFileCode(fileCodeDetails.get(i).getFileCode());
                    mViewModel.saveDocumentInfo(data,poDocumentsInfo);
                }
                loAdapter = new FileCodeAdapter(ClientInfo.this,TransNox, data,fileCodeDetails, new FileCodeAdapter.OnItemClickListener() {
                    @Override
                    public void OnClick(int position) {
                        poFilexx = new ImageFileCreator(ClientInfo.this , "Credit Application Documents", "SCAN", fileCodeDetails.get(position).getFileCode(), TransNox);
                        poFilexx.CreateScanFile((openCamera, camUsage, photPath, FileName, latitude, longitude) -> {
                            mCurrentPhotoPath = photPath;
                            ScannerConstants.Usage =camUsage;
                            ScannerConstants.Folder = "Credit Application Documents";
                            ScannerConstants.FileCode = fileCodeDetails.get(position).getFileCode();
                            ScannerConstants.PhotoPath = photPath;
                            ScannerConstants.EntryNox = (position + 1);
                            ScannerConstants.FileName = FileName;
                            ScannerConstants.FileDesc = fileCodeDetails.get(position).getBriefDsc();
                            ScannerConstants.Latt = latitude;
                            ScannerConstants.Longi = longitude;
                            startActivityForResult(openCamera, ImageFileCreator.GCAMERA);
                        });
                    }

                    @Override
                    public void OnActionButtonClick() {
                        //TODO: Future update
                    }
                });

                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(loAdapter);
//        recyclerView.getRecycledViewPool().clear();
                loAdapter.notifyDataSetChanged();
            });
            }
        );


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
        lblGoCasNoxxx = findViewById(R.id.lbl_list_GoCasNo);
        lblTransNoxxx = findViewById(R.id.lbl_list_applicationTransNo);
        lblClientName = findViewById(R.id.lbl_list_applicantName);
        lblAppltnDate = findViewById(R.id.lbl_list_applicationDate);
        lblApplResult = findViewById(R.id.lbl_list_applicationWithCI);
        lblDateApprov = findViewById(R.id.lbl_list_approvedDate);
        lblDateSentxx = findViewById(R.id.lbl_list_dateSent);
        lblSentStatus = findViewById(R.id.lbl_applicationSent);
    }
    public void setData(){
        TransNox = getIntent().getStringExtra("TransNox");
        lblGoCasNoxxx.setText(getIntent().getStringExtra("GoCasNoxx"));
        lblTransNoxxx.setText(TransNox);
        lblClientName.setText(getIntent().getStringExtra("ClientNm"));
        lblAppltnDate.setText(getIntent().getStringExtra("DateApplied"));
        lblApplResult.setText(getIntent().getStringExtra("Status"));
//        lblSentStatus.setVisibility(poLoan.getSendStatus());
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ImageFileCreator.GCAMERA){
            if(resultCode == RESULT_OK) {
                cameraCapture(mCurrentPhotoPath);
                startActivityForResult(new Intent(this,ImageCrop.class), CROP_REQUEST_CODE);

            }else {
                infoModel.setDocFilePath("");
            }
        }
        if (requestCode == CROP_REQUEST_CODE ) {
            if(resultCode == RESULT_OK) {
                try {

                    poImageInfo.setMD5Hashx(WebFileServer.createMD5Hash(infoModel.getDocFilePath()));
                    mViewModel.saveDocumentInfoFromCamera(poDocumentsInfo);
                    mViewModel.saveImageInfo(poImageInfo);
                    mViewModel.PostDocumentScanDetail(poDocumentsInfo, new ViewModelCallBack() {
                        @Override
                        public void OnStartSaving() {
                            poDialogx.initDialog("Daily Collection Plan", "Posting collection details. Please wait...", false);
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
                            poMessage.initDialog();
                            poMessage.setTitle("Credit Online \nApplication Documents");
                            poMessage.setMessage(args[0]);
                            poMessage.setPositiveButton("Okay", (view, dialog) -> dialog.dismiss());
                            poMessage.show();
                        }

                        @Override
                        public void OnFailedResult(String message) {
                            poDialogx.dismiss();
                            poMessage.initDialog();
                            poMessage.setTitle("Credit Online \nApplication Documents");
                            poMessage.setMessage(message);
                            poMessage.setPositiveButton("Okay", (view, dialog) -> dialog.dismiss());
                            poMessage.show();
                        }
                    });
                    loAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else {
                infoModel.setDocFilePath("");
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