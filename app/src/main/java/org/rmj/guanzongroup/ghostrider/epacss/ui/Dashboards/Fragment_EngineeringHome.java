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
import org.rmj.guanzongroup.ghostrider.epacss.ViewModel.VMEngineeringHome;

public class Fragment_EngineeringHome extends Fragment {

    private VMEngineeringHome mViewModel;

    public static Fragment_EngineeringHome newInstance() {
        return new Fragment_EngineeringHome();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(VMEngineeringHome.class);
        return inflater.inflate(R.layout.fragment_engineering_home, container, false);
    }
}