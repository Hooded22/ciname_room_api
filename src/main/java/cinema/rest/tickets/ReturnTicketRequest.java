package cinema.rest.tickets;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class ReturnTicketRequest {
    UUID token;

    public ReturnTicketRequest() {
    }

    public ReturnTicketRequest(UUID token) {
        this.token = token;
    }

}
