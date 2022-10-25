package org.rmj.g3appdriver.lib.integsys.CreditApp.Obj;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.dev.Database.DataAccessObject.DCreditApplication;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DTownInfo;
import org.rmj.g3appdriver.dev.Database.Entities.EBarangayInfo;
import org.rmj.g3appdriver.dev.Database.Entities.ECountryInfo;
import org.rmj.g3appdriver.dev.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.dev.Database.Entities.EOccupationInfo;
import org.rmj.g3appdriver.dev.Database.GGC_GriderDB;
import org.rmj.g3appdriver.dev.Database.Repositories.RBarangay;
import org.rmj.g3appdriver.dev.Database.Repositories.RTown;
import org.rmj.g3appdriver.lib.integsys.CreditApp.CreditApp;
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.ClientResidence;
import org.rmj.gocas.base.GOCASApplication;

import java.util.List;

public class ResidenceInfo implements CreditApp {
    private static final String TAG = ResidenceInfo.class.getSimpleName();

    private final DCreditApplication poDao;
    private final RTown poTown;
    private final RBarangay poBrgy;

    private String message;

    public ResidenceInfo(Application instance) {
        this.poDao = GGC_GriderDB.getInstance(instance).CreditApplicationDao();
        this.poTown = new RTown(instance);
        this.poBrgy = new RBarangay(instance);
    }

    @Override
    public LiveData<ECreditApplicantInfo> GetApplication(String args) {
        return poDao.GetApplicantInfo(args);
    }

    @Override
    public Object Parse(ECreditApplicantInfo args) {
        try{
            String lsDetail = args.getResidnce();
            GOCASApplication gocas = new GOCASApplication();
            gocas.setData(lsDetail);

            ClientResidence loDetail = new ClientResidence();
            loDetail.setAddress1(gocas.ResidenceInfo().PresentAddress().getAddress1());
            loDetail.setAddress2(gocas.ResidenceInfo().PresentAddress().getAddress2());
            loDetail.setOneAddress(args.getSameAddx().equalsIgnoreCase("1"));

            return loDetail;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return null;
        }
    }

    @Override
    public int Validate(Object args) {
        return 0;
    }

    @Override
    public boolean Save(Object args) {
        try{
            ClientResidence loDetail = (ClientResidence) args;

            ECreditApplicantInfo loApp = poDao.GetApplicantDetails(loDetail.getTransNox());

            if(loApp == null){
                message = "Unable to find record for update. Please restart credit app and try again.";
                return false;
            }

            GOCASApplication gocas = new GOCASApplication();

            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public LiveData<List<EBarangayInfo>> GetBarangayList(String args) {
        return poBrgy.getAllBarangayFromTown(args);
    }

    @Override
    public LiveData<List<DTownInfo.TownProvinceInfo>> GetTownProvinceList() {
        return poTown.getTownProvinceInfo();
    }

    @Override
    public LiveData<List<ECountryInfo>> GetCountryList() {
        return null;
    }

    @Override
    public LiveData<List<EOccupationInfo>> GetOccupations() {
        return null;
    }
}
