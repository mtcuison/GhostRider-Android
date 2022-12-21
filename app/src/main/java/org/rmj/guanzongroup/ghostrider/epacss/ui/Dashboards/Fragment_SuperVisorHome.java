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
import org.rmj.guanzongroup.ghostrider.epacss.ViewModel.VMSuperVisor;

public class Fragment_SuperVisorHome extends Fragment {

    private VMSuperVisor mViewModel;

    public static Fragment_SuperVisorHome newInstance() {
        return new Fragment_SuperVisorHome();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(VMSuperVisor.class);
        return inflater.inflate(R.layout.fragment_super_visor_home, container, false);
    }
}