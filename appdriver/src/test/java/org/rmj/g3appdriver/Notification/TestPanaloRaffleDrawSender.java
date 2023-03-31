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
        int lnStatus = 1;

        boolean isSuccess = PanaloNotificationSender.SendSystemPanaloRaffleNotification(lsApp,
                                                                                        lsUser,
                                                                                        lsTitle,
                                                                                        lsMessage,
                                                                                        lnStatus);
        assertTrue(isSuccess);
    }

    @Test
    public void test02SendRewardsNotification() {
        String lsApp = "gRider";
        String lsUser = "GAP023000254";
        String lsTitle = "I LOVE MY JOB";
        String lsMessage = "Congratulations!, You won a permanent Work from home.";

        boolean isSuccess = PanaloNotificationSender.SendSystemPanaloRewardNotification(lsApp,
                lsUser,
                lsTitle,
                lsMessage);


        assertTrue(isSuccess);
    }

    @Test
    public void test03SendBranchOpeningNotification() {
        String lsApp = "gRider";
        String lsUser = "GAP023000254";
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
        String lsUser = "GAP023000254";
        String lsTitle = "I LOVE MY JOB";
        String lsMessage = "Congratulations! \n " +
                "You win a token Trip To Baguio for 1 Year. " +
                "Raffle token prizes will be automatically redeemed and HR will be responsible " +
                "for the schedule of your trip. \n" +
                "\n" +
                "\n" +
                "Have a Safe Trip!";

        boolean isSuccess = PanaloNotificationSender.SendRegularSystemNotification(lsApp,
                lsUser,
                lsTitle,
                lsMessage);
        assertTrue(isSuccess);
    }
}
