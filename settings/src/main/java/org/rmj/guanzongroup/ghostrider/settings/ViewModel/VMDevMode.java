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

import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployee;
import org.rmj.g3appdriver.GRider.Etc.SessionManager;
import org.rmj.g3appdriver.etc.AppConfigPreference;

public class VMDevMode extends AndroidViewModel {

    private final Application instance;
    private final SessionManager poSession;
    private final AppConfigPreference poConfig;

    public interface OnRestoreCallback{
        void OnRestore();
    }

    public VMDevMode(@NonNull Application application) {
        super(application);
        this.instance = application;
        this.poSession = new SessionManager(application);
        this.poConfig = AppConfigPreference.getInstance(application);
    }

    public void setEmployeeLevel(String val){
        poSession.setEmployeeLevel(val);
    }

    public void setDepartmentID(String val){
        poSession.setDepartment(val);
    }

    public void setDebugMode(boolean val){
        poConfig.setIsTesting(val);
    }

    public boolean getIsDebugMode(){
        return poConfig.isTesting_Phase();
    }

    public String getDepartment(){
        return poSession.getDeptID();
    }

    public String getEmployeeLevel(){
        return poSession.getEmployeeLevel();
    }

    public void RestoreDefault(OnRestoreCallback callback){
        new RestoreSessionInfoTask(instance, callback).execute();
    }

    private static class RestoreSessionInfoTask extends AsyncTask<Void, Void, Void>{

        private final SessionManager poSession;
        private final AppConfigPreference poConfig;
        private final REmployee poEmployee;
        private final OnRestoreCallback callback;

        public RestoreSessionInfoTask(Application application, OnRestoreCallback callback){
            poSession = new SessionManager(application);
            poConfig = AppConfigPreference.getInstance(application);
            poEmployee = new REmployee(application);
            this.callback = callback;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            EEmployeeInfo loEmployee = poEmployee.getUserNonLiveData();
            poSession.setEmployeeLevel(loEmployee.getEmpLevID());
            poSession.setDepartment(loEmployee.getDeptIDxx());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            callback.OnRestore();
            super.onPostExecute(aVoid);
        }
    }
}
