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
        Seat seat = seatsRepository.findByRowAndColumn(row, colum);

        if (seat == null) {
            throw new SeatNotFoundException(ErrorMessages.SEAT_NOT_FOUND.getMessage());
        }

        if (seat.isOccupied()) {
            throw new SeatOccupiedException(ErrorMessages.SEAT_OCCUPIED.getMessage());
        }

        return seatsRepository.findByIdAndUpdateIsOccupied(seat.getId(), true);
    }

    public Seat freeSeat(int row, int colum) {
        Seat seat = seatsRepository.findByRowAndColumn(row, colum);

        if (seat == null) {
            throw new SeatNotFoundException(ErrorMessages.SEAT_NOT_FOUND.getMessage());
        }

        return seatsRepository.findByIdAndUpdateIsOccupied(seat.getId(), false);
    }

    public int getNumberOfFreeSeats() {
        List<Seat> seats = seatsRepository.findAll();
        return seats.stream().filter(seat -> !seat.isOccupied()).toList().size();
    }


}
