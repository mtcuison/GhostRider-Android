package org.rmj.g3appdriver.lib.EmployeeLoan.Obj;

import android.app.Application;

import org.rmj.g3appdriver.lib.EmployeeLoan.model.LoanApplication;
import org.rmj.g3appdriver.lib.EmployeeLoan.model.LoanType;

import java.util.ArrayList;
import java.util.List;

public class Contingency {
    private static final String TAG = Contingency.class.getSimpleName();

    private String message;

    public Contingency(Application instance) {

    }

    public List<LoanType> GetLoanTypes(){
        try{
            List<LoanType> loList = new ArrayList<>();
            loList.add(new LoanType("11001", "MC LOAN", "1", "0"));
            loList.add(new LoanType("11002", "CP LOAN", "1", "0"));
            loList.add(new LoanType("11003", "CONTG LOAN", "0", "0"));
            loList.add(new LoanType("11004", "COMPUTER", "1", "0"));
            loList.add(new LoanType("11005", "OTHERS", "0", "0"));
            loList.add(new LoanType("11006", "Tuition Fee", "0", "0"));
            loList.add(new LoanType("11007", "PAGIBIG-MP3", "0", "1"));
            loList.add(new LoanType("11008", "PAGIBIG-CAL2", "0", "1"));
            loList.add(new LoanType("11009", "SSS", "0", "1"));
            loList.add(new LoanType("11010", "SSS-CAL", "0", "1"));
            loList.add(new LoanType("11011", "Lending", "0", "0"));
            loList.add(new LoanType("11012", "Bike", "0", "0"));
            loList.add(new LoanType("11013", "Car Loan", "0", "0"));
            loList.add(new LoanType("H1001", "MC LOAN", "0", "0"));
            loList.add(new LoanType("H1002", "CP LOAN", "0", "0"));
            loList.add(new LoanType("H1003", "CONTG LOAN", "0", "0"));
            loList.add(new LoanType("H1004", "COMPUTER", "0", "0"));
            loList.add(new LoanType("H1005", "OTHERS", "0", "0"));
            loList.add(new LoanType("H1006", "Tuition Fee", "0", "0"));
            loList.add(new LoanType("H1007", "PAGIBIG-MP3", "0", "1"));
            loList.add(new LoanType("H1008", "PAGIBIG-CAL2", "0", "1"));
            loList.add(new LoanType("H1009", "SSS", "0", "1"));
            loList.add(new LoanType("H1010", "SSS-CAL", "0", "1"));
            loList.add(new LoanType("H1011", "LOAN PMPL", "0", "0"));
            loList.add(new LoanType("H1012", "PMPL", "0", "0"));
            loList.add(new LoanType("H1013", "LOVE FUND", "0", "0"));
            loList.add(new LoanType("H1014", "PMP1", "0", "0"));
            loList.add(new LoanType("H1015", "PMP2", "0", "0"));
            loList.add(new LoanType("H1016", "SCAL", "0", "0"));
            loList.add(new LoanType("H1017", "SAVINGS", "0", "0"));
            loList.add(new LoanType("H1018", "Laptop", "0", "0"));
            loList.add(new LoanType("H1019", "Bike", "0", "0"));
            loList.add(new LoanType("H1020", "Sss Loan", "0", "1"));
            loList.add(new LoanType("1", "MC Loan", "0", "0"));
            loList.add(new LoanType("2", "CP Loan", "0", "0"));
            loList.add(new LoanType("3", "Contingency Loan", "0", "0"));
            loList.add(new LoanType("4", "SSS Loan", "0", "1"));
            loList.add(new LoanType("5", "HDMF Loan", "0", "1"));
            loList.add(new LoanType("6", "Computer Loan", "0", "0"));
            loList.add(new LoanType("7", "Others", "0", "0"));
            loList.add(new LoanType("8", "Part Damage Charge", "0", "0"));
            loList.add(new LoanType("9", "Uniform", "0", "0"));
            loList.add(new LoanType("10", "House Load", "0", "0"));
            loList.add(new LoanType("11", "Car Loan", "0", "0"));
            loList.add(new LoanType("12", "Cash Loan", "0", "0"));
            loList.add(new LoanType("13", "Love Fund", "0", "0"));
            return loList;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return null;
        }
    }

    public boolean SaveApplication(LoanApplication application){
        try{

            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }
}