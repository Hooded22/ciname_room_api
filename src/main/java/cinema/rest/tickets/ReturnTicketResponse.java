package cinema.rest.tickets;

import cinema.rest.seats.SeatResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ReturnTicketResponse {
    SeatResponse ticketSeat;

    public ReturnTicketResponse(SeatResponse ticketSeat) {
        this.ticketSeat = ticketSeat;
    }

}
