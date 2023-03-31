package org.rmj.g3appdriver.lib.PacitaReward.Obj;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.dev.Api.HttpHeaders;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DPacita;
import org.rmj.g3appdriver.dev.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.dev.Database.GGC_GriderDB;
import org.rmj.g3appdriver.dev.Database.Repositories.RBranch;
import org.rmj.g3appdriver.lib.PacitaReward.pojo.BranchRate;

import java.util.ArrayList;
import java.util.List;

public class Pacita {
    private static final String TAG = Pacita.class.getSimpleName();

    private final DPacita poDao;
    private final HttpHeaders poHeaders;

    private String message;

    public Pacita(Application instance) {
        this.poDao = GGC_GriderDB.getInstance(instance).pacitaDao();
        this.poHeaders = HttpHeaders.getInstance(instance);
    }

    public String getMessage() {
        return message;
    }

    public List<BranchRate> GetBranchRates(){
        try{
            return new ArrayList<>();
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return null;
        }
    }

    public LiveData<List<EBranchInfo>> GetBranchesList(){
        return poDao.GetBranchList();
    }

    public boolean SaveBranchRatings(BranchRate foVal){
        try{

            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }

    public boolean UpdateBranchRate(){
        try{

            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }
}
