package org.rmj.g3appdriver.FacilityTrack.Itinerary;

import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.FacilityTrack.room.DataAccessObject.DItinerary;
import org.rmj.g3appdriver.FacilityTrack.room.Entities.EBuildingVisit;
import org.rmj.g3appdriver.FacilityTrack.room.GGC_SecSysDb;

import java.util.List;

public class Facility {
    private static final String TAG = Facility.class.getSimpleName();

    private final Application instance;

    private final DItinerary poDao;

    private String message;

    public Facility(Application instance) {
        this.instance = instance;
        this.poDao = GGC_SecSysDb.getInstance(instance).itineraryDao();
    }

    public String getMessage(){
        return message;
    }

    public boolean ImportPlaces(){
        try{

            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }

    public LiveData<List<EBuildingVisit>> GetItinerary(){
        return poDao.GetItinerary();
    }

    public boolean PlaceVisited(String fsVal){
        try{

            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }
}
