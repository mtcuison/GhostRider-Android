package org.rmj.g3appdriver.lib.Notifications.Obj;

import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DMessages;
import org.rmj.g3appdriver.GCircle.room.GGC_GCircleDB;
import org.rmj.g3appdriver.lib.Notifications.NOTIFICATION_STATUS;
import org.rmj.g3appdriver.lib.Notifications.Obj.Receiver.NMM_Regular;

import java.util.List;

public class Message extends NMM_Regular {
    private static final String TAG = Message.class.getSimpleName();

    private final DMessages poDao;

    private String message;

    public Message(Application application) {
        super(application);
        this.poDao = GGC_GCircleDB.getInstance(application).messagesDao();
    }

    public LiveData<List<DMessages.MessageUsers>> GetUsersMessages(){
        return poDao.GetMessageUsers();
    }

    public LiveData<Integer> GetUnreadMessagesCount(){
        return poDao.GetUnreadMessagesCount();
    }

    /**
     *
     * @param args pass the sender user id
     * @return returns livedata containing string user name
     */
    public LiveData<String> GetSenderName(String args){
        return poDao.GetSenderName(args);
    }

    public LiveData<List<DMessages.UserMessages>> GetUserMessages(String args){
        return poDao.GetUserMessages(args);
    }

    /**
     *
     * @param args pass the user id of the sender
     * @return returns true if the operation is successful else false if didn't
     */
    public boolean SendResponse(String args){
        try{
            List<String> lsMsgIDs = poDao.GetUnreadMessagesID(args);

            if(lsMsgIDs == null){
                message = "No unread messages found.";
                return false;
            }

            if(lsMsgIDs.size() == 0){
                message = "No unread messages found.";
                return false;
            }

            for(int x = 0; x < lsMsgIDs.size(); x++){
                if(SendResponse(lsMsgIDs.get(x), NOTIFICATION_STATUS.READ) == null){
                    Log.e(TAG, super.getMessage());
                }
                Thread.sleep(1000);
            }

            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }

    @Override
    public String getMessage() {
        return message;
    }
}
