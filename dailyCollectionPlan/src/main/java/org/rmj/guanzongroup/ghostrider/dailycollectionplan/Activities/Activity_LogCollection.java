package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.EBankInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCP_Remittance;
import org.rmj.g3appdriver.GRider.Etc.FormatUIText;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Adapter.CollectionLogAdapter;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Dialog.DialogRemitCollection;
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

public class Activity_LogCollection extends AppCompatActivity {
    private static final String TAG = Activity_LogCollection.class.getSimpleName();

    private VMCollectionLog mViewModel;

    private TextView lblBranch, lblAddrss, lblTotRemit, lblCashOH, lblTotalClt;
    //private CollectionLogAdapter poAdapter;
    private LinearLayoutManager poManager;
    private TextInputEditText txtDate, txtSearch;
    private RecyclerView recyclerView;
    private TextView txtNoLog, txtNoName;
    private TextInputLayout tilSearch;
    private LinearLayout linearCashInfo;

    private List<EDCPCollectionDetail> filteredCollectionDetlx;

    private boolean hasLog = true;

    private List<EBankInfo> poBankList = new ArrayList<>();
    private List<EBranchInfo> poBrnchList = new ArrayList<>();

    private String psCltCheck;
    private String psCltCashx;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_log);
        initWidgets();

        mViewModel = new ViewModelProvider(this).get(VMCollectionLog.class);
        mViewModel.getUserBranchInfo().observe(Activity_LogCollection.this, eBranchInfo -> {
            try {
                lblBranch.setText(eBranchInfo.getBranchNm());
                lblAddrss.setText(eBranchInfo.getAddressx());
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        mViewModel.getAllAddress().observe(Activity_LogCollection.this, eAddressUpdates -> {
            try {
                mViewModel.setAddressList(eAddressUpdates);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        });

        mViewModel.getBankInfoList().observe(Activity_LogCollection.this, eBankInfos -> {
            try {
                poBankList = eBankInfos;
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        mViewModel.getBranchInfoList().observe(Activity_LogCollection.this, eBranchInfos -> {
            try {
                poBrnchList = eBranchInfos;
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        mViewModel.getAllMobileNox().observe(Activity_LogCollection.this, eMobileUpdates -> {
            try {
                mViewModel.setMobileList(eMobileUpdates);
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        });

        mViewModel.getCollectionMaster().observe(Activity_LogCollection.this, collectionMaster -> {
            try{
                mViewModel.setCollectionMaster(collectionMaster);
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        txtDate.setOnClickListener(view -> {
            final Calendar newCalendar = Calendar.getInstance();
            @SuppressLint("SimpleDateFormat") final SimpleDateFormat dateFormatter = new SimpleDateFormat("MMMM dd, yyyy");
            final DatePickerDialog StartTime = new DatePickerDialog(Activity_LogCollection.this, (view131, year, monthOfYear, dayOfMonth) -> {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                String lsDate = dateFormatter.format(newDate.getTime());
                mViewModel.setDateTransact(lsDate);
                txtDate.setText(lsDate);
            }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
            StartTime.getDatePicker().setMaxDate(System.currentTimeMillis() + 1000);
            StartTime.show();
        });

        mViewModel.getDateTransact().observe(Activity_LogCollection.this, s -> mViewModel.getCollectionDetailForDate(s).observe(Activity_LogCollection.this, collectionDetails -> {
            try{
                if(collectionDetails.size() > 0) {
                    hasLog = true;
                    txtNoLog.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    tilSearch.setVisibility(View.VISIBLE);
                    txtSearch.setText("");
                    linearCashInfo.setVisibility(View.VISIBLE);

                    filteredCollectionDetlx.clear();
                    for(int z = 0; z < collectionDetails.size(); z++) {
//                    if(collectionDetails.get(z).getRemCodex() != null) {
                        filteredCollectionDetlx.add(collectionDetails.get(z));
//                    }
                    }

                    CollectionLogAdapter poAdapter = new CollectionLogAdapter(filteredCollectionDetlx, position -> {
                        Intent loIntent = new Intent(Activity_LogCollection.this, Activity_LogTransaction.class);
                        loIntent.putExtra("sTransNox", filteredCollectionDetlx.get(position).getTransNox());
                        loIntent.putExtra("entryNox",filteredCollectionDetlx.get(position).getEntryNox());
                        loIntent.putExtra("acctNox",filteredCollectionDetlx.get(position).getAcctNmbr());
                        loIntent.putExtra("fullNme", filteredCollectionDetlx.get(position).getFullName());
                        loIntent.putExtra("remCodex", filteredCollectionDetlx.get(position).getRemCodex());
                        loIntent.putExtra("imgNme", filteredCollectionDetlx.get(position).getImageNme());
                        loIntent.putExtra("sClientID", filteredCollectionDetlx.get(position).getClientID());
                        loIntent.putExtra("sAddressx", filteredCollectionDetlx.get(position).getAddressx());
                        loIntent.putExtra("sRemarksx", filteredCollectionDetlx.get(position).getRemarksx());
                        startActivity(loIntent);
                    });

                    poManager = new LinearLayoutManager(Activity_LogCollection.this);
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
                            if(!charSequence.toString().trim().isEmpty()) {
                                poAdapter.getCollectionFilter().filter(charSequence.toString().toLowerCase());
                                poAdapter.notifyDataSetChanged();
                                if(poAdapter.getItemCount() == 0) {
                                    txtNoName.setVisibility(View.VISIBLE);
                                    recyclerView.setVisibility(View.GONE);
                                } else {
                                    txtNoName.setVisibility(View.GONE);
                                    recyclerView.setVisibility(View.VISIBLE);
                                }
                            } else {
                                poAdapter.getCollectionFilter().filter(charSequence.toString().toLowerCase());
                                poAdapter.notifyDataSetChanged();
                                txtNoName.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    });

                    mViewModel.getCollectedTotal(s).observe(this, value -> lblTotalClt.setText("Total Collection : " + FormatUIText.getCurrencyUIFormat(value)));

                    mViewModel.getCashOnHand(s).observe(this, value -> lblCashOH.setText("Cash-On-Hand : " + FormatUIText.getCurrencyUIFormat(value)));

                    mViewModel.getTotalRemittedCollection(s).observe(this, value -> lblTotRemit.setText("Total Remitted : " + FormatUIText.getCurrencyUIFormat(value)));

                    mViewModel.getCollectedCheckPayment(s).observe(this, value -> psCltCheck = value );

                    mViewModel.getCollectedCashPayment(s).observe(this, value -> psCltCashx = value);
                } else {
                    hasLog = false;
                    txtNoLog.setVisibility(View.VISIBLE);
                    txtNoName.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.GONE);
                    tilSearch.setVisibility(View.GONE);
                    linearCashInfo.setVisibility(View.GONE);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }));

        mViewModel.getUnsentImageInfoList().observe(Activity_LogCollection.this, eImageInfos -> {
            try{
                mViewModel.setImageInfoList(eImageInfos);
            } catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    private void initWidgets(){
        Toolbar toolbar = findViewById(R.id.toolbar_collectionLog);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recyclerview_collectionLog);
        txtNoLog = findViewById(R.id.txt_no_logs);
        txtNoName = findViewById(R.id.txt_no_name);
        lblBranch = findViewById(R.id.lbl_headerBranch);
        lblAddrss = findViewById(R.id.lbl_headerAddress);
        lblTotRemit = findViewById(R.id.lbl_totalRemitCollection);
        lblCashOH = findViewById(R.id.lbl_totalCashOnHand);
        lblTotalClt = findViewById(R.id.lbl_totalCollected);

        txtDate = findViewById(R.id.txt_collectionDate);
        txtSearch = findViewById(R.id.txt_collectionSearch);
        tilSearch = findViewById(R.id.til_collectionSearch);
        linearCashInfo = findViewById(R.id.linear_collectionCashInfo);
        filteredCollectionDetlx = new ArrayList<>();

        try {
            @SuppressLint("SimpleDateFormat") Date loDate = new SimpleDateFormat("yyyy-MM-dd").parse(AppConstants.CURRENT_DATE);
            @SuppressLint("SimpleDateFormat") SimpleDateFormat loFormatter = new SimpleDateFormat("MMM dd, yyyy");
            txtDate.setText(loFormatter.format(Objects.requireNonNull(loDate)));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        linearCashInfo.setOnClickListener(v -> {
            try {
                Intent loIntent = new Intent(Activity_LogCollection.this, Activity_CollectionRemittance.class);
                String lsDate = Objects.requireNonNull(txtDate.getText()).toString();
                @SuppressLint("SimpleDateFormat") Date loDate = new SimpleDateFormat("MMM dd, yyyy").parse(lsDate);
                @SuppressLint("SimpleDateFormat") SimpleDateFormat loFormatter = new SimpleDateFormat("yyyy-MM-dd");
                loIntent.putExtra("dTransact", loFormatter.format(Objects.requireNonNull(loDate)));
                startActivity(loIntent);
            } catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu_dcp_log, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getViewModelStore().clear();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        } else if(item.getItemId() == R.id.action_menu_remit_collection){
            if(hasLog) {
                DialogRemitCollection poRemit = new DialogRemitCollection(Activity_LogCollection.this);
                poRemit.setPoBankList(poBankList);
                poRemit.setPoBrnchList(poBrnchList);
                poRemit.setPsCltCheck(psCltCheck);
                poRemit.setPsCltCashx(psCltCashx);
                poRemit.initDialog(new DialogRemitCollection.RemitDialogListener() {
                    @Override
                    public void OnConfirm(AlertDialog dialog, EDCP_Remittance remittance) {
                        dialog.dismiss();
                    }

                    @Override
                    public void OnCancel(AlertDialog dialog) {
                        dialog.dismiss();
                    }
                });
                poRemit.show();
            } else {
                MessageBox loMessage = new MessageBox(Activity_LogCollection.this);
                loMessage.initDialog();
                loMessage.setPositiveButton("Okay", (view, dialog) -> dialog.dismiss());
                loMessage.setMessage("There's no collection record for this date.");
                loMessage.setTitle("No Record Found");
                loMessage.show();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}