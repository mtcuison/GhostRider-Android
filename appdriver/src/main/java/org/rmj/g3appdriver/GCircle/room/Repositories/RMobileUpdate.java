package org.rmj.g3appdriver.GCircle.room.Repositories;

import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import org.json.JSONObject;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DMobileUpdate;
import org.rmj.g3appdriver.GCircle.room.Entities.EMobileUpdate;
import org.rmj.g3appdriver.GCircle.room.GGC_GCircleDB;
import org.rmj.g3appdriver.etc.AppConstants;
import org.rmj.g3appdriver.GCircle.Apps.Dcp.pojo.MobileUpdate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RMobileUpdate {
    private static final String TAG = RAddressUpdate.class.getSimpleName();

    private final DMobileUpdate poDao;

    private String message;

    public RMobileUpdate(Application instance) {
        this.poDao = GGC_GCircleDB.getInstance(instance).mobileUpdateDao();
    }

    /**
     *
     * @return returns a message everytime operation fails...
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param foVal pass the object created from class which contains validation inside.
     * @return true String operation successful else null if operation fails,
     * call @getMessage() function to get an error message.
     */
    public String SaveMobileUpdate(MobileUpdate foVal){
        try{
            if(!foVal.isDataValid()){
                message = foVal.getMessage();
                return null;
            }

            EMobileUpdate loMobile = new EMobileUpdate();
            String lsTransNo = CreateUniqueID();
            loMobile.setTransNox(lsTransNo);
            loMobile.setMobileNo(foVal.getMobileNo());
            loMobile.setClientID(foVal.getClientID());
            loMobile.setPrimaryx(foVal.getPrimaryx());
            loMobile.setRemarksx(foVal.getsRemarksx());
            loMobile.setReqstCDe(foVal.getReqstCode());
            loMobile.setModified(AppConstants.DATE_MODIFIED());
            loMobile.setTimeStmp(AppConstants.DATE_MODIFIED());
            poDao.SaveMobileUpdate(loMobile);
            return lsTransNo;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return null;
        }
    }

    /**
     *
     * @param fsVal pass the client ID
     * @return list of entries for mobile updates...
     */
    public LiveData<List<DMobileUpdate.MobileUpdateInfo>> GetMobileUpdates(String fsVal){
        return poDao.GetMobileUpdateInfo(fsVal);
    }

    /**
     *
     * @param fsVal pass transaction no.
     * @return true if operation was successful else false if operation fails...
     * call @getMessage() function to get an error message.
     */
    public boolean DeleteAddress(String fsVal){
        try {
            poDao.DeleteMobileUpdate(fsVal);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }

    /**
     *
     * @param fsVal customer's client id
     * @return returns a json object which consist of details of mobile update, else null of operation fails.
     * call getMessage() if operation fails to get error message.
     */
    public JSONObject GetMobileDetailForPosting(String fsVal){
        try{
            EMobileUpdate loMobile = poDao.GetMobileUpdate(fsVal);

            if(loMobile == null){
                message = "Mobile info not found.";
                return null;
            }

            JSONObject params = new JSONObject();
            params.put("cReqstCDe", loMobile.getReqstCDe());
            params.put("sMobileNo", loMobile.getMobileNo());
            params.put("cPrimaryx", loMobile.getPrimaryx());
            params.put("sRemarksx", loMobile.getRemarksx());
            return params;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return null;
        }
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
}
