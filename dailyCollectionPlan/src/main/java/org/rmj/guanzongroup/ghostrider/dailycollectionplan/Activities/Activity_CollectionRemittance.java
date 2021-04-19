package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.rmj.g3appdriver.GRider.Database.Entities.EDCP_Remittance;
import org.rmj.g3appdriver.GRider.Database.Repositories.RDCP_Remittance;
import org.rmj.g3appdriver.GRider.Etc.FormatUIText;
import org.rmj.g3appdriver.GRider.Etc.LoadDialog;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Dialog.DialogRemitCollection;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel.VMCollectionRemittance;

import java.util.Objects;

public class Activity_CollectionRemittance extends AppCompatActivity {

    private VMCollectionRemittance mViewModel;
    private LoadDialog poDialogx;
    private MessageBox poMessage;
    private String psCheckx, psCashxx;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_remittance);
        poDialogx = new LoadDialog(Activity_CollectionRemittance.this);
        poMessage = new MessageBox(Activity_CollectionRemittance.this);
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

        lblRmtBrnch.setText("Branch :" + "Calculating...");
        lblRmtBankx.setText("Bank :" + "Calculating...");
        lblRmtOther.setText("Others :" + "Calculating...");
        lblOHCashxx.setText("Cash :" + "Calculating...");
        lblOHCheckx.setText("Check :" + "Calculating...");

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
                lblRmtBrnch.setText("Branch : " + FormatUIText.getCurrencyUIFormat(s));
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        mViewModel.getTotalBankRemittedCollection().observe(this, s -> {
            try {
                lblRmtBankx.setText("Bank : " + FormatUIText.getCurrencyUIFormat(s));
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        mViewModel.getTotalOtherRemittedCollection().observe(this, s -> {
            try {
                lblRmtOther.setText("Payment Partners : " + FormatUIText.getCurrencyUIFormat(s));
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        mViewModel.Calculate_COH_Remitted(result -> {
            try {
                lblOHCashxx.setText("Cash : " + FormatUIText.getCurrencyUIFormat(result));
                psCashxx = result;
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        mViewModel.Calculate_Check_Remitted(result -> {
            try {
                lblOHCheckx.setText("Check : " + FormatUIText.getCurrencyUIFormat(result));
                psCheckx = result;
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        btnRemit.setOnClickListener(v -> {
            DialogRemitCollection poRemit = new DialogRemitCollection(Activity_CollectionRemittance.this);
            poRemit.initDialog(new DialogRemitCollection.RemitDialogListener() {
                @Override
                public void OnConfirm(AlertDialog dialog, EDCP_Remittance remittance) {
                    dialog.dismiss();
                    mViewModel.RemitCollection(remittance, new VMCollectionRemittance.OnRemitCollectionCallback() {
                        @Override
                        public void OnRemit() {
                            poDialogx.initDialog("Remit Collection", "Sending your remittance. Please wait...", false);
                            poDialogx.show();
                        }

                        @Override
                        public void OnSuccess() {
                            poDialogx.dismiss();
                            poMessage.initDialog();
                            poMessage.setTitle("Remit Collection");
                            poMessage.setMessage("Your remittance has been sent successfully.");
                            poMessage.setPositiveButton("Okay", (view, dialog1) -> dialog1.dismiss());
                            poMessage.show();
                        }

                        @Override
                        public void OnFailed(String message) {
                            poDialogx.dismiss();
                            poMessage.initDialog();
                            poMessage.setTitle("Remit Collection");
                            poMessage.setMessage(message);
                            poMessage.setPositiveButton("Okay", (view, dialog1) -> dialog1.dismiss());
                            poMessage.show();
                        }
                    });
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