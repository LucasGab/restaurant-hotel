package com.java.restaurant_hotel.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    private DateUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static Date convertToDate(String date) {
        try {
            return new SimpleDateFormat("dd/MM/yyyy").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("Error trying to parse data");
            return null;
        }
    }
}
