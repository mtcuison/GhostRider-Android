/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(EImageInfo imageInfo);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(EImageInfo imageInfo);

    /**
     *
     * @param TransNox pass the new transaction no which will be return by API after uploading image...
     * @param DateModifield call the AppConstants.DATE_MODIFIED to set the current date time for dTimeStmp
     * @param oldTransNox pass the old transaction no. of image info before uploading image to server...
     */
    @Query("UPDATE Image_Information " +
            "SET sTransNox =:TransNox, " +
            "cSendStat = '1', " +
            "dSendDate =:DateModifield " +
            "WHERE sTransNox =:oldTransNox")
    void updateImageInfo(String TransNox, String DateModifield, String oldTransNox);

    @Query("UPDATE Image_Information " +
            "SET sTransNox =:TransNox, " +
            "cSendStat = '1', " +
            "dSendDate =:DateModifield " +
            "WHERE sFileCode =:fileCode")
    void updateImageInfos(String TransNox, String DateModifield, String fileCode);

    @Query("SELECT * FROM Image_Information " +
            "WHERE sSourceNo = (SELECT sTransNox " +
            "FROM LR_DCP_Collection_Master " +
            "ORDER BY dTransact DESC LIMIT 1)")
    LiveData<EImageInfo> getImageInfo();

    /**
     *
     * @return returns a LiveData List of all unsent DCP image info...
     */
    @Query("SELECT * FROM Image_Information " +
            "WHERE cSendStat = 0 " +
            "AND sSourceNo = (SELECT sTransNox " +
            "FROM LR_DCP_Collection_Master " +
            "ORDER BY dTransact DESC LIMIT 1) " +
            "AND sFileCode = (SELECT sFileCode FROM EDocSys_File WHERE sBarrcode = 'DCP001')")
    LiveData<List<EImageInfo>> getUnsentDCPImageInfoList();

    @Query("SELECT * FROM Image_Information")
    LiveData<List<EImageInfo>> getImageInfoList();


    @Query("SELECT * FROM Image_Information WHERE sSourceNo =:TransNox")
    LiveData<List<EImageInfo>> getImageListInfo(String TransNox);

    @Query("SELECT * FROM Image_Information WHERE sSourceNo =:TransNox AND sFileCode='0029'")
    LiveData<EImageInfo> getImageLogPreview(String TransNox);

    @Update
    void updateImageInfo(EImageInfo imageInfo);

    @Query("SELECT * FROM Image_Information " +
            "WHERE sDtlSrcNo = :sDtlSrcNo AND " +
            "sImageNme = :sImageNme")
    LiveData<EImageInfo> getImageLocation(String sDtlSrcNo, String sImageNme);

    @Query("SELECT * FROM Image_Information WHERE dCaptured LIKE:DateLog")
    LiveData<List<EImageInfo>> getCurrentLogTimeIfExist(String DateLog);

    @Query("SELECT * FROM Image_Information WHERE sFileCode = '0021' AND cSendStat <> '1'")
    List<EImageInfo> getUnsentLoginImageInfo();

    /**
     *
     * @param oldTransNox pass the old SourceNo of Image_Information which is not sent to server
     * @param TransNox pass the new Transaction No which is return by API after sending Credit_Online_Application
     */
    @Query("UPDATE Image_Information SET sSourceNo =:TransNox WHERE sSourceNo =:oldTransNox AND cSendStat <> '1'")
    void updateLoanApplicationImageSourceNo(String oldTransNox, String TransNox);

    /**
     *
     * @param TransNox pass the transaction no. of Credit_Online_Application
     * @return list of all scanned documents which are stored in local while internet is not available
     */
    @Query("SELECT * FROM Image_Information " +
            "WHERE sSourceNo = (SELECT sTransNox " +
            "FROM Credit_Online_Application WHERE sTransNox =:TransNox) " +
            "AND cSendStat <>'1' " +
            "AND sFileCode <> '0020' " +
            "AND sFileCode <> '0021'")
    List<EImageInfo> getUnsentLoanAppDocFiles(String TransNox);

    @Query("SELECT sFileLoct FROM Image_Information WHERE sSourceNo = :fsSource")
    LiveData<String> getImageLocationFromSrcId(String fsSource);

    @Query("SELECT * FROM Image_Information WHERE sSourceNo =:TransNox AND sDtlSrcNo=:AccntNo")
    EImageInfo getDCPImageInfoForPosting(String TransNox, String AccntNo);

    @Query("SELECT * FROM Image_Information WHERE sSourceCD = 'DCPa' AND cSendStat <> '1'")
    LiveData<List<EImageInfo>> getDCPUnpostedImageList();
}
