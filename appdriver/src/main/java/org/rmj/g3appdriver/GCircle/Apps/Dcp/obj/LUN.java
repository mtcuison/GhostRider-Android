package org.rmj.g3appdriver.GCircle.Apps.Dcp.obj;

import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;
import android.util.Log;

import org.rmj.g3appdriver.GCircle.Apps.Dcp.model.LRDcp;
import org.rmj.g3appdriver.GCircle.Apps.Dcp.pojo.LoanUnit;
import org.rmj.g3appdriver.GCircle.room.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.etc.AppConstants;

public class LUN extends LRDcp {
    private static final String TAG = LUN.class.getSimpleName();

    public LUN(Application instance) {
        super(instance);
    }

    @Override
    public boolean SaveTransaction(Object args) {
        try{
            LoanUnit foVal = (LoanUnit) args;
            if(!foVal.isDataValid()){
                message = foVal.getMessage();
                return false;
            }

            String TransNox = foVal.getTransNox();
            String AccntNox = foVal.getAccntNox();
            String EntryNox = foVal.getEntryNox();

            EDCPCollectionDetail loDetail = poDao.GetCollectionDetail(
                    TransNox,
                    EntryNox,
                    AccntNox);

            if(loDetail == null){
                message = "Unable to find account detail. Please restart app and try again.";
                return false;
            }

            String lsFullName = foVal.getLastName() + ", " + foVal.getFrstName();

            if(foVal.getMiddName() != null){
                if(foVal.getMiddName().isEmpty()){
                    lsFullName = lsFullName + " " + foVal.getMiddName();
                }
            }

            if(foVal.getSuffixxx() != null){
                if(foVal.getSuffixxx().isEmpty()){
                    lsFullName = lsFullName + ", " + foVal.getSuffixxx();
                }
            }

            String lsResult = poClient.SaveClientUpdate(foVal);
            if(lsResult == null){
                message = poClient.getMessage();
                return false;
            }

            loDetail.setFullName(lsFullName);
            loDetail.setBrgyName(foVal.getBrgyIDxx());
            loDetail.setHouseNox(foVal.getHouseNox());
            loDetail.setAddressx(foVal.getStreetxx());
            loDetail.setTownName(foVal.getTownIDxx());
            loDetail.setMobileNo(foVal.getMobileNo());
            loDetail.setModified(AppConstants.DATE_MODIFIED());

            loDetail.setTranStat("1");
            loDetail.setRemCodex("LUn");
            loDetail.setRemarksx(foVal.getRemarksx());

            String lsImageID = poImage.SaveDcpImage(
                    foVal.getAccntNox(),
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
}
