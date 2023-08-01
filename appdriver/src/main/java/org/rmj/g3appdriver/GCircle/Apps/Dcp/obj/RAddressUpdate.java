package org.rmj.g3appdriver.GCircle.Apps.Dcp.obj;

import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import org.json.JSONObject;
import org.rmj.g3appdriver.GCircle.Apps.Dcp.pojo.AddressUpdate;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DAddressUpdate;
import org.rmj.g3appdriver.GCircle.room.Entities.EAddressUpdate;
import org.rmj.g3appdriver.GCircle.room.GGC_GCircleDB;
import org.rmj.g3appdriver.etc.AppConstants;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RAddressUpdate {
    private static final String TAG = RAddressUpdate.class.getSimpleName();

    private final DAddressUpdate poDao;

    private String message;

    public RAddressUpdate(Application instance) {
        this.poDao = GGC_GCircleDB.getInstance(instance).addressUpdateDao();
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
     * @return true if operation successful else false if operation fails,
     * call @getMessage() function to get an error message.
     */
    public String SaveAddressUpdate(AddressUpdate foVal){
        try{
            if(!foVal.isDataValid()){
                message = foVal.getMessage();
                return null;
            }

            EAddressUpdate loAddress = new EAddressUpdate();
            String lsTransNox = CreateUniqueID();
            loAddress.setTransNox(lsTransNox);
            loAddress.setClientID(foVal.getClientID());
            loAddress.setReqstCDe(foVal.getRequestCode());
            loAddress.setAddrssTp(foVal.getAddrssTp());
            loAddress.setHouseNox(foVal.getHouseNumber());
            loAddress.setAddressx(foVal.getAddress());
            loAddress.setBrgyIDxx(foVal.getBarangayID());
            loAddress.setTownIDxx(foVal.getTownID());
            loAddress.setPrimaryx(foVal.getPrimaryStatus());
            loAddress.setLongitud(foVal.getLongitude());
            loAddress.setLatitude(foVal.getLatitude());
            loAddress.setRemarksx(foVal.getRemarks());
            loAddress.setTranStat("1");
            loAddress.setSendStat("0");
            loAddress.setModified(AppConstants.DATE_MODIFIED());
            loAddress.setTimeStmp(AppConstants.DATE_MODIFIED());
            poDao.SaveAddressUpdate(loAddress);
            return lsTransNox;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return null;
        }
    }

    /**
     *
     * @param fsVal pass the client ID
     * @return list of entries for address updates...
     */
    public LiveData<List<DAddressUpdate.AddressUpdateInfo>> GetAddressUpdates(String fsVal){
        return poDao.GetAddressUpdateInfo(fsVal);
    }

    /**
     *
     * @param fsVal pass transaction no.
     * @return true if operation was successful else false if operation fails...
     * call @getMessage() function to get an error message.
     */
    public boolean DeleteAddress(String fsVal){
        try{
            poDao.DeleteAddressUpdate(fsVal);
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
     * @return returns a json object which consist of details of address update, else null of operation fails.
     * call getMessage() if operation fails to get error message.
     */
    public JSONObject GetAddressDetailForPosting(String fsVal){
        try{
            EAddressUpdate loAddress = poDao.GetAddressInfo(fsVal);

            if(loAddress == null){
                message = "Address info not found.";
                return null;
            }

            JSONObject params = new JSONObject();
            params.put("cReqstCDe", loAddress.getReqstCDe());
            params.put("cAddrssTp", loAddress.getAddrssTp());
            params.put("sHouseNox", loAddress.getHouseNox());
            params.put("sAddressx", loAddress.getAddressx());
            params.put("sTownIDxx", loAddress.getTownIDxx());
            params.put("sBrgyIDxx", loAddress.getBrgyIDxx());
            params.put("cPrimaryx", loAddress.getPrimaryx());
            params.put("sRemarksx", loAddress.getRemarksx());
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
