package tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import exception.PiggyException;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class EventTest {
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    //private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("EEEE, MMM dd yyyy, h:mma");
    //private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("EEEE, MMM dd yyyy");

    @Test
    public void constructor_invalidTimeRange_throwsException() {
        try {
            new Event("Conference",
                    LocalDateTime.parse("12/3/2025 1800", INPUT_FORMATTER),
                    LocalDateTime.parse("10/3/2025 1000", INPUT_FORMATTER));
            assertEquals(1, 0); // Constructor should not be able to reach this line -> else: fail
        } catch (PiggyException e) {
            assertEquals("Event start time must be before end time.", e.getMessage());
        }
    }

    @Test
    public void includesDate_withinEventPeriod_returnsTrue() throws PiggyException {
        Event event = new Event("Conference",
                LocalDateTime.parse("10/3/2025 1000", INPUT_FORMATTER),
                LocalDateTime.parse("12/3/2025 1800", INPUT_FORMATTER));

        boolean result = event.includesDate(LocalDate.parse("11/3/2025", DateTimeFormatter.ofPattern("d/M/yyyy")));
        assertEquals(true, result);
    }

    @Test
    public void includesDate_withinEventPeriod_returnsFalse() throws PiggyException {
        Event event = new Event("Conference",
                LocalDateTime.parse("10/3/2025 1000", INPUT_FORMATTER),
                LocalDateTime.parse("12/3/2025 1800", INPUT_FORMATTER));

        boolean result = event.includesDate(LocalDate.parse("9/3/2025", DateTimeFormatter.ofPattern("d/M/yyyy")));
        assertEquals(false, result);
    }

    @Test
    public void getDates_correctlyFormatsDates() throws PiggyException {
        Event event = new Event("Conference",
                LocalDateTime.parse("10/3/2025 1000", INPUT_FORMATTER),
                LocalDateTime.parse("12/3/2025 1800", INPUT_FORMATTER));

        assertEquals("from: Monday, Mar 10 2025, to: Wednesday, Mar 12 2025", event.getDates());
    }
}

