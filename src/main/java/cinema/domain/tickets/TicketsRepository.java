package cinema.domain.tickets;

import cinema.domain.archivedTicket.ArchivedTicket;
import lombok.Getter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public interface TicketsRepository extends JpaRepository<Ticket, UUID> {
    Ticket deleteTicketByToken(UUID token);
}
