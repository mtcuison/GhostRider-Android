/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.notifications
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.notifications.ViewModel;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class VMMessageListTest {

//    @Mock
    JSONArray rcpts = new JSONArray();

//    @Mock
    JSONObject rcpt = new JSONObject();

//    @Mock
    JSONObject param = new JSONObject();

    @Before
    public void setUp() throws Exception {
        rcpt.put("app", "GuanzonApp");
        rcpt.put("user", "GAP0190362");
        rcpts.put(rcpt);

        //Create the parameters needed by the API
        param.put("type", "00000");
        param.put("parent", null);
        param.put("title", "This is a test");
        param.put("message", "This is a test #2 notification");
        param.put("rcpt", rcpts);
    }

    @Test
    public void getMessageList() throws Exception {
        assertNull(param.toString());
    }
}