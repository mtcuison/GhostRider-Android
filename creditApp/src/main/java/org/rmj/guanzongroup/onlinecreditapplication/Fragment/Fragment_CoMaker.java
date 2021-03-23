package org.rmj.guanzongroup.onlinecreditapplication.Fragment;

import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.rmj.g3appdriver.GRider.Etc.GToast;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.gocas.base.GOCASApplication;
import org.rmj.guanzongroup.onlinecreditapplication.Activity.Activity_ApplicationHistory;
import org.rmj.guanzongroup.onlinecreditapplication.Activity.Activity_CreditApplication;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.OnDateSetListener;
import org.rmj.guanzongroup.onlinecreditapplication.Model.CoMakerModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;
import org.rmj.guanzongroup.onlinecreditapplication.R;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMCoMaker;

import java.util.Objects;

public class Fragment_CoMaker extends Fragment implements ViewModelCallBack {

    private VMCoMaker mViewModel;
    private static final String TAG = Fragment_CoMaker.class.getSimpleName();

    private String TransNox;
    private GOCASApplication poGoCas;

    private String ProvID = "";
    private String TownID = "";

    private String spnIncomePosition = "-1";
    private String spnCoRelationPosition = "-1";
    private String spnPrmryCntctPosition = "-1";
    private String spnScndCntctPosition = "-1";
    private String spnTrtCntctPosition = "-1";

    private TextInputEditText tieLastname;
    private TextInputEditText tieFrstname;
    private TextInputEditText tieMiddname;
    private AutoCompleteTextView tieSuffixxx;
    private TextInputEditText tieNickname;
    private TextInputEditText tieBrthDate;
    private AutoCompleteTextView tieBrthProv;
    private AutoCompleteTextView tieBrthTown;
    private TextInputEditText tiePrmCntct;
    private TextInputEditText tieScnCntct;
    private TextInputEditText tieTrtCntct;
    private TextInputLayout tilPrmCntctPlan;
    private TextInputLayout tilScnCntctPlan;
    private TextInputLayout tilTrtCntctPlan;
    private TextInputEditText tiePrmCntctPlan;
    private TextInputEditText tieScnCntctPlan;
    private TextInputEditText tieTrtCntctPlan;
    private TextInputEditText tieFbAcctxx;
    //    private Spinner spnBrwrRltn;
    private LinearLayout linearPrmContact;
    private LinearLayout linearScnContact;
    private LinearLayout linearTrtContact;
    private AutoCompleteTextView spnPrmCntct;
    private AutoCompleteTextView spnScnCntct;
    private AutoCompleteTextView spnTrtCntct;
    private TextInputLayout tilPrimaryCntctStats;
    private TextInputLayout tilScnCntctStats;
    private TextInputLayout tilTrtCntctStats;
    private AutoCompleteTextView spnIncmSrce;
    private AutoCompleteTextView spnBrwrRltn;
    private Button btnPrvs;
    private Button btnNext;
    private CoMakerModel infoModel;

    public static Fragment_CoMaker newInstance() {
        return new Fragment_CoMaker();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comaker, container, false);
        TransNox = Activity_CreditApplication.getInstance().getTransNox();
        infoModel = new CoMakerModel();
        setupWidgets(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(VMCoMaker.class);
        // TODO: Use the ViewModel
        mViewModel.setTransNox(Activity_CreditApplication.getInstance().getTransNox());
        mViewModel.getCreditApplicationInfo().observe(getViewLifecycleOwner(), eCreditApplicantInfo -> mViewModel.setCreditApplicantInfo(eCreditApplicantInfo));
        mViewModel.getSpnCMakerRelation().observe(getViewLifecycleOwner(), stringArrayAdapter -> spnBrwrRltn.setAdapter(stringArrayAdapter));
        mViewModel.getSpnCMakerIncomeSource().observe(getViewLifecycleOwner(), stringArrayAdapter -> spnIncmSrce.setAdapter(stringArrayAdapter));
        mViewModel.getCmrPrimaryCntctPlan().observe(getViewLifecycleOwner(), integer -> tilPrmCntctPlan.setVisibility(integer));
        mViewModel.getCmrSecondaryCntctPlan().observe(getViewLifecycleOwner(), integer -> tilScnCntctPlan.setVisibility(integer));
        mViewModel.getCmrTertiaryCntctPlan().observe(getViewLifecycleOwner(), integer -> tilTrtCntctPlan.setVisibility(integer));
        mViewModel.getTertiaryContact().observe(getViewLifecycleOwner(), s -> {
            spnTrtCntct.setSelection(Integer.parseInt(s));
            spnTrtCntctPosition = s;
            Log.e("Mobile 1", s);
        });

        mViewModel.getSecondaryContact().observe(getViewLifecycleOwner(), s -> {
            spnScnCntct.setSelection(Integer.parseInt(s));
            spnScndCntctPosition = s;
            Log.e("Mobile 2", s);
        });
        mViewModel.getPrimaryContact().observe(getViewLifecycleOwner(), s -> {
            spnPrmCntct.setSelection(Integer.parseInt(s));
            spnPrmryCntctPosition = s;
            Log.e("Mobile 3 ", s);
        });
        mViewModel.getCMakeIncomeSource().observe(getViewLifecycleOwner(), s -> {
            spnIncmSrce.setSelection(Integer.parseInt(s));
            spnIncomePosition = s;
            Log.e("Mobile 1", s);
        });
        mViewModel.getCMakerRelation().observe(getViewLifecycleOwner(), s -> {
            spnBrwrRltn.setSelection(Integer.parseInt(s));
            spnCoRelationPosition = s;
            Log.e("Mobile 1", s);
        });
        mViewModel.getMobileNoType().observe(getViewLifecycleOwner(),(stringArrayAdapter) -> {
            spnPrmCntct.setAdapter(stringArrayAdapter);
            spnScnCntct.setAdapter(stringArrayAdapter);
            spnTrtCntct.setAdapter(stringArrayAdapter);
        });
        mViewModel.getProvinceNameList().observe(getViewLifecycleOwner(), strings -> {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_dropdown_item, strings);
            tieBrthProv.setAdapter(adapter);
        });

        tieBrthProv.setOnItemClickListener((adapterView, view, i, l) -> mViewModel.getProvinceInfoList().observe(getViewLifecycleOwner(), provinceInfos -> {
            for(int x = 0; x < provinceInfos.size(); x++){
                if(tieBrthProv.getText().toString().equalsIgnoreCase(provinceInfos.get(x).getProvName())){
                    mViewModel.setProvID(provinceInfos.get(x).getProvIDxx());
                    break;
                }
            }

            mViewModel.getAllTownNames().observe(getViewLifecycleOwner(), strings -> {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_dropdown_item, strings);
                tieBrthTown.setAdapter(adapter);
            });
        }));

        tieBrthTown.setOnItemClickListener((adapterView, view, i, l) -> mViewModel.getTownInfoList().observe(getViewLifecycleOwner(), townInfoList -> {
            for(int x = 0; x < townInfoList.size(); x++){
                if(tieBrthTown.getText().toString().equalsIgnoreCase(townInfoList.get(x).getTownName())){
                    mViewModel.setTownID(townInfoList.get(x).getTownIDxx());
                    break;
                }
            }
        }));
        spnPrmCntct.setOnItemClickListener(new Fragment_CoMaker.OnItemClickListener(spnPrmCntct));
        spnScnCntct.setOnItemClickListener(new Fragment_CoMaker.OnItemClickListener(spnScnCntct));
        spnTrtCntct.setOnItemClickListener(new Fragment_CoMaker.OnItemClickListener(spnTrtCntct));
        spnIncmSrce.setOnItemClickListener(new Fragment_CoMaker.OnItemClickListener(spnIncmSrce));
        spnBrwrRltn.setOnItemClickListener(new Fragment_CoMaker.OnItemClickListener(spnBrwrRltn));

    }
    private void setupWidgets(View v){
        tieLastname = v.findViewById(R.id.tie_cap_cmrLastname);
        tieFrstname = v.findViewById(R.id.tie_cap_cmrFirstname);
        tieMiddname = v.findViewById(R.id.tie_cap_cmrMiddname);
        tieSuffixxx = v.findViewById(R.id.tie_cap_cmrSuffix);
        tieNickname = v.findViewById(R.id.tie_cap_cmrNickname);
        tieBrthDate = v.findViewById(R.id.tie_cap_cmrBirthdate);
        tieBrthProv = v.findViewById(R.id.tie_cap_cmrBirthProv);
        tieBrthTown = v.findViewById(R.id.tie_cap_cmrBirthTown);
        tiePrmCntct = v.findViewById(R.id.tie_cap_cmrPrimaryContactNo);
        tieScnCntct = v.findViewById(R.id.tie_cap_cmrSecondaryContactNo);
        tieTrtCntct = v.findViewById(R.id.tie_cap_cmrTertiaryContactNo);
        tilPrmCntctPlan = v.findViewById(R.id.til_cap_cmrPrimaryCntctPlan);
        tilScnCntctPlan = v.findViewById(R.id.til_cap_cmrSecondaryCntctPlan);
        tilTrtCntctPlan = v.findViewById(R.id.til_cap_cmrTertiaryCntctPlan);
        tiePrmCntctPlan = v.findViewById(R.id.tie_cap_cmrPrimaryCntctPlan);
        tieScnCntctPlan = v.findViewById(R.id.tie_cap_cmrSecondaryCntctPlan);
        tieTrtCntctPlan = v.findViewById(R.id.tie_cap_cmrTertiaryCntctPlan);
        tieFbAcctxx = v.findViewById(R.id.tie_cap_cmrFacebookacc);
        spnIncmSrce = v.findViewById(R.id.spinner_cap_cmrIncomeSrc);
        spnBrwrRltn = v.findViewById(R.id.spinner_cap_cmrBarrowerRelation);
        linearPrmContact = v.findViewById(R.id.linear_cmrPrimaryContact);
        spnPrmCntct = v.findViewById(R.id.spinner_cap_cmrPrimaryCntctStats);
        spnScnCntct = v.findViewById(R.id.spinner_cap_cmrSecondaryCntctStats);
        spnTrtCntct = v.findViewById(R.id.spinner_cap_cmrTertiaryCntctStats);

        tilPrimaryCntctStats = v.findViewById(R.id.til_cap_cmrPrimaryCntctStats);
        tilScnCntctStats = v.findViewById(R.id.til_cap_cmrSecondaryCntctStats);
        tilTrtCntctStats = v.findViewById(R.id.til_cap_cmrTertiaryCntctStats);

        tieBrthDate.addTextChangedListener(new OnDateSetListener(tieBrthDate));
        btnNext = v.findViewById(R.id.btn_creditAppNext);
        btnPrvs = v.findViewById(R.id.btn_creditAppPrvs);
        btnNext.setOnClickListener(v1 -> {
            infoModel = new CoMakerModel(
                    Objects.requireNonNull(tieLastname.getText().toString()),
                    Objects.requireNonNull(tieFrstname.getText().toString()),
                    Objects.requireNonNull(tieMiddname.getText().toString()),
                    Objects.requireNonNull(tieSuffixxx.getText().toString()),
                    Objects.requireNonNull(tieNickname.getText().toString()),
                    Objects.requireNonNull(tieBrthDate.getText().toString()),
                    Objects.requireNonNull(tieBrthTown.getText().toString()),
                    Objects.requireNonNull(tieFbAcctxx.getText().toString()),
                    Objects.requireNonNull(spnIncomePosition),
                    Objects.requireNonNull(spnCoRelationPosition)
            );
            if(!Objects.requireNonNull(tiePrmCntct.getText()).toString().trim().isEmpty()) {
                if(Integer.parseInt(spnPrmryCntctPosition) == 1) {
                    infoModel.setCoMobileNo(tiePrmCntct.getText().toString(), spnPrmryCntctPosition, Integer.parseInt(Objects.requireNonNull(tiePrmCntctPlan.getText()).toString()));
                } else {
                    infoModel.setCoMobileNo(tiePrmCntct.getText().toString(), spnPrmryCntctPosition, 0);
                }
            }
            if(!Objects.requireNonNull(tieScnCntct.getText()).toString().trim().isEmpty()) {
                if(Integer.parseInt(spnScndCntctPosition) == 1) {
                    infoModel.setCoMobileNo(tieScnCntct.getText().toString(), spnScndCntctPosition, Integer.parseInt(Objects.requireNonNull(tieScnCntctPlan.getText()).toString()));
                } else {
                    infoModel.setCoMobileNo(tieScnCntct.getText().toString(), spnScndCntctPosition, 0);
                }
            }
            if(!Objects.requireNonNull(tieTrtCntct.getText()).toString().trim().isEmpty()) {
                if(Integer.parseInt(spnTrtCntctPosition)  == 1) {
                    infoModel.setCoMobileNo(tieTrtCntct.getText().toString(), spnTrtCntctPosition, Integer.parseInt(Objects.requireNonNull(tieTrtCntctPlan.getText()).toString()));
                } else {
                    infoModel.setCoMobileNo(tieTrtCntct.getText().toString(), spnTrtCntctPosition, 0);
                }
            }
            mViewModel.SubmitComaker(infoModel, Fragment_CoMaker.this);
        });
    }

    @Override
    public void onSaveSuccessResult(String args) {
        Activity_CreditApplication.getInstance().moveToPageNumber(17);
    }

    @Override
    public void onFailedResult(String message) {
        if(!message.equalsIgnoreCase("no_comaker")) {
            GToast.CreateMessage(getActivity(), message, GToast.ERROR).show();
        } else {
            MessageBox loDialog = new MessageBox(getActivity());
            loDialog.initDialog();
            loDialog.setTitle("Co-Maker Info");
            loDialog.setMessage("Send loan application without co-maker info?");
            loDialog.setPositiveButton("Yes", new MessageBox.DialogButton() {
                @Override
                public void OnButtonClick(View view, AlertDialog dialog) {
                    //TODO: create or insert method for transferring data to main table.
                    mViewModel.SaveCreditOnlineApplication();
                    requireActivity().finish();
                }
            });
            loDialog.setNegativeButton("Cancel", (view, dialog) -> dialog.dismiss());
            loDialog.show();
        }
    }

    class OnItemClickListener implements AdapterView.OnItemClickListener {
        AutoCompleteTextView poView;

        public OnItemClickListener(AutoCompleteTextView view) {
            this.poView = view;
        }

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            if (spnPrmCntct.equals(poView)) {
                mViewModel.setPrimaryContact(String.valueOf(i));
            }
            if (spnScnCntct.equals(poView)) {
                mViewModel.setSecondaryContact(String.valueOf(i));
            }
            if (spnTrtCntct.equals(poView)) {
                mViewModel.setTertiaryContact(String.valueOf(i));
            }
            if (spnIncmSrce.equals(poView)) {
                mViewModel.setSpnCMakeIncomeSource(String.valueOf(i));
            }
            if (spnBrwrRltn.equals(poView)) {
                mViewModel.setSpnCMakerRelation(String.valueOf(i));
            }
        }
    }
}