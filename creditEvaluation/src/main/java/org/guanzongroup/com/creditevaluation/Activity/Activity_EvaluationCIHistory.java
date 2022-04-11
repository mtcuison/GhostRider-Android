/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.creditEvaluation
 * Electronic Personnel Access Control Security System
 * project file created : 3/17/22, 10:36 AM
 * project file last modified : 3/17/22, 10:36 AM
 */

package org.guanzongroup.com.creditevaluation.Activity;

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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import org.guanzongroup.com.creditevaluation.Adapter.CreditEvaluationListAdapter;
import org.guanzongroup.com.creditevaluation.R;
import org.guanzongroup.com.creditevaluation.ViewModel.VMEvaluationHistory;
import org.guanzongroup.com.creditevaluation.ViewModel.VMEvaluationList;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DCreditOnlineApplicationCI;
import org.rmj.g3appdriver.GRider.Etc.LoadDialog;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Activity_EvaluationCIHistory extends AppCompatActivity implements VMEvaluationHistory.OnImportCallBack{
    private static final String TAG = Activity_EvaluationCIHistory.class.getSimpleName();
    private RecyclerView recyclerViewClient;
    private VMEvaluationHistory mViewModel;
    private LinearLayoutManager layoutManager;
    private CreditEvaluationListAdapter adapter;
    private LinearLayout loading;
    private List<DCreditOnlineApplicationCI.oDataEvaluationInfo> ciEvaluationList;
    private LoadDialog poDialogx;
    private MessageBox poMessage;
    private String userBranch;
    private TextInputEditText txtSearch;
    private TextView layoutNoRecord;
    private TextView lblBranch,lblAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation_cihistory);
        initWidgets();
        mViewModel = new ViewModelProvider(Activity_EvaluationCIHistory.this).get(VMEvaluationHistory.class);
//        mViewModel.importApplicationInfo(Activity_EvaluationCIHistory.this);
        downloadApplicationsForBHApproval();
        mViewModel.getUserBranchInfo().observe(this, eBranchInfo -> {
            try {
                lblBranch.setText(eBranchInfo.getBranchNm());
                lblAddress.setText(eBranchInfo.getAddressx());
            } catch (Exception e){
                e.printStackTrace();
            }
        });
        initData();

    }
    private void initWidgets() {

        Toolbar toolbar = findViewById(R.id.toolbar_creditEvalutionList);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        poDialogx = new LoadDialog(Activity_EvaluationCIHistory.this);
        poMessage = new MessageBox(Activity_EvaluationCIHistory.this);

        recyclerViewClient = findViewById(R.id.recyclerview_ciList);
        txtSearch = findViewById(R.id.txt_ci_search);
        layoutManager = new LinearLayoutManager(Activity_EvaluationCIHistory.this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        loading = findViewById(R.id.linear_progress);
        layoutNoRecord = findViewById(R.id.layout_ci_noRecord);
        lblBranch = findViewById(R.id.lblBranch);
        lblAddress = findViewById(R.id.lblAddress);
    }

    @Override
    public void onStartImport() {
        poDialogx.initDialog("CI Evaluation List", "Importing latest data. Please wait...", false);
        poDialogx.show();
//        initData();
    }

    @Override
    public void onSuccessImport() {
        poDialogx.dismiss();
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void initData() {
        mViewModel.getForEvaluationListDataPreview().observe(Activity_EvaluationCIHistory.this, ciList -> {
            Log.e("size", String.valueOf(ciList.size()));
            try {
                if (ciList.size() > 0) {
                    loading.setVisibility(View.GONE);
                    ciEvaluationList = new ArrayList<>();
                    for (int x = 0; x < ciList.size(); x++) {
                        DCreditOnlineApplicationCI.oDataEvaluationInfo loan = new DCreditOnlineApplicationCI.oDataEvaluationInfo();
                        loan.sTransNox = (ciList.get(x).sTransNox);
                        loan.dTransact = (ciList.get(x).dTransact);
                        loan.sCredInvx = (ciList.get(x).sCredInvx);
                        loan.sClientNm = (ciList.get(x).sClientNm);
                        loan.nAcctTerm = (ciList.get(x).nAcctTerm);
                        loan.nDownPaym = (ciList.get(x).nDownPaym);
                        loan.sAddressx = (ciList.get(x).sAddressx);
                        loan.sAddrFndg = (ciList.get(x).sAddrFndg);
                        loan.sAssetsxx = (ciList.get(x).sAssetsxx);
                        loan.sAsstFndg = (ciList.get(x).sAsstFndg);
                        loan.sIncomexx = (ciList.get(x).sIncmFndg);
                        loan.cHasRecrd = (ciList.get(x).cHasRecrd);
                        loan.sBranchNm = (ciList.get(x).sBranchNm);
                        loan.sRecrdRem = (ciList.get(x).sRecrdRem);
                        loan.sRcmdtnx1 = (ciList.get(x).sRcmdtnx1);

                        Log.e("sRcmdtnx1 ", ciList.get(x).sRcmdtnx1);
                        ciEvaluationList.add(loan);
                        continue;
                    }


                    String json = new Gson().toJson(ciEvaluationList);

                    adapter = new CreditEvaluationListAdapter(ciEvaluationList, "CI Evaluation History", new CreditEvaluationListAdapter.OnApplicationClickListener() {
                        @Override
                        public void OnClick(int position, List<DCreditOnlineApplicationCI.oDataEvaluationInfo> ciEvaluationLists) {
//                            poMessage.initDialog();
//                            poMessage.setTitle("CI Evaluation History");
//                            poMessage.setMessage("No corresponding feature has been set.");
//                            poMessage.setPositiveButton("Okay", (view, dialog) -> dialog.dismiss());
//                            poMessage.show();
//
                            Intent loIntent = new Intent(Activity_EvaluationCIHistory.this, Activity_CIHistoryPreview.class);
                            loIntent.putExtra("sTransNox", ciEvaluationLists.get(position).sTransNox);
                            loIntent.putExtra("sClientNm", ciEvaluationLists.get(position).sClientNm);
                            loIntent.putExtra("dTransact", ciEvaluationLists.get(position).dTransact);
                            loIntent.putExtra("sBranchxx", ciEvaluationLists.get(position).sBranchNm);
                            startActivity(loIntent);


                        }

                    });
                    LinearLayoutManager layoutManager = new LinearLayoutManager(Activity_EvaluationCIHistory.this);
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
                                if (adapter.getItemCount() == 0) {
                                    layoutNoRecord.setVisibility(View.VISIBLE);
                                } else {
                                    layoutNoRecord.setVisibility(View.GONE);
                                }
                                adapter.notifyDataSetChanged();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            if (adapter.getItemCount() == 0) {
                                layoutNoRecord.setVisibility(View.VISIBLE);
                            } else {
                                layoutNoRecord.setVisibility(View.GONE);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    });
                } else {
//                    mViewModel.ImportCIApplications(Activity_EvaluationCIHistory.this);
                    layoutNoRecord.setVisibility(View.VISIBLE);
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_intent_slide_in_left, R.anim.anim_intent_slide_out_right);
    }

    private void downloadApplicationsForBHApproval() {
        LoadDialog loDialog = new LoadDialog(Activity_EvaluationCIHistory.this);
        mViewModel.DownloadApplicationsForBHApproval(new VMEvaluationHistory.OnTransactionCallback() {
            @Override
            public void onSuccess(String message) {
                loDialog.dismiss();
            }

            @Override
            public void onFailed(String message) {
                loDialog.dismiss();
                Log.e(Activity_EvaluationCIHistory.class.getSimpleName(), message);
            }

            @Override
            public void onLoad() {
                loDialog.initDialog("CI Evaluation", "CI Evaluation downlading.. Please wait.", true);
                loDialog.show();
            }
        });
    }

}