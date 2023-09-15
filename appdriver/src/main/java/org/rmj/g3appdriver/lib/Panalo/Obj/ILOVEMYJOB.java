package org.rmj.g3appdriver.lib.Panalo.Obj;

import android.app.Application;
import android.graphics.Bitmap;
import android.util.Log;

import androidx.lifecycle.LiveData;

import org.json.JSONObject;
import org.rmj.g3appdriver.GCircle.Api.GCircleApi;
import org.rmj.g3appdriver.dev.Api.HttpHeaders;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DRaffleStatus;
import org.rmj.g3appdriver.GCircle.room.Entities.ERaffleStatus;
import org.rmj.g3appdriver.GCircle.room.GGC_GCircleDB;
import org.rmj.g3appdriver.lib.Panalo.model.PanaloRewards;

import java.util.List;

public class ILOVEMYJOB extends GPanalo {
    private static final String TAG = ILOVEMYJOB.class.getSimpleName();

    private final DRaffleStatus poDao;

    private final GCircleApi poApis;
    private final HttpHeaders poHeaders;

    public ILOVEMYJOB(Application instance) {
        super(instance);
        this.poDao = GGC_GCircleDB.getInstance(instance).raffleStatusDao();
        this.poApis = new GCircleApi(instance);
        this.poHeaders = HttpHeaders.getInstance(instance);
    }

    public boolean SaveRaffleStatus(String lsData){
        try{
            JSONObject loJson = new JSONObject(lsData);
            JSONObject loData = loJson.getJSONObject("data");
            Integer lsStatus = Integer.valueOf(loData.getString("status"));

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
            Log.e(TAG, e.getMessage());
            return false;
        }
    }

    public boolean ResetRaffleStatus(){
        try{
            ERaffleStatus loDetail = poDao.GetRaffleStatus();

            if(loDetail == null) {
                Log.e(TAG, "Raffle status is not yet initialize.");
                return false;
            }

            switch (loDetail.getHasRffle()){
                case 0:
                    Log.e(TAG, "Raffle status is already reset.");
                    return false;
                case 1:
                    Log.e(TAG, "Raffle status hasn't started yet.");
                    return false;
                case 2:
                    Log.e(TAG, "Raffle status is on going...");
                    return false;
                default:
                    loDetail.setHasRffle(0);
                    poDao.Update(loDetail);
                    return true;
            }
        } catch (Exception e){
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
            return false;
        }
    }

    public LiveData<ERaffleStatus> GetRaffleStatus(){
        return poDao.HasRaffle();
    }

    @Override
    public List<PanaloRewards> GetRewards(String args) {
        return super.GetRewards(args);
    }

    @Override
    public Bitmap RedeemReward(PanaloRewards args) {
        return super.RedeemReward(args);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    public Boolean ClaimReward(String args) {
        return super.ClaimReward(args);
    }
}
