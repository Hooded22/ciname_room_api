package cinema.tickets;

import cinema.seats.Seat;
import cinema.seats.SeatsService;
import cinema.seats.dto.SeatDTO;
import cinema.tickets.dto.ReturnTicketResponseDTO;
import cinema.tickets.exceptions.TicketsErrorMessages;
import cinema.tickets.exceptions.WrongTokenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class TicketsService {

    @Autowired
    TicketsRepository ticketsRepository;
    @Autowired
    SeatsService seatsService;

    public Ticket purchaseTicket(int row, int col) {
        Seat seat = seatsService.purchaseSeat(row, col);
        Ticket ticket = new Ticket(SeatDTO.convertSeat(seat));

        return ticketsRepository.addTicket(ticket);
    }

    public ReturnTicketResponseDTO returnTicket(UUID token) {
        Ticket deletedTicket = ticketsRepository.deleteTicketByToken(token);
        if (deletedTicket == null) {
            throw new WrongTokenException(TicketsErrorMessages.WRONG_TOKEN.getMessage());
        } else {
            ArchivedTicket archivedTicket = ticketsRepository.addReturnedTicket(deletedTicket);
            seatsService.freeSeat(archivedTicket.getTicket().getRow(), archivedTicket.ticket.getColumn());

            return new ReturnTicketResponseDTO(archivedTicket.getTicket());
        }
    }

    public BigDecimal countTicketsIncome() {
        List<Ticket> allTickets = ticketsRepository.getTickets();
        int ticketsPrice = allTickets.stream().mapToInt(item -> item.getTicket().getPrice()).sum();

        return new BigDecimal(ticketsPrice);
    }

    public int getPurchasedTickets() {
        return ticketsRepository.getTickets().size();
    }

}
