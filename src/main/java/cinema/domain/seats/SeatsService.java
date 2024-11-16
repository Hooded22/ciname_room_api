package cinema.domain.seats;

import cinema.domain.exceptions.ErrorMessages;
import cinema.domain.exceptions.SeatNotFoundException;
import cinema.domain.exceptions.SeatOccupiedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatsService {
    private final SeatsRepository seatsRepository;

    @Autowired
    public SeatsService(SeatsRepository seatsRepository) {
        this.seatsRepository = seatsRepository;
    }

    public List<Seat> getAllSeats() {
        return seatsRepository.findAll();
    }

    public Seat purchaseSeat(int row, int colum) {
        Seat seat = seatsRepository.findBySeatRowAndSeatColumn(row, colum);

        if (seat == null) {
            throw new SeatNotFoundException(ErrorMessages.SEAT_NOT_FOUND.getMessage());
        }

        if (seat.isOccupied()) {
            throw new SeatOccupiedException(ErrorMessages.SEAT_OCCUPIED.getMessage());
        }

        seat.setOccupied(true);

        return seatsRepository.save(seat);
    }

    public Seat freeSeat(int row, int colum) {
        Seat seat = seatsRepository.findBySeatRowAndSeatColumn(row, colum);

        if (seat == null) {
            throw new SeatNotFoundException(ErrorMessages.SEAT_NOT_FOUND.getMessage());
        }

        seat.setOccupied(false);

        return seatsRepository.save(seat);
    }

    public int getNumberOfFreeSeats() {
        List<Seat> seats = seatsRepository.findAll();
        return seats.stream().filter(seat -> !seat.isOccupied()).toList().size();
    }


}
