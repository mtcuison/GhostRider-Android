package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Etc.FormatUIText;
import org.rmj.g3appdriver.GRider.Etc.GToast;
import org.rmj.g3appdriver.GRider.Etc.LoadDialog;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Adapter.CollectionAdapter;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Dialog.DialogAccountDetail;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Dialog.DialogDownloadDCP;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Dialog.DialogOtherClient;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Model.CollectionPlan;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel.VMCollectionList;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel.ViewModelCallback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Activity_CollectionList extends AppCompatActivity implements ViewModelCallback {
    private static final String TAG = Activity_CollectionList.class.getSimpleName();

    private static final int MOBILE_DIALER = 104;

    private LoadDialog poDialogx;
    private MessageBox poMessage;
    private DialogOtherClient loDialog;

    private VMCollectionList mViewModel;

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;

    private TextView lblBranch, lblAddxx, lblDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_list);
        mViewModel = new ViewModelProvider(this).get(VMCollectionList.class);
        initWidgets();

        mViewModel.getCollectionLastEntry().observe(this, collectionDetail -> {
            // Added +1 for entry nox to increment the value which will be
            // use when inserting new AR Client info to database
            try {
                int lnEntry = 1 + Integer.parseInt(collectionDetail.getEntryNox());
                mViewModel.setParameter(collectionDetail.getTransNox(), String.valueOf(lnEntry));
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        mViewModel.getCollectionList().observe(this, collectionDetails -> {
            List<CollectionPlan> loCollection = new ArrayList<>();
            if(collectionDetails.size() > 0) {
                for (int x = 0; x < collectionDetails.size(); x++) {
                    CollectionPlan loPlan = new CollectionPlan();
                    loPlan.setAcctNoxxx(collectionDetails.get(x).getAcctNmbr());
                    loPlan.setDCPNumber(collectionDetails.get(x).getEntryNox());
                    loPlan.setClientNme(collectionDetails.get(x).getFullName());
                    loPlan.setHouseNoxx(collectionDetails.get(x).getHouseNox());
                    loPlan.setAddressxx(collectionDetails.get(x).getAddressx());
                    loPlan.setTownNamex(collectionDetails.get(x).getTownName());
                    loPlan.setContactxx(collectionDetails.get(x).getMobileNo());
                    loPlan.setDCPCountx(String.valueOf(collectionDetails.size()));
                    loPlan.setStatusxxx(collectionDetails.get(x).getTranType());
                    loPlan.setBalancexx("Balance");
                    loPlan.setAmntDuexx(collectionDetails.get(x).getAmtDuexx());
                    loCollection.add(loPlan);
                }
            } else {
                DialogDownloadDCP dialogDownloadDCP = new DialogDownloadDCP(Activity_CollectionList.this);
                dialogDownloadDCP.initDialog(new DialogDownloadDCP.OnDialogButtonClickListener() {
                    @Override
                    public void OnDownloadClick(Dialog Dialog, String Date) {
                        if(!Date.trim().isEmpty()){
                            mViewModel.DownloadDcp(Date);
                            Dialog.dismiss();
                        } else {
                            GToast.CreateMessage(Activity_CollectionList.this, "Please enter date", GToast.ERROR).show();
                        }
                    }

                    @Override
                    public void OnCancel(Dialog Dialog) {
                        Dialog.dismiss();
                    }
                });
                dialogDownloadDCP.show();
            }
            CollectionAdapter loAdapter = new CollectionAdapter(loCollection, new CollectionAdapter.OnItemClickListener() {
                @Override
                public void OnClick(int position) {
                    DialogAccountDetail loDialog = new DialogAccountDetail(Activity_CollectionList.this);
                    loDialog.initAccountDetail(collectionDetails.get(position), (dialog, remarksCode) -> {
                        dialog.dismiss();
                        Intent loIntent = new Intent(Activity_CollectionList.this, Activity_Transaction.class);
                        loIntent.putExtra("remarksx", remarksCode);
                        loIntent.putExtra("transnox", collectionDetails.get(position).getTransNox());
                        loIntent.putExtra("entrynox", collectionDetails.get(position).getEntryNox());
                        startActivity(loIntent);
                    });
                    loDialog.show();
                }

                @Override
                public void OnMobileNoClickListener(String MobileNo) {
                    Intent mobileIntent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", MobileNo, null));
                    startActivityForResult(mobileIntent, MOBILE_DIALER);
                }

                @Override
                public void OnAddressClickListener(String Address, String[] args) {
                    //TODO: Future update... add google map API for auto searching address location...
                }

                @Override
                public void OnActionButtonClick() {
                    //TODO: Future update
                }
            });
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(loAdapter);
        });

        mViewModel.getUserBranchInfo().observe(Activity_CollectionList.this, eBranchInfo -> {
            lblBranch.setText(eBranchInfo.getBranchNm());
            lblAddxx.setText(eBranchInfo.getAddressx());
            lblDate.setText(getDate());
        });
    }

    private void initWidgets(){
        Toolbar toolbar = findViewById(R.id.toolbar_collectionList);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recyclerview_collectionList);
        layoutManager = new LinearLayoutManager(Activity_CollectionList.this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);

        lblBranch = findViewById(R.id.lbl_headerBranch);
        lblAddxx = findViewById(R.id.lbl_headerAddress);
        lblDate = findViewById(R.id.lbl_headerDate);

        poDialogx = new LoadDialog(Activity_CollectionList.this);
        poMessage = new MessageBox(Activity_CollectionList.this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu_dcp_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        } else if(item.getItemId() == R.id.action_menu_ar_client){
            mViewModel.getCollectionMaster().observe(Activity_CollectionList.this, collectionMaster -> {
                try {
                    loDialog = new DialogOtherClient(Activity_CollectionList.this);
                    loDialog.initDialog(DialogOtherClient.TYPE.ACCOUNT_RECIEVABLE, new DialogOtherClient.OnDialogButtonClickListener() {
                        @Override
                        public void OnDownloadClick(Dialog Dialog, String args) {

                            //Check if account entered is already added on DCP list...
                            // args parameter from dialog refers to account number...
                            mViewModel.getDuplicateAccountrEntry(collectionMaster.getTransNox(), args).observe(Activity_CollectionList.this, collectionDetail -> {
                                if(collectionDetail != null){
                                    Dialog.dismiss();
                                    poMessage.setTitle("Account ReceivableC Client");
                                    poMessage.setMessage("This account is already on collection list.");
                                    poMessage.setPositiveButton("Okay", (view, dialog) -> dialog.dismiss());
                                    poMessage.show();
                                } else {
                                    mViewModel.importARClientInfo(args, Activity_CollectionList.this);
                                }
                            });
                        }

                        @Override
                        public void OnCancel(Dialog Dialog) {
                            Dialog.dismiss();
                        }
                    });
                    loDialog.show();
                } catch (Exception e){
                    e.printStackTrace();
                }
            });
        } else if(item.getItemId() == R.id.action_menu_insurance_client){
            loDialog = new DialogOtherClient(Activity_CollectionList.this);
            loDialog.initDialog(DialogOtherClient.TYPE.INSURANCE,new DialogOtherClient.OnDialogButtonClickListener() {
                @Override
                public void OnDownloadClick(Dialog Dialog, String args) {
                    Dialog.dismiss();
                    mViewModel.importInsuranceInfo(args, Activity_CollectionList.this);
                }

                @Override
                public void OnCancel(Dialog Dialog) {
                    Dialog.dismiss();
                }
            });
            loDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == MOBILE_DIALER){
            if(resultCode == RESULT_OK){

            }
        }
    }

    public String getDate(){
        return "Collection For " + FormatUIText.getParseDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()));
    }

    @Override
    public void OnStartSaving() {
        poDialogx.initDialog("AR Client", "Downloading client info. Please wait...", false);
        poDialogx.show();
    }

    @Override
    public void OnSuccessResult(String[] args) {
        poDialogx.dismiss();
        poMessage.setTitle("AR Client");
        poMessage.setMessage(args[0]);
        poMessage.setPositiveButton("Okay", (view, dialog) -> dialog.dismiss());
        poMessage.show();
    }

    @Override
    public void OnFailedResult(String message) {
        poDialogx.dismiss();
        poMessage.setTitle("AR Client");
        poMessage.setMessage(message);
        poMessage.setPositiveButton("Okay", (view, dialog) -> dialog.dismiss());
        poMessage.show();
    }
}