package org.rmj.guanzongroup.onlinecreditapplication.Activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.g3appdriver.GRider.Etc.GToast;
import org.rmj.guanzongroup.onlinecreditapplication.Adapter.LoanApplication;
import org.rmj.guanzongroup.onlinecreditapplication.Adapter.LoanHistoryAdapter;
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
    private RecyclerView recyclerView;

    private List<LoanApplication> loanList;
    private LoanHistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_history);
        initWidgets();
        mViewModel = new ViewModelProvider(this).get(VMApplicationHistory.class);
        mViewModel.LoadApplications(Activity_ApplicationHistory.this);
        mViewModel.getApplicationHistory().observe(Activity_ApplicationHistory.this, applicationLogs -> {
            if(applicationLogs.size()>0) {
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
                adapter = new LoanHistoryAdapter(loanList, new LoanHistoryAdapter.LoanApplicantListActionListener() {
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
                        //Intent loIntent = new Intent(Activity_ApplicationHistory.class);
                    }
                });
                LinearLayoutManager layoutManager = new LinearLayoutManager(Activity_ApplicationHistory.this);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(layoutManager);
            }
        });
    }

    private void initWidgets(){
        toolbar = findViewById(R.id.toolbar_applicationHistory);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.rectangles_applicationHistory);
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
}