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

import org.rmj.g3appdriver.GCircle.Apps.Dcp.obj.CNA;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DEmployeeInfo;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DTownInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.EBarangayInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.lib.Etc.Barangay;
import org.rmj.g3appdriver.lib.Etc.Town;
import org.rmj.g3appdriver.etc.AppConstants;
import org.rmj.g3appdriver.etc.ImageFileCreator;
import org.rmj.g3appdriver.lib.Location.LocationRetriever;
import org.rmj.g3appdriver.GCircle.Account.EmployeeMaster;
import org.rmj.g3appdriver.GCircle.Apps.Dcp.pojo.AddressUpdate;
import org.rmj.g3appdriver.GCircle.Apps.Dcp.pojo.CustomerNotAround;
import org.rmj.g3appdriver.GCircle.Apps.Dcp.model.LRDcp;
import org.rmj.g3appdriver.GCircle.Apps.Dcp.pojo.MobileUpdate;
import org.rmj.g3appdriver.lib.addressbook.data.dao.DAddressUpdate;
import org.rmj.g3appdriver.lib.addressbook.data.dao.DMobileUpdate;
import org.rmj.g3appdriver.utils.Task.OnDoBackgroundTaskListener;
import org.rmj.g3appdriver.utils.Task.OnTaskExecuteListener;
import org.rmj.g3appdriver.utils.Task.TaskExecutor;

import java.util.List;

public class VMCustomerNotAround extends AndroidViewModel {
    private static final String TAG = VMCustomerNotAround.class.getSimpleName();
    private static final String ZERO = "0";
    private final Application instance;

    private final CNA poSys;
    private final EmployeeMaster poUser;

    private final Town poTown;
    private final Barangay poBrgy;

    private String message;

    public VMCustomerNotAround(@NonNull Application application) {
        super(application);
        this.instance = application;
        this.poSys = new CNA(application);
        this.poUser = new EmployeeMaster(application);
        this.poTown = new Town(application);
        this.poBrgy = new Barangay(application);
    }

    public LiveData<DEmployeeInfo.EmployeeBranch> GetUserInfo(){
        return poUser.GetEmployeeBranch();
    }

    public LiveData<EDCPCollectionDetail> GetCollectionDetail(String TransNox, String AccountNo, String EntryNox){
        return poSys.GetAccountDetailForTransaction(TransNox, AccountNo, EntryNox);
    }

    public void SaveNewAddress(AddressUpdate foVal, ViewModelCallback callback){
        TaskExecutor.Execute(foVal, new OnTaskExecuteListener() {
            @Override
            public void OnPreExecute() {
                callback.OnStartSaving();
            }

            @Override
            public Object DoInBackground(Object args) {
                if(!poSys.SaveAddressUpdate((AddressUpdate) args)){
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

    public void SaveNewMobile(MobileUpdate foVal, ViewModelCallback callback){
        TaskExecutor.Execute(foVal, new OnTaskExecuteListener() {
            @Override
            public void OnPreExecute() {
                callback.OnStartSaving();
            }

            @Override
            public Object DoInBackground(Object args) {
                if(!poSys.SaveMobileUpdate((MobileUpdate) args)){
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

    public LiveData<List<DMobileUpdate.MobileUpdateInfo>> GetMobileUpdates(String fsVal){
        return poSys.GetMobileUpdates(fsVal);
    }

    public LiveData<List<DAddressUpdate.AddressUpdateInfo>> GetAddressUpdates(String fsVal){
        return poSys.GetAddressUpdates(fsVal);
    }

    public interface OnRemoveDetailCallback{
        void OnSuccess();
        void OnFailed(String message);
    }

    public void RemoveAddress(String fsVal, OnRemoveDetailCallback callback){
        TaskExecutor.Execute(fsVal, new OnDoBackgroundTaskListener() {
            @Override
            public Object DoInBackground(Object args) {
                if(!poSys.DeleteAddressUpdate((String) args)){
                    message = poSys.getMessage();
                    return false;
                }
                return true;
            }

            @Override
            public void OnPostExecute(Object object) {
                Boolean isSuccess = (Boolean) object;
                if(!isSuccess){
                    callback.OnFailed(message);
                } else {
                    callback.OnSuccess();
                }
            }
        });
    }

    public void RemoveMobile(String fsVal, OnRemoveDetailCallback callback){
        TaskExecutor.Execute(fsVal, new OnDoBackgroundTaskListener() {
            @Override
            public Object DoInBackground(Object args) {
                if(!poSys.DeleteMobileUpdate((String) args)){
                    message = poSys.getMessage();
                    return false;
                }
                return true;
            }

            @Override
            public void OnPostExecute(Object object) {
                Boolean isSuccess = (Boolean) object;
                if(!isSuccess){
                    callback.OnFailed(message);
                } else {
                    callback.OnSuccess();
                }
            }
        });
    }

    public void SaveTransaction(CustomerNotAround foVal, ViewModelCallback callback){
        TaskExecutor.Execute(foVal, new OnTaskExecuteListener() {
            @Override
            public void OnPreExecute() {
                callback.OnStartSaving();
            }

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

    public void InitCameraLaunch(Activity activity, String TransNox, OnInitializeCameraCallback callback){
        ImageFileCreator loImage = new ImageFileCreator(instance, AppConstants.SUB_FOLDER_SELFIE_LOG, TransNox);
        LocationRetriever loLrt = new LocationRetriever(instance, activity);
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
                    return false;
                } else {
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

    public LiveData<List<DTownInfo.TownProvinceInfo>> getTownProvinceInfo(){
        return poTown.getTownProvinceInfo();
    }

    public LiveData<List<EBarangayInfo>> GetBarangayList(String fsVal){
        return poBrgy.getAllBarangayFromTown(fsVal);
    }
}