package org.rmj.g3appdriver.GCircle.Apps.EmployeeLoan.pojo;

public class LoanApplication {
    private String LoanIDxx = ""; //sLoanIDxx
    private double Amountxx = 0.0; //nAmountxx
    private double Interest = 0.0; //nInterest
    private int LoanTerm = 0; //nLoanTerm

    private String message;

    public LoanApplication() {

    }

    public String getMessage() {
        return message;
    }

    public String getLoanIDxx() {
        return LoanIDxx;
    }

    public void setLoanIDxx(String loanIDxx) {
        LoanIDxx = loanIDxx;
    }

    public double getAmountxx() {
        return Amountxx;
    }

    public void setAmountxx(double amountxx) {
        Amountxx = amountxx;
    }

    public double getInterest() {
        return Interest;
    }

    public void setInterest(double interest) {
        Interest = interest;
    }

    public int getLoanTerm() {
        return LoanTerm;
    }

    public void setLoanTerm(int loanTerm) {
        LoanTerm = loanTerm;
    }

    public boolean isDataValid(){
        if(LoanIDxx.trim().isEmpty()){
            message = "Please select loan type";
            return false;
        }
        if(Amountxx == 0.0){
            message = "Please enter loan amount";
            return false;
        }
        if(LoanTerm == 0){
            message = "Please select installment term";
            return false;
        }

        return true;
    }
}
