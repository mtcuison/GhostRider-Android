/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.g3appdriver.GRider.Database.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GRider.Database.GGC_GriderDB;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DEmployeeInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.etc.SessionManager;

public class REmployee {
    private final DEmployeeInfo employeeDao;
    private final LiveData<EEmployeeInfo> employeeInfo;
    private final SessionManager poSession;


    public REmployee(Application application){
        GGC_GriderDB database = GGC_GriderDB.getInstance(application);
        employeeDao = database.EmployeeDao();
        employeeInfo = employeeDao.getEmployeeInfo();
        poSession = new SessionManager(application);
    }

    public LiveData<EEmployeeInfo> getUserInfo(){
        return employeeInfo;
    }

    public EEmployeeInfo getUserNonLiveData() { return employeeDao.getEmployeeInfoNonLiveData(); }

    public void insertEmployee(EEmployeeInfo employeeInfo){
        new InsertEmployeeTask(employeeDao).execute(employeeInfo);
    }

    public void updateEmployee(EEmployeeInfo employeeInfo){
        //TODO: Create asyncktask class that will update data to local database on background thread.
    }

    public void deleteEmployee(EEmployeeInfo employeeInfo){
        //TODO: Create asyncktask class that will delete data to local database on background thread.
    }

    public LiveData<EEmployeeInfo> getEmployeeInfo(){
        return employeeInfo;
    }

    public LiveData<String> getUserID(){
        return employeeDao.getUserID();
    }

    public LiveData<String> getClientID(){
        return employeeDao.getClientID();
    }
    public LiveData<String> getUserBranchID(){
        return employeeDao.getSBranchID();
    }

    public LiveData<String> getLogNumber(){
        return employeeDao.getLogNumber();
    }

    public LiveData<DEmployeeInfo.Session> getSessionTime(){
        return employeeDao.getSessionTime();
    }

    public LiveData<String> getSessionDate(){
        return employeeDao.getSessionDate();
    }

    private static class InsertEmployeeTask extends AsyncTask<EEmployeeInfo, Void, Void>{
        private DEmployeeInfo employeeDao;

        public InsertEmployeeTask(DEmployeeInfo employeeDao){
            this.employeeDao = employeeDao;
        }

        @Override
        protected Void doInBackground(EEmployeeInfo... eEmployeeInfos) {
            employeeDao.deleteAllEmployeeInfo();
            employeeDao.insert(eEmployeeInfos[0]);
            return null;
        }
    }

    public void LogoutUserSession(){
        poSession.initUserLogout();
        new DeleteUserTask(employeeDao).execute();
    }

    public static class DeleteUserTask extends AsyncTask<Void, Void, Void>{
        private DEmployeeInfo employeeDao;

        public DeleteUserTask(DEmployeeInfo employeeDao) {
            this.employeeDao = employeeDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            employeeDao.deleteAllEmployeeInfo();
            return null;
        }
    }
}
