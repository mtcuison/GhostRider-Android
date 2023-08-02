package org.rmj.g3appdriver.GCircle.Apps.CashCount;

public class QuickSearchNames {
    private String RequestName;
    private String RequestIDxx;
    private String RequestDept;
    public QuickSearchNames(){

    }
    public QuickSearchNames(String requestName,
                                 String requestIDxx,
                                 String requestDept){
        this.RequestName = requestName;
        this.RequestIDxx = requestIDxx;
        this.RequestDept = requestDept;
    }

    public String getRequestName() {
        return RequestName;
    }

    public String getRequestIDxx() {
        return RequestIDxx;
    }

    public String getRequestDept() {
        return RequestDept;
    }
}