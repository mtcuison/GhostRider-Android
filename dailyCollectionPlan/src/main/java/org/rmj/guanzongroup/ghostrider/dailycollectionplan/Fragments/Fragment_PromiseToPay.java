package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Etc.DatePickerFragment;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel.VMPromiseToPay;

public class Fragment_PromiseToPay extends Fragment {

    private VMPromiseToPay mViewModel;

    private TextInputEditText actPayDate;
    private MaterialButton btnConfirm;

    public static Fragment_PromiseToPay newInstance() {
        return new Fragment_PromiseToPay();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_promise_to_pay, container, false);
        initWidgets(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VMPromiseToPay.class);
        actPayDate.setOnClickListener(v ->  showDatePickerDialog(actPayDate));
        // TODO: Use the ViewModel
    }

    private void initWidgets(View v){
        actPayDate = v.findViewById(R.id.pToPayDate);

    }
    public void showDatePickerDialog(final TextInputEditText editText) {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                datePicker.setMinDate(System.currentTimeMillis() - 1000);
                final String selectedDate = twoDigits(day) + "/" + twoDigits(month+1) + "/" + year;
                editText.setText(selectedDate);

            }
        });

        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");

        //newFragment.show(activity.getSupportFragmentManager(), "datePicker");
        // newFragment.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

    }
    private String twoDigits(int n) {
        return (n<=9) ? ("0"+n) : String.valueOf(n);
    }
}