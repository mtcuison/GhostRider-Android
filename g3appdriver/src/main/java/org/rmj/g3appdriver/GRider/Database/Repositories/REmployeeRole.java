/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 9/2/21, 11:08 AM
 * project file last modified : 9/2/21, 11:08 AM
 */

package org.rmj.g3appdriver.GRider.Database.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.appdriver.base.GConnection;
import org.rmj.apprdiver.util.SQLUtil;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DEmployeeRole;
import org.rmj.g3appdriver.GRider.Database.DbConnection;
import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeRole;
import org.rmj.g3appdriver.GRider.Database.GGC_GriderDB;

import java.sql.ResultSet;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class REmployeeRole implements DEmployeeRole {
    private static final String TAG = "REmployeeRole";
    private final Application application;
    private final DEmployeeRole roleDao;

    public REmployeeRole(Application application){
        this.application = application;
        this.roleDao = GGC_GriderDB.getInstance(application).employeeRoleDao();
    }

    @Override
    public void InsertEmployeeRole(EEmployeeRole role) {
        roleDao.InsertEmployeeRole(role);
    }

    @Override
    public void DeleteEmployeeRole() {
        roleDao.DeleteEmployeeRole();
    }

    @Override
    public LiveData<List<EEmployeeRole>> getEmployeeRoles() {
        return roleDao.getEmployeeRoles();
    }

    @Override
    public LiveData<List<EEmployeeRole>> getChildRoles() {
        return roleDao.getChildRoles();
    }

    @Override
    public void updateEmployeeRole(String ObjectNm, String ObjectTP, String ObjectDs, String Parentxx, String HasChild, String SeqnceCD, String RecdStat, String TimeStmp) {

    }

    @Override
    public void clearEmployeeRole() {
        roleDao.clearEmployeeRole();
    }

    public boolean SaveEmployeeRole(JSONArray faJson) throws Exception{
        GConnection loConn = DbConnection.doConnect(application);
        boolean result = true;
        if (loConn == null){
            result = false;
        }

        JSONObject loJson;
        String lsSQL;
        ResultSet loRS;

        for(int x = 0; x < faJson.length(); x++){
            loJson = faJson.getJSONObject(x);

            //check if record already exists on database
            lsSQL = "SELECT dTimeStmp FROM xxxAOEmpRole" +
                    " WHERE sObjectNm = " + SQLUtil.toSQL((String) loJson.get("sObjectNm"));
            loRS = Objects.requireNonNull(loConn).executeQuery(lsSQL);

            lsSQL = "";
            //record does not exists
            if (!loRS.next()){
                //create insert statement
                EEmployeeRole loRole = new EEmployeeRole();
                loRole.setObjectNm(loJson.getString("sObjectNm"));
                loRole.setObjectDs(loJson.getString("sObjectDs"));
                loRole.setObjectTP(loJson.getString("cObjectTP"));
                loRole.setParentxx(loJson.getString("sParentxx"));
                loRole.setHasChild(loJson.getString("cHasChild"));
                loRole.setSeqnceCd(loJson.getString("sSeqnceCD"));
                loRole.setRecdStat(loJson.getString("cRecdStat"));
                loRole.setTimeStmp(loJson.getString("dTimeStmp"));
                roleDao.InsertEmployeeRole(loRole);
            } else { //record already exists
                Date ldDate1 = SQLUtil.toDate(loRS.getString("dTimeStmp"), SQLUtil.FORMAT_TIMESTAMP);
                Date ldDate2 = SQLUtil.toDate((String) loJson.get("dTimeStmp"), SQLUtil.FORMAT_TIMESTAMP);

                //compare date if the record from API is newer than the database record
                if (!ldDate1.equals(ldDate2)){
                    //create update statement
                    roleDao.updateEmployeeRole(loJson.getString("sObjectNm"),
                            loJson.getString("cObjectTP"),
                            loJson.getString("sObjectDs"),
                            loJson.getString("sParentxx"),
                            loJson.getString("cHasChild"),
                            loJson.getString("sSeqnceCD"),
                            loJson.getString("cRecdStat"),
                            loJson.getString("dTimeStmp"));

                }
            }
        }

        //terminate object connection
        loConn = null;
        return result;
    }
}
