package cinema.domain.archivedTicket;

import cinema.domain.seats.Seat;
import cinema.domain.tickets.Ticket;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Data
@Setter
@Getter
@NoArgsConstructor
public class ArchivedTicket {
    private Long id;
    protected UUID token;
    private Seat ticketSeat;

    public ArchivedTicket(Seat ticketSeat, UUID token) {
        this.ticketSeat = ticketSeat;
        this.token = token;
    }

    public ArchivedTicket(Ticket ticket) {
        this.ticketSeat = ticket.getTicketSeat();
        this.token = ticket.getToken();
    }
}