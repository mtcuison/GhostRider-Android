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

import org.rmj.guanzongroup.ghostrider.notifications.Object.MessageItemList;

import java.util.ArrayList;
import java.util.List;

public class VMMessageList extends AndroidViewModel {

    private final MutableLiveData<List<MessageItemList>> plMessage = new MutableLiveData<>();

    public VMMessageList(@NonNull Application application) {
        super(application);

        List<MessageItemList> messages = new ArrayList<>();
        messages.add(new MessageItemList("Sir Jovan", "Matinik ka talaga!!!", "Dec 15", "read"));
        messages.add(new MessageItemList("Sir kent", "Matinik ka talaga!!!", "Dec 15", "read"));
        messages.add(new MessageItemList("Sir John Carlo", "Good morning sir, pa access ng pc namin ayaw mag open ng system. Runtime error", "Dec 15", "read"));
        messages.add(new MessageItemList("Sir Pogi", "Sir troubleshoot pc ko walang internet.", "Dec 15", "read"));
        plMessage.setValue(messages);
    }

    public LiveData<List<MessageItemList>> getMessageList(){
        return plMessage;
    }
}