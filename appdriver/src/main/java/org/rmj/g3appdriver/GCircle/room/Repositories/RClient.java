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

package org.rmj.g3appdriver.GCircle.room.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DClientInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.EClientInfo;
import org.rmj.g3appdriver.GCircle.room.GGC_GCircleDB;

public class RClient {
    private DClientInfo clientDao;
    private LiveData<EClientInfo> clientInfo;

    public RClient(Application application){
        GGC_GCircleDB database = GGC_GCircleDB.getInstance(application);
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
