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
