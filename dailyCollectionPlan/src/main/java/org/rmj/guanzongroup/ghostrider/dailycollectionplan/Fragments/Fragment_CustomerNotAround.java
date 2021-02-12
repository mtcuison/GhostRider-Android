package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Activities.Activity_Transaction;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel.VMCustomerNotAround;

public class Fragment_CustomerNotAround extends Fragment {
    VMCustomerNotAround mViewModel;
    private MessageBox poMessage;
    private CheckBox cbPrimeContact, cbPermanentAdd, cbPResentAdd, cbPrimary;
    private Spinner spnRequestCode;
    private TextView lblBranch, lblAddress, lblAccNo, lblClientNm, lblTransNo, lblClientAddress;
    private RadioGroup rg_CNA_Input;
    private TextInputEditText txtContact, txtHouseNox, txtAddress, txtTown,txtProvince , txtBrgy, txtRemarks;
    private LinearLayout lnContactNox,
            lnAddress;
    private MaterialButton btnAdd, btnCommit, btnSubmit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_not_around_fragment, container, false);

        poMessage = new MessageBox(getActivity());
        initWidgets(view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = ViewModelProviders.of(this).get(VMCustomerNotAround.class);



    }

    private void initWidgets(View v){
        cbPrimeContact = v.findViewById(R.id.cb_primaryContact);
        cbPermanentAdd = v.findViewById(R.id.cb_permanent);
        cbPResentAdd = v.findViewById(R.id.cb_present);
        cbPrimary = v.findViewById(R.id.cb_primary);
        spnRequestCode = v.findViewById(R.id.spn_requestCode);
        rg_CNA_Input = v.findViewById(R.id.rg_CnaInput);
        txtContact = v.findViewById(R.id.txt_dcpCNA_contactNox);
        txtHouseNox = v.findViewById(R.id.txt_houseNox);
        txtAddress = v.findViewById(R.id.txt_address);
        txtProvince = v.findViewById(R.id.txt_dcpCNA_prov);
        txtTown = v.findViewById(R.id.txt_dcpCNA_town);
        txtBrgy = v.findViewById(R.id.txt_dcpCNA_brgy);
        txtRemarks = v.findViewById(R.id.txt_dcpCNA_remarks);
        btnAdd = v.findViewById(R.id.btn_add);
        btnCommit = v.findViewById(R.id.btn_commit);
        btnSubmit = v.findViewById(R.id.btn_submit);

        lnContactNox = v.findViewById(R.id.CNA_Contact);
        lnAddress = v.findViewById(R.id.CNA_Address);
        rg_CNA_Input.setOnCheckedChangeListener(new Fragment_CustomerNotAround.OnRadioButtonSelectListener());
    }

    private class OnRadioButtonSelectListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if(group.getId() == R.id.rg_CnaInput) {
                if(checkedId == R.id.rb_contactNox) {
                    // this contact
                }
                else if(checkedId == R.id.rb_address){
                    lnContactNox.setVisibility(View.GONE);
                }
            }
        }

    }
}