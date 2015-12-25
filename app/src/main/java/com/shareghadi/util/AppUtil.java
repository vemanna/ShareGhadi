package com.shareghadi.util;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by BVN on 12/24/2015.
 */

public class AppUtil {

    public static Map<String, String> convertObjectToMap(Object o) {
        HashMap<String, String> map = new HashMap<>();

        Field[] fields = o.getClass().getFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            try {
                map.put(field.getName(), String.valueOf(field.get(o)));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    public static String getTimeDifference(String start, String end) {
        String timeDif = "";
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d1 = format.parse(start);
            Date d2 = format.parse(end);
            long difference = d2.getTime() - d1.getTime();
            long differenceBack = difference / 1000;
            LogUtils.LOGD("Difference : ", start + ":" + end + ":" + String.valueOf(differenceBack));

            long hours = differenceBack / 3600;
            long mints = (differenceBack % 3600) / 60;

            timeDif = "Expires in " + String.valueOf(String.format("%02d", hours)) + "h " + String.valueOf(mints) + " mins";

        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        return timeDif;
    }

    public static String getExpriesColor(String start, String end, String itemsold, String total) {
        String color = "";
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d1 = format.parse(start);
            Date d2 = format.parse(end);
            Date currentDate = format.parse(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            long difference = d2.getTime() - d1.getTime();
            long differenceBack = difference / 1000;

            long differenceFromStart = currentDate.getTime() - d1.getTime();
            long differenceBackFromStart = differenceFromStart / 1000;

            if ((Integer.parseInt(itemsold) / differenceBackFromStart) < (Integer.parseInt(total) / differenceBack)) {
                color = "red";
            } else {
                color = "green";
            }

        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        return color;
    }

    public static String getClosedDate(String end) {
        String date = "";
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d1 = format.parse(end);

            format = new SimpleDateFormat("MMM dd, yyyy");
            date = format.format(d1);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return date;
    }
}
