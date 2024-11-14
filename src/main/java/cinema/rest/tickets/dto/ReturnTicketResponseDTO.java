package cinema.rest.tickets.dto;

import cinema.rest.seats.SeatDTO;

public class ReturnTicketResponseDTO {
    SeatDTO ticket;

    public ReturnTicketResponseDTO(SeatDTO ticket) {
        this.ticket = ticket;
    }

    public SeatDTO getTicket() {
        return ticket;
    }

    public void setTicket(SeatDTO ticket) {
        this.ticket = ticket;
    }
}
