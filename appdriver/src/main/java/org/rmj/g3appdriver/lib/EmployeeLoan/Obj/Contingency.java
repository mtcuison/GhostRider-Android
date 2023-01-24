package org.rmj.g3appdriver.lib.EmployeeLoan.Obj;

import android.app.Application;
import android.util.Log;

import org.rmj.g3appdriver.dev.Database.DataAccessObject.DEmployeeLoan;
import org.rmj.g3appdriver.dev.Database.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.dev.Database.Entities.EEmployeeLoan;
import org.rmj.g3appdriver.dev.Database.GGC_GriderDB;
import org.rmj.g3appdriver.etc.AppConstants;
import org.rmj.g3appdriver.lib.EmployeeLoan.model.LoanApplication;
import org.rmj.g3appdriver.lib.EmployeeLoan.model.LoanType;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Contingency {
    private static final String TAG = Contingency.class.getSimpleName();

    private DEmployeeLoan poDao;

    private String message;

    public Contingency(Application instance) {
        this.poDao = GGC_GriderDB.getInstance(instance).loanDao();
    }

    public String getMessage() {
        return message;
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

    public String SaveApplication(LoanApplication application){
        try{
            EEmployeeInfo loUser = poDao.GetUserInfo();

            if(loUser == null){
                message = "Unable to find user info. Please restart app and try again.";
                return null;
            }

            EEmployeeLoan loLoan = new EEmployeeLoan();

            String lsTransNox = CreateUniqueID();

            if(lsTransNox.trim().isEmpty()){
                message = "Unable to create transaction no.";
                return null;
            }

            loLoan.setTransNox(lsTransNox);
            loLoan.setUserIDxx(loUser.getUserIDxx());
            loLoan.setTransact(AppConstants.CURRENT_DATE);
            loLoan.setLoanIDxx(application.getLoanIDxx());
            loLoan.setLoanType(application.getLoanIDxx());
            loLoan.setAmountxx(application.getAmountxx());
            loLoan.setInterest(application.getInterest());
            loLoan.setLoanTerm(application.getLoanTerm());

            poDao.Save(loLoan);

            return lsTransNox;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return null;
        }
    }

    public boolean UploadApplication(String args){
        try{
            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }

    private String CreateUniqueID(){
        String lsUniqIDx = "";
        try{
            String lsBranchCd = "MX01";
            String lsCrrYear = new SimpleDateFormat("yy", Locale.getDefault()).format(new Date());
            StringBuilder loBuilder = new StringBuilder(lsBranchCd);
            loBuilder.append(lsCrrYear);

            int lnLocalID = poDao.GetRowsCountForID() + 1;
            String lsPadNumx = String.format("%05d", lnLocalID);
            loBuilder.append(lsPadNumx);
            lsUniqIDx = loBuilder.toString();
        } catch (Exception e){
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        }
        Log.d(TAG, lsUniqIDx);
        return lsUniqIDx;
    }
}