/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.settings
 * Electronic Personnel Access Control Security System
 * project file created : 8/14/21 11:33 AM
 * project file last modified : 8/14/21 11:33 AM
 */

package org.rmj.guanzongroup.ghostrider.settings.ViewModel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.dev.Database.GCircle.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.dev.DevTools;

public class VMDevMode extends AndroidViewModel {

    private final Application instance;
    private final DevTools poTool;

    public interface OnChangeListener {
        void OnChanged(String args);
    }

    public VMDevMode(@NonNull Application application) {
        super(application);
        this.instance = application;
        this.poTool = new DevTools(instance);
    }

    public LiveData<EEmployeeInfo> GetUserInfo(){
        return poTool.GetUserInfo();
    }

    public void SaveChanges(EEmployeeInfo args, OnChangeListener callback){
        new SaveChangesTask(callback).execute(args);
    }

    private class SaveChangesTask extends AsyncTask<EEmployeeInfo, Void, Boolean>{

        private final OnChangeListener callback;

        private String message;

        public SaveChangesTask(OnChangeListener callback) {
            this.callback = callback;
        }

        @Override
        protected Boolean doInBackground(EEmployeeInfo... eEmployeeInfos) {
            if(!poTool.Update(eEmployeeInfos[0])){
                message = poTool.getMessage();
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean isSuccess) {
            super.onPostExecute(isSuccess);
            if(!isSuccess){
                callback.OnChanged(message);
            } else {
                callback.OnChanged("Changes Saved!");
            }
        }
    }

    public void RestoreDefault(OnChangeListener callback){
        new RestoreSessionInfoTask(callback).execute();
    }

    private class RestoreSessionInfoTask extends AsyncTask<Void, Void, Boolean>{

        private final OnChangeListener callback;

        private String message;

        public RestoreSessionInfoTask(OnChangeListener callback){
            this.callback = callback;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            if(!poTool.SetDefault()){
                message = poTool.getMessage();
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean isSuccess) {
            super.onPostExecute(isSuccess);
            if(!isSuccess){
                callback.OnChanged(message);
            } else {
                callback.OnChanged("Account restored to default.");
            }
        }
    }
}
