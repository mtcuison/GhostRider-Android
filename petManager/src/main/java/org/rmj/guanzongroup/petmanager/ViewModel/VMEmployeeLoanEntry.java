package org.rmj.guanzongroup.petmanager.ViewModel;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.google.android.material.textfield.TextInputEditText;

import org.rmj.g3appdriver.GCircle.Apps.EmployeeLoan.Obj.EmployeeLoan;
import org.rmj.g3appdriver.GCircle.Apps.EmployeeLoan.pojo.LoanApplication;
import org.rmj.g3appdriver.GCircle.Apps.EmployeeLoan.pojo.LoanType;
import org.rmj.g3appdriver.GCircle.Apps.EmployeeLoan.pojo.LoanTerm;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class VMEmployeeLoanEntry extends AndroidViewModel{
    private static final String TAG = VMEmployeeLoanEntry.class.getSimpleName();
    private final EmployeeLoan poSys;
    public Context context;

    public VMEmployeeLoanEntry(@NonNull Application application) {
        super(application);
        this.poSys = new EmployeeLoan(application);
    }
    public List<LoanType> GetLoanTypes(){
        return poSys.GetLoanTypes();
    }
    public ArrayAdapter<String> getLoanTypeListAdapter(){
        ArrayList<String> loTypes = new ArrayList<>(); //declare an array type

        //create a for loop that scans the list value and store to array object
        for(int x= 0; x < GetLoanTypes().size(); x++){
            LoanType loType = GetLoanTypes().get(x);
            loTypes.add(loType.getLoanName());
        }
        ArrayAdapter<String> loAdapter = new ArrayAdapter<>(
                context, android.R.layout.simple_dropdown_item_1line,
                loTypes.toArray(new String[0]));

        return loAdapter;
    }
    public List<LoanTerm> getTermsList(){
        return poSys.GetLoanTerm();
    }
    public  ArrayAdapter<String> getTermsListAdapter(){
        ArrayList<String> loTerms = new ArrayList<>(); //declare an array type

        //create a for loop that scans the list value and store to array object
        for(int x= 0; x < getTermsList().size(); x++){
            LoanTerm loTerm = getTermsList().get(x);
            loTerms.add(loTerm.getsLoanTerm());
        }

        ArrayAdapter<String> loAdapter = new ArrayAdapter<>(
                context, android.R.layout.simple_dropdown_item_1line,
                loTerms.toArray(new String[0]));

        return loAdapter;
    }
    public Boolean validateAmtFormat(String format, String inputAmt){
        Boolean matchFormat = null;
        if (inputAmt.isEmpty() == false && inputAmt.trim() != "") {
            if (format == "decimal") {
                matchFormat = Pattern.matches("^\\d*\\.\\d*$", inputAmt);
            } else if (format == "integer") {
                matchFormat = Pattern.matches("\\d*", inputAmt);
            }
        }
        return matchFormat;
    }
    public String getDefaultText(String currval, String defText,String formattoCheck){
        String currtext = currval; //displayed value

        if (!currtext.isEmpty() && !currtext.trim().equals("")){
            Boolean matchFormat = validateAmtFormat(formattoCheck, currtext); //amt format return

            if (matchFormat == false) {
                if (!defText.isEmpty() && !defText.trim().equals("")) {
                    if(formattoCheck.equals("decimal")){
                        //format value to decimal format, String cannot be formatted
                        currtext =  new DecimalFormat("####.00").format(Integer.parseInt(currtext));
                    }else if (formattoCheck.equals("integer")){
                        //format value to number format, String cannot be formatted
                        currtext =  NumberFormat.getInstance().format(Double.parseDouble(currtext));
                    }
                }
            }
        }else{
            currtext = defText;
        }
        return currtext;
    }
    public String computeTotalAmt(String sloanAmt, String sfirstPay, String sterms, String returnAmt){
        double loanAmt = 0.00;
        double firstPay = 0.00;
        int terms = 0;

        String sreturnCompAmt = null;

        if (!sloanAmt.isEmpty() && !sloanAmt.equals("")){
            loanAmt = Double.parseDouble(sloanAmt);
        }

        if (!sfirstPay.isEmpty() && !sfirstPay.equals("")){
            firstPay = Double.parseDouble(sfirstPay);
        }

        if (!sterms.isEmpty() && !sterms.equals("")){
            terms = Integer.parseInt(sterms);
        }

        //TOTAL BALANCE: (LOAN - FIRST PAY) + TOTAL INTEREST
        double totalBalance = (loanAmt - firstPay);

        //LOAN PER MONTH: (loan total - first payment) + total interest / terms
        double monthlyPayment = totalBalance / terms; //get loan monthly

        //TOTAL MONTHLY PAYMENT: monthly payment + interest per month
        double totalPayperMonth = (monthlyPayment); //sum total payment monthly

        if(returnAmt.equals("balance")){ //return total loan balance
            sreturnCompAmt = new DecimalFormat("#,###.00").format(totalBalance);
        }else if (returnAmt.equals("interest")) { //return total interest
            //sreturnCompAmt = new DecimalFormat("#,###.00").format(totalIntrst / terms);
        }else if (returnAmt.equals("amort")) { //return total payment per month
            sreturnCompAmt = new DecimalFormat("#,###.00").format(totalPayperMonth);
        }

        return sreturnCompAmt;
    }
    public Boolean validateInputAmt(Activity activity, Boolean required, TextInputEditText[] inputEditTexts){
        Boolean output = true;
        for (int i = 0; i <  inputEditTexts.length; i++){
            String objHint = inputEditTexts[i].getHint().toString();
            String objAmt = inputEditTexts[i].getText().toString();

            if(objAmt.trim().isEmpty() == true){
                Toast.makeText(activity, "Please enter amount on "+ objHint, Toast.LENGTH_SHORT).show();
                output = false;
                break;
            }else if(Double.parseDouble(objAmt) <= 0.00 && required.equals(true)){
                Toast.makeText(activity, "Amount Required on "+ objHint, Toast.LENGTH_SHORT).show();
                output = false;
                break;
            }
        }
        return output;
    }
    public Boolean SaveLoanApplication(String sloanType, String sloanAmt, String sfirstPay, String sTerms, LoanApplication loApp){
        Boolean res = true;

        if (sloanType.trim().isEmpty() == true) {
            Toast.makeText(context, "Please select Loan Type", Toast.LENGTH_SHORT).show();
            res =  false;
        }
        //VALIDATE AMOUNT FORMAT FROM TEXTFIELD
        Boolean loanAmtFormat = validateAmtFormat("decimal", sloanAmt);
        Boolean firstpayAmtFormat = validateAmtFormat("decimal", sfirstPay);
        Boolean termsAmtFormat = validateAmtFormat("integer", sTerms);

        if(loanAmtFormat == false || firstpayAmtFormat == false || termsAmtFormat == false){
            Toast.makeText(context, "Invalid Amount Format", Toast.LENGTH_SHORT).show();
            res = false;
        }
        //Loan Amount should not be less than equal to first payment.
        if(Double.parseDouble(sloanAmt) <= Double.parseDouble(sfirstPay)){
            Toast.makeText(context, "Loan Amount should be higher than First Payment", Toast.LENGTH_SHORT).show();
            res = false;
        }
        //terms should not be lees than 0
        if (Integer.parseInt(sTerms) <= 0){
            Toast.makeText(context, "Please Enter Valid Terms", Toast.LENGTH_SHORT).show();
            res = false;
        }

        return res;
    }
}
