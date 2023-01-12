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
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.rmj.g3appdriver.dev.Database.DataAccessObject.DEmployeeInfo;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DTownInfo;
import org.rmj.g3appdriver.dev.Database.Entities.EBarangayInfo;
import org.rmj.g3appdriver.dev.Database.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.dev.Database.Repositories.RBarangay;
import org.rmj.g3appdriver.dev.Database.Repositories.RBranch;
import org.rmj.g3appdriver.dev.Database.Repositories.RProvince;
import org.rmj.g3appdriver.dev.Database.Repositories.RTown;
import org.rmj.g3appdriver.etc.AppConstants;
import org.rmj.g3appdriver.etc.LocationRetriever;
import org.rmj.g3appdriver.lib.Account.EmployeeMaster;
import org.rmj.g3appdriver.lib.integsys.Dcp.LRDcp;
import org.rmj.g3appdriver.lib.integsys.Dcp.model.LoanUnit;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Etc.DCP_Constants;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;
import org.rmj.g3appdriver.etc.ImageFileCreator;

import java.io.File;
import java.util.List;

public class VMLoanUnit extends AndroidViewModel {
    private static final String TAG = VMLoanUnit.class.getSimpleName();

    private final Application instance;
    private final LRDcp poSys;
    private final EmployeeMaster poUser;
    private final RBranch poBranch;

    private final RProvince poProv;
    private final RTown poTown;
    private final RBarangay Brgy;

    public VMLoanUnit(@NonNull Application application) {
        super(application);
        this.instance = application;
        this.poSys = new LRDcp(application);
        this.poUser = new EmployeeMaster(application);
        this.poBranch = new RBranch(application);
        poProv = new RProvince(application);
        poTown = new RTown(application);
        Brgy = new RBarangay(application);
    }

    public LiveData<DEmployeeInfo.EmployeeBranch> GetUserInfo(){
        return poUser.GetEmployeeBranch();
    }

    public LiveData<EDCPCollectionDetail> GetCollectionDetail(String TransNox, String AccountNo, String EntryNox){
        return poSys.GetAccountDetailForTransaction(TransNox, AccountNo, EntryNox);
    }

    public void InitCameraLaunch(Activity activity, String TransNox, OnInitializeCameraCallback callback){
        new InitializeCameraTask(activity, TransNox, instance, callback).execute();
    }

    private static class InitializeCameraTask extends AsyncTask<String, Void, Boolean>{

        private final OnInitializeCameraCallback callback;
        private final ImageFileCreator loImage;
        private final LocationRetriever loLrt;

        private Intent loIntent;
        private String[] args = new String[4];
        private String message;

        public InitializeCameraTask(Activity activity, String TransNox, Application instance, OnInitializeCameraCallback callback){
            this.callback = callback;
            this.loImage = new ImageFileCreator(instance, AppConstants.SUB_FOLDER_SELFIE_LOG, TransNox);
            this.loLrt = new LocationRetriever(instance, activity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callback.OnInit();
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            if(!loImage.IsFileCreated(true)){
                message = loImage.getMessage();
                return false;
            } else {
                if(loLrt.HasLocation()){
                    args[0] = loImage.getFilePath();
                    args[1] = loImage.getFileName();
                    args[2] = loLrt.getLatitude();
                    args[3] = loLrt.getLongitude();
                    loIntent = loImage.getCameraIntent();
                    return true;
                } else {
                    args[0] = loImage.getFilePath();
                    args[1] = loImage.getFileName();
                    args[2] = loLrt.getLatitude();
                    args[3] = loLrt.getLongitude();
                    loIntent = loImage.getCameraIntent();
                    message = loLrt.getMessage();
                    return false;
                }
            }
        }

        @Override
        protected void onPostExecute(Boolean isSuccess) {
            super.onPostExecute(isSuccess);
            if(isSuccess){
                callback.OnSuccess(loIntent, args);
            } else {
                callback.OnFailed(message, loIntent, args);
            }
        }
    }

    public void SaveTransaction(LoanUnit foVal, ViewModelCallback callback){
        new SaveTransactionTask(callback).execute(foVal);
    }

    private class SaveTransactionTask extends AsyncTask<LoanUnit, Void, Boolean>{

        private final ViewModelCallback callback;

        private String message;

        public SaveTransactionTask(ViewModelCallback callback) {
            this.callback = callback;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(LoanUnit... obj) {
            if(!poSys.SaveLoanUnit(obj[0])){
                message = poSys.getMessage();
                return false;
            }

            return true;
        }

        @Override
        protected void onPostExecute(Boolean isSuccess) {
            super.onPostExecute(isSuccess);
            if(!isSuccess){
                callback.OnFailedResult(message);
            } else {
                callback.OnSuccessResult();
            }
        }
    }

    public LiveData<List<DTownInfo.TownProvinceInfo>> getTownProvinceInfo(){
        return poTown.getTownProvinceInfo();
    }

    public LiveData<String[]> getBarangayNameList(String fsVal){
        return Brgy.getBarangayNamesFromTown(fsVal);
    }

    public LiveData<List<EBarangayInfo>> getBarangayInfoList(String fsVal){
        return Brgy.getAllBarangayFromTown(fsVal);
    }


    public LiveData<ArrayAdapter<String>> getSpnCivilStats(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplication(), android.R.layout.simple_spinner_dropdown_item, DCP_Constants.CIVIL_STATUS)
        {
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
                    ((TextView) v).setTextColor(getApplication().getResources().getColorStateList(R.color.textColor_White));
                }else {
                    ((TextView) v).setTextColor(getApplication().getResources().getColorStateList(R.color.textColor_Black));
                }
                return v;
            }
        };
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(adapter);
        return liveData;
    }

    public String getRealPathFromURI (String contentUri) {
        File target = new File(contentUri);
        if (target.exists() && target.isFile() && target.canWrite()) {
            target.delete();
            Log.d("d_file", "" + target.getName());
        }
        return target.toString();
    }
}