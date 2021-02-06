package org.rmj.guanzongroup.promotions;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.rmj.g3appdriver.GRider.Database.Entities.ERaffleBasis;
import org.rmj.g3appdriver.GRider.Etc.LoadDialog;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.g3appdriver.dev.AppData;
import org.rmj.guanzongroup.promotions.Etc.RaffleEntryCallback;
import org.rmj.guanzongroup.promotions.Model.RaffleEntry;
import org.rmj.guanzongroup.promotions.ViewModel.VMRaffleEntry;

import java.util.List;

public class Fragment_RaffleEntry extends Fragment implements RaffleEntryCallback {
    private TextInputEditText txtName, txtDocuNo, txtMobileNo;
    private Spinner spnDocuType;
    private MaterialButton btnSubmit;
    private VMRaffleEntry mViewModel;
    private String sReferCde = "";
    private LoadDialog dialog;
    private MessageBox loMessage;

    public static Fragment_RaffleEntry newInstance() {
        return new Fragment_RaffleEntry();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_raffle_entry, container, false);
        dialog = new LoadDialog(getActivity());
        loMessage = new MessageBox(getActivity());
        txtName = v.findViewById(R.id.txt_customerName);
        txtDocuNo = v.findViewById(R.id.txt_documentNumber);
        txtMobileNo = v.findViewById(R.id.txt_mobileNo);
        spnDocuType = v.findViewById(R.id.spn_documentType);
        btnSubmit = v.findViewById(R.id.btn_promoSubmit);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VMRaffleEntry.class);
        mViewModel.importDocuments();
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String lsCustomerNm = txtName.getText().toString();
                String lsDocumentNo = txtDocuNo.getText().toString();
                String lsMobileNoxx = txtMobileNo.getText().toString();
                String lsBranchCode = AppData.getInstance(getActivity()).getBranchCode();
                RaffleEntry voucher = new RaffleEntry(lsBranchCode, lsCustomerNm, lsDocumentNo, lsMobileNoxx);
                voucher.setDocumentType(sReferCde);
                mViewModel.submit(voucher, Fragment_RaffleEntry.this);
            }
        });

        mViewModel.getDocuments().observe(getViewLifecycleOwner(), new Observer<String[]>() {
            @Override
            public void onChanged(String[] strings) {
                spnDocuType.setAdapter(new ArrayAdapter<>(getActivity(), R.layout.spinner_drop_down_item, strings));
                spnDocuType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, final int i, long l) {
                        mViewModel.getRaffleBasis().observe(getViewLifecycleOwner(), new Observer<List<ERaffleBasis>>() {
                            @Override
                            public void onChanged(List<ERaffleBasis> raffleBases) {
                                sReferCde = raffleBases.get(i).getReferCde();
                            }
                        });
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
        });
    }

    @Override
    public void OnSendingEntry(String Title, String Message) {
        dialog.initDialog(Title, Message, false);
        dialog.show();
    }

    @Override
    public void OnSuccessEntry() {
        dialog.dismiss();
        loMessage.setTitle("Raffle Entry");
        loMessage.setMessage("Information has been sent to server. Please inform the customer to wait for SMS with link attachment");
        loMessage.setPositiveButton("Okay", new MessageBox.DialogButton() {
            @Override
            public void OnButtonClick(View view, AlertDialog msgDialog) {
                msgDialog.dismiss();
                txtName.setText("");
                txtDocuNo.setText("");
                txtMobileNo.setText("");
                spnDocuType.setSelection(0);
            }
        });
        loMessage.show();
    }

    @Override
    public void OnFailedEntry(String message) {
        dialog.dismiss();
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}