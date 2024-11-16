package cinema.domain.seats;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatsRepository extends JpaRepository<Seat, Long> {
    Seat findBySeatRowAndSeatColumn(int seatRow, int seatColumn);
}
