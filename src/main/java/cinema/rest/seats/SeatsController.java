package cinema.rest.seats;

import cinema.domain.seats.Seat;
import cinema.domain.seats.SeatsMapper;
import cinema.domain.seats.SeatsService;
import cinema.domain.resources.CinemaConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seats")
public class SeatsController {
    final int MAX_ROWS = CinemaConstants.MAX_ROWS_NUMBER;
    final int MAX_COLUMNS = CinemaConstants.MAX_COLUMNS_NUMBER;
    private final SeatsService seatsService;

    @Autowired
    public SeatsController(SeatsService seatsService) {
        this.seatsService = seatsService;
    }

    @GetMapping
    public ResponseEntity<AllSeatsResponse> getSeats(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Seat> seatsPage = seatsService.getAllSeats(pageRequest);

        AllSeatsResponse responseDTO = new AllSeatsResponse(
                MAX_ROWS,
                MAX_COLUMNS,
                SeatsMapper.toResponseList(seatsPage.getContent()),
                seatsPage.getTotalElements(),
                seatsPage.getTotalPages()
        );

        return ResponseEntity.ok(responseDTO);
    }
}

