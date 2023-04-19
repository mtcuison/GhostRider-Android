package org.rmj.guanzongroup.pacitareward.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textview.MaterialTextView;

import org.rmj.g3appdriver.dev.Database.DataAccessObject.DPacita.BranchRecords;
import org.rmj.g3appdriver.etc.LoadDialog;
import org.rmj.g3appdriver.etc.MessageBox;
import org.rmj.guanzongroup.pacitareward.Adapter.RecyclerViewAdapter_BranchRecord;
import org.rmj.guanzongroup.pacitareward.R;
import org.rmj.guanzongroup.pacitareward.ViewModel.VMBranchRecords;

import java.util.List;

public class Activity_BranchRecords extends AppCompatActivity {
    private VMBranchRecords mviewModel;
    private MaterialToolbar toolbar;
    private RecyclerView branch_rec;
    private String intentDataBranchcd;
    private String intentDataBranchName;
    private MaterialTextView mtv_title;
    private LoadDialog poLoad;
    private MessageBox poMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_records);

        intentDataBranchcd = getIntent().getStringArrayExtra("Branch")[0];
        intentDataBranchName = getIntent().getStringArrayExtra("Branch")[1];

        mviewModel = new ViewModelProvider(this).get(VMBranchRecords.class);

        toolbar = findViewById(R.id.toolbar);
        mtv_title = findViewById(R.id.mtv_title);
        branch_rec = findViewById(R.id.branch_rec);

        setSupportActionBar(toolbar); //set object toolbar as default action bar for activity
        getSupportActionBar().setTitle("Branch Evaluation Records"); //set default title for action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //set back button to toolbar
        getSupportActionBar().setDisplayShowHomeEnabled(true); //enable the back button set on toolbar

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mtv_title.setText(intentDataBranchName);

        poLoad = new LoadDialog(Activity_BranchRecords.this);

        poMessage = new MessageBox(Activity_BranchRecords.this);
        poMessage.initDialog();
        poMessage.setPositiveButton("OK", new MessageBox.DialogButton() {
            @Override
            public void OnButtonClick(View view, AlertDialog dialog) {
                dialog.dismiss();
            }
        });

        mviewModel.initializeRecords(intentDataBranchcd, new VMBranchRecords.BranchRecordsCallBack() {
            @Override
            public void onInitialize(String message) {
                poLoad.initDialog("Branch Records", message, false);
                poLoad.show();
            }

            @Override
            public void onSuccess(String message) {
                mviewModel.getBranchRecords(intentDataBranchcd).observe(Activity_BranchRecords.this, new Observer<List<BranchRecords>>() {
                    @Override
                    public void onChanged(List<BranchRecords> branchRecords) {
                        if (branchRecords.size() <= 0){
                            poLoad.dismiss();
                            poMessage.setTitle("No Records");
                            poMessage.setMessage(message);
                            poMessage.show();
                            return;
                        }

                        RecyclerViewAdapter_BranchRecord recyclerViewAdapterBranchRecord = new RecyclerViewAdapter_BranchRecord(
                                Activity_BranchRecords.this, branchRecords, new RecyclerViewAdapter_BranchRecord.onSelectItem() {
                            @Override
                            public void onItemSelected(String dtTransact) {
                                Intent intent = new Intent(Activity_BranchRecords.this, Activity_BranchRecord_Details.class);
                                intent.putExtra("Branch", new String[]{intentDataBranchcd, intentDataBranchName});
                                startActivity(intent);
                            }
                        });

                        branch_rec.setAdapter(recyclerViewAdapterBranchRecord);
                        branch_rec.setLayoutManager(new LinearLayoutManager(Activity_BranchRecords.this));

                        poLoad.dismiss();
                    }
                });
            }

            @Override
            public void onError(String message) {
                poLoad.dismiss();
                poMessage.setTitle("Error Message");
                poMessage.setMessage(message);
                poMessage.show();
                return;
            }
        });
    }
}