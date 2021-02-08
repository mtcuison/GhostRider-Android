    package org.rmj.g3appdriver.GRider.Database.DataAccessObject;

    import androidx.lifecycle.LiveData;
    import androidx.room.Dao;
    import androidx.room.Delete;
    import androidx.room.Insert;
    import androidx.room.OnConflictStrategy;
    import androidx.room.Query;
    import androidx.room.Update;

    import org.rmj.g3appdriver.GRider.Database.Entities.EProvinceInfo;

    import java.util.List;

    @Dao
    public interface DProvinceInfo {

        @Insert
        void insert(EProvinceInfo provinceInfo);

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        void insertBulkData(List<EProvinceInfo> provinceInfoList);

        @Update
        void update(EProvinceInfo provinceInfo);

        @Delete
        void delete(EProvinceInfo provinceInfo);

        @Query("SELECT * FROM Province_Info")
        LiveData<List<EProvinceInfo>> getAllProvinceInfo();

        @Query("SELECT * FROM Province_Info")
        LiveData<List<EProvinceInfo>> getAllProvinceName();

        @Query("SELECT sProvName FROM Province_Info")
        LiveData<String[]> getAllProvinceNames();

        @Query("SELECT * FROM Province_Info WHERE sProvName LIKE:ProvinceName")
        LiveData<List<EProvinceInfo>> searchProvinceName(String ProvinceName);

        @Query("SELECT sProvName FROM Province_Info WHERE sProvIDxx = :provID")
        LiveData<String> getProvinceNameFromProvID(String provID);
    }
