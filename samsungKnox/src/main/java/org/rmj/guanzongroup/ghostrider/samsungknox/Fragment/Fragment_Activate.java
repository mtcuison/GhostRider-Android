package org.rmj.guanzongroup.ghostrider.samsungknox.Fragment;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.rmj.g3appdriver.GRider.Etc.LoadDialog;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.guanzongroup.ghostrider.samsungknox.Etc.ViewModelCallBack;
import org.rmj.guanzongroup.ghostrider.samsungknox.Model.ActivationModel;
import org.rmj.guanzongroup.ghostrider.samsungknox.R;
import org.rmj.guanzongroup.ghostrider.samsungknox.ViewModel.VMActivate;

import java.util.Objects;

public class Fragment_Activate extends Fragment implements ViewModelCallBack {

    private VMActivate mViewModel;
    private TextInputEditText txtImei, txtRemrks;
    private MaterialButton btnActivate;
    private LoadDialog dialog;
    private MessageBox loMessage;

    public static Fragment_Activate newInstance() {
        return new Fragment_Activate();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_activate, container, false);
        dialog = new LoadDialog(getActivity());
        loMessage = new MessageBox(getActivity());
        txtImei = v.findViewById(R.id.txt_knoxImei);
        txtRemrks = v.findViewById(R.id.txt_knoxRemarks);
        ImageButton btnScanID = v.findViewById(R.id.btn_knoxScan);
        btnActivate = v.findViewById(R.id.btn_knoxActivate);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VMActivate.class);
        btnActivate.setOnClickListener(view -> {
            String lsDevcID = Objects.requireNonNull(txtImei.getText()).toString().trim();
            String lsRemrks = Objects.requireNonNull(txtRemrks.getText()).toString().trim();
            ActivationModel model = new ActivationModel(lsDevcID, lsRemrks);
            mViewModel.ActivateDevice(model, Fragment_Activate.this);
        });
    }

    @Override
    public void OnLoadRequest(String Title, String Message, boolean Cancellable) {
        dialog.initDialog(Title, Message, Cancellable);
        dialog.show();
    }

    @Override
    public void OnRequestSuccess(String args) {
        dialog.dismiss();
        Toast.makeText(getActivity(), args, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnRequestFailed(String message) {
        dialog.dismiss();
        loMessage.setMessage(message);
        loMessage.setTitle("Unlock Device");
        loMessage.setPositiveButton("Okay", (view, msgDialog) -> msgDialog.dismiss());
        loMessage.show();
    }
}