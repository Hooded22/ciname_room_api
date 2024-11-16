package cinema.domain.archivedTicket;

import cinema.domain.tickets.Ticket;
import lombok.Getter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface ArchivedTicketRepository extends JpaRepository<ArchivedTicket, Integer> {
}
