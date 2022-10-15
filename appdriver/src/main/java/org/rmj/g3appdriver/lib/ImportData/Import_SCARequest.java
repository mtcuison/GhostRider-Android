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

package org.rmj.g3appdriver.lib.ImportData;

import android.app.Application;

import org.rmj.g3appdriver.lib.ApprovalCode.ApprovalCode;

public class Import_SCARequest implements ImportInstance{
    public static final String TAG = Import_SCARequest.class.getSimpleName();
    private final ApprovalCode poSys;

    public Import_SCARequest(Application instance) {
        this.poSys = new ApprovalCode(instance);
    }

    @Override
    public void ImportData(ImportDataCallback callback) {
        if(!poSys.ImportSCARequest()){
            callback.OnFailedImportData(poSys.getMessage());
        } else {
            callback.OnSuccessImportData();
        }
    }
}
