package org.rmj.g3appdriver.GCircle.Apps.integsys.Dcp.obj;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GCircle.Apps.integsys.Dcp.model.iDcp;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DEmployeeInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.EDCPCollectionDetail;

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
