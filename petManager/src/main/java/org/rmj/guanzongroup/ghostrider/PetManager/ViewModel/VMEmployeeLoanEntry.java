package org.rmj.guanzongroup.ghostrider.PetManager.ViewModel;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

import android.app.Activity;
import android.app.Application;
import android.app.DatePickerDialog;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import org.rmj.g3appdriver.lib.EmployeeLoan.Obj.Contingency;
import org.rmj.g3appdriver.lib.EmployeeLoan.model.LoanType;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

public class VMEmployeeLoanEntry extends AndroidViewModel implements DatePickerDialog.OnDateSetListener {
    private static final String TAG = VMEmployeeLoanEntry.class.getSimpleName();
    private final Contingency poSys;
    public EditText editDtPicker;
    public VMEmployeeLoanEntry(@NonNull Application application) {
        super(application);
        this.poSys = new Contingency(application);
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
    public void showDatePicker(Activity activity){
        Calendar cal = Calendar.getInstance();
        DatePickerDialog dpd = new DatePickerDialog(activity, this, cal.get(YEAR), cal.get(MONTH), cal.get(DAY_OF_MONTH));
        dpd.setCancelable(true);
        dpd.show();
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
        Log.d(TAG, String.valueOf(matchFormat));
        return matchFormat;
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        month += 1;
        String seldate = month + "/" + dayOfMonth + "/" + year;
        editDtPicker.setText(seldate);
    }
}
