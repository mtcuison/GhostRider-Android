package org.rmj.guanzongroup.ghostrider.ahmonitoring.Model;

import android.view.View;

public class CashCountDetailedInfo {
    private boolean isHeader;
    private boolean isSubHead;
    private String psHeader;
    private String psLabel;
    private String psContent;
    private Values poValue;

    public CashCountDetailedInfo(boolean isHeader, boolean isSubHead, String psHeader, String psLabel, String psContent, Values foValue) {
        this.isHeader = isHeader;
        this.isSubHead = isSubHead;
        this.psHeader = psHeader;
        this.psLabel = psLabel;
        this.psContent = psContent;
        this.poValue = foValue;
    }

    public String getHeader() {
        return psHeader;
    }

    public boolean isSubHead() {
        return isSubHead;
    }

    public int isHeader() {
        if(isHeader){
            return View.VISIBLE;
        }
        return View.GONE;
    }

    public int isContent(){
        if(!isHeader){
            return View.VISIBLE;
        }
        return View.GONE;
    }

    public String getLabel() {
        return psLabel + " :";
    }

    public String getContent() {
        if(psContent==null) {
            return "";
        }
        return psContent;
    }

    public String getTotal() {
        double lnCashQty = "".equalsIgnoreCase(getContent()) ? 0 : Double.parseDouble(getContent());
        switch (poValue) {
            case P1000:
                return String.valueOf(1000 * lnCashQty);
            case P0500:
                return String.valueOf(500 * lnCashQty);
            case P0200:
                return String.valueOf(200 * lnCashQty);
            case P0100:
                return String.valueOf(100  * lnCashQty);
            case P0050:
                return String.valueOf(50  * lnCashQty);
            case P0020:
                return String.valueOf(20 * lnCashQty);
            case P0010:
                return String.valueOf(10  * lnCashQty);
            case P0005:
                return String.valueOf(5 * lnCashQty);
            case P0001:
                return String.valueOf(1 * lnCashQty);
            case C0025:
                return String.valueOf(0.25 * lnCashQty);
            case C0010:
                return String.valueOf(0.10 * lnCashQty);
            case C0005:
                return String.valueOf(0.05 * lnCashQty);
            case C0001:
                return String.valueOf(0.01 * lnCashQty);
            default:
                return "0.0";
        }
    }

    public enum Values{
        P1000,
        P0500,
        P0200,
        P0100,
        P0050,
        P0020,
        P0010,
        P0005,
        P0001,
        C0025,
        C0010,
        C0005,
        C0001,
        NONE
    }
}
