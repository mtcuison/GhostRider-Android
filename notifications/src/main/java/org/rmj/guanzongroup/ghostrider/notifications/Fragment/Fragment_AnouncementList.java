package org.rmj.guanzongroup.ghostrider.notifications.Fragment;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.rmj.g3appdriver.lib.Notifications.data.SampleData;
import org.rmj.guanzongroup.ghostrider.notifications.Adapter.AdapterAnnouncements;
import org.rmj.guanzongroup.ghostrider.notifications.R;
import org.rmj.guanzongroup.ghostrider.notifications.ViewModel.VMAnouncementList;

public class Fragment_AnouncementList extends Fragment {

    private VMAnouncementList mViewModel;

    private View view;

    private RecyclerView recyclerView;

    public static Fragment_AnouncementList newInstance() {
        return new Fragment_AnouncementList();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(VMAnouncementList.class);
        view = inflater.inflate(R.layout.fragment_anouncement_list, container, false);

        recyclerView = view.findViewById(R.id.recyclerview);
        initAnnouncements();
        return view;
    }

    private void initAnnouncements(){
        AdapterAnnouncements loAdapter = new AdapterAnnouncements(SampleData.GetAnnouncementList(), new AdapterAnnouncements.OnItemClickListener() {
            @Override
            public void OnClick(String args) {

            }
        });
        LinearLayoutManager loManager = new LinearLayoutManager(requireActivity());
        loManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(loManager);
        recyclerView.setAdapter(loAdapter);
    }
}