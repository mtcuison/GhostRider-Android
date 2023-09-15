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

package org.rmj.g3appdriver.GCircle.Etc;

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

    public static String getDepartmentCode(String DeptName){
        switch (DeptName){
            case "SALES":
                return "015";
            case "HUMAN CAPITAL MANAGEMENT":
                return "021";
            case "CREDIT SUPPORT SERVICES":
                return "022";
            case "FACILITY AND SECURITY MANAGEMENT":
                return "023";
            case "SUPPLY CHAIN MANAGEMENT":
                return "024";
            case "MARKETING AND PROMOTIONS":
                return "025";
            case "MANAGEMENT INFORMATION SYSTEM":
                return "026";
            case "AFTER SALES MANAGEMENT":
                return "027";
            case "FINANCE MANAGEMENT":
                return "028";
            case "RECORD DEPOSITORY":
                return "029";
            case "DUTY AND REQUISITES SERVICES":
                return "030";
            case "PRE OWNED MANAGEMENT":
                return "031";
            case "ENGINEERING SERVICES":
                return "032";
            case "PROCUREMENT SERVICES":
                return "033";
            case "COMPLIANCE MANAGEMENT":
                return "034";
            case "TELEMARKETING MANAGEMENT":
                return "035";
            case "MOBILE PHONE":
                return "036";
            case "CELLPHONE WAREHOUSE":
                return "037";
            case "MOBILE PHONE 1":
                return "038";
            case "AUTOGROUP":
                return "039";
            case "ADMINISTRATION":
                return "040";
            case "MP WAREHOUSE":
                return "041";
            case "MP AFTERSALES":
                return "042";
            case "SECURITY":
                return "043";
            case "HOUSEKEEPING":
                return "044";
            case "EXECUTIVE DEPARTMENT":
                return "045";
            case "FNB SERVICE":
                return "046";
            case "FNB PRODUCTION":
                return "047";
            case "HUMAN RESOURCES":
                return "048";
            case "FRONT OFFICE":
                return "049";
            case "SALES AND MARKETING":
                return "050";
            case "ENGINEERING":
                return "051";
            case "OTHERS":
                return "052";
        }
        return "";
    }

    public static int LEVEL_RANK_FILE = 0;
    public static int LEVEL_SUPERVISOR = 1;
    public static int LEVEL_DEPARTMENT_HEAD = 2;
    public static int LEVEL_BRANCH_HEAD = 3;
    public static int LEVEL_AREA_MANAGER = 4;
    public static int LEVEL_GENERAL_MANAGER = 5;

    public static String parseUserLevel(int level){
        switch (level){
            case 0:
                return "Rank File";
            case 1:
                return "Supervisor";
            case 2:
                return "Department Head";
            case 3:
                return "Branch Head";
            case 4:
                return "Area Manager";
            default:
                return "General Manager";
        }
    }

    public static String getUserLevelCode(String level){
        switch (level){
            case "Rank File":
                return "0";
            case "Supervisor":
                return "1";
            case "Department Head":
                return "2";
            case "Branch Head":
                return "3";
            case "Area Manager":
                return "4";
            default:
                return "5";
        }
    }

    public static final String[] Departments= {
                        "SALES",
                        "HUMAN CAPITAL MANAGEMENT",
                        "CREDIT SUPPORT SERVICES",
                        "FACILITY AND SECURITY MANAGEMENT",
                        "SUPPLY CHAIN MANAGEMENT",
                        "MARKETING AND PROMOTIONS",
                        "MANAGEMENT INFORMATION SYSTEM",
                        "AFTER SALES MANAGEMENT",
                        "FINANCE MANAGEMENT",
                        "RECORD DEPOSITORY",
                        "DUTY AND REQUISITES SERVICES",
                        "PRE OWNED MANAGEMENT",
                        "ENGINEERING SERVICES",
                        "PROCUREMENT SERVICES",
                        "COMPLIANCE MANAGEMENT",
                        "TELEMARKETING MANAGEMENT",
                        "MOBILE PHONE",
                        "CELLPHONE WAREHOUSE",
                        "MOBILE PHONE 1",
                        "AUTOGROUP",
                        "ADMINISTRATION",
                        "MP WAREHOUSE",
                        "MP AFTERSALES",
                        "SECURITY",
                        "HOUSEKEEPING",
                        "EXECUTIVE DEPARTMENT",
                        "FNB SERVICE",
                        "FNB PRODUCTION",
                        "HUMAN RESOURCES",
                        "FRONT OFFICE",
                        "SALES AND MARKETING",
                        "ENGINEERING",
                        "OTHERS"
    };

    public static final String[] Employee_Levels= {
            "Rank File",
            "Supervisor",
            "Department Head",
            "Branch Head",
            "Area Manager",
            "General Manager",
    };
}
