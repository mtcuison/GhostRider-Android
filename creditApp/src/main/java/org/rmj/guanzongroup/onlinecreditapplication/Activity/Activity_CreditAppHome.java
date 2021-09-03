/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.creditApp
 * Electronic Personnel Access Control Security System
 * project file created : 8/31/21 8:28 AM
 * project file last modified : 8/31/21 8:26 AM
 */

package org.rmj.guanzongroup.onlinecreditapplication.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.textfield.TextInputEditText;

import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.GRider.Etc.GToast;
import org.rmj.g3appdriver.GRider.Etc.LoadDialog;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.g3appdriver.etc.WebFileServer;
import org.rmj.guanzongroup.ghostrider.griderscanner.dialog.DialogImagePreview;
import org.rmj.guanzongroup.ghostrider.imgcapture.ImageFileCreator;
import org.rmj.guanzongroup.ghostrider.notifications.Object.GNotifBuilder;
import org.rmj.guanzongroup.onlinecreditapplication.Adapter.CreditAppHomeAdapter;
import org.rmj.guanzongroup.onlinecreditapplication.Adapter.LoanApplication;
import org.rmj.guanzongroup.onlinecreditapplication.Adapter.UserLoanApplicationsAdapter;
import org.rmj.guanzongroup.onlinecreditapplication.Model.DownloadImageCallBack;
import org.rmj.guanzongroup.onlinecreditapplication.R;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMApplicationHome;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMResidenceInfo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.rmj.guanzongroup.ghostrider.notifications.Object.GNotifBuilder.APP_SYNC_DATA;

public class Activity_CreditAppHome extends AppCompatActivity {
    private VMApplicationHome mViewModel;
    private Toolbar toolbar;
    private TextInputEditText txtSearch;
    private RecyclerView recyclerView;
    private LinearLayout noRecord;

    private List<LoanApplication> loanList;
    private CreditAppHomeAdapter adapter;

    private ImageFileCreator poCamera;
    private EImageInfo poImage;

    private LoadDialog poDialogx;
    private MessageBox poMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_app_home);
        mViewModel = new ViewModelProvider(this).get(VMApplicationHome.class);
        initWidgets();
        mViewModel.getAllCreditApp().observe(Activity_CreditAppHome.this, credits->{
            if(credits != null || credits.size()>0) {
                noRecord.setVisibility(View.GONE);
                adapter = new CreditAppHomeAdapter(Activity_CreditAppHome.this,credits, new CreditAppHomeAdapter.OnApplicationClickListener() {
                    @Override
                    public void OnClick(int position, ECreditApplicantInfo loanList) {
//                        ECreditApplicantInfo creditApplicantInfo = loanList.get(position);
                        Intent loIntent = new Intent(Activity_CreditAppHome.this, Activity_CreditApplication.class);
                        loIntent.putExtra("transno", loanList.getTransNox());
                        startActivity(loIntent);
                    }
                });
                LinearLayoutManager layoutManager = new LinearLayoutManager(Activity_CreditAppHome.this);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(layoutManager);
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
                                noRecord.setVisibility(View.VISIBLE);
                            }else {
                                noRecord.setVisibility(View.GONE);
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
            else{

                noRecord.setVisibility(View.VISIBLE);
            }
        });
    }
    public void initWidgets(){
        toolbar = findViewById(R.id.toolbar_applicationHome);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        txtSearch = findViewById(R.id.txt_purchaseSearch);
        recyclerView = findViewById(R.id.recyclerview_applicationHome);
        noRecord = findViewById(R.id.layout_application_home_noRecord);
    }
    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.credit_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        else if(item.getItemId() == R.id.action_create_new){
            startActivity(new Intent(Activity_CreditAppHome.this, Activity_IntroductoryQuestion.class));
        }
        return super.onOptionsItemSelected(item);
    }


}