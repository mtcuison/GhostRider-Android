package org.rmj.g3appdriver.GRider.Database.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GRider.Database.AppDatabase;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DEmployeeInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.etc.SessionManager;

public class REmployee {
    private final DEmployeeInfo employeeDao;
    private final LiveData<EEmployeeInfo> employeeInfo;
    private final SessionManager poSession;


    public REmployee(Application application){
        AppDatabase database = AppDatabase.getInstance(application);
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
