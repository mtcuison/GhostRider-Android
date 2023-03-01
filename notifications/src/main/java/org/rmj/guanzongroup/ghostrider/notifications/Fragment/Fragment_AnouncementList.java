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
import org.rmj.guanzongroup.ghostrider.notifications.ViewModel.VMAnouncementList;

public class Fragment_AnouncementList extends Fragment {

    private VMAnouncementList mViewModel;

    public static Fragment_AnouncementList newInstance() {
        return new Fragment_AnouncementList();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(VMAnouncementList.class);
        return inflater.inflate(R.layout.fragment_anouncement_list, container, false);
    }
}