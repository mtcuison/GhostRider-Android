package org.rmj.guanzongroup.ghostrider.ahmonitoring.Model;

public class RequestNamesInfoModel {
    private String RequestName;
    private String RequestIDxx;
    private String RequestDept;
    public RequestNamesInfoModel(){

    }
    public RequestNamesInfoModel(String requestName,
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
