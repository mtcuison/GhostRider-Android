package org.rmj.g3appdriver.GCircle.Apps.CreditInvestigator.pojo;

import org.rmj.g3appdriver.etc.FormatUIText;

public class oChildFndg {

    private final String sLabelx;
    private final String sKeyxxx;
    private String sValxxx;

    /**
     *
     * @param Label Name of field to be update base on Local Database
     * @param KeyName Name of key to be update
     * @param Value Value of key
     *              -1.00 Or -1 for checking
     *              -10 Ignore
     *              10 = true;
     *              20 = false;
     */
    public oChildFndg(String Label,String KeyName, String Value) {
        this.sLabelx = Label;
        this.sKeyxxx = KeyName;
        this.sValxxx = Value;
    }

    public String getLabel() {
        double lsResult;
        double lnVal;
        String lsValue;
        if(sLabelx.equalsIgnoreCase("1")){
            lsValue = "YES";
        } else {
            lsValue = "NO";
        }
        switch (sKeyxxx){
            case "sProprty1":
                return "Property 1";
            case "sProprty2":
                return "Property 2";
            case "sProprty3":
                return "Property 3";
            case "cWith4Whl":
                return "Has 4 Wheeled Vehicle : "  + lsValue;
            case "cWith3Whl":
                return "Has 3 Wheel Vehicle(Tricycle) : " + lsValue;
            case "cWith2Whl":
                return "Has Bicycles/Motorcycles : " + lsValue;
            case "cWithRefx":
                return "Has Refrigerator : " + lsValue;
            case "cWithTVxx":
                return "Has Television : " + lsValue;
            case "cWithACxx":
                return "Has air condition : " + lsValue;
            case "nLenServc":
                lnVal = Double.parseDouble(sLabelx);
                if(lnVal % 1 == 0) {
                    lsValue = "Year/s";
                    lsResult =  lnVal;
                } else {
                    lsValue = "Month/s";
                    lnVal = lnVal * 12;
                    lsResult = (double) Math.round(lnVal);
                }
                return "Length Of Service : " + lsResult + lsValue;
            case "nBusLenxx":
                lnVal = Double.parseDouble(sLabelx);
                if(lnVal % 1 == 0) {
                    lsResult =  lnVal;
                } else {
                    lnVal = lnVal * 12;
                    lsResult = (double) Math.round(lnVal);
                }
                return "Years of Business : " + lsResult + lsValue;
            case "nSalaryxx":
                return "Salary : " + FormatUIText.getCurrencyUIFormat(sLabelx);
            case "nBusIncom":
                return "Business Income : " + FormatUIText.getCurrencyUIFormat(sLabelx);
            case "nMonExpns":
                return "Monthly Expenses : " + FormatUIText.getCurrencyUIFormat(sLabelx);
            case "nEstIncme":
                return "Estimate Income : " + FormatUIText.getCurrencyUIFormat(sLabelx);
            case "nPensionx":
                return "Pension Amount : " + FormatUIText.getCurrencyUIFormat(sLabelx);
            default:
                return sLabelx;
        }
    }

    public String getKey() {
        return sKeyxxx;
    }

    public String getValue() {
        return sValxxx;
    }

    public void setsValue(String sValxxx) {
        this.sValxxx = sValxxx;
    }

    public interface FIELDS{
        String ADDRESS = "sAddrFndg";
        String ASSETS = "sAsstFndg";
        String MEANS = "sIncmFndg";
    }
}
