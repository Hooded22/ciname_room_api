package cinema.domain.tickets;

import cinema.config.TestCacheConfig;
import cinema.domain.archivedTicket.ArchivedTicket;
import cinema.domain.archivedTicket.ArchivedTicketRepository;
import cinema.domain.exceptions.ErrorMessages;
import cinema.domain.exceptions.WrongTokenException;
import cinema.domain.seats.Seat;
import cinema.domain.seats.SeatsMapper;
import cinema.domain.seats.SeatsService;
import cinema.rest.seats.SeatResponse;
import cinema.rest.tickets.ReturnTicketResponse;
import cinema.rest.tickets.TicketResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Import(TestCacheConfig.class)
class TicketsServiceTest {

    @Mock
    private TicketsRepository ticketsRepository;

    @Mock
    private ArchivedTicketRepository archivedTicketRepository;

    @Mock
    private TicketMapper ticketMapper;

    @Mock
    private SeatsMapper seatsMapper;

    @Mock
    private SeatsService seatsService;

    @InjectMocks
    private TicketsService ticketsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getTicketByToken_shouldReturnTicket() {
        Ticket ticket = new Ticket();
        UUID token = UUID.randomUUID();
        ticket.setToken(token.toString());

        when(ticketsRepository.findTicketByToken(token.toString())).thenReturn(Optional.of(ticket));

        Ticket returnedTicket = ticketsService.getTicketByToken(token);

        assertEquals(ticket, returnedTicket);
    }

    @Test
    void getTicketByToken_shouldThrowExceptionWhenTicketNotFound() {
        UUID token = UUID.randomUUID();

        when(ticketsRepository.findTicketByToken(token.toString())).thenReturn(Optional.empty());

        WrongTokenException wrongTokenException = assertThrows(WrongTokenException.class, () -> ticketsService.getTicketByToken(token));

        assertEquals(wrongTokenException.getMessage(), ErrorMessages.WRONG_TOKEN.getMessage());
    }

    @Test
    void purchaseTicket_shouldReturnTicketResponse() {
        // Arrange
        Seat seat = new Seat();
        SeatResponse seatResponse = new SeatResponse();
        Ticket ticket = new Ticket();
        ticket.setTicketSeat(seat);

        TicketResponse expectedResponse = new TicketResponse();
        expectedResponse.setTicketSeat(seatResponse);
        expectedResponse.setToken(ticket.getToken());

        when(seatsService.purchaseSeat(Mockito.anyInt(), Mockito.anyInt())).thenReturn(seat);
        when(ticketsRepository.save(Mockito.any(Ticket.class))).thenReturn(ticket);
        when(ticketMapper.toTicketResponse(Mockito.any(Ticket.class))).thenReturn(expectedResponse);

        // Act
        TicketResponse actualResponse = ticketsService.purchaseTicket(1, 1);

        // Assert
        assertEquals(expectedResponse.getToken(), actualResponse.getToken());
        assertEquals(expectedResponse.getTicketSeat().getColumn(), actualResponse.getTicketSeat().getColumn());
    }

    @Test
    void returnTicket_shouldReturnResponse_whenTokenIsValid() {
        UUID token = UUID.randomUUID();
        Seat seat = new Seat(); // Create and initialize seat
        seat.setRow(1);  // Set a valid row
        seat.setColumn(1); // Set a valid column

        Ticket ticket = new Ticket();
        ticket.setTicketSeat(seat); // Set the seat for the ticket

        ArchivedTicket archivedTicket = new ArchivedTicket();
        archivedTicket.setTicketSeat(seat);
        archivedTicket.setToken(ticket.getToken());// Set the seat for the archived ticket

        SeatResponse seatResponse = new SeatResponse(); // Create seat response
        seatResponse.setRow(seat.getRow());
        seatResponse.setColumn(seat.getColumn());

        ReturnTicketResponse expectedResponse = new ReturnTicketResponse();
        expectedResponse.setTicketSeat(seatResponse);

        when(ticketsRepository.deleteTicketByToken(token.toString())).thenReturn(ticket);
        when(ticketMapper.toArchiveTicket(ticket)).thenReturn(archivedTicket);
        when(archivedTicketRepository.save(archivedTicket)).thenReturn(archivedTicket);
        when(seatsMapper.toResponse(archivedTicket.getTicketSeat())).thenReturn(seatResponse);

        ReturnTicketResponse actualResponse = ticketsService.returnTicket(token);

        assertEquals(expectedResponse.getTicketSeat().getColumn(), actualResponse.getTicketSeat().getColumn());
        assertEquals(expectedResponse.getTicketSeat().getRow(), actualResponse.getTicketSeat().getRow());
        assertEquals(expectedResponse.getTicketSeat().getPrice(), actualResponse.getTicketSeat().getPrice());

        verify(archivedTicketRepository).save(archivedTicket);
        verify(seatsService).freeSeat(seat.getRow(), seat.getColumn());
    }

    @Test
    void returnTicket_shouldThrowException_whenTokenIsInvalid() {
        UUID invalidToken = UUID.randomUUID();

        when(ticketsRepository.deleteTicketByToken(invalidToken.toString())).thenReturn(null);

        assertThrows(WrongTokenException.class, () -> ticketsService.returnTicket(invalidToken));
    }

    @Test
    void countTicketsIncome_shouldReturnCorrectIncome() {
        // Arrange
        Seat seat1 = new Seat();
        seat1.setPrice(10);

        Seat seat2 = new Seat();
        seat2.setPrice(8);

        Ticket ticket1 = new Ticket();
        ticket1.setTicketSeat(seat1);

        Ticket ticket2 = new Ticket();
        ticket2.setTicketSeat(seat2);

        List<Ticket> tickets = Arrays.asList(ticket1, ticket2);

        when(ticketsRepository.findAll()).thenReturn(tickets);

        BigDecimal expectedIncome = new BigDecimal(18);

        // Act
        BigDecimal actualIncome = ticketsService.countTicketsIncome();

        // Assert
        assertEquals(expectedIncome, actualIncome);
    }

    @Test
    void getPurchasedTickets_shouldReturnCountOfPurchasedTickets() {
        // Arrange
        List<Ticket> tickets = Arrays.asList(new Ticket(), new Ticket(), new Ticket());
        when(ticketsRepository.findAll()).thenReturn(tickets);

        int expectedCount = 3;

        // Act
        int actualCount = ticketsService.getPurchasedTickets();

        // Assert
        assertEquals(expectedCount, actualCount);
    }
}