package org.rmj.g3appdriver.GCircle.Apps.EmployeeLoan.pojo;

public class LoanType {

    private String LoanIDxx = ""; //sLoanIDxx
    private String LoanName = ""; //sLoanName
    private String IntegSys = ""; //cIntegSys
    private String OnPaySlp = ""; //cOnPaySlp

    public LoanType(String loanIDxx, String loanName, String integSys, String onPaySlp) {
        LoanIDxx = loanIDxx;
        LoanName = loanName;
        IntegSys = integSys;
        OnPaySlp = onPaySlp;
    }

    public String getLoanIDxx() {
        return LoanIDxx;
    }

    public String getLoanName() {
        return LoanName;
    }

    public String getIntegSys() {
        return IntegSys;
    }

    public String getOnPaySlp() {
        return OnPaySlp;
    }
}
