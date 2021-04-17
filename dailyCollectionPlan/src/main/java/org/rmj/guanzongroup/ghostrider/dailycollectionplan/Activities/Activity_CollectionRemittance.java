package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import org.rmj.g3appdriver.GRider.Database.Entities.EDCP_Remittance;
import org.rmj.g3appdriver.GRider.Etc.FormatUIText;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Dialog.DialogRemitCollection;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel.VMCollectionRemittance;

import java.util.Objects;

public class Activity_CollectionRemittance extends AppCompatActivity {

    private VMCollectionRemittance mViewModel;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_remittance);
        mViewModel = new ViewModelProvider(this).get(VMCollectionRemittance.class);
        mViewModel.setTransact(getIntent().getStringExtra("dTransact"));
        Toolbar toolbar = findViewById(R.id.toolbar_collectionRemit);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        TextView lblBranch = findViewById(R.id.lbl_headerBranch);
        TextView lblAddrss = findViewById(R.id.lbl_headerAddress);
        TextView lblCltdCash = findViewById(R.id.lbl_collectedCash);
        TextView lblCltdChck = findViewById(R.id.lbl_collectedCheck);
        TextView lblRmtBrnch = findViewById(R.id.lbl_remittanceBranch);
        TextView lblRmtBankx = findViewById(R.id.lbl_remittanceBank);
        TextView lblRmtOther = findViewById(R.id.lbl_remittanceOthers);
        TextView lblOHCashxx = findViewById(R.id.lbl_remittanceCash);
        TextView lblOHCheckx = findViewById(R.id.lbl_remittanceCheck);
        Button btnRemit = findViewById(R.id.btn_confirm);

        lblRmtBrnch.setText("Branch :" + "sRmtBrnch");
        lblRmtBankx.setText("Bank :" + "sRmtBankx");
        lblRmtOther.setText("Others :" + "sRmtOther");
        lblOHCashxx.setText("Cash :" + "sOHCashxx");
        lblOHCheckx.setText("Check :" + "sOHCheckx");

        mViewModel.getUserBranchInfo().observe(this, eBranchInfo -> {
            try{
                lblBranch.setText(eBranchInfo.getBranchNm());
                lblAddrss.setText(eBranchInfo.getAddressx());
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        mViewModel.getTotalCollectedCash().observe(this, s -> {
            try {
                lblCltdCash.setText("Cash : " + FormatUIText.getCurrencyUIFormat(s));
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        mViewModel.getTotalCollectedCheck().observe(this, s -> {
            try {
                lblCltdChck.setText("Check : " + FormatUIText.getCurrencyUIFormat(s));
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        mViewModel.getTotalBranchRemittedCollection().observe(this, s -> {
            try {
                lblRmtBrnch.setText("Check : " + FormatUIText.getCurrencyUIFormat(s));
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        mViewModel.getTotalBankRemittedCollection().observe(this, s -> {
            try {
                lblRmtBankx.setText("Check : " + FormatUIText.getCurrencyUIFormat(s));
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        mViewModel.getTotalOtherRemittedCollection().observe(this, s -> {
            try {
                lblRmtOther.setText("Check : " + FormatUIText.getCurrencyUIFormat(s));
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        btnRemit.setOnClickListener(v -> {
            DialogRemitCollection poRemit = new DialogRemitCollection(Activity_CollectionRemittance.this);
            poRemit.initDialog(new DialogRemitCollection.RemitDialogListener() {
                @Override
                public void OnConfirm(AlertDialog dialog, EDCP_Remittance remittance) {

                }

                @Override
                public void OnCancel(AlertDialog dialog) {
                    dialog.dismiss();
                }
            });
            poRemit.show();
        });
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
}