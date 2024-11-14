package cinema.rest.tickets.exceptions;

public enum TicketsErrorMessages {
    WRONG_TOKEN("Wrong token!");

    private final String message;

    TicketsErrorMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
