package com.example.Mybook.utilities;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class CommonMethods {

    public static Timestamp getCurrentTime()
    {
        return new Timestamp(System.currentTimeMillis());
    }
    public static Timestamp getEndTime(int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, +days);
        return new Timestamp(cal.getTimeInMillis());

    }
    public static Timestamp getCurrentUTCTime()
    {
        ZonedDateTime utc = ZonedDateTime.now(ZoneOffset.UTC);
        System.out.println("utc"+utc.toInstant());
        System.out.println("time -->>"+Timestamp.valueOf(utc.toLocalDateTime()));
        System.out.println("time in method -->>"+new Timestamp(utc.toInstant().toEpochMilli()));
        return Timestamp.valueOf(utc.toLocalDateTime());
    }
    public static Date getCurrentDate()
    {
        long millis=System.currentTimeMillis();
        return new java.sql.Date(millis);
    }
    public static int  getTimeTakenByExpert(Timestamp start, Timestamp end){
        long diffInMS = end.getTime() - start.getTime();
        return (int) TimeUnit.HOURS.convert(diffInMS, TimeUnit.MILLISECONDS);
    }

}
