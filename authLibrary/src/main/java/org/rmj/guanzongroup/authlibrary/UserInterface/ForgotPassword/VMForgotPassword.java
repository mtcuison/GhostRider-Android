/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.authLibrary
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */

package org.rmj.guanzongroup.authlibrary.UserInterface.ForgotPassword;

import android.app.Application;
import android.text.TextUtils;
import android.util.Patterns;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import org.rmj.g3appdriver.lib.Account.AccountMaster;
import org.rmj.g3appdriver.lib.Account.Model.Auth;
import org.rmj.g3appdriver.lib.Account.Model.iAuth;
import org.rmj.g3appdriver.lib.Account.gCircle.obj.SendVerification;
import org.rmj.g3appdriver.lib.Account.pojo.PasswordUpdate;
import org.rmj.g3appdriver.lib.Account.pojo.VerificationInfo;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.Task.OnTaskExecuteListener;
import org.rmj.g3appdriver.utils.Task.TaskExecutor;

import java.util.List;
import java.util.concurrent.Future;

public class VMForgotPassword extends AndroidViewModel {

    public static final String TAG = VMForgotPassword.class.getSimpleName();
    private final iAuth poSysForgot;
    private final iAuth poSysChangePassword;
    private final SendVerification poVerify;
    private final ConnectionUtil conn;
    private final Application instance;
    private String message;

    public VMForgotPassword(@NonNull Application application) {
        super(application);

        this.instance = application;
        this.poSysForgot = new AccountMaster(instance).initGuanzonApp().getInstance(Auth.FORGOT_PASSWORD);
        this.poSysChangePassword = new AccountMaster(instance).initGuanzonApp().getInstance(Auth.CHANGE_PASSWORD);
        this.poVerify = new SendVerification(instance);
        this.conn = new ConnectionUtil(application);
    }

    public void SendVerificaiton(String fsParams, VerifyAccount mListener){

        TaskExecutor.Execute(fsParams, new OnTaskExecuteListener() {
            @Override
            public void OnPreExecute() {
                mListener.OnSendRequest("Send Request", "Sending Request . . .");
            }

            @Override
            public Object DoInBackground(Object args) {

                if (!conn.isDeviceConnected()){
                    message = conn.getMessage();
                    return false;
                }
                message = poVerify.GetMessage();
                return poVerify.VerifyAccount((String) args);
            }

            @Override
            public void OnPostExecute(Object object) {

                if (object instanceof VerificationInfo){

                    if (((VerificationInfo) object).getsUserIDxx().isEmpty()){
                        mListener.OnFailedRequest("Account information not found");
                        return;
                    }else if (((VerificationInfo) object).getsOTPxx().isEmpty()){
                        mListener.OnFailedRequest("OTP number is invalid");
                        return;
                    }
                    mListener.OnSuccessRequest((VerificationInfo) object);
                    return;
                }
                mListener.OnFailedRequest(message);
            }
        });

    }

    public void ChangePassword(PasswordUpdate foPassword, RequestPasswordCallback mListener){

        TaskExecutor.Execute(foPassword, new OnTaskExecuteListener() {
            @Override
            public void OnPreExecute() {
                mListener.OnSendRequest("Change Password", "Sending Request . . .");
            }

            @Override
            public Object DoInBackground(Object args) {
                if (!conn.isDeviceConnected()){
                    message = conn.getMessage();
                    return false;
                }
                if (poSysChangePassword.DoAction((PasswordUpdate) args) == 0){
                    message = poSysChangePassword.getMessage();
                    return false;
                }else {
                    message = poSysChangePassword.getMessage();
                    return true;
                }
            }

            @Override
            public void OnPostExecute(Object object) {
                Boolean result = (Boolean) object;

                if (result.equals(false)){
                    mListener.OnFailedRequest(message);
                }else {
                    mListener.OnSuccessRequest();
                }
            }
        });

    }

    public void RequestPassword(String Email, RequestPasswordCallback mListener){

        TaskExecutor.Execute(Email, new OnTaskExecuteListener() {
            @Override
            public void OnPreExecute() {
                mListener.OnSendRequest("Send Request", "Sending Request . . .");
            }

            @Override
            public Object DoInBackground(Object args) {
                if (!conn.isDeviceConnected()){
                    message = conn.getMessage();
                    return false;
                }

                if (poSysForgot.DoAction((String) args) == 0){
                    message = poSysForgot.getMessage();
                    return false;
                }else {
                    message = poSysForgot.getMessage();
                    return true;
                }
            }

            @Override
            public void OnPostExecute(Object object) {
                Boolean result = (Boolean) object;

                if (result.equals(false)){
                    mListener.OnFailedRequest(message);
                }else {
                    mListener.OnSuccessRequest();
                }
            }
        });
    }

    public interface RequestPasswordCallback{
        void OnSendRequest(String title, String message);
        void OnSuccessRequest();
        void OnFailedRequest(String message);
    }

    public interface VerifyAccount{
        void OnSendRequest(String title, String message);
        void OnSuccessRequest(VerificationInfo fsUserIDxx);
        void OnFailedRequest(String message);
    }
}