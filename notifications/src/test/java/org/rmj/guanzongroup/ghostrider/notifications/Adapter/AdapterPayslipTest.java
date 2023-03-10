package org.rmj.guanzongroup.ghostrider.notifications.Adapter;

import static org.junit.Assert.*;

import org.junit.Test;

public class AdapterPayslipTest {

    @Test
    public void test01ParseStringMessage() {
        String lsMessage = "Good day! \n" +
                "\n" +
                " Attached is your payslip for the payroll period 2023-02-16 - 2023-02-28.\n" +
                "\n" +
                " [http://gts1.guanzongroup.com.ph:2007/repl/misc/download_ps.php?period=TTAwMTIzMTE=&client=TTAwMTE4MDAzMTUz]";
        String lsResult = lsMessage.split("\n\n")[0] + "\n\n" + lsMessage.split("\n\n")[1];
        String lsExpect = "Good day! \n" +
                "\n" +
                " Attached is your payslip for the payroll period 2023-02-16 - 2023-02-28.";
        assertEquals(lsExpect, lsResult);
    }
}