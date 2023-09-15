/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.g3appdriver.lib.ImportData;

import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.rmj.g3appdriver.dev.Api.WebFileServer;

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