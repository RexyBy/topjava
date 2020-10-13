package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class MealFilter {
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;

    public MealFilter(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public boolean isEmpty(){
        return startDate != null || endDate != null || startTime != null || endTime != null;
    }

    public LocalDate getStartDate() {
        if (startDate == null){
            return LocalDate.MIN;
        }
        return startDate;
    }

    public LocalDate getEndDate() {
        if (endDate == null){
            return LocalDate.MAX;
        }
        return endDate;
    }

    public LocalTime getStartTime() {
        if (startTime == null){
            return LocalTime.MIN;
        }
        return startTime;
    }

    public LocalTime getEndTime() {
        if (endTime == null) {
            return LocalTime.MAX;
        }
        return endTime;
    }
}
