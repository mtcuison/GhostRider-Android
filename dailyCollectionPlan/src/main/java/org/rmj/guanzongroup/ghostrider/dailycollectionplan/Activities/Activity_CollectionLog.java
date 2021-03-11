package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Etc.FormatUIText;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Adapter.CollectionLogAdapter;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel.VMCollectionLog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class Activity_CollectionLog extends AppCompatActivity {
    private static final String TAG = Activity_CollectionLog.class.getSimpleName();

    private VMCollectionLog mViewModel;

    private TextView lblBranch, lblAddrss;
    //private CollectionLogAdapter poAdapter;
    private LinearLayoutManager poManager;
    private TextInputEditText txtDate, txtSearch;
    private RecyclerView recyclerView;

    private List<EDCPCollectionDetail> filteredCollectionDetlx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_log);
        initWidgets();

        mViewModel = new ViewModelProvider(this).get(VMCollectionLog.class);
        mViewModel.getUserBranchInfo().observe(Activity_CollectionLog.this, eBranchInfo -> {
            lblBranch.setText(eBranchInfo.getBranchNm());
            lblAddrss.setText(eBranchInfo.getAddressx());
        });

        mViewModel.getAllAddress().observe(Activity_CollectionLog.this, eAddressUpdates -> {
            try {
                mViewModel.setAddressList(eAddressUpdates);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        });

        mViewModel.getAllMobileNox().observe(Activity_CollectionLog.this, eMobileUpdates -> {
            try {
                mViewModel.setMobileList(eMobileUpdates);
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        });

        mViewModel.getCollectionMaster().observe(Activity_CollectionLog.this, collectionMaster -> {
            try{
                mViewModel.setCollectionMaster(collectionMaster);
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        txtDate.setOnClickListener(view -> {
            final Calendar newCalendar = Calendar.getInstance();
            @SuppressLint("SimpleDateFormat") final SimpleDateFormat dateFormatter = new SimpleDateFormat("MMMM dd, yyyy");
            final DatePickerDialog StartTime = new DatePickerDialog(Activity_CollectionLog.this, (view131, year, monthOfYear, dayOfMonth) -> {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                String lsDate = dateFormatter.format(newDate.getTime());
                mViewModel.setDateTransact(lsDate);
                txtDate.setText(lsDate);
            }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
            StartTime.getDatePicker().setMaxDate(System.currentTimeMillis() + 1000);
            StartTime.show();
        });

        mViewModel.getDateTransact().observe(Activity_CollectionLog.this, s -> mViewModel.getCollectionDetailForDate(s).observe(Activity_CollectionLog.this, collectionDetails -> {
            try{
                for(int z = 0; z < collectionDetails.size(); z++) {
                    if(collectionDetails.get(z).getRemCodex() != null) {
                        filteredCollectionDetlx.add(collectionDetails.get(z));
                    }
                }

                CollectionLogAdapter poAdapter = new CollectionLogAdapter(filteredCollectionDetlx, position -> {
                    Intent loIntent = new Intent(Activity_CollectionLog.this, Activity_TransactionLog.class);
                    loIntent.putExtra("entryNox",filteredCollectionDetlx.get(position).getEntryNox());
                    loIntent.putExtra("acctNox",filteredCollectionDetlx.get(position).getAcctNmbr());
                    loIntent.putExtra("fullNme", filteredCollectionDetlx.get(position).getFullName());
                    loIntent.putExtra("remCodex", filteredCollectionDetlx.get(position).getRemCodex());
                    loIntent.putExtra("imgNme", filteredCollectionDetlx.get(position).getImageNme());
                    loIntent.putExtra("sClientID", filteredCollectionDetlx.get(position).getClientID());
                    loIntent.putExtra("sAddressx", filteredCollectionDetlx.get(position).getAddressx());
                    startActivity(loIntent);
                });

                poManager = new LinearLayoutManager(Activity_CollectionLog.this);
                poManager.setOrientation(RecyclerView.VERTICAL);
                recyclerView.setLayoutManager(poManager);
                recyclerView.setAdapter(poAdapter);
                recyclerView.getRecycledViewPool().clear();
                poAdapter.notifyDataSetChanged();

                txtSearch.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        poAdapter.getCollectionFilter().filter(charSequence.toString().toLowerCase());
                        poAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
            } catch (Exception e){
                e.printStackTrace();
            }
        }));


        mViewModel.getUnsentImageInfoList().observe(Activity_CollectionLog.this, eImageInfos -> {
            try{
                mViewModel.setImageInfoList(eImageInfos);
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        //btnPost.setOnClickListener(view -> mViewModel.PostLRCollectionDetail(Activity_CollectionLog.this));
    }

    private void initWidgets(){
        Toolbar toolbar = findViewById(R.id.toolbar_collectionLog);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recyclerview_collectionLog);
        lblBranch = findViewById(R.id.lbl_headerBranch);
        lblAddrss = findViewById(R.id.lbl_headerAddress);

        txtDate = findViewById(R.id.txt_collectionDate);
        txtSearch = findViewById(R.id.txt_collectionSearch);
        filteredCollectionDetlx = new ArrayList<>();

        try {
            @SuppressLint("SimpleDateFormat") Date loDate = new SimpleDateFormat("yyyy-MM-dd").parse(AppConstants.CURRENT_DATE);
            @SuppressLint("SimpleDateFormat") SimpleDateFormat loFormatter = new SimpleDateFormat("MMM dd, yyyy");
            txtDate.setText(loFormatter.format(Objects.requireNonNull(loDate)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public String getDate(){
        return "Collection For " + FormatUIText.getParseDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()));
    }
}