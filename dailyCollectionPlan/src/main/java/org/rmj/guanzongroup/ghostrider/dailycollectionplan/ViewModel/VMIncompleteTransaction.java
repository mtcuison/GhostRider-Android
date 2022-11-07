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

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.dev.Database.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.etc.AppConstants;
import org.rmj.g3appdriver.etc.LocationRetriever;
import org.rmj.g3appdriver.lib.integsys.Dcp.LRDcp;
import org.rmj.g3appdriver.lib.integsys.Dcp.model.OtherRemCode;
import org.rmj.guanzongroup.ghostrider.imgcapture.ImageFileCreator;

public class VMIncompleteTransaction extends AndroidViewModel {
    private static final String TAG = VMIncompleteTransaction.class.getSimpleName();

    private final Application instance;

    private final LRDcp poSys;

    public VMIncompleteTransaction(@NonNull Application application) {
        super(application);
        this.instance = application;
        this.poSys = new LRDcp(application);
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

    public void SaveTransaction(OtherRemCode foVal, ViewModelCallback callback){
        new SaveTransactionTask(callback).execute(foVal);
    }

    private class SaveTransactionTask extends AsyncTask<OtherRemCode, Void, Boolean>{

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
        protected Boolean doInBackground(OtherRemCode... obj) {
            if(!poSys.SaveOtherRemCode(obj[0])){
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
}