package org.rmj.guanzongroup.onlinecreditapplication.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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

import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.GRider.Etc.GToast;
import org.rmj.g3appdriver.etc.SessionManager;
import org.rmj.g3appdriver.etc.WebFileServer;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.guanzongroup.ghostrider.imgcapture.ImageFileCreator;
import org.rmj.guanzongroup.onlinecreditapplication.Adapter.LoanApplication;
import org.rmj.guanzongroup.onlinecreditapplication.Adapter.UserLoanHistoryAdapter;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;
import org.rmj.guanzongroup.onlinecreditapplication.R;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMApplicationHistory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Activity_ApplicationHistory extends AppCompatActivity implements ViewModelCallBack {
    private static final String TAG = Activity_ApplicationHistory.class.getSimpleName();

    private VMApplicationHistory mViewModel;

    private Toolbar toolbar;
    private TextInputEditText txtSearch;
    private RecyclerView recyclerView;
    private LinearLayout noRecord;

    private List<LoanApplication> loanList;
    private UserLoanHistoryAdapter adapter;

    private ImageFileCreator poCamera;
    private EImageInfo poImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_history);
        initWidgets();
        poImage = new EImageInfo();
        mViewModel = new ViewModelProvider(this).get(VMApplicationHistory.class);
        mViewModel.LoadApplications(Activity_ApplicationHistory.this);
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
                    loan.setDateApproved(applicationLogs.get(x).dVerified);
                    loanList.add(loan);
                }
                adapter = new UserLoanHistoryAdapter(loanList, new UserLoanHistoryAdapter.LoanApplicantListActionListener() {
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
                    public void OnPreview(String TransNox) {

                    }

                    @Override
                    public void OnCamera(String TransNox) {

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
        poImage.setMD5Hashx(WebFileServer.createMD5Hash(poImage.getFileLoct()));
        mViewModel.saveImageFile(poImage);
    }

    private class CheckImageFileTask extends AsyncTask<String, Void, String>{
        private String TransNox;
        private ConnectionUtil poConn;
        private SessionManager poUser;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            poConn = new ConnectionUtil(Activity_ApplicationHistory.this);
            poUser = new SessionManager(Activity_ApplicationHistory.this);
        }

        @Override
        protected String doInBackground(String... strings) {
            TransNox = strings[0];
            String lsResponse = "";
            if(poConn.isDeviceConnected()){
                String lsClient = WebFileServer.RequestClientToken("IntegSys",
                        poUser.getClientId(),
                        poUser.getUserID());
                String lsAccess = WebFileServer.RequestAccessToken(lsClient);

                if(!lsAccess.isEmpty()){
                    org.json.simple.JSONObject loUpload = WebFileServer.CheckFile(lsAccess,
                            "0029",
                            poUser.getBranchCode(),
                            "COAD",
                            TransNox);

                    lsResponse = (String) loUpload.get("result");
                    Log.e(TAG, "Uploading image result : " + lsResponse);

                    if (Objects.requireNonNull(lsResponse).equalsIgnoreCase("success")) {
                        String lsTransNo = (String) loUpload.get("sTransNox");
                        //poImgMngr.updateImageInfo(lsTransNo, poImage.getTransNox());
                    } else {
                        lsResponse = AppConstants.LOCAL_EXCEPTION_ERROR("Loan application has been sent. But failed to upload customer photo.");
                    }
                }
            }
            return lsResponse;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            poCamera = new ImageFileCreator(Activity_ApplicationHistory.this,
                    AppConstants.APP_PUBLIC_FOLDER,
                    AppConstants.SUB_FOLDER_CREDIT_APP,
                    "0029",
                    20,
                    TransNox);
            poCamera.CreateScanFile((openCamera, camUsage, photPath, FileName, latitude, longitude) -> {
                poImage.setFileLoct(photPath);
                poImage.setFileCode("0029");
                poImage.setSourceCD("COAD");
                poImage.setLatitude(String.valueOf(latitude));
                poImage.setLongitud(String.valueOf(longitude));
                poImage.setSourceNo(TransNox);
                poImage.setImageNme(FileName);
                poImage.setCaptured(AppConstants.DATE_MODIFIED);
                startActivityForResult(openCamera, ImageFileCreator.GCAMERA);
            });
        }
    }
}