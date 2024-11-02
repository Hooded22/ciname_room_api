package cinema.seats.exception;

public enum ErrorMessages {
    SEAT_NOT_FOUND("The number of a row or a column is out of bounds!"),
    SEAT_OCCUPIED("Seat is already occupied.");

    private final String message;

    ErrorMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
