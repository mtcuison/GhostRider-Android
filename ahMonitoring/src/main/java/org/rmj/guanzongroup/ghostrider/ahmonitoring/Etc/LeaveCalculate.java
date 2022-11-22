package org.rmj.guanzongroup.ghostrider.ahmonitoring.Etc;

public class LeaveCalculate {
    private static final String TAG = LeaveCalculate.class.getSimpleName();

    private static String CalculateWithPay(int fnWthOPay, int fnWithPay){
        int lnWOPayx = Math.abs(fnWthOPay - fnWithPay);
        return String.valueOf(lnWOPayx);
    }

}
