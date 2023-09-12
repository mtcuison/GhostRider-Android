/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.samsungKnox
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.samsungknox.ViewModel;

import static org.rmj.g3appdriver.dev.Api.ApiResult.getErrorMessage;
import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import org.rmj.g3appdriver.GCircle.Apps.knox.Obj.KnoxGetStatus;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.Task.OnTaskExecuteListener;
import org.rmj.g3appdriver.utils.Task.TaskExecutor;
import org.rmj.guanzongroup.ghostrider.samsungknox.Etc.ViewModelCallBack;

public class VMGetStatus extends AndroidViewModel {
    public static final String TAG = VMGetStatus.class.getSimpleName();
    private final ConnectionUtil poConn;
    private final KnoxGetStatus poSys;

    private String message;

    public VMGetStatus(@NonNull Application instance) {
        super(instance);
        this.poConn = new ConnectionUtil(instance);
        this.poSys = new KnoxGetStatus(instance);
    }

    public void GetDeviceStatus(String DeviceID, ViewModelCallBack callBack) {
        TaskExecutor.Execute(DeviceID, new OnTaskExecuteListener() {
            @Override
            public void OnPreExecute() {
                callBack.OnLoadRequest("Status", "Checking device status", false);
            }

            @Override
            public Object DoInBackground(Object args) {
                try {
                    String lsDeviceID = (String) args;
                    if (!poConn.isDeviceConnected()) {
                        message = poConn.getMessage();
                        return null;
                    }

                    String lsResult = poSys.GetResult(lsDeviceID);

                    if(lsResult == null){
                        message = poSys.getMessage();
                        return null;
                    }

                    return lsResult;
                } catch (Exception e) {
                    e.printStackTrace();
                    message = getLocalMessage(e);
                    return null;
                }
            }

            @Override
            public void OnPostExecute(Object object) {
                if(object == null){
                    callBack.OnRequestFailed(message);
                    return;
                }

                callBack.OnRequestSuccess(
                        poSys.getDeviceID(),
                        poSys.getStatus(),
                        poSys.getDetails(),
                        poSys.getLastUpdate());
            }
        });
    }
}