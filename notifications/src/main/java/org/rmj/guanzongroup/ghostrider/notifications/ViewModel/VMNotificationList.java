/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.notifications
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.notifications.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.rmj.guanzongroup.ghostrider.notifications.Object.NotificationItemList;

import java.util.ArrayList;
import java.util.List;

public class VMNotificationList extends AndroidViewModel {

    private MutableLiveData<List<NotificationItemList>> plMessage = new MutableLiveData<>();

    public VMNotificationList(@NonNull Application application) {
        super(application);

        List<NotificationItemList> messages = new ArrayList<>();
        NotificationItemList message = new NotificationItemList();
        message.setName("Guanzon Group");
        message.setTitle("Employee Timesheet Report");
        message.setMessage("Sample Employee Timesheet report");
        message.setDateTime("Dec 3");
        messages.add(message);

        NotificationItemList message1 = new NotificationItemList();
        message1.setName("HCM");
        message1.setTitle("PAGIBIG LOYALTY CARD");
        message1.setMessage("Magandang Umaga" +
                "Para sa PAGIBIG Loyalty Card Plus application/ registration, (for new members & updating for old members) tignan po ang InterOffice Memo para sa GK sched.\n" +
                "Maraming salamat");
        message1.setDateTime("Nov 11");
        messages.add(message1);

        NotificationItemList message2 = new NotificationItemList();
        message2.setName("HCM");
        message2.setTitle("PAGIBIG LOYALTY CARD");
        message2.setMessage("Good day!\n" +
                "HDMF/Pag-Ibig in coordination with HCM is accepting applications for those who would like to avail of the new Loyalty Card Plus.Please fill up and send a hard copy of the application form together with your valid ID to Ms. Shirley Maynigo at HCM, GK bldg. before November 5, 2020.\n" +
                "For interested applicants, please prepare P125 to be paid on the actual date of registration.\n" +
                "\n" +
                "The new Pag-Ibig loyalty card plus has a built-in EMV chip. You can use it as a cash card where you may conveniently receive your Pag-Ibig Multi-Purpose Loan (MPL) proceeds, MP2 saving dividends, and other benefits.");
        message2.setDateTime("Oct 30");
        messages.add(message2);

        NotificationItemList message3 = new NotificationItemList();
        message3.setName("Guanzon Group");
        message3.setTitle("Employee Timesheet Report");
        message3.setMessage("Sample Employee Timesheet report");
        message3.setDateTime("Nov 3");
        messages.add(message3);

        plMessage.setValue(messages);
    }

    public LiveData<List<NotificationItemList>> getMessageList(){
        return plMessage;
    }
}