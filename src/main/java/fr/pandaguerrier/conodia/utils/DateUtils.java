package fr.pandaguerrier.conodia.utils;

import java.time.*;
import java.util.ArrayList;

public class DateUtils {
    private static Period getPeriod(LocalDateTime dob, LocalDateTime now) {
        return Period.between(dob.toLocalDate(), now.toLocalDate());
    }

    private static int[] getTime(LocalDateTime dob, LocalDateTime now) {
        LocalDateTime today = LocalDateTime.of(now.getYear(),
                now.getMonthValue(), now.getDayOfMonth(), dob.getHour(), dob.getMinute(), dob.getSecond());
        Duration duration = Duration.between(today, now);

        long seconds = duration.getSeconds();

        int hours = (int) seconds / 3600;
        int minutes = (int) ((seconds % 3600) / 60);
        int secs = (int) (seconds % 60);

        return new int[]{hours, minutes, secs};
    }

    private static String LocalDateTimeDifference(LocalDateTime localDateTime, LocalDateTime localDateTimeDefault) {
        Period period = getPeriod(localDateTimeDefault, localDateTime);
        int[] time = getTime(localDateTimeDefault, localDateTime);
        int year = period.getYears();
        int months = period.getMonths();
        int day = period.getDays();
        int hours = time[0];
        int minutes = time[1];
        int seconds = time[2];
        ArrayList<String> sal = getListFromValues(year, months, day, hours, minutes, seconds);
        return getFormattedStringFromList(sal);
    }

    private static String getFormattedStringFromList(ArrayList<String> sal){
        StringBuilder date = new StringBuilder();
        if(!sal.isEmpty()){
            for(int i = 0; i < sal.size(); i++){
                if(i != 0){ date.append(" "); }
                if(i != 0 && i == sal.size() - 1){ date.append("et "); }
                date.append(sal.get(i));
            }
        } else {
            date = new StringBuilder("0 seconde");
        }
        return date.toString();
    }

    public static ArrayList<String> getListFromValues(int year, int months, int day, int hours, int minutes, int seconds){
        ArrayList<String> sal = new ArrayList<>();
        if(year > 0){ if(year == 1){ sal.add(year + " an"); } else { sal.add(year + " ans"); } }
        if(months > 0){ if(months == 1){ sal.add(months + " mois"); } else { sal.add(months + " mois"); } }
        if(day > 0){ if(day == 1){ sal.add(day + " jour"); } else { sal.add(day + " jours"); } }
        if(hours > 0){ if(hours == 1){ sal.add(hours + " heure"); } else { sal.add(hours + " heures"); } }
        if(minutes > 0){ if(minutes == 1){ sal.add(minutes + " minute"); } else { sal.add(minutes + " minutes"); } }
        if(seconds > 0){ if(seconds == 1){ sal.add(seconds + " seconde"); } else { sal.add(seconds + " secondes"); } }
        if(sal.isEmpty()){ sal.add("0 seconde"); }
        return sal;
    }

    public static String AllValuesToFormattedString(int year, int months, int day, int hours, int minutes, int seconds){
        ArrayList<String> sal = getListFromValues(year, months, day, hours, minutes, seconds);
        StringBuilder date = new StringBuilder();
        for(int i = 0; i < sal.size(); i++){
            if(i != 0 && i == sal.size() - 1){ date.append("et "); }
            date.append(sal.get(i));
        }
        return date.toString();
    }

    public static String getDateAfterCheck(String date, String check){
        if(date.contains(check)){
            String[] split = date.split(check);
            if(split.length == 2){ return split[1]; }
            else { return ""; }
        }
        return date;
    }

    public static boolean isInvalid(String date, String check){
        try {
            if(date.contains(check)){ return StringUtils.countMatches(date, check) != 1 || date.split(check).length == 0 || (date.split(check).length > 0 && !CheckUtils.isInteger(date.split(check)[0])); }
        } catch (Exception exception){ exception.printStackTrace(); return true; }
        return false;
    }

    public static boolean isValid(String date){
        if(isInvalid(date, "y")){ return false; }
        date = getDateAfterCheck(date, "y");

        if(isInvalid(date, "mo")){ return false; }
        date = getDateAfterCheck(date, "mo");

        if(isInvalid(date, "d")){ return false; }
        date = getDateAfterCheck(date, "d");

        if(isInvalid(date, "h")){ return false; }
        date = getDateAfterCheck(date, "h");

        if(isInvalid(date, "m")){ return false; }
        date = getDateAfterCheck(date, "m");

        if(isInvalid(date, "s")){ return false; }
        date = getDateAfterCheck(date, "s");

        return !StringUtils.containsLetter(date);
    }

    public static String unixEndToDate(long unixEnd){
        String date = "";
        long unixCurrent = System.currentTimeMillis() / 1000;
        if(unixCurrent >= unixEnd){ date = "0 seconde"; }
        else {
            long diff = unixEnd - unixCurrent;
            Instant instant = Instant.ofEpochSecond(diff);
            LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.of("Europe/Paris"));
            LocalDateTime localDateTimeDefault = LocalDateTime.of(1970, 1, 1, 1, 0, 0);
            date = LocalDateTimeDifference(localDateTime, localDateTimeDefault);
        }
        return date;
    }

    public static String unixToTimer(long unixStart, long unixEnd){
        String date = "";
        Instant instantStart = Instant.ofEpochMilli(unixStart);
        LocalDateTime localDateTimeStart = LocalDateTime.ofInstant(instantStart, ZoneId.of("Europe/Paris"));
        Instant instantEnd = Instant.ofEpochSecond(unixEnd);
        LocalDateTime localDateTimeEnd = LocalDateTime.ofInstant(instantEnd, ZoneId.of("Europe/Paris"));
        date = LocalDateTimeDifference(localDateTimeEnd, localDateTimeStart);

        return date;
    }

    public static String unixToDate(long unix) {
        String date = "";
        Instant instant = Instant.ofEpochMilli(unix);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.of("Europe/Paris"));
        date = localDateTime.getDayOfMonth() + "/" + localDateTime.getMonthValue() + "/" + localDateTime.getYear() + " " + localDateTime.getHour() + ":" + localDateTime.getMinute() + ":" + localDateTime.getSecond();
        return date;
    }

    private static int getValueFromStringDate(String date, String check) {
        if(date.contains(check)){
            String[] split = date.split(check);
            if(split.length >= 1){ return CheckUtils.isInteger(split[0]) ? Integer.parseInt(split[0]) : 0; }
        }
        return 0;
    }

    public static int[] getIntArrayFromMinimalStringDate(String date){
        // year , months , days, hours, minutes, seconds
        int year = 0, months = 0, days = 0, hours = 0, minutes = 0, seconds = 0;
        if(isValid(date)){
            year = getValueFromStringDate(date, "y");
            date = getDateAfterCheck(date, "y");

            months = getValueFromStringDate(date, "mo");
            date = getDateAfterCheck(date, "mo");

            days = getValueFromStringDate(date, "d");
            date = getDateAfterCheck(date, "d");

            hours = getValueFromStringDate(date, "h");
            date = getDateAfterCheck(date, "h");

            minutes = getValueFromStringDate(date, "m");
            date = getDateAfterCheck(date, "m");

            seconds = getValueFromStringDate(date, "s");
            date = getDateAfterCheck(date, "s");
        }

        return new int[]{year, months, days, hours, minutes, seconds};
    }

    public static long getUnixEndTimeFromValues(int year, int months, int days, int hours, int minutes, int seconds){
        long unixCurrent = System.currentTimeMillis() / 1000;
        Instant instant = Instant.ofEpochSecond(unixCurrent);
        ZoneId zoneId = ZoneId.of("Europe/Paris");

        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zoneId);
        localDateTime = localDateTime.plusYears(year)
                .plusMonths(months)
                .plusDays(days)
                .plusHours(hours)
                .plusMinutes(minutes)
                .plusSeconds(seconds);
        ZoneOffset zoneOffset = zoneId.getRules().getOffset(localDateTime);
        return localDateTime.toEpochSecond(zoneOffset);
    }

    public static long unixEndFromFormattedDate(String date){
        int[] list = getIntArrayFromMinimalStringDate(date);
        int year = list[0], months = list[1], days = list[2],hours = list[3], minutes = list[4], seconds = list[5];
        return getUnixEndTimeFromValues(year, months, days, hours, minutes, seconds);
    }

}
