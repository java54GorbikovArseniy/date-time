package telran.time.test;

import org.junit.jupiter.api.Test;
import telran.time.BarMizvaAdjuster;
import telran.time.Friday13Range;
import telran.time.NextFriday13;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;

import static org.junit.jupiter.api.Assertions.*;

public class DateTimeTest {

    @Test
    void barMizvaAdjusterTest() {
        LocalDate birthDate = LocalDate.parse("1799-06-06");
        LocalDate expected = LocalDate.of(1812, 6, 6);
        assertEquals(expected, birthDate.with(new BarMizvaAdjuster()));
    }

    @Test
    void nextFriday13Test() {
        NextFriday13 nextFriday13 = new NextFriday13();
        LocalDate expected = LocalDate.of(2024, 9, 13);
        assertEquals(expected, nextFriday13.adjustInto((LocalDate.from(LocalDate.now()))));
        LocalDate expected1 = LocalDate.of(2024,12,13);
        assertEquals(expected1, nextFriday13.adjustInto((LocalDate.from(LocalDate.of(2024,9, 14)))));
        LocalDate wrongDate = LocalDate.of(2024,12,20);
        assertNotEquals(wrongDate, nextFriday13.adjustInto((LocalDate.from(LocalDate.of(2024,9, 14)))));
    }

    @Test
    void friday13RangeTest() {
        Temporal[] expected = new Temporal[]{
                LocalDate.of(2024, 9, 13),
                LocalDate.of(2024, 12, 13)
        };
        Temporal[] actual = new Temporal[expected.length];
        Temporal from = LocalDate.of(2024, 1, 1);
        Temporal to = LocalDate.of(2025, 1, 1);
        Friday13Range friday13Range = new Friday13Range(from, to);
        int length = 0;
        for (Temporal temporal : friday13Range) {
            actual[length++] = temporal;
        }
        assertArrayEquals(expected, actual);
    }

    @Test
    void displayCurrentDateTimeTest() {
        displayCurrentDateTime("Canada");
        displayCurrentDateTime("Israel");
        displayCurrentDateTime("abcd");
        displayCurrentDateTime(" ");
    }

    private void displayCurrentDateTime(String zonePart) {
        for (String zone : ZoneId.getAvailableZoneIds()) {
            if (zone.contains(zonePart)) {
                ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of(zone));
                String formattedDateTime = zonedDateTime.format(DateTimeFormatter.ofPattern("yyyy-M-d-H:m-zzzz"));
                System.out.println(formattedDateTime + "-" + zone);
            }
        }
    }
}