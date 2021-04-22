package org.rmj.guanzongroup.ghostrider.creditevaluator.Activity;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import org.rmj.g3appdriver.GRider.Etc.GToast;
import org.rmj.g3appdriver.GRider.Etc.LoadDialog;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Adapter.CreditEvaluationListAdapter;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Dialog.DialogAddApplication;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Model.CreditEvaluationModel;
import org.rmj.guanzongroup.ghostrider.creditevaluator.R;
import org.rmj.guanzongroup.ghostrider.creditevaluator.ViewModel.VMEvaluationList;
import org.rmj.guanzongroup.ghostrider.creditevaluator.ViewModel.ViewModelCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Activity_EvaluationList extends AppCompatActivity implements ViewModelCallback, VMEvaluationList.OnImportCallBack {
    private static final String TAG = Activity_EvaluationList.class.getSimpleName();
    private RecyclerView recyclerViewClient;
    private VMEvaluationList mViewModel;
    private LinearLayoutManager layoutManager;
    private CreditEvaluationListAdapter adapter;
    private LinearLayout loading;
    private List<CreditEvaluationModel> ciEvaluationList;
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
                         // TODO: import single application
                        mViewModel.importApplicationInfo("fsTransNo", Activity_EvaluationList.this);
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
        mViewModel.getCICreditApplication().observe(Activity_EvaluationList.this, ciList -> {
            if(ciList.size()>0) {
                loading.setVisibility(View.GONE);
                ciEvaluationList = new ArrayList<>();
                for (int x = 0; x < ciList.size(); x++) {
                    CreditEvaluationModel loan = new CreditEvaluationModel();
                    loan.setsTransNox(ciList.get(x).getTransNox());
                    loan.setdTransact(ciList.get(x).getTransact());
                    loan.setsCredInvx(ciList.get(x).getCredInvx());
                    loan.setsCompnyNm(ciList.get(x).getCompnyNm());
                    loan.setsSpouseNm(ciList.get(x).getSpouseNm());
                    loan.setsAddressx(ciList.get(x).getAddressx());
                    loan.setsMobileNo(ciList.get(x).getMobileNo());
                    loan.setsQMAppCde(ciList.get(x).getQMAppCde());
                    loan.setsModelNme(ciList.get(x).getModelNme());
                    loan.setnDownPaym(ciList.get(x).getDownPaym());
                    loan.setnAcctTerm(ciList.get(x).getAcctTerm());
                    loan.setcTranStat(ciList.get(x).getTranStat());
                    loan.setdTimeStmp(ciList.get(x).getTimeStmp());
                    ciEvaluationList.add(loan);

                }
                String json = new Gson().toJson(ciEvaluationList);
                Log.e(TAG, json);
                adapter = new CreditEvaluationListAdapter(ciEvaluationList, new CreditEvaluationListAdapter.OnApplicationClickListener() {
                    @Override
                    public void OnClick(int position, List<CreditEvaluationModel> ciEvaluationLists) {
//
                        Intent loIntent = new Intent(Activity_EvaluationList.this, Activity_CIApplication.class);
                        loIntent.putExtra("transno",ciEvaluationLists.get(position).getsTransNox());
                        loIntent.putExtra("ClientNm",ciEvaluationLists.get(position).getsCompnyNm());
                        loIntent.putExtra("dTransact",ciEvaluationLists.get(position).getdTransact());
                        loIntent.putExtra("ModelName",ciEvaluationLists.get(position).getsModelNme());
                        loIntent.putExtra("MobileNo",ciEvaluationLists.get(position).getsMobileNo());
                        loIntent.putExtra("term",ciEvaluationLists.get(position).getnAcctTerm());
                        loIntent.putExtra("Status",ciEvaluationLists.get(position).getTransactionStatus());
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

    @Override
    public void OnStartSaving() {

    }

    @Override
    public void OnSuccessResult(String[] args) {

    }

    @Override
    public void OnFailedResult(String message) {

    }
}