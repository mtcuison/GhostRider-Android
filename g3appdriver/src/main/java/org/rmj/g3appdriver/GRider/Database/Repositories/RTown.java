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

package org.rmj.g3appdriver.GRider.Database.Repositories;

    import android.app.Application;

    import androidx.lifecycle.LiveData;

    import org.json.JSONArray;
    import org.json.JSONObject;
    import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DTownInfo;
    import org.rmj.g3appdriver.GRider.Database.Entities.ETownInfo;
    import org.rmj.g3appdriver.GRider.Database.GGC_GriderDB;

    import java.util.List;

public class RTown {
    private static final String TAG = "DB_Town_Repository";
    private final DTownInfo townDao;
    private LiveData<List<ETownInfo>> allTownInfo;
    private LiveData<List<ETownInfo>> townInfoFromProvince;
    private final Application application;

    public RTown(Application application){
        this.application = application;
        GGC_GriderDB GGCGriderDB = GGC_GriderDB.getInstance(application);
        townDao = GGCGriderDB.TownDao();
        allTownInfo = townDao.getAllTownInfo();
    }

    public LiveData<List<ETownInfo>> getTownInfoFromProvince(String ProvinceID){
        return townDao.getAllTownInfoFromProvince(ProvinceID);
    }

    public LiveData<String[]> getTownNamesFromProvince(String ProvinceID){
        return townDao.getTownNamesFromProvince(ProvinceID);
    }

    public void insertBulkData(List<ETownInfo> townInfoList){
        townDao.insertBulkData(townInfoList);
    }

    public String getLatestDataTime(){
        return townDao.getLatestDataTime();
    }

    public LiveData<ETownInfo> getTownNameAndProvID(String fsID){
        return townDao.getTownNameAndProvID(fsID);
    }
    public LiveData<DTownInfo.BrgyTownProvinceInfo> getBrgyTownProvinceInfo(String fsID){
        return townDao.getBrgyTownProvinceInfo(fsID);
    }
    public LiveData<DTownInfo.BrgyTownProvinceInfo> getTownProvinceInfo(String fsID){
        return townDao.getTownProvinceInfo(fsID);
    }
    public LiveData<List<DTownInfo.TownProvinceInfo>> getTownProvinceInfo(){
        return townDao.getTownProvinceInfo();
    }

    public DTownInfo.TownProvinceName getTownProvinceName(String TownID){
        return townDao.getTownProvinceNames(TownID);
    }

    public LiveData<DTownInfo.TownProvinceName> getLiveTownProvinceNames(String TownID){
        return townDao.getLiveTownProvinceNames(TownID);
    }
    public LiveData<DTownInfo.TownProvinceInfo> getTownProvinceByTownID(String TownID){
        return townDao.getTownProvinceByTownID(TownID);
    }
    public LiveData<DTownInfo.TownProvinceInfo> getTownProvinceByTownName(String TownNm){
        return townDao.getTownProvinceByTownName(TownNm);
    }
    public LiveData<DTownInfo.BrgyTownProvinceInfoWithID> getBrgyTownProvinceInfoWithID(String BrgyID){
        return townDao.getBrgyTownProvinceInfoWithID(BrgyID);
    }

    public void saveTownInfo(JSONArray faJson) throws Exception {
        for (int x = 0; x < faJson.length(); x++) {
            JSONObject loJson = faJson.getJSONObject(x);
            if(townDao.CheckIfExist(loJson.getString("sTownIDxx")) == null){
                ETownInfo loTown = new ETownInfo();
                loTown.setTownIDxx(loJson.getString("sTownIDxx"));
                loTown.setTownName(loJson.getString("sTownName"));
                loTown.setZippCode(loJson.getString("sZippCode"));
                loTown.setProvIDxx(loJson.getString("sProvIDxx"));
                loTown.setMuncplCd(loJson.getString("sMuncplCd"));
                loTown.setHasRoute(loJson.getString("cHasRoute"));
                loTown.setBlackLst(loJson.getString("cBlackLst"));
                loTown.setRecdStat(loJson.getString("cRecdStat"));
                loTown.setTimeStmp(loJson.getString("dTimeStmp"));
                townDao.insert(loTown);
            }
        }
    }

    public Integer GetTownRecordsCount(){
        return townDao.GetTownRecordsCount();
    }
}
