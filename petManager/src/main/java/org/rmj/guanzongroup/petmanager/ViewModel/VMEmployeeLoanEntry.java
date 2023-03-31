package org.rmj.guanzongroup.petmanager.ViewModel;

import android.app.Activity;
import android.app.Application;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.google.android.material.textfield.TextInputEditText;

import org.rmj.g3appdriver.lib.EmployeeLoan.Obj.EmployeeLoan;
import org.rmj.g3appdriver.lib.EmployeeLoan.pojo.LoanType;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class VMEmployeeLoanEntry extends AndroidViewModel{
    private static final String TAG = VMEmployeeLoanEntry.class.getSimpleName();
    private final EmployeeLoan poSys;
    public EditText editDtPicker;
    public VMEmployeeLoanEntry(@NonNull Application application) {
        super(application);
        this.poSys = new EmployeeLoan(application);
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
}
