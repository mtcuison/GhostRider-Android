package org.rmj.guanzongroup.promotions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.rmj.g3appdriver.GRider.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.GRider.Etc.LoadDialog;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.g3appdriver.dev.AppData;
import org.rmj.guanzongroup.promotions.Etc.RaffleEntryCallback;
import org.rmj.guanzongroup.promotions.Model.RaffleEntry;
import org.rmj.guanzongroup.promotions.ViewModel.VMRaffleEntry;

import java.util.Objects;

public class Activity_RaffleEntry extends AppCompatActivity implements RaffleEntryCallback {

    private TextView lblbranch, lblAddrss, lblMessage;
    private Toolbar toolbar;
    private TextInputEditText txtName, txtAddress, txtDocuNo, txtMobileNo;
    private AutoCompleteTextView txtTown;
    private Spinner spnDocuType;
    private MaterialButton btnSubmit;
    private VMRaffleEntry mViewModel;
    private String sReferCde = "";
    private LoadDialog dialog;
    private MessageBox loMessage;
    private RaffleEntry voucher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raffle_entry);
        initWidgets();
        mViewModel = new ViewModelProvider(this).get(VMRaffleEntry.class);
        mViewModel.importDocuments();

        mViewModel.getUserBranchInfo().observe(Activity_RaffleEntry.this, eBranchInfo -> {
            lblbranch.setText(eBranchInfo.getBranchNm());
            lblAddrss.setText(eBranchInfo.getAddressx());
        });

//        get the latest entries of raffle entry to valid if the current entry is already exist.
//        mViewModel.getDuplicateEntry(txtName.getText().toString(),
//                sReferCde,
//                txtDocuNo.getText().toString(),
//                txtMobileNo.getText().toString()).observe(this, eRaffleInfos -> {
//            if(!(eRaffleInfos.size() > 0)){
//                mViewModel.submit(voucher, Activity_RaffleEntry.this);
//            } else {
//                Toast.makeText(Activity_RaffleEntry.this, "Entry is already existed.", Toast.LENGTH_SHORT).show();
//            }
//        });

        btnSubmit.setOnClickListener(view -> {
            String lsBranchCode = AppData.getInstance(Activity_RaffleEntry.this).getBranchCode();
            voucher.setCustomerName(Objects.requireNonNull(txtName.getText()).toString());
            voucher.setCustomerAddx(Objects.requireNonNull(txtAddress.getText()).toString());
            voucher.setMobileNumber(Objects.requireNonNull(txtMobileNo.getText()).toString());
            voucher.setDocumentNoxx(Objects.requireNonNull(txtDocuNo.getText()).toString());
            voucher.setBranchCodexx(lsBranchCode);
            voucher.setDocumentType(sReferCde);
            mViewModel.submit(voucher, Activity_RaffleEntry.this);
        });

        mViewModel.getTownProvinceInfo().observe(Activity_RaffleEntry.this, townProvinceInfos -> {
            String[] laInfo = new String[townProvinceInfos.size()];
            for(int x = 0; x < townProvinceInfos.size(); x++){
                laInfo[x] = townProvinceInfos.get(x).sTownName + ", " + townProvinceInfos.get(x).sProvName;
            }
            ArrayAdapter<String> loAdapter = new ArrayAdapter<>(Activity_RaffleEntry.this, android.R.layout.simple_spinner_dropdown_item, laInfo);
            txtTown.setAdapter(loAdapter);
        });

        txtTown.setOnItemClickListener((adapterView, view, i, l) -> mViewModel.getTownProvinceInfo().observe(Activity_RaffleEntry.this, townProvinceInfos -> {
            for(int x = 0; x < townProvinceInfos.size(); x++){
                if(txtTown.getText().toString().equalsIgnoreCase(townProvinceInfos.get(x).sTownName + ", " + townProvinceInfos.get(x).sProvName)){
                    voucher.setCustomerTown(townProvinceInfos.get(x).sTownIDxx);
                    voucher.setCustomerProv(townProvinceInfos.get(x).sProvIDxx);
                    break;
                }
            }
        }));

        mViewModel.getDocuments().observe(Activity_RaffleEntry.this, strings -> {
            spnDocuType.setAdapter(new ArrayAdapter<>(Activity_RaffleEntry.this, R.layout.spinner_drop_down_item, strings));
            spnDocuType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, final int i, long l) {
                    mViewModel.getRaffleBasis().observe(Activity_RaffleEntry.this, raffleBases -> sReferCde = raffleBases.get(i).getReferCde());
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        });
    }

    private void initWidgets(){
        dialog = new LoadDialog(Activity_RaffleEntry.this);
        loMessage = new MessageBox(Activity_RaffleEntry.this);
        voucher = new RaffleEntry();
        toolbar = findViewById(R.id.toolbar_raffleEntry);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        lblbranch = findViewById(R.id.lbl_headerBranch);
        lblAddrss = findViewById(R.id.lbl_headerAddress);
        lblMessage = findViewById(R.id.lbl_footerMessage);
        txtName = findViewById(R.id.txt_customerName);
        txtAddress = findViewById(R.id.txt_customerAddress);
        txtTown = findViewById(R.id.txt_customerTownProv);
        txtDocuNo = findViewById(R.id.txt_documentNumber);
        txtMobileNo = findViewById(R.id.txt_mobileNo);
        spnDocuType = findViewById(R.id.spn_documentType);
        btnSubmit = findViewById(R.id.btn_promoSubmit);
    }

    @Override
    public void OnSendingEntry(String Title, String Message) {
        dialog.initDialog(Title, Message, false);
        dialog.show();
    }

    @Override
    public void OnSuccessEntry() {
        dialog.dismiss();
        loMessage.initDialog();
        loMessage.setTitle("Raffle Entry");
        loMessage.setMessage("Information has been sent to server. Please inform the customer to wait for SMS with link attachment");
        loMessage.setPositiveButton("Okay", (view, msgDialog) -> {
            msgDialog.dismiss();
            txtName.setText("");
            txtAddress.setText("");
            txtTown.setText("");
            txtDocuNo.setText("");
            txtMobileNo.setText("");
            spnDocuType.setSelection(0);
        });
        loMessage.show();
    }

    @Override
    public void OnFailedEntry(String message) {
        dialog.dismiss();
        Toast.makeText(Activity_RaffleEntry.this, message, Toast.LENGTH_SHORT).show();
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