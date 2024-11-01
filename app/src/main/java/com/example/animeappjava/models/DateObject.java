package com.example.animeappjava.models;

import java.util.Objects;

public class DateObject {
    private final Integer day;
    private final Integer month;
    private final Integer year;

    public DateObject(Integer day, Integer month, Integer year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public Integer getDay() {
        return day;
    }

    public Integer getMonth() {
        return month;
    }

    public Integer getYear() {
        return year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DateObject that = (DateObject) o;

        if (!Objects.equals(day, that.day)) return false;
        if (!Objects.equals(month, that.month)) return false;
        return Objects.equals(year, that.year);
    }

    @Override
    public int hashCode() {
        int result = day != null ? day.hashCode() : 0;
        result = 31 * result + (month != null ? month.hashCode() : 0);
        result = 31 * result + (year != null ? year.hashCode() : 0);
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