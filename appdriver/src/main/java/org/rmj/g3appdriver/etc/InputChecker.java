/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */

package org.rmj.g3appdriver.etc;

public abstract class InputChecker {
    protected boolean isStringAllZero(String checkStr) {
        char[] charSplit = checkStr.trim().toCharArray();
        for(char i : charSplit) {
            if(i != '0') {
                return false;
            }
        }
        return true;
    }
}
