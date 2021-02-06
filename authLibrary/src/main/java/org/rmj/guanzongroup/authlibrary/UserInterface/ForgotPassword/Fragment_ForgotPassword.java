package org.rmj.guanzongroup.authlibrary.UserInterface.ForgotPassword;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.rmj.g3appdriver.GRider.Etc.LoadDialog;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.guanzongroup.authlibrary.R;

import java.util.Objects;

public class Fragment_ForgotPassword extends Fragment implements VMForgotPassword.RequestPasswordCallback {

    private VMForgotPassword mViewModel;

    private TextInputEditText tieEmail;
    private MaterialButton btnSendEmail;
    private LoadDialog poDialog;
    private MessageBox poMsgBox;

    public static Fragment_ForgotPassword newInstance() {
        return new Fragment_ForgotPassword();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_forgot_password, container, false);
        poDialog = new LoadDialog(getActivity());
        poMsgBox = new MessageBox(getActivity());
        tieEmail = v.findViewById(R.id.tie_fp_Email);
        btnSendEmail = v.findViewById(R.id.btn_sendEmail);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VMForgotPassword.class);
        btnSendEmail.setOnClickListener(view -> {
            String email = Objects.requireNonNull(tieEmail.getText()).toString().trim();
            mViewModel.RequestPassword(email, Fragment_ForgotPassword.this);
        });
    }

    @Override
    public void OnSendRequest(String title, String message) {
        poDialog.initDialog(title, message, false);
        poDialog.show();
    }

    @Override
    public void OnSuccessRequest() {
        poDialog.dismiss();
        poMsgBox.setTitle("Forgot Password");
        poMsgBox.setMessage("You'll be receiving an email from MIS, Please check your email account");
        poMsgBox.setPositiveButton("Okay", (view, msgDialog) -> msgDialog.dismiss());
        poMsgBox.show();
    }

    @Override
    public void OnFailedRequest(String message) {
        poDialog.dismiss();
        poDialog.dismiss();
        poMsgBox.setTitle("Forgot Password");
        poMsgBox.setMessage(message);
        poMsgBox.setPositiveButton("Okay", (view, msgDialog) -> msgDialog.dismiss());
        poMsgBox.show();
    }
}