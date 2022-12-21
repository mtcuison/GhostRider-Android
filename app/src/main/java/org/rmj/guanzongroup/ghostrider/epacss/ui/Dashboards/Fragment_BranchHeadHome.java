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
import org.rmj.guanzongroup.ghostrider.epacss.ViewModel.VMBranchHead;

public class Fragment_BranchHeadHome extends Fragment {

    private VMBranchHead mViewModel;

    public static Fragment_BranchHeadHome newInstance() {
        return new Fragment_BranchHeadHome();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(VMBranchHead.class);
        return inflater.inflate(R.layout.fragment_branch_head_home, container, false);
    }
}