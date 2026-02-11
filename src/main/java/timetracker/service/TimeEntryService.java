package timetracker.service;

import org.springframework.stereotype.Service;
import timetracker.db.TimeEntryRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Service
public class TimeEntryService {

    private final TimeEntryRepository repo;

    public TimeEntryService(TimeEntryRepository repo) {
        this.repo = repo;
    }

    public TimeEntryRepository.TimeEntryRow create(UUID userId, LocalDate date, LocalTime start, LocalTime end) {
        
        if (date == null || start == null || end == null) {
            throw new IllegalArgumentException("date/start/end må være satt");
        }
        if (!start.isBefore(end)) {
            throw new IllegalArgumentException("start må være før end");
        }
        return repo.insert(UUID.randomUUID(), userId, date, start, end);
    }
}
