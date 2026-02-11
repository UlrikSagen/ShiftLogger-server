package timetracker.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import timetracker.db.TimeEntryRepository;
import timetracker.dto.TimeEntryDto;
import timetracker.security.UserPrincipal;
import timetracker.service.TimeEntryService;

@RestController
public class TimeEntryController {

    private final TimeEntryService service;

    public TimeEntryController(TimeEntryService service) {
        this.service = service;
    }

    @PostMapping("/entries")
    @ResponseStatus(HttpStatus.CREATED)
    public TimeEntryDto create(@RequestBody CreateTimeEntryRequest req, @AuthenticationPrincipal UserPrincipal me) {
        if (me == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        try {
            TimeEntryRepository.TimeEntryRow saved = service.create(me.userId(), req.date(), req.start(), req.end());

            return new TimeEntryDto(
                    saved.id(),
                    saved.userId(),
                    saved.date(),
                    saved.start(),
                    saved.end(),
                    saved.createdAt(),
                    saved.lastEdit()
            );
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }
}
