package cinema.domain.tickets;

import cinema.domain.archivedTicket.ArchivedTicket;
import cinema.domain.seats.SeatsMapper;
import cinema.rest.seats.SeatResponse;
import cinema.rest.tickets.TicketResponse;
import cinema.rest.tickets.ReturnTicketResponse;

public class TicketMapper {
    public TicketResponse toTicketResponse(Ticket ticket) {
        TicketResponse ticketResponse = new TicketResponse();
        SeatResponse seatResponse = new SeatsMapper().toResponse(ticket.getTicketSeat());

        ticketResponse.setTicketSeat(seatResponse);
        ticketResponse.setToken(ticket.getToken());

        return ticketResponse;
    }

    public ReturnTicketResponse toReturnTicketResponse(Ticket ticket) {
        ReturnTicketResponse returnTicketResponse = new ReturnTicketResponse();
        SeatResponse seatResponse = new SeatsMapper().toResponse(ticket.getTicketSeat());
        returnTicketResponse.setTicketSeat(seatResponse);

        return returnTicketResponse;
    }

    public ArchivedTicket toArchiveTicket(Ticket ticket) {
        ArchivedTicket archivedTicket = new ArchivedTicket();

       archivedTicket.setTicketSeat(ticket.getTicketSeat());
       archivedTicket.setToken(ticket.getToken());

       return archivedTicket;
    }
}
