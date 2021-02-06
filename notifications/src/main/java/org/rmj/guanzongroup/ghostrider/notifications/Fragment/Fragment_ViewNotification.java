package org.rmj.guanzongroup.ghostrider.notifications.Fragment;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.rmj.guanzongroup.ghostrider.notifications.Activity.Activity_Notifications;
import org.rmj.guanzongroup.ghostrider.notifications.R;
import org.rmj.guanzongroup.ghostrider.notifications.ViewModel.VMViewNotification;

public class Fragment_ViewNotification extends Fragment {

    private VMViewNotification mViewModel;

    public static Fragment_ViewNotification newInstance() {
        return new Fragment_ViewNotification();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_message, container, false);

        TextView title = view.findViewById(R.id.lbl_messageTitle);
        TextView sender = view.findViewById(R.id.lbl_messageSender);
        TextView date = view.findViewById(R.id.lbl_messageDateTime);
        TextView message = view.findViewById(R.id.lbl_messageBody);

        title.setText(Activity_Notifications.getInstance().getMessageTitle());
        sender.setText(Activity_Notifications.getInstance().getSender());
        date.setText(Activity_Notifications.getInstance().getDate());
        message.setText(Activity_Notifications.getInstance().getMessage());

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VMViewNotification.class);
        // TODO: Use the ViewModel
    }
}