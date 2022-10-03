/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.dailyCollectionPlan
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;

import org.rmj.g3appdriver.GRider.Etc.LoadDialog;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.g3appdriver.etc.WebFileServer;
import org.rmj.g3appdriver.lib.integsys.Dcp.AddressUpdate;
import org.rmj.g3appdriver.lib.integsys.Dcp.MobileUpdate;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Activities.Activity_Transaction;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Adapter.AddressInfoAdapter;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Adapter.MobileInfoAdapter;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel.VMCustomerNotAround;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel.ViewModelCallback;
import org.rmj.guanzongroup.ghostrider.imgcapture.ImageFileCreator;

import java.util.ArrayList;
import java.util.List;

public class Fragment_CustomerNotAround extends Fragment {

    private VMCustomerNotAround mViewModel;
    private LoadDialog poDialog;
    private MessageBox poMessage;
    private AddressUpdate poAddress;
    private MobileUpdate poMobile;
    private MobileInfoAdapter mobileAdapter;
    private AddressInfoAdapter addressAdapter;
    private EImageInfo poImageInfo;

    private ImageFileCreator poImage;

    private CheckBox cbPrimeContact, cbPrimary;
    private TextView lblBranch, lblAddress, lblAccNo, lblClientNm, lblClientAddress;
    private RadioGroup rg_CNA_Input, rg_addressType;
    private RadioButton rb_permanent, rb_present;
    private TextInputEditText txtContact, txtHouseNox, txtAddress, txtRemarks;
    private AutoCompleteTextView txtTown, txtBrgy, spnRequestCode;
    private LinearLayout lnContactNox,
            lnAddress;
    private MaterialButton btnAdd, btnSubmit;
    private RecyclerView rvCNAOutputs;
    private String Remarksx;

    private String sRqstCode, sPrimaryx , psPhotox;
    private static final int MOBILE_DIALER = 104;

    private List<String> TownProvince = new ArrayList();

    private boolean isAddressAdded, isMobileAdded, isMobileToggled = true;

    private final ActivityResultLauncher<Intent> poCamera = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == RESULT_OK){

                if (isMobileToggled) {
//                    mViewModel.saveMobileToLocal(Fragment_CustomerNotAround.this);
                } else {
//                    mViewModel.saveAddressToLocal(Fragment_CustomerNotAround.this);
                }

                try{
                    poImageInfo.setMD5Hashx(WebFileServer.createMD5Hash(psPhotox));
//                    mViewModel.saveImageInfo(poImageInfo);

//                    mViewModel.updateCollectionDetail(txtRemarks.getText().toString());
//                    Log.e("Fragment_CNA:", "Image Info Save");
//                    OnSuccessResult();
                } catch (RuntimeException e){
                    e.printStackTrace();
                }
            }else {
                requireActivity().finish();
            }
        }
    });

    private final ActivityResultLauncher<Intent> poDialer = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if(result.getResultCode() == RESULT_OK){

        } else {

        }
    });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(requireActivity()).get(VMCustomerNotAround.class);
        View view = inflater.inflate(R.layout.fragment_customer_not_around_fragment, container, false);
        poAddress = new AddressUpdate();
        poMobile = new MobileUpdate();
        poDialog = new LoadDialog(requireActivity());
        poMessage = new MessageBox(requireActivity());
        poImageInfo = new EImageInfo();

        String TransNox = Activity_Transaction.getInstance().getTransNox();
        String AccountN = Activity_Transaction.getInstance().getAccntNox();
        int EntryNox = Activity_Transaction.getInstance().getEntryNox();
        initWidgets(view);

        mViewModel.GetUserInfo().observe(getViewLifecycleOwner(), user -> {
            try{
                lblBranch.setText(user.sBranchNm);
                lblAddress.setText(user.sAddressx);
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        mViewModel.GetCollectionDetail(TransNox, AccountN, String.valueOf(EntryNox)).observe(getViewLifecycleOwner(), detail -> {
            try{
                poAddress.setsClientID(detail.getClientID());
                poMobile.setClientID(detail.getClientID());

                lblAccNo.setText(detail.getAcctNmbr());
                lblClientNm.setText(detail.getFullName());
                lblClientAddress.setText(detail.getAddressx());

                mViewModel.GetMobileUpdates(detail.getClientID()).observe(getViewLifecycleOwner(), mobileNox -> {
                    try {
                        mobileAdapter = new MobileInfoAdapter(new MobileInfoAdapter.OnItemInfoClickListener() {
                            @Override
                            public void OnDelete(int position) {
                                mViewModel.RemoveMobile(mobileNox.get(position).sTransNox, new VMCustomerNotAround.OnRemoveDetailCallback() {
                                    @Override
                                    public void OnSuccess() {
                                        Toast.makeText(requireActivity(), "Mobile info remove.", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void OnFailed(String message) {
                                        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                            @Override
                            public void OnMobileNoClick(String MobileNo) {
                                Intent mobileIntent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", MobileNo, null));
                                poDialer.launch(mobileIntent);
                            }
                        });
                        rvCNAOutputs.setAdapter(mobileAdapter);
                        mobileAdapter.setMobileNox(mobileNox);
                    }
                    catch(Exception e) {
                        e.printStackTrace();
                    }
                });

                mViewModel.GetAddressUpdates(detail.getClientID()).observe(getViewLifecycleOwner(), address -> {
                    try {
                        addressAdapter = new AddressInfoAdapter(position -> mViewModel.RemoveAddress(address.get(position).sTransNox, new VMCustomerNotAround.OnRemoveDetailCallback() {
                            @Override
                            public void OnSuccess() {
                                Toast.makeText(requireActivity(), "Mobile info remove.", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void OnFailed(String message) {
                                Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show();
                            }
                        }));
                        rvCNAOutputs.setAdapter(addressAdapter);
                        addressAdapter.setAddress(address);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                });
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        mViewModel.getTownProvinceInfo().observe(getViewLifecycleOwner(), townProvinceInfos -> {
            try {
                List<String> loTownList = new ArrayList<>();
                for (int x = 0; x < townProvinceInfos.size(); x++) {
                    loTownList.add(townProvinceInfos.get(x).sTownName + ", " + townProvinceInfos.get(x).sProvName);
                }

                ArrayAdapter<String> loAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, loTownList.toArray(new String[0]));
                txtTown.setAdapter(loAdapter);

                txtTown.setOnItemClickListener((adapterView, v, i, l) -> {
                    String lsTown = txtTown.getText().toString();
                    for (int x = 0; x < townProvinceInfos.size(); x++) {
                        if (lsTown.equalsIgnoreCase(townProvinceInfos.get(x).sTownName + ", " + townProvinceInfos.get(x).sProvName)) {
                            poAddress.setsProvIDxx(townProvinceInfos.get(x).sProvIDxx);
                            poAddress.setTownID(townProvinceInfos.get(x).sTownIDxx);
                            break;
                        }
                    }

                    mViewModel.GetBarangayList(poAddress.getTownID()).observe(getViewLifecycleOwner(), barangays -> {
                        List<String> loBrgyList = new ArrayList<>();
                        for(int x = 0; x < barangays.size(); x++){
                            loBrgyList.add(barangays.get(x).getBrgyName());
                        }
                        ArrayAdapter<String> loBrgy = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_dropdown_item, loBrgyList.toArray(new String[0]));
                        txtBrgy.setAdapter(loBrgy);
                    });
                });
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        txtBrgy.setOnItemClickListener((adapterView, v, i, l) -> mViewModel.GetBarangayList(poAddress.getTownID()).observe(getViewLifecycleOwner(), eBarangayInfos -> {
            for (int x = 0; x < eBarangayInfos.size(); x++) {
                if (txtBrgy.getText().toString().equalsIgnoreCase(eBarangayInfos.get(x).getBrgyName())) {
                    poAddress.setBarangayID(eBarangayInfos.get(x).getBrgyIDxx());
                    break;
                }
            }
        }));

        mViewModel.getRequestCodeOptions().observe(getViewLifecycleOwner(), stringArrayAdapter -> {
            spnRequestCode.setAdapter(stringArrayAdapter);
            spnRequestCode.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
        });

        btnAdd.setOnClickListener(view1 -> {
            if(isMobileToggled) {
                poMobile.setMobileNo(txtContact.getText().toString());
                poMobile.setRemarksx(txtRemarks.getText().toString());
                mViewModel.SaveNewMobile(poMobile, new ViewModelCallback() {
                    @Override
                    public void OnStartSaving() {
                        poDialog.initDialog("Customer Not Around", "Saving mobile update. Please wait...", false);
                        poDialog.show();
                    }

                    @Override
                    public void OnSuccessResult() {
                        poDialog.dismiss();
                        Toast.makeText(requireActivity(), "New record save!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void OnFailedResult(String message) {
                        poDialog.dismiss();
                        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                poAddress.setHouseNumber(txtHouseNox.getText().toString());
                poAddress.setAddress(txtAddress.getText().toString());
                poAddress.setsRemarksx(txtRemarks.getText().toString());
                mViewModel.SaveNewAddress(poAddress, new ViewModelCallback() {
                    @Override
                    public void OnStartSaving() {
                        poDialog.initDialog("Customer Not Around", "Saving address update. Please wait...", false);
                        poDialog.show();
                    }

                    @Override
                    public void OnSuccessResult() {
                        poDialog.dismiss();
                        Toast.makeText(requireActivity(), "New record save!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void OnFailedResult(String message) {
                        poDialog.dismiss();
                        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btnSubmit.setOnClickListener(v -> {
            poMessage.initDialog();
            poMessage.setTitle("Customer Not Around");
            poMessage.setMessage("Please take a selfie in customer's place in order to confirm transaction.");
            poMessage.setPositiveButton("Okay", (v1, dialog) -> {
                dialog.dismiss();
                poImage.CreateFile((openCamera, camUsage, photPath, FileName) -> {
                });
            });
            poMessage.setNegativeButton("Cancel", (v1, dialog) -> dialog.dismiss());
            poMessage.show();
        });
        return view;
    }

    private void initWidgets(View v){
        Remarksx = Activity_Transaction.getInstance().getRemarksCode();
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
        btnSubmit = v.findViewById(R.id.btn_submit);

        lnContactNox = v.findViewById(R.id.CNA_Contact);
        lnAddress = v.findViewById(R.id.CNA_Address);

        rvCNAOutputs = v.findViewById(R.id.rv_CNA_outputs);
        rvCNAOutputs.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvCNAOutputs.setHasFixedSize(true);

        rg_CNA_Input.setOnCheckedChangeListener(new Fragment_CustomerNotAround.OnRadioButtonSelectListener());
        rg_addressType.setOnCheckedChangeListener(new Fragment_CustomerNotAround.OnRadioButtonSelectListener());
        spnRequestCode.setOnItemClickListener((adapterView, view, i, l) -> {
            poMobile.setReqstCde(String.valueOf(i));
            poAddress.setRequestCode(String.valueOf(i));
        });
        cbPrimary.setOnCheckedChangeListener(new OnCheckboxSetListener());
        cbPrimeContact.setOnCheckedChangeListener(new OnCheckboxSetListener());
    }

//    private void addAddress() {
//        poAddress.setHouseNumber(Objects.requireNonNull(Objects.requireNonNull(txtHouseNox.getText()).toString()));
//        poAddress.setAddress(Objects.requireNonNull(Objects.requireNonNull(txtAddress.getText()).toString()));
////        new LocationRetriever(getActivity(), getActivity()).getLocation((message, latitude, longitude) -> {
////            addressInfoModel.setLatitude(String.valueOf(latitude));
////            addressInfoModel.setLongitude(String.valueOf(longitude));
////        });
//        poAddress.setsRemarksx(Objects.requireNonNull(Objects.requireNonNull(txtRemarks.getText()).toString()));
//
//        isAddressAdded = mViewModel.addAddressToList(poAddress, Fragment_CustomerNotAround.this);
//        checkIfAddressAdded(isAddressAdded);
//    }
//
//    private void addMobile() {
//        mViewModel.getRequestCode().observe(getViewLifecycleOwner(), string -> sRqstCode = string);
//        mViewModel.getPrimeContact().observe(getViewLifecycleOwner(), string -> sPrimaryx = string);
//
//        String sMobileNo = Objects.requireNonNull(txtContact.getText()).toString();
//        String sRemarks = Objects.requireNonNull(txtRemarks.getText()).toString();
//
//        poMobile = new MobileUpdate(sRqstCode, sMobileNo, sPrimaryx,sRemarks);
//        isMobileAdded = mViewModel.AddMobileToList(poMobile, Fragment_CustomerNotAround.this);
//        checkIfMobileAdded(isMobileAdded);
//    }
//
//    @Override
//    public void OnStartSaving() {
//
//    }
//
//    @Override
//    public void OnSuccessResult() {
//        poMessage.initDialog();
//        poMessage.setTitle("Transaction Success");
//        poMessage.setMessage("Collection save successfully");
//        poMessage.setPositiveButton("Okay", (view, dialog) -> {
//            dialog.dismiss();
//            getActivity().finish();
//        });
//        poMessage.show();
//    }
//
//    @Override
//    public void OnFailedResult(String message) {
//        poMessage.initDialog();
//        poMessage.setTitle("Transaction Failed");
//        poMessage.setMessage(message);
//        poMessage.setPositiveButton("Okay", (view, dialog) -> dialog.dismiss());
//        poMessage.show();
//    }
//
//    private void checkIfAddressAdded(boolean isAddressAdded) {
//        if(isAddressAdded) {
//            spnRequestCode.setSelection(0);
//            rb_permanent.setChecked(false);
//            rb_present.setChecked(false);
//            txtHouseNox.setText("");
//            txtAddress.setText("");
//            txtTown.setText("");
//            txtBrgy.setText("");
//            cbPrimary.setChecked(false);
//            txtRemarks.setText("");
//            addressAdapter.notifyDataSetChanged();
//        }
//    }
//
//    private void checkIfMobileAdded(boolean isMobileAdded) {
//        if(isMobileAdded) {
//            spnRequestCode.setSelection(0);
//            cbPrimeContact.setChecked(false);
//            txtContact.setText("");
//            txtRemarks.setText("");
//            mobileAdapter.notifyDataSetChanged();
//        }
//    }

    private class OnRadioButtonSelectListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if(group.getId() == R.id.rg_CnaInput) {
                if(checkedId == R.id.rb_contactNox) {
                    isMobileToggled = true;
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

                } else if(checkedId == R.id.rb_address){
                    isMobileToggled = false;
                    lnContactNox.setVisibility(View.GONE);
                    lnAddress.setVisibility(View.VISIBLE);
                    spnRequestCode.setSelection(0);
                    cbPrimeContact.setChecked(false);
                    txtContact.setText("");
                    txtRemarks.setText("");
                }
            }
            else if(group.getId() == R.id.rg_address_type) {
                if(checkedId == R.id.rb_permanent) {
                    poAddress.setcAddrssTp("0");
                }
                else if(checkedId == R.id.rb_present) {
                    poAddress.setcAddrssTp("1");
                }
            }
        }
    }

    private class OnCheckboxSetListener implements CheckBox.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(buttonView.isChecked()){
                poMobile.setPrimaryx("1");
                poAddress.setPrimaryStatus("1");
            } else{
                poMobile.setPrimaryx("0");
                poAddress.setPrimaryStatus("0");
            }
        }
    }
}