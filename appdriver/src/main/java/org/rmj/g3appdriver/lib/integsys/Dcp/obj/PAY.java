package org.rmj.g3appdriver.lib.integsys.Dcp.obj;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.dev.Database.DataAccessObject.DEmployeeInfo;
import org.rmj.g3appdriver.dev.Database.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.lib.integsys.Dcp.iDcp;

public class PAY implements iDcp {
    private static final String TAG = PAY.class.getSimpleName();



    @Override
    public LiveData<DEmployeeInfo.EmployeeBranch> GetEmployeeInfo() {
        return null;
    }

    @Override
    public LiveData<EDCPCollectionDetail> GetCollectionDetail(String fsVal) {
        return null;
    }

    @Override
    public boolean SaveMobileNo() {
        return false;
    }

    @Override
    public boolean SaveAddress() {
        return false;
    }

    @Override
    public boolean SaveClient() {
        return false;
    }

    @Override
    public boolean SaveCollection(Object args) {
        return false;
    }
}
