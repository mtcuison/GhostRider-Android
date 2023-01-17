package org.rmj.g3appdriver.etc;

public class CashFormatter {

    public static String parse(String fsCashAmt) {
        String lsFormat = String.format("%,.2f", Double.parseDouble(fsCashAmt));
        return "₱" + lsFormat;
    }

}
