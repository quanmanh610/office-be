package com.cmcglobal.backend.utility;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.cmcglobal.backend.constant.Constant.DD_MM_YYYY;
import static com.cmcglobal.backend.constant.Constant.YYYY_MM_DD;
import static java.time.temporal.ChronoUnit.DAYS;

public class DateUtility {
    public static String toStringYYYY_MM_DD(String strDate) {
        LocalDate localDate = LocalDate.parse(strDate, DateTimeFormatter.ofPattern(DD_MM_YYYY));
        return localDate.format(DateTimeFormatter.ofPattern(YYYY_MM_DD));
    }

    public static int calculateDaysBetween(LocalDate fromDate, LocalDate toDate) {
        return (int) Math.abs(DAYS.between(fromDate, toDate)) + 1;
    }
}
