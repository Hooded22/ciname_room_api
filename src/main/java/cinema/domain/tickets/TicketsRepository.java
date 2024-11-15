package cinema.domain.tickets;

import cinema.domain.archivedTicket.ArchivedTicket;
import lombok.Getter;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Repository
public class TicketsRepository {
    List<Ticket> tickets = new ArrayList<>();

    public Ticket findTicketByToken(UUID token) {
        for (Ticket ticket : tickets) {
            if (ticket.getToken().equals(token)) {
                return ticket;
            }
        }
        return null;
    }

    public Ticket deleteTicketByToken(UUID token) {
        for (Ticket ticket : tickets) {
            if (ticket.getToken().equals(token)) {
                tickets.remove(ticket);
                return ticket;
            }
        }
        return null;
    }

    public Ticket addTicket(Ticket ticket) {
        tickets.add(ticket);
        return ticket;
    }

}
