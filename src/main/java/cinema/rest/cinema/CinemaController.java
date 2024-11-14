package cinema.rest.cinema;

import cinema.domain.exceptions.*;
import cinema.rest.error.ErrorResponseDTO;
import cinema.rest.seats.PurchaseSeatBodyDTO;
import cinema.domain.auth.PasswordValidationService;
import cinema.rest.statisctics.StatisticsResponseDTO;
import cinema.domain.statistics.StatisticsService;
import cinema.domain.tickets.Ticket;
import cinema.domain.tickets.TicketsService;
import cinema.rest.tickets.dto.ReturnTicketBodyDTO;
import cinema.rest.tickets.dto.ReturnTicketResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class CinemaController {

    private final TicketsService ticketsService;
    private final StatisticsService statisticsService;
    private final PasswordValidationService passwordValidationService;

    @Autowired
    public CinemaController(TicketsService ticketsService, StatisticsService statisticsService
    , PasswordValidationService passwordValidationService) {
        this.ticketsService = ticketsService;
        this.statisticsService = statisticsService;
        this.passwordValidationService = passwordValidationService;
    }

    @PostMapping("/purchase")
    public ResponseEntity<Ticket> purchaseSeat(@RequestBody PurchaseSeatBodyDTO body) {
        Ticket ticket = ticketsService.purchaseTicket(body.getRow(), body.getColumn());

        return new ResponseEntity<>(ticket, HttpStatus.OK);
    }

    @PostMapping("/return")
    public ResponseEntity<ReturnTicketResponseDTO> returnTicket(@RequestBody ReturnTicketBodyDTO body) {
        return new ResponseEntity<>(ticketsService.returnTicket(body.getToken()), HttpStatus.OK);

    }

    @GetMapping("/stats")
    public ResponseEntity<StatisticsResponseDTO> getAllSeats(@RequestParam String password) {
        passwordValidationService.validatePassword(password);

        StatisticsResponseDTO responseDTO = statisticsService.getStatistics();
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponseDTO> handleMissingParams(MissingServletRequestParameterException ex) {
        String missingParam = ex.getParameterName();
        if (missingParam.equals("password")) {
            return buildUnauthorizedResponse();
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SeatNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleSeatNotFoundException(SeatNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(exception.getMessage()));
    }

    @ExceptionHandler(SeatOccupiedException.class)
    public ResponseEntity<ErrorResponseDTO> handleSeatOccupiedException(SeatOccupiedException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(exception.getMessage()));
    }

    @ExceptionHandler(WrongTokenException.class)
    public ResponseEntity<ErrorResponseDTO> handleWrongTokenException(WrongTokenException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(exception.getMessage()));
    }

    @ExceptionHandler(WrongPasswordException.class)
    public ResponseEntity<ErrorResponseDTO> handleWrongPassword() {
        return buildUnauthorizedResponse();
    }

    private ResponseEntity<ErrorResponseDTO> buildUnauthorizedResponse() {
        ErrorResponseDTO response = new ErrorResponseDTO(ErrorMessages.WRONG_PASSWORD.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

}
