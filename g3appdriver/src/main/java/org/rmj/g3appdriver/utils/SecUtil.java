package org.rmj.g3appdriver.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecUtil {
    public static String md5Hex(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte[] bytes = digest.digest();

            char[] hexArray = "0123456789ABCDEF".toCharArray();
            char[] hexChars = new char[bytes.length * 2];

            for ( int j = 0; j < bytes.length; j++ ) {
                int v = bytes[j] & 0xFF;
                hexChars[j * 2] = hexArray[v >>> 4];
                hexChars[j * 2 + 1] = hexArray[v & 0x0F];
            }
            return new String(hexChars);

        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
