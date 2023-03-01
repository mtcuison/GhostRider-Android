package org.rmj.guanzongroup.ghostrider.notifications.Fragment;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.rmj.guanzongroup.ghostrider.notifications.R;
import org.rmj.guanzongroup.ghostrider.notifications.ViewModel.VMPaySlipList;

public class Fragment_PayslipList extends Fragment {

    private VMPaySlipList mViewModel;

    public static Fragment_PayslipList newInstance() {
        return new Fragment_PayslipList();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(VMPaySlipList.class);
        return inflater.inflate(R.layout.fragment_payslip_list, container, false);
    }
}