package telran.time;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;

public class NextFriday13 implements TemporalAdjuster {
    @Override
    public Temporal adjustInto(Temporal temporal) {
        LocalDate date = LocalDate.from(temporal);
        date = date.withDayOfMonth(13);
        if (date.isBefore(LocalDate.from(temporal)) || date.getDayOfWeek() != DayOfWeek.FRIDAY) {
            date = date.plusMonths(1);
            date = date.withDayOfMonth(13);
        }
        while (date.getDayOfWeek() != DayOfWeek.FRIDAY) {
            date = date.plusMonths(1);
            date = date.withDayOfMonth(13);
        }
        return date;
    }
}
