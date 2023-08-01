/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 5/17/21 9:21 AM
 * project file last modified : 5/17/21 9:21 AM
 */

package org.rmj.g3appdriver.GCircle.room.Repositories;

import android.app.Application;

import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DNNDMRequest;
import org.rmj.g3appdriver.GCircle.room.Entities.ENNDMRequest;
import org.rmj.g3appdriver.GCircle.room.GGC_GCircleDB;

public class RNNDMRequest implements DNNDMRequest {
    private static final String TAG = RNNDMRequest.class.getSimpleName();
    private final Application instance;
    private final DNNDMRequest requestDao;

    public RNNDMRequest(Application application) {
        this.instance = application;
        this.requestDao = GGC_GCircleDB.getInstance(instance).nndmRequestDao();
    }

    @Override
    public void insert(ENNDMRequest enndmRequest) {
        requestDao.insert(enndmRequest);
    }
}
