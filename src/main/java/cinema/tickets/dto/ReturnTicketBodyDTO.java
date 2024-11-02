package cinema.tickets.dto;

import java.util.UUID;

public class ReturnTicketBodyDTO {
    UUID token;

    public ReturnTicketBodyDTO() {
    }

    public ReturnTicketBodyDTO(UUID token) {
        this.token = token;
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }
}
