package org.guanzongroup.com.itinerary.Dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.guanzongroup.com.itinerary.R;
import org.rmj.g3appdriver.etc.AppConstants;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DialogDateSelection {
    private static final String TAG = DialogDateSelection.class.getSimpleName();

    private final Context mContext;

    private TextInputEditText txtFrom, txtThru;

    private String psFrom = AppConstants.CURRENT_DATE, psThru = AppConstants.CURRENT_DATE;

    public interface OnConfirmListener{
        void OnConfirm(String args, String args1);
    }

    public DialogDateSelection(Context context) {
        this.mContext = context;
    }

    public void showDialog(OnConfirmListener listener){
        AlertDialog.Builder poBuilder = new AlertDialog.Builder(mContext);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_date_selection, null);
        poBuilder.setCancelable(false)
                .setView(view);
        AlertDialog poDialogx = poBuilder.create();
        poDialogx.setCancelable(false);

        txtFrom = view.findViewById(R.id.txt_dateFrom);
        txtThru = view.findViewById(R.id.txt_dateThru);

        txtFrom.setText(new AppConstants().CURRENT_DATE_WORD);
        txtThru.setText(new AppConstants().CURRENT_DATE_WORD);

        txtFrom.setOnClickListener(v -> {
            if(txtThru.getText().toString().trim().isEmpty()) {
                final Calendar newCalendar = Calendar.getInstance();
                @SuppressLint("SimpleDateFormat") final SimpleDateFormat dateFormatter = new SimpleDateFormat("MMMM dd, yyyy");
                final DatePickerDialog StartTime = new DatePickerDialog(mContext, R.style.MyTimePickerDialogTheme, (view131, year, monthOfYear, dayOfMonth) -> {
                    try{
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        String lsDate = dateFormatter.format(newDate.getTime());
                        txtFrom.setText(lsDate);
                        Date loDate = new SimpleDateFormat("MMMM dd, yyyy").parse(lsDate);
                        psFrom = new SimpleDateFormat("yyyy-MM-dd").format(loDate);
                        Log.d(TAG, "Date start: " + psFrom + ", UI format: " + lsDate);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                StartTime.show();
            } else {
                final Calendar newCalendar = Calendar.getInstance();
                @SuppressLint("SimpleDateFormat") final SimpleDateFormat dateFormatter = new SimpleDateFormat("MMMM dd, yyyy");
                final DatePickerDialog StartTime = new DatePickerDialog(mContext, R.style.MyTimePickerDialogTheme, (view131, year, monthOfYear, dayOfMonth) -> {
                    try{
                        Date loThru = new SimpleDateFormat("MMMM dd, yyyy").parse(txtThru.getText().toString().trim());
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        String lsDate = dateFormatter.format(newDate.getTime());
                        Date loDate = new SimpleDateFormat("MMMM dd, yyyy").parse(lsDate);
                        if(loThru.before(loDate)){
                            Toast.makeText(mContext, "Invalid date entry.", Toast.LENGTH_SHORT).show();
                        } else {
                            txtFrom.setText(lsDate);
                            psFrom = new SimpleDateFormat("yyyy-MM-dd").format(loDate);
                        }
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                StartTime.show();
            }
        });

        txtThru.setOnClickListener(v -> {
            if(txtFrom.getText().toString().trim().isEmpty()){
                Toast.makeText(mContext, "Date started must be set before the set time end.", Toast.LENGTH_SHORT).show();
            } else {
                final Calendar newCalendar = Calendar.getInstance();
                @SuppressLint("SimpleDateFormat") final SimpleDateFormat dateFormatter = new SimpleDateFormat("MMMM dd, yyyy");
                final DatePickerDialog StartTime = new DatePickerDialog(mContext, R.style.MyTimePickerDialogTheme, (view131, year, monthOfYear, dayOfMonth) -> {
                    try{
                        Date loFrom = new SimpleDateFormat("MMMM dd, yyyy").parse(txtFrom.getText().toString().trim());
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        String lsDate = dateFormatter.format(newDate.getTime());
                        Date loDate = new SimpleDateFormat("MMMM dd, yyyy").parse(lsDate);
                        if(loFrom.after(loDate)){
                            Toast.makeText(mContext, "Invalid date entry.", Toast.LENGTH_SHORT).show();
                        } else {
                            txtThru.setText(lsDate);
                            psThru = new SimpleDateFormat("yyyy-MM-dd").format(loDate);
                        }
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                StartTime.show();
            }
        });

        view.findViewById(R.id.btn_confirm).setOnClickListener(view1 -> {
            listener.OnConfirm(psFrom, psThru);
            poDialogx.dismiss();
        });

        view.findViewById(R.id.btn_cancel).setOnClickListener(view12 -> poDialogx.dismiss());

        poDialogx.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        poDialogx.getWindow().getAttributes().windowAnimations = org.rmj.g3appdriver.R.style.PopupAnimation;
        poDialogx.show();
    }
}
