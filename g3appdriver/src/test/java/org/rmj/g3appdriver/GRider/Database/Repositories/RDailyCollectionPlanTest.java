package org.rmj.g3appdriver.GRider.Database.Repositories;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class RDailyCollectionPlanTest {

    @Mock
    public RDailyCollectionPlan poDcp;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetNextID(){
//        when(poDcp.getImageNextCode()).thenReturn("");
//        assertEquals("", poDcp.getImageNextCode());
    }
}