package cinema.domain.seats;

import cinema.domain.exceptions.ErrorMessages;
import cinema.domain.exceptions.SeatNotFoundException;
import cinema.domain.exceptions.SeatOccupiedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatsService {
    private final SeatsRepository seatsRepository;

    @Autowired
    public SeatsService(SeatsRepository seatsRepository) {
        this.seatsRepository = seatsRepository;
    }

    @Cacheable(value = "seats")
    public Page<Seat> getAllSeats(Pageable pageable) {
        Page<Seat> seats = seatsRepository.findAll(pageable);
        return seatsRepository.findAll(pageable);
    }

    @CacheEvict(value = {"seats", "statistics"}, allEntries = true)
    public Seat purchaseSeat(int row, int colum) {
        Seat seat = seatsRepository.findByRowAndColumn(row, colum);

        if (seat == null) {
            throw new SeatNotFoundException(ErrorMessages.SEAT_NOT_FOUND.getMessage());
        }

        if (seat.isOccupied()) {
            throw new SeatOccupiedException(ErrorMessages.SEAT_OCCUPIED.getMessage());
        }

        seat.setOccupied(true);

        return seatsRepository.save(seat);
    }

    @CacheEvict(value = {"seats", "statistics"}, allEntries = true)
    public Seat freeSeat(int row, int colum) {
        Seat seat = seatsRepository.findByRowAndColumn(row, colum);

        if (seat == null) {
            throw new SeatNotFoundException(ErrorMessages.SEAT_NOT_FOUND.getMessage());
        }

        seat.setOccupied(false);

        return seatsRepository.save(seat);
    }

    @Cacheable(value = "statistics", key = "'free_seats_count'")
    public int getNumberOfFreeSeats() {
        List<Seat> seats = seatsRepository.findAll();
        return seats.stream().filter(seat -> !seat.isOccupied()).toList().size();
    }


}
