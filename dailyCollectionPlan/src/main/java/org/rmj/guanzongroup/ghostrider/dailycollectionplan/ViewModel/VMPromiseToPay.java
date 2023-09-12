/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.dailyCollectionPlan
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GCircle.Apps.Dcp.obj.PTP;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DEmployeeInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.etc.AppConstants;
import org.rmj.g3appdriver.lib.Branch.Branch;
import org.rmj.g3appdriver.lib.Branch.entity.EBranchInfo;
import org.rmj.g3appdriver.lib.Location.LocationRetriever;
import org.rmj.g3appdriver.GCircle.Account.EmployeeMaster;
import org.rmj.g3appdriver.GCircle.Apps.Dcp.model.LRDcp;
import org.rmj.g3appdriver.GCircle.Apps.Dcp.pojo.PromiseToPay;
import org.rmj.g3appdriver.etc.ImageFileCreator;
import org.rmj.g3appdriver.utils.Task.OnDoBackgroundTaskListener;
import org.rmj.g3appdriver.utils.Task.OnTaskExecuteListener;
import org.rmj.g3appdriver.utils.Task.TaskExecutor;

import java.util.List;

public class VMPromiseToPay extends AndroidViewModel {
    private static final String TAG = VMPromiseToPay.class.getSimpleName();

    private final PTP poSys;
    private final EmployeeMaster poUser;

    private final Branch poBranch;
    private final Application instance;

    private String message;

    public VMPromiseToPay(@NonNull Application application) {
        super(application);
        this.instance = application;
        this.poSys = new PTP(application);
        this.poBranch = new Branch(application);
        this.poUser = new EmployeeMaster(application);
    }

    public LiveData<DEmployeeInfo.EmployeeBranch> GetUserInfo(){
        return poUser.GetEmployeeBranch();
    }

    public LiveData<EDCPCollectionDetail> GetCollectionDetail(String TransNo, int EntryNo, String Accountno){
        return poSys.GetAccountDetailForTransaction(TransNo, Accountno, String.valueOf(EntryNo));
    }

    public LiveData<List<EBranchInfo>> getAllBranchInfo(){
        return poBranch.getAllMcBranchInfo();
    }

    public LiveData<String[]> getAllBranchNames(){
        return poBranch.getAllMcBranchNames();
    }

    public void InitCameraLaunch(Activity activity, String TransNox, OnInitializeCameraCallback callback){
        ImageFileCreator loImage = new ImageFileCreator(instance, AppConstants.SUB_FOLDER_DCP, TransNox);
        String[] lsResult = new String[4];
        TaskExecutor.Execute(null, new OnTaskExecuteListener() {
            @Override
            public void OnPreExecute() {
                callback.OnInit();
            }

            @Override
            public Object DoInBackground(Object args) {
                if(!loImage.IsFileCreated(true)){
                    message = loImage.getMessage();
                    return null;
                }

                LocationRetriever loLrt = new LocationRetriever(instance, activity);
                if(loLrt.HasLocation()){
                    lsResult[0] = loImage.getFilePath();
                    lsResult[1] = loImage.getFileName();
                    lsResult[2] = loLrt.getLatitude();
                    lsResult[3] = loLrt.getLongitude();
                    Intent loIntent = loImage.getCameraIntent();
                    loIntent.putExtra("result", true);
                    return loIntent;
                } else {
                    lsResult[0] = loImage.getFilePath();
                    lsResult[1] = loImage.getFileName();
                    lsResult[2] = loLrt.getLatitude();
                    lsResult[3] = loLrt.getLongitude();
                    Intent loIntent = loImage.getCameraIntent();
                    loIntent.putExtra("result", false);
                    message = loLrt.getMessage();
                    return loIntent;
                }
            }

            @Override
            public void OnPostExecute(Object object) {
                Intent loResult = (Intent) object;
                if(loResult.getBooleanExtra("result", false)){
                    callback.OnSuccess(loResult, lsResult);
                } else {
                    callback.OnFailed(message, loResult, lsResult);
                }
            }
        });
    }

    public void SaveTransaction(PromiseToPay foVal, ViewModelCallback callback){
        TaskExecutor.Execute(foVal, new OnDoBackgroundTaskListener() {
            @Override
            public Object DoInBackground(Object args) {
                if(!poSys.SaveTransaction(args)){
                    message = poSys.getMessage();
                    return false;
                }
                return true;
            }

            @Override
            public void OnPostExecute(Object object) {
                Boolean isSuccess = (Boolean) object;
                if(!isSuccess){
                    callback.OnFailedResult(message);
                } else {
                    callback.OnSuccessResult();
                }
            }
        });
    }
}