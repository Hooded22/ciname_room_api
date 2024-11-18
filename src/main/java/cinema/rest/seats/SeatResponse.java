package cinema.rest.seats;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SeatResponse {
    private int row;
    private int column;
    private int price;

    public SeatResponse() {
    }

    public SeatResponse(int row, int column, int price) {
        this.row = row;
        this.column = column;
        this.price = price;
    }

}
