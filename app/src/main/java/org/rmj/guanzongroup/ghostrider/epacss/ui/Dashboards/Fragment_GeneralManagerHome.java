package org.rmj.guanzongroup.ghostrider.epacss.ui.Dashboards;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.rmj.guanzongroup.ghostrider.epacss.R;
import org.rmj.guanzongroup.ghostrider.epacss.ViewModel.VMGeneralManager;

public class Fragment_GeneralManagerHome extends Fragment {

    private VMGeneralManager mViewModel;

    public static Fragment_GeneralManagerHome newInstance() {
        return new Fragment_GeneralManagerHome();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(VMGeneralManager.class);
        return inflater.inflate(R.layout.fragment_general_manager_home, container, false);
    }
}