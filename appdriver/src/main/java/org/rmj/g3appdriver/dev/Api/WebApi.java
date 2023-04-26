/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */

package org.rmj.g3appdriver.dev.Api;

import android.app.Application;

import org.rmj.g3appdriver.etc.AppConfigPreference;

public class WebApi {
    private static final String TAG = WebApi.class.getSimpleName();

    private final Application instance;
    private final AppConfigPreference poConfig;
    private final boolean isUnitTest;
    private final boolean isLiveData;

    public WebApi(Application instance) {
        this.instance = instance;
        this.poConfig = AppConfigPreference.getInstance(instance);
        this.isUnitTest = poConfig.getTestStatus();
        this.isLiveData = poConfig.isLiveDataServer();
    }


    private static final String LOCAL = "http://192.168.10.27/";
    private String LIVE = "";

    private static final String PRIMARY_LIVE = "https://restgk.guanzongroup.com.ph/";
    private static final String SECONDARY_LIVE = "https://restgk1.guanzongroup.com.ph/";

    private static final String URL_AUTH_EMPLOYEE = "security/mlogin.php";
    private static final String URL_CREATE_ACCOUNT = "security/signup.php";
    private static final String URL_FORGOT_PASSWORD = "security/forgotpswd.php";
    private static final String URL_CHANGE_PASSWORD = "security/acctupdate.php";
    private static final String URL_KNOX = "samsung/knox.php";
    private static final String IMPORT_BRANCH_PERFORMANCE = "integsys/bullseye/import_mc_branch_performance.php";
    private static final String IMPORT_AREA_PERFORMANCE = "integsys/bullseye/import_mc_area_performance.php";
    private static final String URL_IMPORT_BARANGAY = "integsys/param/download_barangay.php";
    private static final String URL_IMPORT_TOWN = "integsys/param/download_town.php";
    private static final String URL_IMPORT_PROVINCE = "integsys/param/download_province.php";
    private static final String URL_IMPORT_COUNTRY = "integsys/param/download_country.php";
    private static final String URL_IMPORT_MC_MODEL = "integsys/param/download_mc_model.php";
    private static final String URL_IMPORT_MC_MODEL_PRICE = "integsys/param/download_mc_model_price.php";
    private static final String URL_IMPORT_BRAND = "integsys/param/download_brand.php";
    private static final String URL_IMPORT_MC_CATEGORY = "integsys/param/download_mc_category.php";
    private static final String URL_IMPORT_TERM_CATEGORY = "integsys/param/download_mc_term_category.php";
    private static final String URL_IMPORT_BRANCHES = "integsys/param/download_branch.php";
    private static final String URL_IMPORT_FILE_CODE = "integsys/param/download_edoc_file_type.php";
    private static final String URL_IMPORT_OCCUPATIONS = "integsys/param/download_occupation.php";
    private static final String URL_SUBMIT_ONLINE_APPLICATION = "integsys/gocas/gocas_save.php";
    private static final String URL_GET_INSTALLMENT_TERMS = "integsys/gocas/import_installment_terms.php";
    private static final String URL_IMPORT_RAFFLE_BASIS = "promo/param/download_raffle_entry_basis_all.php";
    private static final String URL_REQUEST_ONLINE_APPLICATIONS = "integsys/gocas/gocas_request_status.php";
    private static final String URL_IMPORT_ONLINE_APPLICATIONS = "integsys/gocas/gocas_request_application.php";

    private static final String URL_SUBMIT_CASHCOUNT = "integsys/cashcount/submit_cash_count.php";
    private static final String URL_QUICK_SEARCH = "integsys/paramqry/cash_count_rqst_officer.php";

    private static final String URL_SAVE_ITINERARY = "engineering/submit_itinerary.php";
    private static final String URL_DOWNLOAD_ITINERARY = "engineering/download_itinerary.php";
    private static final String URL_DOWNLOAD_ITINERARY_USERS = "engineering/get_itinerary_users.php";
    private static final String URL_SCA_REQUEST = "integsys/param/download_sca_request.php";
    private static final String URL_SAVE_APPROVAL = "integsys/codeapproval/save_approval.php";
    private static final String URL_LOAD_APPLICATION_APPROVAL = "integsys/codeapproval/code_load.php";
    private static final String URL_APPLICATION_APPROVE = "integsys/codeapproval/code_decide.php";
    private static final String URL_DOWNLOAD_DCP = "integsys/dcp/dcp_download.php";
    private static final String URL_DCP_SUBMIT = "integsys/dcp/dcp_submit.php";
    private static final String URL_POST_DCP_MASTER = "integsys/dcp/dcp_post.php";
    private static final String URL_GET_AR_CLIENT = "query/client/get_ar_client.php";
    private static final String URL_GET_REG_CLIENT = "query/client/get_reg_client.php";
    private static final String URL_UPDATE_ADDRESS = "integsys/dcp/request_address_update.php";
    private static final String URL_UPDATE_MOBILE = "integsys/dcp/request_mobile_update.php";
    private static final String URL_DOWNLOAD_BANK_INFO = "integsys/param/download_banks.php";
    private static final String URL_POST_SELFIELOG = "integsys/hcm/selfie_log.php";
    private static final String URL_BRANCH_LOAN_APP = "integsys/param/download_credit_online_application_list.php";
    private static final String URL_DCP_REMITTANCE = "integsys/dcp/dcp_remit.php";
    private static final String URL_DCP_LOCATION_REPORT = "integsys/dcp/dcp_sumbit_coordinates.php";
    private static final String LOCAL_COORDINATES_TRACKER = "integsys/dcp/dcp_coordinates_receiver.php";
    private static final String URL_BRANCH_REMITTANCE_ACC = "integsys/param/download_branch_bank_account.php";
    private static final String URL_IMPORT_SYS_CONFIG = "integsys/param/download_system_config.php";
    private static final String URL_DOWNLOAD_CREDIT_ONLINE_APP = "integsys/param/download_credit_online_application_list.php";
    private static final String URL_DOWNLOAD_RELATION = "integsys/param/download_relation.php";
    private static final String URL_UPLOAD_CI_RESULT = "integsys/gocas/upload_ci_result.php";
    private static final String URL_SEND_RESPONSE = "nmm/send_response.php";
    private static final String URL_SEND_REQUEST = "nmm/send_request.php";
    private static final String URL_SEND_REQUEST_SYSTEM = "nmm/send_request_system.php";
    private static final String URL_KWIKSEARCH = "integsys/paramqry/cash_count_rqst_officer.php";
    private static final String URL_SEND_LEAVE_APPLICATION = "petmgr/send_leave_application.php";
    private static final String URL_GET_LEAVE_APPLICATION = "petmgr/get_leave_application.php";
    private static final String URL_CONFIRM_LEAVE_APPLICATION = "petmgr/confirm_leave_application.php";
    private static final String URL_SEND_OB_APPLICATION = "petmgr/send_ob_application.php";
    private static final String URL_GET_OB_APPLICATION = "petmgr/get_ob_application.php";
    private static final String URL_CONFIRM_OB_APPLICATION = "petmgr/confirm_ob_application.php";
    private static final String URL_REQUEST_RANDOM_STOCK_INVENTORY = "integsys/bullseye/random_stock_inventory_request.php";
    private static final String URL_SUBMIT_RANDOM_STOCK_INVENTORY = "integsys/bullseye/random_stock_inventory_submit.php";

    private static final String URL_REQUEST_FOR_EVALUATIONS = "integsys/gocas/ci_request_for_evaluations.php";
    private static final String URL_ADD_FOR_EVALUATION = "integsys/gocas/ci_add_for_evaluation.php";
    private static final String URL_DOWNLOAD_CREDIT_APP = "integsys/gocas/download_credit_app.php";
    private static final String URL_SUBMIT_RESULT = "integsys/gocas/ci_submit_result.php";
    private static final String URL_POST_CI_APPROVAL = "integsys/gocas/ci_submit_approval.php";
    private static final String URL_DOWNLOAD_BH_PREVIEW = "integsys/gocas/bh_request_evaluation_preview.php";
    private static final String URL_POST_BH_APPROVAL = "integsys/gocas/bh_submit_approval.php";

    private static final String REQUEST_USER_ACCESS = "security/request_android_object.php";

    private static final String URL_SUBMIT_APP_VERSION = "security/updateUserAppVersion.php";
    private static final String URL_VERSION_LOG = "query/get_version_info.php";
    private static final String URL_CHECK_UPDATE = "query/check_update.php";

    private static final String URL_DOWNLOAD_UPDATE = "https://restgk.guanzongroup.com.ph/apk/gCircle.apk";
    private static final String URL_DOWNLOAD_TEST_UPDATE = "https://restgk.guanzongroup.com.ph/apk/test/gRider.apk";

    private static final String GET_PANALO_REWARDS = "gconnect/upload/getUserPanalo.php";
    private static final String CLAIM_PANALO_REWARD = "gconnect/upload/getUserPanalo.php";
    private static final String GET_RAFFLE_PARTICIPANTS = "gconnect/upload/getUserPanalo.php";

    private static final String GET_PACITA_RULES = "gCircle/Pacita/import_pacita_rules.php";
    private static final String GET_PACITA_EVALUATIONS = "gCircle/Pacita/import_pacita_evaluations.php";
    private static final String SUBMIT_PACITA_RESULT = "gCircle/Pacita/submit_pacita_result.php";

    public String getUrlAuthEmployee(boolean isLiveData) {
        if(isUnitTest){
            return LOCAL + URL_AUTH_EMPLOYEE;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_AUTH_EMPLOYEE;
    }

    public String getUrlCreateAccount(boolean isLiveData) {
        if(isUnitTest){
            return LOCAL + URL_CREATE_ACCOUNT;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_CREATE_ACCOUNT;
    }

    public String getUrlForgotPassword(boolean isLiveData) {
        if(isUnitTest){
            return LOCAL + URL_FORGOT_PASSWORD;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_FORGOT_PASSWORD;
    }

    public String getUrlKnox(boolean isLiveData) {
        if(isUnitTest){
            return LOCAL + URL_KNOX;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_KNOX;
    }

    public String getUrlChangePassword(boolean isLiveData) {
        if(isUnitTest){
            return LOCAL + URL_CHANGE_PASSWORD;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_CHANGE_PASSWORD;
    }

    public String getImportBranchPerformance(boolean isLiveData) {
        if(isUnitTest){
            return LOCAL + IMPORT_BRANCH_PERFORMANCE;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + IMPORT_BRANCH_PERFORMANCE;
    }

    public String getImportAreaPerformance(boolean isLiveData) {
        if(isUnitTest){
            return LOCAL + IMPORT_AREA_PERFORMANCE;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + IMPORT_AREA_PERFORMANCE;
    }

    public String getUrlImportBarangay(boolean isLiveData) {
        if(isUnitTest){
            return LOCAL + URL_IMPORT_BARANGAY;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_IMPORT_BARANGAY;
    }

    public String getUrlImportTown(boolean isLiveData) {
        if(isUnitTest){
            return LOCAL + URL_IMPORT_TOWN;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_IMPORT_TOWN;
    }

    public String getUrlImportProvince(boolean isLiveData) {
        if(isUnitTest){
            return LOCAL + URL_IMPORT_PROVINCE;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_IMPORT_PROVINCE;
    }

    public String getUrlImportCountry(boolean isLiveData) {
        if(isUnitTest){
            return LOCAL + URL_IMPORT_COUNTRY;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_IMPORT_COUNTRY;
    }

    public String getUrlImportMcModel(boolean isLiveData) {
        if(isUnitTest){
            return LOCAL + URL_IMPORT_MC_MODEL;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_IMPORT_MC_MODEL;
    }

    public String getUrlImportMcModelPrice(boolean isLiveData) {
        if(isUnitTest){
            return LOCAL + URL_IMPORT_MC_MODEL_PRICE;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_IMPORT_MC_MODEL_PRICE;
    }

    public String getUrlImportBrand(boolean isLiveData) {
        if(isUnitTest){
            return LOCAL + URL_IMPORT_BRAND;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_IMPORT_BRAND;
    }

    public String getUrlImportMcCategory(boolean isLiveData) {
        if(isUnitTest){
            return LOCAL + URL_IMPORT_MC_CATEGORY;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_IMPORT_MC_CATEGORY;
    }

    public String getUrlImportTermCategory(boolean isLiveData) {
        if(isUnitTest){
            return LOCAL + URL_IMPORT_TERM_CATEGORY;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_IMPORT_TERM_CATEGORY;
    }

    public String getUrlImportBranches(boolean isLiveData) {
        if(isUnitTest){
            return LOCAL + URL_IMPORT_BRANCHES;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_IMPORT_BRANCHES;
    }

    public String getUrlImportFileCode(boolean isLiveData) {
        if(isUnitTest){
            return LOCAL + URL_IMPORT_FILE_CODE;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_IMPORT_FILE_CODE;
    }

    public String getUrlImportOccupations(boolean isLiveData) {
        if(isUnitTest){
            return LOCAL + URL_IMPORT_OCCUPATIONS;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_IMPORT_OCCUPATIONS;
    }

    public String getUrlSubmitOnlineApplication(boolean isLiveData) {
        if(isUnitTest){
            return LOCAL + URL_SUBMIT_ONLINE_APPLICATION;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_SUBMIT_ONLINE_APPLICATION;
    }

    public String getUrlImportRaffleBasis(boolean isLiveData) {
        if(isUnitTest){
            return LOCAL + URL_IMPORT_RAFFLE_BASIS;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_IMPORT_RAFFLE_BASIS;
    }

    public String getUrlRequestOnlineApplications(boolean isLiveData) {
        if(isUnitTest){
            return LOCAL + URL_REQUEST_ONLINE_APPLICATIONS;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_REQUEST_ONLINE_APPLICATIONS;
    }

    public String getUrlImportOnlineApplications(boolean isLiveData) {
        if(isUnitTest){
            return LOCAL + URL_IMPORT_ONLINE_APPLICATIONS;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_IMPORT_ONLINE_APPLICATIONS;
    }

    public String getUrlSubmitCashcount(boolean isLiveData) {
        if(isUnitTest){
            return LOCAL + URL_SUBMIT_CASHCOUNT;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_SUBMIT_CASHCOUNT;
    }

    public String getUrlQuickSearch(boolean isLiveData) {
        if(isUnitTest){
            return LOCAL + URL_QUICK_SEARCH;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_QUICK_SEARCH;
    }

    public String getUrlScaRequest(boolean isLiveData) {
        if(isUnitTest){
            return LOCAL + URL_SCA_REQUEST;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_SCA_REQUEST;
    }

    public String getUrlSaveApproval(boolean isLiveData) {
        if(isUnitTest){
            return LOCAL + URL_SAVE_APPROVAL;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_SAVE_APPROVAL;
    }

    public String getUrlLoadApplicationApproval(boolean isLiveData) {
        if(isUnitTest){
            return LOCAL + URL_LOAD_APPLICATION_APPROVAL;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_LOAD_APPLICATION_APPROVAL;
    }

    public String getUrlApplicationApprove(boolean isLiveData) {
        if(isUnitTest){
            return LOCAL + URL_APPLICATION_APPROVE;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_APPLICATION_APPROVE;
    }

    public String getUrlDownloadDcp(boolean isLiveData) {
        if(isUnitTest){
            return LOCAL + URL_DOWNLOAD_DCP;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_DOWNLOAD_DCP;
    }

    public String getUrlDcpSubmit(boolean isLiveData) {
        if(isUnitTest){
            return LOCAL + URL_DCP_SUBMIT;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_DCP_SUBMIT;
    }

    public String getUrlPostDcpMaster(boolean isLiveData) {
        if(isUnitTest){
            return LOCAL + URL_POST_DCP_MASTER;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_POST_DCP_MASTER;
    }

    public String getUrlGetArClient(boolean isLiveData) {
        if(isUnitTest){
            return LOCAL + URL_GET_AR_CLIENT;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_GET_AR_CLIENT;
    }

    public String getUrlGetRegClient(boolean isLiveData) {
        if(isUnitTest){
            return LOCAL + URL_GET_REG_CLIENT;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_GET_REG_CLIENT;
    }

    public String getUrlUpdateAddress(boolean isLiveData) {
        if(isUnitTest){
            return LOCAL + URL_UPDATE_ADDRESS;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_UPDATE_ADDRESS;
    }

    public String getUrlUpdateMobile(boolean isLiveData) {
        if(isUnitTest){
            return LOCAL + URL_UPDATE_MOBILE;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_UPDATE_MOBILE;
    }

    public String getUrlDownloadBankInfo(boolean isLiveData) {
        if(isUnitTest){
            return LOCAL + URL_DOWNLOAD_BANK_INFO;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_DOWNLOAD_BANK_INFO;
    }

    public String getUrlPostSelfielog(boolean isLiveData) {
        if(isUnitTest){
            return LOCAL + URL_POST_SELFIELOG;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_POST_SELFIELOG;
    }

    public String getUrlBranchLoanApp(boolean isLiveData) {
        if(isUnitTest){
            return LOCAL + URL_BRANCH_LOAN_APP;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_BRANCH_LOAN_APP;
    }

    public String getUrlDcpRemittance(boolean isLiveData) {
        if(isUnitTest){
            return LOCAL + URL_DCP_REMITTANCE;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_DCP_REMITTANCE;
    }

    public String getUrlDcpLocationReport(boolean isLiveData) {
        if(isUnitTest){
            return LOCAL + URL_DCP_LOCATION_REPORT;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_DCP_LOCATION_REPORT;
    }

    public String getUrlBranchRemittanceAcc(boolean isLiveData) {
        if(isUnitTest){
            return LOCAL + URL_BRANCH_REMITTANCE_ACC;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_BRANCH_REMITTANCE_ACC;
    }

    public String getUrlImportSysConfig(boolean isLiveData) {
        if(isUnitTest){
            return LOCAL + URL_IMPORT_SYS_CONFIG;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_IMPORT_SYS_CONFIG;
    }

    public String getUrlDownloadCreditOnlineApp(boolean isLiveData) {
        if(isUnitTest){
            return LOCAL + URL_DOWNLOAD_CREDIT_ONLINE_APP;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_DOWNLOAD_CREDIT_ONLINE_APP;
    }

    public String getUrlDownloadRelation(boolean isLiveData) {
        if(isUnitTest){
            return LOCAL + URL_DOWNLOAD_RELATION;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_DOWNLOAD_RELATION;
    }

    public String getUrlUploadCiResult(boolean isLiveData) {
        if(isUnitTest){
            return LOCAL + URL_UPLOAD_CI_RESULT;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_UPLOAD_CI_RESULT;
    }

    public String getUrlSendResponse(boolean isLiveData) {
        if(isUnitTest){
            return LOCAL + URL_SEND_RESPONSE;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_SEND_RESPONSE;
    }

    public String getUrlSendRequest(boolean isLiveData) {
        if(isUnitTest){
            return LOCAL + URL_SEND_REQUEST;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_SEND_REQUEST;
    }

    public String getUrlKwiksearch(boolean isLiveData) {
        if(isUnitTest){
            return LOCAL + URL_KWIKSEARCH;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_KWIKSEARCH;
    }

    public String getUrlSendLeaveApplication(boolean isLiveData) {
        if(isUnitTest){
            return LOCAL + URL_SEND_LEAVE_APPLICATION;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_SEND_LEAVE_APPLICATION;
    }

    public String getUrlGetLeaveApplication(boolean isLiveData) {
        if(isUnitTest){
            return LOCAL + URL_GET_LEAVE_APPLICATION;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_GET_LEAVE_APPLICATION;
    }

    public String getUrlConfirmLeaveApplication(boolean isLiveData) {
        if(isUnitTest){
            return LOCAL + URL_CONFIRM_LEAVE_APPLICATION;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_CONFIRM_LEAVE_APPLICATION;
    }

    public String getUrlSendObApplication(boolean isLiveData) {
        if(isUnitTest){
            return LOCAL + URL_SEND_OB_APPLICATION;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_SEND_OB_APPLICATION;
    }

    public String getUrlGetObApplication(boolean isLiveData) {
        if(isUnitTest){
            return LOCAL + URL_GET_OB_APPLICATION;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_GET_OB_APPLICATION;
    }

    public String getUrlConfirmObApplication(boolean isLiveData) {
        if(isUnitTest){
            return LOCAL + URL_CONFIRM_OB_APPLICATION;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_CONFIRM_OB_APPLICATION;
    }

    public String getUrlRequestRandomStockInventory(boolean isLiveData) {
        if(isUnitTest){
            return LOCAL + URL_REQUEST_RANDOM_STOCK_INVENTORY;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_REQUEST_RANDOM_STOCK_INVENTORY;
    }

    public String getUrlSubmitRandomStockInventory(boolean isLiveData) {
        if(isUnitTest){
            return LOCAL + URL_SUBMIT_RANDOM_STOCK_INVENTORY;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_SUBMIT_RANDOM_STOCK_INVENTORY;
    }

    public String getRequestUserAccess(boolean isLiveData) {
        if(isUnitTest){
            return LOCAL + REQUEST_USER_ACCESS;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + REQUEST_USER_ACCESS;
    }

    public String getUrlDownloadUpdate() {
        return URL_DOWNLOAD_UPDATE;
    }

    public String getUrlDownloadTestUpdate() {
        return URL_DOWNLOAD_TEST_UPDATE;
    }

    public String getUrlDownloadCIApplications(boolean isLiveData) {
        if(isUnitTest){
            return LOCAL + URL_REQUEST_FOR_EVALUATIONS;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_REQUEST_FOR_EVALUATIONS;
    }

    public String getUrlSubmitCIResult(boolean isLiveData) {
        if(isUnitTest){
            return LOCAL + URL_SUBMIT_RESULT;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_SUBMIT_RESULT;
    }

    public String getUrlPostCiApproval(boolean isLiveData){
        if(isUnitTest) {
            return LOCAL + URL_POST_CI_APPROVAL;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_POST_CI_APPROVAL;
    }

    public String getUrlPostBhApproval(boolean isLiveData){
        if(isUnitTest) {
            return LOCAL + URL_POST_BH_APPROVAL;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_POST_BH_APPROVAL;
    }

    public String getUrlDownloadBhPreview(boolean isLiveData){
        if(isUnitTest) {
            return LOCAL + URL_DOWNLOAD_BH_PREVIEW;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_DOWNLOAD_BH_PREVIEW;
    }

    public String getUrlAddForEvaluation(boolean isLiveData) {
        if(isUnitTest) {
            return LOCAL + URL_REQUEST_FOR_EVALUATIONS;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_REQUEST_FOR_EVALUATIONS;
    }

    public String getUrlDownloadCreditAppForCI(boolean isLiveData) {
        if(isUnitTest) {
            return LOCAL + URL_DOWNLOAD_CREDIT_APP;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_DOWNLOAD_CREDIT_APP;
    }
    public String getUrlSaveItinerary(boolean isLiveData) {
        if(isUnitTest) {
            return LOCAL + URL_SAVE_ITINERARY;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_SAVE_ITINERARY;
    }

    public String getUrlDownloadItinerary(boolean isLiveData) {
        if(isUnitTest) {
            return LOCAL + URL_DOWNLOAD_ITINERARY;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_DOWNLOAD_ITINERARY;
    }

    public String getUrlDownloadItineraryUsers(boolean isLiveData) {
        if(isUnitTest) {
            return LOCAL + URL_DOWNLOAD_ITINERARY_USERS;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_DOWNLOAD_ITINERARY_USERS;
    }

    public String getUrlSubmitLocationTrack(boolean isLiveData) {
        if(isUnitTest) {
            return LOCAL + LOCAL_COORDINATES_TRACKER;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + LOCAL_COORDINATES_TRACKER;
    }

    public String getUrlSubmitAppVersion(boolean isLiveData) {
        if(isUnitTest) {
            return LOCAL + URL_SUBMIT_APP_VERSION;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_SUBMIT_APP_VERSION;
    }

    public String getUrlVersionLog(boolean isLiveData) {
        if(isUnitTest) {
            return LOCAL + URL_VERSION_LOG;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_VERSION_LOG;
    }

    public String getUrlCheckUpdate(boolean isLiveData) {
        if(isUnitTest) {
            return LOCAL + URL_CHECK_UPDATE;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_CHECK_UPDATE;
    }

    public String getUrlGetPanaloRewards(boolean isLiveData) {
        if(isUnitTest) {
            return LOCAL + GET_PANALO_REWARDS;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + GET_PANALO_REWARDS;
    }

    public String getUrlGetRaffleParticipants(boolean isLiveData) {
        if(isUnitTest) {
            return LOCAL + GET_RAFFLE_PARTICIPANTS;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + GET_RAFFLE_PARTICIPANTS;
    }

    public String getUrlGetPacitaRules(boolean isLiveData) {
        if(isUnitTest) {
            return LOCAL + GET_PACITA_RULES;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + GET_PACITA_RULES;
    }

    public String getUrlGetPacitaEvaluations(boolean isLiveData) {
        if(isUnitTest) {
            return LOCAL + GET_PACITA_EVALUATIONS;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + GET_PACITA_EVALUATIONS;
    }

    public String getUrlSubmitPacitaResult(boolean isLiveData) {
        if(isUnitTest) {
            return LOCAL + SUBMIT_PACITA_RESULT;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + SUBMIT_PACITA_RESULT;
    }

    public String getGetInstallmentTerms(boolean isLiveData) {
        if(isUnitTest) {
            return LOCAL + URL_GET_INSTALLMENT_TERMS;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_GET_INSTALLMENT_TERMS;
    }

    public String getClaimPanaloReward(boolean isLiveData) {
        if(isUnitTest) {
            return LOCAL + CLAIM_PANALO_REWARD;
        } else if(isLiveData){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + CLAIM_PANALO_REWARD;
    }
}
