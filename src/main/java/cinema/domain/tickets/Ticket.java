package cinema.domain.tickets;

import cinema.rest.seats.SeatDTO;

import java.util.UUID;

public class Ticket {
    protected UUID token;
    protected SeatDTO ticket;

    public Ticket(SeatDTO ticket) {
        this.ticket = ticket;
        this.token = UUID.randomUUID();
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }

    public SeatDTO getTicket() {
        return ticket;
    }

    public void setTicket(SeatDTO ticket) {
        this.ticket = ticket;
    }
}
