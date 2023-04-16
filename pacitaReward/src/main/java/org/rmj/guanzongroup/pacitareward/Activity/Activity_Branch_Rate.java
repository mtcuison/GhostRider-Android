package org.rmj.guanzongroup.pacitareward.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import org.rmj.g3appdriver.dev.Database.Entities.EPacitaEvaluation;
import org.rmj.g3appdriver.dev.Database.Entities.EPacitaRule;
import org.rmj.g3appdriver.etc.LoadDialog;
import org.rmj.g3appdriver.etc.MessageBox;
import org.rmj.g3appdriver.lib.GawadPacita.Obj.PacitaRule;
import org.rmj.g3appdriver.lib.GawadPacita.pojo.BranchRate;
import org.rmj.guanzongroup.pacitareward.Adapter.RecyclerViewAdapter_BranchRate;
import org.rmj.guanzongroup.pacitareward.R;
import org.rmj.guanzongroup.pacitareward.ViewModel.VMBranchRate;

import java.util.ArrayList;
import java.util.List;

public class Activity_Branch_Rate extends AppCompatActivity {
    private String TAG = Activity_Branch_Rate.class.getSimpleName();
    private RecyclerView rate_list;
    private MaterialToolbar toolbar;
    private MaterialTextView rate_title;
    private VMBranchRate mViewModel;
    private MessageBox loadDialog;
    private LoadDialog poLoad;
    private String intentDataBranchcd;
    private String intentDataBranchName;
    private String dialogTitle;
    private String dialogMessage;
    private MaterialButton btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_rate);

        mViewModel = new ViewModelProvider(this).get(VMBranchRate.class);

        poLoad = new LoadDialog(Activity_Branch_Rate.this);

        loadDialog = new MessageBox(Activity_Branch_Rate.this);
        loadDialog.initDialog();
        loadDialog.setTitle(dialogTitle);
        loadDialog.setMessage(dialogMessage);
        loadDialog.setPositiveButton("OK", new MessageBox.DialogButton() {
            @Override
            public void OnButtonClick(View view, AlertDialog dialog) {
                dialog.dismiss();
                finish();
            }
        });

        intentDataBranchcd = getIntent().getStringArrayExtra("Branch")[0];
        intentDataBranchName = getIntent().getStringArrayExtra("Branch")[1];

        toolbar = findViewById(R.id.toolbar);
        rate_list = findViewById(R.id.rate_list);
        rate_title = findViewById(R.id.rate_title);
        btn_submit = findViewById(R.id.btn_submit);

        rate_title.setText(intentDataBranchName);

        /*TOOL BAR*/
        setSupportActionBar(toolbar); //set object toolbar as default action bar for activity
        getSupportActionBar().setTitle("Branch Rate"); //set default title for action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //set back button to toolbar
        getSupportActionBar().setDisplayShowHomeEnabled(true); //enable the back button set on toolbar

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mViewModel.InitializeEvaluation(intentDataBranchcd, new VMBranchRate.OnInitializeBranchEvaluationListener() {
            @Override
            public void OnInitialize(String transactNo) {
                mViewModel.getBranchEvaluation(transactNo).observe(Activity_Branch_Rate.this, new Observer<EPacitaEvaluation>() {
                    @Override
                    public void onChanged(EPacitaEvaluation ePacitaEvaluation) {
                        if(ePacitaEvaluation == null){
                            dialogTitle = "No Records";
                            dialogMessage = "No records found for branch " + intentDataBranchName;
                            return;
                        }
                        mViewModel.GetCriteria().observe(Activity_Branch_Rate.this, new Observer<List<EPacitaRule>>() {
                            @Override
                            public void onChanged(List<EPacitaRule> ePacitaRules) {
                                if(ePacitaRules == null){
                                    dialogTitle = "No Records";
                                    dialogMessage = "No Pacita Rules found";
                                    loadDialog.show();
                                    return;
                                }
                                if(ePacitaRules.size() == 0){
                                    dialogTitle = "No Records";
                                    dialogMessage = "No Pacita Rules found";
                                    loadDialog.show();
                                    return;
                                }

                                String lsPayload = ePacitaEvaluation.getPayloadx();
                                List<BranchRate> loRate = PacitaRule.ParseBranchRate(lsPayload, ePacitaRules);

                                RecyclerViewAdapter_BranchRate viewAdapter = new RecyclerViewAdapter_BranchRate(Activity_Branch_Rate.this, loRate,
                                        transactNo, new RecyclerViewAdapter_BranchRate.onSelect() {
                                    @Override
                                    public void onItemSelect(String EntryNox, String result) {
                                        mViewModel.setEvaluationResult(transactNo, EntryNox, result);
                                    }
                                });
                                rate_list.setAdapter(viewAdapter);
                                rate_list.setLayoutManager(new LinearLayoutManager(Activity_Branch_Rate.this, LinearLayoutManager.VERTICAL, false));
                            }
                        });
                    }
                });

                btn_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewModel.saveBranchRatings(transactNo, new VMBranchRate.BranchRatingsCallback() {
                            @Override
                            public void onSave(String title, String message) {
                                poLoad.initDialog(title, message, false);
                                poLoad.show();
                            }

                            @Override
                            public void onSuccess(String message) {
                                poLoad.dismiss();
                                dialogTitle = "Success Saving Application";
                                dialogMessage = message;
                                loadDialog.show();
                            }

                            @Override
                            public void onFailed(String message) {
                                poLoad.dismiss();
                                dialogTitle = "Success Saving Application";
                                dialogMessage = message;
                                loadDialog.show();
                            }
                        });
                    }
                });
            }
            @Override
            public void OnError(String message) {
                dialogTitle = "Message Error";
                dialogMessage = message;
                loadDialog.show();
            }
        });
    }
}