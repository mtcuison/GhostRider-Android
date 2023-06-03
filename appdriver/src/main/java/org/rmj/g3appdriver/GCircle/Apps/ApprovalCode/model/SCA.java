package org.rmj.g3appdriver.GCircle.Apps.ApprovalCode.model;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GCircle.room.Entities.EBranchInfo;
import org.rmj.g3appdriver.GCircle.Apps.ApprovalCode.pojo.CreditAppInfo;

import java.util.List;

public interface SCA {
    LiveData<List<EBranchInfo>> GetBranchList();
    LiveData<String> GetApprovalCodeDescription(String args);
    String GenerateCode(Object foArgs);
    CreditAppInfo LoadApplication(String args, String args1, String args2);
    boolean Upload(String args);
    String getMessage();
}
