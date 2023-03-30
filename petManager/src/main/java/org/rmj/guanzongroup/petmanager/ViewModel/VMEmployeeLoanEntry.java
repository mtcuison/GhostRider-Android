package org.rmj.guanzongroup.petmanager.ViewModel;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.google.android.material.textfield.TextInputEditText;

import org.rmj.g3appdriver.etc.MessageBox;
import org.rmj.g3appdriver.lib.EmployeeLoan.Obj.Contingency;
import org.rmj.g3appdriver.lib.EmployeeLoan.model.LoanApplication;
import org.rmj.g3appdriver.lib.EmployeeLoan.model.LoanType;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

public class VMEmployeeLoanEntry extends AndroidViewModel{
    private static final String TAG = VMEmployeeLoanEntry.class.getSimpleName();
    private final Contingency poSys;
    public Context context;

    public VMEmployeeLoanEntry(@NonNull Application application) {
        super(application);
        this.poSys = new Contingency(application);
    }
    public String getCurrentDateFormat(){
        Calendar cal = Calendar.getInstance();
        String currdate;

        String month = convertMonthtoWord(cal.get(MONTH) + 1);
        String day = String.valueOf(cal.get(DAY_OF_MONTH));
        String year = String.valueOf(cal.get(YEAR));

        if (month == null){
            currdate = "";
            Toast toast = Toast.makeText(context, "Current Date cannot be Displayed", Toast.LENGTH_LONG);
            toast.show();
        }else{
            currdate = month + " " + day + ", " + year;
        }

        return currdate;
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
    public List<LoanType> GetLoanTypes(){
        return poSys.GetLoanTypes();
    }
    public String convertMonthtoWord(int monthindex){
        String monthtoword;

        if(monthindex == 0){
            monthindex += 1;
        } else if (monthindex == 13) {
            monthindex -= 1;
        }else if (monthindex < 0 || monthindex > 12){
            return null;
        }
        ArrayList<String> monthnames = new ArrayList<>();
        monthnames.add(0, "");
        monthnames.add(1, "January");
        monthnames.add(2, "February");
        monthnames.add(3, "March");
        monthnames.add(4, "April");
        monthnames.add(5, "May");
        monthnames.add(6, "June");
        monthnames.add(7, "July");
        monthnames.add(8, "August");
        monthnames.add(9, "September");
        monthnames.add(10, "October");
        monthnames.add(11, "November");
        monthnames.add(12, "December");

        monthtoword = monthnames.get(monthindex);
        return monthtoword;
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
    public String computeTotalAmt(String sloanAmt, String sintrstAmt, String sfirstPay, String sterms, String returnAmt){
        double loanAmt = 0.00;
        double intrstAmt = 0.00;
        double firstPay = 0.00;
        int terms = 0;

        String sreturnCompAmt = null;

        if (!sloanAmt.isEmpty() && !sloanAmt.equals("")){
            loanAmt = Double.parseDouble(sloanAmt);
        }

        if (!sintrstAmt.isEmpty() && !sintrstAmt.equals("")){
            intrstAmt = Double.parseDouble(sintrstAmt);
        }

        if (!sfirstPay.isEmpty() && !sfirstPay.equals("")){
            firstPay = Double.parseDouble(sfirstPay);
        }

        if (!sterms.isEmpty() && !sterms.equals("")){
            terms = Integer.parseInt(sterms);
        }

        //INTEREST RATE / 100 * loan amount
        double totalIntrst = (intrstAmt / 100) * loanAmt;

        //TOTAL BALANCE: (LOAN - FIRST PAY) + TOTAL INTEREST
        double totalBalance = (loanAmt - firstPay) + totalIntrst;

        //INTEREST PER MONTH: rate / terms
        double intrstPerMonth = intrstAmt / terms; //get interest monthly

        //LOAN PER MONTH: (loan total - first payment) + total interest / terms
        double monthlyPayment = totalBalance / terms; //get loan monthly

        //TOTAL MONTHLY PAYMENT: monthly payment + interest per month
        double totalPayperMonth = (monthlyPayment + intrstPerMonth); //sum total payment monthly

        if(returnAmt.equals("balance")){ //return total loan balance
            sreturnCompAmt = new DecimalFormat("#,###.00").format(totalBalance);
        }else if (returnAmt.equals("interest")) { //return total interest
            sreturnCompAmt = new DecimalFormat("#,###.00").format(totalIntrst);
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
    public void SaveLoanApplication(String sloanType, String sloanAmt, String sIntrst, String sfirstPay, String sTerms, LoanApplication loApp){

        if (sloanType.trim().isEmpty() == true) {
            Toast.makeText(context, "Please select Loan Type", Toast.LENGTH_SHORT).show();
            return;
        }
        //VALIDATE AMOUNT FORMAT FROM TEXTFIELD
        Boolean loanAmtFormat = validateAmtFormat("decimal", sloanAmt);
        Boolean interestAmtFormat = validateAmtFormat("decimal", sIntrst);
        Boolean firstpayAmtFormat = validateAmtFormat("decimal", sfirstPay);
        Boolean termsAmtFormat = validateAmtFormat("integer", sTerms);

        if(loanAmtFormat == false || interestAmtFormat == false || firstpayAmtFormat == false || termsAmtFormat == false){
            Toast.makeText(context, "Invalid Amount Format", Toast.LENGTH_SHORT).show();
            return;
        }
        //Loan Amount should not be less than equal to first payment.
        if(Double.parseDouble(sloanAmt) <= Double.parseDouble(sfirstPay)){
            Toast.makeText(context, "Loan Amount should be higher than First Payment", Toast.LENGTH_SHORT).show();
            return;
        }
        //terms should not be lees than 0
        if (Integer.parseInt(sTerms) <= 0){
            Toast.makeText(context, "Please Enter Valid Terms", Toast.LENGTH_SHORT).show();
            return;
        }

        //initialize dialog class
        MessageBox messageBox = new MessageBox(context);
        messageBox.initDialog();

        //set dialog title and message
        messageBox.setTitle("Loan Application");
        messageBox.setMessage("Confirm Loan Application?");

        //set dialog buttons
        messageBox.setPositiveButton("YES", new MessageBox.DialogButton() {
            @Override
            public void OnButtonClick(View view, AlertDialog dialog) {
                //loApp.setLoanType(loantype);
                loApp.setAmountxx(Double.parseDouble(sloanAmt));
                loApp.setInterest(Double.parseDouble(sIntrst));
                loApp.setLoanTerm(Integer.parseInt(sTerms));
                loApp.setAmountxx(Double.parseDouble(sfirstPay));

                //close dialog
                clearFields(view.getRootView());
                dialog.dismiss();
            }
        });

        messageBox.setNegativeButton("NO", new MessageBox.DialogButton() {
            @Override
            public void OnButtonClick(View view, AlertDialog dialog) {
                dialog.dismiss();
                return;
            }
        });

        //show dialog
        messageBox.show();
    }
    private void clearFields(View v){
        if (v instanceof TextInputEditText){
            ((TextInputEditText) v).setText("");
        }
    }
}
