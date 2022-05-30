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

package org.rmj.g3appdriver.utils;

public class WebApi {

    private boolean isUnitTest = false;

    private static final String LOCAL = "http://192.168.10.141/";
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
    private static final String URL_IMPORT_RAFFLE_BASIS = "promo/param/download_raffle_entry_basis_all.php";
    private static final String URL_REQUEST_ONLINE_APPLICATIONS = "integsys/gocas/gocas_request_status.php";
    private static final String URL_IMPORT_ONLINE_APPLICATIONS = "integsys/gocas/gocas_request_application.php";

    private static final String URL_SUBMIT_CASHCOUNT = "integsys/cashcount/submit_cash_count.php";
    private static final String URL_QUICK_SEARCH = "integsys/paramqry/cash_count_rqst_officer.php";

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
    private static final String URL_BRANCH_REMITTANCE_ACC = "integsys/param/download_branch_bank_account.php";
    private static final String URL_IMPORT_SYS_CONFIG = "integsys/param/download_system_config.php";
    private static final String URL_DOWNLOAD_CREDIT_ONLINE_APP = "integsys/param/download_credit_online_application_list.php";
    private static final String URL_DOWNLOAD_RELATION = "integsys/param/download_relation.php";
    private static final String URL_UPLOAD_CI_RESULT = "integsys/gocas/upload_ci_result.php";
    private static final String URL_SEND_RESPONSE = "nmm/send_response.php";
    private static final String URL_SEND_REQUEST = "nmm/send_request.php";
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
    private static final String URL_SUBMIT_RESULT = "integsys/gocas/ci_submit_result.php";
    private static final String URL_POST_CI_APPROVAL = "integsys/gocas/ci_submit_approval.php";
    private static final String URL_DOWNLOAD_BH_PREVIEW = "integsys/gocas/bh_request_evaluation_preview.php";
    private static final String URL_POST_BH_APPROVAL = "integsys/gocas/bh_submit_approval.php";

    private static final String REQUEST_USER_ACCESS = "security/request_android_object.php";

    private static final String URL_DOWNLOAD_UPDATE = "https://restgk.guanzongroup.com.ph/apk/gRider.apk";
    private static final String URL_DOWNLOAD_TEST_UPDATE = "https://restgk.guanzongroup.com.ph/apk/test/gRider.apk";


    public WebApi(boolean isTestCase){
        this.isUnitTest = isTestCase;
    }

    public String getUrlAuthEmployee(boolean isBackUp) {
        if(isUnitTest){
            return LOCAL + URL_AUTH_EMPLOYEE;
        } else if(isBackUp){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_AUTH_EMPLOYEE;
    }

    public String getUrlCreateAccount(boolean isBackUp) {
        if(isUnitTest){
            return LOCAL + URL_CREATE_ACCOUNT;
        } else if(isBackUp){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_CREATE_ACCOUNT;
    }

    public String getUrlForgotPassword(boolean isBackUp) {
        if(isUnitTest){
            return LOCAL + URL_FORGOT_PASSWORD;
        } else if(isBackUp){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_FORGOT_PASSWORD;
    }

    public String getUrlKnox(boolean isBackUp) {
        if(isUnitTest){
            return LOCAL + URL_KNOX;
        } else if(isBackUp){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_KNOX;
    }

    public String getUrlChangePassword(boolean isBackUp) {
        if(isUnitTest){
            return LOCAL + URL_CHANGE_PASSWORD;
        } else if(isBackUp){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_CHANGE_PASSWORD;
    }

    public String getImportBranchPerformance(boolean isBackUp) {
        if(isUnitTest){
            return LOCAL + IMPORT_BRANCH_PERFORMANCE;
        } else if(isBackUp){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + IMPORT_BRANCH_PERFORMANCE;
    }

    public String getImportAreaPerformance(boolean isBackUp) {
        if(isUnitTest){
            return LOCAL + IMPORT_AREA_PERFORMANCE;
        } else if(isBackUp){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + IMPORT_AREA_PERFORMANCE;
    }

    public String getUrlImportBarangay(boolean isBackUp) {
        if(isUnitTest){
            return LOCAL + URL_IMPORT_BARANGAY;
        } else if(isBackUp){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_IMPORT_BARANGAY;
    }

    public String getUrlImportTown(boolean isBackUp) {
        if(isUnitTest){
            return LOCAL + URL_IMPORT_TOWN;
        } else if(isBackUp){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_IMPORT_TOWN;
    }

    public String getUrlImportProvince(boolean isBackUp) {
        if(isUnitTest){
            return LOCAL + URL_IMPORT_PROVINCE;
        } else if(isBackUp){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_IMPORT_PROVINCE;
    }

    public String getUrlImportCountry(boolean isBackUp) {
        if(isUnitTest){
            return LOCAL + URL_IMPORT_COUNTRY;
        } else if(isBackUp){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_IMPORT_COUNTRY;
    }

    public String getUrlImportMcModel(boolean isBackUp) {
        if(isUnitTest){
            return LOCAL + URL_IMPORT_MC_MODEL;
        } else if(isBackUp){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_IMPORT_MC_MODEL;
    }

    public String getUrlImportMcModelPrice(boolean isBackUp) {
        if(isUnitTest){
            return LOCAL + URL_IMPORT_MC_MODEL_PRICE;
        } else if(isBackUp){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_IMPORT_MC_MODEL_PRICE;
    }

    public String getUrlImportBrand(boolean isBackUp) {
        if(isUnitTest){
            return LOCAL + URL_IMPORT_BRAND;
        } else if(isBackUp){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_IMPORT_BRAND;
    }

    public String getUrlImportMcCategory(boolean isBackUp) {
        if(isUnitTest){
            return LOCAL + URL_IMPORT_MC_CATEGORY;
        } else if(isBackUp){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_IMPORT_MC_CATEGORY;
    }

    public String getUrlImportTermCategory(boolean isBackUp) {
        if(isUnitTest){
            return LOCAL + URL_IMPORT_TERM_CATEGORY;
        } else if(isBackUp){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_IMPORT_TERM_CATEGORY;
    }

    public String getUrlImportBranches(boolean isBackUp) {
        if(isUnitTest){
            return LOCAL + URL_IMPORT_BRANCHES;
        } else if(isBackUp){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_IMPORT_BRANCHES;
    }

    public String getUrlImportFileCode(boolean isBackUp) {
        if(isUnitTest){
            return LOCAL + URL_IMPORT_FILE_CODE;
        } else if(isBackUp){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_IMPORT_FILE_CODE;
    }

    public String getUrlImportOccupations(boolean isBackUp) {
        if(isUnitTest){
            return LOCAL + URL_IMPORT_OCCUPATIONS;
        } else if(isBackUp){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_IMPORT_OCCUPATIONS;
    }

    public String getUrlSubmitOnlineApplication(boolean isBackUp) {
        if(isUnitTest){
            return LOCAL + URL_SUBMIT_ONLINE_APPLICATION;
        } else if(isBackUp){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_SUBMIT_ONLINE_APPLICATION;
    }

    public String getUrlImportRaffleBasis(boolean isBackUp) {
        if(isUnitTest){
            return LOCAL + URL_IMPORT_RAFFLE_BASIS;
        } else if(isBackUp){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_IMPORT_RAFFLE_BASIS;
    }

    public String getUrlRequestOnlineApplications(boolean isBackUp) {
        if(isUnitTest){
            return LOCAL + URL_REQUEST_ONLINE_APPLICATIONS;
        } else if(isBackUp){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_REQUEST_ONLINE_APPLICATIONS;
    }

    public String getUrlImportOnlineApplications(boolean isBackUp) {
        if(isUnitTest){
            return LOCAL + URL_IMPORT_ONLINE_APPLICATIONS;
        } else if(isBackUp){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_IMPORT_ONLINE_APPLICATIONS;
    }

    public String getUrlSubmitCashcount(boolean isBackUp) {
        if(isUnitTest){
            return LOCAL + URL_SUBMIT_CASHCOUNT;
        } else if(isBackUp){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_SUBMIT_CASHCOUNT;
    }

    public String getUrlQuickSearch(boolean isBackUp) {
        if(isUnitTest){
            return LOCAL + URL_QUICK_SEARCH;
        } else if(isBackUp){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_QUICK_SEARCH;
    }

    public String getUrlScaRequest(boolean isBackUp) {
        if(isUnitTest){
            return LOCAL + URL_SCA_REQUEST;
        } else if(isBackUp){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_SCA_REQUEST;
    }

    public String getUrlSaveApproval(boolean isBackUp) {
        if(isUnitTest){
            return LOCAL + URL_SAVE_APPROVAL;
        } else if(isBackUp){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_SAVE_APPROVAL;
    }

    public String getUrlLoadApplicationApproval(boolean isBackUp) {
        if(isUnitTest){
            return LOCAL + URL_LOAD_APPLICATION_APPROVAL;
        } else if(isBackUp){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_LOAD_APPLICATION_APPROVAL;
    }

    public String getUrlApplicationApprove(boolean isBackUp) {
        if(isUnitTest){
            return LOCAL + URL_APPLICATION_APPROVE;
        } else if(isBackUp){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_APPLICATION_APPROVE;
    }

    public String getUrlDownloadDcp(boolean isBackUp) {
        if(isUnitTest){
            return LOCAL + URL_DOWNLOAD_DCP;
        } else if(isBackUp){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_DOWNLOAD_DCP;
    }

    public String getUrlDcpSubmit(boolean isBackUp) {
        if(isUnitTest){
            return LOCAL + URL_DCP_SUBMIT;
        } else if(isBackUp){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_DCP_SUBMIT;
    }

    public String getUrlPostDcpMaster(boolean isBackUp) {
        if(isUnitTest){
            return LOCAL + URL_POST_DCP_MASTER;
        } else if(isBackUp){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_POST_DCP_MASTER;
    }

    public String getUrlGetArClient(boolean isBackUp) {
        if(isUnitTest){
            return LOCAL + URL_GET_AR_CLIENT;
        } else if(isBackUp){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_GET_AR_CLIENT;
    }

    public String getUrlGetRegClient(boolean isBackUp) {
        if(isUnitTest){
            return LOCAL + URL_GET_REG_CLIENT;
        } else if(isBackUp){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_GET_REG_CLIENT;
    }

    public String getUrlUpdateAddress(boolean isBackUp) {
        if(isUnitTest){
            return LOCAL + URL_UPDATE_ADDRESS;
        } else if(isBackUp){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_UPDATE_ADDRESS;
    }

    public String getUrlUpdateMobile(boolean isBackUp) {
        if(isUnitTest){
            return LOCAL + URL_UPDATE_MOBILE;
        } else if(isBackUp){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_UPDATE_MOBILE;
    }

    public String getUrlDownloadBankInfo(boolean isBackUp) {
        if(isUnitTest){
            return LOCAL + URL_DOWNLOAD_BANK_INFO;
        } else if(isBackUp){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_DOWNLOAD_BANK_INFO;
    }

    public String getUrlPostSelfielog(boolean isBackUp) {
        if(isUnitTest){
            return LOCAL + URL_POST_SELFIELOG;
        } else if(isBackUp){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_POST_SELFIELOG;
    }

    public String getUrlBranchLoanApp(boolean isBackUp) {
        if(isUnitTest){
            return LOCAL + URL_BRANCH_LOAN_APP;
        } else if(isBackUp){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_BRANCH_LOAN_APP;
    }

    public String getUrlDcpRemittance(boolean isBackUp) {
        if(isUnitTest){
            return LOCAL + URL_DCP_REMITTANCE;
        } else if(isBackUp){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_DCP_REMITTANCE;
    }

    public String getUrlDcpLocationReport(boolean isBackUp) {
        if(isUnitTest){
            return LOCAL + URL_DCP_LOCATION_REPORT;
        } else if(isBackUp){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_DCP_LOCATION_REPORT;
    }

    public String getUrlBranchRemittanceAcc(boolean isBackUp) {
        if(isUnitTest){
            return LOCAL + URL_BRANCH_REMITTANCE_ACC;
        } else if(isBackUp){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_BRANCH_REMITTANCE_ACC;
    }

    public String getUrlImportSysConfig(boolean isBackUp) {
        if(isUnitTest){
            return LOCAL + URL_IMPORT_SYS_CONFIG;
        } else if(isBackUp){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_IMPORT_SYS_CONFIG;
    }

    public String getUrlDownloadCreditOnlineApp(boolean isBackUp) {
        if(isUnitTest){
            return LOCAL + URL_DOWNLOAD_CREDIT_ONLINE_APP;
        } else if(isBackUp){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_DOWNLOAD_CREDIT_ONLINE_APP;
    }

    public String getUrlDownloadRelation(boolean isBackUp) {
        if(isUnitTest){
            return LOCAL + URL_DOWNLOAD_RELATION;
        } else if(isBackUp){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_DOWNLOAD_RELATION;
    }

    public String getUrlUploadCiResult(boolean isBackUp) {
        if(isUnitTest){
            return LOCAL + URL_UPLOAD_CI_RESULT;
        } else if(isBackUp){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_UPLOAD_CI_RESULT;
    }

    public String getUrlSendResponse(boolean isBackUp) {
        if(isUnitTest){
            return LOCAL + URL_SEND_RESPONSE;
        } else if(isBackUp){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_SEND_RESPONSE;
    }

    public String getUrlSendRequest(boolean isBackUp) {
        if(isUnitTest){
            return LOCAL + URL_SEND_REQUEST;
        } else if(isBackUp){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_SEND_REQUEST;
    }

    public String getUrlKwiksearch(boolean isBackUp) {
        if(isUnitTest){
            return LOCAL + URL_KWIKSEARCH;
        } else if(isBackUp){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_KWIKSEARCH;
    }

    public String getUrlSendLeaveApplication(boolean isBackUp) {
        if(isUnitTest){
            return LOCAL + URL_SEND_LEAVE_APPLICATION;
        } else if(isBackUp){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_SEND_LEAVE_APPLICATION;
    }

    public String getUrlGetLeaveApplication(boolean isBackUp) {
        if(isUnitTest){
            return LOCAL + URL_GET_LEAVE_APPLICATION;
        } else if(isBackUp){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_GET_LEAVE_APPLICATION;
    }

    public String getUrlConfirmLeaveApplication(boolean isBackUp) {
        if(isUnitTest){
            return LOCAL + URL_CONFIRM_LEAVE_APPLICATION;
        } else if(isBackUp){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_CONFIRM_LEAVE_APPLICATION;
    }

    public String getUrlSendObApplication(boolean isBackUp) {
        if(isUnitTest){
            return LOCAL + URL_SEND_OB_APPLICATION;
        } else if(isBackUp){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_SEND_OB_APPLICATION;
    }

    public String getUrlGetObApplication(boolean isBackUp) {
        if(isUnitTest){
            return LOCAL + URL_GET_OB_APPLICATION;
        } else if(isBackUp){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_GET_OB_APPLICATION;
    }

    public String getUrlConfirmObApplication(boolean isBackUp) {
        if(isUnitTest){
            return LOCAL + URL_CONFIRM_OB_APPLICATION;
        } else if(isBackUp){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_CONFIRM_OB_APPLICATION;
    }

    public String getUrlRequestRandomStockInventory(boolean isBackUp) {
        if(isUnitTest){
            return LOCAL + URL_REQUEST_RANDOM_STOCK_INVENTORY;
        } else if(isBackUp){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_REQUEST_RANDOM_STOCK_INVENTORY;
    }

    public String getUrlSubmitRandomStockInventory(boolean isBackUp) {
        if(isUnitTest){
            return LOCAL + URL_SUBMIT_RANDOM_STOCK_INVENTORY;
        } else if(isBackUp){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_SUBMIT_RANDOM_STOCK_INVENTORY;
    }

    public String getRequestUserAccess(boolean isBackUp) {
        if(isUnitTest){
            return LOCAL + REQUEST_USER_ACCESS;
        } else if(isBackUp){
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
//    public String getUrlDownloadUpdate(boolean isBackUp) {
//        return URL_DOWNLOAD_UPDATE;
//    }
// else if(isBackUp){
//     LIVE = SECONDARY_LIVE;
//      else {
//          pLIVE = PRIMARY_LIVE;
//        }
//    }
//    public String getUrlDownloadTestUpdate(boolean isBackUp) {
//        return URL_DOWNLOAD_TEST_UPDATE;
//    }
// else if(isBackUp){
//     LIVE = SECONDARY_LIVE;
//      else {
//          public String getLIVE = PRIMARY_LIVE;UrlCheckU
//        }
//    }pdate(boolean isBackUp){
//        return "";
//    }
// else if(isBackUp){
//LIVE = SECONDARY_LIVE;
//    } else {
//     LIVE = PRIMARY_LIVE;
//    }

    public String getUrlDownloadCIApplications(boolean isBackUp) {
        if(isUnitTest){
            return LOCAL + URL_REQUEST_FOR_EVALUATIONS;
        } else if(isBackUp){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LOCAL + URL_REQUEST_FOR_EVALUATIONS;
    }

    public String getUrlSubmitCIResult(boolean isBackUp) {
        if(isUnitTest){
            return LOCAL + URL_SUBMIT_RESULT;
        } else if(isBackUp){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_SUBMIT_RESULT;
    }

    public String getUrlPostCiApproval(boolean isBackUp){
        if(isUnitTest) {
            return LOCAL + URL_POST_CI_APPROVAL;
        } else if(isBackUp){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_POST_CI_APPROVAL;
    }

    public String getUrlPostBhApproval(boolean isBackUp){
        if(isUnitTest) {
            return LOCAL + URL_POST_BH_APPROVAL;
        } else if(isBackUp){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_POST_BH_APPROVAL;
    }

    public String getUrlDownloadBhPreview(boolean isBackUp){
        if(isUnitTest) {
            return LOCAL + URL_DOWNLOAD_BH_PREVIEW;
        } else if(isBackUp){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_DOWNLOAD_BH_PREVIEW;
    }

    public String getUrlAddForEvaluation(boolean isBackUp) {
        if(isUnitTest) {
            return LOCAL + URL_ADD_FOR_EVALUATION;
        } else if(isBackUp){
            LIVE = SECONDARY_LIVE;
        } else {
            LIVE = PRIMARY_LIVE;
        }
        return LIVE + URL_ADD_FOR_EVALUATION;
    }
}
