    package org.rmj.guanzongroup.onlinecreditapplication.Fragment;

    import androidx.lifecycle.Observer;
    import androidx.lifecycle.ViewModelProviders;

    import android.annotation.SuppressLint;
    import android.os.Bundle;

    import androidx.annotation.NonNull;
    import androidx.annotation.Nullable;
    import androidx.fragment.app.Fragment;

    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.AdapterView;
    import android.widget.ArrayAdapter;
    import android.widget.AutoCompleteTextView;
    import android.widget.Button;
    import android.widget.Spinner;

    import com.google.android.material.button.MaterialButton;
    import com.google.android.material.textfield.TextInputEditText;
    import com.google.android.material.textfield.TextInputLayout;

    import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplicantInfo;
    import org.rmj.g3appdriver.GRider.Database.Entities.EProvinceInfo;
    import org.rmj.g3appdriver.GRider.Database.Entities.ETownInfo;
    import org.rmj.g3appdriver.GRider.Etc.GToast;
    import org.rmj.guanzongroup.onlinecreditapplication.Activity.Activity_CreditApplication;
    import org.rmj.guanzongroup.onlinecreditapplication.Etc.OnBirthSetListener;
    import org.rmj.guanzongroup.onlinecreditapplication.Model.SpouseInfoModel;
    import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;
    import org.rmj.guanzongroup.onlinecreditapplication.R;
    import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMSpouseInfo;

    import java.util.List;
    import java.util.Objects;

    public class Fragment_SpouseInfo extends Fragment implements ViewModelCallBack {
        private static final String TAG = Fragment_SpouseInfo.class.getSimpleName();
        private SpouseInfoModel infoModel;
        private VMSpouseInfo mViewModel;
        private AutoCompleteTextView txtProvince, txtTownxx, txtCitizenx;
        private TextInputEditText txtLastName,
                txtFirstName,
                txtSuffix,
                txtMiddName,
                txtNickName,
                txtBDate,
                txtPrimeCntc,
                txtPrimeCntcYr,
                txtSecCntct,
                txtSecCntctYr,
                txtThirCntct,
                txtThirCntctYr,
                txtTelNox,
                txtEmailAdd,
                txtFbAcct,
                txtViberAcct,
                txtMobileYr1,
                txtMobileYr2,
                txtMobileYr3;

        private String transnox;

        private TextInputLayout tilMobileYr1 ,tilMobileYr2 ,tilMobileYr3;
        private Spinner spnMobile1, spnMobile2, spnMobile3;

        private Button btnNext;

        public static Fragment_SpouseInfo newInstance() {
            return new Fragment_SpouseInfo();
        }

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_spouse_info, container, false);
            infoModel = new SpouseInfoModel();
            transnox = Activity_CreditApplication.getInstance().getTransNox();
            txtProvince = view.findViewById(R.id.txt_bpProvince);
            txtTownxx = view.findViewById(R.id.txt_bpTown);

            txtLastName = view.findViewById(R.id.tie_last_name);
            txtFirstName = view.findViewById(R.id.tie_first_name);
            txtSuffix = view.findViewById(R.id.tie_suffix);
            txtMiddName = view.findViewById(R.id.tie_midd_name);
            txtNickName = view.findViewById(R.id.tie_nick_name);
            txtBDate = view.findViewById(R.id.tie_birth_date);
            txtCitizenx = view.findViewById(R.id.tie_citizenship);

            txtPrimeCntc = view.findViewById(R.id.txt_mobileNo1);
            txtPrimeCntcYr = view.findViewById(R.id.txt_mobileNo1Year);

            txtSecCntct = view.findViewById(R.id.txt_mobileNo2);
            txtSecCntctYr = view.findViewById(R.id.txt_mobileNo2Year);

            txtThirCntct = view.findViewById(R.id.txt_mobileNo3);
            txtThirCntctYr = view.findViewById(R.id.txt_mobileNo3Year);

            txtTelNox = view.findViewById(R.id.tie_tel_no);
            txtEmailAdd = view.findViewById(R.id.tie_emailAdd);
            txtFbAcct = view.findViewById(R.id.tie_fbAcct);
            txtViberAcct = view.findViewById(R.id.tie_viberAcct);

            spnMobile1 = view.findViewById(R.id.spn_mobile1Type);
            spnMobile2 = view.findViewById(R.id.spn_mobile2Type);
            spnMobile3 = view.findViewById(R.id.spn_mobile3Type);

            txtMobileYr1 = view.findViewById(R.id.txt_mobileNo1Year);
            tilMobileYr1 = view.findViewById(R.id.til_mobileNo1Year);
            txtMobileYr2 = view.findViewById(R.id.txt_mobileNo2Year);
            tilMobileYr2 = view.findViewById(R.id.til_mobileNo2Year);
            txtMobileYr3 = view.findViewById(R.id.txt_mobileNo3Year);
            tilMobileYr3 = view.findViewById(R.id.til_mobileNo3Year);

            btnNext = view.findViewById(R.id.btn_creditAppNext);

            return view;
        }

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

            mViewModel = ViewModelProviders.of(this).get(VMSpouseInfo.class);

            // Get transNox first from the holding activity
            String TransNox = Activity_CreditApplication.getInstance().getTransNox();

            // SetTransNox of ViewModel
            mViewModel.setTransNox(TransNox);


            // Set DetailInfo to goCas
            mViewModel.getActiveGOCasApplication().observe(getViewLifecycleOwner(), new Observer<ECreditApplicantInfo>() {
                @Override
                public void onChanged(ECreditApplicantInfo eCreditApplicantInfo) {
                    mViewModel.setDetailInfo(eCreditApplicantInfo.getDetlInfo());
                }
            });

            // Set province list in txtProvince
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
                                        txtTownxx.setAdapter(adapter);
                                    } catch (Exception e){
                                        e.printStackTrace();
                                    }
                                }
                            });

                        }
                    });
                }
            });

            txtTownxx.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    mViewModel.getAllTownInfo().observe(getViewLifecycleOwner(), new Observer<List<ETownInfo>>() {
                        @Override
                        public void onChanged(List<ETownInfo> eTownInfos) {
                            for(int x = 0; x < eTownInfos.size(); x++){
                                if(txtTownxx.getText().toString().equalsIgnoreCase(eTownInfos.get(x).getTownName())){ //from id to town name
                                    mViewModel.setTownID(eTownInfos.get(x).getTownIDxx());
                                    break;
                                }
                            }
                        }
                    });
                }
            });

            mViewModel.getAllCountryCitizenNames().observe(getViewLifecycleOwner(), strings -> {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_dropdown_item, strings);
                txtCitizenx.setAdapter(adapter);
            });
            txtCitizenx.setOnItemClickListener((adapterView, view, i, l) -> mViewModel.getCountryInfoList().observe(getViewLifecycleOwner(), eCountryInfos -> {
                for(int x = 0; x < eCountryInfos.size(); x++){
                    if(txtCitizenx.getText().toString().equalsIgnoreCase(eCountryInfos.get(x).getNational())){
                        mViewModel.setCitizenship(eCountryInfos.get(x).getCntryCde());
                        break;
                    }
                }
            }));

            mViewModel.getMobileNoType().observe(getViewLifecycleOwner(), stringArrayAdapter -> {
                spnMobile1.setAdapter(stringArrayAdapter);
                spnMobile2.setAdapter(stringArrayAdapter);
                spnMobile3.setAdapter(stringArrayAdapter);
            });

            spnMobile1.setOnItemSelectedListener(new Fragment_SpouseInfo.OnItemSelectListener(spnMobile1));
            spnMobile2.setOnItemSelectedListener(new Fragment_SpouseInfo.OnItemSelectListener(spnMobile2));
            spnMobile3.setOnItemSelectedListener(new Fragment_SpouseInfo.OnItemSelectListener(spnMobile3));



            txtBDate.addTextChangedListener(new OnBirthSetListener(txtBDate));

            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Set the model for validation
                    infoModel.setLastName(txtLastName.getText().toString());
                    infoModel.setFrstName(txtFirstName.getText().toString());
                    infoModel.setMiddName(txtMiddName.getText().toString());
                    infoModel.setSuffix(txtSuffix.getText().toString());
                    infoModel.setNickName(txtNickName.getText().toString());
                    infoModel.setBirthDate(txtBDate.getText().toString());
                    infoModel.setProvNme(txtProvince.getText().toString());
                    infoModel.setTownNme(txtTownxx.getText().toString());
                    infoModel.setCitizenx(txtCitizenx.getText().toString());

                    if(!Objects.requireNonNull(txtPrimeCntc.getText()).toString().trim().isEmpty()) {
                        if(spnMobile1.getSelectedItemPosition() == 1) {
                            infoModel.setMobileNo(txtPrimeCntc.getText().toString(), String.valueOf(spnMobile1.getSelectedItemPosition()), Integer.parseInt(Objects.requireNonNull(txtMobileYr1.getText()).toString()));
                        } else {
                            infoModel.setMobileNo(txtPrimeCntc.getText().toString(), String.valueOf(spnMobile1.getSelectedItemPosition()), 0);
                        }
                    }
                    if(!Objects.requireNonNull(txtSecCntct.getText()).toString().trim().isEmpty()) {
                        if(spnMobile2.getSelectedItemPosition() == 1) {
                            infoModel.setMobileNo(txtSecCntct.getText().toString(), String.valueOf(spnMobile2.getSelectedItemPosition()), Integer.parseInt(Objects.requireNonNull(txtMobileYr2.getText()).toString()));
                        } else {
                            infoModel.setMobileNo(txtSecCntct.getText().toString(), String.valueOf(spnMobile2.getSelectedItemPosition()), 0);
                        }
                    }
                    if(!Objects.requireNonNull(txtThirCntct.getText()).toString().trim().isEmpty()) {
                        if(spnMobile3.getSelectedItemPosition() == 1) {
                            infoModel.setMobileNo(txtThirCntct.getText().toString(), String.valueOf(spnMobile3.getSelectedItemPosition()), Integer.parseInt(Objects.requireNonNull(txtMobileYr3.getText()).toString()));
                        } else {
                            infoModel.setMobileNo(txtThirCntct.getText().toString(), String.valueOf(spnMobile3.getSelectedItemPosition()), 0);
                        }
                    }

                    infoModel.setPhoneNo(txtTelNox.getText().toString());
                    infoModel.setEmailAdd(txtEmailAdd.getText().toString());
                    infoModel.setFBacct(txtFbAcct.getText().toString());
                    infoModel.setVbrAcct(txtViberAcct.getText().toString());

                    // Trigger save() with SpouseInfoModel instance with data set.
                    mViewModel.Save(infoModel, Fragment_SpouseInfo.this);
                }
            });

        }

        @SuppressLint("RestrictedApi")
        @Override
        public void onSaveSuccessResult(String args) {
            Activity_CreditApplication.getInstance().moveToPageNumber(3);
        }

        @Override
        public void onFailedResult(String message) {
            GToast.CreateMessage(getActivity(), message, GToast.ERROR).show();
        }

        private class OnItemSelectListener implements AdapterView.OnItemSelectedListener {
            Spinner poView;

            public OnItemSelectListener(Spinner view) {
                this.poView = view;
            }

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spnMobile1.equals(poView)) {
                    if(i == 1) {
                        tilMobileYr1.setVisibility(View.VISIBLE);
                    } else {
                        tilMobileYr1.setVisibility(View.GONE);
                    }
                }
                if (spnMobile2.equals(poView)) {
                    if(i == 1) {
                        tilMobileYr2.setVisibility(View.VISIBLE);
                    } else {
                        tilMobileYr2.setVisibility(View.GONE);
                    }
                }
                if (spnMobile3.equals(poView)) {
                    if(i == 1) {
                        tilMobileYr3.setVisibility(View.VISIBLE);
                    } else {
                        tilMobileYr3.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        }
    }