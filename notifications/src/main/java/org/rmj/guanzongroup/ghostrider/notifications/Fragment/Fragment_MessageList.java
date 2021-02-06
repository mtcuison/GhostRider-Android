package org.rmj.guanzongroup.ghostrider.notifications.Fragment;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.rmj.guanzongroup.ghostrider.notifications.Activity.Activity_Notifications;
import org.rmj.guanzongroup.ghostrider.notifications.Adapter.MessageListAdapter;
import org.rmj.guanzongroup.ghostrider.notifications.Object.MessageItemList;
import org.rmj.guanzongroup.ghostrider.notifications.R;
import org.rmj.guanzongroup.ghostrider.notifications.ViewModel.VMMessageList;

import java.util.List;

public class Fragment_MessageList extends Fragment {

    private VMMessageList mViewModel;

    private RecyclerView recyclerView;

    public static Fragment_MessageList newInstance() {
        return new Fragment_MessageList();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message_list, container, false);

        recyclerView = view.findViewById(R.id.recyclerview_messages);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VMMessageList.class);
        mViewModel.getMessageList().observe(getViewLifecycleOwner(), messageItemLists -> {
            LinearLayoutManager manager = new LinearLayoutManager(getActivity());
            manager.setOrientation(RecyclerView.VERTICAL);
            recyclerView.setLayoutManager(manager);
            recyclerView.setAdapter(new MessageListAdapter(messageItemLists, (Title, Message) -> {
                Intent loIntent = new Intent(getActivity(), Activity_Notifications.class);
                loIntent.putExtra("title", Title);
                loIntent.putExtra("message", Message);
                loIntent.putExtra("type", "message");
                startActivity(loIntent);
            }));
        });
    }

}