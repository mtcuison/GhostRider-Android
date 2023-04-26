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

package org.rmj.g3appdriver.dev.Database.GCircle;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import org.rmj.g3appdriver.dev.Database.GCircle.DataAccessObject.DAddressRequest;
import org.rmj.g3appdriver.dev.Database.GCircle.DataAccessObject.DAddressUpdate;
import org.rmj.g3appdriver.dev.Database.GCircle.DataAccessObject.DAppConfig;
import org.rmj.g3appdriver.dev.Database.GCircle.DataAccessObject.DApprovalCode;
import org.rmj.g3appdriver.dev.Database.GCircle.DataAccessObject.DAreaPerformance;
import org.rmj.g3appdriver.dev.Database.GCircle.DataAccessObject.DBankInfo;
import org.rmj.g3appdriver.dev.Database.GCircle.DataAccessObject.DBarangayInfo;
import org.rmj.g3appdriver.dev.Database.GCircle.DataAccessObject.DBranchInfo;
import org.rmj.g3appdriver.dev.Database.GCircle.DataAccessObject.DBranchLoanApplication;
import org.rmj.g3appdriver.dev.Database.GCircle.DataAccessObject.DBranchOpeningMonitor;
import org.rmj.g3appdriver.dev.Database.GCircle.DataAccessObject.DBranchPerformance;
import org.rmj.g3appdriver.dev.Database.GCircle.DataAccessObject.DCIEvaluation;
import org.rmj.g3appdriver.dev.Database.GCircle.DataAccessObject.DCashCount;
import org.rmj.g3appdriver.dev.Database.GCircle.DataAccessObject.DClientInfo;
import org.rmj.g3appdriver.dev.Database.GCircle.DataAccessObject.DClientUpdate;
import org.rmj.g3appdriver.dev.Database.GCircle.DataAccessObject.DCountryInfo;
import org.rmj.g3appdriver.dev.Database.GCircle.DataAccessObject.DCreditApplicantInfo;
import org.rmj.g3appdriver.dev.Database.GCircle.DataAccessObject.DCreditApplication;
import org.rmj.g3appdriver.dev.Database.GCircle.DataAccessObject.DCreditApplicationDocuments;
import org.rmj.g3appdriver.dev.Database.GCircle.DataAccessObject.DCreditOnlineApplicationCI;
import org.rmj.g3appdriver.dev.Database.GCircle.DataAccessObject.DDCPCollectionDetail;
import org.rmj.g3appdriver.dev.Database.GCircle.DataAccessObject.DDCPCollectionMaster;
import org.rmj.g3appdriver.dev.Database.GCircle.DataAccessObject.DDCP_Remittance;
import org.rmj.g3appdriver.dev.Database.GCircle.DataAccessObject.DEmployeeBusinessTrip;
import org.rmj.g3appdriver.dev.Database.GCircle.DataAccessObject.DEmployeeInfo;
import org.rmj.g3appdriver.dev.Database.GCircle.DataAccessObject.DEmployeeLeave;
import org.rmj.g3appdriver.dev.Database.GCircle.DataAccessObject.DEmployeeRole;
import org.rmj.g3appdriver.dev.Database.GCircle.DataAccessObject.DFileCode;
import org.rmj.g3appdriver.dev.Database.GCircle.DataAccessObject.DImageInfo;
import org.rmj.g3appdriver.dev.Database.GCircle.DataAccessObject.DInventoryDao;
import org.rmj.g3appdriver.dev.Database.GCircle.DataAccessObject.DInventoryDetail;
import org.rmj.g3appdriver.dev.Database.GCircle.DataAccessObject.DInventoryMaster;
import org.rmj.g3appdriver.dev.Database.GCircle.DataAccessObject.DItinerary;
import org.rmj.g3appdriver.dev.Database.GCircle.DataAccessObject.DLRDcp;
import org.rmj.g3appdriver.dev.Database.GCircle.DataAccessObject.DLocatorSysLog;
import org.rmj.g3appdriver.dev.Database.GCircle.DataAccessObject.DMcBrand;
import org.rmj.g3appdriver.dev.Database.GCircle.DataAccessObject.DMcCategory;
import org.rmj.g3appdriver.dev.Database.GCircle.DataAccessObject.DMcModel;
import org.rmj.g3appdriver.dev.Database.GCircle.DataAccessObject.DMcModelPrice;
import org.rmj.g3appdriver.dev.Database.GCircle.DataAccessObject.DMcTermCategory;
import org.rmj.g3appdriver.dev.Database.GCircle.DataAccessObject.DMessages;
import org.rmj.g3appdriver.dev.Database.GCircle.DataAccessObject.DMobileRequest;
import org.rmj.g3appdriver.dev.Database.GCircle.DataAccessObject.DMobileUpdate;
import org.rmj.g3appdriver.dev.Database.GCircle.DataAccessObject.DNNDMRequest;
import org.rmj.g3appdriver.dev.Database.GCircle.DataAccessObject.DNotification;
import org.rmj.g3appdriver.dev.Database.GCircle.DataAccessObject.DNotificationDetail;
import org.rmj.g3appdriver.dev.Database.GCircle.DataAccessObject.DNotificationMaster;
import org.rmj.g3appdriver.dev.Database.GCircle.DataAccessObject.DNotificationReceiver;
import org.rmj.g3appdriver.dev.Database.GCircle.DataAccessObject.DNotificationUser;
import org.rmj.g3appdriver.dev.Database.GCircle.DataAccessObject.DOccupationInfo;
import org.rmj.g3appdriver.dev.Database.GCircle.DataAccessObject.DPacita;
import org.rmj.g3appdriver.dev.Database.GCircle.DataAccessObject.DPanalo;
import org.rmj.g3appdriver.dev.Database.GCircle.DataAccessObject.DPayslip;
import org.rmj.g3appdriver.dev.Database.GCircle.DataAccessObject.DProvinceInfo;
import org.rmj.g3appdriver.dev.Database.GCircle.DataAccessObject.DRaffleInfo;
import org.rmj.g3appdriver.dev.Database.GCircle.DataAccessObject.DRaffleStatus;
import org.rmj.g3appdriver.dev.Database.GCircle.DataAccessObject.DRawDao;
import org.rmj.g3appdriver.dev.Database.GCircle.DataAccessObject.DRelation;
import org.rmj.g3appdriver.dev.Database.GCircle.DataAccessObject.DRemittanceAccounts;
import org.rmj.g3appdriver.dev.Database.GCircle.DataAccessObject.DSelfieLog;
import org.rmj.g3appdriver.dev.Database.GCircle.DataAccessObject.DSysConfig;
import org.rmj.g3appdriver.dev.Database.GCircle.DataAccessObject.DToken;
import org.rmj.g3appdriver.dev.Database.GCircle.DataAccessObject.DTownInfo;
import org.rmj.g3appdriver.dev.Database.GCircle.DataAccessObject.DUncapturedClient;
import org.rmj.g3appdriver.dev.Database.GCircle.DataAccessObject.DevTool;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.EAddressUpdate;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.EAppConfig;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.EAreaPerformance;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.EBankInfo;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.EBarangayInfo;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.EBranchInfo;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.EBranchLoanApplication;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.EBranchOpenMonitor;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.EBranchPerformance;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.ECIEvaluation;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.ECashCount;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.EClientInfo;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.EClientUpdate;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.ECodeApproval;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.ECountryInfo;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.ECreditApplication;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.ECreditApplicationDocuments;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.ECreditOnlineApplicationCI;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.EDCPCollectionMaster;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.EDCP_Remittance;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.EEmployeeBusinessTrip;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.EEmployeeLeave;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.EEmployeeRole;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.EFileCode;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.EGLocatorSysLog;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.EImageInfo;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.EInventoryDetail;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.EInventoryMaster;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.EItinerary;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.ELoanTerm;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.EMcBrand;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.EMcCategory;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.EMcModel;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.EMcModelPrice;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.EMcTermCategory;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.EMobileUpdate;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.ENNDMRequest;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.ENotificationMaster;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.ENotificationRecipient;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.ENotificationUser;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.EOccupationInfo;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.EPacitaEvaluation;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.EPacitaRule;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.EPanaloReward;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.EProvinceInfo;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.ERaffleBasis;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.ERaffleInfo;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.ERaffleStatus;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.ERelation;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.ERemittanceAccounts;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.ESCA_Request;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.ESelfieLog;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.ESysConfig;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.ETokenInfo;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.ETownInfo;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.EUncapturedClient;

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
        EPanaloReward.class,
        ERaffleStatus.class,
        EPacitaRule.class,
        EPacitaEvaluation.class,
        ELoanTerm.class}, version = 38, exportSchema = false)
public abstract class GGC_GCircleDB extends RoomDatabase {
    private static final String TAG = "GhostRider_DB_Manager";
    private static GGC_GCircleDB instance;

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
    public abstract DNotificationReceiver ntfReceiverDao();
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
    public abstract DRaffleStatus raffleStatusDao();
    public abstract DMessages messagesDao();
    public abstract DNotification notificationDao();
    public abstract DPacita pacitaDao();

    public static synchronized GGC_GCircleDB getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                     GGC_GCircleDB.class, "GGC_ISysDBF.db")
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
