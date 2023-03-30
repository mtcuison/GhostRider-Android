package org.rmj.guanzongroup.petmanager.Activity;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.rmj.g3appdriver.etc.MessageBox;
import org.rmj.g3appdriver.lib.EmployeeLoan.model.LoanApplication;
import org.rmj.g3appdriver.lib.EmployeeLoan.model.LoanType;
import org.rmj.guanzongroup.petmanager.R;
import org.rmj.guanzongroup.petmanager.ViewModel.VMEmployeeLoanEntry;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import java.util.ArrayList;
import java.util.Calendar;

import java.util.List;

public class Activity_EmployeeLoanEntry extends AppCompatActivity {
    private static final String TAG = Activity_EmployeeLoanEntry.class.getSimpleName();

    private VMEmployeeLoanEntry mViewModel;
    private LoanApplication loApp;
    private Toolbar toolbar;
    private AppCompatAutoCompleteTextView spn_loantype;
    private TextInputEditText txt_loanamt;
    private TextInputEditText txt_interest;
    private TextInputEditText txt_terms;
    private TextInputEditText txt_firstpay;
    private TextView txtview_balance;
    private TextView txtview_amort;
    private TextView txtview_totalinterest;
    private TextView txtview_currdate;
    private MaterialButton btn_saveloanentry;
    private String dialogConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_loan_entry);

        mViewModel = new ViewModelProvider(this).get(VMEmployeeLoanEntry.class);
        loApp = new LoanApplication();

        //set declared  object value by getting id object from xml file
        toolbar = findViewById(R.id.toolbar);
        txtview_currdate = findViewById(R.id.txtview_currdate);
        spn_loantype = findViewById(R.id.spn_loantype);
        txt_loanamt = findViewById(R.id.txt_loanamt);
        txt_interest = findViewById(R.id.txt_interest);
        txt_terms = findViewById(R.id.txt_terms);
        txt_firstpay = findViewById(R.id.txt_firstpay);
        txtview_balance = findViewById(R.id.txtview_balance);
        txtview_amort = findViewById(R.id.txtview_amort);
        txtview_totalinterest = findViewById(R.id.txtview_totalinterest);
        btn_saveloanentry = findViewById(R.id.btn_saveloanentry);

        /*TOOL BAR*/
        setToolbarAction();

        /*TEXTVIEW FOR CURRENT DAY*/
        setDisplayCurrentDate();

        /*Loan Type Object Function*/
        setLoanTypeList();

        /*TextInput*/
        setDefaultText(txt_loanamt, ".00", "decimal");
        setDefaultText(txt_interest, ".00", "decimal");
        setDefaultText(txt_firstpay, ".00", "decimal");
        setDefaultText(txt_terms, "0", "integer");

        /*Apply Button*/
        setButtonAction();
    }
    private void setToolbarAction(){
        setSupportActionBar(toolbar); //set object toolbar as default action bar for activity
        getSupportActionBar().setTitle("Employee Loan"); //set default title for action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //set back button to toolbar
        getSupportActionBar().setDisplayShowHomeEnabled(true); //enable the back button set on toolbar

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    private void setLoanTypeList(){
      //get different loan type values from declared view model above and store to list type
        List<LoanType> loList = mViewModel.GetLoanTypes();
        ArrayList<String> loTypes = new ArrayList<>(); //declare an array type

      //create a for loop that scans the list value and store to array object
        for(int x= 0; x < loList.size(); x++){
            LoanType loType = loList.get(x);
            String lsType = loType.getLoanName();
            loTypes.add(lsType);
        }
        /*create an adapter for array as on how it could be set on object,
            1st arg: CLASS, 2nd arg: Object Layout View, 3rd arg: Value*/
        ArrayAdapter<String> loAdapter = new ArrayAdapter<>(
                Activity_EmployeeLoanEntry.this,
                android.R.layout.simple_dropdown_item_1line,
                loTypes.toArray(new String[0]));

        //set the view of values to object
        spn_loantype.setAdapter(loAdapter);

        // set event listener for the object
        spn_loantype.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LoanType loType = loList.get(position);
                String lsIDxx = loType.getLoanIDxx();
                String lsType = loType.getLoanName();
                loApp.setLoanIDxx(lsIDxx);
                //loApp.setLoanType(lsType);
            }
        });
    }
    private void setDisplayCurrentDate(){
        Calendar cal = Calendar.getInstance();
        String currdate;

        String month = mViewModel.convertMonthtoWord(cal.get(MONTH) + 1);
        String day = String.valueOf(cal.get(DAY_OF_MONTH));
        String year = String.valueOf(cal.get(YEAR));

        if (month == null){
            currdate = "";
            Toast toast = Toast.makeText(getApplicationContext(), "Current Date cannot be Displayed", Toast.LENGTH_LONG);
            toast.show();
        }else{
            currdate = month + " " + day + ", " + year;
        }
        txtview_currdate.setText(currdate);
    }
    private void setDefaultText(TextInputEditText editText, String defText,String formattoCheck){
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == false){
                    String currtext = editText.getText().toString(); //displayed value
                    if (!currtext.isEmpty() && !currtext.trim().equals("")){
                        Boolean matchFormat = mViewModel.validateAmtFormat(formattoCheck, currtext); //amt format return

                        if (matchFormat == false) {
                            if (!defText.isEmpty() && !defText.trim().equals("")) {
                                if(formattoCheck.equals("decimal")){
                                    //format value to decimal format, String cannot be formatted
                                    currtext =  new DecimalFormat("####.00").format(Integer.parseInt(currtext));
                                }else if (formattoCheck.equals("integer")){
                                    //format value to number format, String cannot be formatted
                                    currtext =  NumberFormat.getInstance().format(Double.parseDouble(currtext));
                                }
                                //replace displayed value
                                editText.setText("");
                                editText.setText(currtext);
                            }
                        }
                        //compute total amount and display
                        computeBalance();
                    }else{
                        //set default value
                        editText.setText(defText);
                    }
                }
            }
        });
    }
    private void computeBalance(){
        double loanAmt = 0.00;
        double intrstAmt = 0.00;
        double firstPay = 0.00;
        int terms = 0;

        if (!txt_loanamt.getText().toString().isEmpty() && !txt_loanamt.getText().toString().equals("")){
            loanAmt = Double.parseDouble(txt_loanamt.getText().toString());
        }

        if (!txt_interest.getText().toString().isEmpty() && !txt_interest.getText().toString().equals("")){
            intrstAmt = Double.parseDouble(txt_interest.getText().toString());
        }

        if (!txt_firstpay.getText().toString().isEmpty() && !txt_firstpay.getText().toString().equals("")){
            firstPay = Double.parseDouble(txt_firstpay.getText().toString());
        }

        if (!txt_terms.getText().toString().isEmpty() && !txt_terms.getText().toString().equals("")){
            terms = Integer.parseInt(txt_terms.getText().toString());
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

        txtview_balance.setText(new DecimalFormat("#,###.00").format(totalBalance)); //set total loan balance

        txtview_totalinterest.setText(new DecimalFormat("#,###.00").format(totalIntrst)); //set total interest

        txtview_amort.setText(new DecimalFormat("#,###.00").format(totalPayperMonth)); //set total payment per month
    }
    private void setButtonAction(){
        btn_saveloanentry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //save entries and clear values
                SaveLoanApplication();
            }
        });
    }
    private void SaveLoanApplication(){
        //VALIDATE LOAN TYPE SELECTION
        String loantype = spn_loantype.getText().toString();
        String loanamt = txt_loanamt.getText().toString();
        String interest = txt_interest.getText().toString();
        String firstpay = txt_firstpay.getText().toString();
        String terms = txt_terms.getText().toString();

        if (loantype.trim().isEmpty() == true) {
            Toast.makeText(Activity_EmployeeLoanEntry.this, "Please select Loan Type", Toast.LENGTH_SHORT).show();
            return;
        }
        //VALIDATE TEXTFIELD'S VALUE FOR AMOUNT
        Boolean outputValid = mViewModel.validateInputAmt(Activity_EmployeeLoanEntry.this, true, new TextInputEditText[]{txt_loanamt, txt_terms, txt_firstpay});
        Boolean outputValidIntrst = mViewModel.validateInputAmt(Activity_EmployeeLoanEntry.this, false, new TextInputEditText[]{txt_interest});
        if(outputValid.equals(false)) {
            return;
        }else if (outputValidIntrst.equals(false)){
            return;
        }

        //VALIDATE AMOUNT FORMAT FROM TEXTFIELD
        Boolean loanAmtFormat = mViewModel.validateAmtFormat("decimal", loanamt);
        Boolean interestAmtFormat = mViewModel.validateAmtFormat("decimal", interest);
        Boolean firstpayAmtFormat = mViewModel.validateAmtFormat("decimal", firstpay);
        Boolean termsAmtFormat = mViewModel.validateAmtFormat("integer", terms);

        if(loanAmtFormat == false || interestAmtFormat == false || firstpayAmtFormat == false || termsAmtFormat == false){
            Toast.makeText(Activity_EmployeeLoanEntry.this, "Invalid Amount Format", Toast.LENGTH_SHORT).show();
            return;
        }
        //Loan Amount should not be less than equal to first payment.
        if(Double.parseDouble(loanamt) <= Double.parseDouble(firstpay)){
            Toast.makeText(Activity_EmployeeLoanEntry.this, "Loan Amount should be higher than First Payment", Toast.LENGTH_SHORT).show();
            return;
        }
        //terms should not be lees than 0
        if (Integer.parseInt(terms) <= 0){
            Toast.makeText(Activity_EmployeeLoanEntry.this, "Please Enter Valid Terms", Toast.LENGTH_SHORT).show();
            return;
        }

        //initialize dialog class
        MessageBox messageBox = new MessageBox(Activity_EmployeeLoanEntry.this);
        messageBox.initDialog();

        //set dialog title and message
        messageBox.setTitle("Loan Application");
        messageBox.setMessage("Confirm Loan Application?");

        //set dialog buttons
        messageBox.setPositiveButton("YES", new MessageBox.DialogButton() {
            @Override
            public void OnButtonClick(View view, AlertDialog dialog) {
                //loApp.setLoanType(loantype);
                loApp.setAmountxx(Double.parseDouble(loanamt));
                loApp.setInterest(Double.parseDouble(interest));
                loApp.setLoanTerm(Integer.parseInt(terms));
                loApp.setAmountxx(Double.parseDouble(firstpay));

                //clear values
                clearFields();
                //close dialog
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
    private void clearFields(){
        spn_loantype.setText("");
        txt_loanamt.setText("");
        txt_interest.setText("");
        txt_terms.setText("");
        txt_firstpay.setText("");
        txtview_balance.setText(".00");
        txtview_amort.setText(".00");
        txtview_totalinterest.setText(".00");
    }
}