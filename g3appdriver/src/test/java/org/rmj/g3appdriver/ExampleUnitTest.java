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

package org.rmj.g3appdriver;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    private boolean isSuccess = false;

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }


    @Test
    public void testConnectionLogic(){
        boolean isBackUp = false;
        String lsAddress1 = "address1";
        String lsAddress2 = "address2";

        if(isBackUp){
            if(isReachable(lsAddress1)){
                isSuccess = true;
            } else {
                if(isReachable(lsAddress2)){
                    isSuccess = true;
                } else {

                }
            }
        } else {

        }
    }

    private boolean isReachable(String fsVal){
        return fsVal.equalsIgnoreCase("address1");
    }
}