package org.rmj.g3appdriver.GCircle.Apps.Dcp.obj;

import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;
import android.util.Log;

import org.rmj.g3appdriver.GCircle.Apps.Dcp.model.LRDcp;
import org.rmj.g3appdriver.GCircle.Apps.Dcp.pojo.PromiseToPay;
import org.rmj.g3appdriver.GCircle.room.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.etc.AppConstants;

public class PTP extends LRDcp {
    private static final String TAG = PTP.class.getSimpleName();

    public PTP(Application instance) {
        super(instance);
    }

    @Override
    public boolean SaveTransaction(Object args) {
        try{
            PromiseToPay foVal = (PromiseToPay) args;
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

            loDetail.setRemCodex("PTP");
            loDetail.setPromised(foVal.getTransact());
            loDetail.setApntUnit(foVal.getPaymntxx());
            loDetail.setBranchCd(foVal.getBranchCd());
            loDetail.setTranStat("1");
            loDetail.setRemarksx(foVal.getRemarks());
            loDetail.setLatitude(foVal.getLatitude());
            loDetail.setLongitud(foVal.getLongtude());
            loDetail.setImageNme(foVal.getFileName());
            loDetail.setModified(AppConstants.DATE_MODIFIED());

            String lsImageID = poImage.SaveDcpImage(
                    foVal.getAccntNox(),
                    foVal.getTransNox(),
                    foVal.getFileName(),
                    foVal.getFilePath(),
                    String.valueOf(foVal.getLatitude()),
                    String.valueOf(foVal.getLongtude()));

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
