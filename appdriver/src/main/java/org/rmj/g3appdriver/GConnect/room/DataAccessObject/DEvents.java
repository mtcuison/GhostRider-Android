package org.rmj.g3appdriver.GConnect.room.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.rmj.g3appdriver.GConnect.room.Entities.EEvents;

import java.util.List;

@Dao
public interface DEvents {

    @Insert
    void insert(EEvents events);

    @Insert
    void insertBulkData(List<EEvents> events);

    @Update
    void update(EEvents events);

    @Query("SELECT * FROM App_Event_Info ORDER BY sTransNox ASC")
    LiveData<List<EEvents>> getAllEvents();

    @Query("SELECT * FROM App_Event_Info")
    List<EEvents> getAllEventsForDownloadImg();

    @Query("SELECT EXISTS(SELECT * FROM App_Event_Info WHERE sTransNox =:TransNox AND cNotified = '1')")
    boolean getEventExist(String TransNox);

    @Query("UPDATE App_Event_Info SET cNotified = '1', dModified =:date WHERE sTransNox =:transNox ")
    void updateReadEvent(String date, String transNox);

    @Query("UPDATE App_Event_Info SET sImagePath =:imgPath WHERE sTransNox =:transNox ")
    void updateEventImgPath(String imgPath, String transNox);

    @Query("SELECT COUNT(*) FROM App_Event_Info WHERE cNotified = '0'")
    LiveData<Integer> getEventCount();

    @Query("SELECT * FROM App_Event_Info ORDER BY dEvntFrom DESC LIMIT 1")
    EEvents CheckEvent();

     class  PromoEventsModel {
        public String transNox;
        public String branchNm;
        public String dateFrom;
        public String dateThru;
        public String title;
        public String Address;
        public String url;
        public String imgUrl;
        public String notified;
        public String modified;
        public String imgByte;
        public String division;
        public String directoryFolder;
        public String imgPath;
        }
}
