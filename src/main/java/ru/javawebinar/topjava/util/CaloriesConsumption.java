package ru.javawebinar.topjava.util;

public class CaloriesConsumption {
    int calories = 0;
    boolean excess = false;

    public int getCalories() {
        return calories;
    }

    public void addCalories(int calories) {
        this.calories += calories;
    }

    public boolean isExcess() {
        return excess;
    }

    public void setExcess(boolean excess) {
        this.excess = excess;
    }
}
