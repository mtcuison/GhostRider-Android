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

import java.sql.ResultSet;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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

//    public boolean saveEmployeeLeaveInfo(JSONArray foJson) throws Exception{
//        GConnection loConn = DbConnection.doConnect(instance);
//        boolean result = true;
//        if (loConn == null){
//            result = false;
//        }
//
//        JSONObject loJson;
//        String lsSQL;
//        ResultSet loRS;
//
//        for(int x = 0; x < foJson.length(); x++){
//            loJson = foJson.getJSONObject(x);
//
//            //check if record already exists on database
//            lsSQL = "SELECT dTimeStmp FROM Employee_Leave" +
//                    " WHERE sTransNox = " + SQLUtil.toSQL((String) loJson.get("sTransNox"));
//            loRS = Objects.requireNonNull(loConn).executeQuery(lsSQL);
//
//            lsSQL = "";
//            //record does not exists
//            if (!loRS.next()){
//                EEmployeeLeave leave = new EEmployeeLeave();
//                leave.setTransNox("sTransNox");
//                leave.setTransact("dTransact");
//                leave.setEmployID("xEmployee");
//                leave.setBranchNm("sBranchNm");
//                leave.setDeptName("sDeptName");
//                leave.setPositnNm("sPositnNm");
//                leave.setAppldFrx("dAppldFrx");
//                leave.setAppldTox("dAppldTox");
//                leave.setNoDaysxx("nNoDaysxx");
//                leave.setPurposex("sPurposex");
//                leave.setLeaveTyp("cLeaveTyp");
//                leave.setLveCredt("nLveCredt");
//                leave.setTranStat("cTranStat");
//                employeeDao.insertApplication(leave);
//            } else { //record already exists
//                Date ldDate1 = SQLUtil.toDate(loRS.getString("dTimeStmp"), SQLUtil.FORMAT_TIMESTAMP);
//                Date ldDate2 = SQLUtil.toDate((String) loJson.get("dTimeStmp"), SQLUtil.FORMAT_TIMESTAMP);
//
//                //compare date if the record from API is newer than the database record
//                if (!ldDate1.equals(ldDate2)){
//                    //create update statement
//                    employeeDao.updateLeaveInfo(
//                            loJson.getString("sTransNox"),
//                            loJson.getString("dTransact"),
//                            loJson.getString("xEmployee"),
//                            loJson.getString("sBranchNm"),
//                            loJson.getString("sDeptName"),
//                            loJson.getString("sPositnNm"),
//                            loJson.getString("dAppldFrx"),
//                            loJson.getString("dAppldTox"),
//                            loJson.getString("nNoDaysxx"),
//                            loJson.getString("sPurposex"),
//                            loJson.getString("cLeaveTyp"),
//                            loJson.getString("nLveCredt"),
//                            loJson.getString("cTranStat"));
//                }
//            }
//        }
//
//        //terminate object connection
//        loConn = null;
//        return result;
//    }
}
