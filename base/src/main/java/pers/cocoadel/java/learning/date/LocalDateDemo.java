package pers.cocoadel.java.learning.date;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LocalDateDemo {

    public static void main(String[] args) {
        LocalDate today = LocalDate.now();
        System.out.println("today = " + today);

        LocalDate alonzosBirthday = LocalDate.of(1903, 6, 14);
        alonzosBirthday = LocalDate.of(1903, Month.JUNE, 14);
        System.out.println("alonzosBirthday = " + alonzosBirthday);

        // 程序员日：每年的第256天
        LocalDate programsDay = LocalDate.of(today.getYear(),1,1).plusDays(256);
        System.out.println("programsDay = " + programsDay);

        //
        LocalDate independenceDay = LocalDate.of(2018, Month.JULY, 4);
        LocalDate christmas = LocalDate.of(2018, Month.DECEMBER, 25);

        //这个 api 产生的天数不是很准确，因为每一个月的天数都不相同
        System.out.println("until christmas: " + independenceDay.until(christmas));
        //需要使用下面这个 api
        System.out.println("until christmas: " + independenceDay.until(christmas, ChronoUnit.DAYS));

        //如果+一个月后的最大日期溢出，不会抛出异常，回返回那个月的最大日期
        //例如 1.31 加上一共月后 等于 2.29/28
        System.out.println("2021.01.31 + 1 month = " + LocalDate.of(2021, 1, 31).plusMonths(1));
        //同理减一个月也是
        System.out.println("2021.03.31 - 1 month = " + LocalDate.of(2021, 3, 31).minusMonths(1));


        DayOfWeek startOfLastMillennium = LocalDate.of(1900,1,1).getDayOfWeek();
        System.out.println("startOfLastMillennium = " + startOfLastMillennium);
        System.out.println(startOfLastMillennium.getValue());
        System.out.println(DayOfWeek.SATURDAY.plus(3));

        // java 9 的 api
//        LocalDate start = LocalDate.of(2000,1,1);
//        LocalDate endExclusive = LocalDate.now();
//        Stream<LocalDate> firstDayInMonths = start.datasUntil(endExclusive, Period.ofMonths(1));
//        System.out.println("firstDayInMonths.collect(Collectors.toList()) = " + firstDayInMonths.collect(Collectors.toList()));

    }
}
