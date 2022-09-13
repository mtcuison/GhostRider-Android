/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 8/13/21 10:35 AM
 * project file last modified : 8/13/21 10:35 AM
 */

package org.rmj.g3appdriver.GRider.Database.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.rmj.appdriver.base.GConnection;
import org.rmj.apprdiver.util.MiscUtil;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DEmployeeBusinessTrip;
import org.rmj.g3appdriver.GRider.Database.DbConnection;
import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeBusinessTrip;
import org.rmj.g3appdriver.GRider.Database.GGC_GriderDB;

import java.util.List;

public class REmployeeBusinessTrip implements DEmployeeBusinessTrip {
    private final DEmployeeBusinessTrip employeeBusinessTripDao;
//    private final LiveData<List<EEmployeeBusinessTrip>> employeeOBList;

    private final Application app;

    public REmployeeBusinessTrip(Application application){
        this.app = application;
        GGC_GriderDB GGCGriderDB = GGC_GriderDB.getInstance(application);
        employeeBusinessTripDao = GGCGriderDB.employeeOBDao();
//        employeeOBList = employeeBusinessTripDao.get();
    }

    @Override
    public void insert(EEmployeeBusinessTrip obLeave) {
        employeeBusinessTripDao.insert(obLeave);
    }

    @Override
    public void update(EEmployeeBusinessTrip obLeave) {
        employeeBusinessTripDao.update(obLeave);
    }

    @Override
    public void insertNewOBLeave(EEmployeeBusinessTrip obLeave) {
        employeeBusinessTripDao.insertNewOBLeave(obLeave);
    }

    @Override
    public List<EEmployeeBusinessTrip> getOBIfExist(String TransNox) {
        return employeeBusinessTripDao.getOBIfExist(TransNox);
    }

    @Override
    public LiveData<EEmployeeBusinessTrip> getBusinessTripInfo(String TransNox) {
        return employeeBusinessTripDao.getBusinessTripInfo(TransNox);
    }

    @Override
    public void updateOBSentStatus(String TransNox, String newTransNox) {
        employeeBusinessTripDao.updateOBSentStatus(TransNox, newTransNox);
    }

    @Override
    public void updateOBApproval(String TranStat, String TransNox, String DateSent) {
        employeeBusinessTripDao.updateOBApproval(TranStat, TransNox, DateSent);
    }

    @Override
    public LiveData<List<EEmployeeBusinessTrip>> getOBListForApproval() {
        return employeeBusinessTripDao.getOBListForApproval();
    }

    @Override
    public LiveData<List<EEmployeeBusinessTrip>> getOBList() {
        return employeeBusinessTripDao.getOBList();
    }

    @Override
    public List<EEmployeeBusinessTrip> getUnsentEmployeeOB() {
        return employeeBusinessTripDao.getUnsentEmployeeOB();
    }

    @Override
    public LiveData<List<EEmployeeBusinessTrip>> GetApproveBusTrip() {
        return employeeBusinessTripDao.GetApproveBusTrip();
    }

    public String getOBLeaveNextCode(){
        String lsTransNox = "";
        GConnection loConn = DbConnection.doConnect(app);
        try{
            lsTransNox = MiscUtil.getNextCode("Employee_Business_Trip", "sTransNox", true, loConn.getConnection(), "", 12, false);

        } catch (Exception e){
            e.printStackTrace();
        }
        loConn = null;
        return lsTransNox;
    }

}
