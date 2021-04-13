package org.rmj.guanzongroup.ghostrider.creditevaluator.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import org.rmj.guanzongroup.ghostrider.creditevaluator.R;

public class Fragment_CIDisbursementInfo extends Fragment {

//    private VMCIDisbursementInfo mViewModel;

    public static Fragment_CIDisbursementInfo newInstance() {
        return new Fragment_CIDisbursementInfo();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ci_disbursement_info, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        mViewModel = new ViewModelProvider(this).get(VMCIDisbursementInfo.class);
        // TODO: Use the ViewModel
    }

}