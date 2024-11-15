package cinema.domain.archivedTicket;

import cinema.domain.tickets.Ticket;
import lombok.Getter;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Getter
@Repository
public class ArchivedTicketRepository {
    List<ArchivedTicket> returnedTickets = new ArrayList<>();

    public ArchivedTicket addReturnedTicket(Ticket ticket) {

        ArchivedTicket archivedTicket = new ArchivedTicket(ticket.getTicketSeat(), ticket.getToken());
        returnedTickets.add(archivedTicket);

        return archivedTicket;
    }
}
