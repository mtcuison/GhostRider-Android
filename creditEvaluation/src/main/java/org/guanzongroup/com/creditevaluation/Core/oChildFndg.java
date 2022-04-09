package org.guanzongroup.com.creditevaluation.Core;

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
            default:
            if(sKeyxxx.equalsIgnoreCase("nLenServc")) {
                return "Length Of Service : " + sLabelx;
            } if(sKeyxxx.equalsIgnoreCase("nSalaryxx")){
                return "Salary : " + sLabelx;
            } if(sKeyxxx.equalsIgnoreCase("nBusLenxx")){
                return "Years of Business : " + sLabelx;
            } if(sKeyxxx.equalsIgnoreCase("nBusIncom")){
                return "Business Income : " + sLabelx;
            } if(sKeyxxx.equalsIgnoreCase("nMonExpns")){
                return "Monthly Expenses : " + sLabelx;
            } if(sKeyxxx.equalsIgnoreCase("nEstIncme")){
                return "Estimate Income : " + sLabelx;
            } if(sKeyxxx.equalsIgnoreCase("nPensionx")){
                return "Pension Amount : " + sLabelx;
            } else {
                return sLabelx;
            }
        }
    }

    public String getLabelValue(){
        return sLabelx;
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
