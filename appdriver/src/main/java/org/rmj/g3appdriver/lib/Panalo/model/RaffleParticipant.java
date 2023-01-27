package org.rmj.g3appdriver.lib.Panalo.model;

public class RaffleParticipant {
    private final String sUserIDxx;
    private final String sUserName;
    private final String sBranchCD;

    public RaffleParticipant(String sUserIDxx, String sUserName, String sBranchCD) {
        this.sUserIDxx = sUserIDxx;
        this.sUserName = sUserName;
        this.sBranchCD = sBranchCD;
    }

    public String getsUserIDxx() {
        return sUserIDxx;
    }

    public String getsUserName() {
        return sUserName;
    }

    public String getsBranchCD() {
        return sBranchCD;
    }
}
