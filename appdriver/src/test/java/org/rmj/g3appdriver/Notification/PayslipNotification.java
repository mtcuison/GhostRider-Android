package org.rmj.g3appdriver.Notification;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.rmj.g3appdriver.lib.Notifications.PanaloNotificationSender;

public class PayslipNotification {

    private static boolean isSuccess = false;

    @Test
    public void test04SendRegularSystemNotification() {
        String lsApp = "gRider";
        String lsUser = "GAP023001092";
        String lsTitle = "PAYSLIP (2023-05-16 - 2023-05-31)";


        String lsMessage = "Good day! \n" +
                "\n" +
                " Attached is your payslip for the payroll period 2023-05-16 - 2023-05-31.\n" +
                "\n" +
                " [http://gts1.guanzongroup.com.ph:2007/repl/misc/download_ps.php?period=TTAwMTIzMjk=&client=TTA1MDEwMDAwNTM4]";

        boolean isSuccess = PanaloNotificationSender.SendRegularSystemNotification(lsApp,
                lsUser,
                lsTitle,
                lsMessage);
        assertTrue(isSuccess);
    }
}
