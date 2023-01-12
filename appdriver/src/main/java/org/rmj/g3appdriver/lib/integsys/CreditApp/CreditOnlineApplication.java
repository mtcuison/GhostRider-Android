package org.rmj.g3appdriver.lib.integsys.CreditApp;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.apprdiver.util.SQLUtil;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DCreditApplication;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DEmployeeInfo;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DMcModel;
import org.rmj.g3appdriver.dev.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.dev.Database.Entities.EBranchLoanApplication;
import org.rmj.g3appdriver.dev.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.dev.Database.Entities.ECreditApplication;
import org.rmj.g3appdriver.dev.Database.Entities.EMcBrand;
import org.rmj.g3appdriver.dev.Database.Entities.EMcModel;
import org.rmj.g3appdriver.dev.Database.GGC_GriderDB;
import org.rmj.g3appdriver.dev.Database.Repositories.RBranch;
import org.rmj.g3appdriver.dev.Database.Repositories.RMcBrand;
import org.rmj.g3appdriver.dev.Database.Repositories.RMcModel;
import org.rmj.g3appdriver.dev.HttpHeaders;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.etc.AppConstants;
import org.rmj.g3appdriver.etc.SessionManager;
import org.rmj.g3appdriver.lib.Account.EmployeeMaster;
import org.rmj.g3appdriver.lib.integsys.CreditApp.Obj.ApplicationInfo;
import org.rmj.g3appdriver.lib.integsys.CreditApp.Obj.BusinessInfo;
import org.rmj.g3appdriver.lib.integsys.CreditApp.Obj.CoMakerInfo;
import org.rmj.g3appdriver.lib.integsys.CreditApp.Obj.CoMakerResidenceInfo;
import org.rmj.g3appdriver.lib.integsys.CreditApp.Obj.DependentsInfo;
import org.rmj.g3appdriver.lib.integsys.CreditApp.Obj.DisbursementInfo;
import org.rmj.g3appdriver.lib.integsys.CreditApp.Obj.EmploymentInfo;
import org.rmj.g3appdriver.lib.integsys.CreditApp.Obj.FinancierInfo;
import org.rmj.g3appdriver.lib.integsys.CreditApp.Obj.MeansSelectionInfo;
import org.rmj.g3appdriver.lib.integsys.CreditApp.Obj.OtherInfo;
import org.rmj.g3appdriver.lib.integsys.CreditApp.Obj.PensionInfo;
import org.rmj.g3appdriver.lib.integsys.CreditApp.Obj.PersonalInfo;
import org.rmj.g3appdriver.lib.integsys.CreditApp.Obj.PropertiesInfo;
import org.rmj.g3appdriver.lib.integsys.CreditApp.Obj.ResidenceInfo;
import org.rmj.g3appdriver.lib.integsys.CreditApp.Obj.ReviewLoanInfo;
import org.rmj.g3appdriver.lib.integsys.CreditApp.Obj.SpouseBusinessInfo;
import org.rmj.g3appdriver.lib.integsys.CreditApp.Obj.SpouseEmploymentInfo;
import org.rmj.g3appdriver.lib.integsys.CreditApp.Obj.SpouseInfo;
import org.rmj.g3appdriver.lib.integsys.CreditApp.Obj.SpousePensionInfo;
import org.rmj.g3appdriver.lib.integsys.CreditApp.Obj.SpouseResidenceInfo;
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.LoanInfo;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.g3appdriver.utils.WebClient;
import org.rmj.gocas.base.GOCASApplication;
import org.rmj.gocas.pricelist.PriceFactory;
import org.rmj.gocas.pricelist.Pricelist;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CreditOnlineApplication {
    private static final String TAG = CreditOnlineApplication.class.getSimpleName();

    private final Application instance;

    private final DCreditApplication poDao;

    private final EmployeeMaster poUser;

    private final RBranch poBranch;
    private final RMcBrand poBrand;
    private final RMcModel poModel;
    private final Pricelist poPrice;

    private final AppConfigPreference poConfig;
    private final SessionManager poSession;
    private final WebApi poApi;
    private final HttpHeaders poHeaders;

    private String message;

    public CreditOnlineApplication(Application instance) {
        this.instance = instance;
        this.poDao = GGC_GriderDB.getInstance(instance).CreditApplicationDao();
        this.poUser = new EmployeeMaster(instance);
        this.poConfig = AppConfigPreference.getInstance(instance);
        this.poSession = new SessionManager(instance);
        this.poApi = new WebApi(poConfig.getTestStatus());
        this.poHeaders = HttpHeaders.getInstance(instance);
        this.poBranch = new RBranch(instance);
        this.poBrand = new RMcBrand(instance);
        this.poModel = new RMcModel(instance);
        this.poPrice = PriceFactory.make(PriceFactory.ProductType.MOTORCYCLE);
    }

    public String getMessage() {
        return message;
    }

    public LiveData<DEmployeeInfo.EmployeeBranch> GetUserInfo(){
        return poUser.GetEmployeeBranch();
    }

    public boolean DownloadApplications(){
        try{
            JSONObject params = new JSONObject();
            params.put("bycode", true);
            params.put("sUserIDxx", poSession.getUserID());

            ECreditApplication loApp = poDao.GetLatestRecord();

            if(loApp != null){
                params.put("sUserIDxx", loApp.getTimeStmp());
            }

            String lsResponse = WebClient.httpsPostJSon(
                    poApi.getUrlImportOnlineApplications(poConfig.isBackUpServer()),
                    params.toString(),
                    poHeaders.getHeaders());

            if(lsResponse == null){
                message = "Server no response.";
                return false;
            }

            JSONObject loResponse = new JSONObject(lsResponse);
            String lsResult = loResponse.getString("result");
            if(lsResult.equalsIgnoreCase("error")){
                JSONObject loError = loResponse.getJSONObject("error");
                message = loError.getString("message");
                return false;
            }

            JSONArray laJson = loResponse.getJSONArray("detail");
            for(int x = 0; x < laJson.length(); x++){
                JSONObject loJson = laJson.getJSONObject(x);

                ECreditApplication loDetail = poDao.GetCreditOnlineApplication(loJson.getString("sTransNox"));

                if(loDetail == null) {
                    ECreditApplication info = new ECreditApplication();
                    info.setTransNox(loJson.getString("sTransNox"));
                    info.setBranchCd(loJson.getString("sBranchCd"));
                    info.setTransact(loJson.getString("dTransact"));
                    info.setClientNm(loJson.getString("sClientNm"));
                    info.setGOCASNox(loJson.getString("sGOCASNox"));
                    //TODO : Unit Applied is default to 0 cuz empty String is return from Server upon request
                    // this must be update when credit is available in for mobile phone
                    info.setUnitAppl("0");
                    info.setSourceCD(loJson.getString("sSourceCD"));
                    info.setDetlInfo(loJson.getString("sDetlInfo"));
                    info.setQMatchNo(loJson.getString("sQMatchNo"));
                    info.setWithCIxx(loJson.getString("cWithCIxx"));
                    info.setDownPaym(Double.parseDouble(loJson.getString("nDownPaym")));
                    info.setRemarksx(loJson.getString("sRemarksx"));
                    info.setCreatedx(loJson.getString("sCreatedx"));
                    info.setDateCreatedx(loJson.getString("dCreatedx"));
                    info.setSendStat("1");
                    info.setVerified(loJson.getString("sVerified"));
                    info.setDateVerified(loJson.getString("dVerified"));
                    info.setTranStat(loJson.getString("cTranStat"));
                    info.setDivision(loJson.getString("cDivision"));
                    info.setReceived(loJson.getString("dReceived"));
                    info.setTimeStmp(loJson.getString("dTimeStmp"));
                    poDao.Save(info);
                } else {
                    Date ldDate1 = SQLUtil.toDate(loDetail.getTimeStmp(), SQLUtil.FORMAT_TIMESTAMP);
                    Date ldDate2 = SQLUtil.toDate((String) loJson.get("dTimeStmp"), SQLUtil.FORMAT_TIMESTAMP);

                    if(!ldDate1.equals(ldDate2)){
                        loDetail.setTransNox(loJson.getString("sTransNox"));
                        loDetail.setBranchCd(loJson.getString("sBranchCd"));
                        loDetail.setTransact(loJson.getString("dTransact"));
                        loDetail.setClientNm(loJson.getString("sClientNm"));
                        loDetail.setGOCASNox(loJson.getString("sGOCASNox"));
                        //TODO : Unit Applied is default to 0 cuz empty String is return from Server upon request
                        // this must be update when credit is available in for mobile phone
                        loDetail.setUnitAppl("0");
                        loDetail.setSourceCD(loJson.getString("sSourceCD"));
                        loDetail.setDetlInfo(loJson.getString("sDetlInfo"));
                        loDetail.setQMatchNo(loJson.getString("sQMatchNo"));
                        loDetail.setWithCIxx(loJson.getString("cWithCIxx"));
                        loDetail.setDownPaym(Double.parseDouble(loJson.getString("nDownPaym")));
                        loDetail.setRemarksx(loJson.getString("sRemarksx"));
                        loDetail.setCreatedx(loJson.getString("sCreatedx"));
                        loDetail.setDateCreatedx(loJson.getString("dCreatedx"));
                        loDetail.setSendStat("1");
                        loDetail.setVerified(loJson.getString("sVerified"));
                        loDetail.setDateVerified(loJson.getString("dVerified"));
                        loDetail.setTranStat(loJson.getString("cTranStat"));
                        loDetail.setDivision(loJson.getString("cDivision"));
                        loDetail.setReceived(loJson.getString("dReceived"));
                        loDetail.setTimeStmp(loJson.getString("dTimeStmp"));
                    }
                }
            }

            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }

    public LiveData<List<DCreditApplication.ApplicationLog>> GetCreditApplications(){
        return poDao.getApplicationHistory();
    }

    public boolean DownloadBranchApplications(){
        try{
            JSONObject params = new JSONObject();
            params.put("bycode", true);
            params.put("value", poSession.getBranchCode());

            String lsResponse = WebClient.httpsPostJSon(
                    poApi.getUrlBranchLoanApp(poConfig.isBackUpServer()),
                    params.toString(),
                    poHeaders.getHeaders());

            if(lsResponse == null){
                message = "Server no response.";
                return false;
            }

            JSONObject loResponse = new JSONObject(lsResponse);
            String lsResult = loResponse.getString("result");
            if (lsResult.equalsIgnoreCase("error")) {
                JSONObject loError = loResponse.getJSONObject("error");
                message = loError.getString("message");
                return false;
            }

            JSONArray laJson = loResponse.getJSONArray("detail");

            for(int x= 0; x < laJson.length(); x++){
                JSONObject loJson = laJson.getJSONObject(x);

                EBranchLoanApplication loDetail = poDao.GetBranchApplication(loJson.getString("sTransNox"));

                if(loDetail == null){
                    EBranchLoanApplication loanInfo = new EBranchLoanApplication();
                    loanInfo.setTransNox(loJson.getString("sTransNox"));
                    loanInfo.setBranchCD(loJson.getString("sBranchCd"));
                    loanInfo.setTransact(loJson.getString("dTransact"));
                    loanInfo.setCredInvx(loJson.getString("sCredInvx"));
                    loanInfo.setCompnyNm(loJson.getString("sCompnyNm"));
                    loanInfo.setSpouseNm(loJson.getString("sSpouseNm"));
                    loanInfo.setAddressx(loJson.getString("sAddressx"));
                    loanInfo.setMobileNo(loJson.getString("sMobileNo"));
                    loanInfo.setQMAppCde(loJson.getString("sQMAppCde"));
                    loanInfo.setModelNme(loJson.getString("sModelNme"));
                    loanInfo.setDownPaym(loJson.getString("nDownPaym"));
                    loanInfo.setAcctTerm(loJson.getString("nAcctTerm"));
                    loanInfo.setCreatedX(loJson.getString("sCreatedx"));
                    loanInfo.setTranStat(loJson.getString("cTranStat"));
                    loanInfo.setTimeStmp(loJson.getString("dTimeStmp"));
                    poDao.Save(loanInfo);
                    Log.d(TAG, "Branch loan application has been saved.");
                } else {
                    loDetail.setTransNox(loJson.getString("sTransNox"));
                    loDetail.setBranchCD(loJson.getString("sBranchCd"));
                    loDetail.setTransact(loJson.getString("dTransact"));
                    loDetail.setCredInvx(loJson.getString("sCredInvx"));
                    loDetail.setCompnyNm(loJson.getString("sCompnyNm"));
                    loDetail.setSpouseNm(loJson.getString("sSpouseNm"));
                    loDetail.setAddressx(loJson.getString("sAddressx"));
                    loDetail.setMobileNo(loJson.getString("sMobileNo"));
                    loDetail.setQMAppCde(loJson.getString("sQMAppCde"));
                    loDetail.setModelNme(loJson.getString("sModelNme"));
                    loDetail.setDownPaym(loJson.getString("nDownPaym"));
                    loDetail.setAcctTerm(loJson.getString("nAcctTerm"));
                    loDetail.setCreatedX(loJson.getString("sCreatedx"));
                    loDetail.setTranStat(loJson.getString("cTranStat"));
                    loDetail.setTimeStmp(loJson.getString("dTimeStmp"));
                    poDao.Update(loDetail);
                    Log.d(TAG, "Branch loan application has been updated.");
                }
            }
            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }

    public LiveData<List<EBranchLoanApplication>> GetBranchApplications(){
        return poDao.GetBranchApplications();
    }

    public String CreateApplication(LoanInfo foVal){
        try{
            if(!foVal.isDataValid()){
                message = foVal.getMessage();
                return null;
            }

            String lsTransNo = CreateUniqueIDForApplicant();

            if(lsTransNo.isEmpty()){
                message = "Failed to generate unique id please restart the app and try again.";
                return null;
            }

            GOCASApplication loGocas = new GOCASApplication();
            loGocas.PurchaseInfo().setAppliedFor(String.valueOf(foVal.getAppTypex()));
            loGocas.PurchaseInfo().setTargetPurchase(foVal.getTargetDte());
            loGocas.PurchaseInfo().setCustomerType(String.valueOf(foVal.getCustTypex()));
            loGocas.PurchaseInfo().setPreferedBranch(foVal.getBranchCde());
            loGocas.PurchaseInfo().setBrandName(foVal.getBrandIDxx());
            loGocas.PurchaseInfo().setModelID(foVal.getModelIDxx());
            loGocas.PurchaseInfo().setDownPayment(foVal.getDownPaymt());
            loGocas.PurchaseInfo().setAccountTerm(foVal.getAccTermxx());
            loGocas.PurchaseInfo().setDateApplied(new AppConstants().DATE_MODIFIED());
            loGocas.PurchaseInfo().setMonthlyAmortization(foVal.getMonthlyAm());

            lsTransNo = CreateUniqueIDForApplicant();
            ECreditApplicantInfo loApp = new ECreditApplicantInfo();
            loApp.setTransNox(lsTransNo);
            loApp.setPurchase(loGocas.PurchaseInfo().toJSONString());
            loApp.setBranchCd(loGocas.PurchaseInfo().getPreferedBranch());
            loApp.setAppliedx(loGocas.PurchaseInfo().getAppliedFor());
            loApp.setDownPaym(loGocas.PurchaseInfo().getDownPayment());
            loApp.setCreatedx(new AppConstants().DATE_MODIFIED);
            loApp.setTransact(AppConstants.CURRENT_DATE);
            loApp.setTranStat("0");
            poDao.Save(loApp);

            Log.d(TAG, "New credit online application has been created.");
            return lsTransNo;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return null;
        }
    }

    public boolean UploadApplication(String args){
        try {
            ECreditApplication loApp = poDao.GetCreditOnlineApplication(args);

            if(loApp == null){
                message = "Unable to find application to upload.";
                return false;
            }

            JSONObject params = new JSONObject(loApp.getDetlInfo());
            params.put("dCreatedx", loApp.getCreatedx());

            String lsResponse = WebClient.httpsPostJSon(
                    poApi.getUrlSubmitOnlineApplication(poConfig.isBackUpServer()),
                    params.toString(),
                    poHeaders.getHeaders());

            if(lsResponse == null){
                message = "Server no response.";
                return false;
            }

            JSONObject loResponse = new JSONObject(lsResponse);
            String lsResult = loResponse.getString("result");
            if(lsResult.equalsIgnoreCase("error")){
                JSONObject loError = loResponse.getJSONObject("error");
                message = loError.getString("message");
                return false;
            }

            String lsTransNox = loResponse.getString("sTransNox");
            poDao.updateSentLoanAppl(loApp.getTransNox(), lsTransNox, new AppConstants().DATE_MODIFIED);

            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }

    public boolean UploadApplications(){
        try{
            List<ECreditApplication> loApps = poDao.GetApplicationsForUpload();

            if(loApps == null){
                message = "No credit online application to upload.";
                return false;
            }

            if(loApps.size() == 0){
                message = "No credit online application to upload.";
                return false;
            }

            for(int x = 0; x < loApps.size(); x++){
                ECreditApplication loApp = loApps.get(x);

                JSONObject params = new JSONObject(loApp.getDetlInfo());
                params.put("dCreatedx", loApp.getCreatedx());

                String lsResponse = WebClient.httpsPostJSon(
                        poApi.getUrlSubmitOnlineApplication(poConfig.isBackUpServer()),
                        params.toString(),
                        poHeaders.getHeaders());

                if(lsResponse == null){
                    message = "Server no response.";
                    Log.e(TAG, message);
                    Thread.sleep(1000);
                    continue;
                }

                JSONObject loResponse = new JSONObject(lsResponse);
                String lsResult = loResponse.getString("result");
                if(lsResult.equalsIgnoreCase("error")){
                    JSONObject loError = loResponse.getJSONObject("error");
                    message = loError.getString("message");
                    Log.e(TAG, message);
                    Thread.sleep(1000);
                    continue;
                }

                String lsTransNox = loResponse.getString("sTransNox");
                poDao.updateSentLoanAppl(loApp.getTransNox(), lsTransNox, new AppConstants().DATE_MODIFIED);
                Log.d(TAG, "Credit online application uploaded successfully.");
                Thread.sleep(1000);
            }

            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }

    public LiveData<List<EBranchInfo>> getAllBranchInfo(){
        return poBranch.getAllMcBranchInfo();
    }

    public LiveData<List<EMcBrand>> getAllMcBrand(){
        return poBrand.getAllBrandInfo();
    }

    public LiveData<List<EMcModel>> getAllBrandModelInfo(String args){
        return poModel.getMcModelFromBrand(args);
    }

    public LiveData<DMcModel.McAmortInfo> GetMonthlyPayment(String ModelID, int Term){
        return poModel.GetMonthlyPayment(ModelID, Term);
    }

    public LiveData<DMcModel.McDPInfo> GetInstallmentPlanDetail(String ModelID){
        return poModel.getDownpayment(ModelID);
    }

    public boolean InitializeMcInstallmentTerms(DMcModel.McDPInfo args){
        try{
            org.json.simple.JSONObject loJson = new org.json.simple.JSONObject();
            loJson.put("sModelIDx", args.ModelIDx);
            loJson.put("sModelNme", args.ModelNme);
            loJson.put("nRebatesx", args.Rebatesx);
            loJson.put("nMiscChrg", args.MiscChrg);
            loJson.put("nEndMrtgg", args.EndMrtgg);
            loJson.put("nMinDownx", args.MinDownx);
            loJson.put("nSelPrice", args.SelPrice);
            loJson.put("nLastPrce", args.LastPrce);

            poPrice.setModelInfo(loJson);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }

    public double GetMinimumDownpayment(){
        return poPrice.getMinimumDP();
    }

    public double GetMonthlyAmortization(DMcModel.McAmortInfo args, double args1){
        try{
            org.json.simple.JSONObject loJson = new org.json.simple.JSONObject();
            loJson.put("nSelPrice", args.nSelPrice);
            loJson.put("nMinDownx", args.nMinDownx);
            loJson.put("nMiscChrg", args.nMiscChrg);
            loJson.put("nRebatesx", args.nRebatesx);
            loJson.put("nEndMrtgg", args.nEndMrtgg);
            loJson.put("nAcctThru", args.nAcctThru);
            loJson.put("nFactorRt", args.nFactorRt);

            poPrice.setDownPayment(args1);
            return poPrice.getMonthlyAmort(loJson);
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return 0;
        }
    }

    public double GetMonthlyAmortization(DMcModel.McAmortInfo args, int args1){
        try{
            org.json.simple.JSONObject loJson = new org.json.simple.JSONObject();
            loJson.put("nSelPrice", args.nSelPrice);
            loJson.put("nMinDownx", args.nMinDownx);
            loJson.put("nMiscChrg", args.nMiscChrg);
            loJson.put("nRebatesx", args.nRebatesx);
            loJson.put("nEndMrtgg", args.nEndMrtgg);
            loJson.put("nAcctThru", args.nAcctThru);
            loJson.put("nFactorRt", args.nFactorRt);

            poPrice.setPaymentTerm(args1);
            return poPrice.getMonthlyAmort(loJson);
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return 0;
        }
    }

    public CreditApp getInstance(CreditAppInstance app){
        switch (app){
            case Client_Info:
                return new PersonalInfo(instance);
            case Residence_Info:
                return new ResidenceInfo(instance);
            case Employed_Info:
                return new EmploymentInfo(instance);
            case Self_Employed_Info:
                return new BusinessInfo(instance);
            case Financier_Info:
                return new FinancierInfo(instance);
            case Pension_Info:
                return new PensionInfo(instance);
            case Spouse_Info:
                return new SpouseInfo(instance);
            case Spouse_Residence_Info:
                return new SpouseResidenceInfo(instance);
            case Spouse_Employment_Info:
                return new SpouseEmploymentInfo(instance);
            case Spouse_Self_Employed_Info:
                return new SpouseBusinessInfo(instance);
            case Spouse_Pension_Info:
                return new SpousePensionInfo(instance);
            case Disbursement_Info:
                return new DisbursementInfo(instance);
            case Dependent_Info:
                return new DependentsInfo(instance);
            case Properties_Info:
                return new PropertiesInfo(instance);
            case Other_Info:
                return new OtherInfo(instance);
            case CoMaker_Info:
                return new CoMakerInfo(instance);
            case CoMaker_Residence_Info:
                return new CoMakerResidenceInfo(instance);
            case Application_Info:
                return new ApplicationInfo(instance);
            case Means_Info:
                return new MeansSelectionInfo(instance);
            case ReviewLoanInfo:
                return new ReviewLoanInfo(instance);
            default:
                return null;
        }
    }

    private String CreateUniqueIDForApplicant(){
        String lsUniqIDx = "";
        try{
            String lsBranchCd = "MX01";
            String lsCrrYear = new SimpleDateFormat("yy", Locale.getDefault()).format(new Date());
            StringBuilder loBuilder = new StringBuilder(lsBranchCd);
            loBuilder.append(lsCrrYear);

            int lnLocalID = poDao.GetRowsCountForUniqueID() + 1;
            String lsPadNumx = String.format("%05d", lnLocalID);
            loBuilder.append(lsPadNumx);
            lsUniqIDx = loBuilder.toString();
        } catch (Exception e){
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        }
        Log.d(TAG, lsUniqIDx);
        return lsUniqIDx;
    }
}
