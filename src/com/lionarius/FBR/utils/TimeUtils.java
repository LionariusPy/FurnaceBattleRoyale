package com.lionarius.FBR.utils;

public class TimeUtils {

    public static String getFormattedTime(long time)
    {
        long processedTime = time;
        StringBuilder timeString = new StringBuilder();

        int hours, minutes, seconds;

        hours = (int) processedTime / 3600;
        processedTime -= hours*3600;
        minutes = (int) processedTime / 60;
        processedTime -= minutes*60;
        seconds = (int) processedTime;

        if(hours > 0) timeString.append(hours).append("ч");
        if(minutes > 0 || hours > 0) timeString.append(minutes).append("м");
        if(seconds > 0 || minutes > 0 || hours > 0) timeString.append(seconds).append("с");

        return timeString.toString();
    }
}
