package org.rmj.g3appdriver.dev.Database.GConnect;


import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import org.rmj.g3appdriver.dev.Database.GConnect.DataAccessObject.DAddress;
import org.rmj.g3appdriver.dev.Database.GConnect.DataAccessObject.DBranchInfo;
import org.rmj.g3appdriver.dev.Database.GConnect.DataAccessObject.DClientInfo;
import org.rmj.g3appdriver.dev.Database.GConnect.DataAccessObject.DEmployeeInfo;
import org.rmj.g3appdriver.dev.Database.GConnect.DataAccessObject.DEvents;
import org.rmj.g3appdriver.dev.Database.GConnect.DataAccessObject.DGCardTransactionLedger;
import org.rmj.g3appdriver.dev.Database.GConnect.DataAccessObject.DGcardApp;
import org.rmj.g3appdriver.dev.Database.GConnect.DataAccessObject.DItemCart;
import org.rmj.g3appdriver.dev.Database.GConnect.DataAccessObject.DMCSerialRegistration;
import org.rmj.g3appdriver.dev.Database.GConnect.DataAccessObject.DMobileAddressInfo;
import org.rmj.g3appdriver.dev.Database.GConnect.DataAccessObject.DNotifications;
import org.rmj.g3appdriver.dev.Database.GConnect.DataAccessObject.DOrderDetail;
import org.rmj.g3appdriver.dev.Database.GConnect.DataAccessObject.DOrderMaster;
import org.rmj.g3appdriver.dev.Database.GConnect.DataAccessObject.DPanalo;
import org.rmj.g3appdriver.dev.Database.GConnect.DataAccessObject.DProduct;
import org.rmj.g3appdriver.dev.Database.GConnect.DataAccessObject.DPromo;
import org.rmj.g3appdriver.dev.Database.GConnect.DataAccessObject.DRawDao;
import org.rmj.g3appdriver.dev.Database.GConnect.DataAccessObject.DRedeemItemInfo;
import org.rmj.g3appdriver.dev.Database.GConnect.DataAccessObject.DRedeemablesInfo;
import org.rmj.g3appdriver.dev.Database.GConnect.DataAccessObject.DSearchLog;
import org.rmj.g3appdriver.dev.Database.GConnect.DataAccessObject.DServiceInfo;
import org.rmj.g3appdriver.dev.Database.GConnect.DataAccessObject.DUserInfo;
import org.rmj.g3appdriver.dev.Database.GConnect.Entities.EAddressInfo;
import org.rmj.g3appdriver.dev.Database.GConnect.Entities.EBarangayInfo;
import org.rmj.g3appdriver.dev.Database.GConnect.Entities.EBranchInfo;
import org.rmj.g3appdriver.dev.Database.GConnect.Entities.EClientInfo;
import org.rmj.g3appdriver.dev.Database.GConnect.Entities.ECountryInfo;
import org.rmj.g3appdriver.dev.Database.GConnect.Entities.EEmailInfo;
import org.rmj.g3appdriver.dev.Database.GConnect.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.dev.Database.GConnect.Entities.EEvents;
import org.rmj.g3appdriver.dev.Database.GConnect.Entities.EGCardTransactionLedger;
import org.rmj.g3appdriver.dev.Database.GConnect.Entities.EGcardApp;
import org.rmj.g3appdriver.dev.Database.GConnect.Entities.EGuanzonPanalo;
import org.rmj.g3appdriver.dev.Database.GConnect.Entities.EItemCart;
import org.rmj.g3appdriver.dev.Database.GConnect.Entities.EMCSerialRegistration;
import org.rmj.g3appdriver.dev.Database.GConnect.Entities.EMobileInfo;
import org.rmj.g3appdriver.dev.Database.GConnect.Entities.ENotificationMaster;
import org.rmj.g3appdriver.dev.Database.GConnect.Entities.ENotificationRecipient;
import org.rmj.g3appdriver.dev.Database.GConnect.Entities.ENotificationUser;
import org.rmj.g3appdriver.dev.Database.GConnect.Entities.EOrderDetail;
import org.rmj.g3appdriver.dev.Database.GConnect.Entities.EOrderMaster;
import org.rmj.g3appdriver.dev.Database.GConnect.Entities.EPanaloReward;
import org.rmj.g3appdriver.dev.Database.GConnect.Entities.EProducts;
import org.rmj.g3appdriver.dev.Database.GConnect.Entities.EPromo;
import org.rmj.g3appdriver.dev.Database.GConnect.Entities.EProvinceInfo;
import org.rmj.g3appdriver.dev.Database.GConnect.Entities.ERedeemItemInfo;
import org.rmj.g3appdriver.dev.Database.GConnect.Entities.ERedeemablesInfo;
import org.rmj.g3appdriver.dev.Database.GConnect.Entities.ESearchLog;
import org.rmj.g3appdriver.dev.Database.GConnect.Entities.EServiceInfo;
import org.rmj.g3appdriver.dev.Database.GConnect.Entities.ETokenInfo;
import org.rmj.g3appdriver.dev.Database.GConnect.Entities.ETownInfo;
import org.rmj.g3appdriver.dev.Database.GConnect.Entities.EUserInfo;

@Database(entities = {
        EEvents.class,
        EBranchInfo.class,
        EClientInfo.class,
        EGcardApp.class,
        EGCardTransactionLedger.class,
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
    public abstract DGCardTransactionLedger EGCardTransactionLedgerDao();
    public abstract DMCSerialRegistration EMCSerialRegistrationDao();
    public abstract DPromo EPromoDao();
    public abstract DRedeemablesInfo ERedeemablesDao();
    public abstract DRedeemItemInfo ERedeemItemDao();
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
    public abstract DOrderMaster orderMasterDao();
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
