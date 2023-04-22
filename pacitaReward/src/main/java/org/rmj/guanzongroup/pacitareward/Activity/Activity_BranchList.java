package org.rmj.guanzongroup.pacitareward.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;

import org.rmj.g3appdriver.dev.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.etc.LoadDialog;
import org.rmj.g3appdriver.etc.MessageBox;
import org.rmj.guanzongroup.pacitareward.Adapter.RecyclerViewAdapter_BranchList;
import org.rmj.guanzongroup.pacitareward.Dialog.Dialog_SelectAction;
import org.rmj.guanzongroup.pacitareward.R;
import org.rmj.guanzongroup.pacitareward.ViewModel.VMBranchList;

import java.util.List;

public class Activity_BranchList extends AppCompatActivity {

    private VMBranchList mViewModel;
    private MaterialToolbar toolbar;
    private RecyclerView rvc_branchlist;
    private TextInputEditText searchview;
    private RecyclerViewAdapter_BranchList rec_branchList;
    private LoadDialog poLoad;
    private MessageBox poMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_list);

        mViewModel = new ViewModelProvider(this).get(VMBranchList.class);

        toolbar = findViewById(R.id.toolbar);
        rvc_branchlist = findViewById(R.id.branch_list);
        searchview = findViewById(R.id.searchview);

        poLoad = new LoadDialog(Activity_BranchList.this);

        setSupportActionBar(toolbar); //set object toolbar as default action bar for activity
        getSupportActionBar().setTitle("Branch List"); //set default title for action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //set back button to toolbar
        getSupportActionBar().setDisplayShowHomeEnabled(true); //enable the back button set on toolbar

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        MessageBox loadDialog = new MessageBox(Activity_BranchList.this);
        loadDialog.initDialog();
        loadDialog.setTitle("No Records");
        loadDialog.setMessage("No Branch Records Found");
        loadDialog.setPositiveButton("OK", new MessageBox.DialogButton() {
            @Override
            public void OnButtonClick(View view, AlertDialog dialog) {
                dialog.dismiss();
            }
        });

        mViewModel.importCriteria();
        mViewModel.getBranchlist().observe(Activity_BranchList.this, new Observer<List<EBranchInfo>>() {
            @Override
            public void onChanged(List<EBranchInfo> eBranchInfos) {
                if (eBranchInfos.size() <= 0){
                    loadDialog.show();
                }
                rec_branchList = new RecyclerViewAdapter_BranchList(eBranchInfos, new RecyclerViewAdapter_BranchList.OnBranchSelectListener() {
                    @Override
                    public void OnSelect(String BranchCode, String BranchName) {
                        Dialog_SelectAction selectAction = new Dialog_SelectAction(new Dialog_SelectAction.onDialogSelect() {
                            @Override
                            public void onEvaluate(Class className, String branchCd, String branchNm, Dialog dialog) {
                                Intent intent = new Intent(Activity_BranchList.this, className);
                                intent.putExtra("Branch Code", branchCd);
                                intent.putExtra("Branch Name", branchNm);

                                startActivity(intent);
                                dialog.dismiss();
                            }

                            @Override
                            public void onViewRecords(Class className, String branchCd, String branchNm, Dialog dialog) {
                                Intent intent = new Intent(Activity_BranchList.this, className);
                                intent.putExtra("Branch Code", branchCd);
                                intent.putExtra("Branch Name", branchNm);

                                startActivity(intent);
                                dialog.dismiss();
                            }

                            @Override
                            public void onCancel(Dialog dialog) {
                                dialog.dismiss();
                            }
                        });
                        selectAction.initDialog(Activity_BranchList.this, BranchCode, BranchName);
                    }
                });

                rec_branchList.notifyDataSetChanged();
                rvc_branchlist.setAdapter(rec_branchList);
                rvc_branchlist.setLayoutManager(new LinearLayoutManager(Activity_BranchList.this));
            }
        });
        searchview.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                try{
                    if(s != null) {
                        if (!s.toString().trim().isEmpty()) {
                            String query = s.toString();
                            rec_branchList.getFilter().filter(query);
                            rec_branchList.notifyDataSetChanged();
                        }
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{
                    if(s != null) {
                        if (!s.toString().trim().isEmpty()) {
                            String query = s.toString();
                            rec_branchList.getFilter().filter(query);
                            rec_branchList.notifyDataSetChanged();
                        }
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
}