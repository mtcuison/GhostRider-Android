package org.rmj.g3appdriver.GCircle.Apps.EmployeeLoan.pojo;

public class LoanTerm {

    private final String sLoanTerm;
    private final int nLoanTerm;

    public LoanTerm(String sLoanTerm, int nLoanTerm) {
        this.sLoanTerm = sLoanTerm;
        this.nLoanTerm = nLoanTerm;
    }

    public String getsLoanTerm() {
        return sLoanTerm;
    }

    public int getnLoanTerm() {
        return nLoanTerm;
    }
}
