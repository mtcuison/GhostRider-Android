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
import org.rmj.guanzongroup.ghostrider.epacss.ViewModel.VMRankFile;

public class Fragment_RankFileHome extends Fragment {

    private VMRankFile mViewModel;

    public static Fragment_RankFileHome newInstance() {
        return new Fragment_RankFileHome();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(VMRankFile.class);
        return inflater.inflate(R.layout.fragment_rank_file_home, container, false);
    }
}