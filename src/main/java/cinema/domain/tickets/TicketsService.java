package cinema.domain.tickets;

import cinema.domain.archivedTicket.ArchivedTicket;
import cinema.domain.archivedTicket.ArchivedTicketRepository;
import cinema.domain.exceptions.ErrorMessages;
import cinema.domain.seats.Seat;
import cinema.domain.seats.SeatsMapper;
import cinema.domain.seats.SeatsService;
import cinema.rest.tickets.ReturnTicketResponse;
import cinema.domain.exceptions.WrongTokenException;
import cinema.rest.tickets.TicketResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class TicketsService {

    private final TicketsRepository ticketsRepository;
    private final ArchivedTicketRepository archivedTicketRepository;
    private final SeatsService seatsService;

    @Autowired
    public TicketsService(TicketsRepository ticketsRepository, SeatsService seatsService, ArchivedTicketRepository archivedTicketRepository) {
        this.ticketsRepository = ticketsRepository;
        this.seatsService = seatsService;
        this.archivedTicketRepository = archivedTicketRepository;
    }

    public TicketResponse purchaseTicket(int row, int col) {
        Seat seat = seatsService.purchaseSeat(row, col);
        Ticket ticket = new Ticket();
        ticket.setTicketSeat(seat);

        Ticket addedTicket = ticketsRepository.addTicket(ticket);

        return TicketMapper.toTicketResponse(addedTicket);
    }

    public ReturnTicketResponse returnTicket(UUID token) {
        Ticket deletedTicket = ticketsRepository.deleteTicketByToken(token);
        if (deletedTicket == null) {
            throw new WrongTokenException(ErrorMessages.WRONG_TOKEN.getMessage());
        } else {
            ArchivedTicket archivedTicket = archivedTicketRepository.addReturnedTicket(deletedTicket);
            seatsService.freeSeat(archivedTicket.getTicketSeat().getRow(), archivedTicket.getTicketSeat().getColumn());

            ReturnTicketResponse returnTicketResponse = new ReturnTicketResponse();
            returnTicketResponse.setTicketSeat(SeatsMapper.toResponse(archivedTicket.getTicketSeat()));


            return returnTicketResponse;
        }
    }

    public BigDecimal countTicketsIncome() {
        List<Ticket> allTickets = ticketsRepository.getTickets();
        int ticketsPrice = allTickets.stream().mapToInt(item -> item.getTicketSeat().getPrice()).sum();

        return new BigDecimal(ticketsPrice);
    }

    public int getPurchasedTickets() {
        return ticketsRepository.getTickets().size();
    }

}
