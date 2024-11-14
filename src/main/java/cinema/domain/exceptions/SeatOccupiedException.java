package cinema.domain.exceptions;

public class SeatOccupiedException extends RuntimeException {
    public SeatOccupiedException(String message) {
        super(message);
    }
}
