package cinema.domain.tickets;


import java.util.UUID;

import cinema.domain.seats.Seat;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@NoArgsConstructor
public class Ticket {
    protected UUID token = UUID.randomUUID();;
    protected Seat ticketSeat;

    public Ticket(Seat ticketSeat) {
        this.ticketSeat = ticketSeat;
    }

}
