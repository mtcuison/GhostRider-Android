package org.rmj.g3appdriver.Notification;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.rmj.g3appdriver.lib.Notifications.PanaloNotificationSender;

public class TestPanaloRaffleDrawSender {

    @Test
    public void test01SendRaffleNotification() {
        String lsApp = "gRider";
        String lsUser = "GAP023000254";
        String lsTitle = "I LOVE MY JOB";
        String lsMessage = "Guanzon Panalo raffle draw ended!";
        int lnStatus = 3;

        boolean isSuccess = PanaloNotificationSender.SendSystemPanaloRaffleNotification(lsApp,
                                                                                        lsUser,
                                                                                        lsTitle,
                                                                                        lsMessage,
                                                                                        lnStatus);
        assertTrue(isSuccess);
    }

    @Test
    public void test02SendRewardsNotification() {
        String lsApp = "GuanzonApp";
        String lsUser = "GAP021002961";
        String lsTitle = "Guanzon Panalo";
        String lsMessage = "Congrats, John Doe! You won Php100/mo for 3 mos. on Guanzon Panalo Raffle. Claim prize by May 20, 2023. Thank you for your loyalty.";

        boolean isSuccess = PanaloNotificationSender.SendSystemPanaloRewardNotification(lsApp,
                lsUser,
                lsTitle,
                lsMessage);


        assertTrue(isSuccess);
    }

    @Test
    public void test03SendBranchOpeningNotification() {
        String lsApp = "gRider";
        String lsUser = "GAP021002961";
        String lsTitle = "Branch Opening";
        String lsMessage = "LGK Tarlac - Honda has opened.";

        boolean isSuccess = PanaloNotificationSender.SendSystemBranchOpeningNotification(lsApp,
                lsUser,
                lsTitle,
                lsMessage);
        assertTrue(isSuccess);
    }

    @Test
    public void test04SendRegularSystemNotification() {
        String lsApp = "gRider";
        String lsUser = "GAP023000972";
        String lsTitle = "PAYSLIP (2023-04-01 - 2023-04-15)";
        String lsMessage = "Good day! \n" +
                "\n" +
                " Attached is your payslip for the payroll period 2023-04-01 - 2023-04-15.\n" +
                "\n" +
                " [http://gts1.guanzongroup.com.ph:2007/repl/misc/download_ps.php?period=TTAwMTIzMjA=&client=TTAwMTIwMDAwMzI5]";

        boolean isSuccess = PanaloNotificationSender.SendRegularSystemNotification(lsApp,
                lsUser,
                lsTitle,
                lsMessage);
        assertTrue(isSuccess);
    }
}
