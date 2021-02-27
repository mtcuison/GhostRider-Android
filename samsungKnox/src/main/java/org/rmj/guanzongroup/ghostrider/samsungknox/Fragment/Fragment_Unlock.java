package org.rmj.guanzongroup.ghostrider.samsungknox.Fragment;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.rmj.g3appdriver.GRider.Etc.LoadDialog;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.g3appdriver.utils.CopyToClipboard;
import org.rmj.guanzongroup.ghostrider.samsungknox.Etc.ViewModelCallBack;
import org.rmj.guanzongroup.ghostrider.samsungknox.R;
import org.rmj.guanzongroup.ghostrider.samsungknox.ViewModel.VMUnlock;

import java.util.Objects;

public class Fragment_Unlock extends Fragment implements ViewModelCallBack {

    private VMUnlock mViewModel;
    private TextInputEditText txtImei;
    private EditText txtPIN;
    private MaterialButton btnGetPIN;
    private MessageBox loMessage;
    private LoadDialog dialog;

    public static Fragment_Unlock newInstance() {
        return new Fragment_Unlock();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_unlock, container, false);
        dialog = new LoadDialog(getActivity());
        loMessage = new MessageBox(getActivity());
        txtImei = v.findViewById(R.id.txt_knoxImei);
        txtPIN = v.findViewById(R.id.txt_KnoxPIN);
        ImageButton btnCopy = v.findViewById(R.id.btn_CopyToClipboard);
        btnCopy.setOnClickListener(view -> {
            String KnoxPin = txtPIN.getText().toString();
            String message;
            if (!KnoxPin.isEmpty()) {
                new CopyToClipboard(getActivity()).CopyTextClip("Knox_Pin", KnoxPin);
                message = "Knox pin copied to clipboard.";
            } else {
                message = "Unable to copy empty content.";
            }
            Toast toast = Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        });
        btnGetPIN = v.findViewById(R.id.btn_knoxUnlock);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VMUnlock.class);
        btnGetPIN.setOnClickListener(view -> {
            String lsDevcIDx = Objects.requireNonNull(txtImei.getText()).toString();
            mViewModel.UnlockDevice(lsDevcIDx, Fragment_Unlock.this);
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
        txtPIN.setText(args);
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