package org.rmj.g3appdriver.GRider.Database.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GRider.Database.AppDatabase;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DClientInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EClientInfo;

public class RClient {
    private DClientInfo clientDao;
    private LiveData<EClientInfo> clientInfo;

    public RClient(Application application){
        AppDatabase database = AppDatabase.getInstance(application);
        clientDao = database.ClientDao();
        clientInfo = clientDao.getClientInfo();
    }

    public void insertClient(EClientInfo clientInfo){
        //TODO: Create asyncktask class that will insert data to local database on background thread.
    }


    public void updateClient(EClientInfo clientInfo){
        //TODO: Create asyncktask class that will update data to local database on background thread.
    }


    public void deleteClient(EClientInfo clientInfo){
        //TODO: Create asyncktask class that will delete data to local database on background thread.
    }

    public LiveData<EClientInfo> getClientInfo(){
        return clientInfo;
    }

    public String getUserID(){
        return clientDao.getUserID();
    }
}
