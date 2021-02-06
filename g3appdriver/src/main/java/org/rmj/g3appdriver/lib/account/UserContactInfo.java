package org.rmj.g3appdriver.lib.account;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.StrictMode;
import android.telephony.TelephonyManager;

import androidx.core.app.ActivityCompat;

import org.rmj.g3appdriver.dev.AppData;

public class UserContactInfo {

    private Context ctx;
    private TelephonyManager tmlgr;
    private AppData appData;

    private String MobileNumber;

    public UserContactInfo(Context context) {
        this.ctx = context;
        this.appData = AppData.getInstance(ctx);
        this.tmlgr = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
    }

    @SuppressLint("HardwareIds")
    public boolean hasMobileNumber(String Email) {
        boolean result = false;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(ctx, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(ctx, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
        }
        if(!appData.isContactAndEmailExisted(Email)) {
            if (tmlgr.getLine1Number() != null) {
                if (!tmlgr.getLine1Number().equalsIgnoreCase("")) {
                    String MobNo = tmlgr.getLine1Number();
                    char lcStart = MobNo.charAt(0);
                    char lcPlus = '+';
                    if (lcStart == lcPlus) {
                        if (MobNo.substring(0, 3).equals("+63")) {
                            MobileNumber = MobNo.replace("+63", "0");
                            result = true;
                        } else {
                            return false;
                        }
                    } else {
                        if (MobNo.substring(0, 3).equals("639")) {
                            MobileNumber = "0" + MobNo.substring(2);
                            result = true;
                        } else {
                            return false;
                        }
                    }
                } else {
                    result = false;
                }
            } else {
                result = false;
            }
        } else {
            result = false;
        }
        return result;
    }

    String getMobileNumber(){
        return MobileNumber;
    }

    public void setMobileNumber(String MobileNumber){
        this.MobileNumber = MobileNumber;
    }
}

