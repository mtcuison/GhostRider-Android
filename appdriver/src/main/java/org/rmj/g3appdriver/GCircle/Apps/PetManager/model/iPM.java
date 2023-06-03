package org.rmj.g3appdriver.GCircle.Apps.PetManager.model;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DEmployeeInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.EEmployeeBusinessTrip;
import org.rmj.g3appdriver.GCircle.room.Entities.EEmployeeLeave;

import java.util.List;

public interface iPM {
    LiveData<DEmployeeInfo.EmployeeBranch> GetUserInfo();
    boolean ImportApplications();
    boolean DownloadApplication(String args);

    String SaveApplication(Object foVal);
    boolean UploadApplication(String args);
    String SaveApproval(Object foVal);
    boolean UploadApproval(Object args);
    boolean UploadApplications();
    boolean UploadApprovals();
    LiveData<List<EEmployeeLeave>> GetLeaveApplicationList();
    LiveData<List<EEmployeeLeave>> GetLeaveApplicationsForApproval();
    LiveData<List<EEmployeeLeave>> GetApproveLeaveApplications();
    LiveData<EEmployeeLeave> GetLeaveApplicationInfo(String args);
    LiveData<List<EEmployeeBusinessTrip>> GetOBApplicationList();
    LiveData<List<EEmployeeBusinessTrip>> GetOBApplicationsForApproval();
    LiveData<EEmployeeBusinessTrip> GetOBApplicationInfo(String args);
    LiveData<List<EEmployeeBusinessTrip>> GetApproveOBApplications();
    String getMessage();
}
