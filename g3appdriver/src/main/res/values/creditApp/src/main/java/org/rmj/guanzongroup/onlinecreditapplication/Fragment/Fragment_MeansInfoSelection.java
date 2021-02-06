package org.rmj.guanzongroup.onlinecreditapplication.Fragment;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import org.rmj.guanzongroup.onlinecreditapplication.Activity.Activity_CreditApplication;
import org.rmj.guanzongroup.onlinecreditapplication.R;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMMeansInfoSelection;

public class Fragment_MeansInfoSelection extends Fragment {

    private VMMeansInfoSelection mViewModel;

    private CheckBox cbEmployed;
    private CheckBox cbSEmployd;
    private CheckBox cbFinancex;
    private CheckBox cbPensionx;

    private Button btnNext;
    private Button btnPrvs;

    private String TransNox;

    public static Fragment_MeansInfoSelection newInstance() {
        return new Fragment_MeansInfoSelection();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_means_info_selection, container, false);
        TransNox = Activity_CreditApplication.getInstance().getTransNox();
        initWidgets(view);

        return view;
    }

    private void initWidgets(View v){
        cbEmployed = v.findViewById(R.id.cb_employed);
        cbSEmployd = v.findViewById(R.id.cb_sEmployed);
        cbFinancex = v.findViewById(R.id.cb_finance);
        cbPensionx = v.findViewById(R.id.cb_pension);

        btnNext = v.findViewById(R.id.btn_creditAppNext);
        btnPrvs = v.findViewById(R.id.btn_creditAppPrvs);

        cbEmployed.setOnCheckedChangeListener(new OnMeansInfoCheckListener(cbEmployed));
        cbSEmployd.setOnCheckedChangeListener(new OnMeansInfoCheckListener(cbSEmployd));
        cbFinancex.setOnCheckedChangeListener(new OnMeansInfoCheckListener(cbFinancex));
        cbPensionx.setOnCheckedChangeListener(new OnMeansInfoCheckListener(cbPensionx));

        btnPrvs.setOnClickListener(view -> Activity_CreditApplication.getInstance().moveToPageNumber(1));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VMMeansInfoSelection.class);
        mViewModel.setTransNox(TransNox);
        mViewModel.getCreditApplicantInfo(TransNox).observe(getViewLifecycleOwner(), eCreditApplicantInfo -> mViewModel.setGOCasDetailInfo(eCreditApplicantInfo.getDetlInfo()));
        btnNext.setOnClickListener(view -> mViewModel.getMeansInfoPage().observe(getViewLifecycleOwner(), integer -> Activity_CreditApplication.getInstance().moveToPageNumber(integer)));
    }

    private class OnMeansInfoCheckListener implements CompoundButton.OnCheckedChangeListener{
        CheckBox cb;

        public OnMeansInfoCheckListener(CheckBox cb) {
            this.cb = cb;
        }

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if(cb.getId() == R.id.cb_employed){
                if(b){
                    mViewModel.addMeansInfo("employed");
                } else {
                    mViewModel.removeMeansInfo("employed");
                }
            }
            if(cb.getId() == R.id.cb_sEmployed){
                if(b){
                    mViewModel.addMeansInfo("self-employed");
                }else {
                    mViewModel.removeMeansInfo("self-employed");
                }
            }
            if(cb.getId() == R.id.cb_finance){
                if(b){
                    mViewModel.addMeansInfo("finance");
                }else {
                    mViewModel.removeMeansInfo("finance");
                }
            }
            if(cb.getId() == R.id.cb_pension){
                if(b){
                    mViewModel.addMeansInfo("pension");
                }else {
                    mViewModel.removeMeansInfo("pension");
                }
            }
        }
    }
}