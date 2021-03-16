package org.rmj.guanzongroup.ghostrider.griderscanner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
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

import com.google.android.material.button.MaterialButton;

import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplicationDocuments;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.etc.WebFileServer;
import org.rmj.guanzongroup.ghostrider.griderscanner.adapter.FileCodeAdapter;
import org.rmj.guanzongroup.ghostrider.griderscanner.adapter.LoanApplication;
import org.rmj.guanzongroup.ghostrider.griderscanner.helpers.ScannerConstants;
import org.rmj.guanzongroup.ghostrider.griderscanner.model.CreditAppDocumentModel;
import org.rmj.guanzongroup.ghostrider.griderscanner.viewModel.VMClientInfo;
import org.rmj.guanzongroup.ghostrider.griderscanner.viewModel.VMMainScanner;
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
    TextView lblGoCasNoxxx;
    TextView lblTransNoxxx;
    TextView lblClientName;
    TextView lblAppltnDate;
    TextView lblApplResult;
    TextView lblDateApprov;
    TextView lblDateSentxx;
    TextView lblSentStatus;

    private List<LoanApplication> plLoanApp;
    private int position;
    public static CreditAppDocumentModel infoModel;
//    Grider initialization

    public ImageView imgBitmap;
    public static String mCurrentPhotoPath;
    public MaterialButton mbCamera, mbGallery, mbCropOkay;
    public static ImageFileCreator poFilexx;
    private final String FOLDER_NAME = "DocumentScan";
    public static ContentResolver contentResolver;
    public static int CAMERA_REQUEST_CODE = 1231, GALLERY_REQUEST_CODE = 1111, CROP_REQUEST_CODE = 1234;

    public static EImageInfo poImageInfo;
    public static ECreditApplicationDocuments poDocumentsInfo;
    String TransNox, FileCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_info);
        Toolbar toolbar = findViewById(R.id.toolbar_client);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        mViewModel = new ViewModelProvider(this).get(VMClientInfo.class);
        plLoanApp =  new ArrayList<>();
        infoModel = new CreditAppDocumentModel();
        position = getIntent().getIntExtra("position", 0);
        plLoanApp = (List<LoanApplication>) getIntent().getSerializableExtra("loanList");
        initWidgets();
        contentResolver = this.getContentResolver();
        setData(position,plLoanApp);
        mViewModel.getImgInfo().observe(ClientInfo.this, data -> mViewModel.setImgInfo(data));
        mViewModel.getDocument(plLoanApp.get(position).getTransNox(), position).observe(this, data -> mViewModel.setDocumentInfo(data));

        mViewModel.getFileCode().observe(this, fileCodeDetails -> {

            FileCodeAdapter loAdapter = new FileCodeAdapter(mViewModel.getDocInfo(),fileCodeDetails, new FileCodeAdapter.OnItemClickListener() {
                @Override
                public void OnClick(int position) {
                    poFilexx = new ImageFileCreator(ClientInfo.this , "Credit Application Documents", "SCAN", fileCodeDetails.get(position).getFileCode(), plLoanApp.get(position).getTransNox());
                    poFilexx.CreateScanFile((openCamera, camUsage, photPath, FileName, latitude, longitude) -> {
                        mCurrentPhotoPath = photPath;
                        ScannerConstants.Usage =camUsage;
                        ScannerConstants.Folder = "Credit Application Documents";

                        ScannerConstants.TransNox = plLoanApp.get(position).getTransNox() ;
                        ScannerConstants.FileCode = fileCodeDetails.get(position).getFileCode();
                        ScannerConstants.PhotoPath = photPath;
                        ScannerConstants.EntryNox = position;
                        ScannerConstants.FileName = FileName;
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
    public void setData(int pos, List<LoanApplication> list){

        Log.e("position", String.valueOf(pos));
        Log.e("List", String.valueOf(list));
        LoanApplication poLoan = list.get(pos);
        lblGoCasNoxxx.setText("GOCas No. :"+poLoan.getGOCasNumber());
        lblTransNoxxx.setText(poLoan.getTransNox());
        lblClientName.setText(poLoan.getClientName());
        lblAppltnDate.setText(poLoan.getDateTransact());
        lblApplResult.setText(poLoan.getTransactionStatus());
        try {
            lblDateApprov.setText(poLoan.getDateApproved());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        lblGoCasNoxxx.setText(poLoan.getGOCasNumber());
        lblClientName.setText(poLoan.getClientName());
        lblTransNoxxx.setText(poLoan.getTransNox());
        lblAppltnDate.setText(poLoan.getDateTransact());
        lblApplResult.setText(poLoan.getTransactionStatus());
        try {
            lblDateApprov.setText(poLoan.getDateApproved());
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        lblSentStatus.setVisibility(poLoan.getSendStatus());
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("REQUEST CODE", String.valueOf(requestCode));
        Log.e("RESULT CODE", String.valueOf(resultCode));
        Log.e("Image Path ", mCurrentPhotoPath);

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
                    mViewModel.saveDocumentInfo(poDocumentsInfo);
                    mViewModel.saveImageInfo(poImageInfo);

                    //submitLUn(Remarksx);
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