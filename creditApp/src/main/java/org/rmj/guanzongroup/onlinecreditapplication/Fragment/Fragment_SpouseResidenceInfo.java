    package org.rmj.guanzongroup.onlinecreditapplication.Fragment;

    import androidx.lifecycle.Observer;
    import androidx.lifecycle.ViewModelProviders;

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
    import android.widget.CheckBox;
    import android.widget.CompoundButton;

    import com.google.android.material.textfield.TextInputEditText;

    import org.rmj.g3appdriver.GRider.Database.Entities.EBarangayInfo;
    import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplicantInfo;
    import org.rmj.g3appdriver.GRider.Database.Entities.EProvinceInfo;
    import org.rmj.g3appdriver.GRider.Database.Entities.ETownInfo;
    import org.rmj.g3appdriver.GRider.Etc.GToast;
    import org.rmj.guanzongroup.onlinecreditapplication.Activity.Activity_CreditApplication;
    import org.rmj.guanzongroup.onlinecreditapplication.Model.SpouseResidenceInfoModel;
    import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;
    import org.rmj.guanzongroup.onlinecreditapplication.R;
    import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMSpouseResidenceInfo;

    import java.util.List;
    import java.util.Objects;

    public class Fragment_SpouseResidenceInfo extends Fragment implements ViewModelCallBack {

        private SpouseResidenceInfoModel infoModel;
        private VMSpouseResidenceInfo mViewModel;

        private CheckBox cbLivingWithSpouse;
        private TextInputEditText txtLandMark,
                txtHouseNox,
                txtAddress1,
                txtAddress2;
        private AutoCompleteTextView txtBarangay,
                txtTown,
                txtProvince;
        private Button btnNext;
        private Button btnPrvs;
        private String transnox;

        public static Fragment_SpouseResidenceInfo newInstance() {
            return new Fragment_SpouseResidenceInfo();
        }

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_spouse_residence_info, container, false);
            infoModel = new SpouseResidenceInfoModel();
            transnox = Activity_CreditApplication.getInstance().getTransNox();

            initWidgets(view);
            return view;
        }

        private void initWidgets(View v) {
            cbLivingWithSpouse = v.findViewById(R.id.cb_cap_livingWithSpouse);
            txtLandMark = v.findViewById(R.id.txt_landmark);
            txtHouseNox = v.findViewById(R.id.txt_houseNox);
            txtAddress1 = v.findViewById(R.id.txt_address);
            txtAddress2 = v.findViewById(R.id.txt_address2);
            txtProvince = v.findViewById(R.id.txt_province);
            txtTown = v.findViewById(R.id.txt_town);
            txtBarangay = v.findViewById(R.id.txt_barangay);

            btnNext = v.findViewById(R.id.btn_creditAppNext);
            btnPrvs = v.findViewById(R.id.btn_creditAppPrvs);
        }

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            mViewModel = ViewModelProviders.of(this).get(VMSpouseResidenceInfo.class);
            mViewModel.setTransNox(transnox);

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
                                        txtBarangay.setAdapter(adapter);
                                    } catch (Exception e){
                                        e.printStackTrace();
                                    }
                                }
                            });

                        }
                    });
                }
            });

            txtBarangay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    mViewModel.getPermanentBarangayInfoList().observe(getViewLifecycleOwner(), new Observer<List<EBarangayInfo>>() {
                        @Override
                        public void onChanged(List<EBarangayInfo> eBrgyInfos) {
                            for(int x = 0; x < eBrgyInfos.size(); x++){
                                if(txtBarangay.getText().toString().equalsIgnoreCase(eBrgyInfos.get(x).getBrgyName())){ //from id to town name
                                    mViewModel.setBrgyID(eBrgyInfos.get(x).getBrgyIDxx());
                                    break;
                                }
                            }
                        }
                    });
                }
            });

            cbLivingWithSpouse.setOnCheckedChangeListener(new OnCheckboxSetListener());
            btnNext.setOnClickListener(view -> Save());;
            btnPrvs.setOnClickListener(view -> Activity_CreditApplication.getInstance().moveToPageNumber(2));
        }

        private void Save() {
            infoModel.setLandmark(Objects.requireNonNull(txtLandMark.getText().toString()));
            infoModel.setHouseNox(Objects.requireNonNull(txtHouseNox.getText().toString()));
            infoModel.setAddress1(Objects.requireNonNull(txtAddress1.getText().toString()));
            infoModel.setAddress2(Objects.requireNonNull(txtAddress2.getText().toString()));
            infoModel.setProvince(txtProvince.getText().toString());
            infoModel.setTown(txtTown.getText().toString());
            infoModel.setBarangay(txtBarangay.getText().toString());

            mViewModel.Save(infoModel, Fragment_SpouseResidenceInfo.this);
        }

        @Override
        public void onSaveSuccessResult(String args) {
            Activity_CreditApplication.getInstance().moveToPageNumber(4);
        }

        @Override
        public void onFailedResult(String message) {
            GToast.CreateMessage(getActivity(), message, GToast.ERROR).show();
        }

        class OnCheckboxSetListener implements CheckBox.OnCheckedChangeListener{

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isChecked()){
                    mViewModel.setAddress();
                    txtLandMark.setText(mViewModel.getLandmark());
                    txtHouseNox.setText(mViewModel.getHouseNox());
                    txtAddress1.setText(mViewModel.getAddress1());
                    txtAddress2.setText(mViewModel.getAddress2());

                    mViewModel.getTownNameAndProvID(mViewModel.getTownName()).observe(getViewLifecycleOwner(), new Observer<ETownInfo>() {
                        @Override
                        public void onChanged(ETownInfo eTownInfo) {
                            txtTown.setText(eTownInfo.getTownName());

                            mViewModel.setProvinceID(eTownInfo.getProvIDxx());

                            mViewModel.getProvinceNameFromProvID(eTownInfo.getProvIDxx()).observe(getViewLifecycleOwner(), new Observer<String>() {
                                @Override
                                public void onChanged(String s) {
                                    txtProvince.setText(s);
                                }
                            });

                        }
                    });

                    mViewModel.getBarangayInfoFromID(mViewModel.getBrgyName()).observe(getViewLifecycleOwner(), new Observer<String>() {
                        @Override
                        public void onChanged(String s) {
                            txtBarangay.setText(s);
                        }
                    });

                }
                else {
                    txtLandMark.setText("");
                    txtHouseNox.setText("");
                    txtAddress1.setText("");
                    txtAddress2.setText("");
                    txtProvince.setText("");
                    txtTown.setText("");
                    txtBarangay.setText("");

                    mViewModel.setProvinceID("");
                    mViewModel.setTownID("");
                    mViewModel.setBrgyID("");
                }
            }
        }


    }