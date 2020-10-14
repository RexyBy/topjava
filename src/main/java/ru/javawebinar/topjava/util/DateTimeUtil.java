package ru.javawebinar.topjava.util;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static <T> boolean isBetweenHalfOpen(Comparable<T> toCheck, T start, T end) {
        return toCheck.compareTo(start) >= 0 && toCheck.compareTo(end) < 0;
    }

    public static <T> boolean isBetweenHalfClose(Comparable<T> toCheck, T start, T end) {
        return toCheck.compareTo(start) >= 0 && toCheck.compareTo(end) <= 0;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}

