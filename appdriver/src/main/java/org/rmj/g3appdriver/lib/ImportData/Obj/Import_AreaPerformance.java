/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.g3appdriver.lib.ImportData.Obj;

import android.app.Application;

import org.rmj.g3appdriver.dev.Database.Repositories.RAreaPerformance;
import org.rmj.g3appdriver.lib.ImportData.model.ImportDataCallback;
import org.rmj.g3appdriver.lib.ImportData.model.ImportInstance;

public class Import_AreaPerformance implements ImportInstance {
    private static final String TAG = Import_AreaPerformance.class.getSimpleName();

    private final RAreaPerformance poSys;

    public Import_AreaPerformance(Application instance) {
        this.poSys = new RAreaPerformance(instance);
    }

    @Override
    public void ImportData(ImportDataCallback callback) {
        if(!poSys.ImportData()){
            callback.OnFailedImportData(poSys.getMessage());
        } else {
            callback.OnSuccessImportData();
        }
    }
}
