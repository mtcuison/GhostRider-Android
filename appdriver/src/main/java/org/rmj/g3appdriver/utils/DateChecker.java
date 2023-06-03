package org.rmj.g3appdriver.utils;

import java.util.Calendar;

public class DateChecker {
    public static String calculateServiceDate(double totalServiceLength) {
        // Get the current calendar instance
        Calendar calendar = Calendar.getInstance();

        // Subtract the total service length from the current year
        int year = calendar.get(Calendar.YEAR) - (int) totalServiceLength;

        // Subtract the decimal part from the current month
        double remainingMonths = (totalServiceLength - (int) totalServiceLength) * 12;
        int month = calendar.get(Calendar.MONTH) - (int) remainingMonths;

        // Adjust the month if it becomes negative
        if (month < 0) {
            month += 12;
            year -= 1;
        }

        // Create a string representation of the service date
        String serviceDate = getMonthName(month) + " " + year;

        return serviceDate;
    }

    private static String getMonthName(int month) {
        // Array of month names
        String[] monthNames = {"January", "February", "March", "April", "May", "June", "July",
                "August", "September", "October", "November", "December"};

        // Adjust month value to match array index
        month -= 1;

        // Ensure the month value is within valid range
        if (month < 0 || month >= monthNames.length) {
            return "";
        }

        // Get the corresponding month name
        return monthNames[month];
    }
}
