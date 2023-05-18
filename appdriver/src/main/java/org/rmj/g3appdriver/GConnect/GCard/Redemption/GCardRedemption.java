package org.rmj.g3appdriver.GConnect.GCard.Redemption;

import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;

import org.rmj.g3appdriver.GConnect.GCard.Redemption.pojo.CartItem;
import org.rmj.g3appdriver.GConnect.room.DataAccessObject.DRedeemItemInfo;
import org.rmj.g3appdriver.GConnect.room.Entities.ERedeemItemInfo;
import org.rmj.g3appdriver.GConnect.room.GGC_GConnectDB;
import org.rmj.g3appdriver.dev.encryp.CodeGenerator;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.etc.AppConstants;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class GCardRedemption {
    private static final String TAG = GCardRedemption.class.getSimpleName();

    private final Application instance;
    private final DRedeemItemInfo poDao;

    private String message;

    public GCardRedemption(Application instance) {
        this.instance = instance;
        this.poDao = GGC_GConnectDB.getInstance(instance).redeemedDao();
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

    public boolean AddToCart(CartItem item){
        try{
            if (isPointsValid(item.getTotalItemPoints())) {
                message = "Insufficient Points. You do not have enough points available for this transaction.";
                return false;
            }

            ERedeemItemInfo loItem = new ERedeemItemInfo();
            String lsTransNo = CreateUniqueID();
            loItem.setTransNox(lsTransNo);
            loItem.setGCardNox(poDao.getCardNox());
            loItem.setPromoIDx(item.getPromoIDx());
            loItem.setItemQtyx(item.getItemQtyx());
            loItem.setPointsxx(item.getPoints());
            loItem.setOrderedx(AppConstants.GCARD_DATE_TIME());
            loItem.setTranStat("0");
            loItem.setPlacOrdr("0");

            String lsTransNox = item.getTransNox();
            String lsPromoIDx = item.getPromoIDx();
            if(poDao.getRedeemableIfExist(lsTransNox, lsPromoIDx).size() > 0) {
                poDao.UpdateExistingItemOnCart(lsTransNox, lsPromoIDx, item.getItemQtyx());
            } else {
                poDao.insert(loItem);
            }

            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }

    public boolean UpdateCartItem(CartItem item){
        try{
            String lsTransNox = item.getTransNox();
            String lsPromoIDx = item.getPromoIDx();

            if (isPointsValid(item.getTotalItemPoints())) {
                message = "Insufficient Points. You do not have enough points available for this transaction.";
                return false;
            }

            poDao.UpdateExistingItemOnCart(lsTransNox, lsPromoIDx, item.getItemQtyx());
            return false;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }

    public boolean PlaceOrder(){
        try{

            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }

    public Bitmap GeneratePreOrderQrCode(String BatchNo){
        try {
            CodeGenerator loCode = new CodeGenerator();
            String lsTranTpe = "PREORDER";
            String lsDevIDxx = AppConfigPreference.getInstance(instance).getDeviceID();
            String lsCardNox = poDao.GetGCardNumber();
            String lsUserIDx = poDao.GetUserID();
            String lsMobilex = AppConfigPreference.getInstance(instance).getMobileNo();
            String lsDteTime = AppConstants.GCARD_DATE_TIME();
            double lsCardPts = poDao.GetRemainingGcardPoints();
            String lsBuildxx = Build.MODEL;
            String lsBatchNo = BatchNo;

            return loCode.generateQrCode(lsTranTpe,
                    lsDevIDxx,
                    lsCardNox,
                    lsUserIDx,
                    lsMobilex,
                    lsDteTime,
                    lsCardPts,
                    lsBuildxx,
                    lsBatchNo);
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return null;
        }
    }

    public boolean DeleteCartItem(String args){
        try{
            poDao.removeItemFromCart(args);
            return true;



        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }

    private boolean isPointsValid(double fnItemPts){
        String lsCrdNmbr = poDao.GetGCardNumber();
        double lnTotalPt = poDao.getGCardTotPoints(lsCrdNmbr);
        double lnOrderPt = poDao.getOrderPoints(lsCrdNmbr);
        double lnRmnPnts = Math.abs(lnTotalPt - lnOrderPt);

        if(fnItemPts > lnRmnPnts){
            return true;
        } else {
            return false;
        }
    }
}
