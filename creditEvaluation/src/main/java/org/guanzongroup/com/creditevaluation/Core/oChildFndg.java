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
     *              0 = false;
     *              1 = true;
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
