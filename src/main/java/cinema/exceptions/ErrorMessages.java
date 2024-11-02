package cinema.exceptions;

public enum ErrorMessages {
    TICKET_ALREADY_PURCHASED("The ticket has been already purchased!"),
    SEAT_NOT_FOUND("The number of a row or a column is out of bounds!"),
    WRONG_PASSWORD("The password is wrong!");

    private final String message;

    ErrorMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
