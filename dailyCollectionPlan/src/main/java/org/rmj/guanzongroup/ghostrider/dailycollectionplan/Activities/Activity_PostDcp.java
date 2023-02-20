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
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.android.material.divider.MaterialDivider;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.imageview.ShapeableImageView;
import  com.google.android.material.checkbox.MaterialCheckBox;

import org.rmj.g3appdriver.dev.Database.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.etc.LoadDialog;
import org.rmj.g3appdriver.etc.MessageBox;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Adapter.PostDcpAdapter;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel.VMPostDcp;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Activity_PostDcp extends AppCompatActivity {

    private VMPostDcp mViewModel;
    private LoadDialog poLoading;
    private MessageBox poMessage;
    private RecyclerView recyclerV;
    private MaterialTextView lblBranch, lblAddrss, lblNoList;
    private String psRemarks = "";
    private boolean isPosting;

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
            !isPosting){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(!isPosting){
            finish();
        }
    }

    private void initWidgets() {
        MaterialToolbar toolbar = findViewById(R.id.toolbar_collectionList);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        lblBranch = findViewById(R.id.lbl_headerBranch);
        lblAddrss = findViewById(R.id.lbl_headerAddress);
        lblNoList = findViewById(R.id.lbl_noAvailable);
        recyclerV = findViewById(R.id.recyclerView);
        LinearLayoutManager lnManager = new LinearLayoutManager(Activity_PostDcp.this);
        lnManager.setOrientation(RecyclerView.VERTICAL);
        recyclerV.setLayoutManager(lnManager);
    }

    private void setUpDcpList() {

        mViewModel.getUserBranchInfo().observe(Activity_PostDcp.this, eBranchInfo -> {
            try {
                lblBranch.setText(eBranchInfo.getBranchNm());
                lblAddrss.setText(eBranchInfo.getAddressx());
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        mViewModel.getUnpostedCollectionList().observe(Activity_PostDcp.this, unpostedList -> {
            try {
                if(unpostedList.size() > 0) {
                    lblNoList.setVisibility(View.GONE);
                    recyclerV.setVisibility(View.VISIBLE);
                    PostDcpAdapter poAdapter = new PostDcpAdapter(unpostedList, new PostDcpAdapter.OnPostDcpClick() {
                        @Override
                        public void onClick(EDCPCollectionDetail dcpDetail) {
                            mViewModel.PostLRDCPTransaction(dcpDetail, new VMPostDcp.OnPostCollection() {
                                @Override
                                public void onLoading() {
                                    isPosting = true;
                                    poLoading = new LoadDialog(Activity_PostDcp.this);
                                    poLoading.initDialog("Posting DCP", "Posting DCP. Please wait...", false);
                                    poLoading.show();
                                }

                                @Override
                                public void onSuccess(String fsMessage) {
                                    isPosting = false;
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
                                    isPosting = false;
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
        mViewModel.PostLRDCPCollection(new VMPostDcp.OnPostCollection() {
            @Override
            public void onLoading() {
                isPosting = true;
                poLoading = new LoadDialog(Activity_PostDcp.this);
                poLoading.initDialog("Posting DCP List", "Posting DCP List. Please wait...", false);
                poLoading.show();
            }

            @Override
            public void onSuccess(String fsMessage) {
                isPosting = false;
                poLoading.dismiss();
                poMessage.initDialog();
                poMessage.setTitle("Daily Collection Plan");
                poMessage.setMessage(fsMessage);
                poMessage.setPositiveButton("Okay", (view, dialog) -> {
                    dialog.dismiss();
                    finish();
                });
                poMessage.show();
            }

            @Override
            public void onFailed(String fsMessage) {
                isPosting = false;
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