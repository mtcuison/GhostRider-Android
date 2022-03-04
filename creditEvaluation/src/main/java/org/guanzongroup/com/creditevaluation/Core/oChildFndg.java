package org.guanzongroup.com.creditevaluation.Core;

public class oChildFndg {

    private final String sFieldx;
    private final String sParent;
    private final String sLabelx;
    private final String sKeyxxx;
    private String sValxxx;

    /**
     *
     * @param Field Table field name on local database
     *              ex. sAddrFndg, sAsstFndg, sIncmFndg
     *              get values from FIELDS
     * @param Parent JSONObject parent object name
     * @param Label Name of field to be update base on Local Database
     * @param KeyName Name of key to be update
     * @param Value Value of key
     *              0 = false;
     *              1 = true;
     */
    public oChildFndg(String Field, String Parent, String Label,String KeyName, String Value) {
        this.sFieldx = Field;
        this.sParent = Parent;
        this.sLabelx = Label;
        this.sKeyxxx = KeyName;
        this.sValxxx = Value;
    }

    public String getParent() {
        return sParent;
    }

    public String getLabel() {
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
