package org.guanzongroup.com.creditevaluation.Core;

import static org.junit.Assert.*;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditOnlineApplicationCI;

import java.util.HashMap;
import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class PreviewParserTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void test01PreviewResult() throws Exception{
        ECreditOnlineApplicationCI loCI = new ECreditOnlineApplicationCI();
        loCI.setTransNox("CI5UV2200018");
        loCI.setAddressx("{\"present_address\":{\"cAddrType\":\"0\",\"sAddressx\":\"B3 Lt4 Lingueta St., Pio Cruzcosa, Calumpit Bulacan\",\"sAddrImge\":\"\",\"nLatitude\":0.0,\"nLongitud\":0.0},\"primary_address\":{\"cAddrType\":\"1\",\"sAddressx\":\"B3 Lt4 Lingueta St., Pio Cruzcosa, Calumpit Bulacan\",\"sAddrImge\":\"\",\"nLatitude\":0.0,\"nLongitud\":0.0}}");
        loCI.setAddrFndg("{\"present_address\":{\"cAddrType\":null,\"sAddressx\":10,\"sAddrImge\":null,\"nLatitude\":0.0,\"nLongitud\":0.0},\"primary_address\":{\"cAddrType\":null,\"sAddressx\":10,\"sAddrImge\":null,\"nLatitude\":0.0,\"nLongitud\":0.0}}");
        loCI.setAssetsxx("{\"sProprty1\":\"\",\"sProprty2\":\"\",\"sProprty3\":\"\",\"cWith4Whl\":\"0\",\"cWith3Whl\":\"0\",\"cWith2Whl\":\"0\",\"cWithRefx\":\"1\",\"cWithTVxx\":\"1\",\"cWithACxx\":\"1\"}");
        loCI.setAsstFndg("{\"sProprty1\":null,\"sProprty2\":null,\"sProprty3\":null,\"cWith4Whl\":null,\"cWith3Whl\":null,\"cWith2Whl\":null,\"cWithRefx\":10,\"cWithTVxx\":10,\"cWithACxx\":null}");
        loCI.setIncomexx("{\"employed\":{\"sEmployer\":\"United Phlp And Phper Inc.\",\"sWrkAddrx\":\"Km 48 Mc Arthur Hi-way Iba Este, Calumpit Bulacan\",\"sPosition\":\"M009680\",\"nLenServc\":23.0,\"nSalaryxx\":35000.0},\"self_employed\":null,\"financed\":null,\"pensioner\":null}");
        loCI.setIncmFndg("{\"employed\":{\"sEmployer\":null,\"sWrkAddrx\":\"-1\",\"sPosition\":null,\"nLenServc\":20,\"nSalaryxx\":10},\"self_employed\":{\"sBusiness\":null,\"sBusAddrx\":null,\"nBusLenxx\":0.0,\"nBusIncom\":0.0,\"nMonExpns\":20},\"financed\":{\"sFinancer\":null,\"sReltnDsc\":null,\"sCntryNme\":null,\"nEstIncme\":20},\"pensioner\":{\"sPensionx\":null,\"nPensionx\":0.0}}");
        loCI.setHasRecrd("1");
        loCI.setRecrdRem("Blotter not paying debts");
        loCI.setPrsnBrgy("Brgy Tawi Tawi");
        loCI.setPrsnPstn("Kagawad");
        loCI.setPrsnNmbr("09123456789");
        loCI.setNeighBr1("Sample Neighbors");
        loCI.setNeighBr2("Sample Neighbors");
        loCI.setNeighBr3("Sample Neighbors");
        loCI.setRcmdRcd1("2022-04-08");
        loCI.setRcmdtnx1("2022-04-08");
        loCI.setRcmdtnc1("0");
        loCI.setRcmdtns1("Disapproved sample");

        List<oPreview> loPreview = PreviewParser.getCIResultPreview(loCI);
        assertNotNull(loPreview);
        assertTrue(loPreview.size() > 0);
    }
}