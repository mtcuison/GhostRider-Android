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

package org.rmj.g3appdriver.GRider.Database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DAddressRequest;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DAppConfig;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DApprovalCode;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DAreaPerformance;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DBankInfo;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DBarangayInfo;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DBranchInfo;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DBranchLoanApplication;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DBranchPerformance;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DCIEvaluation;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DClientInfo;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DClientUpdate;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DCountryInfo;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DCreditApplicantInfo;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DCreditApplication;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DCreditApplicationDocuments;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DDCPCollectionMaster;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DDCP_Remittance;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DEmployeeInfo;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DFileCode;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DImageInfo;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DLocatorSysLog;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DLog_Selfie;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DMcBrand;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DMcCategory;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DMcModel;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DMcModelPrice;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DMcTermCategory;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DMobileRequest;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DNNDMRequest;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DNotifications;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DOccupationInfo;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DProvinceInfo;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DRaffleInfo;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DRawDao;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DRelation;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DRemittanceAccounts;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DSysConfig;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DTownInfo;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DUncapturedClient;
import org.rmj.g3appdriver.GRider.Database.Entities.EAddressUpdate;
import org.rmj.g3appdriver.GRider.Database.Entities.EAppConfig;
import org.rmj.g3appdriver.GRider.Database.Entities.EAreaPerformance;
import org.rmj.g3appdriver.GRider.Database.Entities.EBankInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EBarangayInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EBranchPerformance;
import org.rmj.g3appdriver.GRider.Database.Entities.ECIEvaluation;
import org.rmj.g3appdriver.GRider.Database.Entities.EClientInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EClientUpdate;
import org.rmj.g3appdriver.GRider.Database.Entities.ECodeApproval;
import org.rmj.g3appdriver.GRider.Database.Entities.ECountryInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EBranchLoanApplication;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplication;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplicationDocuments;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionMaster;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCP_Remittance;
import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EFileCode;
import org.rmj.g3appdriver.GRider.Database.Entities.EGLocatorSysLog;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.ELog_Selfie;
import org.rmj.g3appdriver.GRider.Database.Entities.EMcBrand;
import org.rmj.g3appdriver.GRider.Database.Entities.EMcCategory;
import org.rmj.g3appdriver.GRider.Database.Entities.EMcModel;
import org.rmj.g3appdriver.GRider.Database.Entities.EMcModelPrice;
import org.rmj.g3appdriver.GRider.Database.Entities.EMcTermCategory;
import org.rmj.g3appdriver.GRider.Database.Entities.EMobileUpdate;
import org.rmj.g3appdriver.GRider.Database.Entities.ENNDMRequest;
import org.rmj.g3appdriver.GRider.Database.Entities.ENotificationMaster;
import org.rmj.g3appdriver.GRider.Database.Entities.ENotificationRecipient;
import org.rmj.g3appdriver.GRider.Database.Entities.ENotificationUser;
import org.rmj.g3appdriver.GRider.Database.Entities.EOccupationInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EProvinceInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.ERaffleBasis;
import org.rmj.g3appdriver.GRider.Database.Entities.ERaffleInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.ERelation;
import org.rmj.g3appdriver.GRider.Database.Entities.ERemittanceAccounts;
import org.rmj.g3appdriver.GRider.Database.Entities.ESCA_Request;
import org.rmj.g3appdriver.GRider.Database.Entities.ESysConfig;
import org.rmj.g3appdriver.GRider.Database.Entities.ETokenInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.ETownInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EUncapturedClient;

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
        ELog_Selfie.class,
        EBranchLoanApplication.class,
        EUncapturedClient.class,
        ECIEvaluation.class,
        EDCP_Remittance.class,
        ERemittanceAccounts.class,
        ESysConfig.class,
        EGLocatorSysLog.class,
        ERelation.class,
        ENNDMRequest.class,
        EAppConfig.class}, version = 1, exportSchema = false)
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
    public abstract DDCPCollectionMaster DcpMasterDao();
    public abstract DDCPCollectionDetail DcpDetailDao();
    public abstract DAddressRequest AddressRequestDao();
    public abstract DMobileRequest MobileRequestDao();
    public abstract DImageInfo ImageInfoDao();
    public abstract DCreditApplicationDocuments DocumentInfoDao();
    public abstract DClientUpdate ClientUpdateDao();
    public abstract DBankInfo BankInfoDao();
    public abstract DLog_Selfie SelfieDao();
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

    private static final RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Log.e(TAG, "Local database has been created.");
        }
    };
}
