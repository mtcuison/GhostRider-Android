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
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.json.JSONObject;
import org.rmj.g3appdriver.dev.Database.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.dev.DevTools;
import org.rmj.g3appdriver.dev.HttpHeaders;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.etc.AppConstants;
import org.rmj.g3appdriver.etc.SessionManager;
import org.rmj.g3appdriver.lib.Account.EmployeeMaster;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebApi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;

public class VMDevMode extends AndroidViewModel {

    private final Application instance;
    private final DevTools poTool;

    public interface OnRestoreCallback{
        void OnRestore();
    }

    public VMDevMode(@NonNull Application application) {
        super(application);
        this.instance = application;
        this.poTool = new DevTools(instance);
    }

    public LiveData<EEmployeeInfo> GetUserInfo(){
        return poTool.GetUserInfo();
    }

    public void SaveChanges(EEmployeeInfo args, OnRestoreCallback callback){

    }

    public void RestoreDefault(OnRestoreCallback callback){
        new RestoreSessionInfoTask(instance, callback).execute();
    }

    private static class RestoreSessionInfoTask extends AsyncTask<Void, Void, Void>{

        private final SessionManager poSession;
        private final AppConfigPreference poConfig;
        private final EmployeeMaster poEmployee;
        private final OnRestoreCallback callback;

        public RestoreSessionInfoTask(Application application, OnRestoreCallback callback){
            poSession = new SessionManager(application);
            poConfig = AppConfigPreference.getInstance(application);
            poEmployee = new EmployeeMaster(application);
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
