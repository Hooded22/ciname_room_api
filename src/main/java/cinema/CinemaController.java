package cinema;

import cinema.dto.ErrorResponseDTO;
import cinema.dto.PurchaseSeatBodyDTO;
import cinema.exceptions.ErrorMessages;
import cinema.passwordValidation.PasswordValidationService;
import cinema.passwordValidation.exceptions.WrongPasswordException;
import cinema.seats.exception.SeatNotFoundException;
import cinema.seats.exception.SeatOccupiedException;
import cinema.statisctics.StatisticsResponseDTO;
import cinema.statisctics.StatisticsService;
import cinema.tickets.Ticket;
import cinema.tickets.TicketsService;
import cinema.tickets.dto.ReturnTicketBodyDTO;
import cinema.tickets.dto.ReturnTicketResponseDTO;
import cinema.tickets.exceptions.WrongTokenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class CinemaController {

    @Autowired
    CinemaService cinemaService;
    @Autowired
    TicketsService ticketsService;
    @Autowired
    StatisticsService statisticsService;
    @Autowired
    private PasswordValidationService passwordValidationService;

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
        ErrorResponseDTO response = new ErrorResponseDTO(ErrorMessages.SEAT_NOT_FOUND.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(SeatOccupiedException.class)
    public ResponseEntity<ErrorResponseDTO> handleSeatOccupiedException(SeatOccupiedException exception) {
        ErrorResponseDTO response = new ErrorResponseDTO(ErrorMessages.TICKET_ALREADY_PURCHASED.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(WrongTokenException.class)
    public ResponseEntity<ErrorResponseDTO> handleWrongTokenException(WrongTokenException exception) {
        ErrorResponseDTO response = new ErrorResponseDTO(exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(WrongPasswordException.class)
    public ResponseEntity<ErrorResponseDTO> handleWrongPassword(WrongPasswordException exception) {
        return buildUnauthorizedResponse();
    }

    private ResponseEntity<ErrorResponseDTO> buildUnauthorizedResponse() {
        ErrorResponseDTO response = new ErrorResponseDTO(ErrorMessages.WRONG_PASSWORD.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

}
