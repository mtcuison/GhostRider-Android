package org.rmj.g3appdriver.dev.Database.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DPayslip {

    @Query("SELECT b.sTransNox, " +
            "a.sMesgIDxx, " +
            "a.sMsgTitle, " +
            "a.sMessagex, " +
            "b.dReceived " +
            "FROM Notification_Info_Master a " +
            "LEFT JOIN Notification_Info_Recepient b ON a.sMesgIDxx = b.sTransNox " +
            "WHERE a.sMsgTypex = '00000' " +
            "AND a.sAppSrcex = 'IntegSys' " +
            "AND a.sMsgTitle LIKE 'PAYSLIP%' " +
            "AND b.sRecpntID = (SELECT sUserIDxx FROM User_Info_Master)")
    LiveData<List<Payslip>> GetPaySlipList();

    class Payslip{
        public String sTransNox;
        public String sMesgIDxx;
        public String sMsgTitle;
        public String sMessagex;
        public String dReceived;
    }
}
