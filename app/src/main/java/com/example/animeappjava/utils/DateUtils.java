package com.example.animeappjava.utils;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    public static String formatDateToAgo(String dateString) {
        PrettyTime prettyTime = new PrettyTime(Locale.getDefault());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault());
        try {
            Date date = sdf.parse(dateString);
            return prettyTime.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return dateString;
        }
    }
}