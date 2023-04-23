package pl.isa.thebuggers.fitly;

import java.util.Calendar;

public class TrainingCalendar {
    public void calendarData() {

        Calendar calendar = Calendar.getInstance();

        //ustawiamy datę
        calendar.set(Calendar.YEAR, 2023);
        calendar.set(Calendar.MONTH, Calendar.APRIL);
        calendar.set(Calendar.DAY_OF_MONTH, 2);
        //wprowadzenie godziny oraz danych
        calendar.set(Calendar.HOUR_OF_DAY, 10);
        calendar.set(Calendar.MINUTE, 30);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set( 1,2);
        calendar.set(3,4);

        //wypisanie danych
        System.out.println("Title: " + calendar.get(1));
        System.out.println("Description: " + calendar.get(3));
        System.out.println("Date: " + calendar.getTime());
        System.out.println("Time: " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE));
    }

}
