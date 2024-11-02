package cinema.tickets.dto;

import cinema.seats.dto.SeatDTO;

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
