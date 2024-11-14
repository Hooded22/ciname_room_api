package cinema.domain.seats;

import cinema.domain.exceptions.ErrorMessages;
import cinema.domain.exceptions.SeatNotFoundException;
import cinema.domain.resources.CinemaConstants;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class SeatsRepository {
    int MAX_ROWS = CinemaConstants.MAX_ROWS_NUMBER;
    int MAX_COLUMNS = CinemaConstants.MAX_COLUMNS_NUMBER;
    List<Seat> seats = generateSeats(MAX_ROWS, MAX_COLUMNS);

    public List<Seat> findAll() {
        return seats;
    }

    private List<Seat> generateSeats(int rows, int columns) {
        List<Seat> seats = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                seats.add(new Seat(i + 1, j + 1));
            }
        }
        return seats;
    }

    public Seat findByRowAndColumn(int row, int column) {
        return seats.stream().filter(seat -> seat.getRow() == row && seat.getColumn() == column).findFirst().orElse(null);
    }

    public Seat findByIdAndUpdateIsOccupied(Long id, boolean isOccupied) {
        for (int i = 0; i < seats.size(); i++) {
            if (seats.get(i).getId().equals(id)) {
                Seat seat = seats.get(i);
                seat.setOccupied(isOccupied);
                return seats.get(i);
            }
        }
        throw new SeatNotFoundException(ErrorMessages.SEAT_NOT_FOUND.getMessage());
    }
}
