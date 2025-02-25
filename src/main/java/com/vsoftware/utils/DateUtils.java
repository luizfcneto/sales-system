package com.vsoftware.utils;

import java.time.LocalDate;
import java.time.temporal.ChronoField;

public class DateUtils {
	
	public static LocalDate calculateNextClosing(int closingDay, LocalDate reference) {
        LocalDate nextClosing = reference.withDayOfMonth(closingDay);
        if (reference.isAfter(nextClosing)) {
            nextClosing = nextClosing.plusMonths(1);
        }
        
        while (!nextClosing.isSupported(ChronoField.DAY_OF_MONTH)) {
            nextClosing = nextClosing.minusDays(1);
        }
        
        return nextClosing;
    }
	
	public static LocalDate calculateLastClosing(int closingDay, LocalDate referenceDate) {
        LocalDate lastClosing;

        if (referenceDate.getDayOfMonth() <= closingDay) {
            lastClosing = LocalDate.of(referenceDate.getYear(), referenceDate.getMonthValue() - 1, closingDay);
        } else {
            lastClosing = LocalDate.of(referenceDate.getYear(), referenceDate.getMonthValue(), closingDay);
        }

        if (lastClosing.getMonthValue() == 0) {
            lastClosing = LocalDate.of(lastClosing.getYear() - 1, 12, closingDay);
        }

        return lastClosing;
    }

}
