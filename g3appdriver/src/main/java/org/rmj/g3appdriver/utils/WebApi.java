package org.rmj.g3appdriver.utils;

import android.content.Context;

import org.rmj.g3appdriver.etc.AppConfigPreference;

public class WebApi {

    /**Main URL
     * WEB SERVER*/

    //TODO: create a function that will change the server for testing phase.
    private static String URL_MAIN;
    private AppConfigPreference appConfigPreference;

    public WebApi(Context context){
        this.appConfigPreference = AppConfigPreference.getInstance(context);
    }

    /**GCARD MANAGEMENT*/
    private static String URL_GCARD = "gcard/";

    /**ACCOUNT SECURITY*/
    private static String URL_SECURITY = "security/";

    /**MS MANAGEMENT*/
    private static String URL_MS = "ms/";

    private static String URL_MX = "mx/";

    public String URL_REGISTRATION(){
        return appConfigPreference.getAppServer() + URL_SECURITY +"signup.php";
    }

    public String URL_AUTH_EMPLOYEE(){
        return appConfigPreference.getAppServer() + URL_SECURITY + "mlogin.php";
    }

    public String URL_SIGN_IN_USER(){
        return appConfigPreference.getAppServer() + URL_SECURITY + "signin.php";
    }

    public String URL_FORGOTPASSWORD(){
        return appConfigPreference.getAppServer() + URL_SECURITY + "forgotpswd.php";
    }

    /**FOR ADDING NEW GCARD NUMBER*/
    public String URL_ADD_NEW_GCARD(){
        return appConfigPreference.getAppServer() + URL_GCARD + URL_MS + "add_gcardnumber.php";
    }

    /**FOR IMPORTING PROMO LINKS AND IMAGES*/
    public String URL_IMPORT_PROMOLINK(){
        return appConfigPreference.getAppServer() + URL_GCARD + URL_MS + "import_promo_link.php";
    }

    /**FOR IMPORTING PLACE ORDERS*/
    public String URL_IMPORT_PLACE_ORDER(){
        return appConfigPreference.getAppServer() + URL_GCARD + URL_MS + "import_placed_orders.php";
    }

    /**FOR IMPORTING REDEEMABLES*/
    public String URL_IMPORT_REDEEM_ITEMS(){
        return appConfigPreference.getAppServer() + URL_GCARD + URL_MS + "import_redeem_item.php";
    }

    /**FOR IMPORTING TRANSACTIONS OFFLINE*/
    public String URL_IMPORT_TRANSACTIONS_OFFLINE(){
        return appConfigPreference.getAppServer() + URL_GCARD + URL_MS + "import_trans_offline.php";
    }

    /**FOR IMPORTING TRANSACTIONS ONLINE*/
    public String URL_IMPORT_TRANSACTIONS_ONLINE(){
        return appConfigPreference.getAppServer() + URL_GCARD + URL_MS + "import_trans_online.php";
    }

    /**FOR IMPORTING TRANSACTIONS PRE-ORDER*/
    public String URL_IMPORT_TRANSACTIONS_PREORDER(){
        return appConfigPreference.getAppServer() + URL_GCARD + URL_MS + "import_trans_preorder.php";
    }

    /**FOR IMPORTING TRANSACTIONS REDEMPTION*/
    public String URL_IMPORT_TRANSACTIONS_REDEMPTION(){
        return appConfigPreference.getAppServer() + URL_GCARD + URL_MS + "import_trans_redemption.php";
    }

    /**FOR IMPORTING MC REGISTRATION NOTICE*/
    public String URL_IMPORT_MC_REGISTRATION(){
        return appConfigPreference.getAppServer() + URL_GCARD + URL_MX + "import_registration.php";
    }

    /**FOR REQUESTING MC SERVICE STATUS*/
    public String URL_IMPORT_SERVICE(){
        return appConfigPreference.getAppServer() + URL_GCARD + URL_MX + "import_service.php";
    }

    /**FOR IMPORTING GUANZON MC AND MP BRANCHES*/
    public String URL_IMPORT_BRANCH(){
        return appConfigPreference.getAppServer() + URL_GCARD + URL_MS + "import_branch.php";
    }

    /**FOR IMPORTING USER GCARD*/
    public String URL_IMPORT_GCARD(){
        return appConfigPreference.getAppServer() + URL_GCARD + URL_MS + "import_gcard.php";
    }

    /**FOR REQUESTING AVAILABLE POINTS*/
    public String URL_REQUEST_AVAIL_POINTS(){
        return appConfigPreference.getAppServer() + URL_GCARD + URL_MS + "request_avl_points.php";
    }

    /**FOR PLACING A PRE ORDER TRANSACTION*/
    public String URL_PLACE_ODER(){
        return appConfigPreference.getAppServer() + URL_GCARD + URL_MS + "place_order.php";
    }

    /**FOR CANCELING PLACE ORDER*/
    public String URL_CANCEL_ORDER(){
        return appConfigPreference.getAppServer() + URL_GCARD + URL_MS + "cancel_order_item.php";
    }

    /**FOR ACCOUNT SETTINGS CHANGING PASSWORD*/
    public String URL_CHANGE_PASSWORD(){
        return appConfigPreference.getAppServer() + URL_SECURITY + "/acctupdate.php";
    }

    /**FOR ACCOUNT SETTINGS REQUEST LOGIN DEVICES*/
    public String URL_REQUEST_DEVICES(){
        return appConfigPreference.getAppServer() + URL_SECURITY + "/acctdevice.php";
    }

    /** For importing events*/
    public String URL_IMPORT_EVENTS(){
        return appConfigPreference.getAppServer() + URL_GCARD + URL_MX + "import_events.php";
    }

    public static String URL_KNOX = "https://restgk.guanzongroup.com.ph/samsung/knox.php";

    public static String IMPORT_BRANCH_PERFORMANCE = "https://restgk.guanzongroup.com.ph/integsys/bullseye/import_mc_branch_performance.php";
    public static String IMPORT_AREA_PERFORMANCE = "https://restgk.guanzongroup.com.ph/integsys/bullseye/import_mc_area_performance.php";

    public static String URL_IMPORT_BARANGAY = "https://restgk.guanzongroup.com.ph/integsys/param/download_barangay.php";
    public static String URL_IMPORT_TOWN = "https://restgk.guanzongroup.com.ph/integsys/param/download_town.php";
    public static String URL_IMPORT_PROVINCE = "https://restgk.guanzongroup.com.ph/integsys/param/download_province.php";
    public static String URL_IMPORT_COUNTRY = "https://restgk.guanzongroup.com.ph/integsys/param/download_country.php";
    public static String URL_IMPROT_RELIGION = "https://restgk.guanzongroup.com.ph/integsys/param/download_religion.php";
    public static String URL_IMPORT_MC_MODEL = "https://restgk.guanzongroup.com.ph/integsys/param/download_mc_model.php";
    public static String URL_IMPORT_MC_MODEL_PRICE = "https://restgk.guanzongroup.com.ph/integsys/param/download_mc_model_price.php";
    public static String URL_IMPORT_BRAND = "https://restgk.guanzongroup.com.ph/integsys/param/download_brand.php";
    public static String URL_IMPORT_MC_CATEGORY = "https://restgk.guanzongroup.com.ph/integsys/param/download_mc_category.php";
    public static String URL_IMPORT_TERM_CATEGORY = "https://restgk.guanzongroup.com.ph/integsys/param/download_mc_term_category.php";
    public static String URL_IMPORT_BRANCHES = "https://restgk.guanzongroup.com.ph/integsys/param/download_branch.php";
    public static String URL_IMPORT_OCCUPATIONS = "https://restgk.guanzongroup.com.ph/integsys/param/download_occupation.php";
    public static String URL_SUBMIT_ONLINE_APPLICATION = "https://restgk.guanzongroup.com.ph/integsys/gocas/gocas_save.php";
    public static String URL_REQUEST_ONLINE_APPLICATIONS = "https://restgk.guanzongroup.com.ph/integsys/gocas/gocas_request_status.php";
    public static String URL_IMPORT_ONLINE_APPLICATIONS = "https://restgk.guanzongroup.com.ph/integsys/gocas/gocas_request_application.php";
    public static String URL_IMPORT_C_I_APPLICATIONS = "https://restgk.guanzongroup.com.ph/integsys/";

    public static String URL_REQUEST_ORCR_STATUS = "https://restgk.guanzongroup.com.ph/integsys/registration/reqst_orcr_status.php";

    public static String URL_SUBMIT_CASHCOUNT = "https://restgk.guanzongroup.com.ph/integsys/cashcount/submit_cash_count.php";
    public static String URL_KWIKSEARCH = "https://restgk.guanzongroup.com.ph/integsys/paramqry/cash_count_rqst_officer.php";

    public static String URL_APPROVAL_USER_AUTH = "";

    public static String URL_SCA_REQUEST = "https://restgk.guanzongroup.com.ph/integsys/param/download_sca_request.php";
    public static String URL_SAVE_APPROVAL = "https://restgk.guanzongroup.com.ph/integsys/codeapproval/save_approval.php";
    public static String URL_LOAD_APPLICATION_APPROVAL = "https://restgk.guanzongroup.com.ph/integsys/codeapproval/code_load.php";
    public static String URL_APPLICATION_APPROVE = "https://restgk.guanzongroup.com.ph/integsys/codeapproval/code_decide.php";

    public static String URL_DOWNLOAD_DCP = "https://restgk.guanzongroup.com.ph/integsys/dcp/dcp_download.php";
}
