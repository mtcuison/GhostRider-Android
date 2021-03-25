package org.rmj.guanzongroup.onlinecreditapplication.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.textfield.TextInputEditText;

import org.rmj.g3appdriver.GRider.Etc.GToast;
import org.rmj.guanzongroup.onlinecreditapplication.Activity.Activity_CreditApplication;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.TextFormatter;
import org.rmj.guanzongroup.onlinecreditapplication.Model.PensionInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;
import org.rmj.guanzongroup.onlinecreditapplication.R;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMPensionInfo;

public class Fragment_PensionInfo extends Fragment implements ViewModelCallBack {

    private AutoCompleteTextView spnSector;
    private String sectorPosition = "-1";
    private TextInputEditText txtRangxx,
                              txtYearxx,
                              txtOthInc,
                              txtRngInc;
    private Button btnPrvs, btnNext;
    private PensionInfoModel infoModel;
    private VMPensionInfo mViewModel;

    public static Fragment_PensionInfo newInstance() {
        return new Fragment_PensionInfo();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pension_info, container, false);
        infoModel = new PensionInfoModel();
        spnSector = view.findViewById(R.id.spn_psnSector);
        txtRangxx = view.findViewById(R.id.txt_psnIncRange);
        txtYearxx = view.findViewById(R.id.txt_psnRtrmntYear);
        txtOthInc = view.findViewById(R.id.txt_natureOfIncome);
        txtRngInc = view.findViewById(R.id.txt_incRange);
        btnPrvs = view.findViewById(R.id.btn_creditAppPrvs);
        btnNext = view.findViewById(R.id.btn_creditAppNext);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String TransNox = Activity_CreditApplication.getInstance().getTransNox();
        mViewModel = ViewModelProviders.of(this).get(VMPensionInfo.class);
        mViewModel.setTransNox(TransNox);
        mViewModel.getApplicationInfo().observe(getViewLifecycleOwner(), eCreditApplicantInfo -> {
            mViewModel.setDetailInfo(eCreditApplicantInfo);
        });

        mViewModel.getPensionSector().observe(getViewLifecycleOwner(), stringArrayAdapter -> spnSector.setAdapter(stringArrayAdapter));

        btnPrvs.setOnClickListener(view -> {
            try {
                Activity_CreditApplication.getInstance().moveToPageNumber(mViewModel.getPreviousPage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        mViewModel.getSPensionSector().observe(getViewLifecycleOwner(), s -> {
            sectorPosition = s;
            spnSector.setSelection(Integer.parseInt(s));
        });
        spnSector.setOnItemClickListener(new OnItemClickListener(spnSector));

        txtRangxx.addTextChangedListener(new TextFormatter.OnTextChangedCurrencyFormatter(txtRangxx));
        txtRngInc.addTextChangedListener(new TextFormatter.OnTextChangedCurrencyFormatter(txtRngInc));
        btnNext.setOnClickListener(view -> {
            infoModel.setPensionSector(sectorPosition);
            infoModel.setPensionIncomeRange(txtRangxx.getText().toString().replace(",", ""));
            infoModel.setRetirementYear(txtYearxx.getText().toString());
            infoModel.setNatureOfIncome(txtOthInc.getText().toString());
            infoModel.setRangeOfIncom(txtRngInc.getText().toString().replace(",", ""));
            mViewModel.SavePensionInfo(infoModel, Fragment_PensionInfo.this);
        });
    }

    @Override
    public void onSaveSuccessResult(String args) {
        Activity_CreditApplication.getInstance().moveToPageNumber(Integer.parseInt(args));
    }

    @Override
    public void onFailedResult(String message) {
        GToast.CreateMessage(getActivity(), message, GToast.ERROR).show();
    }
    class OnItemClickListener implements AdapterView.OnItemClickListener {
        AutoCompleteTextView poView;

        public OnItemClickListener(AutoCompleteTextView view) {
            this.poView = view;
        }
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            if (spnSector.equals(poView)) {
                sectorPosition = String.valueOf(i);
                mViewModel.setPensionSector(sectorPosition);
            }
        }
    }
}