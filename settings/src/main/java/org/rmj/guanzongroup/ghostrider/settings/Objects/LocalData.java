/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.settings
 * Electronic Personnel Access Control Security System
 * project file created : 6/23/21 2:31 PM
 * project file last modified : 6/23/21 2:31 PM
 */

package org.rmj.guanzongroup.ghostrider.settings.Objects;

public class LocalData {
    private final String DataName;
    private final int DataCount;
    private final String DataApi;

    public LocalData(String dataName, int dataCount, String dataApi) {
        DataName = dataName;
        DataCount = dataCount;
        DataApi = dataApi;
    }

    public String getDataName() {
        return DataName;
    }

    public int getDataCount() {
        return DataCount;
    }

    public String getDataApi() {
        return DataApi;
    }
}
