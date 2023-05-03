package org.rmj.g3appdriver.GCircle.Apps.integsys.Dcp.model;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DEmployeeInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.EDCPCollectionDetail;

public interface iDcp {
    LiveData<DEmployeeInfo.EmployeeBranch> GetEmployeeInfo();
    LiveData<EDCPCollectionDetail> GetCollectionDetail(String fsVal);
    boolean SaveMobileNo();
    boolean SaveAddress();
    boolean SaveClient();
    boolean SaveCollection(Object args);
}
