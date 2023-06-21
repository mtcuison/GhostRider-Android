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

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import org.json.JSONObject;
import org.rmj.g3appdriver.GCircle.Api.GCircleApi;
import org.rmj.g3appdriver.dev.Api.HttpHeaders;
import org.rmj.g3appdriver.dev.Api.WebClient;
import org.rmj.g3appdriver.etc.AppConstants;
import org.rmj.g3appdriver.etc.LoadDialog;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.Task.OnTaskExecuteListener;
import org.rmj.g3appdriver.utils.Task.TaskExecutor;
import org.rmj.guanzongroup.ghostrider.samsungknox.Etc.KnoxErrorCode;
import org.rmj.guanzongroup.ghostrider.samsungknox.Etc.ViewModelCallBack;
import org.rmj.guanzongroup.ghostrider.samsungknox.Model.ActivationModel;

public class VMActivate extends AndroidViewModel {
    private static final String TAG = VMActivate.class.getSimpleName();
    private final ConnectionUtil conn;
    private final HttpHeaders headers;
    private final LoadDialog dialog;
    private final Application instance;
    private final GCircleApi poApi;

    public VMActivate(@NonNull Application application) {
        super(application);
        this.instance = application;
        this.poApi = new GCircleApi(instance);
        conn = new ConnectionUtil(application);
        headers = HttpHeaders.getInstance(application);
        dialog = new LoadDialog(application);
    }

    public void ActivateDevice(ActivationModel model, ViewModelCallBack callBack) {
        if (model.isDeviceValid()) {
            TaskExecutor.Execute(model, new OnTaskExecuteListener() {
                @Override
                public void OnPreExecute() {
                    callBack.OnLoadRequest("Samsung Knox", "Activating. Please wait...", false);
                }

                @Override
                public Object DoInBackground(Object args) {
                    ActivationModel lsActivationModel = (ActivationModel) args;
                    String response = "";
                    try {
                        if (conn.isDeviceConnected()) {
                            JSONObject loJSon = new JSONObject();
                            JSONObject loParam = new JSONObject();
                            loJSon.put("deviceUid", lsActivationModel.getDeviceID());
                            loJSon.put("approveComment", lsActivationModel.getRemarksx());
                            loParam.put("request", AppConstants.ACTIVATE_REQUEST);
                            loParam.put("param", loJSon.toString());
                            Log.e(TAG, loParam.toString());
                            response = WebClient.sendRequest(poApi.getUrlKnox(), loParam.toString(), headers.getHeaders());
                        } else {
                            response = AppConstants.NO_INTERNET();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return response;
                }

                @Override
                public void OnPostExecute(Object object) {
                    String lsString = (String) object;
                    try {
                        JSONObject loResponse = new JSONObject(lsString);
                        String lsResult = loResponse.getString("result");
                        if (lsResult.equalsIgnoreCase("success")) {
                            callBack.OnRequestSuccess("Device ID has been activated successfully");
                        } else {
                            JSONObject loError = loResponse.getJSONObject("error");
                            String lsMessage = getErrorMessage(loError);
                            Log.e(TAG, lsMessage);
                            String lsErrCode = loError.getString("code");
                            callBack.OnRequestFailed(KnoxErrorCode.getMessage(lsErrCode, lsMessage));
                            Log.e(TAG, lsString);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        callBack.OnRequestFailed(e.getMessage());
                    }

                }
            });
        } else {
            callBack.OnRequestFailed(model.getPsMessage());
        }
    }
}