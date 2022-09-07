package pl.kithard.core.api.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class TimeUtil {

    private TimeUtil() {}

    public static String formatTimeMillisToDate(long millis) {
        if (millis < 0){
            return "nigdy";
        }

        return new SimpleDateFormat("HH:mm:ss dd.MM.yyyy").format(new Date(millis));
    }

    public static String formatTimeMillis(long millis) {
        if (millis == -1) {
            return "nigdy";
        }

        long seconds = millis / 1000L;

        if (seconds <= 0) {
            return (int) TimeUnit.MILLISECONDS.toMillis(millis) + "ms";
        }

        long minutes = seconds / 60;
        seconds = seconds % 60;
        long hours = minutes / 60;
        minutes = minutes % 60;
        long day = hours / 24;
        hours = hours % 24;
        long years = day / 365;
        day = day % 365;

        StringBuilder time = new StringBuilder();

        if (years != 0) {
            time.append(years).append("r");
        }

        if (day != 0) {
            time.append(day).append("d");
        }

        if (hours != 0) {
            time.append(hours).append("h");
        }

        if (minutes != 0) {
            time.append(minutes).append("min");
        }

        if (seconds != 0) {
            time.append(seconds).append("s");
        }

        return time.toString().trim();
    }

    public static long timeFromString(String string) {
        if (string.isEmpty()) {
            return 0L;
        }

        StringBuilder builder = new StringBuilder();
        long time = 0L;

        for (char character : string.toCharArray()) {
            if (Character.isDigit(character)) {
                builder.append(character);
                continue;
            }

            int amount = Integer.parseInt(builder.toString());
            switch (character) {
                case 'd': {
                    time += TimeUnit.DAYS.toMillis(amount);
                    break;
                }
                case 'h': {
                    time += TimeUnit.HOURS.toMillis(amount);
                    break;
                }
                case 'm': {
                    time += TimeUnit.MINUTES.toMillis(amount);
                    break;
                }
                case 's': time += TimeUnit.SECONDS.toMillis(amount);
            }

            builder = new StringBuilder();
        }

        return time;
    }

    public static int getHour(long time) {
        Date date = new Date(time);
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH");
        return Integer.parseInt(dateFormat.format(date));
    }

}