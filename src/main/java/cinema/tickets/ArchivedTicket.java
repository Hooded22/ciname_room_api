package cinema.tickets;

import cinema.seats.dto.SeatDTO;

import java.util.UUID;

public class ArchivedTicket extends Ticket {
    public ArchivedTicket(SeatDTO ticket, UUID token) {
        super(ticket);
        this.token = token;
    }
}
