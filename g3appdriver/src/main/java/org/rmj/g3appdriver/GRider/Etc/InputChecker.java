package org.rmj.g3appdriver.GRider.Etc;

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
