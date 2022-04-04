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
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.appdriver.base.GConnection;
import org.rmj.apprdiver.util.MiscUtil;
import org.rmj.apprdiver.util.SQLUtil;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DCreditApplicantInfo;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DEmployeeBusinessTrip;
import org.rmj.g3appdriver.GRider.Database.DbConnection;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeBusinessTrip;
import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeLeave;
import org.rmj.g3appdriver.GRider.Database.GGC_GriderDB;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.utils.GenerateID;

import java.sql.ResultSet;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Random;

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
    public void delete(EEmployeeBusinessTrip obLeave) {
        employeeBusinessTripDao.delete(obLeave);
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
    public EEmployeeBusinessTrip getOBApplicationInfo(String TransNo) {
        return employeeBusinessTripDao.getOBApplicationInfo(TransNo);
    }

    @Override
    public void updateOBSentStatus(String TransNox) {
        employeeBusinessTripDao.updateOBSentStatus(TransNox);
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
    public EEmployeeBusinessTrip getBusinessTripForPosting(String TransNox) {
        return employeeBusinessTripDao.getBusinessTripForPosting(TransNox);
    }

    @Override
    public List<EEmployeeBusinessTrip> getOBListForUpload() {
        return employeeBusinessTripDao.getOBListForUpload();
    }

    public String getOBLeaveNextCode(){
        if(AppConfigPreference.getInstance(app).getTestStatus()){
            return new GenerateID(12).nextString();
        } else {
            String lsTransNox = "";
            GConnection loConn = DbConnection.doConnect(app);
            try {
                lsTransNox = MiscUtil.getNextCode("Employee_Business_Trip", "sTransNox", true, loConn.getConnection(), "", 12, false);

            } catch (Exception e) {
                e.printStackTrace();
            }
            loConn = null;
            return lsTransNox;
        }
    }

}
