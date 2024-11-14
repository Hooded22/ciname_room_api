package cinema.rest.seats;

import cinema.domain.seats.Seat;
import cinema.domain.seats.SeatsService;
import cinema.domain.resources.CinemaConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/seats")
public class SeatsController {
    final int MAX_ROWS = CinemaConstants.MAX_ROWS_NUMBER;
    final int MAX_COLUMNS = CinemaConstants.MAX_COLUMNS_NUMBER;

    @Autowired
    SeatsService seatsService;

    @GetMapping
    public ResponseEntity<AllSeatsResponseDTO> getSeats() {
        List<Seat> seats = seatsService.getAllSeats();
        AllSeatsResponseDTO responseDTO = new AllSeatsResponseDTO(MAX_ROWS, MAX_COLUMNS, SeatDTO.convertSeatsList(seats));

        return ResponseEntity.ok(responseDTO);
    }
}

