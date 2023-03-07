/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.g3appdriver.dev.Database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import org.rmj.g3appdriver.dev.Database.DataAccessObject.DAddressRequest;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DAddressUpdate;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DAppConfig;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DApprovalCode;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DAreaPerformance;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DBankInfo;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DBarangayInfo;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DBranchInfo;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DBranchLoanApplication;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DBranchOpeningMonitor;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DBranchPerformance;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DCIEvaluation;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DCashCount;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DClientInfo;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DClientUpdate;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DCountryInfo;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DCreditApplicantInfo;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DCreditApplication;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DCreditApplicationDocuments;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DCreditOnlineApplicationCI;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DDCPCollectionDetail;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DDCPCollectionMaster;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DDCP_Remittance;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DEmployeeBusinessTrip;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DEmployeeInfo;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DEmployeeLeave;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DEmployeeRole;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DFileCode;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DImageInfo;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DInventoryDao;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DInventoryDetail;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DInventoryMaster;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DItinerary;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DLRDcp;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DLocatorSysLog;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DMcBrand;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DMcCategory;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DMcModel;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DMcModelPrice;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DMcTermCategory;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DMobileRequest;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DMobileUpdate;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DNNDMRequest;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DNotificationDetail;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DNotificationMaster;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DNotificationUser;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DNotifications;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DOccupationInfo;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DPanalo;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DPayslip;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DProvinceInfo;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DRaffleInfo;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DRawDao;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DRelation;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DRemittanceAccounts;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DSelfieLog;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DSysConfig;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DToken;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DTownInfo;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DUncapturedClient;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DevTool;
import org.rmj.g3appdriver.dev.Database.Entities.EAddressUpdate;
import org.rmj.g3appdriver.dev.Database.Entities.EAppConfig;
import org.rmj.g3appdriver.dev.Database.Entities.EAreaPerformance;
import org.rmj.g3appdriver.dev.Database.Entities.EBankInfo;
import org.rmj.g3appdriver.dev.Database.Entities.EBarangayInfo;
import org.rmj.g3appdriver.dev.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.dev.Database.Entities.EBranchLoanApplication;
import org.rmj.g3appdriver.dev.Database.Entities.EBranchOpenMonitor;
import org.rmj.g3appdriver.dev.Database.Entities.EBranchPerformance;
import org.rmj.g3appdriver.dev.Database.Entities.ECIEvaluation;
import org.rmj.g3appdriver.dev.Database.Entities.ECashCount;
import org.rmj.g3appdriver.dev.Database.Entities.EClientInfo;
import org.rmj.g3appdriver.dev.Database.Entities.EClientUpdate;
import org.rmj.g3appdriver.dev.Database.Entities.ECodeApproval;
import org.rmj.g3appdriver.dev.Database.Entities.ECountryInfo;
import org.rmj.g3appdriver.dev.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.dev.Database.Entities.ECreditApplication;
import org.rmj.g3appdriver.dev.Database.Entities.ECreditApplicationDocuments;
import org.rmj.g3appdriver.dev.Database.Entities.ECreditOnlineApplicationCI;
import org.rmj.g3appdriver.dev.Database.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.dev.Database.Entities.EDCPCollectionMaster;
import org.rmj.g3appdriver.dev.Database.Entities.EDCP_Remittance;
import org.rmj.g3appdriver.dev.Database.Entities.EEmployeeBusinessTrip;
import org.rmj.g3appdriver.dev.Database.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.dev.Database.Entities.EEmployeeLeave;
import org.rmj.g3appdriver.dev.Database.Entities.EEmployeeRole;
import org.rmj.g3appdriver.dev.Database.Entities.EFileCode;
import org.rmj.g3appdriver.dev.Database.Entities.EGLocatorSysLog;
import org.rmj.g3appdriver.dev.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.dev.Database.Entities.EInventoryDetail;
import org.rmj.g3appdriver.dev.Database.Entities.EInventoryMaster;
import org.rmj.g3appdriver.dev.Database.Entities.EItinerary;
import org.rmj.g3appdriver.dev.Database.Entities.EMcBrand;
import org.rmj.g3appdriver.dev.Database.Entities.EMcCategory;
import org.rmj.g3appdriver.dev.Database.Entities.EMcModel;
import org.rmj.g3appdriver.dev.Database.Entities.EMcModelPrice;
import org.rmj.g3appdriver.dev.Database.Entities.EMcTermCategory;
import org.rmj.g3appdriver.dev.Database.Entities.EMobileUpdate;
import org.rmj.g3appdriver.dev.Database.Entities.ENNDMRequest;
import org.rmj.g3appdriver.dev.Database.Entities.ENotificationMaster;
import org.rmj.g3appdriver.dev.Database.Entities.ENotificationRecipient;
import org.rmj.g3appdriver.dev.Database.Entities.ENotificationUser;
import org.rmj.g3appdriver.dev.Database.Entities.EOccupationInfo;
import org.rmj.g3appdriver.dev.Database.Entities.EPanaloReward;
import org.rmj.g3appdriver.dev.Database.Entities.EProvinceInfo;
import org.rmj.g3appdriver.dev.Database.Entities.ERaffleBasis;
import org.rmj.g3appdriver.dev.Database.Entities.ERaffleInfo;
import org.rmj.g3appdriver.dev.Database.Entities.ERelation;
import org.rmj.g3appdriver.dev.Database.Entities.ERemittanceAccounts;
import org.rmj.g3appdriver.dev.Database.Entities.ESCA_Request;
import org.rmj.g3appdriver.dev.Database.Entities.ESelfieLog;
import org.rmj.g3appdriver.dev.Database.Entities.ESysConfig;
import org.rmj.g3appdriver.dev.Database.Entities.ETokenInfo;
import org.rmj.g3appdriver.dev.Database.Entities.ETownInfo;
import org.rmj.g3appdriver.dev.Database.Entities.EUncapturedClient;

@Database(entities = {
        EBranchInfo.class,
        EMcBrand.class,
        EMcModel.class,
        EMcCategory.class,
        EProvinceInfo.class,
        EMcModelPrice.class,
        ETownInfo.class,
        EBarangayInfo.class,
        EMcTermCategory.class,
        ECountryInfo.class,
        EOccupationInfo.class,
        ECreditApplication.class,
        ECreditApplicantInfo.class,
        EAreaPerformance.class,
        EBranchPerformance.class,
        ERaffleBasis.class,
        ERaffleInfo.class,
        ENotificationMaster.class,
        ENotificationRecipient.class,
        ENotificationUser.class,
        ECodeApproval.class,
        ESCA_Request.class,
        EDCPCollectionMaster.class,
        EDCPCollectionDetail.class,
        EAddressUpdate.class,
        EMobileUpdate.class,
        EImageInfo.class,
        EEmployeeInfo.class,
        EFileCode.class,
        EClientInfo.class,
        ETokenInfo.class,
        ECreditApplicationDocuments.class,
        EClientUpdate.class,
        EBankInfo.class,
        ESelfieLog.class,
        EBranchLoanApplication.class,
        EUncapturedClient.class,
        ECIEvaluation.class,
        EDCP_Remittance.class,
        ERemittanceAccounts.class,
        ESysConfig.class,
        EGLocatorSysLog.class,
        ERelation.class,
        ENNDMRequest.class,
        EAppConfig.class,
        ECashCount.class,
        EBranchOpenMonitor.class,
        EEmployeeLeave.class,
        EEmployeeBusinessTrip.class,
        EEmployeeRole.class,
        EInventoryMaster.class,
        EInventoryDetail.class,
        ECreditOnlineApplicationCI.class,
        EItinerary.class,
        EPanaloReward.class}, version = 36, exportSchema = false)
public abstract class GGC_GriderDB extends RoomDatabase {
    private static final String TAG = "GhostRider_DB_Manager";
    private static GGC_GriderDB instance;

    public abstract DEmployeeInfo EmployeeDao();
    public abstract DFileCode FileCodeDao();
    public abstract DClientInfo ClientDao();
    public abstract DBarangayInfo BarangayDao();
    public abstract DTownInfo TownDao();
    public abstract DProvinceInfo ProvinceDao();
    public abstract DCountryInfo CountryDao();
    public abstract DMcCategory McCategoryDao();
    public abstract DMcTermCategory McTermCategoryDao();
    public abstract DMcBrand McBrandDao();
    public abstract DMcModel McModelDao();
    public abstract DMcModelPrice McModelPriceDao();
    public abstract DBranchInfo BranchDao();
    public abstract DOccupationInfo OccupationDao();
    public abstract DCreditApplication CreditApplicationDao();
    public abstract DCreditApplicantInfo CreditApplicantDao();
    public abstract DAreaPerformance AreaPerformanceDao();
    public abstract DBranchPerformance BranchPerformanceDao();
    public abstract DRawDao RawDao();
    public abstract DRaffleInfo RaffleDao();
    public abstract DNotifications NotificationDao();
    public abstract DApprovalCode ApprovalDao();
    public abstract DLRDcp DcpDao();
    public abstract DDCPCollectionMaster DcpMasterDao();
    public abstract DDCPCollectionDetail DcpDetailDao();
    public abstract DAddressRequest AddressRequestDao();
    public abstract DAddressUpdate addressUpdateDao();
    public abstract DMobileUpdate mobileUpdateDao();
    public abstract DMobileRequest MobileRequestDao();
    public abstract DImageInfo ImageInfoDao();
    public abstract DCreditApplicationDocuments DocumentInfoDao();
    public abstract DClientUpdate ClientUpdateDao();
    public abstract DBankInfo BankInfoDao();
    public abstract DSelfieLog SelfieDao();
    public abstract DInventoryDao InventoryDao();
    public abstract DBranchLoanApplication CreditAppDocsDao();
    public abstract DUncapturedClient UncapturedDao();
    public abstract DCIEvaluation CIDao();
    public abstract DRelation RelDao();
    public abstract DDCP_Remittance DCPRemitanceDao();
    public abstract DRemittanceAccounts RemitanceAccDao();
    public abstract DSysConfig sysConfigDao();
    public abstract DLocatorSysLog locatorSysLogDao();
    public abstract DNNDMRequest nndmRequestDao();
    public abstract DAppConfig appConfigDao();
    public abstract DCashCount CashCountDao();
    public abstract DBranchOpeningMonitor openingMonitoryDao();
    public abstract DEmployeeLeave employeeLeaveDao();
    public abstract DEmployeeBusinessTrip employeeOBDao();
    public abstract DEmployeeRole employeeRoleDao();
    public abstract DInventoryMaster inventoryMasterDao();
    public abstract DInventoryDetail inventoryDetailDao();
    public abstract DCreditOnlineApplicationCI creditEvaluationDao();
    public abstract DItinerary itineraryDao();
    public abstract DToken dToken();
    public abstract DevTool devTool();
    public abstract DNotificationMaster nMasterDao();
    public abstract DNotificationDetail nDetailDao();
    public abstract DNotificationUser nUserDao();
    public abstract DPanalo panaloDao();
    public abstract DPayslip payslipDao();

    public static synchronized GGC_GriderDB getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                     GGC_GriderDB.class, "GGC_ISysDBF.db")
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
