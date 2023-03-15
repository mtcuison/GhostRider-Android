package org.rmj.guanzongroup.ghostrider.notifications.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.lib.Notifications.Obj.Payslip;

public class VMNotifications extends AndroidViewModel {
    private static final String TAG = VMNotifications.class.getSimpleName();

    private final Payslip poPaySlip;

    public VMNotifications(@NonNull Application application) {
        super(application);
        this.poPaySlip = new Payslip(application);
    }

    public LiveData<Integer> GetUnreadPayslipCount(){
        return poPaySlip.GetUnreadPayslipCount();
    }
}
