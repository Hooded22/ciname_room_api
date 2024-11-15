package cinema.rest.tickets;

import cinema.domain.seats.Seat;
import cinema.rest.seats.SeatResponse;
import lombok.*;

import java.util.UUID;

@Data
@Setter
@Getter
@NoArgsConstructor
public class TicketResponse {
    private UUID token;
    private SeatResponse ticketSeat;


    public TicketResponse(SeatResponse ticketSeat, UUID token) {
        this.ticketSeat = ticketSeat;
        this.token = token;
    }
}