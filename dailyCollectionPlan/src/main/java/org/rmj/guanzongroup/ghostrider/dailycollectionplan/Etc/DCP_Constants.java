package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Etc;

import android.graphics.Bitmap;

public class DCP_Constants {


    /*
    * PAY - Paid
    * PTP - Promise to Pay
    * CNA - Customer Not Around
    * FLA - For Legal Action
    * Car - Carnap
    * UNC - Uncooperative
    * MCs - Missing Customer
    * MUn - Missing Unit
    * MCU - Missing Client and Unit
    * LUn - Loan Unit
    * TA - Transferred/Assumed
    * FO - False Ownership
    * DNP - Did Not Pay
    * NV - Not Visited
    * OTH - Others
    */
//FILE CODE
    public static Bitmap selectedImageBitmap;
    public static String FOLDER_NAME = "DCP";
    public static String TRANSACT_PAY = "PAY";
    public static String TRANSACT_PTP = "PTP";
    public static String TRANSACT_FLA = "FLA";
    public static String TRANSACT_Car = "Car";
    public static String TRANSACT_UNC = "UNC";
    public static String TRANSACT_MCs = "MCs";
    public static String TRANSACT_MUn = "MUn";
    public static String TRANSACT_LUn = "LUn";
    public static String TRANSACT_TA = "TA";
    public static String TRANSACT_FO = "FO";
    public static String TRANSACT_DNP = "DNP";
    public static String TRANSACT_NV = "NV";
    public static String TRANSACT_OTH = "OTH";
    public static double varLatitude;
    public static double varLongitude;
    public static boolean saveStorage=false;

    public static String[] TRANSACTION_TYPE= {
            "Paid",
            "Promise to Pay",
            "Customer Not Around",
            "For Legal Action",
            "Car nap",
            "Uncooperative",
            "Missing Customer",
            "Missing Unit",
            "Missing Client and Unit",
            "Loan Unit",
            "Transferred/Assumed",
            "False Ownership",
            "Did not Pay",
            "Not Visited",
            "Others"};

    public static String[] PAYMENT_TYPE = {
            "Monthly Payment",
            "Cash Balance",
            "Penalty Payment",
            "Registration",
            "Insurance",
            "Change Class",
            "Deed Of Sale",
            "Release",
            "Miscellaneous"};

    public static String[] CIVIL_STATUS = {
            "Single",
            "Married",
            "Separated",
            "Widowed",
            "Single-Parent",
            "Single-Parent W/ Live-in Partner"};

    public static String[] REQUEST_CODE = {
            "Request Code",
            "New",
            "Update",
            "Change"
    };


    public static String getRemarksDescription(String fsCode){
        switch (fsCode){
            case "PAY":
                return "Paid";
            case "PTP":
                return "Promise to Pay";
            case "CNA":
                return "Customer Not Around";
            case "FLA":
                return "For Legal Action";
            case "Car":
                return "Carnap";
            case "UNC":
                return "Uncooperative";
            case "MCs":
                return "Missing Customer";
            case "MUn":
                return "Missing Unit";
            case "MCU":
                return "Missing Client and Unit";
            case "LUn":
                return "Loan Unit";
            case "TA":
                return "Transferred/Assumed";
            case "FO":
                return "False Ownership";
            case "DNP":
                return "Did Not Pay";
            case "NV":
                return "Not Visited";
            case "OTH":
                return "Others";
        }
        return "";
    }

    public static String getRemarksCode(String Description){
        switch (Description){
            case "Paid":
                return "PAY";
            case "Promise to Pay":
                return "PTP";
            case "Customer Not Around":
                return "CNA";
            case "For Legal Action":
                return "FLA";
            case "Carnap":
                return "Car";
            case "Uncooperative":
                return "UNC";
            case "Missing Customer":
                return "MCs";
            case "Missing Unit":
                return "MUn";
            case "Missing Client and Unit":
                return "MCU";
            case "Loan Unit":
                return "LUn";
            case "Transferred/Assumed":
                return "TA";
            case "False Ownership":
                return "FO";
            case "Did Not Pay":
                return "DNP";
            case "Not Visited":
                return "NV";
            case "Others":
                return "OTH";
        }
        return "";
    }
}
