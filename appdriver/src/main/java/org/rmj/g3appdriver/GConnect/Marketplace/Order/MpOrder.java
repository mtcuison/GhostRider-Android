package org.rmj.g3appdriver.GConnect.Marketplace.Order;

import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;

import org.rmj.g3appdriver.GConnect.Account.ClientSession;
import org.rmj.g3appdriver.GConnect.Api.GConnectApi;
import org.rmj.g3appdriver.GConnect.room.DataAccessObject.DOrder;
import org.rmj.g3appdriver.GConnect.room.Entities.EItemCart;
import org.rmj.g3appdriver.GConnect.room.GGC_GConnectDB;
import org.rmj.g3appdriver.dev.Api.HttpHeaders;
import org.rmj.g3appdriver.etc.AppConstants;

public class MpOrder {
    private static final String TAG = MpOrder.class.getSimpleName();

    private final Application instance;
    private final DOrder poDao;
    private final HttpHeaders poHeaders;
    private final GConnectApi poApi;
    private final ClientSession poSession;

    private String message;

    public MpOrder(Application instance) {
        this.instance = instance;
        this.poDao = GGC_GConnectDB.getInstance(instance).ordersDao();
        this.poHeaders = HttpHeaders.getInstance(instance);
        this.poApi = new GConnectApi(instance);
        this.poSession = ClientSession.getInstance(instance);
    }

    public String getMessage(){
        return message;
    }

    public boolean BuyNow(String fsLstngID, int fnQuantity){
        try {
            poDao.RemoveItemFromCart();
            EItemCart loItem = new EItemCart();
            loItem.setUserIDxx(poSession.getUserID());
            loItem.setListIDxx(fsLstngID);
            loItem.setQuantity(fnQuantity);
            loItem.setBuyNowxx("1");
            loItem.setCheckOut("1");
            loItem.setCreatedx(AppConstants.DATE_MODIFIED());
            loItem.setTimeStmp(AppConstants.DATE_MODIFIED());
            poDao.SaveItemInfo(loItem);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }
}
