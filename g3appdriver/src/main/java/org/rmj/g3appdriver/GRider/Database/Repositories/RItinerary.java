package org.rmj.g3appdriver.GRider.Database.Repositories;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.apprdiver.util.SQLUtil;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DItinerary;
import org.rmj.g3appdriver.GRider.Database.Entities.EItinerary;
import org.rmj.g3appdriver.GRider.Database.GGC_GriderDB;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.g3appdriver.utils.WebClient;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RItinerary {
    private static final String TAG = RItinerary.class.getSimpleName();

    private final Application instance;

    private final DItinerary poDao;
    private final REmployee poEmployee;

    private String sTransNox;
    private String message;

    public RItinerary(Application instance) {
        this.instance = instance;
        this.poDao = GGC_GriderDB.getInstance(instance).itineraryDao();
        this.poEmployee = new REmployee(instance);
    }

    public String getTransNox() {
        return sTransNox;
    }

    public String getMessage() {
        return message;
    }

    public boolean SaveItinerary(Itinerary foVal){
        try{
            String lsTransno = CreateUniqueID();
            Log.d(TAG, "Itinerary record id: " + lsTransno);
            if(!foVal.isDataValid()){
               message = foVal.getMessage();
               return false;
            } else if(lsTransno.isEmpty()){
                message = "Unable to create unique id";
                return false;
            } else {
                String lsEmployID = poEmployee.getEmployeeID();
                EItinerary loDetail = new EItinerary();
                loDetail.setTransNox(lsTransno);
                loDetail.setEmployID(lsEmployID);
                loDetail.setTransact(new AppConstants().DATE_MODIFIED);
                loDetail.setTimeFrom(foVal.getTimeStrt());
                loDetail.setTimeThru(foVal.getTimeEndx());
                loDetail.setLocation(foVal.getLocation());
                loDetail.setRemarksx(foVal.getRemarksx());
                poDao.Save(loDetail);
                sTransNox = lsTransno;
                return true;
            }
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }

    public boolean UploadItinerary(String TransNox){
        try{
            JSONObject params = new JSONObject();
            AppConfigPreference loConfig = AppConfigPreference.getInstance(instance);
            WebApi loApi = new WebApi(loConfig.getTestStatus());
            EItinerary loDetail = poDao.GetItineraryForUpload(TransNox);
            Log.d(TAG, "Employee ID: " + loDetail.getEmployID());
            if(loDetail == null){
                message = "Itinerary record not found.";
                Log.d(TAG, message);
                return false;
            }
            params.put("sTransNox", loDetail.getTransNox());
            params.put("sEmployID", loDetail.getEmployID());
            params.put("dTransact", loDetail.getTransact());
            params.put("dTimeFrom", loDetail.getTimeFrom());
            params.put("dTimeThru", loDetail.getTimeThru());
            params.put("sLocation", loDetail.getLocation());
            params.put("sRemarksx", loDetail.getRemarksx());
            Log.d(TAG, params.toString());

            String lsResponse = WebClient.httpsPostJSon(loApi.getUrlSaveItinerary(loConfig.isBackUpServer()),
                    params.toString(),
                    HttpHeaders.getInstance(instance).getHeaders());
            if(lsResponse == null){
                message = "No server response.";
                return false;
            } else {
                Log.d(TAG, lsResponse);
                JSONObject loResponse = new JSONObject(lsResponse);
                String lsResult = loResponse.getString("result");
                if(!lsResult.equalsIgnoreCase("success")){
                    JSONObject loError = loResponse.getJSONObject("error");
                    message = loError.getString("message");
                    return false;
                } else {
                    String lsTransNox = loResponse.getString("sTransNox");
                    Log.d(TAG, "Upload success new transaction no. : " + lsTransNox);
                    poDao.UpdateSentItinerary(TransNox, lsTransNox);
                    return true;
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }

    public boolean UploadUnsentItinerary(){
        try{
            List<EItinerary> loList = poDao.GetUnsentItinerary();
            if(loList.size() > 0){
                for(int x = 0; x < loList.size(); x++) {
                    if(!UploadItinerary(loList.get(x).getTransNox())){
                        Log.e(TAG, "Unable to upload entry. Message: " + getMessage());
                    } else {
                        Log.d(TAG, "Itinerary entry has been uploaded successfully.");
                    }
                }
            } else {
                Log.d(TAG, "No entries to upload.");
            }
            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }

    public boolean DownloadItinerary(){
        try {
            String lsEmployID = poEmployee.getEmployeeID();
            AppConfigPreference loConfig = AppConfigPreference.getInstance(instance);
            WebApi loApi = new WebApi(loConfig.getTestStatus());
            JSONObject params = new JSONObject();
            params.put("sEmployID", lsEmployID);
            String lsResponse = WebClient.httpsPostJSon(loApi.getUrlDownloadItinerary(loConfig.isBackUpServer()),
                    params.toString(),
                    HttpHeaders.getInstance(instance).getHeaders());
            if(lsResponse == null){
                message = "No server response.";
                return false;
            } else {
                Log.d(TAG, lsResponse);
                JSONObject loResponse = new JSONObject(lsResponse);
                String lsResult = loResponse.getString("result");
                if(!lsResult.equalsIgnoreCase("success")){
                    JSONObject loError = loResponse.getJSONObject("error");
                    message = loError.getString("message");
                    return false;
                } else {
                    JSONArray jaDetail = loResponse.getJSONArray("detail");
                    for(int x = 0; x < jaDetail.length(); x++){
                        EItinerary loDetail = new EItinerary();
                        loDetail.setSendStat(2);
                        JSONObject joDetail = jaDetail.getJSONObject(x);
                        EItinerary exist = poDao.GetItineraryForUpload(joDetail.getString("sTransNox"));
                        if(exist == null) {
                            loDetail.setTransNox(joDetail.getString("sTransNox"));
                            loDetail.setEmployID(joDetail.getString("sEmployID"));
                            loDetail.setTransact(joDetail.getString("dTransact"));
                            loDetail.setTimeFrom(joDetail.getString("dTimeFrom"));
                            loDetail.setTimeThru(joDetail.getString("dTimeThru"));
                            loDetail.setTimeStmp(joDetail.getString("dTimeStmp"));
                            poDao.Save(loDetail);
                            Log.d(TAG, "New record save!");
                        } else {
                            Date ldDate1 = SQLUtil.toDate(exist.getTimeStmp(), SQLUtil.FORMAT_TIMESTAMP);
                            Date ldDate2 = SQLUtil.toDate((String) joDetail.get("dTimeStmp"), SQLUtil.FORMAT_TIMESTAMP);
                            if (!ldDate1.equals(ldDate2)){
                                poDao.UpdateItineraryStatus(joDetail.getString("sTransNox"));
                                Log.d(TAG, "Record updated for preview!");
                            }
                        }
                    }
                    return true;
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }

    public LiveData<List<EItinerary>> GetItineraryList(){
        return poDao.GetItineraryList();
    }

    private String CreateUniqueID(){
        String lsUniqIDx = "";
        try{
            String lsBranchCd = "MX01";
            String lsCrrYear = new SimpleDateFormat("yy", Locale.getDefault()).format(new Date());
            StringBuilder loBuilder = new StringBuilder(lsBranchCd);
            loBuilder.append(lsCrrYear);

            int lnLocalID = poDao.GetRowsCountForID() + 1;
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

    public static class Itinerary{
        private String dTimeStrt;
        private String dTimeEndx;
        private String sLocation;
        private String sRemarksx;

        private String message;

        public Itinerary() {
        }

        public String getMessage() {
            return message;
        }

        public String getTimeStrt() {
            return dTimeStrt;
        }

        public void setTimeStrt(String dTimeStrt) {
            this.dTimeStrt = dTimeStrt;
        }

        public String getTimeEndx() {
            return dTimeEndx;
        }

        public void setTimeEndx(String dTimeEndx) {
            this.dTimeEndx = dTimeEndx;
        }

        public String getLocation() {
            return sLocation;
        }

        public void setLocation(String sLocation) {
            this.sLocation = sLocation;
        }

        public String getRemarksx() {
            return sRemarksx;
        }

        public void setRemarksx(String sRemarksx) {
            this.sRemarksx = sRemarksx;
        }

        public boolean isDataValid(){
            if(dTimeStrt == null){
                message = "Please enter";
                return false;
            } else if (dTimeStrt.trim().isEmpty()){
                message = "Please enter";
                return false;
            } else if(dTimeEndx == null){
                message = "Please enter";
                return false;
            } else if(dTimeEndx.trim().isEmpty()){
                message = "Please enter";
                return false;
            } else if(sLocation == null){
                message = "Please enter";
                return false;
            } else if(sLocation.trim().isEmpty()){
                message = "Please enter";
                return false;
            } else if(sRemarksx == null){
                message = "Please enter";
                return false;
            } else if(sRemarksx.trim().isEmpty()){
                message = "Please enter";
                return false;
            } else {
                return true;
            }
        }
    }
}
