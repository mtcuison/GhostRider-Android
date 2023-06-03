package org.rmj.guanzongroup.ghostrider.notifications.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DMessages;
import org.rmj.g3appdriver.lib.Notifications.Obj.Message;
import org.rmj.g3appdriver.utils.ConnectionUtil;

import java.util.List;

public class VMMessageUsers extends AndroidViewModel {
    private static final String TAG = VMMessageUsers.class.getSimpleName();

    private final Message poSys;
    private final ConnectionUtil poConn;

    public VMMessageUsers(@NonNull Application application) {
        super(application);
        this.poSys = new Message(application);
        this.poConn = new ConnectionUtil(application);
    }

    public LiveData<List<DMessages.MessageUsers>> GetUsersMessages(){
        return poSys.GetUsersMessages();
    }

    public LiveData<List<DMessages.UserMessages>> GetUserMessages(String args){
        return poSys.GetUserMessages(args);
    }
}
