/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 6/19/21 10:51 AM
 * project file last modified : 6/19/21 10:51 AM
 */

package org.rmj.g3appdriver.GRider.Database.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.rmj.appdriver.base.GConnection;
import org.rmj.apprdiver.util.MiscUtil;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DEmployeeLeave;
import org.rmj.g3appdriver.GRider.Database.DbConnection;
import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeLeave;
import org.rmj.g3appdriver.GRider.Database.GGC_GriderDB;

import java.util.List;

public class REmployeeLeave implements DEmployeeLeave {
    private final DEmployeeLeave employeeDao;
    private final Application instance;

    public REmployeeLeave(Application application) {
        this.instance = application;
        this.employeeDao = GGC_GriderDB.getInstance(instance).employeeLeaveDao();
    }

    @Override
    public void insertApplication(EEmployeeLeave poLeave) {
        employeeDao.insertApplication(poLeave);
    }

    @Override
    public void updateApplication(EEmployeeLeave poLeave) {
        employeeDao.updateApplication(poLeave);
    }

    @Override
    public List<EEmployeeLeave> getTransnoxIfExist(String TransNox) {
        return employeeDao.getTransnoxIfExist(TransNox);
    }


    @Override
    public void deleteApplication(String TransNox) {
        employeeDao.deleteApplication(TransNox);
    }

    @Override
    public LiveData<EEmployeeLeave> getEmployeeLeaveInfo(String TransNox) {
        return employeeDao.getEmployeeLeaveInfo(TransNox);
    }

    @Override
    public void updateSendStatus(String DateSent, String TransNox, String newTransNox) {
        employeeDao.updateSendStatus(DateSent, TransNox, newTransNox);
    }

    @Override
    public LiveData<List<LeaveOBApplication>> getAllLeaveOBApplication() {
        return employeeDao.getAllLeaveOBApplication();
    }

    public String getNextLeaveCode(){
        String lsNextCode = "";
        try{
            GConnection loConn = DbConnection.doConnect(instance);
            lsNextCode = MiscUtil.getNextCode("Employee_Leave", "sTransNox", true, loConn.getConnection(), "", 12, false);
        } catch (Exception e){
            e.printStackTrace();
        }
        return lsNextCode;
    }
}
