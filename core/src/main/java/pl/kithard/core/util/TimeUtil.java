package pl.kithard.core.util;

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

    public static long parseDateDiff(String time, boolean future) {
        try {
            Pattern timePattern = Pattern.compile("(?:([0-9]+)\\s*y[a-z]*[,\\s]*)?(?:([0-9]+)\\s*mo[a-z]*[,\\s]*)?(?:([0-9]+)\\s*w[a-z]*[,\\s]*)?(?:([0-9]+)\\s*d[a-z]*[,\\s]*)?(?:([0-9]+)\\s*h[a-z]*[,\\s]*)?(?:([0-9]+)\\s*m[a-z]*[,\\s]*)?(?:([0-9]+)\\s*(?:s[a-z]*)?)?", Pattern.CASE_INSENSITIVE);
            Matcher m = timePattern.matcher(time);
            int years = 0;
            int months = 0;
            int weeks = 0;
            int days = 0;
            int hours = 0;
            int minutes = 0;
            int seconds = 0;
            boolean found = false;
            while (m.find()) {
                if (m.group() != null && !m.group().isEmpty()) {
                    for (int i = 0; i < m.groupCount(); ++i) {
                        if (m.group(i) != null && !m.group(i).isEmpty()) {
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        continue;
                    }
                    if (m.group(1) != null && !m.group(1).isEmpty()) {
                        years = Integer.parseInt(m.group(1));
                    }
                    if (m.group(2) != null && !m.group(2).isEmpty()) {
                        months = Integer.parseInt(m.group(2));
                    }
                    if (m.group(3) != null && !m.group(3).isEmpty()) {
                        weeks = Integer.parseInt(m.group(3));
                    }
                    if (m.group(4) != null && !m.group(4).isEmpty()) {
                        days = Integer.parseInt(m.group(4));
                    }
                    if (m.group(5) != null && !m.group(5).isEmpty()) {
                        hours = Integer.parseInt(m.group(5));
                    }
                    if (m.group(6) != null && !m.group(6).isEmpty()) {
                        minutes = Integer.parseInt(m.group(6));
                    }
                    if (m.group(7) == null) {
                        break;
                    }
                    if (m.group(7).isEmpty()) {
                        break;
                    }
                    seconds = Integer.parseInt(m.group(7));
                    break;
                }
            }
            if (!found) {
                return -1L;
            }
            Calendar calendar = new GregorianCalendar();
            if (years > 0) {
                calendar.add(Calendar.YEAR, years * (future ? 1 : -1));
            }
            if (months > 0) {
                calendar.add(Calendar.MONTH, months * (future ? 1 : -1));
            }
            if (weeks > 0) {
                calendar.add(Calendar.WEEK_OF_YEAR, weeks * (future ? 1 : -1));
            }
            if (days > 0) {
                calendar.add(Calendar.DATE, days * (future ? 1 : -1));
            }
            if (hours > 0) {
                calendar.add(Calendar.HOUR_OF_DAY, hours * (future ? 1 : -1));
            }
            if (minutes > 0) {
                calendar.add(Calendar.MINUTE, minutes * (future ? 1 : -1));
            }
            if (seconds > 0) {
                calendar.add(Calendar.SECOND, seconds * (future ? 1 : -1));
            }
            final Calendar max = new GregorianCalendar();
            max.add(Calendar.YEAR, 10);
            if (calendar.after(max)) {
                return max.getTimeInMillis();
            }
            return calendar.getTimeInMillis();
        }
        catch (Exception e) {
            return -1L;
        }
    }

    public static int getHour(long time) {
        Date date = new Date(time);
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH");
        return Integer.parseInt(dateFormat.format(date));
    }

}