package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.g3appdriver.GRider.Etc.FormatUIText;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Adapter.CollectionAdapter;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Etc.DialogAccountDetail;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Model.CollectionPlan;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel.VMCollectionList;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Activity_CollectionList extends AppCompatActivity {
    private static final String TAG = Activity_CollectionList.class.getSimpleName();
    private static final int MOBILE_DIALER = 104;
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
        mViewModel.DownloadDcp();
        mViewModel.getCollectionList().observe(this, collectionDetails -> {
            List<CollectionPlan> loCollection = new ArrayList<>();
            if(collectionDetails != null)
            for(int x = 0; x < collectionDetails.size(); x++){
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

                }

                @Override
                public void OnActionButtonClick() {

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
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
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
}