package org.rmj.guanzongroup.ghostrider.griderscanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DEmployeeInfo;
import org.rmj.g3appdriver.dev.DeptCode;
import org.rmj.g3appdriver.etc.SessionManager;
import org.rmj.guanzongroup.ghostrider.griderscanner.adapter.ClientInfoAdapter;
import org.rmj.guanzongroup.ghostrider.griderscanner.adapter.FileCodeAdapter;
import org.rmj.guanzongroup.ghostrider.griderscanner.adapter.LoanApplication;
import org.rmj.guanzongroup.ghostrider.griderscanner.viewModel.VMMainScanner;
import org.rmj.guanzongroup.ghostrider.griderscanner.viewModel.ViewModelCallBack;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainScanner extends AppCompatActivity implements ViewModelCallBack {
    private RecyclerView recyclerViewClient;
    private VMMainScanner mViewModel;
    private LinearLayoutManager layoutManager;
    private ClientInfoAdapter adapter;
    private LinearLayout loading;
    private List<LoanApplication> loanList;
    private String userBranch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_scanner);
        Toolbar toolbar = findViewById(R.id.toolbar_docScanner);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        recyclerViewClient = findViewById(R.id.recyclerview_clienInfo);
        layoutManager = new LinearLayoutManager(MainScanner.this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        loading = findViewById(R.id.linear_progress);
        mViewModel = new ViewModelProvider(this).get(VMMainScanner.class);
        mViewModel.LoadApplications(MainScanner.this);
        mViewModel.getEmployeeInfo().observe(this, eEmployeeInfo -> {
            try {
                mViewModel.getBranchCreditApplication().observe(MainScanner.this, brnCreditList -> {
                    if(brnCreditList.size()>0) {
                        loading.setVisibility(View.GONE);
                        loanList = new ArrayList<>();
                        for (int x = 0; x < brnCreditList.size(); x++) {
                            LoanApplication loan = new LoanApplication();
                            loan.setsTransNox(brnCreditList.get(x).getTransNox());
                            loan.setdTransact(brnCreditList.get(x).getTransact());
                            loan.setsCredInvx(brnCreditList.get(x).getCredInvx());
                            loan.setsCompnyNm(brnCreditList.get(x).getCompnyNm());
                            loan.setsSpouseNm(brnCreditList.get(x).getSpouseNm());
                            loan.setsAddressx(brnCreditList.get(x).getAddressx());
                            loan.setsMobileNo(brnCreditList.get(x).getMobileNo());
                            loan.setsQMAppCde(brnCreditList.get(x).getQMAppCde());
                            loan.setsModelNme(brnCreditList.get(x).getModelNme());
                            loan.setnDownPaym(brnCreditList.get(x).getDownPaym());
                            loan.setnAcctTerm(brnCreditList.get(x).getAcctTerm());
                            loan.setcTranStat(brnCreditList.get(x).getTranStat());
                            loan.setdTimeStmp(brnCreditList.get(x).getTimeStmp());
                            loanList.add(loan);
                            Log.e("Loan List", String.valueOf(loan));
                        }
                        adapter = new ClientInfoAdapter(loanList, new ClientInfoAdapter.OnApplicationClickListener() {
                            @Override
                            public void OnClick(int position, List<LoanApplication> loanLists) {
//                                mViewModel.getDocument(loanLists.get(position).getTransNox()).observe(MainScanner.this, data -> {
//                                    mViewModel.setDocumentInfo(data);
//                                });

                                Intent loIntent = new Intent(MainScanner.this, ClientInfo.class);
                                loIntent.putExtra("TransNox",loanLists.get(position).getsTransNox());
                                loIntent.putExtra("ClientNm",loanLists.get(position).getsCompnyNm());
                                loIntent.putExtra("dTransact",loanLists.get(position).getdTransact());
                                loIntent.putExtra("ModelName",loanLists.get(position).getsModelNme());
                                loIntent.putExtra("AccntTerm",loanLists.get(position).getnAcctTerm());
                                loIntent.putExtra("MobileNo",loanLists.get(position).getsMobileNo());
                                loIntent.putExtra("Status",loanLists.get(position).getTransactionStatus());
                                startActivity(loIntent);
                            }

                        });
                        LinearLayoutManager layoutManager = new LinearLayoutManager(MainScanner.this);
                        recyclerViewClient.setAdapter(adapter);
                        recyclerViewClient.setLayoutManager(layoutManager);
                    }else {
                        Log.e("Application List ", String.valueOf(brnCreditList.toArray()));
                    }
                });
            } catch (Exception e){
                e.printStackTrace();
            }
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
    public void OnStartSaving() {

    }

    @Override
    public void onSaveSuccessResult(String args) {

    }

    @Override
    public void onFailedResult(String message) {

    }

    @Override
    public void OnSuccessResult(String[] strings) {

    }

    @Override
    public void OnFailedResult(String message) {

    }
}