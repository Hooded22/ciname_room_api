package cinema.domain.tickets;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class TicketsRepository {
    List<Ticket> tickets = new ArrayList<>();
    List<ArchivedTicket> returnedTickets = new ArrayList<>();

    public List<Ticket> getTickets() {
        return tickets;
    }

    public List<ArchivedTicket> getReturnedTickets() {
        return returnedTickets;
    }

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

    public ArchivedTicket addReturnedTicket(Ticket ticket) {

        ArchivedTicket archivedTicket = new ArchivedTicket(ticket.getTicket(), ticket.getToken());
        returnedTickets.add(archivedTicket);

        return archivedTicket;
    }

    public Ticket addTicket(Ticket ticket) {
        tickets.add(ticket);
        return ticket;
    }

}
