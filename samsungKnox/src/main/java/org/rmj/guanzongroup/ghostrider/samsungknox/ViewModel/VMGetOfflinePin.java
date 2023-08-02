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

import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import org.rmj.g3appdriver.GCircle.Apps.knox.Obj.KnoxOfflinePin;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.Task.OnTaskExecuteListener;
import org.rmj.g3appdriver.utils.Task.TaskExecutor;
import org.rmj.guanzongroup.ghostrider.samsungknox.Etc.ViewModelCallBack;
import org.rmj.guanzongroup.ghostrider.samsungknox.Model.PinModel;

public class VMGetOfflinePin extends AndroidViewModel {
    public static final String TAG = VMGetOfflinePin.class.getSimpleName();
    private final ConnectionUtil poConn;
    private final KnoxOfflinePin poSys;

    private String message;

    public VMGetOfflinePin(@NonNull Application application) {
        super(application);
        this.poConn = new ConnectionUtil(application);
        this.poSys = new KnoxOfflinePin(application);
    }

    public void UnlockWithPasskey(PinModel model, ViewModelCallBack callBack) {
        TaskExecutor.Execute(model, new OnTaskExecuteListener() {
            @Override
            public void OnPreExecute() {
                callBack.OnLoadRequest("Samsung Knox", "Getting unlock PIN. Please wait...", false);
            }

            @Override
            public Object DoInBackground(Object args) {
                try {
                    PinModel loInfo = (PinModel) args;

                    if(!poConn.isDeviceConnected()) {
                        message = poConn.getMessage();
                        return null;
                    }

                    String lsResult = poSys.GetResult(loInfo.getDeviceID(), loInfo.getPassKey());
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
            public void OnPostExecute(Object result) {
                if(result == null){
                    callBack.OnRequestFailed(message);
                    return;
                }

                callBack.OnRequestSuccess((String) result, null, null, null);
            }
        });
    }
}