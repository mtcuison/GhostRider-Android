package org.rmj.guanzongroup.ghostrider.creditevaluator.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.rmj.g3appdriver.GRider.Etc.GToast;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Activity.Activity_CIApplication;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Etc.ViewModelCallBack;
import org.rmj.guanzongroup.ghostrider.creditevaluator.R;
import org.rmj.guanzongroup.ghostrider.creditevaluator.ViewModel.VMCIResidenceInfo;
//import org.rmj.guanzongroup.ghostrider.creditevaluator.ViewModel.VMCIResidenceInfo;

public class Fragment_CIResidenceInfo extends Fragment implements ViewModelCallBack {

    private VMCIResidenceInfo mViewModel;
    private TextInputEditText tiwLandmark;
    private RadioGroup rgHouseOwnership,rgHouseType,rgHouseHolds;
    private MaterialButton btnNext;

    public static Fragment_CIResidenceInfo newInstance() {
        return new Fragment_CIResidenceInfo();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_ci_residence_info, container, false);
        initWidgets(root);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VMCIResidenceInfo.class);
    }
    public void  initWidgets(View view){
        tiwLandmark = view.findViewById(R.id.tie_landmark);
        rgHouseHolds = view.findViewById(R.id.rg_ci_houseHold);
        rgHouseOwnership = view.findViewById(R.id.rg_ci_ownership);
        rgHouseType = view.findViewById(R.id.rg_ci_houseType);
        btnNext = view.findViewById(R.id.btn_ciAppNext);

    }


    @SuppressLint("RestrictedApi")
    @Override
    public void onSaveSuccessResult(String args) {
        Activity_CIApplication.getInstance().moveToPageNumber(1);
    }

    @Override
    public void onFailedResult(String message) {
        GToast.CreateMessage(getActivity(), message, GToast.ERROR).show();
    }
}