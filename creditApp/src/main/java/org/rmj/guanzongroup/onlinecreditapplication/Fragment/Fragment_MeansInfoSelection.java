package org.rmj.guanzongroup.onlinecreditapplication.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import org.rmj.g3appdriver.GRider.Etc.GToast;
import org.rmj.guanzongroup.onlinecreditapplication.Activity.Activity_CreditApplication;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;
import org.rmj.guanzongroup.onlinecreditapplication.R;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMMeansInfoSelection;

public class Fragment_MeansInfoSelection extends Fragment implements ViewModelCallBack {

    private VMMeansInfoSelection.MeansInfo infoModel;
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
        infoModel = new VMMeansInfoSelection.MeansInfo();
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
        btnNext.setOnClickListener(view -> mViewModel.SaveMeansInfo(infoModel, Fragment_MeansInfoSelection.this));
    }

    @Override
    public void onSaveSuccessResult(String args) {
        Activity_CreditApplication.getInstance().moveToPageNumber(Integer.parseInt(args));
    }

    @Override
    public void onFailedResult(String message) {
        GToast.CreateMessage(getActivity(), message,GToast.ERROR).show();
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
                    infoModel.setEmployed("1");
                    mViewModel.addMeansInfo("employed");
                } else {
                    infoModel.setEmployed("0");
                    mViewModel.removeMeansInfo("employed");
                }
            }
            if(cb.getId() == R.id.cb_sEmployed){
                if(b){
                    infoModel.setSelfEmployed("1");
                    mViewModel.addMeansInfo("sEmplyed");
                }else {
                    infoModel.setSelfEmployed("0");
                    mViewModel.removeMeansInfo("sEmplyed");
                }
            }
            if(cb.getId() == R.id.cb_finance){
                if(b){
                    infoModel.setFinance("1");
                    mViewModel.addMeansInfo("Financex");
                }else {
                    infoModel.setFinance("0");
                    mViewModel.removeMeansInfo("Financex");
                }
            }
            if(cb.getId() == R.id.cb_pension){
                if(b){
                    infoModel.setPension("1");
                    mViewModel.addMeansInfo("Pensionx");
                }else {
                    infoModel.setPension("0");
                    mViewModel.removeMeansInfo("Pensionx");
                }
            }
        }
    }
}