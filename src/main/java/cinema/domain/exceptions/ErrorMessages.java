package cinema.domain.exceptions;

public enum ErrorMessages {
    SEAT_NOT_FOUND("The number of a row or a column is out of bounds!"),
    SEAT_OCCUPIED("Seat is already occupied."),
    WRONG_TOKEN("Wrong token!"),
    TICKET_ALREADY_PURCHASED("The ticket has been already purchased!"),
    WRONG_PASSWORD("The password is wrong!");

    private final String message;

    ErrorMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
