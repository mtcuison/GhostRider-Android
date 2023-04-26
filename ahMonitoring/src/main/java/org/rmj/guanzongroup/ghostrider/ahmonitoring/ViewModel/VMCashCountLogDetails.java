package org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.dev.Database.GCircle.Entities.ECashCount;
import org.rmj.g3appdriver.dev.Database.GCircle.Repositories.RBranch;
import org.rmj.g3appdriver.dev.Database.GCircle.Repositories.RCashCount;

public class VMCashCountLogDetails extends AndroidViewModel {
    private static final String TAG = VMCashCountLogDetails.class.getSimpleName();
    private final RCashCount poCashCnt;
    private final RBranch poBranchx;

    public VMCashCountLogDetails(@NonNull Application application) {
        super(application);
        this.poCashCnt = new RCashCount(application);
        this.poBranchx = new RBranch(application);
    }

    public LiveData<String> getBranchName(String sBranchCd) {
        return poBranchx.getBranchName(sBranchCd);
    }

    public LiveData<ECashCount> getCashCounDetetail(String fsTransNo) {
        return poCashCnt.getCashCounDetetail(fsTransNo);
    }

}
