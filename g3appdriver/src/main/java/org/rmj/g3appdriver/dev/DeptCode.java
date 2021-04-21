package org.rmj.g3appdriver.dev;

public class DeptCode {

    public static String SALES = "015";
    public static String HUMAN_CAPITAL_MANAGEMENT = "021";
    public static String CREDIT_SUPPORT_SERVICES = "022";
    public static String FACILITY_AND_SECURITY_MANAGEMENT = "023";
    public static String SUPPLY_CHAIN_MANAGEMENT = "024";
    public static String MARKETING_AND_PROMOTIONS = "025";
    public static String MANAGEMENT_INFORMATION_SYSTEM = "026";
    public static String AFTER_SALES_MANAGEMENT = "027";
    public static String FINANCE_MANAGEMENT = "028";
    public static String RECORD_DEPOSITORY = "029";
    public static String DUTY_AND_REQUISITES_SERVICES = "030";
    public static String PRE_OWNED_MANAGEMENT = "031";
    public static String ENGINEERING_SERVICES = "032";
    public static String PROCUREMENT_SERVICES = "033";
    public static String COMPLIANCE_MANAGEMENT = "034";
    public static String TELEMARKETING_MANAGEMENT = "035";
    public static String MOBILE_PHONE = "036";
    public static String CELLPHONE_WAREHOUSE = "037";
    public static String MOBILE_PHONE_1 = "038";
    public static String AUTOGROUP = "039";
    public static String ADMINISTRATION = "040";
    public static String MP_WAREHOUSE = "041";
    public static String MP_AFTERSALES = "042";
    public static String SECURITY = "043";
    public static String HOUSEKEEPING = "044";
    public static String EXECUTIVE_DEPARTMENT = "045";
    public static String FNB_SERVICE = "046";
    public static String FNB_PRODUCTION = "047";
    public static String HUMAN_RESOURCES = "048";
    public static String FRONT_OFFICE = "049";
    public static String SALES_AND_MARKETING = "050";
    public static String ENGINEERING = "051";
    public static String OTHERS = "052";

    public static String getDepartmentName(String DeptCode){
        switch (DeptCode){
            case "015":
               return "SALES";
            case "021":
                return "HUMAN CAPITAL MANAGEMENT";
            case "022":
                return "CREDIT SUPPORT SERVICES";
            case "023":
                return "FACILITY AND SECURITY MANAGEMENT";
            case "024":
                return "SUPPLY CHAIN MANAGEMENT";
            case "025":
                return "MARKETING AND PROMOTIONS";
            case "026":
                return "MANAGEMENT INFORMATION SYSTEM";
            case "027":
                return "AFTER SALES MANAGEMENT";
            case "028":
                return "FINANCE MANAGEMENT";
            case "029":
                return "RECORD DEPOSITORY";
            case "030":
                return "DUTY AND REQUISITES SERVICES";
            case "031":
                return "PRE OWNED MANAGEMENT";
            case "032":
                return "ENGINEERING SERVICES";
            case "033":
                return "PROCUREMENT SERVICES";
            case "034":
                return "COMPLIANCE MANAGEMENT";
            case "035":
                return "TELEMARKETING MANAGEMENT";
            case "036":
                return "MOBILE PHONE";
            case "037":
                return "CELLPHONE WAREHOUSE";
            case "038":
                return "MOBILE PHONE 1";
            case "039":
                return "AUTOGROUP";
            case "040":
                return "ADMINISTRATION";
            case "041":
                return "MP WAREHOUSE";
            case "042":
                return "MP AFTERSALES";
            case "043":
                return "SECURITY";
            case "044":
                return "HOUSEKEEPING";
            case "045":
                return "EXECUTIVE DEPARTMENT";
            case "046":
                return "FNB SERVICE";
            case "047":
                return "FNB PRODUCTION";
            case "048":
                return "HUMAN RESOURCES";
            case "049":
                return "FRONT OFFICE";
            case "050":
                return "SALES AND MARKETING";
            case "051":
                return "ENGINEERING";
            case "052":
                return "OTHERS";
        }
        return "";
    }

    private static int RANK_FILE = 0;
    private static int SUPERVISOR = 1;
    private static int DEPARTMENT_HEAD = 2;
    private static int BEANCH_HEAD = 3;
    private static int AREA_MANAGER = 4;
    private static int GENERAL_MANAGER = 5;
}
