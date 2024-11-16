package cinema.domain.archivedTicket;

import cinema.domain.seats.Seat;
import cinema.domain.tickets.Ticket;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Entity
@Table(name = "ArchivedTicket")
@Data
@NoArgsConstructor
public class ArchivedTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID token;

    @ManyToOne
    @JoinColumn(name = "seat_id")
    private Seat ticketSeat;

    public ArchivedTicket(Seat ticketSeat, UUID token) {
        this.ticketSeat = ticketSeat;
        this.token = token;
    }
}