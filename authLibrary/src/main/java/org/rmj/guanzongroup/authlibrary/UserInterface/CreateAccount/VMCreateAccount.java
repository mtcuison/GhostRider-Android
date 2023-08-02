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

package org.rmj.guanzongroup.authlibrary.UserInterface.CreateAccount;

import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import org.rmj.g3appdriver.lib.Account.AccountMaster;
import org.rmj.g3appdriver.lib.Account.Model.Auth;
import org.rmj.g3appdriver.lib.Account.Model.iAuth;
import org.rmj.g3appdriver.lib.Account.pojo.AccountInfo;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.Task.OnTaskExecuteListener;
import org.rmj.g3appdriver.utils.Task.TaskExecutor;

public class VMCreateAccount extends AndroidViewModel{
    public static final String TAG = VMCreateAccount.class.getSimpleName();
    private final Application instance;

    private final iAuth poSys;
    private final ConnectionUtil poConn;

    private String message;

    public VMCreateAccount(@NonNull Application application) {
        super(application);
        this.instance = application;
        this.poSys = new AccountMaster(instance).initGuanzonApp().getInstance(Auth.CREATE_ACCOUNT);
        this.poConn = new ConnectionUtil(instance);
    }

    public void SubmitInfo(AccountInfo accountInfo, CreateAccountCallBack callBack){
        TaskExecutor.Execute(accountInfo, new OnTaskExecuteListener() {
            @Override
            public void OnPreExecute() {
                callBack.OnAccountLoad("Guanzon Circle", "Creating Account. Please wait...");
            }

            @Override
            public Object DoInBackground(Object args) {
                try {
                    if(!poConn.isDeviceConnected()){
                        message = poConn.getMessage();
                        return false;
                    }

                    int lnResult = poSys.DoAction(args);

                    if(lnResult == 0){
                        message = poSys.getMessage();
                        return false;
                    }

                    return true;
                } catch (Exception e){
                    e.printStackTrace();
                    message = getLocalMessage(e);
                    return false;
                }
            }

            @Override
            public void OnPostExecute(Object object) {
                boolean isSuccess = (boolean) object;
                if(!isSuccess){
                    callBack.OnFailedRegistration(message);
                    return;
                }

                callBack.OnSuccessRegistration();
            }
        });
    }
}