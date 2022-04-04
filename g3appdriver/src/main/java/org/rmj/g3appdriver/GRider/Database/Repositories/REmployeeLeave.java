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
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.appdriver.base.GConnection;
import org.rmj.apprdiver.util.MiscUtil;
import org.rmj.apprdiver.util.SQLUtil;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DEmployeeLeave;
import org.rmj.g3appdriver.GRider.Database.DbConnection;
import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeLeave;
import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeRole;
import org.rmj.g3appdriver.GRider.Database.GGC_GriderDB;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.utils.GenerateID;

import java.sql.ResultSet;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Random;

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
    public List<EEmployeeLeave> getTransnoxIfExist(String TransNox, String TranStat) {
        return employeeDao.getTransnoxIfExist(TransNox, TranStat);
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
    public void updateLeaveApproval(String TranStat, String TransNox, String DateSent) {
        employeeDao.updateLeaveApproval(TranStat, TransNox, DateSent);
    }

    @Override
    public LiveData<List<LeaveOBApplication>> getAllLeaveOBApplication() {
        return employeeDao.getAllLeaveOBApplication();
    }

    @Override
    public LiveData<List<EEmployeeLeave>> getEmployeeLeaveForApprovalList() {
        return employeeDao.getEmployeeLeaveForApprovalList();
    }

    @Override
    public LiveData<List<EEmployeeLeave>> getEmployeeLeaveList() {
        return employeeDao.getEmployeeLeaveList();
    }

    @Override
    public EEmployeeLeave getLeaveForPosting(String TransNox) {
        return employeeDao.getLeaveForPosting(TransNox);
    }

    @Override
    public EEmployeeLeave getLeaveForApproval(String TransNox) {
        return employeeDao.getLeaveForApproval(TransNox);
    }

    @Override
    public List<EEmployeeLeave> getUnsentEmployeeLeave() {
        return employeeDao.getUnsentEmployeeLeave();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String getNextLeaveCode(){
        if(AppConfigPreference.getInstance(instance).getTestStatus()){
            return new GenerateID(12).nextString();
        } else {
            String lsNextCode = "";
            GConnection loConn = DbConnection.doConnect(instance);
            lsNextCode = MiscUtil.getNextCode("Employee_Leave", "sTransNox", true, loConn.getConnection(), "", 12, false);

            return lsNextCode;
        }
    }
}
