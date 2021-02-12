package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.rmj.g3appdriver.GRider.Database.Entities.EBarangayInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EProvinceInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.ETownInfo;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Activities.Activity_Transaction;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Model.AddressUpdate;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel.VMCustomerNotAround;

import java.util.List;
import java.util.Objects;

public class Fragment_CustomerNotAround extends Fragment {
    private VMCustomerNotAround mViewModel;
    private AddressUpdate infoModel;
    private MessageBox poMessage;
    private CheckBox cbPrimeContact, cbPrimary;
    private Spinner spnRequestCode;
    private TextView lblBranch, lblAddress, lblAccNo, lblClientNm, lblClientAddress;
    private RadioGroup rg_CNA_Input, rg_addressType;
    private TextInputEditText txtContact, txtHouseNox, txtAddress, txtRemarks;
    private AutoCompleteTextView txtTown,txtProvince , txtBrgy;
    private LinearLayout lnContactNox,
            lnAddress;
    private MaterialButton btnAdd, btnCommit, btnSubmit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_not_around_fragment, container, false);


        infoModel = new AddressUpdate();
        poMessage = new MessageBox(getActivity());
        initWidgets(view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String TransNox = Activity_Transaction.getInstance().getTransNox();
        String EntryNox = Activity_Transaction.getInstance().getEntryNox();
        mViewModel = ViewModelProviders.of(this).get(VMCustomerNotAround.class);

        mViewModel.setParameter(TransNox, EntryNox);

        mViewModel.getCollectionDetail().observe(getViewLifecycleOwner(), collectionDetail -> {
            try {
                lblAccNo.setText(collectionDetail.getAcctNmbr());
                lblClientNm.setText(collectionDetail.getFullName());
                lblClientAddress.setText(collectionDetail.getAddressx());

                mViewModel.setClientID(collectionDetail.getClientID());
                mViewModel.setCurrentCollectionDetail(collectionDetail);
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        mViewModel.getUserBranchEmployee().observe(getViewLifecycleOwner(), eBranchInfo -> {
            lblBranch.setText(eBranchInfo.getBranchNm());
            lblAddress.setText(eBranchInfo.getAddressx());
        });

        mViewModel.getProvinceNames().observe(getViewLifecycleOwner(), new Observer<String[]>() {
            @Override
            public void onChanged(String[] strings) {
                try{
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, strings);
                    txtProvince.setAdapter(adapter);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });


        //Getting ID of the Province
        txtProvince.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mViewModel.getProvinceInfos().observe(getViewLifecycleOwner(), new Observer<List<EProvinceInfo>>() {
                    @Override
                    public void onChanged(List<EProvinceInfo> eProvinceInfos) {
                        for(int x = 0; x < eProvinceInfos.size(); x++){
                            if(txtProvince.getText().toString().equalsIgnoreCase(eProvinceInfos.get(x).getProvName())){
                                mViewModel.setProvinceID(eProvinceInfos.get(x).getProvIDxx());
                                break;
                            }
                        }

                        mViewModel.getAllTownNames().observe(getViewLifecycleOwner(), new Observer<String[]>() {
                            @Override
                            public void onChanged(String[] strings) {
                                try{
                                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, strings);
                                    txtTown.setAdapter(adapter);
                                } catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        });

                    }
                });
            }
        });

        txtTown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mViewModel.getAllTownInfo().observe(getViewLifecycleOwner(), new Observer<List<ETownInfo>>() {
                    @Override
                    public void onChanged(List<ETownInfo> eTownInfos) {
                        for(int x = 0; x < eTownInfos.size(); x++){
                            if(txtTown.getText().toString().equalsIgnoreCase(eTownInfos.get(x).getTownName())){ //from id to town name
                                mViewModel.setTownID(eTownInfos.get(x).getTownIDxx());
                                break;
                            }
                        }

                        mViewModel.getPermanentBarangayNameList().observe(getViewLifecycleOwner(), new Observer<String[]>() {
                            @Override
                            public void onChanged(String[] strings) {
                                try{
                                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, strings);
                                    txtBrgy.setAdapter(adapter);
                                } catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        });

                    }
                });
            }
        });

        txtBrgy.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mViewModel.getPermanentBarangayInfoList().observe(getViewLifecycleOwner(), new Observer<List<EBarangayInfo>>() {
                    @Override
                    public void onChanged(List<EBarangayInfo> eBrgyInfos) {
                        for(int x = 0; x < eBrgyInfos.size(); x++){
                            if(txtBrgy.getText().toString().equalsIgnoreCase(eBrgyInfos.get(x).getBrgyName())){ //from id to town name
                                mViewModel.setBrgyID(eBrgyInfos.get(x).getBrgyIDxx());
                                break;
                            }
                        }
                    }
                });
            }
        });

        mViewModel.getRequestCodeOptions().observe(getViewLifecycleOwner(), stringArrayAdapter -> spnRequestCode.setAdapter(stringArrayAdapter));


        btnAdd.setOnClickListener(view -> addAddress());
    }

    private void initWidgets(View v){
        lblBranch = v.findViewById(R.id.lbl_headerBranch);
        lblAddress = v.findViewById(R.id.lbl_headerAddress);
        lblAccNo = v.findViewById(R.id.lbl_dcpAccNo);
        lblClientAddress = v.findViewById(R.id.lbl_dcpClientAddress);
        lblClientNm = v.findViewById(R.id.lbl_dcpClientNm);

        cbPrimeContact = v.findViewById(R.id.cb_primaryContact);
        cbPrimary = v.findViewById(R.id.cb_primary);
        spnRequestCode = v.findViewById(R.id.spn_requestCode);
        rg_CNA_Input = v.findViewById(R.id.rg_CnaInput);
        rg_addressType = v.findViewById(R.id.rg_address_type);
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
        rg_addressType.setOnCheckedChangeListener(new Fragment_CustomerNotAround.OnRadioButtonSelectListener());
        spnRequestCode.setOnItemSelectedListener(new Fragment_CustomerNotAround.OnJobStatusSelectedListener());
        cbPrimary.setOnCheckedChangeListener(new OnCheckboxSetListener());
    }

    private void addAddress() {
        infoModel.setHouseNumber(Objects.requireNonNull(txtHouseNox.getText().toString()));
        infoModel.setAddress(Objects.requireNonNull(txtAddress.getText().toString()));
//        infoModel.setsProvIDxx(Objects.requireNonNull());
//        infoModel.setTownID(Objects.requireNonNull());
//        infoModel.setBarangayID(Objects.requireNonNull());
        infoModel.setsRemarksx(Objects.requireNonNull(txtRemarks.getText().toString()));

        mViewModel.addAddress(infoModel);
    }

    private class OnRadioButtonSelectListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if(group.getId() == R.id.rg_CnaInput) {
                if(checkedId == R.id.rb_contactNox) {
                    lnContactNox.setVisibility(View.VISIBLE);
                    lnAddress.setVisibility(View.GONE);
                }
                else if(checkedId == R.id.rb_address){
                    lnContactNox.setVisibility(View.GONE);
                    lnAddress.setVisibility(View.VISIBLE);
                }
            }
            else if(group.getId() == R.id.rg_address_type) {
                if(checkedId == R.id.rb_permanent) {
                    mViewModel.setAddressType("0");
                }
                else if(checkedId == R.id.rb_present) {
                    mViewModel.setAddressType("1");
                }
            }
        }

    }

    private class OnJobStatusSelectedListener implements AdapterView.OnItemSelectedListener{

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (parent.getItemAtPosition(position).toString().equalsIgnoreCase("Request Code")) {
                mViewModel.setRequestCode("");
            } else if (parent.getItemAtPosition(position).toString().equalsIgnoreCase("New")) {
                mViewModel.setRequestCode("0");
            } else if (parent.getItemAtPosition(position).toString().equalsIgnoreCase("Update")) {
                mViewModel.setRequestCode("1");
            } else if (parent.getItemAtPosition(position).toString().equalsIgnoreCase("Change")) {
                mViewModel.setRequestCode("2");
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private class OnCheckboxSetListener implements CheckBox.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(buttonView.isChecked()){
                mViewModel.setPrimeAddress("1");
            }
            else {
                mViewModel.setPrimeAddress("0");
            }
        }
    }
}