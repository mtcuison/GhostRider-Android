/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.app
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */

package org.rmj.guanzongroup.ghostrider.epacss.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import org.rmj.guanzongroup.ghostrider.epacss.Service.InternetStatusReciever;

public class VMMainActivity extends AndroidViewModel {
    private static final String TAG = "GRider Main Activity";
    private final Application app;
    private final InternetStatusReciever poNetRecvr;

    public VMMainActivity(@NonNull Application application) {
        super(application);
        this.app = application;
        this.poNetRecvr = new InternetStatusReciever(app);
    }

    public InternetStatusReciever getInternetReceiver(){
        return poNetRecvr;
    }
}
