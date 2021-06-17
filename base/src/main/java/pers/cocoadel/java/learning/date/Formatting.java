package pers.cocoadel.java.learning.date;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.format.TextStyle;
import java.util.Locale;

public class Formatting {
    public static void main(String[] args) {
        //春节
        ZonedDateTime springFestival = ZonedDateTime.of(
                2020,2,12,0,0,0,0, ZoneId.of("Asia/Shanghai"));


        String formatted = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(springFestival);
        System.out.println("formatted1 = " + formatted);

        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG);
        formatted = formatter.format(springFestival);
        System.out.println("formatted2 = " + formatted);

        formatted = formatter.withLocale(Locale.CHINA).format(springFestival);
        System.out.println("formatted3 = " + formatted);

        formatter = DateTimeFormatter.ofPattern("E yyyy-MM-dd HH:mm");
        formatted = formatter.format(springFestival);
        System.out.println("formatted4 = " + formatted);

        LocalDate birthDay = LocalDate.parse("1988-03-24");
        System.out.println("birthDay = " + birthDay);
        springFestival = ZonedDateTime.parse("2020-02-12 00:00:00-0000",DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssxx"));
        System.out.println("springFestival = " + springFestival);

        for (DayOfWeek w : DayOfWeek.values()) {
            System.out.println(w.getDisplayName(TextStyle.SHORT, Locale.CHINA));
        }
    }
}
