package org.rmj.g3appdriver.GConnect.room;


import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import org.rmj.g3appdriver.GConnect.room.DataAccessObject.DAddress;
import org.rmj.g3appdriver.GConnect.room.DataAccessObject.DBranchInfo;
import org.rmj.g3appdriver.GConnect.room.DataAccessObject.DClientInfo;
import org.rmj.g3appdriver.GConnect.room.DataAccessObject.DEmployeeInfo;
import org.rmj.g3appdriver.GConnect.room.DataAccessObject.DEvents;
import org.rmj.g3appdriver.GConnect.room.DataAccessObject.DGCardTransactionLedger;
import org.rmj.g3appdriver.GConnect.room.DataAccessObject.DGcardApp;
import org.rmj.g3appdriver.GConnect.room.DataAccessObject.DItemCart;
import org.rmj.g3appdriver.GConnect.room.DataAccessObject.DMCSerialRegistration;
import org.rmj.g3appdriver.GConnect.room.DataAccessObject.DMobileAddressInfo;
import org.rmj.g3appdriver.GConnect.room.DataAccessObject.DNotifications;
import org.rmj.g3appdriver.GConnect.room.DataAccessObject.DOrderDetail;
import org.rmj.g3appdriver.GConnect.room.DataAccessObject.DOrder;
import org.rmj.g3appdriver.GConnect.room.DataAccessObject.DPanalo;
import org.rmj.g3appdriver.GConnect.room.DataAccessObject.DProduct;
import org.rmj.g3appdriver.GConnect.room.DataAccessObject.DPromo;
import org.rmj.g3appdriver.GConnect.room.DataAccessObject.DRawDao;
import org.rmj.g3appdriver.GConnect.room.DataAccessObject.DRedeemItemInfo;
import org.rmj.g3appdriver.GConnect.room.DataAccessObject.DRedeemablesInfo;
import org.rmj.g3appdriver.GConnect.room.DataAccessObject.DSearchLog;
import org.rmj.g3appdriver.GConnect.room.DataAccessObject.DServiceInfo;
import org.rmj.g3appdriver.GConnect.room.DataAccessObject.DUserInfo;
import org.rmj.g3appdriver.GConnect.room.Entities.EAddressInfo;
import org.rmj.g3appdriver.GConnect.room.Entities.EBarangayInfo;
import org.rmj.g3appdriver.GConnect.room.Entities.EBranchInfo;
import org.rmj.g3appdriver.GConnect.room.Entities.EClientInfo;
import org.rmj.g3appdriver.GConnect.room.Entities.ECountryInfo;
import org.rmj.g3appdriver.GConnect.room.Entities.EEmailInfo;
import org.rmj.g3appdriver.GConnect.room.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.GConnect.room.Entities.EEvents;
import org.rmj.g3appdriver.GConnect.room.Entities.EGCardLedger;
import org.rmj.g3appdriver.GConnect.room.Entities.EGcardApp;
import org.rmj.g3appdriver.GConnect.room.Entities.EGuanzonPanalo;
import org.rmj.g3appdriver.GConnect.room.Entities.EItemCart;
import org.rmj.g3appdriver.GConnect.room.Entities.EMCSerialRegistration;
import org.rmj.g3appdriver.GConnect.room.Entities.EMobileInfo;
import org.rmj.g3appdriver.GConnect.room.Entities.ENotificationMaster;
import org.rmj.g3appdriver.GConnect.room.Entities.ENotificationRecipient;
import org.rmj.g3appdriver.GConnect.room.Entities.ENotificationUser;
import org.rmj.g3appdriver.GConnect.room.Entities.EOrderDetail;
import org.rmj.g3appdriver.GConnect.room.Entities.EOrderMaster;
import org.rmj.g3appdriver.GConnect.room.Entities.EPanaloReward;
import org.rmj.g3appdriver.GConnect.room.Entities.EProducts;
import org.rmj.g3appdriver.GConnect.room.Entities.EPromo;
import org.rmj.g3appdriver.GConnect.room.Entities.EProvinceInfo;
import org.rmj.g3appdriver.GConnect.room.Entities.ERedeemItemInfo;
import org.rmj.g3appdriver.GConnect.room.Entities.ERedeemablesInfo;
import org.rmj.g3appdriver.GConnect.room.Entities.ESearchLog;
import org.rmj.g3appdriver.GConnect.room.Entities.EServiceInfo;
import org.rmj.g3appdriver.GConnect.room.Entities.ETokenInfo;
import org.rmj.g3appdriver.GConnect.room.Entities.ETownInfo;
import org.rmj.g3appdriver.GConnect.room.Entities.EUserInfo;

@Database(entities = {
        EEvents.class,
        EBranchInfo.class,
        EClientInfo.class,
        EGcardApp.class,
        EGCardLedger.class,
        EMCSerialRegistration.class,
        EPromo.class,
        ENotificationMaster.class,
        ENotificationRecipient.class,
        ENotificationUser.class,
        ERedeemablesInfo.class,
        ERedeemItemInfo.class,
        EServiceInfo.class,
        EEmployeeInfo.class,
        EUserInfo.class,
        ETokenInfo.class,
        EBarangayInfo.class,
        ETownInfo.class,
        EProvinceInfo.class,
        ECountryInfo.class,
        EMobileInfo.class,
        EAddressInfo.class,
        EProducts.class,
        EItemCart.class,
        EOrderDetail.class,
        EOrderMaster.class,
        ESearchLog.class,
        EEmailInfo.class,
        EPanaloReward.class,
        EGuanzonPanalo.class}, version = 2, exportSchema = false)
public abstract class GGC_GConnectDB extends RoomDatabase {
    private static final String TAG = "GuanzonApp_DB_Manager";
    private static GGC_GConnectDB instance;


//    public abstract DAppEventInfo EAppEventDao();
    public abstract DBranchInfo EBranchDao();
    public abstract DClientInfo EClientDao();
    public abstract DGcardApp EGcardAppDao();
    public abstract DGCardTransactionLedger gcardLedgerDao();
    public abstract DMCSerialRegistration EMCSerialRegistrationDao();
    public abstract DPromo EPromoDao();
    public abstract DRedeemablesInfo redeemablesDao();
    public abstract DRedeemItemInfo redeemedDao();
    public abstract DServiceInfo EServiceDao();
    public abstract DUserInfo EUserInfoDao();
    public abstract DRawDao RawDao();
    public abstract DEmployeeInfo EmployeeDao();
    public abstract DNotifications NotificationDao();
    public abstract DEvents EventDao();
    public abstract DAddress AddDao();
    public abstract DMobileAddressInfo mobAddDao();
    public abstract DProduct prodctDao();
    public abstract DItemCart itemCartDao();
    public abstract DOrder ordersDao();
    public abstract DOrderDetail orderDetailDao();
    public abstract DSearchLog searchDao();
    public abstract DPanalo panaloDao();

    public static synchronized GGC_GConnectDB getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    GGC_GConnectDB.class, "GGC_ISysDBF.db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .addCallback(roomCallBack)
                    .build();
        }
        return instance;
    }

    private static final Callback roomCallBack = new Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Log.e(TAG, "Local database has been created.");
        }
    };
}
