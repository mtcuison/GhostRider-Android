package org.rmj.guanzongroup.pacitareward.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.appbar.MaterialToolbar;
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

import java.util.List;

public class Activity_Branch_Rate extends AppCompatActivity {
    private String TAG = Activity_Branch_Rate.class.getSimpleName();
    private RecyclerView rate_list;
    private MaterialToolbar toolbar;
    private MaterialTextView rate_title;
    private VMBranchRate mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_rate);

        mViewModel = new ViewModelProvider(this).get(VMBranchRate.class);

        toolbar = findViewById(R.id.toolbar);
        rate_list = findViewById(R.id.rate_list);
        rate_title = findViewById(R.id.rate_title);

        rate_title.setText(getIntent().getStringExtra("Branch"));

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

        mViewModel.InitializeEvaluation(getIntent().getStringExtra("Branch"), new VMBranchRate.OnInitializeBranchEvaluationListener() {
            @Override
            public void OnInitialize(String transactNo) {
                mViewModel.getBranchEvaluation(transactNo).observe(Activity_Branch_Rate.this, new Observer<EPacitaEvaluation>() {
                    @Override
                    public void onChanged(EPacitaEvaluation ePacitaEvaluation) {
                        if(ePacitaEvaluation == null){
                            Log.e(TAG, "No evaluation record found.");
                            return;
                        }
                        mViewModel.GetCriteria().observe(Activity_Branch_Rate.this, new Observer<List<EPacitaRule>>() {
                            @Override
                            public void onChanged(List<EPacitaRule> ePacitaRules) {
                                if(ePacitaRules == null){
                                    Log.e(TAG, "No pacita rules found.");
                                    return;
                                }

                                if(ePacitaRules.size() == 0){
                                    Log.e(TAG, "No pacita rules found.");
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
            }
            @Override
            public void OnError(String message) {
                MessageBox loadDialog = new MessageBox(Activity_Branch_Rate.this);
                loadDialog.initDialog();
                loadDialog.setTitle("Message Error");
                loadDialog.setMessage(message);
                loadDialog.setPositiveButton("OK", new MessageBox.DialogButton() {
                    @Override
                    public void OnButtonClick(View view, AlertDialog dialog) {
                        dialog.dismiss();
                    }
                });
                loadDialog.show();
                finish();
            }
        });
    }
}