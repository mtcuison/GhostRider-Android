/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 5/14/21 4:13 PM
 * project file last modified : 5/14/21 4:13 PM
 */

package org.rmj.g3appdriver.GCircle.ImportData.Obj;

import android.app.Application;

import org.rmj.g3appdriver.lib.Etc.Relation;
import org.rmj.g3appdriver.GCircle.ImportData.model.ImportDataCallback;
import org.rmj.g3appdriver.GCircle.ImportData.model.ImportInstance;

public class Import_Relation implements ImportInstance {
    private static final String TAG = Import_Relation.class.getSimpleName();

    private final Relation poSys;

    public Import_Relation(Application application){
        this.poSys = new Relation(application);
    }

    @Override
    public void ImportData(ImportDataCallback callback) {
        if(!poSys.ImportRelations()){
            callback.OnFailedImportData(poSys.getMessage());
        } else {
            callback.OnSuccessImportData();
        }
    }
}
