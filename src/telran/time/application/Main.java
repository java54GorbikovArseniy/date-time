package telran.time.application;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.util.Arrays;
import java.util.Locale;

record MonthYear(int month, int year, String weekDay) {

}

public class Main {
    private static final int TITLE_OFFSET = 9;
    private static final int COLUMN_WIDTH = 4;
    private static String weekDay = "";

    public static void main(String[] args) {
        MonthYear monthYear = null;
        try {
            monthYear = getMonthYear(args);
            printCalendar(monthYear);
        } catch (RuntimeException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static MonthYear getMonthYear(String[] args) throws Exception {
        int monthNumber = getMonth(args);
        int year = getYear(args);
        String weekDay = getFirstWeekDay(args);
        return new MonthYear(monthNumber, year, weekDay);
    }

    private static String getFirstWeekDay(String[] args) throws Exception {
        weekDay = args.length < 3 ? getDefaultWeekDay() : getFirstWeekDay(args[2]);
        return weekDay;
    }

    private static String getDefaultWeekDay() {
        return DayOfWeek.MONDAY.getDisplayName(TextStyle.FULL, Locale.getDefault());
    }

    private static String getFirstWeekDay(String firstWeekDay) throws Exception {
        try {
            return DayOfWeek.valueOf(firstWeekDay.toUpperCase()).getDisplayName(TextStyle.FULL, Locale.getDefault());
        } catch (IllegalArgumentException e) {
            throw new Exception(firstWeekDay + " wrong day of week");
        }
    }

    private static int getYear(String[] args) throws Exception {
        return args.length < 2 ? getCurrentYear() : getYear(args[1]);
    }

    private static int getYear(String yearStr) throws Exception {
        try {
            return Integer.parseInt(yearStr);
        } catch (NumberFormatException e) {
            throw new Exception("year must be number");
        }
    }

    private static int getCurrentYear() {
        return LocalDate.now().getYear();
    }

    private static int getMonth(String[] args) throws Exception {
        return args.length == 0 ? getCurrentMonth() : getMonthNumber(args[0]);
    }

    private static int getMonthNumber(String monthString) throws Exception {
        try {
            int res = Integer.parseInt(monthString);
            if (res < 1) {
                throw new Exception("Month cannot be less than 1");
            }
            if (res > 12) {
                throw new Exception("Month cannot be more than 12");
            }
            return res;
        } catch (NumberFormatException e) {
            throw new Exception("Month must be a number");
        }
    }

    private static int getCurrentMonth() {
        return LocalDate.now().get(ChronoField.MONTH_OF_YEAR);
    }

    private static void printCalendar(MonthYear monthYear) {
        printTitle(monthYear);
        printWeekDays();
        printDays(monthYear);
    }

    private static void printDays(MonthYear monthYear) {
        int nDays = getDaysInMonth(monthYear);
        int firstDayOfWeek = getFirstDayOfMonth(monthYear);

        int startOffset = (firstDayOfWeek - DayOfWeek.valueOf(weekDay.toUpperCase()).getValue() + 7) % 7;
        for (int i = 0; i < startOffset; i++) {
            System.out.printf("%" + COLUMN_WIDTH + "s", "");
        }
        for (int day = 1; day <= nDays; day++) {
            System.out.printf("%" + COLUMN_WIDTH + "d", day);
            if ((startOffset + day) % 7 == 0 || day == nDays) {
                System.out.println();
            }
        }
    }

    private static int getFirstOffset(int currentWeekDay) {
        return COLUMN_WIDTH * (currentWeekDay - 1);
    }

    private static int getFirstDayOfMonth(MonthYear monthYear) {
        LocalDate localDate = LocalDate.of(monthYear.year(), monthYear.month(), 1);
        return localDate.get(ChronoField.DAY_OF_WEEK);
    }

    private static int getDaysInMonth(MonthYear monthYear) {
        YearMonth ym = YearMonth.of(monthYear.year(), monthYear.month());
        return ym.lengthOfMonth();
    }

    private static void printWeekDays() {
        System.out.printf("%s", " ".repeat(COLUMN_WIDTH / 3));
        DayOfWeek startDayOfWeek = DayOfWeek.valueOf(weekDay.toUpperCase());
        DayOfWeek[] orderedWeekdays = new DayOfWeek[7];
        for (int i = 0; i < 7; i++) {
            orderedWeekdays[i] = startDayOfWeek.plus(i);
        }
        for (DayOfWeek dayOfWeek : orderedWeekdays) {
            System.out.printf("%" + COLUMN_WIDTH + "s", dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()));
        }
        System.out.println();

    }

    private static void printTitle(MonthYear monthYear) {
        String monthName = Month.of(monthYear.month()).getDisplayName(TextStyle.FULL, Locale.getDefault());
        System.out.printf("%s%s %d\n", " ".repeat(TITLE_OFFSET), monthName, monthYear.year());
    }
}
