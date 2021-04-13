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
//import org.rmj.guanzongroup.ghostrider.creditevaluator.ViewModel.VMCIBarangayRecord;

public class Fragment_CIBarangayRecord extends Fragment {

//    private VMCIBarangayRecord mViewModel;

    public static Fragment_CIBarangayRecord newInstance() {
        return new Fragment_CIBarangayRecord();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ci_barangay_record, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        mViewModel = new ViewModelProvider(this).get(VMCIBarangayRecord.class);
        // TODO: Use the ViewModel
    }

}