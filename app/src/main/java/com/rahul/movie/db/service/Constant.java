package com.rahul.movie.db.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.SimpleFormatter;

/**
 * \tCreated by Rahul on 8/2/2016.
 */

public final class Constant {

    static final String API_KEY = "YOUR_API_KEY";

    static final String BASE_URL = "https://api.themoviedb.org/3/";
    public static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/original";

    public static String getDateString(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date d;
        try {
            d = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            return "";
        }

        SimpleDateFormat formatter = new SimpleDateFormat("EEE d MMM yy", Locale.ENGLISH);
        if (d != null) {
            return formatter.format(d);
        } else {
            return "";
        }
    }
}
