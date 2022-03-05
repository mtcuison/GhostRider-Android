package org.guanzongroup.com.creditevaluation.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import org.guanzongroup.com.creditevaluation.R;
import org.guanzongroup.com.creditevaluation.ViewModel.VMEvaluation;
import org.rmj.g3appdriver.GRider.Etc.LoadDialog;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;

import java.util.Objects;

public class Activity_Evaluation extends AppCompatActivity {

    private VMEvaluation mViewModel;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private TextInputEditText txtPersonnel, txtPosition, txtMobileNo;
    private TextInputEditText txtNeighbor1, txtNeighbor2, txtNeighbor3;
    private RadioGroup rgHasRecord;

    private TextView lblTransNox, sCompnyNm, dTransact, sModelNm, nTerm, nMobile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation);
        initWidgets();
        initIntentData();
        mViewModel = new ViewModelProvider(this).get(VMEvaluation.class);
    }
    private void initIntentData(){
        lblTransNox.setText("Transaction No.: " + getIntent().getStringExtra("transno"));
        sCompnyNm.setText(getIntent().getStringExtra("ClientNm"));
        dTransact.setText(getIntent().getStringExtra("dTransact"));
        sModelNm.setText(getIntent().getStringExtra("ModelName"));
        nTerm.setText(getIntent().getStringExtra("term") + " Month/s");
        nMobile.setText(getIntent().getStringExtra("MobileNo"));
    }
    private void initWidgets(){
        Toolbar toolbar = findViewById(R.id.toolbar_ci_evaluation);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.recyclerview_ci_evaluation);
        layoutManager = new LinearLayoutManager(Activity_Evaluation.this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        txtPersonnel = findViewById(R.id.tie_ci_asstPersonel);
        txtPosition = findViewById(R.id.tie_ci_asstPosition);
        txtMobileNo = findViewById(R.id.tie_ci_asstPhoneNo);

        txtNeighbor1 = findViewById(R.id.tie_ci_neighborName1);
        txtNeighbor2 = findViewById(R.id.tie_ci_neighborName2);
        txtNeighbor3 = findViewById(R.id.tie_ci_neighborName3);
        rgHasRecord = findViewById(R.id.rg_ci_brgyRecord);
        lblTransNox = findViewById(R.id.lbl_ci_transNox);
        sCompnyNm = findViewById(R.id.lbl_ci_applicantName);
        dTransact = findViewById(R.id.lbl_ci_applicationDate);
        sModelNm = findViewById(R.id.lbl_ci_modelName);
        nTerm = findViewById(R.id.lbl_ci_accntTerm);
        nMobile = findViewById(R.id.lbl_ci_mobileNo);
    }
}