package org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GCircle.room.Entities.ECashCount;
import org.rmj.g3appdriver.GCircle.room.Repositories.RCashCount;
import org.rmj.g3appdriver.lib.Branch.Branch;

public class VMCashCountLogDetails extends AndroidViewModel {
    private static final String TAG = VMCashCountLogDetails.class.getSimpleName();
    private final RCashCount poCashCnt;
    private final Branch poBranchx;

    public VMCashCountLogDetails(@NonNull Application application) {
        super(application);
        this.poCashCnt = new RCashCount(application);
        this.poBranchx = new Branch(application);
    }

    public LiveData<String> getBranchName(String sBranchCd) {
        return poBranchx.getBranchName(sBranchCd);
    }

    public LiveData<ECashCount> getCashCounDetetail(String fsTransNo) {
        return poCashCnt.getCashCounDetetail(fsTransNo);
    }

}
