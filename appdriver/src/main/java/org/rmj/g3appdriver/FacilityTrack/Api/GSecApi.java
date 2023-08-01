package org.rmj.g3appdriver.FacilityTrack.Api;

import android.app.Application;
import android.util.Log;

import org.rmj.g3appdriver.dev.Api.WebApi;

public class GSecApi extends WebApi {
    private static final String TAG = GSecApi.class.getSimpleName();

    private static final String IMPORT_ITINERARY = "";

    public GSecApi(Application instance) {
        super(instance);
    }

    public String getAuthentication(){
        if(isUnitTest()){
            Log.d(TAG, "Initialize api:" + LOCAL + IMPORT_ITINERARY);
            return LOCAL + IMPORT_ITINERARY;
        }
        Log.d(TAG, "Initialize api:" + LOCAL + IMPORT_ITINERARY);
        return LIVE + IMPORT_ITINERARY;
    }
}
