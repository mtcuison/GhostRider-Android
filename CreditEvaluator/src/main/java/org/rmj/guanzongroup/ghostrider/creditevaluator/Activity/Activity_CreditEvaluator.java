package org.rmj.guanzongroup.ghostrider.creditevaluator.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.textfield.TextInputEditText;

import org.rmj.g3appdriver.GRider.Etc.LoadDialog;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Adapter.CreditEvaluationListAdapter;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Adapter.CreditEvaluatorAdapter;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Model.CreditEvaluationModel;
import org.rmj.guanzongroup.ghostrider.creditevaluator.R;
import org.rmj.guanzongroup.ghostrider.creditevaluator.ViewModel.VMCreditEvaluator;
import org.rmj.guanzongroup.ghostrider.creditevaluator.ViewModel.VMEvaluationList;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Activity_CreditEvaluator extends AppCompatActivity implements VMCreditEvaluator.OnImportCallBack {
    private static final String TAG = Activity_CreditEvaluator.class.getSimpleName();
    private RecyclerView recyclerViewClient;
    private VMCreditEvaluator mViewModel;
    private LinearLayoutManager layoutManager;
    private CreditEvaluatorAdapter adapter;
    private LinearLayout loading;
    private List<CreditEvaluationModel> creditList;
    private LoadDialog poDialogx;
    private MessageBox poMessage;
    private String userBranch;
    private TextInputEditText txtSearch;
    private LinearLayout layoutNoRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_evaluator);
        initWidgets();

        mViewModel = new ViewModelProvider(Activity_CreditEvaluator.this).get(VMCreditEvaluator.class);
//        mViewModel.ImportCIApplications(Activity_CreditEvaluator.this);
        mViewModel.ImportCIApplications(Activity_CreditEvaluator.this);
        initData();



    }
    private void initWidgets(){
        Toolbar toolbar = findViewById(R.id.toolbar_evaluator);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        poDialogx = new LoadDialog(Activity_CreditEvaluator.this);
        poMessage = new MessageBox(Activity_CreditEvaluator.this);

        recyclerViewClient = findViewById(R.id.recyclerview_evaluatorList);
        txtSearch = findViewById(R.id.txt_evaluator_search);
        layoutManager = new LinearLayoutManager(Activity_CreditEvaluator.this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        loading = findViewById(R.id.linear_progress);
        layoutNoRecord = findViewById(R.id.layout_noRecord);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onSuccessImport() {
        poDialogx.dismiss();
        initData();
    }



    @Override
    public void onStartImport() {
        poDialogx.initDialog("CI Evaluation List\nHistory", "Importing latest data. Please wait...", false);
        poDialogx.show();
    }

    @Override
    public void onImportFailed(String message) {
        poDialogx.dismiss();
        poMessage.initDialog();
        poMessage.setTitle("CI Evaluation List\nHistory");
        poMessage.setMessage(message);
        poMessage.setPositiveButton("Okay", (view, dialog) -> dialog.dismiss());
        poMessage.show();
    }
    public void initData(){
        mViewModel.getAllCICreditApplicationLog().observe(Activity_CreditEvaluator.this, brnCreditList -> {
            if(brnCreditList.size()>0) {
                loading.setVisibility(View.GONE);
                creditList = new ArrayList<>();
                for (int x = 0; x < brnCreditList.size(); x++) {
                    CreditEvaluationModel loan = new CreditEvaluationModel();
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
                    creditList.add(loan);
                    if (brnCreditList.get(x).getCreatedX().equalsIgnoreCase("GAP0190799")){
                        Log.e(TAG, "sCredInvx = " + brnCreditList.get(x).getCredInvx());
                    }

                }
                adapter = new CreditEvaluatorAdapter(creditList, new CreditEvaluatorAdapter.OnApplicationClickListener() {
                    @Override
                    public void OnClick(int position, List<CreditEvaluationModel> creditLists) {
//                                mViewModel.getDocument(creditLists.get(position).getTransNox()).observe(Activity_CreditEvaluator.this, data -> {
//                                    mViewModel.setDocumentInfo(data);
//                                });

//                                Intent loIntent = new Intent(Activity_CreditEvaluator.this, Activity_DocumentToScan.class);
//                                loIntent.putExtra("TransNox",creditLists.get(position).getsTransNox());
//                                loIntent.putExtra("ClientNm",creditLists.get(position).getsCompnyNm());
//                                loIntent.putExtra("dTransact",creditLists.get(position).getdTransact());
//                                loIntent.putExtra("ModelName",creditLists.get(position).getsModelNme());
//                                loIntent.putExtra("AccntTerm",creditLists.get(position).getnAcctTerm());
//                                loIntent.putExtra("MobileNo",creditLists.get(position).getsMobileNo());
//                                loIntent.putExtra("Status",creditLists.get(position).getTransactionStatus());
//                                startActivity(loIntent);
                    }

                });
                LinearLayoutManager layoutManager = new LinearLayoutManager(Activity_CreditEvaluator.this);
                recyclerViewClient.setAdapter(adapter);
                recyclerViewClient.setLayoutManager(layoutManager);
                adapter.notifyDataSetChanged();

                txtSearch.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        try {

                            adapter.getFilter().filter(s.toString());
                            adapter.notifyDataSetChanged();
                            if (adapter.getItemCount() == 0){
                                layoutNoRecord.setVisibility(View.VISIBLE);

                            }else {
                                layoutNoRecord.setVisibility(View.GONE);
                            }
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }else {
                layoutNoRecord.setVisibility(View.VISIBLE);
            }
        });
    }

}