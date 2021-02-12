package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Activities.Activity_Transaction;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Etc.OnBirthSetListener;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Model.LoanUnitModel;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel.VMLoanUnit;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel.ViewModelCallback;

public class Fragment_LoanUnit extends Fragment implements ViewModelCallback {

    private VMLoanUnit mViewModel;
    private String spnCivilStatsPosition = "";
    private String genderPosition = "";
    private TextView lblBranch, lblAddress, lblAccNo, lblClientNm, lblTransNo;
    private TextInputEditText tieLName, tieFName, tieMName, tieSuffix;
    private TextInputEditText tieHouseNo, tieStreet, tieTown, tieBrgy;
    private RadioGroup rgGender;
    private TextInputEditText tieBDate, tieBPlace, tiePhone, tieMobileNo, tieEmailAdd;
    private MaterialButton btnLUSubmit;
    private AutoCompleteTextView spnCivilStats;

    private LoanUnitModel infoModel;
    private MessageBox poMessage;
    public static Fragment_LoanUnit newInstance() {
        return new Fragment_LoanUnit();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loan_unit, container, false);
        poMessage = new MessageBox(getActivity());
        infoModel = new LoanUnitModel();
        initWidgets(view);
        return view;
    }

    private void initWidgets(View v) {

        lblBranch = v.findViewById(R.id.lbl_headerBranch);
        lblAddress = v.findViewById(R.id.lbl_headerAddress);
        lblAccNo = v.findViewById(R.id.tvAccountNo);
        lblClientNm = v.findViewById(R.id.tvClientname);
        lblTransNo = v.findViewById(R.id.lbl_dcpTransNo);
//        Full Name
        tieLName = v.findViewById(R.id.tie_lun_lName);
        tieFName = v.findViewById(R.id.tie_lun_fName);
        tieMName = v.findViewById(R.id.tie_lun_middName);
        tieSuffix = v.findViewById(R.id.tie_lun_suffix);
//        Address
        tieHouseNo = v.findViewById(R.id.tie_lun_houseNo);
        tieStreet = v.findViewById(R.id.tie_lun_street);
        tieTown = v.findViewById(R.id.tie_lun_town);
        tieBrgy = v.findViewById(R.id.tie_lun_brgy);
//        Gender
        rgGender = v.findViewById(R.id.rg_dcp_gender);
//        Other Info
        spnCivilStats = v.findViewById(R.id.spn_lun_cstatus);

        tieBDate = v.findViewById(R.id.tie_lun_bdate);
        tieBPlace = v.findViewById(R.id.tie_lun_bplace);
        tiePhone = v.findViewById(R.id.tie_lun_phone);
        tieMobileNo = v.findViewById(R.id.tie_lun_mobileNp);
        tieEmailAdd = v.findViewById(R.id.tie_lun_email);

        btnLUSubmit = v.findViewById(R.id.btn_dcpSubmit);

        btnLUSubmit.setOnClickListener(view -> submitLUn());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String Remarksx = Activity_Transaction.getInstance().getRemarksCode();
        String TransNox = Activity_Transaction.getInstance().getTransNox();
        String EntryNox = Activity_Transaction.getInstance().getEntryNox();
        mViewModel = new ViewModelProvider(this).get(VMLoanUnit.class);
        mViewModel.setParameter(TransNox, EntryNox);
        mViewModel.getSpnCivilStats().observe(getViewLifecycleOwner(), stringArrayAdapter -> spnCivilStats.setAdapter(stringArrayAdapter));

        mViewModel.getCollectionDetail().observe(getViewLifecycleOwner(), collectionDetail -> {
            try {
                lblAccNo.setText(collectionDetail.getAcctNmbr());
                lblClientNm.setText(collectionDetail.getFullName());
                lblTransNo.setText(collectionDetail.getTransNox());
                mViewModel.setCurrentCollectionDetail(collectionDetail);
            } catch (Exception


                    e){
                e.printStackTrace();
            }
        });
        mViewModel.getUserBranchEmployee().observe(getViewLifecycleOwner(), eBranchInfo -> {
            lblBranch.setText(eBranchInfo.getBranchNm());
            lblAddress.setText(eBranchInfo.getAddressx());
        });
        // TODO: Use the ViewModel

        spnCivilStats.setOnItemClickListener(new Fragment_LoanUnit.OnItemClickListener(spnCivilStats));

        tieBDate.addTextChangedListener(new OnBirthSetListener(tieBDate));

        rgGender.setOnCheckedChangeListener((radioGroup, i) -> {
            if(i == R.id.rb_male){
                mViewModel.setGender("0");
            }
            if(i == R.id.rb_female){
                mViewModel.setGender("1");
            }
            if(i == R.id.rb_lgbt){
                mViewModel.setGender("2");
            }
        });
    }

    @Override
    public void OnStartSaving() {

    }

    @Override
    public void OnSuccessResult(String[] args) {
        poMessage.setTitle("Transaction Success");
        poMessage.setMessage(args[0]);
        poMessage.setPositiveButton("Okay", (view, dialog) -> {
            dialog.dismiss();
            getActivity().finish();
        });
        poMessage.show();
    }

    @Override
    public void OnFailedResult(String message) {
        poMessage.setTitle("Transaction Failed");
        poMessage.setMessage(message);
        poMessage.setPositiveButton("Okay", (view, dialog) -> dialog.dismiss());
        poMessage.show();
    }
    class OnItemClickListener implements AdapterView.OnItemClickListener {
        AutoCompleteTextView poView;
        public OnItemClickListener(AutoCompleteTextView view) {
            this.poView = view;
        }

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            if (spnCivilStats.equals(poView)) {
                spnCivilStatsPosition = String.valueOf(i);
                mViewModel.setSpnCivilStats(spnCivilStatsPosition);
            }
        }
    }

    private void submitLUn(){
        infoModel.setLuLastName(tieLName.getText().toString());
        infoModel.setLuFirstName(tieFName.getText().toString());
        infoModel.setLuMiddleName(tieMName.getText().toString());
        infoModel.setLuSuffix(tieSuffix.getText().toString());
        infoModel.setLuHouseNo(tieHouseNo.getText().toString());
        infoModel.setLuStreet(tieStreet.getText().toString());
        infoModel.setLuTown(tieTown.getText().toString());
        infoModel.setLuBrgy(tieBrgy.getText().toString());
        infoModel.setLuBDate(tieBDate.getText().toString());
        infoModel.setLuBPlace(tieBPlace.getText().toString());
        infoModel.setLuPhone(tiePhone.getText().toString());
        infoModel.setLuMobile(tieMobileNo.getText().toString());
        infoModel.setLuEmail(tieEmailAdd.getText().toString());
        mViewModel.saveLuInfo(infoModel, Fragment_LoanUnit.this);
    }
}