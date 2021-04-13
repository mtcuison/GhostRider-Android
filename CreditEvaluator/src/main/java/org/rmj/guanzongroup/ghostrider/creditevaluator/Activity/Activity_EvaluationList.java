package org.rmj.guanzongroup.ghostrider.creditevaluator.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import org.rmj.g3appdriver.GRider.Etc.GToast;
import org.rmj.g3appdriver.GRider.Etc.LoadDialog;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Adapter.CreditEvaluationListAdapter;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Dialog.DialogAddApplication;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Model.CreditEvaluationModel;
import org.rmj.guanzongroup.ghostrider.creditevaluator.R;
import org.rmj.guanzongroup.ghostrider.creditevaluator.ViewModel.VMEvaluationList;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Activity_EvaluationList extends AppCompatActivity implements VMEvaluationList.OnImportCallBack {
    private static final String TAG = Activity_EvaluationList.class.getSimpleName();
    private RecyclerView recyclerViewClient;
    private VMEvaluationList mViewModel;
    private LinearLayoutManager layoutManager;
    private CreditEvaluationListAdapter adapter;
    private LinearLayout loading;
    private List<CreditEvaluationModel> creditList;
    private LoadDialog poDialogx;
    private MessageBox poMessage;
    private String userBranch;
    private TextInputEditText txtSearch;
    private TextView layoutNoRecord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        setContentView(R.layout.activity_evaluation_list);
        initWidgets();
        mViewModel = new ViewModelProvider(Activity_EvaluationList.this).get(VMEvaluationList.class);
//        mViewModel.ImportCIApplications(Activity_EvaluationList.this);
        initData();

    }
    private void initWidgets(){

        Toolbar toolbar = findViewById(R.id.toolbar_creditEvalutionList);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        poDialogx = new LoadDialog(Activity_EvaluationList.this);
        poMessage = new MessageBox(Activity_EvaluationList.this);

        recyclerViewClient = findViewById(R.id.recyclerview_ciList);
        txtSearch = findViewById(R.id.txt_ci_search);
        layoutManager = new LinearLayoutManager(Activity_EvaluationList.this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        loading = findViewById(R.id.linear_progress);
        layoutNoRecord = findViewById(R.id.layout_ci_noRecord);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu_credit_evaluator_list, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        } else if(item.getItemId() == R.id.action_menu_add_application) {
            try {
                DialogAddApplication loDialog = new DialogAddApplication(Activity_EvaluationList.this);
                loDialog.initDialog(new DialogAddApplication.OnDialogButtonClickListener() {
                    @Override
                    public void OnDownloadClick(Dialog Dialog, String args) {
                        GToast.CreateMessage(Activity_EvaluationList.this,
                                "Add Application is under development.",
                                GToast.INFORMATION).show();
                    }

                    @Override
                    public void OnCancel(Dialog Dialog) {
                        Dialog.dismiss();
                    }
                });
                loDialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
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
        poDialogx.initDialog("CI Evaluation List", "Importing latest data. Please wait...", false);
        poDialogx.show();
    }

    @Override
    public void onImportFailed(String message) {
        poDialogx.dismiss();
        poMessage.initDialog();
        poMessage.setTitle("CI Evaluation List");
        poMessage.setMessage(message);
        poMessage.setPositiveButton("Okay", (view, dialog) -> dialog.dismiss());
        poMessage.show();
    }
    public void initData(){
        mViewModel.getCICreditApplication().observe(Activity_EvaluationList.this, brnCreditList -> {
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
                }
                adapter = new CreditEvaluationListAdapter(creditList, new CreditEvaluationListAdapter.OnApplicationClickListener() {
                    @Override
                    public void OnClick(int position, List<CreditEvaluationModel> creditLists) {
//                                mViewModel.getDocument(creditLists.get(position).getTransNox()).observe(Activity_EvaluationList.this, data -> {
//                                    mViewModel.setDocumentInfo(data);
//                                });
                                Intent loIntent = new Intent(Activity_EvaluationList.this, Activity_CIApplication.class);
                                loIntent.putExtra("transno",creditLists.get(position).getsTransNox());
                                loIntent.putExtra("ClientNm",creditLists.get(position).getsCompnyNm());
                                loIntent.putExtra("dTransact",creditLists.get(position).getdTransact());
                                loIntent.putExtra("ModelName",creditLists.get(position).getsModelNme());
                                loIntent.putExtra("AccntTerm",creditLists.get(position).getnAcctTerm());
                                loIntent.putExtra("MobileNo",creditLists.get(position).getsMobileNo());
                                loIntent.putExtra("Status",creditLists.get(position).getTransactionStatus());
                                startActivity(loIntent);

                    }

                });
                LinearLayoutManager layoutManager = new LinearLayoutManager(Activity_EvaluationList.this);
                recyclerViewClient.setAdapter(adapter);
                recyclerViewClient.setLayoutManager(layoutManager);
                adapter.notifyDataSetChanged();
                if (adapter.getItemCount() == 0){
                    layoutNoRecord.setVisibility(View.VISIBLE);
                }else {
                    layoutNoRecord.setVisibility(View.GONE);
                }
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