package cinema.domain.tickets;


import cinema.domain.seats.Seat;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Entity
@Table(name = "Ticket")
@Data
@NoArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID token = UUID.randomUUID();

    @ManyToOne
    @JoinColumn(name = "seat_id")
    private Seat ticketSeat;

    public Ticket(Seat ticketSeat) {
        this.ticketSeat = ticketSeat;
    }
}
