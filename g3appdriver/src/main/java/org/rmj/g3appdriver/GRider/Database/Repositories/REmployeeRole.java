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

import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DEmployeeRole;
import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeRole;
import org.rmj.g3appdriver.GRider.Database.GGC_GriderDB;

import java.util.List;

public class REmployeeRole implements DEmployeeRole {
    private static final String TAG = "REmployeeRole";
    private final DEmployeeRole roleDao;

    public REmployeeRole(Application application){
        this.roleDao = GGC_GriderDB.getInstance(application).employeeRoleDao();
    }

    @Override
    public void InsertEmployeeRole(EEmployeeRole role) {
        roleDao.InsertEmployeeRole(role);
    }

    @Override
    public void DeleteEmployeeRole(String Product, String UserID, String Object) {
        roleDao.DeleteEmployeeRole(Product, UserID, Object);
    }

    @Override
    public LiveData<List<EEmployeeRole>> getEmployeeRoles() {
        return roleDao.getEmployeeRoles();
    }

    @Override
    public LiveData<List<EEmployeeRole>> getChildRoles() {
        return roleDao.getChildRoles();
    }
}
