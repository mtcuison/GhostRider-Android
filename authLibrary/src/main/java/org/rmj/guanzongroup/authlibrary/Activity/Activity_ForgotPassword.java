package org.rmj.guanzongroup.authlibrary.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.etc.LoadDialog;
import org.rmj.g3appdriver.etc.MessageBox;
import org.rmj.g3appdriver.lib.Account.pojo.PasswordUpdate;
import org.rmj.g3appdriver.lib.Account.pojo.VerificationInfo;
import org.rmj.guanzongroup.authlibrary.R;
import org.rmj.guanzongroup.authlibrary.UserInterface.ForgotPassword.VMForgotPassword;

import java.util.List;
import java.util.Objects;

public class Activity_ForgotPassword extends AppCompatActivity{
    private VMForgotPassword mViewModel;
    private LoadDialog poDialog;
    private MessageBox poMsgBox;
    private AppConfigPreference poConfigx;

    private VerificationInfo loResult;

    private View layout_otp;
    private TextInputEditText tie_otp1, tie_otp2, tie_otp3, tie_otp4, tie_otp5, tie_otp6;
    private TextInputEditText newpassword, verifypassword;
    private MaterialTextView lblVersion, mtv_resend;
    private TextInputEditText tie_email;
    private MaterialButton btn_log, btn_verify, btn_cancel;
    private MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        poDialog = new LoadDialog(this);
        poMsgBox = new MessageBox(this);
        poConfigx = AppConfigPreference.getInstance(this);
        mViewModel = new ViewModelProvider(this).get(VMForgotPassword.class);

        poMsgBox.initDialog();

        InitViews();
        InitListener();

        lblVersion.setText(poConfigx.getVersionInfo());
    }

    private void InitViews(){

        newpassword = findViewById(R.id.newpassword);
        verifypassword = findViewById(R.id.verifypassword);

        layout_otp = findViewById(R.id.layout_otp);
        mtv_resend = layout_otp.findViewById(R.id.mtv_resend);
        btn_verify = layout_otp.findViewById(R.id.btn_verify);
        btn_cancel = layout_otp.findViewById(R.id.btn_cancel);

        tie_otp1 = layout_otp.findViewById(R.id.tie_otp1);
        tie_otp2 = layout_otp.findViewById(R.id.tie_otp2);
        tie_otp3 = layout_otp.findViewById(R.id.tie_otp3);
        tie_otp4 = layout_otp.findViewById(R.id.tie_otp4);
        tie_otp5 = layout_otp.findViewById(R.id.tie_otp5);
        tie_otp6 = layout_otp.findViewById(R.id.tie_otp6);

        toolbar = findViewById(R.id.toolbar);
        lblVersion = findViewById(R.id.lbl_versionInfo);
        tie_email = findViewById(R.id.email);
        btn_log = findViewById(R.id.btn_log);

        setSupportActionBar(toolbar); //set object toolbar as default action bar for activity
        getSupportActionBar().setTitle(""); //set default title for action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //set back button to toolbar
        getSupportActionBar().setDisplayShowHomeEnabled(true); //enable the back button set on toolbar

    }

    private void InitListener(){

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btn_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (newpassword.getText() == null || newpassword.getText().toString().isEmpty()){
                    Toast.makeText(Activity_ForgotPassword.this, "Password is empty", Toast.LENGTH_SHORT).show();
                    newpassword.requestFocus();
                    return;
                }else if (verifypassword.getText() == null || verifypassword.getText().toString().isEmpty()){
                    Toast.makeText(Activity_ForgotPassword.this, "Please verify password", Toast.LENGTH_SHORT).show();
                    verifypassword.requestFocus();
                    return;
                }else if (!(newpassword.getText().toString().equalsIgnoreCase(verifypassword.getText().toString()))){
                    Toast.makeText(Activity_ForgotPassword.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    newpassword.requestFocus();
                    return;
                }

                mViewModel.SendVerificaiton(tie_email.getText().toString(), new VMForgotPassword.VerifyAccount() {
                    @Override
                    public void OnSendRequest(String title, String message) {
                        poDialog.initDialog("Verify Account", "Sending OTP. Please wait . . .", false);
                        poDialog.show();
                    }

                    @Override
                    public void OnSuccessRequest(VerificationInfo foResult) {

                        poDialog.dismiss();

                        layout_otp.setAnimation(AnimationUtils.loadAnimation(Activity_ForgotPassword.this, R.anim.anim_slide_up));
                        layout_otp.setVisibility(View.VISIBLE);

                        tie_otp1.setText("");
                        tie_otp2.setText("");
                        tie_otp3.setText("");
                        tie_otp4.setText("");
                        tie_otp5.setText("");
                        tie_otp6.setText("");

                        tie_otp1.requestFocus();

                        loResult = foResult;
                    }

                    @Override
                    public void OnFailedRequest(String message) {
                        poDialog.dismiss();

                        poMsgBox.setIcon(R.drawable.baseline_error_24);
                        poMsgBox.setTitle("Result");
                        poMsgBox.setMessage("Failed to send request: " + message);
                        poMsgBox.setPositiveButton("Okay", new MessageBox.DialogButton() {
                            @Override
                            public void OnButtonClick(View view, AlertDialog dialog) {
                                dialog.dismiss();
                            }
                        });
                        poMsgBox.show();
                    }
                });
            }
        });

        mtv_resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mViewModel.SendVerificaiton(tie_email.getText().toString(), new VMForgotPassword.VerifyAccount() {
                    @Override
                    public void OnSendRequest(String title, String message) {
                        poDialog.initDialog("Verify Account", "Sending OTP. Please wait . . .", false);
                        poDialog.show();
                    }

                    @Override
                    public void OnSuccessRequest(VerificationInfo foResult) {
                        poDialog.dismiss();

                        tie_otp1.setText("");
                        tie_otp2.setText("");
                        tie_otp3.setText("");
                        tie_otp4.setText("");
                        tie_otp5.setText("");
                        tie_otp6.setText("");

                        tie_otp1.requestFocus();

                        loResult = foResult;
                    }

                    @Override
                    public void OnFailedRequest(String message) {
                        poDialog.dismiss();

                        poMsgBox.setIcon(R.drawable.baseline_error_24);
                        poMsgBox.setTitle("Result");
                        poMsgBox.setMessage("Failed to send request: " + message);
                        poMsgBox.setPositiveButton("Okay", new MessageBox.DialogButton() {
                            @Override
                            public void OnButtonClick(View view, AlertDialog dialog) {
                                dialog.dismiss();
                            }
                        });
                        poMsgBox.show();
                    }
                });
            }
        });

        btn_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String lsOTP =
                                (tie_otp1.getText() == null ? "" : tie_otp1.getText().toString()) +
                                (tie_otp2.getText() == null ? "" : tie_otp2.getText().toString()) +
                                (tie_otp3.getText() == null ? "" : tie_otp3.getText().toString()) +
                                (tie_otp4.getText() == null ? "" : tie_otp4.getText().toString()) +
                                (tie_otp5.getText() == null ? "" : tie_otp5.getText().toString()) +
                                (tie_otp6.getText() == null ? "" : tie_otp6.getText().toString());

                if (loResult == null){
                    Toast.makeText(Activity_ForgotPassword.this, "Verification is invalid. Please try resending OTP", Toast.LENGTH_SHORT).show();
                    return;
                }else if (loResult.getsUserIDxx().isEmpty()){
                    Toast.makeText(Activity_ForgotPassword.this, "User information not found", Toast.LENGTH_SHORT).show();
                    return;
                }else if (loResult.getsOTPxx().isEmpty()){
                    Toast.makeText(Activity_ForgotPassword.this, "OTP number not initialized", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!lsOTP.equalsIgnoreCase(loResult.getsOTPxx())){

                    tie_otp1.setText("");
                    tie_otp2.setText("");
                    tie_otp3.setText("");
                    tie_otp4.setText("");
                    tie_otp5.setText("");
                    tie_otp6.setText("");

                    tie_otp1.requestFocus();

                    Toast.makeText(Activity_ForgotPassword.this, "OTP number did not match " + lsOTP, Toast.LENGTH_SHORT).show();
                    return;
                }

                PasswordUpdate loPassChange = new PasswordUpdate(loResult.getsUserIDxx(), "", newpassword.getText().toString(), verifypassword.getText().toString());
                mViewModel.ChangePassword(loPassChange, new VMForgotPassword.RequestPasswordCallback() {
                    @Override
                    public void OnSendRequest(String title, String message) {
                        poDialog.initDialog(title, message, false);
                        poDialog.show();
                    }

                    @Override
                    public void OnSuccessRequest() {
                        poDialog.dismiss();

                        poMsgBox.setIcon(R.drawable.baseline_message_24);
                        poMsgBox.setTitle("Account Update");
                        poMsgBox.setMessage("Password has been changed successfully");
                        poMsgBox.setPositiveButton("Okay", new MessageBox.DialogButton() {
                            @Override
                            public void OnButtonClick(View view, AlertDialog dialog) {
                                dialog.dismiss();
                                finish();
                            }
                        });
                        poMsgBox.show();
                    }

                    @Override
                    public void OnFailedRequest(String message) {
                        poDialog.dismiss();

                        poMsgBox.setIcon(R.drawable.baseline_error_24);
                        poMsgBox.setTitle("Account Update");
                        poMsgBox.setMessage(message);
                        poMsgBox.setPositiveButton("Okay", new MessageBox.DialogButton() {
                            @Override
                            public void OnButtonClick(View view, AlertDialog dialog) {
                                dialog.dismiss();
                            }
                        });
                        poMsgBox.show();
                    }
                });
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_otp.setVisibility(View.GONE);
            }
        });

        tie_otp1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                IsOTPValid();
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        tie_otp2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                IsOTPValid();
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        tie_otp3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                IsOTPValid();
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        tie_otp4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                IsOTPValid();
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        tie_otp5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                IsOTPValid();
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        tie_otp6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                IsOTPValid();
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }

    private void IsOTPValid(){

        List<View> laFields = List.of(tie_otp1, tie_otp2, tie_otp3, tie_otp4, tie_otp5, tie_otp6);
        boolean isValidField = true;

        for (View view : laFields){

            if (view instanceof TextInputEditText){

                if ( ((TextInputEditText) view).getText() == null || ((TextInputEditText) view).getText().toString().isEmpty() ){
                    isValidField = false;
                    btn_verify.setEnabled(false);
                    view.requestFocus();
                    break;
                }
            }
        }
        btn_verify.setEnabled(isValidField);
    }

}