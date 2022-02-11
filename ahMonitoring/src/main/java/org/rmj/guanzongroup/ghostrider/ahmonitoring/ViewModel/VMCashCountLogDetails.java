package org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GRider.Database.Entities.ECashCount;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCashCount;

public class VMCashCountLogDetails extends AndroidViewModel {
    private static final String TAG = VMCashCountLogDetails.class.getSimpleName();
    private final RCashCount poCashCnt;

    public VMCashCountLogDetails(@NonNull Application application) {
        super(application);
        this.poCashCnt = new RCashCount(application);
    }

    public LiveData<ECashCount> getCashCounDetetail(String fsTransNo) {
        return poCashCnt.getCashCounDetetail(fsTransNo);
    }

}
