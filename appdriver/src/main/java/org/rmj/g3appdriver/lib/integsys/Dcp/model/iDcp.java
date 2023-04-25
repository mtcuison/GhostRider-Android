package org.rmj.g3appdriver.lib.integsys.Dcp.model;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.dev.Database.DataAccessObject.DEmployeeInfo;
import org.rmj.g3appdriver.dev.Database.Entities.EDCPCollectionDetail;

public interface iDcp {
    LiveData<DEmployeeInfo.EmployeeBranch> GetEmployeeInfo();
    LiveData<EDCPCollectionDetail> GetCollectionDetail(String fsVal);
    boolean SaveMobileNo();
    boolean SaveAddress();
    boolean SaveClient();
    boolean SaveCollection(Object args);
}
