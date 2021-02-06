package org.rmj.g3appdriver.dev;

public class Server {
    public static boolean isOnline() {
        try {
            Process p1 = Runtime.getRuntime().exec("ping -c 1 restgk.guanzongroup.com.ph");

            int returnVal = p1.waitFor();
            boolean reachable = (returnVal == 0);

            return reachable;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
