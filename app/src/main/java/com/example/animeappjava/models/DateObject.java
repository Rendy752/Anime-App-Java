package com.example.animeappjava.models;

public class DateObject {
    private final int day;
    private final int month;
    private final int year;

    public DateObject(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DateObject that = (DateObject) o;

        if (day != that.day) return false;
        if (month != that.month) return false;
        return year == that.year;
    }

    @Override
    public int hashCode() {
        int result = day;
        result = 31 * result + month;
        result = 31 * result + year;
        return result;
    }

    @Override
    public String toString() {
        return "DateObject{" +
                "day=" + day +
                ", month=" + month +
                ", year=" + year +
                '}';
    }
}