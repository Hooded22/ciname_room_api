package cinema.domain.tickets;

import cinema.domain.exceptions.ErrorMessages;
import cinema.domain.seats.Seat;
import cinema.domain.seats.SeatsService;
import cinema.rest.seats.SeatDTO;
import cinema.rest.tickets.dto.ReturnTicketResponseDTO;
import cinema.domain.exceptions.WrongTokenException;
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
            throw new WrongTokenException(ErrorMessages.WRONG_TOKEN.getMessage());
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
