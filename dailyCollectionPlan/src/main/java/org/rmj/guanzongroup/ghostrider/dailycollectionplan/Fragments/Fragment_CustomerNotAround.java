    package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Fragments;

    import android.os.Bundle;

    import androidx.annotation.Nullable;
    import androidx.fragment.app.Fragment;
    import androidx.lifecycle.ViewModelProviders;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;

    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.AdapterView;
    import android.widget.ArrayAdapter;
    import android.widget.AutoCompleteTextView;
    import android.widget.CheckBox;
    import android.widget.CompoundButton;
    import android.widget.LinearLayout;
    import android.widget.RadioButton;
    import android.widget.RadioGroup;
    import android.widget.Spinner;
    import android.widget.TextView;

    import com.google.android.material.button.MaterialButton;
    import com.google.android.material.textfield.TextInputEditText;

    import org.rmj.g3appdriver.GRider.Etc.MessageBox;
    import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Activities.Activity_Transaction;
    import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Adapter.AddressInfoAdapter;
    import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Adapter.MobileInfoAdapter;
    import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Model.AddressUpdate;
    import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Model.MobileUpdate;
    import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;
    import org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel.VMCustomerNotAround;
    import org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel.ViewModelCallback;

    import java.util.Objects;

    public class Fragment_CustomerNotAround extends Fragment implements ViewModelCallback {
        private VMCustomerNotAround mViewModel;
        private AddressUpdate addressInfoModel;
        private MobileUpdate mobileInfoModel;

        private MobileInfoAdapter mobileAdapter;
        private AddressInfoAdapter addressAdapter;

        private MessageBox poMessage;
        private CheckBox cbPrimeContact, cbPrimary;
        private Spinner spnRequestCode;
        private TextView lblBranch, lblAddress, lblAccNo, lblClientNm, lblClientAddress;
        private RadioGroup rg_CNA_Input, rg_addressType;
        private RadioButton rb_permanent, rb_present;
        private TextInputEditText txtContact, txtHouseNox, txtAddress, txtRemarks;
        private AutoCompleteTextView txtTown, txtBrgy;
        private LinearLayout lnContactNox,
                lnAddress;
        private MaterialButton btnAdd, btnCommit, btnSubmit;
        private RecyclerView rvCNAOutputs;

        private String sRqstCode, sPrimaryx;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_customer_not_around_fragment, container, false);
            addressInfoModel = new AddressUpdate();
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

            mViewModel.getMobileRequestList().observe(getViewLifecycleOwner(), mobileNox -> {
                try {
                    mobileAdapter = new MobileInfoAdapter(new MobileInfoAdapter.OnItemInfoClickListener() {
                        @Override
                        public void OnDelete(int position) {
                            mViewModel.deleteMobile(mobileNox.get(position).getTransNox());
                        }
                    });
                    rvCNAOutputs.setAdapter(mobileAdapter);
                    mobileAdapter.setMobileNox(mobileNox);
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
            });

            // Province
            mViewModel.getTownProvinceInfo().observe(getViewLifecycleOwner(), townProvinceInfos -> {
                String[] townProvince = new String[townProvinceInfos.size()];
                for(int x = 0; x < townProvinceInfos.size(); x++){
                    townProvince[x] = townProvinceInfos.get(x).sTownName + ", " + townProvinceInfos.get(x).sProvName;
                }
                ArrayAdapter<String> loAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, townProvince);
                txtTown.setAdapter(loAdapter);
            });

            txtTown.setOnItemClickListener((adapterView, view, i, l) -> {
                String lsTown = txtTown.getText().toString();
                String[] town = lsTown.split(", ");
                mViewModel.getTownProvinceInfo().observe(getViewLifecycleOwner(), townProvinceInfos -> {
                    for(int x = 0; x < townProvinceInfos.size(); x++){
                        if(lsTown.equalsIgnoreCase(townProvinceInfos.get(x).sTownName + ", " + townProvinceInfos.get(x).sProvName)){
                            addressInfoModel.setTownID(townProvinceInfos.get(x).sTownIDxx);
                            mViewModel.setTownID(townProvinceInfos.get(x).sTownIDxx);
                            break;
                        }
                    }

                    mViewModel.getBarangayNameList().observe(getViewLifecycleOwner(), strings -> {
                        ArrayAdapter<String> loAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, strings);
                        txtBrgy.setAdapter(loAdapter);
                    });
                });
            });

            txtBrgy.setOnItemClickListener((adapterView, view, i, l) -> mViewModel.getBarangayInfoList().observe(getViewLifecycleOwner(), eBarangayInfos -> {
                for(int x = 0; x < eBarangayInfos.size(); x++){
                    if(txtBrgy.getText().toString().equalsIgnoreCase(eBarangayInfos.get(x).getBrgyName())){
                        addressInfoModel.setBarangayID(eBarangayInfos.get(x).getBrgyIDxx());
                        mViewModel.setBrgyID(eBarangayInfos.get(x).getBrgyIDxx());
                        break;
                    }
                }
            }));


            mViewModel.getRequestCodeOptions().observe(getViewLifecycleOwner(), stringArrayAdapter -> spnRequestCode.setAdapter(stringArrayAdapter));
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
            rb_permanent = v.findViewById(R.id.rb_permanent);
            rb_present = v.findViewById(R.id.rb_present);
            txtContact = v.findViewById(R.id.txt_dcpCNA_contactNox);
            txtHouseNox = v.findViewById(R.id.txt_houseNox);
            txtAddress = v.findViewById(R.id.txt_address);
            txtTown = v.findViewById(R.id.txt_dcpCNA_town);
            txtBrgy = v.findViewById(R.id.txt_dcpCNA_brgy);
            txtRemarks = v.findViewById(R.id.txt_dcpCNA_remarks);
            btnAdd = v.findViewById(R.id.btn_add);
            btnCommit = v.findViewById(R.id.btn_commit);
            btnSubmit = v.findViewById(R.id.btn_submit);

            lnContactNox = v.findViewById(R.id.CNA_Contact);
            lnAddress = v.findViewById(R.id.CNA_Address);

            rvCNAOutputs = v.findViewById(R.id.rv_CNA_outputs);
            rvCNAOutputs.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvCNAOutputs.setHasFixedSize(true);

            btnAdd.setOnClickListener(view -> addMobile());
            rg_CNA_Input.setOnCheckedChangeListener(new Fragment_CustomerNotAround.OnRadioButtonSelectListener());
            rg_addressType.setOnCheckedChangeListener(new Fragment_CustomerNotAround.OnRadioButtonSelectListener());
            spnRequestCode.setOnItemSelectedListener(new Fragment_CustomerNotAround.OnJobStatusSelectedListener());
            cbPrimary.setOnCheckedChangeListener(new OnCheckboxSetListener());
            cbPrimeContact.setOnCheckedChangeListener(new OnCheckboxSetListener());
        }

        private void addAddress() {
            addressInfoModel.setHouseNumber(Objects.requireNonNull(txtHouseNox.getText().toString()));
            addressInfoModel.setAddress(Objects.requireNonNull(txtAddress.getText().toString()));
            addressInfoModel.setsRemarksx(Objects.requireNonNull(txtRemarks.getText().toString()));

            mViewModel.addAddress(addressInfoModel, Fragment_CustomerNotAround.this);
        }

        private void addMobile() {
            mViewModel.getRequestCode().observe(getViewLifecycleOwner(), string -> {
                sRqstCode = string;
            });
            mViewModel.getPrimeContact().observe(getViewLifecycleOwner(), string -> {
               sPrimaryx = string;
            });

            String sMobileNo = txtContact.getText().toString();
            String sRemarks = txtRemarks.getText().toString();

            mobileInfoModel = new MobileUpdate(sRqstCode, sMobileNo, sPrimaryx,sRemarks);
            mViewModel.addMobile(mobileInfoModel, Fragment_CustomerNotAround.this);
        }

        @Override
        public void OnStartSaving() {

        }

        @Override
        public void OnSuccessResult(String[] args) {
            poMessage.setTitle("Transaction Success");
            poMessage.setMessage(args[0]);
            poMessage.setPositiveButton("Okay", (view, dialog) -> dialog.dismiss());
            poMessage.show();
        }

        @Override
        public void OnFailedResult(String message) {
            poMessage.setTitle("Transaction Failed");
            poMessage.setMessage(message);
            poMessage.setPositiveButton("Okay", (view, dialog) -> dialog.dismiss());
            poMessage.show();
        }

        private class OnRadioButtonSelectListener implements RadioGroup.OnCheckedChangeListener {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(group.getId() == R.id.rg_CnaInput) {
                    if(checkedId == R.id.rb_contactNox) {
                        lnContactNox.setVisibility(View.VISIBLE);
                        lnAddress.setVisibility(View.GONE);
                        spnRequestCode.setSelection(0);
                        rb_permanent.setChecked(false);
                        rb_present.setChecked(false);
                        txtHouseNox.setText("");
                        txtAddress.setText("");
                        txtTown.setText("");
                        txtBrgy.setText("");
                        cbPrimary.setChecked(false);
                        txtRemarks.setText("");
                        btnAdd.setOnClickListener(view -> addMobile());

                        mViewModel.getMobileRequestList().observe(getViewLifecycleOwner(), mobileNox -> {
                            try {
                                mobileAdapter = new MobileInfoAdapter(new MobileInfoAdapter.OnItemInfoClickListener() {
                                    @Override
                                    public void OnDelete(int position) {
                                        mViewModel.deleteMobile(mobileNox.get(position).getTransNox());
                                    }
                                });
                                rvCNAOutputs.setAdapter(mobileAdapter);
                                mobileAdapter.setMobileNox(mobileNox);
                            }
                            catch(Exception e) {
                                e.printStackTrace();
                            }
                        });
                    }
                    else if(checkedId == R.id.rb_address){
                        lnContactNox.setVisibility(View.GONE);
                        lnAddress.setVisibility(View.VISIBLE);
                        spnRequestCode.setSelection(0);
                        cbPrimeContact.setChecked(false);
                        txtContact.setText("");
                        txtRemarks.setText("");
                        btnAdd.setOnClickListener(view -> addAddress());

                        mViewModel.getAddressNames().observe(getViewLifecycleOwner(), addressNme -> {
                            try {
                                addressAdapter = new AddressInfoAdapter(new AddressInfoAdapter.OnDeleteInfoListener() {
                                    @Override
                                    public void OnDelete(int position) {
                                        mViewModel.deleteAddress(addressNme.get(position).sTransNox);
                                    }
                                });
                                rvCNAOutputs.setAdapter(addressAdapter);
                                addressAdapter.setAddress(addressNme);
                            } catch (Exception e){
                                e.printStackTrace();
                            }
                        });
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
                    mViewModel.setPrimeContact("1");
                }
                else{
                    mViewModel.setPrimeAddress("0");
                    mViewModel.setPrimeContact("0");
                }
            }
        }

        public void deleteAddress(String TransNox) {
            mViewModel.deleteAddress(TransNox);
        }
    }