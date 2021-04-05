package org.rmj.g3appdriver.GRider.ImportData;

import android.util.Log;

import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.rmj.g3appdriver.etc.WebFileServer;

import static org.junit.Assert.*;

public class Import_LoanApplicationsTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testFileServerResponse(){
        String lsClient = WebFileServer.RequestClientToken("IntegSys", "GGC_BM001", "GAP0190799");
        String lsAccess = WebFileServer.RequestAccessToken(lsClient);
        JSONObject loJson = WebFileServer.CheckFile(lsAccess, "0029", "M001", "", "");

        String lsResult = (String) loJson.get("result");
        assertEquals("success", lsResult);
    }
}