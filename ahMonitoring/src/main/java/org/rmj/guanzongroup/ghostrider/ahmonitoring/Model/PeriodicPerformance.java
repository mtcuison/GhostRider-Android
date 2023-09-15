package org.rmj.guanzongroup.ghostrider.ahmonitoring.Model;

public class PeriodicPerformance {

    private final String sPeriod;
    private final String nActual;
    private final String nGoalxx;

    public PeriodicPerformance(String sPeriod, String nActual, String nGoalxx) {
        this.sPeriod = sPeriod;
        this.nActual = nActual;
        this.nGoalxx = nGoalxx;
    }

    public String getsPeriod() {
        return sPeriod;
    }

    public String getnActual() {
        return nActual;
    }

    public String getnGoalxx() {
        return nGoalxx;
    }
}
