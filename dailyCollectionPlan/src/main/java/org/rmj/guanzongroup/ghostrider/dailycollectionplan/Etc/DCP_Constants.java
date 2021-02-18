package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Etc;

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
}
