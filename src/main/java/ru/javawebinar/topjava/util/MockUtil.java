package ru.javawebinar.topjava.util;

import java.time.LocalTime;

public class MockUtil {
    private static final LocalTime defaultStartTime = LocalTime.MIN;
    private static final LocalTime defaultEndTime = LocalTime.MAX;
    private static final int defaultCaloriesPerDay = 2000;

    public static LocalTime getDefaultStartTime() {
        return defaultStartTime;
    }

    public static LocalTime getDefaultEndTime() {
        return defaultEndTime;
    }

    public static int getDefaultCaloriesPerDay() {
        return defaultCaloriesPerDay;
    }
}
