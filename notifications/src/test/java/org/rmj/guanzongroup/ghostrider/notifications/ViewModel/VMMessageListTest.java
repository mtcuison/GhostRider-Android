package org.rmj.guanzongroup.ghostrider.notifications.ViewModel;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.*;

public class VMMessageListTest {

    @Mock
    JSONArray rcpts = new JSONArray();

    @Mock
    JSONObject rcpt = new JSONObject();

    @Mock
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