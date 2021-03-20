package org.rmj.g3appdriver.GRider.Database.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;

import java.util.List;

@Dao
public interface DImageInfo {

    @Insert
    void insert(EImageInfo imageInfo);

    @Update
    void update(EImageInfo imageInfo);

    @Query("UPDATE Image_Information " +
            "SET sTransNox =:TransNox, " +
            "cSendStat = '1', " +
            "dSendDate =:DateModifield " +
            "WHERE sTransNox =:oldTransNox")
    void updateImageInfo(String TransNox, String DateModifield, String oldTransNox);

    @Query("SELECT * FROM Image_Information " +
            "WHERE sSourceNo = (SELECT sTransNox " +
            "FROM LR_DCP_Collection_Master " +
            "ORDER BY dTransact DESC LIMIT 1)")
    LiveData<EImageInfo> getImageInfo();

    /**
     *
     * @return returns a LiveData List of all unsent image info...
     */
    @Query("SELECT * FROM Image_Information " +
            "WHERE cSendStat = 0 " +
            "AND sSourceNo = (SELECT sTransNox " +
            "FROM LR_DCP_Collection_Master " +
            "ORDER BY dTransact DESC LIMIT 1) " +
            "AND sFileCode = (SELECT sFileCode FROM EDocSys_File WHERE sBarrcode = 'DCP001')")
    LiveData<List<EImageInfo>> getUnsentImageInfoList();

    @Query("SELECT * FROM Image_Information")
    LiveData<List<EImageInfo>> getImageInfoList();


    @Query("SELECT * FROM Image_Information WHERE sSourceNo =:TransNox")
    LiveData<List<EImageInfo>> getImageListInfo(String TransNox);

    @Update
    void updateImageInfo(EImageInfo imageInfo);

    @Query("SELECT * FROM Image_Information " +
            "WHERE sDtlSrcNo = :sDtlSrcNo AND " +
            "sImageNme = :sImageNme")
    LiveData<EImageInfo> getImageLocation(String sDtlSrcNo, String sImageNme);

    @Query("SELECT * FROM Image_Information WHERE dCaptured LIKE:DateLog")
    LiveData<List<EImageInfo>> getCurrentLogTimeIfExist(String DateLog);

    @Query("SELECT * FROM Image_Information WHERE sFileCode = '0021' AND cSendStat <> '1'")
    LiveData<List<EImageInfo>> getUnsentLoginImageInfo();
}
