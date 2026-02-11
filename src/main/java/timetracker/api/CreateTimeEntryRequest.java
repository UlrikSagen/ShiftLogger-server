package timetracker.api;

import java.time.LocalDate;
import java.time.LocalTime;

public record CreateTimeEntryRequest(
        LocalDate date,
        LocalTime start,
        LocalTime end
) {}
