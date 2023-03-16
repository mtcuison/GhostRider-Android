package org.rmj.g3appdriver.lib.Panalo.Obj;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.json.JSONObject;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DRaffleStatus;
import org.rmj.g3appdriver.dev.Database.Entities.ERaffleStatus;
import org.rmj.g3appdriver.dev.Database.GGC_GriderDB;

public class ILOVEMYJOB {
    private static final String TAG = ILOVEMYJOB.class.getSimpleName();

    private final DRaffleStatus poDao;

    private String message;

    public ILOVEMYJOB(Application instance) {
        this.poDao = GGC_GriderDB.getInstance(instance).raffleStatusDao();
    }

    public boolean SaveRaffleStatus(String lsData){
        try{
            JSONObject loJson = new JSONObject(lsData);
            JSONObject loData = loJson.getJSONObject("data");
            Integer lsStatus = Integer.valueOf(loData.getString("cTranStat"));

            ERaffleStatus loDetail = poDao.GetRaffleStatus();

            if(loDetail == null) {
                ERaffleStatus loStatus = new ERaffleStatus();
                loStatus.setHasRffle(lsStatus);
                poDao.CreateStatus(loStatus);
                return true;
            }

            loDetail.setHasRffle(lsStatus);
            poDao.Update(loDetail);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }

    public LiveData<ERaffleStatus> GetRaffleStatus(){
        return poDao.HasRaffle();
    }
}
