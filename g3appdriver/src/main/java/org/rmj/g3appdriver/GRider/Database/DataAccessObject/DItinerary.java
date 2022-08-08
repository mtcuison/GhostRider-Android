package org.rmj.g3appdriver.GRider.Database.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import org.rmj.g3appdriver.GRider.Database.Entities.EItinerary;

import java.util.List;

@Dao
public interface DItinerary {

    @Insert
    void Save(EItinerary foVal);

    @Insert
    void SaveBulkData(List<EItinerary> foVal);

    @Query("SELECT COUNT (*) FROM Employee_Itinerary")
    int GetRowsCountForID();

    @Query("UPDATE Employee_Itinerary SET cSendStat = 1, sTransNox =:TransNo1 WHERE sTransNox =:TransNox")
    void UpdateSentItinerary(String TransNox, String TransNo1);

    @Query("UPDATE Employee_Itinerary SET cSendStat = 2 WHERE sTransNox =:TransNox")
    void UpdateItineraryStatus(String TransNox);

    @Query("SELECT * FROM Employee_Itinerary WHERE sTransNox =:TransNox")
    EItinerary GetItineraryForUpload(String TransNox);

    @Query("SELECT * FROM Employee_Itinerary WHERE cSendStat = 0")
    List<EItinerary> GetUnsentItineraryList();

    @Query("SELECT * FROM Employee_Itinerary WHERE sEmployID = (SELECT sEmployID FROM User_Info_Master) ORDER BY dTransact DESC")
    LiveData<List<EItinerary>> GetItineraryList();

    @Query("SELECT * FROM Employee_Itinerary WHERE sEmployID = (SELECT sEmployID FROM User_Info_Master) AND cSendStat == '0' ORDER BY dTransact DESC")
    List<EItinerary> GetUnsentItinerary();
}
