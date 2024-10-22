package com.example.animeappjava.models;

public class Broadcast {
    private final String day;
    private final String time;
    private final String timezone;
    private final String string;

    public Broadcast(String day, String time, String timezone, String string) {
        this.day = day;
        this.time = time;
        this.timezone = timezone;
        this.string = string;
    }

    public String getDay() {
        return day;
    }

    public String getTime() {
        return time;
    }

    public String getTimezone() {
        return timezone;
    }

    public String getString() {
        return string;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Broadcast broadcast = (Broadcast) o;

        if (day != null ? !day.equals(broadcast.day) : broadcast.day != null) return false;
        if (time != null ? !time.equals(broadcast.time) : broadcast.time != null) return false;
        if (timezone != null ? !timezone.equals(broadcast.timezone) : broadcast.timezone != null) return false;
        return string != null ? string.equals(broadcast.string) : broadcast.string == null;
    }

    @Override
    public int hashCode() {
        int result = day != null ? day.hashCode() : 0;
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (timezone != null ? timezone.hashCode() : 0);
        result = 31 * result + (string != null ? string.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Broadcast{" +
                "day='" + day + '\'' +
                ", time='" + time + '\'' +
                ", timezone='" + timezone + '\'' +
                ", string='" + string + '\'' +
                '}';
    }
}