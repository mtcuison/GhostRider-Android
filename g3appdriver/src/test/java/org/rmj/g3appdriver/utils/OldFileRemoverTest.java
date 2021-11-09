package org.rmj.g3appdriver.utils;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class OldFileRemoverTest extends TestCase {
    private OldFileRemover poRemover;
    private String psFilePth;

    @Before
    public void setUp() {
        poRemover = new OldFileRemover();
        psFilePth = "/storage/emulated/0/Download";
    }

    @After
    public void tearDown() {
        poRemover = null;
        psFilePth = null;
    }

    @Test
    public void testExecute() {
        assertTrue(!poRemover.execute(psFilePth));
    }

}
