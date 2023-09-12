package org.rmj.guanzongroup.ghostrider.notifications.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.lib.Notifications.obj.Message;
import org.rmj.g3appdriver.lib.Notifications.obj.Notification;
import org.rmj.g3appdriver.lib.Notifications.obj.Payslip;

public class VMNotifications extends AndroidViewModel {
    private static final String TAG = VMNotifications.class.getSimpleName();

    private final Payslip poPaySlip;
    private final Message poMessage;
    private final Notification poNotification;

    public VMNotifications(@NonNull Application application) {
        super(application);
        this.poPaySlip = new Payslip(application);
        this.poMessage = new Message(application);
        this.poNotification = new Notification(application);
    }

    public LiveData<Integer> GetUnreadPayslipCount(){
        return poPaySlip.GetUnreadPayslipCount();
    }

    public LiveData<Integer> GetUnreadMessagesCount(){
        return poMessage.GetUnreadMessagesCount();
    }

    public LiveData<Integer> GetUnreadNotificationCount(){
        return poNotification.GetUnreadNotificationCount();
    }
}
