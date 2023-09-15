package org.rmj.g3appdriver.GCircle.Apps.Dcp.obj;

import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GCircle.Apps.Dcp.model.LRDcp;
import org.rmj.g3appdriver.GCircle.Apps.Dcp.pojo.AddressUpdate;
import org.rmj.g3appdriver.GCircle.Apps.Dcp.pojo.CustomerNotAround;
import org.rmj.g3appdriver.GCircle.Apps.Dcp.pojo.MobileUpdate;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DAddressUpdate;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DMobileUpdate;
import org.rmj.g3appdriver.GCircle.room.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.etc.AppConstants;

import java.util.List;

public class CNA extends LRDcp {
    private static final String TAG = CNA.class.getSimpleName();

    public CNA(Application instance) {
        super(instance);
    }

    @Override
    public boolean SaveTransaction(Object args) {
        try{
            CustomerNotAround foVal = (CustomerNotAround) args;
            if(!foVal.isDataValid()){
                message = foVal.getMessage();
                return false;
            }

            String TransNox = foVal.getTransNox();
            String AccntNox = foVal.getAccountNo();
            String EntryNox = foVal.getEntryNox();
            EDCPCollectionDetail loDetail = poDao.GetCollectionDetail(
                    TransNox,
                    EntryNox,
                    AccntNox);

            if(loDetail == null){
                message = "Unable to find account detail. Please restart app and try again.";
                return false;
            }

            loDetail.setTranStat("1");
            loDetail.setRemCodex("CNA");
            loDetail.setRemarksx(foVal.getRemarksx());
            loDetail.setModified(AppConstants.DATE_MODIFIED());

            String lsImageID = poImage.SaveDcpImage(
                    foVal.getAccountNo(),
                    foVal.getTransNox(),
                    foVal.getFileName(),
                    foVal.getFilePath(),
                    foVal.getLatitude(),
                    foVal.getLongtude());

            if(lsImageID == null){
                message = poImage.getMessage();
                return false;
            }

            poDao.UpdateCollectionDetail(loDetail);
            Log.d(TAG, "Collection detail has been updated.");
            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }

    public boolean SaveMobileUpdate(MobileUpdate foVal){
        try{
            String lsTransNo = poMobile.SaveMobileUpdate(foVal);
            if(lsTransNo == null){
                message = poMobile.getMessage();
                return false;
            }

            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }

    public boolean SaveAddressUpdate(AddressUpdate foVal){
        try{
            String lsTransNo = poAddress.SaveAddressUpdate(foVal);
            if(lsTransNo == null){
                message = poAddress.getMessage();
                return false;
            }
            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }

    public LiveData<List<DMobileUpdate.MobileUpdateInfo>> GetMobileUpdates(String fsVal){
        return poMobile.GetMobileUpdates(fsVal);
    }

    public LiveData<List<DAddressUpdate.AddressUpdateInfo>> GetAddressUpdates(String fsVal){
        return poAddress.GetAddressUpdates(fsVal);
    }

    public boolean DeleteAddressUpdate(String fsVal){
        return poAddress.DeleteAddress(fsVal);
    }

    public boolean DeleteMobileUpdate(String fsVal){
        return poMobile.DeleteAddress(fsVal);
    }
}
