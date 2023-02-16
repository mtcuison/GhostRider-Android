package org.rmj.guanzongroup.onlinecreditapplication.Activities;

import static org.rmj.guanzongroup.documentscanner.xxxImageStatic.FileCode;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.rmj.g3appdriver.etc.LoadDialog;
import org.rmj.g3appdriver.etc.MessageBox;
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.CreditAppDocs;
import org.rmj.guanzongroup.documentscanner.Activity_DocumentScan;
import org.rmj.guanzongroup.documentscanner.xxxImageStatic;
import org.rmj.guanzongroup.onlinecreditapplication.Adapter.DocumentToScanAdapter;
import org.rmj.guanzongroup.onlinecreditapplication.R;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMApplicantDocuments;

public class Activity_ApplicantDocuments extends AppCompatActivity {

    private VMApplicantDocuments mViewModel;

    private LoadDialog poDialogx;
    private MessageBox poMessage;

    private CreditAppDocs poDetail;

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private TextView lblTransNoxxx;
    private TextView lblClientName;
    private TextView lblAppltnDate;
    private TextView lblModelName;
    private TextView lblAccntTern;
    private TextView lblMobileNo;
    private TextView lblStatus;

    private final ActivityResultLauncher<Intent> poScan = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == RESULT_OK){

            } else {

            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(Activity_ApplicantDocuments.this).get(VMApplicantDocuments.class);
        setContentView(R.layout.activity_applicant_documents);
        initWidgets();
        String TransNox = getIntent().getStringExtra("TransNox");
        lblTransNoxxx.setText("TransNox. :" + TransNox);
        lblClientName.setText(getIntent().getStringExtra("ClientNm"));
        lblAppltnDate.setText(getIntent().getStringExtra("dTransact"));
        lblStatus.setText(getIntent().getStringExtra("Status"));
        lblModelName.setText(getIntent().getStringExtra("ModelName"));
        lblAccntTern.setText(getIntent().getStringExtra("AccntTerm"));
        lblMobileNo.setText(getIntent().getStringExtra("MobileNo"));

        mViewModel.InitializeDocuments(TransNox, new VMApplicantDocuments.OnInitializeCreditAppDocuments() {
            @Override
            public void OnSuccess() {

            }

            @Override
            public void OnFailed(String message) {

            }
        });

        mViewModel.GetCreditAppDocuments(TransNox).observe(Activity_ApplicantDocuments.this, documents -> {
            try{
                DocumentToScanAdapter loAdapter = new DocumentToScanAdapter(documents, new DocumentToScanAdapter.OnItemClickListener() {
                    @Override
                    public void OnClick(int position) {
                        if (documents.get(position).sSendStat == null && documents.get(position).sImageNme == null){
                            poDetail = new CreditAppDocs();
                            poDetail.setTransNox(TransNox);
                            poDetail.setFileCode(documents.get(position).sFileCode);
                            Intent loIntent = new Intent(Activity_ApplicantDocuments.this, Activity_DocumentScan.class);
                            xxxImageStatic.TransNox = TransNox;
                            xxxImageStatic.EntryNox = String.valueOf(position + 1);
                            xxxImageStatic.FileCode = documents.get(position).sFileCode;
                            poScan.launch(loIntent);
                        }
                    }

                    @Override
                    public void OnClick(String args, String args1) {

                    }
                });
                recyclerView.setAdapter(loAdapter);
            } catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    public void initWidgets(){
        poDialogx = new LoadDialog(Activity_ApplicantDocuments.this);
        poMessage = new MessageBox(Activity_ApplicantDocuments.this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Applicant Documents");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recyclerview_docScan);
        layoutManager = new LinearLayoutManager(Activity_ApplicantDocuments.this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        lblTransNoxxx = findViewById(R.id.lbl_list_transNox);
        lblClientName = findViewById(R.id.lbl_list_applicantName);
        lblAppltnDate = findViewById(R.id.lbl_list_applicationDate);
        lblStatus = findViewById(R.id.lbl_list_applicationWithCI);
        lblModelName = findViewById(R.id.lbl_modelName);
        lblAccntTern = findViewById(R.id.lbl_accntTerm);
        lblMobileNo = findViewById(R.id.lbl_mobileNo);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}