package cinema.rest.seats;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Setter
@Getter
public class AllSeatsResponse {
    int rows;
    int columns;
    long totalElements;
    int totalPages;
    List<SeatResponse> seats;

    public AllSeatsResponse(int rows, int columns, List<SeatResponse> seats, long totalElements, int totalPages) {
        this.rows = rows;
        this.columns = columns;
        this.seats = seats;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

}
