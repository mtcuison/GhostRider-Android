package org.rmj.g3appdriver.lib.integsys.CreditApp.Obj;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DCreditApplication;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DTownInfo;
import org.rmj.g3appdriver.dev.Database.Entities.EBarangayInfo;
import org.rmj.g3appdriver.dev.Database.Entities.ECountryInfo;
import org.rmj.g3appdriver.dev.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.dev.Database.Entities.EOccupationInfo;
import org.rmj.g3appdriver.dev.Database.GGC_GriderDB;
import org.rmj.g3appdriver.lib.integsys.CreditApp.CreditApp;
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.Disbursement;
import org.rmj.gocas.base.GOCASApplication;

import java.util.List;
import java.util.Objects;

public class DisbursementInfo implements CreditApp {
    private static final String TAG = DisbursementInfo.class.getSimpleName();

    private final DCreditApplication poDao;

    private Disbursement poDetail;

    private String message;

    public DisbursementInfo(Application instance) {
        this.poDao = GGC_GriderDB.getInstance(instance).CreditApplicationDao();
    }

    @Override
    public LiveData<ECreditApplicantInfo> GetApplication(String args) {
        return poDao.GetApplicantInfo(args);
    }

    @Override
    public Object Parse(ECreditApplicantInfo args) {
        try{
            Disbursement loDetail = new Disbursement();
            if (args.getDisbrsmt() != null){
                String lsDetail = args.getDisbrsmt();
                GOCASApplication gocas = new GOCASApplication();
                JSONParser loJson = new JSONParser();
                JSONObject joDetail = (JSONObject) loJson.parse(lsDetail);
                gocas.DisbursementInfo().setData(joDetail);

                loDetail.setAcctType(gocas.DisbursementInfo().BankAccount().getAccountType());
                loDetail.setElectric(gocas.DisbursementInfo().Expenses().getElectricBill());
                loDetail.setFoodExps(gocas.DisbursementInfo().Expenses().getFoodAllowance());
                loDetail.setWaterExp(gocas.DisbursementInfo().Expenses().getWaterBill());
                loDetail.setLoanExps(gocas.DisbursementInfo().Expenses().getLoanAmount());
                loDetail.setBankName(gocas.DisbursementInfo().BankAccount().getBankName());
                loDetail.setCrdtBank(gocas.DisbursementInfo().CreditCard().getBankName());
                loDetail.setCrdtLimt(gocas.DisbursementInfo().CreditCard().getCreditLimit());
//            loDetail.setCrdtYear(gocas.DisbursementInfo().CreditCard().getMemberSince());

                poDetail = loDetail;
            }
            return loDetail;

        } catch (NullPointerException e){
            e.printStackTrace();
            message = e.getMessage();
            return null;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return null;
        }
    }

    @Override
    public int Validate(Object args) {
        Disbursement loDetail = (Disbursement) args;

        if(poDetail == null){

            if(!loDetail.isDataValid()){
                message = loDetail.getMessage();
                return 0;
            }

        } else {

            //TODO: if all information inside each old object and new object is not the same,
            // return 2 to indicate validation needs confirmation from user to update the
            // previous information being save.

//            if(!poDetail.isEqual(loDetail)){
//                return 2;
//            } else {
//                return 1;
//            }
        }

        return 1;
    }

    @Override
    public boolean Save(Object args) {
        try{Disbursement loDetail = (Disbursement) args;

            ECreditApplicantInfo loApp = poDao.GetApplicantDetails(loDetail.getTransNox());

            if(loApp == null){
                message = "Unable to find record for update. Please restart credit app and try again.";
                return false;
            }

            GOCASApplication gocas = new GOCASApplication();
            gocas.DisbursementInfo().Expenses().setElectricBill(loDetail.getElectric());
            gocas.DisbursementInfo().Expenses().setFoodAllowance(loDetail.getFoodExps());
            gocas.DisbursementInfo().Expenses().setWaterBill(loDetail.getWaterExp());
            gocas.DisbursementInfo().Expenses().setLoanAmount((loDetail.getLoanExps()));
            gocas.DisbursementInfo().BankAccount().setBankName(loDetail.getBankName());
            gocas.DisbursementInfo().BankAccount().setAccountType(loDetail.getAcctType());
            gocas.DisbursementInfo().CreditCard().setBankName(loDetail.getCrdtBank());
            gocas.DisbursementInfo().CreditCard().setCreditLimit(loDetail.getCrdtLimt());
            gocas.DisbursementInfo().CreditCard().setMemberSince(loDetail.getCrdtYear());
            loApp.setDisbrsmt(gocas.DisbursementInfo().toJSONString());
            poDao.Update(loApp);
            return true;
        } catch (NullPointerException e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
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
        return null;
    }

    @Override
    public LiveData<List<DTownInfo.TownProvinceInfo>> GetTownProvinceList() {
        return null;
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
