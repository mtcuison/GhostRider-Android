package org.rmj.g3appdriver.lib.ApprovalCode;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.dev.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.lib.ApprovalCode.model.CreditAppInfo;

import java.util.List;

public interface SCA {
    LiveData<List<EBranchInfo>> GetBranchList();
    LiveData<String> GetApprovalCodeDescription(String args);
    String GenerateCode(Object foArgs);
    CreditAppInfo LoadApplication(String args, String args1, String args2);
    boolean Upload(String args);
    String getMessage();
}
