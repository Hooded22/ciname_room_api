package cinema.domain.tickets;

import java.io.Serializable;

import cinema.domain.seats.Seat;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Entity
@Table(name = "Ticket")
@Data
@NoArgsConstructor
public class Ticket implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(36)")
    private String token = UUID.randomUUID().toString();

    @ManyToOne
    @JoinColumn(name = "seat_id")
    private Seat ticketSeat;

    public Ticket(Seat ticketSeat) {
        this.ticketSeat = ticketSeat;
    }

    public UUID getTokenAsUUID() {
        return UUID.fromString(token);
    }
}
