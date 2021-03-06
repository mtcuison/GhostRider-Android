/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 5/21/21 9:53 AM
 * project file last modified : 5/21/21 9:53 AM
 */

package org.rmj.g3appdriver.GRider.Database.Repositories;

import android.app.Application;

import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DAppConfig;
import org.rmj.g3appdriver.GRider.Database.GGC_GriderDB;

public class RAppConfig {
    private static final String TAG = RAppConfig.class.getSimpleName();

    private final DAppConfig configDao;

    public RAppConfig(Application application) {
        this.configDao = GGC_GriderDB.getInstance(application).appConfigDao();
    }


}
