/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.CreditEvaluator
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.creditevaluator.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import org.rmj.g3appdriver.GRider.Database.Repositories.RImageInfo;
import org.rmj.g3appdriver.GRider.Etc.LoadDialog;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Adapter.CreditEvaluationHistoryAdapter;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Dialog.DialogCIReason;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Model.CreditEvaluationModel;
import org.rmj.guanzongroup.ghostrider.creditevaluator.R;
import org.rmj.guanzongroup.ghostrider.creditevaluator.ViewModel.VMEvaluationHistory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Activity_EvaluationHistory extends AppCompatActivity implements VMEvaluationHistory.OnImportCallBack {
    private static final String TAG = Activity_EvaluationHistory.class.getSimpleName();
    private RecyclerView recyclerViewClient;
    private VMEvaluationHistory mViewModel;
    private RImageInfo poImage;
    private LinearLayoutManager layoutManager;
    private CreditEvaluationHistoryAdapter adapter;
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
        setContentView(R.layout.activity_evaluation_history);
        initWidgets();
        poImage = new RImageInfo(getApplication());
        mViewModel = new ViewModelProvider(Activity_EvaluationHistory.this).get(VMEvaluationHistory.class);
        mViewModel.ImportCIApplications(Activity_EvaluationHistory.this);
        initData();



    }
    private void initWidgets(){
        Toolbar toolbar = findViewById(R.id.toolbar_evaluator);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        poDialogx = new LoadDialog(Activity_EvaluationHistory.this);
        poMessage = new MessageBox(Activity_EvaluationHistory.this);

        recyclerViewClient = findViewById(R.id.recyclerview_evaluatorList);
        txtSearch = findViewById(R.id.txt_evaluator_search);
        layoutManager = new LinearLayoutManager(Activity_EvaluationHistory.this);
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
        mViewModel.getAllCICreditApplicationLog().observe(Activity_EvaluationHistory.this, ciList -> {
            if(ciList.size()>0) {
                loading.setVisibility(View.GONE);
                creditList = new ArrayList<>();
                for (int x = 0; x < ciList.size(); x++) {
                    CreditEvaluationModel loan = new CreditEvaluationModel();
                    loan.setsTransNox(ciList.get(x).sTransNox);
                    loan.setdTransact(ciList.get(x).dTransact);
                    loan.setsCredInvx(ciList.get(x).sCredInvx);
                    loan.setsCompnyNm(ciList.get(x).sCompnyNm);
                    loan.setsSpouseNm(ciList.get(x).sSpouseNm);
                    loan.setsAddressx(ciList.get(x).sAddressx);
                    loan.setsMobileNo(ciList.get(x).sMobileNo);
                    loan.setsQMAppCde(ciList.get(x).sQMAppCde);
                    loan.setsModelNme(ciList.get(x).sModelNme);
                    loan.setnDownPaym(ciList.get(x).nDownPaym);
                    loan.setnAcctTerm(ciList.get(x).nAcctTerm);
                    loan.setcTranStat(ciList.get(x).cTranStat);
                    loan.setdTimeStmp(ciList.get(x).dTimeStmp);
                    loan.setCiTranStat(ciList.get(x).ciTranStat);
                    creditList.add(loan);
                }
                adapter = new CreditEvaluationHistoryAdapter(creditList, new CreditEvaluationHistoryAdapter.OnApplicationClickListener() {
                    @Override
                    public void OnClick(int position, List<CreditEvaluationModel> creditLists) {

                        poImage.getImageLocationFromSrcId(creditLists.get(position).getsTransNox())
                                .observe(Activity_EvaluationHistory.this, imgPath ->{
                                    Intent loIntent = new Intent(Activity_EvaluationHistory.this, Activity_EvaluationHistoryInfo.class);
                                    loIntent.putExtra("sImgPathx", imgPath);
                                    loIntent.putExtra("sTransNox", creditLists.get(position).getsTransNox());
                                    loIntent.putExtra("sCompnyNm", creditLists.get(position).getsCompnyNm());
                                    loIntent.putExtra("sModelNme", creditLists.get(position).getsModelNme());
                                    loIntent.putExtra("nDownPaym", creditLists.get(position).getnDownPaym());
                                    loIntent.putExtra("nAcctTerm", creditLists.get(position).getnAcctTerm());
                                    startActivity(loIntent);
                                });
                    }

                });
                LinearLayoutManager layoutManager = new LinearLayoutManager(Activity_EvaluationHistory.this);
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
//                mViewModel.ImportCIApplications(Activity_EvaluationHistory.this);
                layoutNoRecord.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_intent_slide_in_left, R.anim.anim_intent_slide_out_right);
    }

}