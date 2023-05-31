package org.rmj.g3appdriver.FacilityTrack.Itinerary;

import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.rmj.g3appdriver.FacilityTrack.pojo.Facility;
import org.rmj.g3appdriver.FacilityTrack.room.DataAccessObject.DItinerary;
import org.rmj.g3appdriver.FacilityTrack.room.Entities.EBuildingVisit;
import org.rmj.g3appdriver.FacilityTrack.room.GGC_SecSysDb;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class FacilityMaster {
    private static final String TAG = FacilityMaster.class.getSimpleName();

    private final Application instance;

    private final DItinerary poDao;

    private String message;

    public FacilityMaster(Application instance) {
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

    public LiveData<List<Facility>> GetFacilities(){
        MutableLiveData<List<Facility>> loFacilities = new MutableLiveData<>();
        List<Facility> loList = new ArrayList<>();

        Random random = new Random();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for (int i = 0; i < 20; i++) {
            String lsWhouseID = "MX01" + String.format("%02d", i + 1);
            String lsWHouseNm = "Facility " + (i + 1);
            Date date = new Date(random.nextLong());

            loList.add(new Facility(lsWhouseID, lsWHouseNm, dateFormat.format(date)));
        }
        loFacilities.postValue(loList);
        return loFacilities;
    }
}
