package org.rmj.g3appdriver.Notification;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.rmj.g3appdriver.lib.Notifications.PanaloNotificationSender;

public class TestPanaloRaffleDrawSender {

    @Test
    public void test01SendRaffleStarting() {
        String lsApp = "gRider";
        String lsUser = "GAP021002961";
        String lsTitle = "I LOVE MY JOB";
        String lsMessage = "Guanzon Panalo raffle draw ended!";
        String lsPanalo = "0";
        int lnStatus = 3;

        boolean isSuccess = PanaloNotificationSender.SendSystemPanaloRaffleNotification(lsApp,
                                                                                        lsUser,
                                                                                        lsTitle,
                                                                                        lsMessage,
                                                                                        lsPanalo,
                                                                                        lnStatus);

        assertTrue(isSuccess);
    }
}
