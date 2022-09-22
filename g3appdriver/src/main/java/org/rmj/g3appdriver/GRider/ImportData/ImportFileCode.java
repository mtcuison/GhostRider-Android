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

package org.rmj.g3appdriver.GRider.ImportData;

import android.annotation.SuppressLint;
import android.app.Application;

import org.rmj.g3appdriver.GRider.Database.Repositories.RFileCode;

public class ImportFileCode implements ImportInstance{
    private final RFileCode poSys;

    public ImportFileCode(Application instance) {
        this.poSys = new RFileCode(instance);
    }

    @SuppressLint("NewApi")
    @Override
    public void ImportData(ImportDataCallback callback) {
        if(!poSys.ImportFileCode()){
            callback.OnFailedImportData(poSys.getMessage());
        } else {
            callback.OnSuccessImportData();
        }
    }
}
