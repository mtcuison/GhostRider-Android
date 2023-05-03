/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 4/26/21 4:25 PM
 * project file last modified : 4/26/21 4:25 PM
 */

package org.rmj.g3appdriver.GCircle.ImportData.Obj;

import android.app.Application;

import org.rmj.g3appdriver.GCircle.ImportData.model.ImportDataCallback;
import org.rmj.g3appdriver.GCircle.ImportData.model.ImportInstance;
import org.rmj.g3appdriver.GCircle.room.Repositories.RSysConfig;

public class Import_SysConfig implements ImportInstance {
    private static final String TAG = Import_SysConfig.class.getSimpleName();

    private final RSysConfig poSys;

    public Import_SysConfig(Application instance) {
        this.poSys = new RSysConfig(instance);
    }

    @Override
    public void ImportData(ImportDataCallback callback) {
        if(!poSys.ImportSysConfig()){
            callback.OnFailedImportData(poSys.getMessage());
        } else {
            callback.OnSuccessImportData();
        }
    }
}
