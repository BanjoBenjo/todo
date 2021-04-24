package com.example.todo.dialogfragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    int selectedYear = 0;
    int selectedMonth = 0;
    int selectedDay = 0;
    public Boolean selected = false;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();

        if (selectedYear != 0){
            return new DatePickerDialog(getActivity(), this, selectedYear, selectedMonth, selectedDay);
        }

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        selectedDay = day;
        selectedMonth = month;
        selectedYear = year;
        selected = true;
    }

    public void setSelectedDay(int selectedDay) {
        this.selectedDay = selectedDay;
    }

    public void setSelectedMonth(int selectedMonth) {
        this.selectedMonth = selectedMonth;
    }

    public void setSelectedYear(int selectedYear) {
        this.selectedYear = selectedYear;
    }

    public void setSelected() {
        this.selected = true;
    }

    public int getSelectedDay(){
        return selectedDay;
    }

    public int getSelectedMonth(){
        return selectedMonth;
    }

    public int getSelectedYear(){
        return selectedYear;
    }
}
