/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.notifications
 * Electronic Personnel Access Control Security System
 * project file created : 5/22/21 11:36 AM
 * project file last modified : 5/22/21 11:36 AM
 */

package org.rmj.guanzongroup.ghostrider.notifications.Function;

import com.google.firebase.messaging.RemoteMessage;

public interface ANM_Factory {
    void SaveNotification(RemoteMessage foMessage);
}
