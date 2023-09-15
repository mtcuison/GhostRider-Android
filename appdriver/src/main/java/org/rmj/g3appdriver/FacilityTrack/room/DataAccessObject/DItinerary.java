package org.rmj.g3appdriver.FacilityTrack.room.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import org.rmj.g3appdriver.FacilityTrack.room.Entities.EBuildingVisit;

import java.util.List;

@Dao
public interface DItinerary {

    @Query("SELECT * FROM Building_Visit")
    LiveData<List<EBuildingVisit>> GetItineraries();

    @Query("SELECT * FROM Building_Visit")
    List<EBuildingVisit> GetItinerary();
}
