package org.rmj.guanzongroup.authlibrary.UserInterface.CreateAccount;

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

public class Fragment_CreateAccount extends Fragment implements CreateAccountCallBack {

    private VMCreateAccount mViewModel;
    private LoadDialog dialog;
    private MessageBox loMessage;
    private TextInputEditText tieLastname, tieFirstname, tieMiddname, tieSuffix, tieEmail, tiePassword, tiecPassword, tieMobileno;
    private MaterialButton btnSubmit;

    public static Fragment_CreateAccount newInstance() {
        return new Fragment_CreateAccount();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_create_account, container, false);
        loMessage = new MessageBox(getActivity());
        dialog = new LoadDialog(getActivity());

        tieLastname = v.findViewById(R.id.tie_ca_lastName);
        tieFirstname = v.findViewById(R.id.tie_ca_firstName);
        tieMiddname = v.findViewById(R.id.tie_ca_middleName);
        tieSuffix = v.findViewById(R.id.tie_ca_suffix);
        tieEmail = v.findViewById(R.id.tie_ca_email);
        tiePassword = v.findViewById(R.id.tie_ca_password);
        tiecPassword = v.findViewById(R.id.tie_ca_confirmPass);
        tieMobileno = v.findViewById(R.id.tie_ca_mobileNumber);
        btnSubmit = v.findViewById(R.id.btn_createAccount);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VMCreateAccount.class);
        btnSubmit.setOnClickListener(view -> {
            AccountInfo accountInfo = new AccountInfo();
            accountInfo.setLastName(Objects.requireNonNull(tieLastname.getText()).toString());
            accountInfo.setFrstName(Objects.requireNonNull(tieFirstname.getText()).toString());
            accountInfo.setMiddName(Objects.requireNonNull(tieMiddname.getText()).toString());
            accountInfo.setSuffix(Objects.requireNonNull(tieSuffix.getText()).toString());
            accountInfo.setEmail(Objects.requireNonNull(tieEmail.getText()).toString());
            accountInfo.setPassword(Objects.requireNonNull(tiePassword.getText()).toString());
            accountInfo.setcPasswrd(Objects.requireNonNull(tiecPassword.getText()).toString());
            accountInfo.setMobileNo(Objects.requireNonNull(tieMobileno.getText()).toString());
            mViewModel.SubmitInfo(accountInfo, Fragment_CreateAccount.this);
        });
    }

    @Override
    public void OnAccountLoad(String Title, String Message) {
        dialog.initDialog(Title, Message, false);
        dialog.show();
    }

    @Override
    public void OnSuccessRegistration() {
        dialog.dismiss();
        loMessage.initDialog();
        loMessage.setTitle("Create Account");
        loMessage.setMessage("A verification email has been sent to your email account. Please check your inbox or spam folder.");
        loMessage.setPositiveButton("Okay", (view, msgDialog) -> msgDialog.dismiss());
        loMessage.show();
    }

    @Override
    public void OnFailedRegistration(String message) {
        dialog.dismiss();
        loMessage.initDialog();
        loMessage.setTitle("Create Account");
        loMessage.setMessage(message);
        loMessage.setPositiveButton("Okay", (view, msgDialog) -> msgDialog.dismiss());
        loMessage.show();
    }
}