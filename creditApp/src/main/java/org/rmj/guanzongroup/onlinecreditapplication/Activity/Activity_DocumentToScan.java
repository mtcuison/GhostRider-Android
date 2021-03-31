package org.rmj.guanzongroup.onlinecreditapplication.Activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DCreditApplicationDocuments;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplicationDocuments;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.GRider.Etc.LoadDialog;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.g3appdriver.etc.WebFileServer;
import org.rmj.guanzongroup.ghostrider.griderscanner.ImageCrop;
import org.rmj.guanzongroup.ghostrider.griderscanner.helpers.ScannerConstants;
import org.rmj.guanzongroup.ghostrider.griderscanner.model.CreditAppDocumentModel;
import org.rmj.guanzongroup.ghostrider.griderscanner.viewModel.VMMainScanner;
import org.rmj.guanzongroup.ghostrider.griderscanner.viewModel.ViewModelCallBack;
import org.rmj.guanzongroup.ghostrider.imgcapture.ImageFileCreator;
import org.rmj.guanzongroup.onlinecreditapplication.Adapter.DocumentToScanAdapter;
import org.rmj.guanzongroup.onlinecreditapplication.R;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMDocumentToScan;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Activity_DocumentToScan extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private VMDocumentToScan mViewModel;
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
    private final String FOLDER_NAME = "DocumentScan";
    public static ContentResolver contentResolver;
    public static int  CROP_REQUEST_CODE = 1234;

    private EImageInfo poImageInfo;
    private ECreditApplicationDocuments poDocumentsInfo;
    String TransNox;
    DocumentToScanAdapter loAdapter;

    private List<DCreditApplicationDocuments.ApplicationDocument> documentDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_to_scan);
        Toolbar toolbar = findViewById(org.rmj.guanzongroup.ghostrider.griderscanner.R.id.toolbar_client);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        mViewModel = new ViewModelProvider(this).get(VMDocumentToScan.class);
        sViewModel = new ViewModelProvider(this).get(VMMainScanner.class);
//        mViewModel.getDocumentInfos().observe(this, collectionDetails -> {
//            try {
//                plDetail = collectionDetails;
//                mViewModel.setCollectionListForPosting(collectionDetails);
//            } catch (Exception e){
//                e.printStackTrace();
//            }
//        });
        docInfo =  new ArrayList<>();
        initWidgets();
        contentResolver = this.getContentResolver();
        setData();

        ScannerConstants.TransNox = lblTransNoxxx.getText().toString();
        mViewModel.initAppDocs(TransNox);
        mViewModel.setsTransNox(TransNox);
        initFileCode();
        mViewModel.getDocumentDetailForPosting().observe(this, documentList -> {
            try {
                documentDetails = documentList;
                mViewModel.setDocumentListForPosting(documentList);
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        mViewModel.getDocumentDetailForPosting().observe(Activity_DocumentToScan.this, documentList -> mViewModel.setDocumentListForPosting(documentList));



    }
    public void initFileCode(){
        mViewModel.getDocumentInfos(TransNox).observe(Activity_DocumentToScan.this, fileCodeDetails -> {
            loAdapter = new DocumentToScanAdapter(Activity_DocumentToScan.this, fileCodeDetails, new DocumentToScanAdapter.OnItemClickListener() {
                @Override
                public void OnClick(int position) {
                    poImageInfo = new EImageInfo();
                    poDocumentsInfo = new ECreditApplicationDocuments();
                    poFilexx = new ImageFileCreator(Activity_DocumentToScan.this,
                            AppConstants.APP_PUBLIC_FOLDER,
                            AppConstants.SUB_FOLDER_CREDIT_APP,
                            fileCodeDetails.get(position).sFileCode,
                            fileCodeDetails.get(position).nEntryNox,
                            TransNox);
                    poFilexx.CreateScanFile((openCamera, camUsage, photPath, FileName, latitude, longitude) -> {
                        mCurrentPhotoPath = photPath;
                        ScannerConstants.Usage =camUsage;
                        ScannerConstants.Folder = AppConstants.APP_PUBLIC_FOLDER;
                        ScannerConstants.FileCode = fileCodeDetails.get(position).sFileCode;
                        ScannerConstants.PhotoPath = photPath;
                        ScannerConstants.EntryNox = (position + 1);
                        ScannerConstants.FileName = FileName;
                        ScannerConstants.FileDesc = fileCodeDetails.get(position).sBriefDsc;
                        ScannerConstants.Latt = latitude;
                        ScannerConstants.Longi = longitude;
                        startActivityForResult(openCamera, ImageFileCreator.GCAMERA);
                    });
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu_credit_app_list, menu);

        return true;
    }

    public void initWidgets(){
        poDialogx = new LoadDialog(Activity_DocumentToScan.this);
        poMessage = new MessageBox(Activity_DocumentToScan.this);
        recyclerView = findViewById(R.id.recyclerview_docScan);
        layoutManager = new LinearLayoutManager(Activity_DocumentToScan.this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);

        lblTransNoxxx = findViewById(R.id.lbl_list_transNox);
        lblClientName = findViewById(R.id.lbl_list_applicantName);
        lblAppltnDate = findViewById(R.id.lbl_list_applicationDate);
        lblStatus = findViewById(R.id.lbl_list_applicationWithCI);
        lblModelName = findViewById(R.id.lbl_modelName);
        lblAccntTern = findViewById(R.id.lbl_accntTerm);
        lblMobileNo = findViewById(R.id.lbl_mobileNo);
        documentDetails = new ArrayList<>();

    }

    public void setData(){
        TransNox = getIntent().getStringExtra("TransNox");
        lblTransNoxxx.setText("TransNox. :" + TransNox);
        lblClientName.setText(getIntent().getStringExtra("ClientNm"));
        lblAppltnDate.setText(getIntent().getStringExtra("dTransact"));
        lblStatus.setText(getIntent().getStringExtra("Status"));
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
                startActivityForResult(new Intent(this, ImageCrop.class), CROP_REQUEST_CODE);

            }else {
                ScannerConstants.selectedImageBitmap = null;
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
                    mViewModel.PostDocumentScanDetail(poImageInfo,poDocumentsInfo, new ViewModelCallBack() {
                        @Override
                        public void OnStartSaving() {
                            poDialogx.initDialog("Credit Online \nApplication Documents", "Posting " + ScannerConstants.FileDesc + " details. Please wait...", false);
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
                            poMessage.setPositiveButton("Okay", (view, dialog) ->{
//                                initFileCode();
                                dialog.dismiss();
                            });
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
                    contentResolver, Uri.fromFile(new File(photoPath)));
        } catch (IOException e) {
            return false;
        }

        return ScannerConstants.selectedImageBitmap != null;
    }



}