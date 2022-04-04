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

import android.content.Context;

import org.rmj.g3appdriver.etc.AppConfigPreference;

public class WebApi {

    private boolean isUnitTest = false;

    private static final String LOCAL = "http://192.168.10.141/";
    private static final String LIVE = "https://restgk.guanzongroup.com.ph/";

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

    private static final String REQUEST_USER_ACCESS = "security/request_android_object.php";

    private static final String URL_DOWNLOAD_UPDATE = "";
    private static final String URL_DOWNLOAD_TEST_UPDATE = "";

    public WebApi(boolean isTestCase){
        this.isUnitTest = isTestCase;
    }

    public String getUrlAuthEmployee() {
        if(isUnitTest){
            return LOCAL + URL_AUTH_EMPLOYEE;
        }
        return LIVE + URL_AUTH_EMPLOYEE;
    }

    public String getUrlCreateAccount() {
        if(isUnitTest){
            return LOCAL + URL_CREATE_ACCOUNT;
        }
        return LIVE + URL_CREATE_ACCOUNT;
    }

    public String getUrlForgotPassword() {
        if(isUnitTest){
            return LOCAL + URL_FORGOT_PASSWORD;
        }
        return LIVE + URL_FORGOT_PASSWORD;
    }

    public String getUrlKnox() {
        if(isUnitTest){
            return LOCAL + URL_KNOX;
        }
        return LIVE + URL_KNOX;
    }

    public String getUrlChangePassword() {
        if(isUnitTest){
            return LOCAL + URL_CHANGE_PASSWORD;
        }
        return LIVE + URL_CHANGE_PASSWORD;
    }

    public String getImportBranchPerformance() {
        if(isUnitTest){
            return LOCAL + IMPORT_BRANCH_PERFORMANCE;
        }
        return LIVE + IMPORT_BRANCH_PERFORMANCE;
    }

    public String getImportAreaPerformance() {
        if(isUnitTest){
            return LOCAL + IMPORT_AREA_PERFORMANCE;
        }
        return LIVE + IMPORT_AREA_PERFORMANCE;
    }

    public String getUrlImportBarangay() {
        if(isUnitTest){
            return LOCAL + URL_IMPORT_BARANGAY;
        }
        return LIVE + URL_IMPORT_BARANGAY;
    }

    public String getUrlImportTown() {
        if(isUnitTest){
            return LOCAL + URL_IMPORT_TOWN;
        }
        return LIVE + URL_IMPORT_TOWN;
    }

    public String getUrlImportProvince() {
        if(isUnitTest){
            return LOCAL + URL_IMPORT_PROVINCE;
        }
        return LIVE + URL_IMPORT_PROVINCE;
    }

    public String getUrlImportCountry() {
        if(isUnitTest){
            return LOCAL + URL_IMPORT_COUNTRY;
        }
        return LIVE + URL_IMPORT_COUNTRY;
    }

    public String getUrlImportMcModel() {
        if(isUnitTest){
            return LOCAL + URL_IMPORT_MC_MODEL;
        }
        return LIVE + URL_IMPORT_MC_MODEL;
    }

    public String getUrlImportMcModelPrice() {
        if(isUnitTest){
            return LOCAL + URL_IMPORT_MC_MODEL_PRICE;
        }
        return LIVE + URL_IMPORT_MC_MODEL_PRICE;
    }

    public String getUrlImportBrand() {
        if(isUnitTest){
            return LOCAL + URL_IMPORT_BRAND;
        }
        return LIVE + URL_IMPORT_BRAND;
    }

    public String getUrlImportMcCategory() {
        if(isUnitTest){
            return LOCAL + URL_IMPORT_MC_CATEGORY;
        }
        return LIVE + URL_IMPORT_MC_CATEGORY;
    }

    public String getUrlImportTermCategory() {
        if(isUnitTest){
            return LOCAL + URL_IMPORT_TERM_CATEGORY;
        }
        return LIVE + URL_IMPORT_TERM_CATEGORY;
    }

    public String getUrlImportBranches() {
        if(isUnitTest){
            return LOCAL + URL_IMPORT_BRANCHES;
        }
        return LIVE + URL_IMPORT_BRANCHES;
    }

    public String getUrlImportFileCode() {
        if(isUnitTest){
            return LOCAL + URL_IMPORT_FILE_CODE;
        }
        return LIVE + URL_IMPORT_FILE_CODE;
    }

    public String getUrlImportOccupations() {
        if(isUnitTest){
            return LOCAL + URL_IMPORT_OCCUPATIONS;
        }
        return LIVE + URL_IMPORT_OCCUPATIONS;
    }

    public String getUrlSubmitOnlineApplication() {
        if(isUnitTest){
            return LOCAL + URL_SUBMIT_ONLINE_APPLICATION;
        }
        return LIVE + URL_SUBMIT_ONLINE_APPLICATION;
    }

    public String getUrlImportRaffleBasis() {
        if(isUnitTest){
            return LOCAL + URL_IMPORT_RAFFLE_BASIS;
        }
        return LIVE + URL_IMPORT_RAFFLE_BASIS;
    }

    public String getUrlRequestOnlineApplications() {
        if(isUnitTest){
            return LOCAL + URL_REQUEST_ONLINE_APPLICATIONS;
        }
        return LIVE + URL_REQUEST_ONLINE_APPLICATIONS;
    }

    public String getUrlImportOnlineApplications() {
        if(isUnitTest){
            return LOCAL + URL_IMPORT_ONLINE_APPLICATIONS;
        }
        return LIVE + URL_IMPORT_ONLINE_APPLICATIONS;
    }

    public String getUrlSubmitCashcount() {
        if(isUnitTest){
            return LOCAL + URL_SUBMIT_CASHCOUNT;
        }
        return LIVE + URL_SUBMIT_CASHCOUNT;
    }

    public String getUrlQuickSearch() {
        if(isUnitTest){
            return LOCAL + URL_QUICK_SEARCH;
        }
        return LIVE + URL_QUICK_SEARCH;
    }

    public String getUrlScaRequest() {
        if(isUnitTest){
            return LOCAL + URL_SCA_REQUEST;
        }
        return LIVE + URL_SCA_REQUEST;
    }

    public String getUrlSaveApproval() {
        if(isUnitTest){
            return LOCAL + URL_SAVE_APPROVAL;
        }
        return LIVE + URL_SAVE_APPROVAL;
    }

    public String getUrlLoadApplicationApproval() {
        if(isUnitTest){
            return LOCAL + URL_LOAD_APPLICATION_APPROVAL;
        }
        return LIVE + URL_LOAD_APPLICATION_APPROVAL;
    }

    public String getUrlApplicationApprove() {
        if(isUnitTest){
            return LOCAL + URL_APPLICATION_APPROVE;
        }
        return LIVE + URL_APPLICATION_APPROVE;
    }

    public String getUrlDownloadDcp() {
        if(isUnitTest){
            return LOCAL + URL_DOWNLOAD_DCP;
        }
        return LIVE + URL_DOWNLOAD_DCP;
    }

    public String getUrlDcpSubmit() {
        if(isUnitTest){
            return LOCAL + URL_DCP_SUBMIT;
        }
        return LIVE + URL_DCP_SUBMIT;
    }

    public String getUrlPostDcpMaster() {
        if(isUnitTest){
            return LOCAL + URL_POST_DCP_MASTER;
        }
        return LIVE + URL_POST_DCP_MASTER;
    }

    public String getUrlGetArClient() {
        if(isUnitTest){
            return LOCAL + URL_GET_AR_CLIENT;
        }
        return LIVE + URL_GET_AR_CLIENT;
    }

    public String getUrlGetRegClient() {
        if(isUnitTest){
            return LOCAL + URL_GET_REG_CLIENT;
        }
        return LIVE + URL_GET_REG_CLIENT;
    }

    public String getUrlUpdateAddress() {
        if(isUnitTest){
            return LOCAL + URL_UPDATE_ADDRESS;
        }
        return LIVE + URL_UPDATE_ADDRESS;
    }

    public String getUrlUpdateMobile() {
        if(isUnitTest){
            return LOCAL + URL_UPDATE_MOBILE;
        }
        return LIVE + URL_UPDATE_MOBILE;
    }

    public String getUrlDownloadBankInfo() {
        if(isUnitTest){
            return LOCAL + URL_DOWNLOAD_BANK_INFO;
        }
        return LIVE + URL_DOWNLOAD_BANK_INFO;
    }

    public String getUrlPostSelfielog() {
        if(isUnitTest){
            return LOCAL + URL_POST_SELFIELOG;
        }
        return LIVE + URL_POST_SELFIELOG;
    }

    public String getUrlBranchLoanApp() {
        if(isUnitTest){
            return LOCAL + URL_BRANCH_LOAN_APP;
        }
        return LIVE + URL_BRANCH_LOAN_APP;
    }

    public String getUrlDcpRemittance() {
        if(isUnitTest){
            return LOCAL + URL_DCP_REMITTANCE;
        }
        return LIVE + URL_DCP_REMITTANCE;
    }

    public String getUrlDcpLocationReport() {
        if(isUnitTest){
            return LOCAL + URL_DCP_LOCATION_REPORT;
        }
        return LIVE + URL_DCP_LOCATION_REPORT;
    }

    public String getUrlBranchRemittanceAcc() {
        if(isUnitTest){
            return LOCAL + URL_BRANCH_REMITTANCE_ACC;
        }
        return LIVE + URL_BRANCH_REMITTANCE_ACC;
    }

    public String getUrlImportSysConfig() {
        if(isUnitTest){
            return LOCAL + URL_IMPORT_SYS_CONFIG;
        }
        return LIVE + URL_IMPORT_SYS_CONFIG;
    }

    public String getUrlDownloadCreditOnlineApp() {
        if(isUnitTest){
            return LOCAL + URL_DOWNLOAD_CREDIT_ONLINE_APP;
        }
        return LIVE + URL_DOWNLOAD_CREDIT_ONLINE_APP;
    }

    public String getUrlDownloadRelation() {
        if(isUnitTest){
            return LOCAL + URL_DOWNLOAD_RELATION;
        }
        return LIVE + URL_DOWNLOAD_RELATION;
    }

    public String getUrlUploadCiResult() {
        if(isUnitTest){
            return LOCAL + URL_UPLOAD_CI_RESULT;
        }
        return LIVE + URL_UPLOAD_CI_RESULT;
    }

    public String getUrlSendResponse() {
        if(isUnitTest){
            return LOCAL + URL_SEND_RESPONSE;
        }
        return LIVE + URL_SEND_RESPONSE;
    }

    public String getUrlSendRequest() {
        if(isUnitTest){
            return LOCAL + URL_SEND_REQUEST;
        }
        return LIVE + URL_SEND_REQUEST;
    }

    public String getUrlKwiksearch() {
        if(isUnitTest){
            return LOCAL + URL_KWIKSEARCH;
        }
        return LIVE + URL_KWIKSEARCH;
    }

    public String getUrlSendLeaveApplication() {
        if(isUnitTest){
            return LOCAL + URL_SEND_LEAVE_APPLICATION;
        }
        return LIVE + URL_SEND_LEAVE_APPLICATION;
    }

    public String getUrlGetLeaveApplication() {
        if(isUnitTest){
            return LOCAL + URL_GET_LEAVE_APPLICATION;
        }
        return LIVE + URL_GET_LEAVE_APPLICATION;
    }

    public String getUrlConfirmLeaveApplication() {
        if(isUnitTest){
            return LOCAL + URL_CONFIRM_LEAVE_APPLICATION;
        }
        return LIVE + URL_CONFIRM_LEAVE_APPLICATION;
    }

    public String getUrlSendObApplication() {
        if(isUnitTest){
            return LOCAL + URL_SEND_OB_APPLICATION;
        }
        return LIVE + URL_SEND_OB_APPLICATION;
    }

    public String getUrlGetObApplication() {
        if(isUnitTest){
            return LOCAL + URL_GET_OB_APPLICATION;
        }
        return LIVE + URL_GET_OB_APPLICATION;
    }

    public String getUrlConfirmObApplication() {
        if(isUnitTest){
            return LOCAL + URL_CONFIRM_OB_APPLICATION;
        }
        return LIVE + URL_CONFIRM_OB_APPLICATION;
    }

    public String getUrlRequestRandomStockInventory() {
        if(isUnitTest){
            return LOCAL + URL_REQUEST_RANDOM_STOCK_INVENTORY;
        }
        return LIVE + URL_REQUEST_RANDOM_STOCK_INVENTORY;
    }

    public String getUrlSubmitRandomStockInventory() {
        if(isUnitTest){
            return LOCAL + URL_SUBMIT_RANDOM_STOCK_INVENTORY;
        }
        return LIVE + URL_SUBMIT_RANDOM_STOCK_INVENTORY;
    }

    public String getRequestUserAccess() {
        if(isUnitTest){
            return LOCAL + REQUEST_USER_ACCESS;
        }
        return LIVE + REQUEST_USER_ACCESS;
    }

    public String getUrlDownloadUpdate() {
        return URL_DOWNLOAD_UPDATE;
    }

    public String getUrlDownloadTestUpdate() {
        return URL_DOWNLOAD_TEST_UPDATE;
    }

    public String getUrlCheckUpdate(){
        return "";
    }
}
