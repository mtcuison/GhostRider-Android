/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.dailyCollectionPlan
 * Electronic Personnel Access Control Security System
 * project file created : 10/22/21, 3:13 PM
 * project file last modified : 10/22/21, 3:13 PM
 */

package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Etc.LoadDialog;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Adapter.PostDcpAdapter;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel.VMPostDcp;

import java.util.ArrayList;
import java.util.List;

public class Activity_PostDcp extends AppCompatActivity {

    private VMPostDcp mViewModel;
    private LoadDialog poLoading;
    private MessageBox poMessage;
    private RecyclerView recyclerV;
    private TextView lblBranch, lblAddrss, lblNoList;
    private String psRemarks = "";
    private boolean Posting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_dcp);
        psRemarks = getIntent().getStringExtra("sRemarksx");
        mViewModel = new ViewModelProvider(this).get(VMPostDcp.class);
        poMessage = new MessageBox(Activity_PostDcp.this);
        initWidgets();
        setUpDcpList();
        postCollection();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home &&
            !Posting){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(!Posting){
            finish();
        }
    }

    private void initWidgets() {
        lblBranch = findViewById(R.id.lbl_headerBranch);
        lblAddrss = findViewById(R.id.lbl_headerAddress);
        lblNoList = findViewById(R.id.lbl_noAvailable);
        recyclerV = findViewById(R.id.recyclerView);
        LinearLayoutManager lnManager = new LinearLayoutManager(Activity_PostDcp.this);
        lnManager.setOrientation(RecyclerView.VERTICAL);
        recyclerV.setLayoutManager(lnManager);
    }

    private void setUpDcpList() {
        mViewModel.getUnpostedCollectionList().observe(Activity_PostDcp.this, unpostedList -> {
            try {
                if(unpostedList.size() > 0) {
                    lblNoList.setVisibility(View.GONE);
                    recyclerV.setVisibility(View.VISIBLE);
                    PostDcpAdapter poAdapter = new PostDcpAdapter(unpostedList, new PostDcpAdapter.OnPostDcpClick() {
                        @Override
                        public void onClick(EDCPCollectionDetail dcpDetail) {
                            // TODO: call the individual posting of dcp clicked.
                        }
                    });
                    recyclerV.setAdapter(poAdapter);
                    poAdapter.notifyDataSetChanged();
                } else {
                    lblNoList.setVisibility(View.VISIBLE);
                    recyclerV.setVisibility(View.GONE);
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    private void postCollection() {
        mViewModel.PostLRDCPCollection(psRemarks, new VMPostDcp.OnPostCollection() {
            @Override
            public void onLoading() {
                poLoading = new LoadDialog(Activity_PostDcp.this);
                poLoading.initDialog("Posting DCP List", "Posting DCP List. Please wait...", false);
                poLoading.show();
            }

            @Override
            public void onSuccess(String fsMessage) {
                poLoading.dismiss();
                poMessage.initDialog();
                poMessage.setTitle("Daily Collection Plan");
                poMessage.setMessage(fsMessage);
                poMessage.setPositiveButton("Okay", (view, dialog) -> {
                    dialog.dismiss();
                });
                poMessage.show();
            }

            @Override
            public void onFailed(String fsMessage) {
                poLoading.dismiss();
                poMessage.initDialog();
                poMessage.setTitle("Daily Collection Plan");
                poMessage.setMessage(fsMessage);
                poMessage.setPositiveButton("Okay", (view, dialog) -> {
                    dialog.dismiss();
                });
                poMessage.show();
            }
        });
    }

}