package cinema.domain.tickets;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TicketsRepository extends JpaRepository<Ticket, Long> {
    Optional<Ticket> findTicketByToken(String token);
    Ticket deleteTicketByToken(String token);
}
